package com.hero.wireless.web.service;

import com.drondea.wireless.config.Constant;
import com.drondea.wireless.config.ResultStatus;
import com.drondea.wireless.util.*;
import com.hero.wireless.enums.*;
import com.hero.wireless.json.SmsUIObjectMapper;
import com.hero.wireless.sms.sender.service.ISenderSmsService;
import com.hero.wireless.web.config.DatabaseCache;
import com.hero.wireless.web.dao.send.IInputLogDAO;
import com.hero.wireless.web.entity.base.Pagination;
import com.hero.wireless.web.entity.business.*;
import com.hero.wireless.web.entity.business.ext.ContactExt;
import com.hero.wireless.web.entity.business.ext.ExportFileExt;
import com.hero.wireless.web.entity.business.ext.SmsTemplateExt;
import com.hero.wireless.web.entity.ext.SqlStatisticsEntity;
import com.hero.wireless.web.entity.send.*;
import com.hero.wireless.web.entity.send.ext.*;
import com.hero.wireless.web.service.base.BaseSendManage;
import com.hero.wireless.web.util.*;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.io.ByteOrderMark;
import org.apache.commons.io.input.BOMInputStream;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


/**
 * 
 * 
 * SendManageImpl
 * 
 * @author 张丽阳
 * @createTime 2015年6月3日 下午5:39:02
 * @version 1.0.0
 * 
 */
@Service("sendManage")
public class SendManageImpl extends BaseSendManage implements ISendManage {


	//使用预编译正则表达式
	private static final Pattern BLANK_PATTERN = Pattern.compile("\\s*|\t");

	@Autowired
	IInputLogDAO<InputLog> inputLogDAO;

	@Resource(name = "defaultSenderServiceImpl")
	private ISenderSmsService senderSmsService;

	@Override
	public int batchInputSms(InputExt input) throws Exception {
		Enterprise enterprise = validateInputEnterprise(input);
		EnterpriseUser user = validateInputUser(input);
		String newContent = validateInputContent(user, input);
		input.setContent(newContent);

		input.setBusiness_User_Id(enterprise.getBusiness_User_Id());
		input.setAgent_No(enterprise.getAgent_No());
		List<String> mobiles = new ArrayList<>();
		if (StringUtils.isNotEmpty(input.getPhone_Nos())) {
			mobiles.addAll(Arrays.asList(input.getPhone_Nos().split(Constant.MUTL_MOBILE_SPLIT)));
            mobiles = mobiles.stream().filter(no -> StringUtils.isNotEmpty(no)).collect(Collectors.toList());
		}
		if (StringUtils.isNotEmpty(input.getFilePath())){
			String filePath = input.getFilePath();
			addFileMobiles(mobiles,filePath);
			String dirPath = filePath.substring(0,filePath.lastIndexOf(File.separator));
			FileUtil.deleteDirectory(dirPath);
		}
		int mobileCount = mobiles.size();
		// 余额校验
		checkBalance(user);
		List<List<String>> subList = ListUtils.partition(mobiles, 500);
		subList.forEach(item -> {
			String m = StringUtils.join(item, ",");
			if (m.length() > 8000) {
				throw new ServiceException("手机号码长度错误");
			}
			try {
				saveInput((Input) input.clone(), user, item.size(), m);
			} catch (Exception e) {
				SuperLogger.error(e.getMessage(), e);
				throw new ServiceException(e.getMessage());
			}
		});
		return mobileCount;
	}

	/**
	 * 格式化短信发送
	 * 
	 * @param file
	 * @param
	 * @return
	 */
	@Override
	public int formatSmsSend(MultipartFile file, InputExt input) throws Exception {
		SuperLogger.debug(file.getOriginalFilename());
		Enterprise enterprise = validateInputEnterprise(input);
		EnterpriseUser user = validateInputUser(input);
		input.setBusiness_User_Id(enterprise.getBusiness_User_Id());
		input.setAgent_No(enterprise.getAgent_No());
		ExcelReadTemplate excel = new ExcelReadTemplate(file) {
			private Input newInput = null;

			@Override
			protected void readSheetEvent(Sheet sheet) {
				checkBalance(user);
			}

			@Override
			protected void readRowBeforeEvent(Sheet sheet, Row row) {
				newInput = (Input) input.clone();
				newInput.setId(null);
			}

			@Override
			protected void readCellEvent(Sheet sheet, Row row, Cell cell, String cellName) {
				SuperLogger.debug("行号:" + row.getRowNum());
				if (row.getRowNum() == 0) {
					return;
				}
				if (cell == null) {
					return;
				}
				if (cell.getColumnIndex() == 0) {
					newInput.setPhone_Nos(getCellValue(cell).trim());
				} else {
					newInput.setContent(newInput.getContent().replaceAll("##" + cellName + "##", getCellValue(cell)));
				}
			}

			@Override
			protected void readRowAfterEvent(Sheet sheet, Row row) throws Exception {
				if (!StringUtils.isEmpty(newInput.getPhone_Nos())) {
					validateInputContent(user, newInput);
					saveInput(newInput, user, 1);
				} else {
					this.setReadedRowCount(this.getReadedRowCount() - 1);
				}
			}
		};
		excel.setStartRowNum(1);
		excel.setFileName(file.getOriginalFilename());
		excel.read();
		return excel.getReadedRowCount();
	}

	/**
	 * 
	 * @param inputs
	 * @return
	 * @author volcano
	 * @date 2019年10月24日下午6:15:38
	 * @version V1.0
	 */
	@Override
	public int insertInputs(List<Input> inputs) {
		int record = this.inputDAO.insertList(inputs);
		return record;
	}

	@Override
	public int batchInputSms(Input data) throws Exception {
		//检测企业是否锁定
		Enterprise enterprise = validateInputEnterprise(data);
		//检测用户是否锁定
		EnterpriseUser user = validateInputUser(data);
		//检测内容
		String content = validateInputContent(user, data);
		data.setContent(content);
		//检测批量发送手机号数量
		int mobileCount = validateInputPhoneNo(data);
		data.setBusiness_User_Id(enterprise.getBusiness_User_Id());
		data.setAgent_No(ObjectUtils.defaultIfNull(enterprise.getAgent_No(), SMSUtil.DEFAULT_NO));
		checkBalance(user);
		// 企业请求编号,这里是不允许为空的
		Assert.notNull(data.getMsg_Batch_No(), "MegId is null");
		saveInput(data, user, mobileCount);
		return mobileCount;
	}

    /**
     * 短信审核
     * @param status
     * @param inputExtList
     */
	@Override
	public void approveSMS(String status, List<InputExt> inputExtList) {
		if (ObjectUtils.isEmpty(inputExtList) || StringUtils.isEmpty(status)) {
			SuperLogger.error("短信审核异常，status或者inputExtList is null");
			throw new NullPointerException("status or inputExtList");
		}
		AuditSMS auditSMS = new AuditSMS(inputExtList, status, this);
		synchronized (waitApproveSmsQueue) {
			waitApproveSmsQueue.offer(auditSMS);
			waitApproveSmsQueue.notifyAll();
		}
	}
	
	@Override
	public Integer editInput(Input input) {
		return this.inputDAO.updateByPrimaryKeySelective(input);
	}

	@Override
	public Integer editAgreeInput(InputExt inputExt) {
		InputExample example = assemblyInputListConditon(inputExt);
		Input input = new Input();
		input.setContent(inputExt.getEditContentValue());
        input.setAssist_Audit_Key(SecretUtil.MD5(inputExt.getEnterprise_No()
                +inputExt.getEnterprise_User_Id()+inputExt.getEditContentValue()));
		return this.inputDAO.updateByExampleSelective(input, example);
	}

	@Override
	public SqlStatisticsEntity queryInputLogListTotalData(InputLogExt condition) {
		InputLogExample example = assemblyInputLogListConditon(condition);
		return this.inputLogExtDAO.statisticsExtByExample(example);
	}

	@Override
	public SqlStatisticsEntity querySubmitListTotalData(SubmitExt condition) {
		int timeInterval = DatabaseCache.getIntValueBySortCodeAndCode("sys_performance_setup", "header_statistics_time_interval", 7200);
		List<DateTime.DateInterVal> dateList = DateTime.getIntervalDate(timeInterval,condition.getMinSubmitDate(), condition.getMaxSubmitDate());
		List<SqlStatisticsEntity> resultList = new ArrayList<SqlStatisticsEntity>();
		for(int i =0;i<dateList.size();i++){
			SubmitExample example = null;
			DateTime.DateInterVal dateInterVal = dateList.get(i);
			condition.setMinSubmitDate(dateInterVal.getBegin());
			condition.setMaxSubmitDate(dateInterVal.getEnd());
			example = assemblySubmitListConditon(condition, false);
			if(i == (dateList.size() -1)){
				example = assemblySubmitListConditon(condition, true);
			}
			SqlStatisticsEntity entity = submitExtDAO.statisticsExtByExample(example);
			resultList.add(entity);
		}
		SqlStatisticsEntity newEntity = getSubmitSqlStatisticsEntity(resultList);
		return newEntity;
	}

	@Override
	public SqlStatisticsEntity queryReportListTotalData(ReportExt condition) {
		int timeInterval = DatabaseCache.getIntValueBySortCodeAndCode("sys_performance_setup", "header_statistics_time_interval", 7200);
		List<DateTime.DateInterVal> dateList = DateTime.getIntervalDate(timeInterval,condition.getMinSubmitDate(), condition.getMaxSubmitDate());
		List<SqlStatisticsEntity> resultList = new ArrayList<SqlStatisticsEntity>();
		for(int i =0;i< dateList.size(); i++){
			ReportExample example = null;
			final DateTime.DateInterVal dateInterVal = dateList.get(i);
			condition.setMinSubmitDate(dateInterVal.getBegin());
			condition.setMaxSubmitDate(dateInterVal.getEnd());
			example = assemblyReportListConditon(condition, false);
			if(i == (dateList.size() -1)){
				example = assemblyReportListConditon(condition, true);
			}
			SqlStatisticsEntity entity = this.reportExtDAO.statisticsExtByExample(example);
			resultList.add(entity);
		}
		SqlStatisticsEntity newEntity = getReportSqlStatisticsEntity(resultList);
		return newEntity;
	}
	
