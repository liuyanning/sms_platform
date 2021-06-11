package com.hero.wireless.sms.sender.service;

import com.drondea.wireless.util.*;
import com.hero.wireless.enums.*;
import com.hero.wireless.sharding.ShardingKeyGenerator;
import com.hero.wireless.web.config.DatabaseCache;
import com.hero.wireless.web.dao.business.IEnterpriseUserDAO;
import com.hero.wireless.web.dao.send.*;
import com.hero.wireless.web.dao.send.ext.IInputLogExtDAO;
import com.hero.wireless.web.dao.send.ext.IReportExtDAO;
import com.hero.wireless.web.dao.send.ext.IReportNotifyExtDAO;
import com.hero.wireless.web.dao.send.ext.ISubmitExtDAO;
import com.hero.wireless.web.entity.base.Pagination;
import com.hero.wireless.web.entity.base.ShardingBatchInsert;
import com.hero.wireless.web.entity.business.*;
import com.hero.wireless.web.entity.send.*;
import com.hero.wireless.web.entity.send.ext.SubmitExt;
import com.hero.wireless.web.service.IBusinessManage;
import com.hero.wireless.web.service.ISendManage;
import com.hero.wireless.web.util.BlackListUtil;
import com.hero.wireless.web.util.CodeUtil;
import com.hero.wireless.web.util.QueueUtil;
import com.hero.wireless.web.util.SMSUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static com.drondea.wireless.util.DateTime.Y_M_D_2;

/**
 * 业务
 * 
 * @author Administrator
 */
public abstract class AbstractSenderService implements ISenderSmsService {

	@Autowired
	ISubmitDAO<Submit> submitDAO;
	@Autowired
	ISubmitExtDAO submitExtDAO;
	@Autowired
	ISubmitExpiredDAO<SubmitExpired> submitExpiredDAO;
	@Autowired
	IReportDAO<Report> reportDAO;
	@Autowired
	IReportExtDAO reportExtDAO;
	@Autowired
	IInboxDAO<Inbox> inboxDAO;
	@Autowired
	IInputLogDAO<InputLog> inputLogDAO;
	@Autowired
	IInputLogExtDAO inputLogExtDAO;
	@Autowired
	IInputDAO<Input> inputDAO;
	@Autowired
	IAutoReplySmsDAO<AutoReplySms> autoReplySmsDAO;
	@Resource(name = "businessManage")
	IBusinessManage businessManage;
	@Autowired
	IEnterpriseUserDAO<EnterpriseUser> enterpriseUserDAO;
	@Resource
    IReportExtraDAO<ReportExtra> reportExtraDAO;
	@Resource(name = "sendManage")
	private ISendManage sendManage;
	@Resource(name = "reportNotifyExtDAO")
	IReportNotifyExtDAO reportNotifyExtDAO;
	@Resource(name = "ISubmitAwaitDAO")
	protected ISubmitAwaitDAO<SubmitAwait> submitAwaitDAO;
	@Resource(name = "IReportNotifyAwaitDAO")
	protected IReportNotifyAwaitDAO<ReportNotifyAwait> reportNotifyAwaitDAO;


	@Override
	public boolean saveMO(Inbox inbox) {
		Channel channel = DatabaseCache.getChannelCachedByNo(inbox.getChannel_No());
		inbox.setGroup_Code(channel.getGroup_Code());
		Submit submit = moMatchSubmit(inbox);
		inbox.setCountry_Code(submit.getCountry_Code());
		inbox.setEnterprise_No(StringUtils.defaultIfEmpty(submit.getEnterprise_No(), ""));
		inbox.setAgent_No(StringUtils.defaultIfEmpty(submit.getAgent_No(), ""));
		inbox.setEnterprise_User_Id(
				ObjectUtils.isEmpty(submit.getEnterprise_User_Id()) ? 0 : submit.getEnterprise_User_Id());
		inbox.setProtocol_Type_Code(
				StringUtils.defaultIfEmpty(submit.getProtocol_Type_Code(), ProtocolType.WEB.toString()));
		inbox.setNotify_Total(0);
		inbox.setPull_Total(0);
		inbox.setSub_Code(StringUtils.defaultString(submit.getSub_Code(), ""));
		if (StringUtils.isEmpty(inbox.getSP_Number())) {
			inbox.setSP_Number(StringUtils.defaultString(submit.getSP_Number(), inbox.getSP_Number()));
		}
		inbox.setChannel_No(StringUtils.defaultString(submit.getChannel_No(), inbox.getChannel_No()));
		inbox.setGroup_Code(StringUtils.defaultString(inbox.getGroup_Code(), submit.getChannel_No()));
		inbox.setNotify_Status_Code(NotifyStatus.UNNOTIFY.toString());
		inbox.setSender_Local_IP(submit.getSender_Local_IP());
		inbox.setContent(StringUtils.defaultIfEmpty(inbox.getContent(), ""));
		inbox.setInput_Msg_No(submit.getMsg_Batch_No());
		inbox.setInput_Sub_Code(submit.getInput_Sub_Code());
		inbox.setInput_Create_Date(submit.getInput_Log_Date());
		setInboxInputInfo(submit.getMsg_Batch_No(), submit.getInput_Log_Date(), inbox);

		inboxDAO.insert(inbox);
		// 未匹配到企业直接返回
		if (inbox.getEnterprise_User_Id() == 0) {
			return true;
		}
		autoAddBlack(inbox, submit.getCountry_Code());
		autoReplySms(inbox);
		return true;
	}

