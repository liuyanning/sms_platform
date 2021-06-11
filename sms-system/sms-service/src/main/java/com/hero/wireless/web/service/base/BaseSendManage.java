package com.hero.wireless.web.service.base;

import com.drondea.wireless.config.Constant;
import com.drondea.wireless.config.ResultStatus;
import com.drondea.wireless.util.DateTime;
import com.drondea.wireless.util.SecretUtil;
import com.drondea.wireless.util.ServiceException;
import com.drondea.wireless.util.SuperLogger;
import com.fasterxml.jackson.core.type.TypeReference;
import com.hero.wireless.enums.*;
import com.hero.wireless.json.JsonUtil;
import com.hero.wireless.web.config.DatabaseCache;
import com.hero.wireless.web.dao.business.*;
import com.hero.wireless.web.dao.business.ext.IContactExtDAO;
import com.hero.wireless.web.dao.business.ext.ISmsTemplateExtDAO;
import com.hero.wireless.web.dao.send.*;
import com.hero.wireless.web.dao.send.ext.*;
import com.hero.wireless.web.entity.base.Pagination;
import com.hero.wireless.web.entity.business.Properties;
import com.hero.wireless.web.entity.business.*;
import com.hero.wireless.web.entity.business.ext.ContactExt;
import com.hero.wireless.web.entity.business.ext.ExportFileExt;
import com.hero.wireless.web.entity.send.*;
import com.hero.wireless.web.entity.send.ext.*;
import com.hero.wireless.web.service.IBusinessManage;
import com.hero.wireless.web.service.INoticeManage;
import com.hero.wireless.web.service.ISendManage;
import com.hero.wireless.web.util.*;
import com.hero.wireless.web.util.SMSUtil.NullChannel;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.hero.wireless.web.util.SMSUtil.getPartitionMobileList;

public class BaseSendManage extends BaseService {

	private static String FALSE = "false";
	private static String TRUE = "true";
	private static String PROCEED = "proceed";
	public static Queue<AuditSMS> waitApproveSmsQueue = new ConcurrentLinkedQueue<>();