	@Override
	public List<InputExt> queryInputList(InputExt condition) {
		InputExample example = assemblyInputListConditon(condition);
		example.setOrderByClause(" id desc ");
		example.setPagination(condition.getPagination());
		return this.inputExtDAO.selectExtByExamplePage(example);
	}

	@Override
	public List<InputExt> queryAuditingInputList(InputExt condition) {
		InputExample example = assemblyInputListConditon(condition);
		if (condition.getPagination() != null) {
			int count = this.inputExtDAO.selectAuditingExtByExampleCount(example);
			Pagination page = condition.getPagination();
			page.setTotalCount(count);
			example.setPagination(condition.getPagination());
		}
		List<InputExt> list = this.inputExtDAO.selectAuditingExtByExample(example);
		return list;
	}

	@Override
	public int queryAuditingInputCount(InputExt inputExt) {
		InputExample example = assemblyInputListConditon(inputExt);
		return inputDAO.countByExample(example);
	}

	@Override
	public List<InputLogExt> queryInputLogList(InputLogExt condition) {
		InputLogExample example = assemblyInputLogListConditon(condition);
		example.setOrderByClause(" id desc ");
		example.setPagination(condition.getPagination());
		return this.inputLogExtDAO.selectExtByExamplePage(example);
	}

	@Override
	public List<InputLogExt> queryInputLogListSharding(InputLogExt condition) {
		List<InputLogExample> examples = assemblyInputLogListShardingCondition(condition);
		return queryShardingPageList(inputLogExtDAO, examples, condition);
	}

	@Override
	public ContactGroup addContactGroup(ContactGroup contactGroup) {
		contactGroup.setCreate_Date(new Date());
		ContactGroupExample qExample = new ContactGroupExample();
		ContactGroupExample.Criteria qCriteria = qExample.createCriteria();
		qCriteria.andGroup_NameEqualTo(contactGroup.getGroup_Name());
		if (!StringUtils.isEmpty(contactGroup.getEnterprise_No())) {
			qCriteria.andEnterprise_NoEqualTo(contactGroup.getEnterprise_No());
		}
		if (contactGroup.getCreate_Enterprise_User_Id() != null) {
			qCriteria.andCreate_Enterprise_User_IdEqualTo(contactGroup.getCreate_Enterprise_User_Id());
		}
		int existsCount = this.contactGroupDAO.countByExample(qExample);
		if (existsCount > 0) {
			throw new ServiceException(ResultStatus.GROUP_NAME_EXSITS);
		}
		this.contactGroupDAO.insert(contactGroup);
		return contactGroup;
	}

	@Override
	public Boolean exsitsContactGroup(ContactGroup contactGroup) {
		ContactGroupExample qExample = new ContactGroupExample();
		ContactGroupExample.Criteria qCriteria = qExample.createCriteria();
		if (!StringUtils.isEmpty(contactGroup.getGroup_Name())) {
			qCriteria.andGroup_NameEqualTo(contactGroup.getGroup_Name());
		}
		if (!StringUtils.isEmpty(contactGroup.getEnterprise_No())) {
			qCriteria.andEnterprise_NoEqualTo(contactGroup.getEnterprise_No());
		}
		if (contactGroup.getCreate_Enterprise_User_Id() != null) {
			qCriteria.andCreate_Enterprise_User_IdEqualTo(contactGroup.getCreate_Enterprise_User_Id());
		}
		int existsCount = this.contactGroupDAO.countByExample(qExample);
		return existsCount > 0;
	}

	@Override
	public Integer editContactGroup(ContactGroup contactGroup) {
		ContactGroupExample qExample = new ContactGroupExample();
		ContactGroupExample.Criteria qCriteria = qExample.createCriteria();
		qCriteria.andGroup_NameEqualTo(contactGroup.getGroup_Name());
		if (!StringUtils.isEmpty(contactGroup.getEnterprise_No())) {
			qCriteria.andEnterprise_NoEqualTo(contactGroup.getEnterprise_No());
		}
		if (contactGroup.getCreate_Enterprise_User_Id() != null) {
			qCriteria.andCreate_Enterprise_User_IdEqualTo(contactGroup.getCreate_Enterprise_User_Id());
		}
		int existsCount = this.contactGroupDAO.countByExample(qExample);
		if (existsCount > 0) {
			throw new ServiceException(ResultStatus.GROUP_NAME_EXSITS);
		}
		return this.contactGroupDAO.updateByPrimaryKeySelective(contactGroup);
	}

	@Override
	public Integer deleteContactGroup(ContactGroup contactGroup) {
		ContactExample contactExample = new ContactExample();
		ContactExample.Criteria cri = contactExample.createCriteria();
		cri.andGroup_IdEqualTo(contactGroup.getId());
		this.contactExtDAO.deleteByExample(contactExample);
		return this.contactGroupDAO.deleteByPrimaryKey(contactGroup.getId());
	}

	@Override
	public Integer deleteContactGroupByIds(List<Integer> idList, EnterpriseUser enterpriseUser) {
		ContactExample contactExample = new ContactExample();
		ContactExample.Criteria contactCri = contactExample.createCriteria();
		contactCri.andGroup_IdIn(idList);
		ContactGroupExample example = new ContactGroupExample();
		ContactGroupExample.Criteria cri = example.createCriteria();
		cri.andIdIn(idList);
		if (StringUtils.isNotEmpty(enterpriseUser.getEnterprise_No())){
			contactCri.andEnterprise_NoEqualTo(enterpriseUser.getEnterprise_No());
			cri.andEnterprise_NoEqualTo(enterpriseUser.getEnterprise_No());
		}
		if (enterpriseUser.getId()!= null){
			contactCri.andCreate_Enterprise_User_IdEqualTo(enterpriseUser.getId());
			cri.andCreate_Enterprise_User_IdEqualTo(enterpriseUser.getId());
		}
		this.contactDAO.deleteByExample(contactExample);
		return this.contactGroupDAO.deleteByExample(example);
	}

	@Override
	public Integer deleteContactByIds(List<Integer> idList, EnterpriseUser enterpriseUser) {
		ContactExample example = new ContactExample();
		ContactExample.Criteria cri = example.createCriteria();
		cri.andIdIn(idList);
		if (StringUtils.isNotEmpty(enterpriseUser.getEnterprise_No())){
			cri.andEnterprise_NoEqualTo(enterpriseUser.getEnterprise_No());
		}
		if (enterpriseUser.getId()!= null){
			cri.andCreate_Enterprise_User_IdEqualTo(enterpriseUser.getId());
		}
		return this.contactDAO.deleteByExample(example);
	}

	@Override
	public List<ContactGroup> queryContactGroupList(ContactGroup condition) {
		ContactGroupExample contactExample = new ContactGroupExample();
		ContactGroupExample.Criteria cri = contactExample.createCriteria();
		if (condition.getId() != null) {
			cri.andIdEqualTo(condition.getId());
		}
		if (!StringUtils.isEmpty(condition.getGroup_Name())) {
			cri.andGroup_NameLike("%" + condition.getGroup_Name() + "%");
		}
		if (!StringUtils.isEmpty(condition.getEnterprise_No())) {
			cri.andEnterprise_NoEqualTo(condition.getEnterprise_No());
		}
		if (condition.getCreate_Enterprise_User_Id() != null) {
			cri.andCreate_Enterprise_User_IdEqualTo(condition.getCreate_Enterprise_User_Id());
		}
		contactExample.setPagination(condition.getPagination());
		return this.contactGroupDAO.selectByExamplePage(contactExample);
	}

	@Override
	public Contact addContact(Contact contact) {
		ContactExample example = new ContactExample();
		ContactExample.Criteria cri = example.createCriteria();
		cri.andPhone_NoEqualTo(contact.getPhone_No());
		cri.andGroup_IdEqualTo(contact.getGroup_Id());
		cri.andEnterprise_NoEqualTo(contact.getEnterprise_No());
		int count = this.contactDAO.countByExample(example);
		if (count > 0) {
			throw new ServiceException(ResultStatus.CONTACT_MOBILE_EXSITS, contact.getPhone_No());
		}
		contact.setCreate_Date(new Date());
		this.contactDAO.insert(contact);
		return contact;
	}

	@Override
	public Integer editContact(Contact contact) {
		ContactExample example = new ContactExample();
		ContactExample.Criteria cri = example.createCriteria();
		cri.andIdEqualTo(contact.getId());
		if (StringUtils.isNotEmpty(contact.getEnterprise_No())){
			cri.andEnterprise_NoEqualTo(contact.getEnterprise_No());
		}
		if (contact.getCreate_Enterprise_User_Id()!= null){
			cri.andCreate_Enterprise_User_IdEqualTo(contact.getCreate_Enterprise_User_Id());
		}
		contact.setId(null);
		contact.setEnterprise_No(null);
		contact.setCreate_Enterprise_User_Id(null);
		return this.contactDAO.updateByExampleSelective(contact,example);
	}

	@Override
	public Integer deleteContact(Contact contact) {
		return this.contactExtDAO.deleteByPrimaryKey(contact.getId());
	}

	@Override
	public List<ExportFile> queryExportFile(ExportFile condition) {
		ExportFileExample example = new ExportFileExample();
		ExportFileExample.Criteria cri = example.createCriteria();
		if (!StringUtils.isEmpty(condition.getBatch_Id())) {
			cri.andBatch_IdEqualTo(condition.getBatch_Id());
		}
		if (!StringUtils.isEmpty(condition.getEnterprise_No())) {
			cri.andEnterprise_NoEqualTo(condition.getEnterprise_No());
		}
		if (!StringUtils.isEmpty(condition.getAgent_No())) {
			cri.andAgent_NoEqualTo(condition.getAgent_No());
		}
		if (condition.getUser_Id() != null) {
			cri.andUser_IdEqualTo(condition.getUser_Id());
		}
		if (condition.getId() != null) {
			cri.andIdEqualTo(condition.getId());
		}
		if (StringUtils.isNotEmpty(condition.getFIle_Name())) {
			cri.andFIle_NameEqualTo(condition.getFIle_Name());
		}
		example.setOrderByClause(" id desc ");
		example.setPagination(condition.getPagination());
		return this.exportFileDAO.selectByExamplePage(example);
	}