	/**
	 * 设置用户提交相关信息，重点是用户提交的码号
	 * @param msgBatchNo
	 * @param inputLogDate
	 * @param inbox
	 */
	@Override
	public void setInboxInputInfo(String msgBatchNo, Date inputLogDate, Inbox inbox) {
		if (StringUtils.isNotEmpty(msgBatchNo)) {
			// 匹配inputlog
			InputLogExample inputLogExample = new InputLogExample();
			InputLogExample.Criteria criteria = inputLogExample.createCriteria();
			criteria.andMsg_Batch_NoEqualTo(msgBatchNo);
			//分表要限定时间，确定表
			criteria.andCreate_DateBetween(DateTime.getStartOfDay(inputLogDate), DateTime.getEndOfDay(inputLogDate));
			inputLogExample.setPagination(new Pagination(0, 1));
			List<InputLog> inputLogs = this.inputLogDAO.selectByExample(inputLogExample);
			if (!ObjectUtils.isEmpty(inputLogs)) {
				InputLog inputLog = inputLogs.get(0);
				inbox.setInput_Content(inputLog.getContent());
				inbox.setInput_Create_Date(inputLog.getCreate_Date());
				inbox.setInput_Msg_No(inputLog.getMsg_Batch_No());
				inbox.setInput_Sub_Code(inputLog.getSub_Code());
			}
		}
	}

	@Override
	public void saveReport(List<Report> list) {
		try {
			//按天拆分
			List<ShardingBatchInsert<Report>> batchInsert = SMSUtil.getBatchInsert(list, (item) -> {
				item.setId(ShardingKeyGenerator.getKey());
				return DateTime.getString(item.getSubmit_Date(), Y_M_D_2);
			});
			for (ShardingBatchInsert<Report> insert : batchInsert) {
				reportExtDAO.insertListSharding(insert);
			}
		} catch (Exception e) {
			// 尝试单条插入
			list.forEach(item -> {
				try {
					reportDAO.insertSelective(item);
				} catch (Exception e1) {
					SuperLogger.error(item);
				}
			});
		}
	}


	@Override
	public void saveSubmit(List<Submit> list) {
		try {
			//按天拆分
			List<ShardingBatchInsert<Submit>> batchInsert = SMSUtil.getBatchInsert(list, (item) -> {
				item.setId(ShardingKeyGenerator.getKey());
				return DateTime.getString(item.getSubmit_Date(), Y_M_D_2);
			});
			for (ShardingBatchInsert<Submit> insert : batchInsert) {
				submitExtDAO.insertListSharding(insert);
			}
		} catch (Exception e) {
			// 尝试单条插入
			list.forEach(item -> {
				try {
					submitExtDAO.insertSelective(item);
				} catch (Exception e1) {
					SuperLogger.error(e1.getMessage(), e1);
					SuperLogger.error(item);
				}
			});
		}
	}

	@Override
	public void saveSubmitExpired(Submit submit) {
		String channelSubMsgNo = submit.getChannel_Msg_Id();
		submit.setChannel_Msg_Id(null);
		//生成id
		Long submitId = ShardingKeyGenerator.getKey();
		submit.setId(submitId);
		submitExtDAO.insertSelective(submit);

		SubmitExpired submitExpired = new SubmitExpired();
		submitExpired.setSubmit_Id(submitId);
		submitExpired.setSubmit_Key(channelSubMsgNo);
		submitExpired.setCreate_Date(new Date());
		submitExpiredDAO.insert(submitExpired);
	}