	static {
		new Thread(() -> {
			while (true) {
				AuditSMS auditSms = null;
				synchronized (waitApproveSmsQueue) {
					auditSms = waitApproveSmsQueue.poll();
					if (auditSms == null) {
						try {
							waitApproveSmsQueue.wait();
							auditSms = waitApproveSmsQueue.poll();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
				try {
					List<InputExt> inputExtList = auditSms.getSmsList();
					String status = auditSms.getStatus();
					BaseSendManage baseSendManage = auditSms.getBaseSendManage();
					baseSendManage.doAuditSelect(inputExtList, status);
				} catch (Exception e) {//捕获异常，防止线程终止
					SuperLogger.error(e.getMessage(), e);
				}
			}
		}).start();
	}

	public ExecutorService rejectedFixedPool = Executors.newFixedThreadPool(10);
	@Resource(name = "IEnterpriseUserDAO")
	protected IEnterpriseUserDAO<EnterpriseUser> enterpriseUserDAO;
	@Resource(name = "IInputDAO")
	protected IInputDAO<Input> inputDAO;
	@Resource(name = "ISubmitAwaitDAO")
	protected ISubmitAwaitDAO<SubmitAwait> submitAwaitDAO;
	@Resource(name = "IReportNotifyAwaitDAO")
	protected IReportNotifyAwaitDAO<ReportNotifyAwait> reportNotifyAwaitDAO;
	@Resource(name = "inputExtDAO")
	protected IInputExtDAO inputExtDAO;
	@Resource(name = "inputLogExtDAO")
	protected IInputLogExtDAO inputLogExtDAO;
	@Resource(name = "IContactGroupDAO")
	protected IContactGroupDAO<ContactGroup> contactGroupDAO;
	@Resource(name = "IContactDAO")
	protected IContactDAO<Contact> contactDAO;
	@Resource(name = "contactExtDAO")
	protected IContactExtDAO contactExtDAO;
	@Resource(name = "IInboxDAO")
	protected IInboxDAO<Inbox> inboxDAO;
	@Resource(name = "inboxExtDAO")
	protected IInboxExtDAO inboxExtDAO;
	@Resource(name = "IExportFileDAO")
	protected IExportFileDAO<ExportFile> exportFileDAO;
	@Resource(name = "IAutoReplySmsDAO")
	protected IAutoReplySmsDAO<AutoReplySms> autoReplySmsDAO;
	@Resource(name = "submitExtDAO")
	protected ISubmitExtDAO submitExtDAO;
	@Resource(name = "reportExtDAO")
	protected IReportExtDAO reportExtDAO;
	@Resource(name = "reportNotifyExtDAO")
	protected IReportNotifyExtDAO reportNotifyExtDAO;
	@Resource(name = "noticeManage")
	protected INoticeManage noticeManage;
	@Resource(name = "ISmsTemplateExtDAO")
	protected ISmsTemplateExtDAO smsTemplateExtDAO;
	@Resource(name = "IMmsTemplateDAO")
	private IMmsTemplateDAO<MmsTemplate> mmsTemplateDAO;
	@Resource(name = "IMmsMaterialDAO")
	private IMmsMaterialDAO<MmsMaterial> mmsMaterialDAO;
	@Resource(name = "businessManage")
	protected IBusinessManage businessManage;
	@Autowired
	private ISendManage sendManage;


	/**
	 * 定时短信
	 *
	 * @param newInput
	 * @param userProperties
	 * @return
	 * @author volcano
	 * @date 2019年9月18日上午4:37:28
	 * @version V1.0
	 */
	private static boolean timerSendMq(Input newInput, Map<String, String> userProperties) {
		String firstGroupBeginTime = userProperties.get(PropertiesType.Property_Name.FIRST_GROUP_BEGIN_TIME.toString());
		String firstGroupEndTime = userProperties.get(PropertiesType.Property_Name.FIRST_GROUP_END_TIME.toString());
		String secondGroupBeginTime = userProperties.get(PropertiesType.Property_Name.SECOND_GROUP_BEGIN_TIME.toString());
		String secondGroupEndTime = userProperties.get(PropertiesType.Property_Name.SECOND_GROUP_END_TIME.toString());
		String isAllow = PROCEED;

		if(StringUtils.isEmpty(firstGroupBeginTime)){
			//没有限制时间段也没有设置发送时间
			if (newInput.getSend_Time() == null) {
				return false;
			}
			//校验是否是定时短信
			isAllow = checkTimeQuantum(null,null, newInput, "", null);
		} else {
			isAllow = checkTimeQuantum(firstGroupBeginTime, firstGroupEndTime, newInput, "first", secondGroupBeginTime);
			if(isAllow.equalsIgnoreCase(PROCEED)){
				isAllow = checkTimeQuantum(secondGroupBeginTime, secondGroupEndTime, newInput, "second", firstGroupBeginTime);
			}
		}
		boolean result = false;
		if (isAllow.equalsIgnoreCase(TRUE)) {
			result = true;
		}
		return result;
	}

	private static String checkTimeQuantum(String beginTime, String endTime, Input newInput, String mark, String allowBeginTime) {
		Calendar now = Calendar.getInstance();
		//设置了第二个时间段的  则设置为这个时间再发送
		Date initAllowBeginTime = DateTime.warpTodayMinDate(allowBeginTime);
		// 如果超过今天的发送时间  则设置为这个时间再发送
		Date initTime = DateTime.warpTodayMinDate(beginTime);
		//允许发送短信的最小时间点
		Date allowMinDate = DateTime.warpTodayMinDate(beginTime);
		//允许发送短信的最晚时间点儿
		Date allowMaxDate = DateTime.warpTodayMaxDate(endTime);
		if (now.getTimeInMillis() > allowMinDate.getTime() && now.getTimeInMillis() < allowMaxDate.getTime()) {
			if (newInput.getSend_Time() == null) {
				return FALSE;
			}
			Calendar sendDate = Calendar.getInstance();
			sendDate.setTime(newInput.getSend_Time());
			if (sendDate.compareTo(now) < 1) {
				return FALSE;
			}
			return TRUE;
		}

		// 定时到允许的最小时间
		if (now.getTimeInMillis() < allowMinDate.getTime()) {
			newInput.setSend_Time(allowMinDate);
			return TRUE;
		}

		// 第一个时间段，并且第二个时间段没有设置值，则定时到第二天的允许发送的第一个时间段开始时间
		if("first".equals(mark) && StringUtils.isEmpty(allowBeginTime)){
			// 明天发送
			allowMinDate = DateUtils.addDays(initTime, 1);
			newInput.setSend_Time(allowMinDate);
			return TRUE;
		}
		// 第二个时间段 定时到明天的最小时间发送
		if("second".equals(mark)){
			// 明天发送
			allowMinDate = DateUtils.addDays(initAllowBeginTime, 1);
			newInput.setSend_Time(allowMinDate);
			return TRUE;
		}
		return PROCEED;
	}

	private void doAuditSelect(List<InputExt> inputExtList, String status) {
		// 查询单次查询最大条数，如果么有设置默认3000
		int selectMax = DatabaseCache.getIntValueBySortCodeAndCode("sys_performance_setup", "selectmaxcount", 3000);
		// 循环处理
		inputExtList.forEach(inputExt -> {
			checkInputExt(inputExt);//校验
			Pagination pagination = new Pagination(1, selectMax);
			InputExample selectExample = new InputExample();
			InputExample.Criteria selectCriteria = selectExample.createCriteria();
			if ("1".equals(inputExt.getAuditFlag())) {//普通审核
				selectCriteria.andAssist_Audit_KeyEqualTo(inputExt.getAssist_Audit_Key());//md5Key
			} else if ("2".equals(inputExt.getAuditFlag())) {//批量审核
				selectCriteria.andEnterprise_NoEqualTo(inputExt.getEnterprise_No());
				selectCriteria.andEnterprise_User_IdEqualTo(inputExt.getEnterprise_User_Id());
				if (StringUtils.isNotEmpty(inputExt.getContent())) {
					selectCriteria.andContentEqualTo(inputExt.getContent());
				}
			}
			selectCriteria.andAudit_Status_CodeEqualTo(ContentAuditStatus.AUDITING.toString());// 待审核
			while (true) {
				selectExample.setPagination(pagination);
				List<Input> inputList = inputDAO.selectByExample(selectExample);
				if (ObjectUtils.isEmpty(inputList)) {
					return;
				}
				doAudit(inputList, status, inputExt);
				if (inputList.size() < selectMax) {
					return;
				}
			}
		});
	}

	private void checkInputExt(InputExt inputExt) {
		if (ObjectUtils.isEmpty(inputExt) || StringUtils.isEmpty(inputExt.getAuditFlag())) {
			SuperLogger.error("短信审核异常，数据错误");
			throw new NullPointerException("inputExt or inputExt.getAuditFlag() is null");
		}
		//普通审核
		if ("1".equals(inputExt.getAuditFlag()) && StringUtils.isEmpty(inputExt.getAssist_Audit_Key())) {
			SuperLogger.error("短信审核异常，数据错误");
			throw new NullPointerException("inputExt.getAssist_Audit_Key() is null");
		}
		//批量审核
		if ("2".equals(inputExt.getAuditFlag()) && (StringUtils.isEmpty(inputExt.getEnterprise_No())
				|| inputExt.getEnterprise_User_Id() == null)) {
			SuperLogger.error("短信批量审核异常，数据错误");
			throw new NullPointerException("inputExt.getEnterprise_No() or inputExt.getEnterprise_User_Id() is null");
		}
	}

	protected void saveInput(Input data, EnterpriseUser user, int mobileCount) throws Exception {
		saveInput(data, user, mobileCount, data.getPhone_Nos());
	}

	protected void saveInput(Input data, EnterpriseUser user, int mobileCount, String mobiles) throws Exception {
		String content = data.getContent();
		Map<String, String> userProperties = DatabaseCache.getCachedUserProperties(String.valueOf(user.getId()));
		//2020.10.21 过滤国际区号（用户属性配置）
		mobiles = replaceCountryCode(userProperties, mobiles);
		data.setId(null);
		data.setPhone_Nos(mobiles);
		data.setPhone_Nos_Count(mobileCount);
		data.setEnterprise_No(user.getEnterprise_No());
		data.setAgent_No(ObjectUtils.defaultIfNull(data.getAgent_No(), SMSUtil.DEFAULT_NO));
		data.setPriority_Number(ObjectUtils.defaultIfNull(user.getPriority_Level(), Priority.MIDDLE_LEVEL.value()));
		data.setIs_LMS(false);// 默认false 普通短信重新赋值
		// 普通短信 追加签名
		if (MessageType.SMS.toString().equalsIgnoreCase(data.getMessage_Type_Code())) {
			data.setIs_LMS(SMSUtil.isLms(content));// 是否长短信重新赋值
			// 追加签名,内容自带签名就不追加了，防重
			if (!StringUtils.isEmpty(user.getSuffix()) && !content.contains(user.getSuffix())) {
				content = addUserSignatureToContent(data.getContent(), user);
				//根据用户签名位置添加内容
				data.setContent(content);
			}
		}
		//设置是否审核短信
		data.setAudit_Status_Code(contentAuditStatusCode(user, content, mobileCount,
				MessageType.valueOf(data.getMessage_Type_Code().toUpperCase())).toString());
		data.setCreate_Date(new Date());
		data.setUUID(UUID.randomUUID().toString());
		//200310新增辅助字段：md5(enterprise_No+enterprise_User_Id+content)
		data.setAssist_Audit_Key(
				SecretUtil.MD5(data.getEnterprise_No() + data.getEnterprise_User_Id() + data.getContent()));
		//判断是否定时发送
		if (timerSendMq(data, userProperties)) {
			sendManage.insertInputs(getPartitionInput(data));
			return;
		}
		//判断是否需要审核
		if (ContentAuditStatus.PASSED.equals(data.getAudit_Status_Code())) {
			QueueUtil.notifySorter(data, user.getPriority_Level());
		} else {
			sendManage.insertInputs(getPartitionInput(data));
		}
	}

	private List<Input> getPartitionInput(Input input) {
		List<Input> allInput = new ArrayList<>();
		if (input.getPhone_Nos_Count() <= 500) {
			allInput.add(input);
			return allInput;
		}
		List<List<String>> partitionMobileList = getPartitionMobileList(input.getPhone_Nos());
		input.setPhone_Nos("");
		input.setPhone_Nos_Count(0);
		partitionMobileList.forEach(item -> {
			Input newInput = new Input();
			CopyUtil.INPUT_SELF_COPIER.copy(input, newInput, null);
			newInput.setPhone_Nos(org.apache.commons.lang3.StringUtils.join(item, ","));
			newInput.setPhone_Nos_Count(item.size());
			allInput.add(newInput);
		});
		return allInput;
	}

	//过滤国际区号（用户属性配置）
	private String replaceCountryCode(Map<String, String> userProperties, String mobiles) {
		try {
			String propertyValue  = userProperties.get(PropertiesType.Property_Name.COUNTRY_CODE_VALUE.toString());
			if(StringUtils.isNotEmpty(propertyValue)){
				StringBuffer sb = new StringBuffer();
				String[] split = mobiles.split(Constant.MUTL_MOBILE_SPLIT);
				for (String phoneNo : split) {
					if(phoneNo.startsWith(propertyValue)) {
						phoneNo = StringUtils.replaceOnce(phoneNo, propertyValue,"");
					}
					sb.append(phoneNo);
					sb.append(",");
				}
				return sb.substring(0, sb.length() - 1);
			}
		}catch (Exception e){
			SuperLogger.error(e.getMessage(),e);
		}
		return mobiles;
	}

	//发送内容拼接企业签名
	private String addUserSignatureToContent(String content, EnterpriseUser user) {
		Properties bean = new Properties();
		bean.setType_Code(PropertiesType.ENTERPRISE_USER.toString());
		bean.setType_Code_Num(String.valueOf(user.getId()));
		bean.setProperty_Name(PropertiesType.Property_Name.SIGNATURE_LOCATION.toString());
		List<Properties> properties = businessManage.queryPropertiesList(bean);
		if(ObjectUtils.isNotEmpty(properties) && "prefix".equals(properties.get(0).getProperty_Value())){
			return user.getSuffix() + content;//签名前置
		}
		return content + user.getSuffix();//默认后置
	}

	/**
	 * 获取内容审核状态
	 *
	 * @param user
	 * @param content
	 * @return
	 * @author volcano
	 * @date 2019年9月17日上午7:28:01
	 * @version V1.0
	 */
	protected ContentAuditStatus contentAuditStatusCode(EnterpriseUser user, String content, int mobileCount,
														MessageType messageType) {
		// 彩信、视频彩信 默认通过
		if (MessageType.MMS.equals(messageType) || MessageType.VIDEO.equals(messageType)) {
			return ContentAuditStatus.PASSED;
		}
		// 普通短信校验签名个数
		if (MessageType.SMS.equals(messageType)) {
			// 超过一个签名,进入审核
			String signature = SMSUtil.getSignature(content);
			if (StringUtils.isEmpty(signature) && DatabaseCache.getStringValueBySystemEnvAndCode(
					"zero_signature_operation", "ignore").equalsIgnoreCase("auditing")) {
				return ContentAuditStatus.AUDITING;
			}
		}
		// 默认免审
		String auditType = ObjectUtils.defaultIfNull(user.getAudit_Type_Code(), ContentAuditType.NO_AUDIT.toString());
		// 免审检查
		if (auditType.equalsIgnoreCase(ContentAuditType.NO_AUDIT.toString())) {
			// 检查是否设置免审核条数
			int ofAudits = user.getNumber_Of_Audits() == null ? 0 : user.getNumber_Of_Audits();
			if (ofAudits > 0 && ofAudits < mobileCount) {
				return ContentAuditStatus.AUDITING;
			}
			return ContentAuditStatus.PASSED;
		}

		if (!messageType.equals(MessageType.SMS)) {
			return ContentAuditStatus.PASSED;
		}

		// 普通短信，模板审核
		if (MessageType.SMS.equals(messageType) && auditType.equalsIgnoreCase(ContentAuditType.TEMPLATE.toString())) {
			if (SMSUtil.matchSmsTemplate(user, content)) {
				return ContentAuditStatus.PASSED;
			}
			return ContentAuditStatus.AUDITING;
		}
		return ContentAuditStatus.AUDITING;
	}

	/**
	 * ----------审核通过，调用doPassed方法--------- <br/> 审核操作-- --如果需要删除删除input表中的数据<br/> ----------审核拒绝，调用doRejected方法-------
	 *
	 * @param inputList
	 * @param status
	 * @param auditInputExt
	 */
	protected void doAudit(List<Input> inputList, String status, InputExt auditInputExt) {
		List<Long> delList = new ArrayList<>();
		if (ContentAuditStatus.PASSED.toString().equals(status)) {// 同意
			doPassed(inputList, delList, auditInputExt);
		}
		if (ContentAuditStatus.REJECT.toString().equals(status)) {// 拒绝
			doRejected(inputList, delList, auditInputExt);
		}
		if (delList.size() > 0) {
			InputExample updateExample = new InputExample();
			updateExample.createCriteria().andIdIn(delList);
			this.inputDAO.deleteByExample(updateExample);
		}
	}

	protected String validateInputContent(EnterpriseUser user, Input data) throws Exception {
		if (StringUtils.isEmpty(data.getContent())) {
			throw new ServiceException(ResultStatus.CONTENT_NOT_NULL);
		}
		int contentLength = data.getContent().length();
		int maxContentLength = DatabaseCache.getIntValueBySortCodeAndCode("sys_performance_setup",
				"max_short_message_count", 500);
		// 只有普通短信才检查短信长度
		if (MessageType.SMS.toString().equals(data.getMessage_Type_Code())) {
			if (contentLength > maxContentLength) {
				throw new ServiceException(ResultStatus.OUT_MAX_SHORT_MESSAGE_COUNT, String.valueOf(maxContentLength));
			}
		}
		if (MessageType.MMS.toString().equals(data.getMessage_Type_Code())) {
			checkMMSContent(user, data.getContent());
		}
		if (MessageType.VIDEO.toString().equals(data.getMessage_Type_Code())) {
			// 视频短信 校验模板编号是否存在
			MmsTemplateExample example = new MmsTemplateExample();
			example.createCriteria().andEnterprise_NoEqualTo(user.getEnterprise_No()).andTemplate_CodeEqualTo(
					data.getContent()).andApprove_StatusEqualTo(AuditStatus.PASS.value());
			List<MmsTemplate> list = mmsTemplateDAO.selectByExample(example);
			if (ObjectUtils.isEmpty(list)) {
				throw new ServiceException(ResultStatus.VIDEO_TEMPLATE_NOT_EXSITS);
			}
			data.setContent(list.get(0).getChannel_Template_Code());
		}
		return data.getContent();
	}

	/**
	 * 校验彩信内容
	 *
	 * @param content
	 * @throws Exception
	 */
	private void checkMMSContent(EnterpriseUser user, String content) throws Exception {
		byte[] conte = content.getBytes();
		if ((conte.length / 1024) > 128) {
			throw new ServiceException(ResultStatus.MMS_CONTENT_BEYOND_THE_MAXIMUM);
		}
		Map<String, Object> map = JsonUtil.STANDARD.readValue(content, new TypeReference<Map<String, Object>>() {
		});
		List<Map<String, String>> submitMap = JsonUtil.STANDARD.readValue(map.get("data").toString(),
				new TypeReference<List<Map<String, String>>>() {
				});
		for (int i = 0; i < submitMap.size(); i++) {
			if (StringUtils.isEmpty(submitMap.get(i).get("type")) || StringUtils.isEmpty(
					submitMap.get(i).get("content"))) {
				throw new ServiceException(ResultStatus.DATA_NOT_NULL_OR_FORMAT_ERROR);
			}
			// 校验是否和模板匹配
			if (MMSSubmitMaterialType.TXT.toString().equalsIgnoreCase(submitMap.get(i).get("type"))) {
				if (!SMSUtil.matchSmsTemplate(user, submitMap.get(i).get("content"))) {
					throw new ServiceException(ResultStatus.CONTENT_DOES_NOT_MATCH_THE_TEMPLATE);
				}
			}
			// 不是文字 则是图片 判断图片是否审核 是否存在
			if (MMSSubmitMaterialType.PICTURE.toString().equalsIgnoreCase(submitMap.get(i).get("type"))) {
				MmsMaterialExample example = new MmsMaterialExample();
				example.createCriteria().andEnterprise_NoEqualTo(user.getEnterprise_No()).andApprove_StatusEqualTo(
						AuditStatus.PASS.value()).andMaterial_CodeEqualTo(submitMap.get(i).get("content"));
				List<MmsMaterial> list = mmsMaterialDAO.selectByExample(example);
				if (ObjectUtils.isEmpty(list)) {
					throw new ServiceException(ResultStatus.MMS_MATERIAL_NOT_EXSITS);
				}
			}
		}
	}

	protected int validateInputPhoneNo(Input data) {
		int mobileCount = data.getPhone_Nos().split(Constant.MUTL_MOBILE_SPLIT).length;
		int sysMaxPhone_NoCount = DatabaseCache.getIntValueBySortCodeAndCode("sys_performance_setup",
				"max_batch_mobile_count", 500);
		if (mobileCount > sysMaxPhone_NoCount) {
			SuperLogger.debug("超出手机号码限制，手机号码个数=" + mobileCount);
			throw new ServiceException(ResultStatus.MAX_BATCH_MOBILE_COUNT, String.valueOf(sysMaxPhone_NoCount));
		}
		return mobileCount;
	}

	protected EnterpriseUser validateInputUser(Input data) {
		EnterpriseUser eUserInfo = DatabaseCache.getEnterpriseUserCachedById(data.getEnterprise_User_Id());
		if (!eUserInfo.getStatus().equals(Constant.ENTERPRISE_INFO_STATUS_NORMAL)) {
			throw new ServiceException(ResultStatus.ACCOUT_LOCKED);
		}
		return eUserInfo;
	}

	protected Enterprise validateInputEnterprise(Input data) {
		Enterprise enterprise = DatabaseCache.getEnterpriseCachedByNo(data.getEnterprise_No());
		if (!enterprise.getStatus().equals(Constant.ENTERPRISE_INFO_STATUS_NORMAL)) {
			throw new ServiceException(ResultStatus.ACCOUT_LOCKED);
		}
		return enterprise;
	}

	protected InputLog convertToInputLog(Input input) {
		return SMSUtil.buildInputLog(input);
	}

	protected ContactExample getContactExample(ContactExt condition) {
		ContactExample example = new ContactExample();
		ContactExample.Criteria cri = example.createCriteria();
		if (StringUtils.isNotBlank(condition.getPhone_No())) {
			cri.andPhone_NoEqualTo(condition.getPhone_No());
		}
		if (StringUtils.isNotBlank(condition.getEnterprise_No())) {
			cri.andEnterprise_NoEqualTo(condition.getEnterprise_No());
		}
		if (condition.getId() != null) {
			cri.andIdEqualTo(condition.getId());
		}
		if (condition.getCreate_Enterprise_User_Id() != null) {
			cri.andCreate_Enterprise_User_IdEqualTo(condition.getCreate_Enterprise_User_Id());
		}
		if (condition.getGroup_Id() != null) {
			cri.andGroup_IdEqualTo(condition.getGroup_Id());
		}
		if (!StringUtils.isEmpty(condition.getReal_Name())) {
			cri.andReal_NameLike("%" + condition.getReal_Name() + "%");
		}
		return example;
	}

	protected void exportInputLogExcel(String baseExportPath, List<Map<String, Object>> beanList,
									   ExportFileExt exportFile) {

		Object[][] titles = new Object[][]{{"Enterprise_Name", "企业名称", 4000, "Enterprise_No", ExcelTranslator.enterpriseNameFunction},
				{"Message_Type_Code", "消息类型", 4000},
				{"Content", "短信内容", 7000}, {"Phone_Nos", "手机号码", 4000}, {"Content_Length", "短信字数", 2000},
				{"Phone_Nos_Count", "号码个数", 2000}, {"Fee_Count", "计费条数", 2000}, {"Sale_Amount", "消费金额(元)", 4000},
				{"Remark", "备注", 4000}, {"Real_Name", "提交人", 4000, "Enterprise_User_Id", ExcelTranslator.enterpriseUserRealNameFunction},
				{"Create_Date", "提交时间", 6000}};
		exportAndInsert(exportFile, baseExportPath, "发件箱", titles, beanList);// 导出数据
	}

	protected void exportSubmitExcel(String path, List<Map<String, Object>> beanList, ExportFileExt exportFile,
									 String comeFlag) {

		Object[][] titles = null;
		if (Constant.PLATFORM_FLAG_ADMIN.equals(comeFlag)) {
			titles = new Object[][]{{"Channel_Name", "发送通道", 4000, "Channel_No", ExcelTranslator.channelNameFunction},
					{"Enterprise_Name", "企业名称", 4000, "Enterprise_No", ExcelTranslator.enterpriseNameFunction},
					{"Message_Type_Code", "消息类型", 4000}, {"Content", "短信内容", 7000}, {"Is_LMS_Name", "长短信", 2000},
					{"Content_Length", "字数", 2000}, {"Phone_No", "手机号码", 4000},
					{"Real_Name", "提交人", 4000, "Enterprise_User_Id", ExcelTranslator.enterpriseUserRealNameFunction},
					{"Enterprise_User_Taxes", "发票成本", 3000}, {"Channel_Taxes", "发票抵消", 4000},
					{"Channel_Unit_Price", "成本", 4000}, {"Profits", "利润", 4000}, {"Create_Date", "创建时间", 6000}};
		} else if (Constant.PLATFORM_FLAG_ENTERPRISE.equals(comeFlag) || Constant.PLATFORM_FLAG_AGENT.equals(
				comeFlag)) {
			titles = new Object[][]{{"Enterprise_Name", "企业名称", 4000, "Enterprise_No", ExcelTranslator.enterpriseNameFunction},
					{"Message_Type_Code", "消息类型", 4000},
					{"Content", "短信内容", 7000}, {"Is_LMS_Name", "长短信", 2000}, {"Content_Length", "字数", 2000},
					{"Phone_No", "手机号码", 4000},
					{"Real_Name", "提交人", 4000, "Enterprise_User_Id", ExcelTranslator.enterpriseUserRealNameFunction},
					{"Create_Date", "创建时间", 6000}};
		}
		exportAndInsert(exportFile, path, "发送记录", titles, beanList);// 导出数据
	}

	protected void exportReportExcel(String baseExportBasePath, List<Map<String, Object>> beanList,
									 ExportFileExt exportFile, String comeFlag) {
		Object[][] titles = null;
		if (Constant.PLATFORM_FLAG_ADMIN.equals(comeFlag)) {
			titles = new Object[][]{{"Channel_Name", "发送通道", 4000, "Channel_No", ExcelTranslator.channelNameFunction},
					{"Enterprise_Name", "企业名称", 4000, "Enterprise_No", ExcelTranslator.enterpriseNameFunction},
					{"Message_Type_Code", "消息类型", 4000}, {"Phone_No", "手机号码", 4000}, {"Operator", "运营商", 4000},
					{"Sequence", "短信序列", 2000}, {"Submit_Status_Code", "提交状态", 3000}, {"Native_Status", "原生状态", 3000},
					{"Status_Date", "状态时间", 6000}, {"Create_Date", "创建时间", 6000}};
		} else if (Constant.PLATFORM_FLAG_ENTERPRISE.equals(comeFlag) || Constant.PLATFORM_FLAG_AGENT.equals(
				comeFlag)) {
			titles = new Object[][]{{"Enterprise_Name", "企业名称", 4000, "Enterprise_No", ExcelTranslator.enterpriseNameFunction},
					{"Message_Type_Code", "消息类型", 4000},
					{"Phone_No", "手机号码", 4000}, {"Operator", "运营商", 4000}, {"Sequence", "短信序列", 2000},
					{"Submit_Status_Code", "提交状态", 3000}, {"Native_Status", "原生状态", 3000}, {"Status_Date", "状态时间", 6000},
					{"Create_Date", "创建时间", 6000}};
		}
		exportAndInsert(exportFile, baseExportBasePath, "发送回执", titles, beanList);// 导出数据
	}

	protected void exportInputExcel(String baseExportPath, List<Map<String, Object>> beanList, ExportFileExt exportFile) {
		Object[][] titles = new Object[][]{{"Enterprise_Name", "企业名称", 4000, "Enterprise_No", ExcelTranslator.enterpriseNameFunction},
				{"Message_Type_Code", "消息类型", 4000},
				{"Is_LMS_Name", "长短信", 2000}, {"Content", "短信内容", 7000}, {"Phone_Nos", "手机号码", 4000},
				{"Real_Name", "提交人", 4000, "Enterprise_User_Id", ExcelTranslator.enterpriseUserRealNameFunction},
				{"Protocol_Type_Code", "提交方式", 3000}, {"Source_IP", "提交IP", 5000},
				{"Priority_Number", "优先级", 2000}, {"Audit_Status_Code_Name", "审核", 4000}, {"Create_Date", "定时", 6000}};
		exportAndInsert(exportFile, baseExportPath, "等待分拣", titles, beanList);// 导出数据
	}

	protected void exportContactExcel(String baseExportPath, List<Map<String, Object>> beanList,
									  ExportFileExt exportFile) {
		Object[][] titles = new Object[][]{{"Group_Name", "分组", 4000}, {"Phone_No", "手机号码", 4000},
				{"Real_Name", "姓名", 4000}, {"Create_Time", "创建时间", 6000}};
		exportAndInsert(exportFile, baseExportPath, "联系人", titles, beanList);// 导出数据
	}

	/**
	 * 检查余额
	 *
	 * @param user
	 * @author volcano
	 * @date 2019年9月16日上午3:21:44
	 * @version V1.0
	 */
	protected void checkBalance(EnterpriseUser user) {
		BigDecimal availableAmount = user.getAvailable_Amount();// 余额
		if (availableAmount == null) {
			throw new ServiceException(ResultStatus.NO_VAILABLE_SMS_TOTAL);
		}
		// 余额不足,并且等于预付费
		String settlementType = StringUtils.defaultIfBlank(user.getSettlement_Type_Code(),
				SettlementType.PREPAID.toString());
		if (availableAmount.doubleValue() <= 0 && settlementType.equalsIgnoreCase(SettlementType.PREPAID.toString())) {
			throw new ServiceException(ResultStatus.NO_VAILABLE_SMS_TOTAL);
		}
	}

	/**
	 * 查询提交组装 使用的地方：发送回执查询，发送回执导出，批量推送状态，统计查询
	 *
	 * @param condition
	 * @param mark
	 * @return
	 */
	protected ReportExample assemblyReportListConditon(ReportExt condition, boolean mark) {
		ReportExample example = new ReportExample();
		ReportExample.Criteria cri = example.createCriteria();
		if (condition.getBusiness_User_Id() != null) {
			cri.andBusiness_User_IdEqualTo(condition.getBusiness_User_Id());
		}
		if (StringUtils.isNotBlank(condition.getPhone_No())) {
			cri.andPhone_NoEqualTo(condition.getPhone_No());
		}
		if (condition.getMinSubmitDate() == null) {
			condition.setMinSubmitDate(DateTime.getDate(DateTime.getCurrentDayMinDate()));
		}
		if (condition.getMaxSubmitDate() == null) {
			condition.setMaxSubmitDate(DateTime.getDate(DateTime.getCurrentDayMaxDate()));
		}
		cri.andSubmit_DateGreaterThanOrEqualTo(condition.getMinSubmitDate());
		if (mark) {
			cri.andSubmit_DateLessThanOrEqualTo(condition.getMaxSubmitDate());
		} else {
			cri.andSubmit_DateLessThan(condition.getMaxSubmitDate());
		}

		if (StringUtils.isNotBlank(condition.getMsg_Batch_No())) {
			cri.andMsg_Batch_NoEqualTo(condition.getMsg_Batch_No());
		}
		if (StringUtils.isNotBlank(condition.getMessage_Type_Code())) {
			cri.andMessage_Type_CodeEqualTo(condition.getMessage_Type_Code());
		}
		if (StringUtils.isNotBlank(condition.getAgent_No())) {
			cri.andAgent_NoEqualTo(condition.getAgent_No());
		}
		if (StringUtils.isNotBlank(condition.getContent())) {
			cri.andContentLike("%" + condition.getContent() + "%");
		}
		if (StringUtils.isNotBlank(condition.getEnterprise_No())) {
			cri.andEnterprise_NoEqualTo(condition.getEnterprise_No());
		}
		if (StringUtils.isNotBlank(condition.getChannel_No())) {
			cri.andChannel_NoEqualTo(condition.getChannel_No());
		}
		if (condition.getEnterprise_User_Id() != null) {
			cri.andEnterprise_User_IdEqualTo(condition.getEnterprise_User_Id());
		}
		if (StringUtils.isNotBlank(condition.getCountry_Number())) {
			cri.andCountry_NumberEqualTo(condition.getCountry_Number());
		}
		if (StringUtils.isNotBlank(condition.getOperator())) {
			cri.andOperatorEqualTo(condition.getOperator());
		}
		if (StringUtils.isNotBlank(condition.getSubmit_Status_Code())) {
			cri.andSubmit_Status_CodeEqualTo(condition.getSubmit_Status_Code());
		}
		if (StringUtils.isNotBlank(condition.getChannel_Msg_Id())) {
			cri.andChannel_Msg_IdEqualTo(condition.getChannel_Msg_Id());
		}
		if (StringUtils.isNotBlank(condition.getStatus_Code())) {
			cri.andStatus_CodeEqualTo(condition.getStatus_Code());
		}
		if (StringUtils.isNotBlank(condition.getNative_Status())) {
			cri.andNative_StatusEqualTo(condition.getNative_Status());
		}
		if (StringUtils.isNotBlank(condition.getSignature())) {
			cri.andSignatureEqualTo(condition.getSignature());
		}
		if (StringUtils.isNotBlank(condition.getProvince_Code())) {
			cri.andProvince_CodeEqualTo(condition.getProvince_Code());
		}
		//代理商和企业端不展示第一次重发失败的数据
		if (SystemType.AGENT.equals(DatabaseCache.getSystemType()) || SystemType.ENTERPRISE.equals(DatabaseCache.getSystemType())) {
			cri.andData_Status_CodeNotEqualTo(DataStatus.DELETE.toString());
		}
		if (StringUtils.isNotBlank(condition.getData_Status_Code())) {
			cri.andData_Status_CodeEqualTo(condition.getData_Status_Code());
		}
		if (StringUtils.isNotBlank(condition.getSubmit_Description())) {
			cri.andSubmit_DescriptionEqualTo(condition.getSubmit_Description());
		}
		if (condition.getId() != null) {
			cri.andIdEqualTo(condition.getId());
		}
		return example;
	}

	protected List<ReportExample> assemblyReportListShardingCondition(ReportExt condition) {
		Date minSubmitDate = condition.getMinSubmitDate();
		Date maxSubmitDate = condition.getMaxSubmitDate();
		if (minSubmitDate == null) {
			minSubmitDate = DateTime.getDate(DateTime.getCurrentDayMinDate());
		}
		if (maxSubmitDate == null) {
			maxSubmitDate = DateTime.getDate(DateTime.getCurrentDayMaxDate());
		}
		List<ReportExample> result = new ArrayList<>();
		List<DateTime.DateInterVal> dayInterval = DateTime.getDayIntervalDate(minSubmitDate, maxSubmitDate);
		dayInterval.stream().forEach(interVal -> {
			condition.setMinSubmitDate(interVal.getBegin());
			condition.setMaxSubmitDate(interVal.getEnd());
			ReportExample reportExample = assemblyReportListConditon(condition, true);
			reportExample.setOrderByClause(" id desc");
			result.add(reportExample);
		});
		return result;
	}

	protected ReportNotifyExample assemblyReportNnotifyListConditon(ReportNotifyExt condition) {
		ReportNotifyExample example = new ReportNotifyExample();
		ReportNotifyExample.Criteria cri = example.createCriteria();
		if (StringUtils.isNotBlank(condition.getPhone_No())) {
			cri.andPhone_NoEqualTo(condition.getPhone_No());
		}
		if (condition.getMinSubmitDate() == null) {
			condition.setMinSubmitDate(DateTime.getDate(DateTime.getCurrentDayMinDate()));
		}
		if (condition.getMinSubmitDate() == null) {
			condition.setMaxSubmitDate(DateTime.getDate(DateTime.getCurrentDayMaxDate()));
		}
		cri.andSubmit_DateGreaterThanOrEqualTo(condition.getMinSubmitDate());
		cri.andSubmit_DateLessThanOrEqualTo(condition.getMaxSubmitDate());
		if (StringUtils.isNotBlank(condition.getMsg_Batch_No())) {
			cri.andMsg_Batch_NoEqualTo(condition.getMsg_Batch_No());
		}
		if (StringUtils.isNotBlank(condition.getAgent_No())) {
			cri.andAgent_NoEqualTo(condition.getAgent_No());
		}
		if (StringUtils.isNotBlank(condition.getEnterprise_No())) {
			cri.andEnterprise_NoEqualTo(condition.getEnterprise_No());
		}
		if (StringUtils.isNotBlank(condition.getChannel_No())) {
			cri.andChannel_NoEqualTo(condition.getChannel_No());
		}
		if (condition.getEnterprise_User_Id() != null) {
			cri.andEnterprise_User_IdEqualTo(condition.getEnterprise_User_Id());
		}
		if (condition.getEnterprise_Msg_Id() != null) {
			cri.andEnterprise_Msg_IdEqualTo(condition.getEnterprise_Msg_Id());
		}
		if (StringUtils.isNotBlank(condition.getCountry_Number())) {
			cri.andCountry_NumberEqualTo(condition.getCountry_Number());
		}
		if (StringUtils.isNotBlank(condition.getOperator())) {
			cri.andOperatorEqualTo(condition.getOperator());
		}
		if (StringUtils.isNotBlank(condition.getSubmit_Status_Code())) {
			cri.andSubmit_Status_CodeEqualTo(condition.getSubmit_Status_Code());
		}
		if (StringUtils.isNotBlank(condition.getChannel_Msg_Id())) {
			cri.andChannel_Msg_IdEqualTo(condition.getChannel_Msg_Id());
		}
		if (StringUtils.isNotBlank(condition.getStatus_Code())) {
			cri.andStatus_CodeEqualTo(condition.getStatus_Code());
		}
		if (StringUtils.isNotBlank(condition.getNative_Status())) {
			cri.andNative_StatusEqualTo(condition.getNative_Status());
		}
		if (StringUtils.isNotEmpty(condition.getNotify_Status_Code())){
			cri.andNotify_Status_CodeEqualTo(condition.getNotify_Status_Code());
		}
		if (!StringUtils.isEmpty(condition.getMessage_Type_Code())) {
			cri.andMessage_Type_CodeEqualTo(condition.getMessage_Type_Code());
		}
		if (StringUtils.isNotBlank(condition.getContent())) {
			cri.andContentLike("%" + condition.getContent() + "%");
		}
		return example;
	}

	protected List<ReportNotifyExample> assemblyReportNotifyListShardingCondition(ReportNotifyExt condition) {
		Date minSubmitDate = condition.getMinSubmitDate();
		Date maxSubmitDate = condition.getMaxSubmitDate();
		if (minSubmitDate == null) {
			minSubmitDate = DateTime.getDate(DateTime.getCurrentDayMinDate());
		}
		if (maxSubmitDate == null) {
			maxSubmitDate = DateTime.getDate(DateTime.getCurrentDayMaxDate());
		}
		List<ReportNotifyExample> result = new ArrayList<>();
		List<DateTime.DateInterVal> dayInterval = DateTime.getDayIntervalDate(minSubmitDate, maxSubmitDate);
		dayInterval.stream().forEach(interVal -> {
			condition.setMinSubmitDate(interVal.getBegin());
			condition.setMaxSubmitDate(interVal.getEnd());
			ReportNotifyExample reportNotifyExample = assemblyReportNnotifyListConditon(condition);
			reportNotifyExample.setOrderByClause(" id desc");
			result.add(reportNotifyExample);
		});
		return result;
	}

	protected List<Map<String, Object>> queryContactListForExportPage(ContactExt condition) {
		ContactExample example = getContactExample(condition);
		if (example == null) {
			return null;
		}
		example.setPagination(condition.getPagination());
		return this.contactExtDAO.queryContactListForExportPage(example);
	}

	/**
	 * 组装查询submit的查询条件
	 *
	 * @param condition
	 * @param mark      处理Create_Date字段的方式   true:between...and...  false:>= and <
	 * @return
	 */
	protected SubmitExample assemblySubmitListConditon(SubmitExt condition, boolean mark) {
		SubmitExample example = new SubmitExample();
		SubmitExample.Criteria cri = example.createCriteria();
		if (condition.getBusiness_User_Id() != null) {
			cri.andBusiness_User_IdEqualTo(condition.getBusiness_User_Id());
		}
		if (StringUtils.isNotBlank(condition.getPhone_No())) {
			cri.andPhone_NoEqualTo(condition.getPhone_No());
		}
		if (condition.getMinSubmitDate() == null) {
			condition.setMinSubmitDate(DateTime.getDate(DateTime.getCurrentDayMinDate()));
		}
		if (condition.getMaxSubmitDate() == null) {
			condition.setMaxSubmitDate(DateTime.getDate(DateTime.getCurrentDayMaxDate()));
		}
		if (mark) {
			cri.andSubmit_DateBetween(condition.getMinSubmitDate(), condition.getMaxSubmitDate());
		} else {
			cri.andSubmit_DateGreaterThanOrEqualTo(condition.getMinSubmitDate());
			cri.andSubmit_DateLessThan(condition.getMaxSubmitDate());
		}
		if (StringUtils.isNotBlank(condition.getEnterprise_No())) {
			cri.andEnterprise_NoEqualTo(condition.getEnterprise_No());
		}
		if (condition.getEnterprise_User_Id() != null) {
			cri.andEnterprise_User_IdEqualTo(condition.getEnterprise_User_Id());
		}
		if (StringUtils.isNotBlank(condition.getChannel_No())) {
			cri.andChannel_NoEqualTo(condition.getChannel_No());
		}
		if (!StringUtils.isEmpty(condition.getContent())) {
			cri.andContentLike("%" + condition.getContent() + "%");
		}
		if (StringUtils.isNotBlank(condition.getMsg_Batch_No())) {
			cri.andMsg_Batch_NoEqualTo(condition.getMsg_Batch_No());
		}
		if (StringUtils.isNotBlank(condition.getCountry_Number())) {
			cri.andCountry_NumberEqualTo(condition.getCountry_Number());
		}
		if (StringUtils.isNotBlank(condition.getOperator())) {
			cri.andOperatorEqualTo(condition.getOperator());
		}
		if (StringUtils.isNotBlank(condition.getSubmit_Status_Code())) {
			cri.andSubmit_Status_CodeEqualTo(condition.getSubmit_Status_Code());
		}
		if (StringUtils.isNotBlank(condition.getAgent_No())) {
			cri.andAgent_NoEqualTo(condition.getAgent_No());
		}
		if (StringUtils.isNotBlank(condition.getMessage_Type_Code())) {
			cri.andMessage_Type_CodeEqualTo(condition.getMessage_Type_Code());
		}
		if (StringUtils.isNotBlank(condition.getType_Code())) {
			cri.andChannel_NoLike(condition.getType_Code() + "%");
		}
		if (StringUtils.isNotBlank(condition.getChannel_Msg_Id())) {
			cri.andChannel_Msg_IdEqualTo(condition.getChannel_Msg_Id());
		}
		if (StringUtils.isNotBlank(condition.getSignature())) {
			cri.andSignatureEqualTo(condition.getSignature());
		}
		if (StringUtils.isNotBlank(condition.getProvince_Code())) {
			cri.andProvince_CodeEqualTo(condition.getProvince_Code());
		}
		//代理商和企业端不展示第一次重发失败的数据
		if (SystemType.AGENT.equals(DatabaseCache.getSystemType()) || SystemType.ENTERPRISE.equals(DatabaseCache.getSystemType())) {
			cri.andData_Status_CodeNotEqualTo(DataStatus.DELETE.toString());
		}
		if (StringUtils.isNotBlank(condition.getData_Status_Code())) {
			cri.andData_Status_CodeEqualTo(condition.getData_Status_Code());
		}
		if (StringUtils.isNotBlank(condition.getSubmit_Description())) {
			cri.andSubmit_DescriptionEqualTo(condition.getSubmit_Description());
		}
		if(StringUtils.isNotBlank(condition.getGroup_Code())){
			cri.andGroup_CodeEqualTo(condition.getGroup_Code());
		}
		if(condition.getIs_Deduct() != null) {
			cri.andIs_DeductEqualTo(condition.getIs_Deduct());
		}
		return example;
	}

	protected List<SubmitExample> assemblySubmitListShardingCondition(SubmitExt condition) {
		Date minSubmitDate = condition.getMinSubmitDate();
		Date maxSubmitDate = condition.getMaxSubmitDate();
		if (minSubmitDate == null) {
			minSubmitDate = DateTime.getDate(DateTime.getCurrentDayMinDate());
		}
		if (maxSubmitDate == null) {
			maxSubmitDate = DateTime.getDate(DateTime.getCurrentDayMaxDate());
		}
		List<SubmitExample> result = new ArrayList<>();
		List<DateTime.DateInterVal> dayInterval = DateTime.getDayIntervalDate(minSubmitDate, maxSubmitDate);
		dayInterval.stream().forEach(interVal -> {
			condition.setMinSubmitDate(interVal.getBegin());
			condition.setMaxSubmitDate(interVal.getEnd());
			SubmitExample submitExample = assemblySubmitListConditon(condition, true);
			submitExample.setOrderByClause(" id desc");
			result.add(submitExample);
		});
		return result;
	}

	protected InputExample assemblyInputListConditon(InputExt condition) {
		InputExample example = new InputExample();
		InputExample.Criteria criteria = example.createCriteria();
		if (null != condition.getId()) {
			criteria.andIdEqualTo(condition.getId());
		}
		if (StringUtils.isNotEmpty(condition.getAssist_Audit_Key())) {
			criteria.andAssist_Audit_KeyEqualTo(condition.getAssist_Audit_Key());
		}
		if (StringUtils.isNotEmpty(condition.getMsg_Batch_No())) {
			criteria.andMsg_Batch_NoEqualTo(condition.getMsg_Batch_No());
		}
		if (StringUtils.isNotEmpty(condition.getEnterprise_No())) {
			criteria.andEnterprise_NoEqualTo(condition.getEnterprise_No());
		}
		if (StringUtils.isNotEmpty(condition.getAgent_No())) {
			criteria.andAgent_NoEqualTo(condition.getAgent_No());
		}
		if (null != condition.getBusiness_User_Id()) {
			criteria.andBusiness_User_IdEqualTo(condition.getBusiness_User_Id());
		}
		if (null != condition.getEnterprise_User_Id()) {
			criteria.andEnterprise_User_IdEqualTo(condition.getEnterprise_User_Id());
		}
		if (StringUtils.isNotEmpty(condition.getProduct_No())) {
			criteria.andProduct_NoEqualTo(condition.getProduct_No());
		}
		if (StringUtils.isNotEmpty(condition.getMessage_Type_Code())) {
			criteria.andMessage_Type_CodeEqualTo(condition.getMessage_Type_Code());
		}
		if (!StringUtils.isEmpty(condition.getPhone_Nos())) {
			criteria.andPhone_NosLike("%" + condition.getPhone_Nos() + "%");
		}
		if (!StringUtils.isEmpty(condition.getContent())) {
			criteria.andContentEqualTo(condition.getContent());
		}
		if (StringUtils.isNotEmpty(condition.getProtocol_Type_Code())) {
			criteria.andProtocol_Type_CodeEqualTo(condition.getProtocol_Type_Code());
		}
		if (StringUtils.isNotEmpty(condition.getSource_IP())) {
			criteria.andSource_IPEqualTo(condition.getSource_IP());
		}
		if (null != condition.getPriority_Number()) {
			criteria.andPriority_NumberEqualTo(condition.getPriority_Number());
		}
		if (StringUtils.isNotEmpty(condition.getAudit_Status_Code())) {
			criteria.andAudit_Status_CodeEqualTo(condition.getAudit_Status_Code());
		}
		if (StringUtils.isNotEmpty(condition.getSub_Code())) {
			criteria.andSub_CodeEqualTo(condition.getSub_Code());
		}
		if (StringUtils.isNotEmpty(condition.getDescription())) {
			criteria.andDescriptionEqualTo(condition.getDescription());
		}
		if (StringUtils.isNotEmpty(condition.getRemark())) {
			criteria.andRemarkEqualTo(condition.getRemark());
		}
		if ("wait_Sort".equals(condition.getFlag())) {
			criteria.andSend_TimeIsNull();
		}
		return example;
	}

	protected InputLogExample assemblyInputLogListConditon(InputLogExt condition) {
		InputLogExample example = new InputLogExample();
		InputLogExample.Criteria cri = example.createCriteria();
		final Date date = new Date();
		if (condition.getMinCreateDate() == null) {
			condition.setMinCreateDate(DateTime.getStartOfDay(date));
		}
		if (condition.getBusiness_User_Id() != null) {
			cri.andBusiness_User_IdEqualTo(condition.getBusiness_User_Id());
		}

		cri.andCreate_DateGreaterThanOrEqualTo(condition.getMinCreateDate());
		if (condition.getMaxCreateDate() == null) {
			condition.setMaxCreateDate(DateTime.getEndOfDay(date));
		}
		cri.andCreate_DateLessThanOrEqualTo(condition.getMaxCreateDate());
		if (StringUtils.isNotBlank(condition.getMsg_Batch_No())) {
			cri.andMsg_Batch_NoEqualTo(condition.getMsg_Batch_No());
		}
		if (StringUtils.isNotBlank(condition.getEnterprise_No())) {
			cri.andEnterprise_NoEqualTo(condition.getEnterprise_No());
		}
		if (StringUtils.isNotBlank(condition.getMessage_Type_Code())) {
			cri.andMessage_Type_CodeEqualTo(condition.getMessage_Type_Code());
		}
		if (condition.getEnterprise_User_Id() != null) {
			cri.andEnterprise_User_IdEqualTo(condition.getEnterprise_User_Id());
		}
		if (condition.getId() != null) {
			cri.andIdEqualTo(condition.getId());
		}

		if (StringUtils.isNotBlank(condition.getPhone_Nos())) {
			cri.andPhone_NosLike("%" + condition.getPhone_Nos() + "%");
		}
		if (StringUtils.isNotBlank(condition.getAgent_No())) {
			cri.andAgent_NoEqualTo(condition.getAgent_No());
		}
		if (StringUtils.isNotBlank(condition.getContent())) {
			cri.andContentLike("%" + condition.getContent() + "%");
		}
		//代理商和企业端不展示第一次重发失败的数据
		if (SystemType.AGENT.equals(DatabaseCache.getSystemType()) || SystemType.ENTERPRISE.equals(DatabaseCache.getSystemType())) {
			cri.andData_Status_CodeNotEqualTo(DataStatus.DELETE.toString());
		}
		if (StringUtils.isNotBlank(condition.getData_Status_Code())) {
			cri.andData_Status_CodeEqualTo(condition.getData_Status_Code());
		}
		return example;
	}

	protected List<InputLogExample> assemblyInputLogListShardingCondition(InputLogExt condition) {
		Date minCreateDate = condition.getMinCreateDate();
		Date maxCreateDate = condition.getMaxCreateDate();
		Date now = new Date();
		if (minCreateDate == null) {
			minCreateDate = DateTime.getStartOfDay(now);
		}
		if (maxCreateDate == null) {
			maxCreateDate = DateTime.getEndOfDay(now);
		}
		List<InputLogExample> result = new ArrayList<>();
		List<DateTime.DateInterVal> dayInterval = DateTime.getDayIntervalDate(minCreateDate, maxCreateDate);
		dayInterval.stream().forEach(interval -> {
			condition.setMinCreateDate(interval.getBegin());
			condition.setMaxCreateDate(interval.getEnd());
			InputLogExample inputLogExample = assemblyInputLogListConditon(condition);
			inputLogExample.setOrderByClause(" id desc");
			result.add(inputLogExample);
		});
		return result;
	}

	protected InboxExample assemblyInboxListConditon(InboxExt inbox) {
		InboxExample example = new InboxExample();
		InboxExample.Criteria cri = example.createCriteria();
		String minSubmitDate = inbox.getMinSubmitDate();// 最小提交时间
		String maxSubmitDate = inbox.getMaxSubmitDate();// 最大提交时间
		// 初始化查询日期默认值
		if (StringUtils.isEmpty(minSubmitDate)) {
			minSubmitDate = DateTime.getCurrentDayMinDate();
		}
		cri.andCreate_DateGreaterThanOrEqualTo(DateTime.getDate(minSubmitDate));
		if (StringUtils.isEmpty(maxSubmitDate)) {
			maxSubmitDate = DateTime.getCurrentDayMaxDate();
		}
		cri.andCreate_DateLessThanOrEqualTo(DateTime.getDate(maxSubmitDate));
		if (inbox.getId() != null) {
			cri.andIdEqualTo(inbox.getId());
		}
		if (!StringUtils.isEmpty(inbox.getSP_Number())) {
			cri.andSP_NumberLike("%" + inbox.getSP_Number());
		}
		if (!StringUtils.isEmpty(inbox.getAgent_No())) {
			cri.andAgent_NoEqualTo(inbox.getAgent_No());
		}
		if (!StringUtils.isEmpty(inbox.getEnterprise_No())) {
			cri.andEnterprise_NoEqualTo(inbox.getEnterprise_No());
		}
		if (inbox.getEnterprise_User_Id() != null) {
			cri.andEnterprise_User_IdEqualTo(inbox.getEnterprise_User_Id());
		}
		if (!StringUtils.isEmpty(inbox.getPhone_No())) {
			cri.andPhone_NoEqualTo(inbox.getPhone_No());
		}
		if (!StringUtils.isEmpty(inbox.getContent())) {
			cri.andContentEqualTo(inbox.getContent());
		}
		return example;
	}

	/**
	 * 审核通过 循环查询到的inputList 如果是定时短信，添加修改id到updateList，修改审核状态为审核通过状态 如果不是定时短信，添加删除id到delList，发送MQ分拣通知 返回delList
	 *
	 * @param inputList
	 * @param delList
	 * @param auditInputExt
	 * @return
	 */
	private List<Long> doPassed(List<Input> inputList, List<Long> delList, InputExt auditInputExt) {
		List<Long> timerList = new ArrayList<Long>();
		List<Long> updateDateList = new ArrayList<>();
		inputList.forEach(item -> {
			if (ObjectUtils.isEmpty(item.getSend_Time())) {
				updateDateList.add(item.getId());
			} else {// 定时短信，修改审核状态
				timerList.add(item.getId());
			}
		});
		Input input = new Input();
		input.setAudit_Admin_User_Id(auditInputExt.getAudit_Admin_User_Id());
		input.setAudit_Date(auditInputExt.getAudit_Date());
		input.setAudit_Status_Code(ContentAuditStatus.PASSED.toString());
		//定时短信
		if (timerList.size() > 0) {
			InputExample updateExample = new InputExample();
			updateExample.createCriteria().andIdIn(timerList);
			this.inputDAO.updateByExampleSelective(input, updateExample);
		}
		//普通审核短信
		if (updateDateList.size() > 0) {
			//设置发送时间为当前时间
			input.setSend_Time(new Date());
			InputExample updateExample = new InputExample();
			updateExample.createCriteria().andIdIn(updateDateList);
			this.inputDAO.updateByExampleSelective(input, updateExample);
		}
		return delList;
	}

	/**
	 * 审核拒绝 循环查询到的inputList 将input表中的数据添加到insertInputLogList中，批量保存到inputLog中 将要删除的数据id添加到delList中，返回delList
	 * <p>
	 * 2020年2月20日17:05:39 改成异步保存inputlog
	 *
	 * @param inputList
	 * @param delList
	 * @param auditInputExt
	 * @return
	 */
	private List<Long> doRejected(List<Input> inputList, List<Long> delList, InputExt auditInputExt) {
		inputList.forEach(item -> {
			item.setAudit_Admin_User_Id(auditInputExt.getAudit_Admin_User_Id());
			item.setAudit_Date(auditInputExt.getAudit_Date());
			delList.add(item.getId());
			InputLog insertInputLog = convertToInputLog(item);
			insertInputLog.setAudit_Status_Code(ContentAuditStatus.REJECT.toString());
			QueueUtil.notifySaveInputLog(insertInputLog);
		});
		// 审核拒绝的通知下游
		String description = "驳回，审核拒绝";
		rejectNotifyReport(inputList, description);
		return delList;
	}

	private void rejectNotifyReport(List<Input> inputList, String description) {
		inputList.forEach(item -> {
			rejectedFixedPool.execute(new Runnable() {
				@Override
				public void run() {
					try {
						String[] phoneNos = item.getPhone_Nos().split(",");
						String enterpriseMsgId = item.getEnterprise_Msg_Id();
						String[] msgIds = null;
						if (StringUtils.isNotEmpty(enterpriseMsgId)) {
							msgIds = enterpriseMsgId.split(",");
						}

						List<String> listPhoneNos = Arrays.asList(phoneNos);
						String[] finalMsgIds = msgIds;
						listPhoneNos.forEach(phone -> {
							String[] contents = SMSUtil.splitContent(item.getContent());
							for (int i = 0; i < contents.length; i++) {
								String content = contents[i];

								Submit submit = new Submit();
								CopyUtil.INPUT_SUBMIT_COPIER.copy(item, submit, null);
								submit.setId(null);
								submit.setEnterprise_Msg_Id(null);
								submit.setChannel_No(new NullChannel().getNo());
								submit.setContent_Length(content.length());
								submit.setContent(content);
								submit.setSequence(i + 1);
								submit.setPhone_No(phone);
								submit = SMSUtil.buildSubmitAreaAndOperator(submit);
								submit.setSubmit_Date(new Date());
								submit.setSubmit_Status_Code(SubmitStatus.SORT_FAILD.toString());
								submit.setSubmit_Description(StringUtils.defaultIfBlank(description, ReportStatus.REJECTD.toString()));
								submit.setChannel_Msg_Id(CodeUtil.buildMsgNo());
								submit.setSubmit_Date(new Date());
								submit.setCreate_Date(new Date());
								if (finalMsgIds != null && i < finalMsgIds.length) {
									submit.setEnterprise_Msg_Id(finalMsgIds[i]);
								}

								QueueUtil.saveSubmit(submit);

								Report report = SMSUtil.buildReport(submit, phone);
								report.setNative_Status(ReportStatus.REJECTD.toString().toUpperCase());
								report.setStatus_Code(ReportStatus.FAILD.toString());
								report.setStatus_Date(new Date());
								QueueUtil.saveReport(report);
							}
						});
					} catch (Exception e) {
						SuperLogger.error(e.getMessage(), e);
					}
				}
			});

		});
	}

	protected void exportSubmitReportUnknownListExcel(String path, List<Map<String, Object>> dataList,
			ExportFileExt exportFile, String comeFlag) {

		Object[][] titles = null;
		if (Constant.PLATFORM_FLAG_ADMIN.equals(comeFlag)) {
			titles = new Object[][]{{"Channel_Name", "发送通道", 4000, "Channel_No", ExcelTranslator.channelNameFunction},
					{"Enterprise_Name", "企业名称", 4000, "Enterprise_No", ExcelTranslator.enterpriseNameFunction},
					{"Message_Type_Code", "消息类型", 4000}, {"Is_LMS_Name", "长短信", 2000}, {"Content", "短信内容", 7000},
					{"Content_Length", "字数", 2000}, {"Phone_Nos", "手机号码", 4000},
					{"Real_Name", "提交人", 4000, "Enterprise_User_Id", ExcelTranslator.enterpriseUserRealNameFunction},
					{"Enterprise_User_Taxes", "发票成本", 3000}, {"Channel_Taxes", "发票抵消", 4000},
					{"Channel_Unit_Price", "成本", 4000}, {"Profits", "利润", 4000}, {"Create_Date", "创建时间", 6000}};
		} else if (Constant.PLATFORM_FLAG_ENTERPRISE.equals(comeFlag) || Constant.PLATFORM_FLAG_AGENT.equals(
				comeFlag)) {
			titles = new Object[][]{{"Enterprise_Name", "企业名称", 4000, "Enterprise_No", ExcelTranslator.enterpriseNameFunction},
					{"Message_Type_Code", "消息类型", 4000},
					{"Is_LMS_Name", "长短信", 2000}, {"Content", "短信内容", 7000}, {"Content_Length", "字数", 2000},
					{"Phone_Nos", "手机号码", 4000},
					{"Real_Name", "提交人", 4000, "Enterprise_User_Id", ExcelTranslator.enterpriseUserRealNameFunction},
					{"Create_Date", "创建时间", 6000}};
		}
		exportAndInsert(exportFile, path, "未知记录", titles, dataList);// 导出数据
	}



	protected SmsTemplateExample exportSmsTemplateList(SmsTemplate condition) {
		SmsTemplateExample example = new SmsTemplateExample();
		SmsTemplateExample.Criteria cri = example.createCriteria();
		if (StringUtils.isNotBlank(condition.getEnterprise_No())) {
			cri.andEnterprise_NoEqualTo(condition.getEnterprise_No());
		}
		if (condition.getEnterprise_User_Id() != null) {
			cri.andEnterprise_User_IdEqualTo(condition.getEnterprise_User_Id());
		}
		if (StringUtils.isNotBlank(condition.getApprove_Status())) {
			cri.andApprove_StatusEqualTo(condition.getApprove_Status());
		}
		if (StringUtils.isNotBlank(condition.getDescription())) {
			cri.andDescriptionEqualTo(condition.getDescription());
		}
		if (StringUtils.isNotBlank(condition.getTemplate_Content())) {
			cri.andTemplate_ContentEqualTo(condition.getTemplate_Content());
		}

		if (StringUtils.isNotBlank(condition.getTemplate_Name())) {
			cri.andTemplate_NameEqualTo(condition.getTemplate_Name());
		}
		if (StringUtils.isNotBlank(condition.getTemplate_Type())) {
			cri.andTemplate_TypeEqualTo(condition.getTemplate_Type());
		}
		if (condition.getCreate_Date() != null) {
			cri.andCreate_DateEqualTo(condition.getCreate_Date());
		}
		return example;
	}

	protected void exportSmsTemplateExcel(String baseExportBasePath, List<Map<String, Object>> beanList,
			ExportFileExt exportFile) {
		Object[][] titles = new Object[][]{{"Template_Name", "模板名称", 4000}, {"Template_Content", "模板内容", 6000},
				{"Template_Type", "行业类型", 3000}, {"Approve_Status", "审核状态", 2000}, {"Description", "描述", 4000},
				{"Create_Date", "创建时间", 6000}};

		exportAndInsert(exportFile, baseExportBasePath, "短信模板", titles, beanList);// 导出数据

	}

	protected void checkTimes(SubmitExt submitExt) {
		Date minDate = submitExt.getMinSubmitDate();
		Date maxDate = submitExt.getMaxSubmitDate();

		if (minDate == null) {
			minDate = DateTime.getTheHourAfterMinHour(-1);
			submitExt.setMinSubmitDate(minDate);
		}
		if (maxDate == null) {
			maxDate = DateTime.getTheHourAfterMaxHour(-1);
			submitExt.setMaxSubmitDate(maxDate);
		}
		if (!DateUtils.isSameDay(minDate, maxDate)) {
			throw new ServiceException("不可以跨天查询！");
		}
		int hours = DateTime.hoursBetween(minDate, maxDate) + 1;
		int setHours = DatabaseCache.getIntValueBySortCodeAndCode("sys_performance_setup",
				"send_report_unknown_max_times", 1);
		if (hours > setHours) {
			throw new ServiceException("未知记录查询间隔最大为 " + setHours + " 小时！");
		}
	}

	protected List<String> addFileMobiles(List<String> mobiles, String filePath) throws IOException {
		BufferedReader reader = null;
		InputStreamReader isr = null;
		FileInputStream fis = new FileInputStream(filePath);
		try {
			isr = new InputStreamReader(fis);
			reader = new BufferedReader(isr);
			String filePhoneNos = "";
			while ((filePhoneNos = reader.readLine()) != null) {
				mobiles.addAll(Arrays.asList(filePhoneNos.split(Constant.MUTL_MOBILE_SPLIT)));
			}
		} catch (Exception e) {
			SuperLogger.error(e.getMessage(), e);
		} finally {
			try {
				fis.close();
				isr.close();
				reader.close();
			} catch (IOException e) {
				SuperLogger.error(e.getMessage(), e);
			}
		}
		return mobiles;
	}

	public class AuditSMS {

		private List<InputExt> smsList;

		private String status;

		private BaseSendManage baseSendManage;

		public AuditSMS(List<InputExt> smsList, String status, BaseSendManage baseSendManage) {
			this.smsList = smsList;
			this.status = status;
			this.baseSendManage = baseSendManage;
		}

		public List<InputExt> getSmsList() {
			return smsList;
		}

		public void setSmsList(List<InputExt> smsList) {
			this.smsList = smsList;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public BaseSendManage getBaseSendManage() {
			return baseSendManage;
		}

		public void setBaseSendManage(BaseSendManage baseSendManage) {
			this.baseSendManage = baseSendManage;
		}
	}

	/**
	 *
	 * @param groupTag : 页面分类tag：sender、user、other等
	 * @param topicName
	 * @return
	 */
	private boolean judgeGroupTag(String groupTag,String topicName) {
		if(StringUtils.isEmpty(topicName)){
			return false;
		}
		if(StringUtils.isEmpty(groupTag)){
			return true;//未配置groupTag，直接为true
		}
		boolean result ;
		switch (groupTag){
			case "send"://发送队列
				result = isSendQueue(topicName);
				break;
			case "user"://用户队列
				result = isUserQueue(topicName);
				break;
			default://剩下的都是其他队列
				result = isOtherQueue(topicName);
				break;
		}
		return result;
	}

	/**
	 * 是否是其他队列
	 * @param topicName
	 * @return
	 */
	private boolean isOtherQueue(String topicName) {
		return !(isUserQueue(topicName)||isSendQueue(topicName));
	}

	/**
	 * 是否是用户队列
	 * @param topicName
	 * @return
	 */
	private boolean isUserQueue(String topicName) {
		if(topicName.startsWith("cmpp_notify_")
				|| topicName.startsWith("smpp_notify_")
				|| topicName.startsWith("smgp_notify_")
				|| topicName.startsWith("sgip_notify_")
				|| topicName.startsWith("http_notify_")
		){
			return true;
		}
		return false;
	}

	/**
	 * 是否是发送队列
	 * @param topicName
	 * @return
	 */
	private boolean isSendQueue(String topicName) {
		if(topicName.startsWith("submit_topic_")
				|| topicName.startsWith("report_topic_")
				|| topicName.startsWith("sorter_topic_")
				|| topicName.startsWith("sorter_resend_")
				|| topicName.startsWith("save_")
		){
			return true;
		}
		return false;
	}

	// 利用我们的命名规则  得到group 查询对应的group  topic中的数据，在命名规则修改的时候  这里要做相应的修改  回头再完善这个查询方法
	protected List<QueueDetail> queryDataByTopic(String topicName, boolean isAdmin) {
		return null;
	}

	/**
	 * 获取轮询到的手机号索引
	 * @param phoneNoArray
	 * @param index
	 * @return
	 */
	protected int getPhoneNoIndex(String[] phoneNoArray, int index) {
		int maxIndex = phoneNoArray.length - 1;
		if (maxIndex <= 0 || maxIndex < index) {
			return 0;
		}
		return index;
	}

	/**
	 * 收件箱导出内容
	 * @param condition
	 * @return
	 */
	protected List<Map<String, Object>> queryInboxListForExportPage(InboxExt condition) {
		InboxExample example = assemblyInboxListConditon(condition);
		example.setPagination(condition.getPagination());
		return this.inboxExtDAO.queryInboxListForExportPage(example);
	}

	/**
	 * 导出收件箱
	 * @param path
	 * @param dataList
	 * @param exportFile
	 * @param flag
	 */
	protected void exportInboxListExcel(String path, List<Map<String, Object>> dataList, ExportFileExt exportFile,String flag) {
		Object[][] titles = null;
		if (Constant.PLATFORM_FLAG_ADMIN.equals(flag)) {
			titles = new Object[][]{{"Channel_Name", "发送通道", 4000, "Channel_No", ExcelTranslator.channelNameFunction},
					{"Enterprise_Name", "企业名称", 4000, "Enterprise_No", ExcelTranslator.enterpriseNameFunction},
					{"Real_Name", "用户名称", 4000, "Enterprise_User_Id", ExcelTranslator.enterpriseUserRealNameFunction},
					{"Phone_No", "手机号码", 4000}, {"SP_Number", "上行码号", 4000},
					{"Content", "上行内容", 4000}, {"Input_Content", "下行内容", 4000}, {"Create_Date", "上行时间", 6000}};
		} else if (Constant.PLATFORM_FLAG_ENTERPRISE.equals(flag) || Constant.PLATFORM_FLAG_AGENT.equals(
				flag)) {
			titles = new Object[][]{{"Enterprise_Name", "企业名称", 4000, "Enterprise_No", ExcelTranslator.enterpriseNameFunction},
					{"Real_Name", "用户名称", 4000, "Enterprise_User_Id", ExcelTranslator.enterpriseUserRealNameFunction},
					{"Phone_No", "手机号码", 4000}, {"SP_Number", "上行码号", 4000},
					{"Content", "上行内容", 7000}, {"Input_Content", "下行内容", 7000}, {"Create_Date", "上行时间", 6000}};
		}
		exportAndInsert(exportFile, path, "收件箱", titles, dataList);// 导出数据
	}

    protected void executeSMS(Map<String, String> params, List<Input> inputList) {
        ExecutorService pool = Executors.newFixedThreadPool(1);
        try {
            String url = getNetWayHttpSubmitUrl();
            long sleep = DatabaseCache.getIntValueBySortCodeAndCode("custom_switch","submit_interval",1000);
            pool.execute(()->{
                inputList.forEach(item ->{
                    params.put("phones",item.getPhone_Nos());
                    params.put("content",item.getContent());
                    try {
                        SMSUtil.sendSms(url, params, "utf-8");
                        Thread.sleep(sleep);
                    } catch (Exception e) {
                        SuperLogger.error(e.getMessage(),e);
                    }
                });
            });
        }catch (Exception e){
            SuperLogger.error(e.getMessage(),e);
        }finally {
            pool.shutdown();
        }
    }

    protected Map<String, String> getHttpParam(Integer userId) {
        EnterpriseUser enterpriseUser = enterpriseUserDAO.selectByPrimaryKey(userId);
        String enterpriseNo = enterpriseUser.getEnterprise_No();
        String account = enterpriseUser.getUser_Name();
        String httpSignKey = enterpriseUser.getHttp_Sign_Key();
        String timestamp = DateTime.getString(new Date(),DateTime.Y_M_D_H_M_S_S_2);
        String sign = SecretUtil.MD5(enterpriseNo+account+timestamp+httpSignKey);
        Map<String, String> params = new HashMap<>();
        params.put("enterprise_no", enterpriseNo);
        params.put("account", account);
        params.put("timestamp", timestamp);//yyyyMMddHHmmssSSS
        params.put("sign", sign);//签名
        params.put("subcode","");
        return params;
    }

    protected String getNetWayHttpSubmitUrl() {
        StringBuffer sb = new StringBuffer();
        sb.append("http://");
        sb.append(DatabaseCache.getStringValueBySortCodeAndCode("netway_http", "netway_http_ip",""));
        sb.append(":");
        sb.append(DatabaseCache.getStringValueBySortCodeAndCode("netway_http", "netway_http_port",""));
        sb.append("/json/submit");
        return sb.toString();
    }

}