	@Override
	public List<ContactExt> queryContactList(ContactExt condition) {
		ContactExample example = getContactExample(condition);
		example.setPagination(condition.getPagination());
		return this.contactExtDAO.selectExtByExamplePage(example);
	}

	@Override
	public ContactGroup queryContactGroupById(Integer id) {
		return this.contactGroupDAO.selectByPrimaryKey(id);
	}

	@Override
	public ContactExt queryContactById(Integer id) {
		ContactExt contactExt = new ContactExt();
		contactExt.setId(id);
		List<ContactExt> contactList = this.queryContactList(contactExt);
		if (contactList != null && !contactList.isEmpty()) {
			return contactList.get(0);
		}
		return null;
	}

	/**
	 * 文件导入联系人
	 * 
	 * @return
	 */
	@Override
	public Map<String,Object> importContact(MultipartFile multipartFile, ContactExt data) {
        Map<String,Object> result = new HashMap<>();
        int successTotal = 0;//成功条数
        int failTotal = 0;//失败条数
        List<String> failList = new ArrayList<>();//失败号码

        BOMInputStream bomIn = null;
		BufferedReader reader = null;
        InputStreamReader isr = null;
        String charset = "GBK";
        try {
            //可检测多种类型，并剔除bom
            bomIn = new BOMInputStream(multipartFile.getInputStream(), false, ByteOrderMark.UTF_8, ByteOrderMark.UTF_16LE, ByteOrderMark.UTF_16BE);
            //若检测到bom，则使用bom对应的编码
            if(bomIn.hasBOM()){
                charset = bomIn.getBOMCharsetName();
            }
			isr = new InputStreamReader(multipartFile.getInputStream(), charset);
			reader = new BufferedReader(isr);
			String contactInfo = "";
			List<Contact> insertList = new ArrayList<>();// 插入列表
			while ((contactInfo = reader.readLine()) != null) {
                if (StringUtils.isEmpty(contactInfo)) {
                    continue;
                }
				ContactExt contactExt = (ContactExt) data.clone();
				contactExt.setId(null);
				contactExt.setReal_Name("");
				contactExt.setGender("");
				contactExt.setBirthday(null);
				contactExt.setAddress("");
				contactExt.setCreate_Date(new Date());
				// 号码,姓名,性别,生日,地址
				contactInfo =  replaceSpecialStr(contactInfo);
				String[] contactInfoArray = contactInfo.split(Constant.MUTL_MOBILE_SPLIT);
				if (contactInfoArray.length > 0) {
				    String phoneNo = contactInfoArray[0];
				    if(phoneNo.startsWith(BeanUtil.UFEFF)){
                        phoneNo = phoneNo.replaceAll(BeanUtil.UFEFF, "");
                    }
					if (!phoneNo.matches("1[0-9]{10}")) {
                        failTotal++;
                        failList.add(phoneNo);
						continue;
					}
					contactExt.setPhone_No(phoneNo);
				}
				if (contactInfoArray.length > 1) {
					contactExt.setReal_Name(contactInfoArray[1]);
				}
				if (contactInfoArray.length > 2) {
					contactExt.setGender(contactInfoArray[2]);
				}
				if (contactInfoArray.length > 3) {
					SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
					try {
						contactExt.setBirthday(df.parse(contactInfoArray[3]));
					} catch (ParseException e) {
						SuperLogger.error(e.getMessage(), e);
					}
				}
				if (contactInfoArray.length > 4) {
					contactExt.setAddress(contactInfoArray[4]);
				}
                if (contactInfoArray.length > 5) {
                    contactExt.setCompany(contactInfoArray[5]);//单位
                }
                if (contactInfoArray.length > 6) {
                    contactExt.setPosition(contactInfoArray[6]);//职务
                }
				insertList.add(contactExt);
                successTotal++;
				if (insertList.size() >= 1000) {
					this.contactExtDAO.insertList(insertList);
					insertList.clear();
				}
			}
			this.contactDAO.insertList(insertList);
			insertList.clear();
		} catch (Exception e) {
			SuperLogger.error(e.getMessage(), e);
		} finally {
			try {
                bomIn.close();
				isr.close();
				reader.close();
			} catch (IOException e) {
				SuperLogger.error(e.getMessage(), e);
			}
		}
        result.put("successTotal",successTotal);
        result.put("failTotal",failTotal);
        result.put("failList",failList);
        return result;
	}

	/**
	 * 去除字符串中的空格、制表符（tab）
	 * @param str
	 * @return
	 */
	private String replaceSpecialStr(String str) {
		String repl = "";
		if (str!=null) {
			Matcher m = BLANK_PATTERN.matcher(str);
			repl = m.replaceAll("");
		}
		return repl;
	}

	@Override
	public List<Inbox> queryInboxList(InboxExt inbox) {
		InboxExample example = assemblyInboxListConditon(inbox);
		example.setOrderByClause(" create_date desc ");
		example.setPagination(inbox.getPagination());
		List<Inbox> list = this.inboxDAO.selectByExamplePage(example);
		return list;
	}

	@Override
	public void exportInputLog(final String baseExportBasePath, final InputLogExt inputLogExt,
			final ExportFileExt exportFile) {
        String redisKey = newThreadBefore(Constant.THREAD_TOTAL_EXPORT);//校验
		new Thread(() -> {
			exportFile.setBatch_Id(CodeUtil.buildMsgNo());
			int pageSize = DatabaseCache.getIntValueBySortCodeAndCode("sys_performance_setup", "export_file_size",5000);
			//拆分成天按照每个表分别导数据
			List<DateTime.DateInterVal> dayInterval = DateTime.getDayIntervalDate(inputLogExt.getMinCreateDate(), inputLogExt.getMaxCreateDate());
			dayInterval.stream().forEach(dateInterval -> {
				Pagination firstPage = new Pagination(1, pageSize);
				List<Map<String, Object>> inputLogExtList = null;
				inputLogExt.setMinCreateDate(dateInterval.getBegin());
				inputLogExt.setMaxCreateDate(dateInterval.getEnd());
				while (true) {
					inputLogExt.setPagination(firstPage);
					inputLogExtList = queryInputLogListForExportPage(inputLogExt);
					if (inputLogExtList == null || inputLogExtList.isEmpty()) {
						break;
					}
					exportInputLogExcel(baseExportBasePath, inputLogExtList, exportFile);
					if (firstPage.getPageIndex() == firstPage.getPageCount()) {
						break;
					}
					firstPage = new Pagination(firstPage.getPageIndex() + 1, pageSize);
				}
			});

			newThreadAfter(redisKey);
		}).start();
	}

	@Override
	public List<AutoReplySms> queryAutoReplySmsList(AutoReplySms autoReplySms) {
		AutoReplySmsExample example = new AutoReplySmsExample();
		AutoReplySmsExample.Criteria c = example.createCriteria();
		if (!StringUtils.isEmpty(autoReplySms.getKey_Word())) {
			c.andKey_WordLike("%" + autoReplySms.getKey_Word() + "%");
		}
		if (!StringUtils.isEmpty(autoReplySms.getContent())) {
			c.andContentLike("%" + autoReplySms.getContent() + "%");
		}
		if (!StringUtils.isEmpty(autoReplySms.getEnterprise_No())) {
			c.andEnterprise_NoEqualTo(autoReplySms.getEnterprise_No());
		}
		if (autoReplySms.getEnterprise_User_Id() != null) {
			c.andEnterprise_User_IdEqualTo(autoReplySms.getEnterprise_User_Id());
		}
		example.setPagination(autoReplySms.getPagination());
		return this.autoReplySmsDAO.selectByExamplePage(example);
	}

	@Override
	public AutoReplySms addAutoReplySms(AutoReplySms autoReplySms) {
		autoReplySms.setCreate_Date(new Date());
		this.autoReplySmsDAO.insert(autoReplySms);
		return autoReplySms;
	}

	@Override
	public Integer deleteAutoReplySmsByIds(List<Integer> idList,EnterpriseUser enterpriseUser) {
		AutoReplySmsExample example = new AutoReplySmsExample();
		AutoReplySmsExample.Criteria cri = example.createCriteria();
		cri.andIdIn(idList);
		if (StringUtils.isNotEmpty(enterpriseUser.getEnterprise_No())){
			cri.andEnterprise_NoEqualTo(enterpriseUser.getEnterprise_No());
		}
		if (enterpriseUser.getId()!= null){
			cri.andEnterprise_User_IdEqualTo(enterpriseUser.getId());
		}
		return this.autoReplySmsDAO.deleteByExample(example);
	}

	@Override
	public Integer editAutoReplySms(AutoReplySms autoReplySms) {
		AutoReplySmsExample example = new AutoReplySmsExample();
		AutoReplySmsExample.Criteria cri = example.createCriteria();
		cri.andIdEqualTo(autoReplySms.getId());
		if (StringUtils.isNotEmpty(autoReplySms.getEnterprise_No())){
			cri.andEnterprise_NoEqualTo(autoReplySms.getEnterprise_No());
		}
		if (autoReplySms.getEnterprise_User_Id() != null){
			cri.andEnterprise_User_IdEqualTo(autoReplySms.getEnterprise_User_Id());
		}
		autoReplySms.setId(null);
		autoReplySms.setEnterprise_No(null);
		autoReplySms.setEnterprise_User_Id(null);
		return this.autoReplySmsDAO.updateByExampleSelective(autoReplySms,example);
	}

	@Override
	public AutoReplySms queryAutoReplySmsByIdSelective(AutoReplySms condition) {
		AutoReplySmsExample example = new AutoReplySmsExample();
		AutoReplySmsExample.Criteria cri = example.createCriteria();
		cri.andIdEqualTo(condition.getId());
		if (StringUtils.isNotEmpty(condition.getEnterprise_No())){
			cri.andEnterprise_NoEqualTo(condition.getEnterprise_No());
		}
		if (condition.getEnterprise_User_Id() != null){
			cri.andEnterprise_User_IdEqualTo(condition.getEnterprise_User_Id());
		}
		List<AutoReplySms> list = this.autoReplySmsDAO.selectByExample(example);
		return list.size()>0?list.get(0): null;
	}