	@Override
	public Submit getSubmitExpiredByKey(String key) {
		SubmitExpiredExample submitExpiredExample = new SubmitExpiredExample();
		SubmitExpiredExample.Criteria criteria = submitExpiredExample.createCriteria();
		criteria.andSubmit_KeyEqualTo(key);
		List<SubmitExpired> submitExpireds = submitExpiredDAO.selectByExample(submitExpiredExample);
		if (!ObjectUtils.isEmpty(submitExpireds)) {
			SubmitExpired submitExpired = submitExpireds.get(0);
			//确定表
			Date create_date = submitExpired.getCreate_Date();
			SubmitExample submitExample = new SubmitExample();
			submitExample.createCriteria().andIdEqualTo(submitExpired.getSubmit_Id()).
					andSubmit_DateBetween(DateTime.getStartOfDay(create_date), DateTime.getEndOfDay(create_date));
			List<Submit> submit = submitExtDAO.selectByExample(submitExample);
			if (submit.size() > 0) {
				return submit.get(0);
			}
		}
		return null;
	}

	@Override
	public void updateExpiredSubmit(Submit submit) {
		SubmitExample submitExample = new SubmitExample();
		submitExample.createCriteria().andIdEqualTo(submit.getId()).andSubmit_DateEqualTo(submit.getSubmit_Date());
		submitExtDAO.updateByExample(submit, submitExample);
	}

	/**
	 * 
	 * @param inbox
	 * @return
	 * @author volcano
	 * @date 2019年9月13日上午3:01:59
	 * @version V1.0
	 */
	private Submit moMatchSubmit(Inbox inbox) {
		String mobile = inbox.getPhone_No();
		mobile = mobile.replaceFirst("\\+", "");
		mobile = StringUtils.removeStart(mobile, "86");

		SubmitExt condition = new SubmitExt();
		condition.setPhone_No(mobile);
		condition.setGroup_Code(inbox.getGroup_Code());
		if (StringUtils.isEmpty(inbox.getGroup_Code())) {
			condition.setChannel_No(inbox.getChannel_No());
		}
		List<Submit> submitList = this.sendManage.queryMOSubmitList(condition);
		if (!ObjectUtils.isEmpty(submitList)) {
			return submitList.stream().filter(item -> {
				if (StringUtils.isEmpty(inbox.getSP_Number())) {
					return false;
				}
				if (StringUtils.isEmpty(item.getSP_Number())) {
					return false;
				}
				return inbox.getSP_Number().equals(item.getSP_Number());
			}).findFirst().orElse(submitList.get(0));
		}
		SuperLogger.warn("MO未匹配到企业");
		return new Submit();
	}

	private void autoReplySms(Inbox inbox) {
		// 自动回复短信
		AutoReplySmsExample autoReplySmsExample = new AutoReplySmsExample();
		AutoReplySmsExample.Criteria autoReplySmsCriteria = autoReplySmsExample.createCriteria();
		autoReplySmsCriteria.andEnterprise_NoEqualTo(inbox.getEnterprise_No());
		autoReplySmsCriteria.andKey_WordEqualTo(inbox.getContent());
		List<AutoReplySms> autoReplySmsList = autoReplySmsDAO.selectByExample(autoReplySmsExample);
		if (!ObjectUtils.isEmpty(autoReplySmsList)) {
			Enterprise enterprise = DatabaseCache.getEnterpriseByNo(inbox.getEnterprise_No());
			EnterpriseUser user = DatabaseCache.getEnterpriseUserCachedById(inbox.getEnterprise_User_Id());
			AutoReplySms autSms = autoReplySmsList.get(0);
			// 插入自动回复短信
			Input autoInput = new Input();
			autoInput.setMsg_Batch_No(CodeUtil.buildMsgNo());
			autoInput.setEnterprise_No(inbox.getEnterprise_No());
			autoInput.setAgent_No(enterprise.getAgent_No());
			autoInput.setBusiness_User_Id(enterprise.getBusiness_User_Id());
			autoInput.setEnterprise_User_Id(inbox.getEnterprise_User_Id());
			autoInput.setIs_LMS(SMSUtil.isLms(autSms.getContent()));
			autoInput.setMessage_Type_Code(MessageType.SMS.toString());
			autoInput.setContent(autSms.getContent());
			autoInput.setPhone_Nos(StringUtils.removeStart(inbox.getPhone_No(), "86"));
			autoInput.setProtocol_Type_Code(ProtocolType.SYSTEM.toString());
			autoInput.setSource_IP(SMSUtil.localIp());
			autoInput.setPriority_Number(Priority.HIGH_LEVEL.value());
			autoInput.setAudit_Status_Code(ContentAuditStatus.PASSED.toString());
			autoInput.setSub_Code(user.getSub_Code());
			autoInput.setInput_Date(new Date());
			autoInput.setCreate_Date(new Date());
			autoInput.setCountry_Code(inbox.getCountry_Code());
			autoInput.setGate_Ip(IpUtil.getLocalIp());
			//inputDAO.insert(autoInput);
			QueueUtil.notifySorter(autoInput, user.getPriority_Level());
		}
	}