	@Override
	public List<Submit> queryMOSubmitList(SubmitExt condition){
		List<Submit> result = new ArrayList<>();
		Date now = new Date();
		//匹配近3天的submit记录
		for (int i = 0; i < 3; i++) {
			Date begin = DateTime.getTheDayBeforeMinDate(now, - i);
			Date end = DateTime.getTheDayBeforeMaxDate(now, - i);
			condition.setMinSubmitDate(begin);
			condition.setMaxSubmitDate(end);
			SubmitExample submitExample = assemblySubmitListConditon(condition, true);
			submitExample.setOrderByClause(" id desc");
			submitExample.setPagination(new Pagination(0, 10));
			result = this.submitExtDAO.selectByExample(submitExample);
			if (result.size() > 0) {
				break;
			}
		}
		return result;
	}

	@Override
	public List<SubmitExt> querySubmitListSharding(SubmitExt condition) {
		List<SubmitExample> examples = assemblySubmitListShardingCondition(condition);
		return queryShardingPageList(submitExtDAO, examples, condition);
	}

	@Override
	public List<ReportExt> queryReportExtList(ReportExt condition) {
		ReportExample example = assemblyReportListConditon(condition, true);
		example.setPagination(condition.getPagination());
		return this.reportExtDAO.selectExtByExamplePage(example);
	}

	@Override
	public List<ReportExt> queryReportListSharding(ReportExt condition) {
		List<ReportExample> examples = assemblyReportListShardingCondition(condition);
		return queryShardingPageList(reportExtDAO, examples, condition);
	}

	@Override
	public void repushSmsReport(ReportExt reportExt) throws Exception {
		if (reportExt == null) {
			throw new ServiceException(ResultStatus.REPUSH_SMS_REPORT__NOT_NULL);
		}
		if (!DateUtils.isSameDay(reportExt.getMinSubmitDate(), reportExt.getMaxSubmitDate())) {
			throw new ServiceException("不可以跨天补退！");
		}
		new Thread(() -> {
			int selectMax = DatabaseCache.getIntValueBySortCodeAndCode("sys_performance_setup",
					"selectmaxcount", 3000);
			//拆分成天按照每个表分别导数据
			List<DateTime.DateInterVal> dayInterval = DateTime.getDayIntervalDate(reportExt.getMinSubmitDate(), reportExt.getMaxSubmitDate());
			dayInterval.stream().forEach(dateInterVal -> {
				reportExt.setMinSubmitDate(dateInterVal.getBegin());
				reportExt.setMaxSubmitDate(dateInterVal.getEnd());
				ReportExample example = assemblyReportListConditon(reportExt, true);
				Pagination firstPage = new Pagination(1, selectMax);
				while (true) {
					example.setPagination(firstPage);
					List<ReportExt> list = reportExtDAO.selectExtByExample(example);
					if (ObjectUtils.isEmpty(list)) {
						return;
					}
					list.forEach(item -> {
						// 发送notifyReport消息
						QueueUtil.notifyReport(item);
					});
					firstPage = new Pagination(firstPage.getPageIndex() + 1, selectMax);
				}
			});
		}).start();
	}

	@Override
	public void deleteInputById(List<Long> ckIds, EnterpriseUser enterpriseUser) {
	    if (ObjectUtils.isEmpty(ckIds)) {
			return;
		}
		InputExample inputExample = new InputExample();
		InputExample.Criteria cri = inputExample.createCriteria();
		cri.andIdIn(ckIds);
		if(StringUtils.isNotEmpty(enterpriseUser.getEnterprise_No())){
			cri.andEnterprise_NoEqualTo(enterpriseUser.getEnterprise_No());
		}
		if (enterpriseUser.getId() != null){
			cri.andEnterprise_User_IdEqualTo(enterpriseUser.getId());
		}
		//通知下游
		List<Input> inputList = inputDAO.selectByExample(inputExample);
		inputList.forEach(input -> {
			//保存回执
			SMSUtil.notifyNetWayReport(input, null, "定时取消");
		});
		inputDAO.deleteByExample(inputExample);
	}

	@Override
	public List<Map<String, Object>> queryInputLogListForExportPage(InputLogExt inputLogExt) {
		InputLogExample example = assemblyInputLogListConditon(inputLogExt);
        InputLogExample.Criteria cri = example.createCriteria();
        if (StringUtils.isNotBlank(inputLogExt.getMessage_Type_Code())) {
            cri.andMessage_Type_CodeEqualTo(inputLogExt.getMessage_Type_Code());
        }
		example.setPagination(inputLogExt.getPagination());
		return inputLogExtDAO.queryInputLogListForExportPage(example);
	}

	@Override
	public List<Map<String, Object>> querySubmitListForExportPage(SubmitExt condition) {
		SubmitExample example = assemblySubmitListConditon(condition, true);
		if (example == null) {
			return null;
		}
		example.setPagination(condition.getPagination());
		return submitExtDAO.querySubmitListForExportPage(example);
	}

	@Override
	public void exportSubmit(final String path, final SubmitExt bean, final ExportFileExt exportFile,
                               final String comeFlag) {
        String redisKey = newThreadBefore(Constant.THREAD_TOTAL_EXPORT);//校验
		new Thread(() -> {
			exportFile.setBatch_Id(CodeUtil.buildMsgNo());
			int pageSize = DatabaseCache.getIntValueBySortCodeAndCode("sys_performance_setup", "export_file_size",5000);
			//拆分成天按照每个表分别导数据
			List<DateTime.DateInterVal> dayInterval = DateTime.getDayIntervalDate(bean.getMinSubmitDate(), bean.getMaxSubmitDate());
			dayInterval.stream().forEach(dateInterVal -> {
				Pagination firstPage = new Pagination(1, pageSize);
				List<Map<String, Object>> beanList = null;
				bean.setMinSubmitDate(dateInterVal.getBegin());
				bean.setMaxSubmitDate(dateInterVal.getEnd());
				while (true) {
					bean.setPagination(firstPage);
					beanList = querySubmitListForExportPage(bean);
					if (beanList == null || beanList.isEmpty()) {
						break;
					}
					exportSubmitExcel(path, beanList, exportFile, comeFlag);
					if (firstPage.getPageIndex() == firstPage.getPageCount()) {
						break;
					}
					firstPage = new Pagination(firstPage.getPageIndex() + 1, pageSize);
				}
			});
			newThreadAfter(redisKey);
		}).start();
	}

	@Override
	public List<Map<String, Object>> queryReportListForExportPage(ReportExt condition) {
		ReportExample example = assemblyReportListConditon(condition, true);
		example.setPagination(condition.getPagination());
		return this.reportExtDAO.queryReportListForExportPage(example);
	}

	@Override
	public void exportReport(final String baseExportBasePath, final ReportExt bean, final ExportFileExt exportFile,
			final String comeFlag) {
        String redisKey = newThreadBefore(Constant.THREAD_TOTAL_EXPORT);//校验
        new Thread() {
			@Override
			public void run() {
				exportFile.setBatch_Id(CodeUtil.buildMsgNo());
				int pageSize = DatabaseCache.getIntValueBySortCodeAndCode("sys_performance_setup", "export_file_size",5000);
				//拆分成天按照每个表分别导数据
				List<DateTime.DateInterVal> dayInterval = DateTime.getDayIntervalDate(bean.getMinSubmitDate(), bean.getMaxSubmitDate());
				dayInterval.stream().forEach(dateInterVal -> {
					Pagination firstPage = new Pagination(1, pageSize);
					List<Map<String, Object>> beanList = null;
					bean.setMinSubmitDate(dateInterVal.getBegin());
					bean.setMaxSubmitDate(dateInterVal.getEnd());

					while (true) {
						bean.setPagination(firstPage);
						beanList = queryReportListForExportPage(bean);
						if (beanList == null || beanList.isEmpty()) {
							break;
						}
						exportReportExcel(baseExportBasePath, beanList, exportFile, comeFlag);
						if (firstPage.getPageIndex() == firstPage.getPageCount()) {
							break;
						}
						firstPage = new Pagination(firstPage.getPageIndex() + 1, pageSize);
					}
				});
                newThreadAfter(redisKey);
			}
		}.start();
	}

	@Override
	public List<Map<String, Object>> queryInputListForExportPage(InputExt condition) {
		InputExample example = assemblyInputListConditon(condition);
		example.setPagination(condition.getPagination());
		return this.inputExtDAO.queryInputListForExportPage(example);
	}

	@Override
	public void exportInput(final String baseExportBasePath, final InputExt bean, final ExportFileExt exportFile) {
        String redisKey = newThreadBefore(Constant.THREAD_TOTAL_EXPORT);//校验
		new Thread() {
			@Override
			public void run() {
				int pageSize = DatabaseCache.getIntValueBySortCodeAndCode("sys_performance_setup", "export_file_size",5000);
				Pagination firstPage = new Pagination(1, pageSize);
				List<Map<String, Object>> beanList = null;
				exportFile.setBatch_Id(CodeUtil.buildMsgNo());
				while (true) {
					bean.setPagination(firstPage);
					beanList = queryInputListForExportPage(bean);
					if (beanList == null || beanList.isEmpty()) {
						break;
					}
					exportInputExcel(baseExportBasePath, beanList, exportFile);
					if (firstPage.getPageIndex() == firstPage.getPageCount()) {
						break;
					}
					firstPage = new Pagination(firstPage.getPageIndex() + 1, pageSize);
				}
                newThreadAfter(redisKey);
			}
		}.start();
	}

	@Override
	public void exportContactList(final String baseExportBasePath, final ContactExt bean, final ExportFileExt exportFile) {
        String redisKey = newThreadBefore(Constant.THREAD_TOTAL_EXPORT);//校验
		new Thread() {
			@Override
			public void run() {
				int pageSize = DatabaseCache.getIntValueBySortCodeAndCode("sys_performance_setup", "export_file_size",5000);
				Pagination firstPage = new Pagination(1, pageSize);
				List<Map<String, Object>> beanList = null;
				exportFile.setBatch_Id(CodeUtil.buildMsgNo());
				while (true) {
					bean.setPagination(firstPage);
					beanList = queryContactListForExportPage(bean);
					if (beanList == null || beanList.isEmpty()) {
						break;
					}
					exportContactExcel(baseExportBasePath, beanList, exportFile);
					if (firstPage.getPageIndex() == firstPage.getPageCount()) {
						break;
					}
					firstPage = new Pagination(firstPage.getPageIndex() + 1, pageSize);
				}
                newThreadAfter(redisKey);
			}
		}.start();
	}