	/**
	 * 自动加入黑名单
	 * 
	 * @param inbox
	 */
	private void autoAddBlack(Inbox inbox, String countryCode) {
		String content = inbox.getContent();
		String phone = inbox.getPhone_No();
		String[] keys = DatabaseCache.getStringValueBySystemEnvAndCode("unsubscribe", "T,TD,退订").split(",");
		boolean isUnsubscribe = Arrays.asList(keys).stream().anyMatch(item -> content.equalsIgnoreCase(item));
		if (!isUnsubscribe) {
			return;
		}
		String newPhone = phone.replaceFirst("\\+", "");;
		if("86".equals(countryCode) && !StringUtils.startsWith(newPhone, "86")){
			newPhone = inbox.getCountry_Code() + newPhone;
		}

		boolean isAutoFilterBlack = DatabaseCache.isUserBlackSwitch(String.valueOf(inbox.getEnterprise_User_Id()));

		boolean isBlack = BlackListUtil.isBlack(newPhone, "", inbox.getEnterprise_No(),
				inbox.getEnterprise_User_Id(), true, isAutoFilterBlack);
		if (!isBlack) {
			BlackList blRecord = new BlackList();
			blRecord.setEnterprise_No(inbox.getEnterprise_No());
			blRecord.setPool_Code("");
			if(isAutoFilterBlack){
				blRecord.setEnterprise_User_Id(inbox.getEnterprise_User_Id());
				BlackListUtil.addBlackList(inbox.getEnterprise_No(), "", inbox.getEnterprise_No(), inbox.getEnterprise_User_Id());
			} else {
				BlackListUtil.addBlackList(inbox.getEnterprise_No(), "", inbox.getEnterprise_No(), null);
			}
			blRecord.setPhone_No(newPhone);
			SmsRoute smsRouteByNumber = DatabaseCache.getSmsRouteByNumber(phone,countryCode);
			if(smsRouteByNumber != null){
				blRecord.setRoute_Name_Code(smsRouteByNumber.getRoute_Name_Code());
			}
			blRecord.setCreate_User("system");
			blRecord.setCreate_Date(new Date());
			businessManage.addBlackList(blRecord);
		}
	}

	@Override
	public void querySubmittedId(Report report) {
		long startTime = System.currentTimeMillis();
		SubmitExample example = new SubmitExample();
		SubmitExample.Criteria criteria = example.createCriteria();
		criteria.andMsg_Batch_NoEqualTo(report.getMsg_Batch_No());
		criteria.andPhone_NoEqualTo(report.getPhone_No());
		criteria.andSubmit_DateEqualTo(report.getSubmit_Date());
		criteria.andData_Status_CodeEqualTo(DataStatus.NORMAL.toString());
		List<Submit> list = this.submitDAO.selectByExample(example);

		long endTime = System.currentTimeMillis();
		SuperLogger.debug("查询Submite结束耗时：" + (endTime - startTime) + "毫秒");
		if (list.size() <= 0) {
			throw new ServiceException("查询发送记录条数为：" + list.size()+"批次号："+report.getMsg_Batch_No());
		}
		StringBuffer ids = new StringBuffer();
		list.forEach(entity -> {
			//放值的时候拼接上时间
			updateSubmittedById(entity.getId(), entity.getSubmit_Date());
		});
	}

	@Override
	public void updateSubmittedById(Long id, Date crateDate) {
		SubmitExample example = new SubmitExample();
		SubmitExample.Criteria criteria = example.createCriteria();
		criteria.andIdEqualTo(id);
		criteria.andSubmit_DateBetween(DateTime.getStartOfDay(crateDate), DateTime.getEndOfDay(crateDate));
		Submit submit = new Submit();
		submit.setData_Status_Code(DataStatus.DELETE.toString());
		this.submitDAO.updateByExampleSelective(submit, example);
	}

	@Override
	public void updateInputLogByIds(Long id, Date crateDate) {
		InputLogExample logExample = new InputLogExample();
		InputLogExample.Criteria cri = logExample.createCriteria();
		cri.andIdEqualTo(id);
		cri.andCreate_DateBetween(DateTime.getStartOfDay(crateDate), DateTime.getEndOfDay(crateDate));
		InputLog log = new InputLog();
		log.setData_Status_Code(DataStatus.DELETE.toString());
		inputLogDAO.updateByExampleSelective(log, logExample);
	}
	
	@Override
	public void saveReportExtraList(List<ReportExtra> reportExtraList){
	        try {
	            reportExtraDAO.insertList(reportExtraList);
	        } catch (Exception e){
	            reportExtraList.forEach(entity ->{
	                try {
	                    reportExtraDAO.insert(entity);
	                } catch (Exception ex) {
	                    SuperLogger.error("保存没有submit的状态报告数据异常："+entity.getChannel_Sub_Msg_No()+e.getMessage());
	            }
	        });
	    }
	}

	@Override
	public void saveInputLog(List<InputLog> inputLogs) {
		try {
			//按天拆分
			List<ShardingBatchInsert<InputLog>> batchInsert = SMSUtil.getBatchInsert(inputLogs, (item) -> {
				item.setId(ShardingKeyGenerator.getKey());
				return DateTime.getString(item.getCreate_Date(), Y_M_D_2);
			});
			for (ShardingBatchInsert<InputLog> insert : batchInsert) {
				inputLogExtDAO.insertListSharding(insert);
			}
		} catch (Exception e) {
			// 尝试单条插入
			inputLogs.forEach(item -> {
				try {
					inputLogExtDAO.insertSelective(item);
				} catch (Exception e1) {
					SuperLogger.error(e1.getMessage(), e1);
					SuperLogger.error(item);
				}
			});
		}
	}

	@Override
	public List<Submit> getSubmitAwaitAndDel(String channelNo, Long id) {
		SubmitExample example = new SubmitExample();
		SubmitExample.Criteria criteria = example.createCriteria();
		criteria.andIdGreaterThan(id);
		criteria.andChannel_NoEqualTo(channelNo);
		example.setOrderByClause(" id");
		example.setPagination(new Pagination(100));
		List<Submit> submits = this.submitExtDAO.querySubmitAwaitList(example);
		if (ObjectUtils.isEmpty(submits)) {
			return submits;
		}
		CommonThreadPoolFactory.getInstance().getBizPoolExecutor().submit(() ->
				deleteSubmitAwait(channelNo, submits.get(submits.size() - 1 ). getId()));
		return submits;
	}

	private void deleteSubmitAwait(String channelNo, Long id) {
		SubmitAwaitExample example = new SubmitAwaitExample();
		SubmitAwaitExample.Criteria criteria = example.createCriteria();
		criteria.andIdLessThanOrEqualTo(id);
		criteria.andChannel_NoEqualTo(channelNo);
		submitAwaitDAO.deleteByExample(example);
	}

	@Override
	public List<Report> getReportAwaitAndDel(Integer userId, Long id, boolean syncDel) {
		String key = "user:" + userId;
		synchronized (key.intern()) {
			ReportExample example = new ReportExample();
			ReportExample.Criteria criteria = example.createCriteria();
			criteria.andIdGreaterThan(id);
			criteria.andEnterprise_User_IdEqualTo(userId);
			example.setOrderByClause(" id");
			example.setPagination(new Pagination(100));
			List<Report> reports = this.reportExtDAO.queryReportNotifyAwaitList(example);
			if (ObjectUtils.isEmpty(reports)) {
				return reports;
			}
			if (syncDel) {
				deleteReportAwait(userId, reports.get(reports.size() - 1 ). getId());
				return reports;
			}

			CommonThreadPoolFactory.getInstance().getBizPoolExecutor().submit(() ->
					deleteReportAwait(userId, reports.get(reports.size() - 1 ). getId()));
			return reports;
		}
	}

	private void deleteReportAwait(Integer userId, Long id) {
		ReportNotifyAwaitExample example = new ReportNotifyAwaitExample();
		ReportNotifyAwaitExample.Criteria criteria = example.createCriteria();
		criteria.andIdLessThanOrEqualTo(id);
		criteria.andEnterprise_User_IdEqualTo(userId);
		reportNotifyAwaitDAO.deleteByExample(example);
	}

	@Override
	public void saveReportAwait(List<ReportNotifyAwait> list) {
		reportNotifyAwaitDAO.insertList(list);
	}
}