	@Override
	public void downloadFile(HttpServletResponse response, ExportFile exportFile) throws Exception {
		List<ExportFile> exportFiles = this.queryExportFile(exportFile);
		if (exportFiles == null || exportFiles.size() != 1) {
			return;
		}
		exportFile = exportFiles.get(0);
		File file = new File(exportFile.getFile_Url());
		if (!file.exists()) {
			return;
		}
		try (FileInputStream fis = new FileInputStream(file);
				BufferedInputStream bis = new BufferedInputStream(fis)){
			response.setHeader("content-type", "application/octet-stream");
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition",
					"attachment;filename=" + new String(exportFile.getFIle_Name().getBytes("utf-8"), "ISO-8859-1"));
			OutputStream os = response.getOutputStream();
			byte[] buffer = new byte[1024];
			int i = bis.read(buffer);
			while (i != -1) {
				os.write(buffer, 0, i);
				i = bis.read(buffer);
			}
			return;
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public void repushSmsMo(List<Integer> inboxIds) {
		if (inboxIds == null || inboxIds.isEmpty()) {
			throw new ServiceException(ResultStatus.REPUSH_SMS_MO__NOT_NULL);
		}
		InboxExample example = new InboxExample();
		example.createCriteria().andIdIn(inboxIds);

		Inbox inbox = new Inbox();
		inbox.setPull_Total(0);
		inboxDAO.updateByExampleSelective(inbox, example);
	}



	@Override
	public void resendSms(SubmitExt condition, String subCode, String enterpriseNo, Integer enterpriseUserId) {
		int maxResend = DatabaseCache.getIntValueBySortCodeAndCode("sys_performance_setup", "max_resend_total", 20000);
		SubmitExample example = assemblySubmitListConditon(condition, true);
		List<SubmitExt> list = this.submitExtDAO.selectExtByExample(example);
		if (list.size() > maxResend) {
			throw new  ServiceException("超过最大重发数限制");
		}
		if (ObjectUtils.isEmpty(list)) {
			return;
		}
		InputLogExample inputLogExample = new InputLogExample();
		Map<String, String> map = new HashMap<String, String>();
		list.forEach(item -> {
			if (StringUtils.isEmpty(item.getMsg_Batch_No())) {
				return;
			}
			if (ObjectUtils.isNotEmpty(map.get(item.getMsg_Batch_No()))) {
				return;
			}
			String content = item.getContent();
			if (item.getIs_LMS()) {// 长短信
				inputLogExample.clear();
				Date inputLogDate = item.getInput_Log_Date();
				Date minDate = condition.getMinSubmitDate();
				Date maxDate = condition.getMaxSubmitDate();
				if (inputLogDate != null) {
					minDate = DateTime.getStartOfDay(inputLogDate);
					maxDate = DateTime.getEndOfDay(inputLogDate);
				}
				inputLogExample.createCriteria().andMsg_Batch_NoEqualTo(item.getMsg_Batch_No()).
						andCreate_DateBetween(minDate, maxDate);
				List<InputLog> inputLogs = inputLogExtDAO.selectByExample(inputLogExample);
				content = ObjectUtils.isNotEmpty(inputLogs) ? inputLogs.get(0).getContent() : content;
				map.put(item.getMsg_Batch_No(), "1");
			}
			// 2019.11.14 不再封装Input，直接调接口
			noticeManage.reSendSms(item.getPhone_No(), content, enterpriseNo, enterpriseUserId, subCode);
		});
	}

	@Override
	public void sendDeductReport(SubmitExt condition, String deductStatusCode) {
		int maxDeduct = DatabaseCache.getIntValueBySortCodeAndCode("sys_performance_setup", "max_deduct_report", 20000);
		SubmitExample example = assemblySubmitListConditon(condition, true);
		List<SubmitExt> list = this.submitExtDAO.selectExtByExample(example);
		if (list.size() > maxDeduct) {
			throw new  ServiceException("超过最大数量限制");
		}
		if (ObjectUtils.isEmpty(list)) {
			return;
		}
		list.forEach(submit -> {
			//只有成功的才处理
			if (!SubmitStatus.SUCCESS.toString().equalsIgnoreCase(submit.getSubmit_Status_Code())) {
				return;
			}
			Report report = SMSUtil.buildReport(submit, submit.getPhone_No());
			report.setSP_Number(submit.getSP_Number());
			if (ReportNativeStatus.DELIVRD.toString().equalsIgnoreCase(deductStatusCode)) {
				report.setNative_Status(ReportNativeStatus.DELIVRD.toString());
				report.setStatus_Code(ReportStatus.SUCCESS.toString());
			} else if(ReportStatus.FAILD.toString().equalsIgnoreCase(deductStatusCode)) {
				report.setNative_Status(deductStatusCode);
				report.setStatus_Code(ReportStatus.FAILD.toString());
			} else {
				report.setNative_Status(deductStatusCode);
				report.setStatus_Code(ReportStatus.UNKNOWN.toString());
			}

			report.setStatus_Date(new Date());
			report.setCreate_Date(new Date());
			QueueUtil.saveReport(report);
		});
	}

	@SuppressWarnings("deprecation")
	@Override
	public void sendSMS(String mobiles, String msg,String countryCode) {
		Input input = SMSUtil.buildSystemSms(mobiles, msg, countryCode);
		inputDAO.insert(input);
		// 通知分拣程序 优先级默认低
		QueueUtil.notifySorter(input, Priority.LOW_LEVEL.value());
	}

	// 根据主键查询收件箱
	@Override
	public Inbox queryInboxByPrimaryKey(Integer integer) {
		return inboxDAO.selectByPrimaryKey(integer);
	}

	// 批量补发短信
	@Override
	public void resendInputLog(InputLogExt inputLogExt, String subCode, String enterpriseNo, Integer enterpriseUserId) {
		if (ObjectUtils.isEmpty(inputLogExt)) {
			return;
		}
		int maxResend = DatabaseCache.getIntValueBySortCodeAndCode("sys_performance_setup",
				"max_resend_total", 20000);
		InputLogExample example = assemblyInputLogListConditon(inputLogExt);
		List<InputLogExt> beanList = this.inputLogExtDAO.selectExtByExample(example);
		if (beanList.size() > maxResend) {
			throw new  ServiceException("超过最大重发数限制");
		}
		if (ObjectUtils.isEmpty(beanList)) {
			return;
		}
		beanList.forEach(item -> {
			noticeManage.reSendSms(item.getPhone_Nos(), item.getContent(), enterpriseNo, enterpriseUserId, subCode);
		});
	}

	@Override
	public List<Input> queryTimerInputList(InputExt inputExt) {
		InputExample example = new InputExample();
		InputExample.Criteria criteria = example.createCriteria();
		criteria.andSend_TimeIsNotNull();
		if (!StringUtils.isEmpty(inputExt.getEnterprise_No())) {
			criteria.andEnterprise_NoEqualTo(inputExt.getEnterprise_No());
		}
		if (inputExt.getEnterprise_User_Id() != null) {
			criteria.andEnterprise_User_IdEqualTo(inputExt.getEnterprise_User_Id());
		}
		if (!StringUtils.isEmpty(inputExt.getAgent_No())) {
			criteria.andAgent_NoEqualTo(inputExt.getAgent_No());
		}
		if (!StringUtils.isEmpty(inputExt.getPhone_Nos())) {
			criteria.andPhone_NosLike("%" + inputExt.getPhone_Nos() + "%");
		}
		if (!StringUtils.isEmpty(inputExt.getMessage_Type_Code())) {
			criteria.andMessage_Type_CodeEqualTo(inputExt.getMessage_Type_Code());
		}
		if (inputExt.getId() != null) {
			criteria.andIdEqualTo(inputExt.getId());
		}
		example.setOrderByClause(" id desc");
		example.setPagination(inputExt.getPagination());
		return inputDAO.selectByExamplePage(example);
	}

    @Override
    public List<SubmitExt> querySubmitUnknownList(SubmitExt condition) {
        checkTimes(condition);
        List<SubmitExt> beanList = new ArrayList<>();
		int count = this.submitExtDAO.querySubmitUnknownListCount(condition);
		condition.getPagination().setTotalCount(count);
		if(count > 0){
			beanList = this.submitExtDAO.querySubmitUnknownList(condition);
		}

        return beanList;
    }

    @Override
    public void exportSubmitReportUnknownList(String path, SubmitExt submitExt, ExportFileExt exportFile, String comeFlag) {
        checkTimes(submitExt);
        new Thread(() -> {
            int pageSize = DatabaseCache.getIntValueBySortCodeAndCode("sys_performance_setup", "export_file_size",5000);
            Pagination firstPage = new Pagination(1, pageSize);
            List<Map<String, Object>> dataList;
            exportFile.setBatch_Id(CodeUtil.buildMsgNo());
            while (true) {
                submitExt.setPagination(firstPage);
                dataList = querySubmitReportUnknownListForExportPage(submitExt);
                if (dataList == null || dataList.isEmpty()) {
					break;
				}
                exportSubmitReportUnknownListExcel(path, dataList, exportFile,comeFlag);
                if (firstPage.getPageIndex() == firstPage.getPageCount()) {
					break;
				}
                firstPage = new Pagination(firstPage.getPageIndex() + 1, pageSize);
            }
        }).start();
    }


	public List<Map<String, Object>> querySubmitReportUnknownListForExportPage(SubmitExt condition) {
        return this.submitExtDAO.querySubmitReportUnknownListForExportPage(condition);
    }
	@Override
	public void exportSmsTemplate(final String baseExportBasePath, final SmsTemplateExt bean, final ExportFileExt exportFile) {
		new Thread() {
			@Override
			public void run() {
				int pageSize = DatabaseCache.getIntValueBySortCodeAndCode("sys_performance_setup", "export_file_size",5000);
				Pagination firstPage = new Pagination(1, pageSize);
				List<Map<String, Object>> beanList = null;
				exportFile.setBatch_Id(CodeUtil.buildMsgNo());
				while (true) {
					bean.setPagination(firstPage);
					beanList = querySmsTemplateListForExportPage(bean);
					if (beanList == null || beanList.isEmpty()) {
						break;
					}
					exportSmsTemplateExcel(baseExportBasePath, beanList, exportFile);
					if (firstPage.getPageIndex() == firstPage.getPageCount()) {
						break;
					}
					firstPage = new Pagination(firstPage.getPageIndex() + 1, pageSize);
				}
			}
		}.start();
	}
	@Override
	public List<Map<String, Object>> querySmsTemplateListForExportPage(SmsTemplateExt condition) {
		return this.smsTemplateExtDAO.querySmsTemplateListForExportPage(condition);
	}
	@Override
	public Map<String ,Object> importExcelContact(MultipartFile file, ContactExt contactExt) throws Exception{
        Map<String,Object> result = new HashMap<>();
        List<String> failList = new ArrayList<>();//失败号码
        List<Contact> successList = new ArrayList<>();//成功号码
		ExcelReadTemplate excel = new ExcelReadTemplate(file) {
			private Contact contact = null;

			@Override
			protected void readSheetEvent(Sheet sheet) {
			}

			@Override
			protected void readRowBeforeEvent(Sheet sheet, Row row) {
				contact = (ContactExt) contactExt.clone();
				contact.setId(null);
			}

			@Override
			protected void readCellEvent(Sheet sheet, Row row, Cell cell, String cellName) {
				SuperLogger.debug("行号:" + row.getRowNum());
				if (row.getRowNum() == 0) {
					return;
				}
				if (cell == null) {
					return;
				}
				if (cell.getColumnIndex() == 0) {
                    String phoneNo = getCellValue(cell);
                    if(!phoneNo.matches("1[0-9]{10}")){
                        failList.add(phoneNo);
                        return;
                    }
                    contact.setPhone_No(phoneNo);
                    contact.setReal_Name(getStringValve(row,1));
                    contact.setGender(getStringValve(row,2));
                    if(row.getCell(3) != null && "NUMERIC".equals(row.getCell(3).getCellType().name())){
                        contact.setBirthday(row.getCell(3).getDateCellValue());
                    }
                    contact.setAddress(getStringValve(row,4));
                    contact.setCompany(getStringValve(row,5));
                    contact.setPosition(getStringValve(row,6));
                    contact.setCreate_Date(new Date());
                    successList.add(contact);
                }
			}

            private String getStringValve(Row row, int i) {
                Cell cell = row.getCell(i);
                if (cell == null){
                    return "";
                }
                if (cell.getCellType() == CellType.NUMERIC) {
                    Double d = row.getCell(i).getNumericCellValue();
                    DecimalFormat df = new DecimalFormat("#");
                    return df.format(d);
                }
                if (cell.getCellType() == CellType.STRING) {
                    return replaceSpecialStr(row.getCell(i).getStringCellValue());
                }
                if (cell.getCellType() == CellType.FORMULA) {
                    return row.getCell(i).getStringCellValue();
                }
                return row.getCell(i).getRichStringCellValue().getString();
            }

            @Override
			protected void readRowAfterEvent(Sheet sheet, Row row) throws Exception {
			}
		};
		excel.setStartRowNum(1);
		excel.setFileName(file.getOriginalFilename());
		excel.read();
        result.put("successTotal",successList.size());
        result.put("failTotal",failList.size());
        result.put("failList",failList);
        if(successList.size()>0){
            ListUtils.partition(successList, Constant.INSERT_MAX_LENGTH).forEach(sub -> {
                contactDAO.insertList(sub);
            });
        }
		return result;
	}

	@Override
	public Map<String, String> uploadSendFile(HttpServletRequest request,Integer userId) throws Exception{
		Map<String, String> map = new HashMap<>();
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = multipartRequest.getFile("file");
        BOMInputStream bomIn = null;
        InputStreamReader isr = null;
        BufferedReader reader = null;
        String charset = "GBK";
        try {
            //可检测多种类型，并剔除bom
            bomIn = new BOMInputStream(file.getInputStream(), false, ByteOrderMark.UTF_8, ByteOrderMark.UTF_16LE, ByteOrderMark.UTF_16BE);
            //若检测到bom，则使用bom对应的编码
            if(bomIn.hasBOM()){
                charset = bomIn.getBOMCharsetName();
            }
            isr = new InputStreamReader(file.getInputStream(), charset);
            reader = new BufferedReader(isr);
            String phoneNoLine = "";
            List<String> phoneNoList = new ArrayList<>();
            while ((phoneNoLine = reader.readLine()) != null) {
                if (StringUtils.isEmpty(phoneNoLine)) {
                    continue;
                }
                String[] phoneNos = phoneNoLine.split(Constant.MUTL_MOBILE_SPLIT);
                for (String phoneNo : phoneNos) {
                    if (StringUtils.isEmpty(phoneNo)) {
                        continue;
                    }
                    if(phoneNo.startsWith(BeanUtil.UFEFF)){
                        phoneNo = phoneNo.replaceAll(BeanUtil.UFEFF, "");
                    }
                    phoneNoList.add(phoneNo);
                }
            }
            map = UploadUtil.uploadFile(file,"sendFile"+ File.separator+ userId);
            map.put("mobileCount", phoneNoList.size()+"");
            map.put("fileName", file.getOriginalFilename());
        } catch (Exception e) {
            SuperLogger.error(e.getMessage(), e);
        }finally {
            bomIn.close();
            isr.close();
            reader.close();
        }
		return map;
	}

	@Override
	public List<Report> queryReportListBySubmit (SubmitExt condition) {
		ReportExample example = new ReportExample();
		ReportExample.Criteria cri = example.createCriteria();
		if (StringUtils.isNotBlank(condition.getChannel_Msg_Id())) {
			cri.andChannel_Msg_IdEqualTo(condition.getChannel_Msg_Id());
		}else{
			cri.andChannel_Msg_IdIsNull();
		}
		if (condition.getMinSubmitDate() != null) {
			cri.andSubmit_DateBetween(condition.getMinSubmitDate(), condition.getMaxSubmitDate());
		}
		if (StringUtils.isNotBlank(condition.getMsg_Batch_No())) {
			cri.andMsg_Batch_NoEqualTo(condition.getMsg_Batch_No());
		}else{
			cri.andMsg_Batch_NoIsNull();
		}
		if (StringUtils.isNotBlank(condition.getPhone_No())) {
			cri.andPhone_NoEqualTo(condition.getPhone_No());
		}else{
			cri.andPhone_NoIsNull();
		}
		return this.reportExtDAO.selectByExample(example);
	}

	@Override
	public List<Submit> querySubmitByPrimaryKey(SubmitExt submitExt) {
		SubmitExample example = new SubmitExample();
		SubmitExample.Criteria cri = example.createCriteria();
		cri.andIdEqualTo(submitExt.getId());
		if (StringUtils.isNotBlank(submitExt.getAgent_No())) {
			cri.andAgent_NoEqualTo(submitExt.getAgent_No());
		}
		if (submitExt.getMinSubmitDate() != null) {
			cri.andSubmit_DateBetween(submitExt.getMinSubmitDate(), submitExt.getMaxSubmitDate());
		}
		if (StringUtils.isNotBlank(submitExt.getEnterprise_No())) {
			cri.andEnterprise_NoEqualTo(submitExt.getEnterprise_No());
		}
		if (submitExt.getEnterprise_User_Id() != null) {
			cri.andEnterprise_User_IdEqualTo(submitExt.getEnterprise_User_Id());
		}
		return this.submitExtDAO.selectByExample(example);
	}

	@Override
	public SqlStatisticsEntity querySubmitedListTotalData(SubmitExt condition) {
		int timeInterval = DatabaseCache.getIntValueBySortCodeAndCode("sys_performance_setup", "header_statistics_time_interval", 7200);
		List<DateTime.DateInterVal> dateList = DateTime.getIntervalDate(timeInterval,condition.getMinSubmitDate(), condition.getMaxSubmitDate());
		List<SqlStatisticsEntity> resultList = new ArrayList<SqlStatisticsEntity>();
		for(int i = 0; i < dateList.size(); i++){
			SubmitExample example = null;
			DateTime.DateInterVal dateInterVal = dateList.get(i);
			condition.setMinSubmitDate(dateInterVal.getBegin());
			condition.setMaxSubmitDate(dateInterVal.getEnd());
			example = assemblySubmitListConditon(condition, false);
			if(i == (dateList.size() -1)){
				example = assemblySubmitListConditon(condition, true);
			}
			SqlStatisticsEntity entity = submitExtDAO.statisticsExtByExample(example);
			resultList.add(entity);
		}
		SqlStatisticsEntity newEntity = getSubmitSqlStatisticsEntity(resultList);
		return newEntity;
	}

	@Override
    public void testSend(Integer userId, String phoneNos, String content) throws Exception{
        if(userId == null || StringUtils.isEmpty(phoneNos)  || StringUtils.isEmpty(content)){
            throw new ServiceException("必填参数不能为空！");
        }
        String url = getNetWayHttpSubmitUrl();
        Map<String, String> params = getHttpParam(userId);
        String[] phoneNoArray = phoneNos.split(Constant.MUTL_MOBILE_SPLIT);
        String[] contentArray = content.split("\r\n|\n");
        if(phoneNoArray.length == 0 || contentArray.length == 0){
            throw new ServiceException("必填参数不能为空！");
        }
        int index = 0;
        for (String str : contentArray) {
            index = getPhoneNoIndex(phoneNoArray,index);
            params.put("phones", phoneNoArray[index]);
            params.put("content", str);
            if(!SMSUtil.sendSms(url, params, "utf-8")){
                params.put("url",url);
                throw new ServiceException("短信发送失败："+params);
            }
            index ++;
        }
    }

    @Override
    public void testSendFile(Integer userId, MultipartFile file) throws Exception{
        if(userId == null || file == null){
            throw new ServiceException("必填参数不能为空！");
        }

        List<Input> inputList = new ArrayList<>();
        ExcelReadTemplate excel = new ExcelReadTemplate(file) {
            private Input input = null;

            @Override
            protected void readSheetEvent(Sheet sheet) {
            }

            @Override
            protected void readRowBeforeEvent(Sheet sheet, Row row) {
                input = new Input();
            }

            @Override
            protected void readCellEvent(Sheet sheet, Row row, Cell cell, String cellName) {
                SuperLogger.debug("行号:" + row.getRowNum());
                if (row.getRowNum() == 0) {
                    return;
                }
                if (cell == null) {
                    return;
                }
                if (cell.getColumnIndex() == 0) {
                    String phoneNo = getCellValue(cell);
                    input.setPhone_Nos(phoneNo);
                    input.setContent(getStringValve(row,1));
                    inputList.add(input);
                }
            }

            private String getStringValve(Row row, int i) {
                Cell cell = row.getCell(i);
                if (cell == null){
                    return "";
                }
                if (cell.getCellType() == CellType.STRING) {
                    return replaceSpecialStr(row.getCell(i).getStringCellValue());
                }
                if (cell.getCellType() == CellType.FORMULA) {
                    return row.getCell(i).getStringCellValue();
                }
                return row.getCell(i).getRichStringCellValue().getString();
            }

            @Override
            protected void readRowAfterEvent(Sheet sheet, Row row) throws Exception {
            }
        };
        excel.setStartRowNum(1);
        excel.setFileName(file.getOriginalFilename());
        excel.read();
        if(inputList.size()>0){
            Map<String, String> params = getHttpParam(userId);
            executeSMS(params,inputList);
        }
    }

    @Override
    public void exportInboxList(String path, InboxExt inboxExt, ExportFileExt exportFile,String flag) {
        String redisKey = newThreadBefore(Constant.THREAD_TOTAL_EXPORT);//校验
        new Thread(()->{
            int pageSize = DatabaseCache.getIntValueBySortCodeAndCode("sys_performance_setup", "export_file_size",5000);
            Pagination firstPage = new Pagination(1, pageSize);
            List<Map<String, Object>> inboxList = null;
            exportFile.setBatch_Id(CodeUtil.buildMsgNo());
            while (true) {
                inboxExt.setPagination(firstPage);
                inboxList = queryInboxListForExportPage(inboxExt);
                if (inboxList == null || inboxList.isEmpty()) {
					break;
				}
                exportInboxListExcel(path, inboxList, exportFile,flag);
                if (firstPage.getPageIndex() == firstPage.getPageCount()) {
					break;
				}
                firstPage = new Pagination(firstPage.getPageIndex() + 1, pageSize);
            }
            newThreadAfter(redisKey);
        }).start();
    }

	@Override
	public List<ReportNotify> queryReportNotifyListSharding(ReportNotifyExt condition) {
		List<ReportNotifyExample> examples = assemblyReportNotifyListShardingCondition(condition);
		return queryShardingPageList(reportNotifyExtDAO, examples, condition);
	}

	@Override
	public List<SubmitExt> querysendSpeed(SubmitExt condition) {
		if(condition.getMinSubmitDate() == null || condition.getMaxSubmitDate() ==null){
			throw new ServiceException("请选择时间");
		}
		if(!DateUtils.isSameDay(condition.getMinSubmitDate(), condition.getMaxSubmitDate())){
			throw new ServiceException("不可跨天查询");
		}
		SubmitExample example = assemblySubmitListConditon(condition, true);
		example.setPagination(condition.getPagination());
		List<SubmitExt> list = submitExtDAO.selectSendSpeedByExamplePage(example);
		List<SubmitExt> resultList = new ArrayList<SubmitExt>();
		int seconds = DateTime.secondsBetween(DateTime.getString(condition.getMinSubmitDate()), DateTime.getString(condition.getMaxSubmitDate()));
		list.forEach(entity->{
			BigDecimal speed = (new BigDecimal(entity.getCount_Total())
					.divide(new BigDecimal(seconds),2, BigDecimal.ROUND_HALF_UP));
			entity.setSendSpeed(speed);
			resultList.add(entity);
		});
		return resultList;
	}

    /**
     * map 包含字段：
     *      手机上传的数据：
     *          phoneNo         手机号
     *          content         手机收到的内容
     *          spNumber        手机收到的码号
     *          receivingTime   手机收到的时间
     *          uploadTime      上传时间
     *          uploadBatchNo   上传批次号
     *      平台处理的数据：
     *          inputContent    平台下发的内容
     *          isIdentical     收发的短信内容是否一致
     *          submitDate      提交时间
     *          reportDate      回执时间
     * @param list
     */
    @Override
    public void smsVerify(List<Map<String, Object>> list) {
        if(ObjectUtils.isEmpty(list)){
            return;
        }
        new Thread(()->{
            String uploadTime = DateTime.getString();//上传时间，同批次改为相同时间
            String uploadBatchNo = CodeUtil.buildMsgNo();//批次号
            list.forEach(map ->{
                map.put("uploadTime",uploadTime);
                map.put("uploadBatchNo",uploadBatchNo);
                map.put("isIdentical","是");//收发短信是否一致，默认 是
                InputLogExt inputLog = new InputLogExt();
                inputLog.setPhone_Nos(map.get("phoneNo").toString());
                inputLog.setContent(map.get("content").toString());
                String receivingTime = map.get("receivingTime").toString();
                Date minDate = DateTime.getDate(receivingTime.substring(0,10)+" 00:00:00");
                Date maxDate = DateTime.getDate(receivingTime.substring(0,10)+" 23:59:59");
                inputLog.setMinCreateDate(minDate);
                inputLog.setMaxCreateDate(maxDate);
                List<InputLogExt> inputLogList = queryInputLogList(inputLog);
                if(ObjectUtils.isEmpty(inputLogList)){
                    map.put("isIdentical","否");
                    return ;
                }
                inputLog = inputLogList.get(0);
                map.put("inputContent",inputLog.getContent());
                ReportExt report = new ReportExt();
                report.setPhone_No(map.get("phoneNo").toString());
                report.setMsg_Batch_No(inputLog.getMsg_Batch_No());
                report.setMinSubmitDate(minDate);
                report.setMaxSubmitDate(maxDate);
                List<ReportExt> reportList = queryReportExtList(report);
                if(ObjectUtils.isEmpty(reportList)){
                    return ;
                }
                report = reportList.get(0);
                map.put("submitDate",DateTime.getString(report.getSubmit_Date()));
                map.put("reportDate",DateTime.getString(report.getStatus_Date()));
            });
            exportSmsVerify(list);
        }).start();
    }

	@Override
	public List<ReportExt> querySendCountDetailFromReport(ReportExt reportExt) {
		return reportExtDAO.querySendCountDetailFromReport(reportExt);
	}

	@Override
	public List<SubmitExt> querySendCountDetailFromSubmit(SubmitExt submitExt) {
		return submitExtDAO.querySendCountDetailFromSubmit(submitExt);
	}

	public synchronized void exportSmsVerify(List<Map<String, Object>> list) {
        Object[][] titles = new Object[][] { { "phoneNo", "手机号", 4000 }, { "content", "手机收到内容", 6000 },
                { "inputContent", "平台发送内容", 6000 }, { "isIdentical", "内容是否一致", 4000 },
                { "submitDate", "提交时间", 5000 }, { "reportDate", "回执时间", 5000 },
                { "spNumber", "码号", 5000 }, { "receivingTime", "手机收到时间", 5000 },
                { "uploadTime", "上传时间", 5000 },{ "uploadBatchNo", "上传批次", 5000 }};
        String path = DatabaseCache.getStringValueBySystemEnvAndCode("export_dir","");
        String fileName = ExcelUtil.addOrEditExcel(path, "测试结果文件", titles, list);
        ExportFile exportFile = new ExportFile();
        exportFile.setFIle_Name(fileName);
        List<ExportFile> files = queryExportFile(exportFile);
        if(ObjectUtils.isNotEmpty(files)){
            return;
        }
        exportFile.setEnterprise_No("0");
        exportFile.setAgent_No("0");
        exportFile.setUser_Id(0);
        exportFile.setFile_Url(path + File.separator + fileName);
        exportFile.setBatch_Id(CodeUtil.buildMsgNo());
        exportFile.setCreate_Date(new Date());
        exportFileDAO.insert(exportFile);
    }

    @Override
    public String queryAlarmLogDetailList(String bingValveType, String bindValue, String beginDate, String endDate, String alarmType, Pagination pagination) {
        //提交成功率预警 = 提交成功/总的提交数 只查submit表
        if ("submitSuccessAlarm".equals(alarmType)) {
            SubmitExt submitedExt = new SubmitExt();
            submitedExt.setPagination(pagination);
            submitedExt.setMinSubmitDate(DateTime.getDate(beginDate));
            submitedExt.setMaxSubmitDate(DateTime.getDate(endDate));
            if("channel".equals(bingValveType)){
                submitedExt.setChannel_No(bindValue);
            }
            if("product".equals(bingValveType)){
                submitedExt.setProduct_No(bindValue);
            }
            List<SubmitExt> submitedExtList = this.querySubmitListSharding(submitedExt);
            return new SmsUIObjectMapper().asSuccessString(submitedExtList, submitedExt.getPagination());
        }
        //接收成功率预警 = 接收成功/提交成功 只查report表
        if ("receptionSuccessAlarm".equals(alarmType)) {
            ReportExt reportExt = new ReportExt();
            reportExt.setMinSubmitDate(DateTime.getDate(beginDate));
            reportExt.setMaxSubmitDate(DateTime.getDate(endDate));
            reportExt.setPagination(pagination);
            if("channel".equals(bingValveType)){
                reportExt.setChannel_No(bindValue);
            }
            if("product".equals(bingValveType)){
                reportExt.setProduct_No(bindValue);
            }
            List<ReportExt> reportExtList = this.queryReportExtList(reportExt);
            return new SmsUIObjectMapper().asSuccessString(reportExtList, reportExt.getPagination());
        }
        //回执率预警 = (接收失败+接收成功)/提交成功 查未知记录
        if ("returnRateAlarm".equals(alarmType)) {
            SubmitExt submitedExt = new SubmitExt();
            submitedExt.setMinSubmitDate(DateTime.getDate(beginDate));
            submitedExt.setMaxSubmitDate(DateTime.getDate(endDate));
            submitedExt.setPagination(pagination);
            if("channel".equals(bingValveType)){
                submitedExt.setChannel_No(bindValue);
            }
            if("product".equals(bingValveType)){
                submitedExt.setProduct_No(bindValue);
            }
            List<SubmitExt> submitedExtList = this.querySubmitUnknownList(submitedExt);
            return new SmsUIObjectMapper().asSuccessString(submitedExtList, submitedExt.getPagination());
        }
        return null;
    }

	@Override
	public void manualSendMO(ReportExt condition, List<Long> reportIds, String msgContent) {
    	if(StringUtils.isEmpty(msgContent)) {
			throw new ServiceException("上行短信内容不能为空");
		}
		for(Long id : reportIds) {
			condition.setId(id);
			ReportExample example = assemblyReportListConditon(condition, true);
			List<ReportExt> reportExts = reportExtDAO.selectExtByExample(example);
			if(reportExts == null || reportExts.isEmpty()) {
				continue;
			}
			ReportExt reportExt = reportExts.get(0);
			if(!reportExt.getStatus_Code().equals(ReportStatus.SUCCESS.toString())) {
				continue;
			}
			Inbox inbox = new Inbox();
			inbox.setChannel_No(reportExt.getChannel_No());
			inbox.setContent(msgContent);
			//特殊标记为人工生成的上行
			inbox.setSP_Number("000");
			inbox.setPhone_No(reportExt.getPhone_No());
			inbox.setCreate_Date(new Date());
			inbox.setCharset(reportExt.getCharset());//编码

			inbox.setGroup_Code(reportExt.getGroup_Code());
			inbox.setCountry_Code(reportExt.getCountry_Code());
			inbox.setEnterprise_No(StringUtils.defaultIfEmpty(reportExt.getEnterprise_No(), ""));
			inbox.setAgent_No(StringUtils.defaultIfEmpty(reportExt.getAgent_No(), ""));
			inbox.setEnterprise_User_Id(
					ObjectUtils.isEmpty(reportExt.getEnterprise_User_Id()) ? 0 : reportExt.getEnterprise_User_Id());
			inbox.setProtocol_Type_Code(
					StringUtils.defaultIfEmpty(reportExt.getProtocol_Type_Code(), ProtocolType.WEB.toString()));
			inbox.setNotify_Total(0);
			inbox.setPull_Total(0);
			inbox.setSub_Code(StringUtils.defaultString(reportExt.getSub_Code(), ""));
			inbox.setNotify_Status_Code(NotifyStatus.UNNOTIFY.toString());
			inbox.setSender_Local_IP(reportExt.getSender_Local_IP());
			inbox.setInput_Msg_No(reportExt.getMsg_Batch_No());
			inbox.setInput_Sub_Code(reportExt.getInput_Sub_Code());
			inbox.setInput_Create_Date(reportExt.getInput_Log_Date());
			senderSmsService.setInboxInputInfo(reportExt.getMsg_Batch_No(), reportExt.getInput_Log_Date(), inbox);
			inboxDAO.insert(inbox);
			// 未匹配到企业直接返回
			if (inbox.getEnterprise_User_Id() == 0) {
				continue;
			}
		}
	}

	/**
	 * @param userId
	 * @return
	 * @author volcano
	 * @date 2019年9月21日上午4:50:50
	 * @version V1.0
	 */
	@Override
	public List<Inbox> queryNotifyInboxList(Integer userId, Integer pageSize) {
		Pagination pagination;
		if (pageSize == null) {
			pagination = new Pagination(0, 500);
		} else {
			pagination = new Pagination(0, pageSize);
		}

		// 拉取数据
		InboxExample example = new InboxExample();
		InboxExample.Criteria cri = example.createCriteria();
		cri.andEnterprise_User_IdEqualTo(userId);
		cri.andPull_TotalEqualTo(0);
		example.setPagination(pagination);
		List<Inbox> inboxList = this.inboxExtDAO.selectByExample(example);
		// 更新拉取信息
		if (ObjectUtils.isNotEmpty(inboxList)) {
			this.inboxExtDAO.updateInboxList(inboxList);
		}
		return inboxList;
	}

	@Override
	public List<SubmitAwait> querySubmitAwaitList(SubmitAwaitExt condition) {
		SubmitAwaitExample example = new SubmitAwaitExample();
		SubmitAwaitExample.Criteria cri = example.createCriteria();
		if (condition.getBusiness_User_Id() != null) {
			cri.andBusiness_User_IdEqualTo(condition.getBusiness_User_Id());
		}
		if (org.apache.commons.lang.StringUtils.isNotBlank(condition.getPhone_No())) {
			cri.andPhone_NoEqualTo(condition.getPhone_No());
		}
		if (condition.getMinCreateDate() == null) {
			condition.setMinCreateDate(DateTime.getDate(DateTime.getCurrentDayMinDate()));
		}
		if (condition.getMaxCreateDate() == null) {
			condition.setMaxCreateDate(DateTime.getDate(DateTime.getCurrentDayMaxDate()));
		}
		cri.andCreate_DateBetween(condition.getMinCreateDate(), condition.getMaxCreateDate());
		if (org.apache.commons.lang.StringUtils.isNotBlank(condition.getEnterprise_No())) {
			cri.andEnterprise_NoEqualTo(condition.getEnterprise_No());
		}
		if (condition.getEnterprise_User_Id() != null) {
			cri.andEnterprise_User_IdEqualTo(condition.getEnterprise_User_Id());
		}
		if (org.apache.commons.lang.StringUtils.isNotBlank(condition.getChannel_No())) {
			cri.andChannel_NoEqualTo(condition.getChannel_No());
		}
		if (!org.apache.commons.lang.StringUtils.isEmpty(condition.getContent())) {
			cri.andContentLike("%" + condition.getContent() + "%");
		}
		if (org.apache.commons.lang.StringUtils.isNotBlank(condition.getCountry_Number())) {
			cri.andCountry_NumberEqualTo(condition.getCountry_Number());
		}
		if (org.apache.commons.lang.StringUtils.isNotBlank(condition.getOperator())) {
			cri.andOperatorEqualTo(condition.getOperator());
		}
		if (org.apache.commons.lang.StringUtils.isNotBlank(condition.getMessage_Type_Code())) {
			cri.andMessage_Type_CodeEqualTo(condition.getMessage_Type_Code());
		}
		if (org.apache.commons.lang.StringUtils.isNotBlank(condition.getProvince_Code())) {
			cri.andProvince_CodeEqualTo(condition.getProvince_Code());
		}
		example.setPagination(condition.getPagination());
		return submitAwaitDAO.selectByExamplePage(example);
	}

	@Override
	public List<ReportNotifyAwait> queryReportNotifyAwaitList(ReportNotifyAwaitExt condition) {
		ReportNotifyAwaitExample example = new ReportNotifyAwaitExample();
		ReportNotifyAwaitExample.Criteria cri = example.createCriteria();
		if (condition.getBusiness_User_Id() != null) {
			cri.andBusiness_User_IdEqualTo(condition.getBusiness_User_Id());
		}
		if (org.apache.commons.lang.StringUtils.isNotBlank(condition.getPhone_No())) {
			cri.andPhone_NoEqualTo(condition.getPhone_No());
		}
		if(condition.getMinSubmitDate() != null){
			cri.andSubmit_DateGreaterThanOrEqualTo(condition.getMinSubmitDate());
		}
		if(condition.getMaxSubmitDate() != null){
			cri.andSubmit_DateLessThanOrEqualTo(condition.getMaxSubmitDate());
		}
		if (org.apache.commons.lang.StringUtils.isNotBlank(condition.getMsg_Batch_No())) {
			cri.andMsg_Batch_NoEqualTo(condition.getMsg_Batch_No());
		}
		if (org.apache.commons.lang.StringUtils.isNotBlank(condition.getMessage_Type_Code())) {
			cri.andMessage_Type_CodeEqualTo(condition.getMessage_Type_Code());
		}
		if (org.apache.commons.lang.StringUtils.isNotBlank(condition.getAgent_No())) {
			cri.andAgent_NoEqualTo(condition.getAgent_No());
		}
		if (org.apache.commons.lang.StringUtils.isNotBlank(condition.getContent())) {
			cri.andContentLike("%" + condition.getContent() + "%");
		}
		if (org.apache.commons.lang.StringUtils.isNotBlank(condition.getEnterprise_No())) {
			cri.andEnterprise_NoEqualTo(condition.getEnterprise_No());
		}
		if (org.apache.commons.lang.StringUtils.isNotBlank(condition.getChannel_No())) {
			cri.andChannel_NoEqualTo(condition.getChannel_No());
		}
		if (condition.getEnterprise_User_Id() != null) {
			cri.andEnterprise_User_IdEqualTo(condition.getEnterprise_User_Id());
		}
		if (org.apache.commons.lang.StringUtils.isNotBlank(condition.getCountry_Number())) {
			cri.andCountry_NumberEqualTo(condition.getCountry_Number());
		}
		if (org.apache.commons.lang.StringUtils.isNotBlank(condition.getOperator())) {
			cri.andOperatorEqualTo(condition.getOperator());
		}
		if (org.apache.commons.lang.StringUtils.isNotBlank(condition.getSubmit_Status_Code())) {
			cri.andSubmit_Status_CodeEqualTo(condition.getSubmit_Status_Code());
		}
		if (org.apache.commons.lang.StringUtils.isNotBlank(condition.getChannel_Msg_Id())) {
			cri.andChannel_Msg_IdEqualTo(condition.getChannel_Msg_Id());
		}
		if (org.apache.commons.lang.StringUtils.isNotBlank(condition.getStatus_Code())) {
			cri.andStatus_CodeEqualTo(condition.getStatus_Code());
		}
		if (org.apache.commons.lang.StringUtils.isNotBlank(condition.getNative_Status())) {
			cri.andNative_StatusEqualTo(condition.getNative_Status());
		}
		if (org.apache.commons.lang.StringUtils.isNotBlank(condition.getSignature())) {
			cri.andSignatureEqualTo(condition.getSignature());
		}
		if (org.apache.commons.lang.StringUtils.isNotBlank(condition.getProvince_Code())) {
			cri.andProvince_CodeEqualTo(condition.getProvince_Code());
		}
		example.setPagination(condition.getPagination());
		return reportNotifyAwaitDAO.selectByExamplePage(example);
	}
}
