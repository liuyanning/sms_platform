package com.hero.wireless.web.service.base;

import com.drondea.wireless.config.Constant;
import com.drondea.wireless.config.ResultStatus;
import com.drondea.wireless.util.DateTime;
import com.drondea.wireless.util.RandomUtil;
import com.drondea.wireless.util.SecretUtil;
import com.drondea.wireless.util.ServiceException;
import com.hero.wireless.enums.*;
import com.hero.wireless.json.JsonUtil;
import com.hero.wireless.web.config.DatabaseCache;
import com.hero.wireless.web.config.SystemKey;
import com.hero.wireless.web.dao.business.*;
import com.hero.wireless.web.dao.business.ext.IEnterpriseExtDAO;
import com.hero.wireless.web.dao.business.ext.IEnterpriseLimitExtDAO;
import com.hero.wireless.web.dao.business.ext.IEnterpriseRoleExtDAO;
import com.hero.wireless.web.dao.business.ext.IEnterpriseUserExtDAO;
import com.hero.wireless.web.entity.business.*;
import com.hero.wireless.web.entity.business.ext.EnterpriseExt;
import com.hero.wireless.web.entity.business.ext.EnterpriseUserExt;
import com.hero.wireless.web.entity.business.ext.ExportFileExt;
import com.hero.wireless.web.util.ChannelUtil.LimitRepeat;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ObjectUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.hero.wireless.web.config.MessagesManger.getSystemMessages;

public class BaseEnterpriseManage extends BaseService {

	@Resource(name = "IEnterpriseDAO")
	protected IEnterpriseDAO<Enterprise> enterpriseDAO;
	@Resource(name = "IEnterpriseUserDAO")
	protected IEnterpriseUserDAO<EnterpriseUser> enterpriseUserDAO;
	@Resource(name = "IEnterpriseLimitDAO")
	protected IEnterpriseLimitDAO<EnterpriseLimit> enterpriseLimitDAO;
	@Resource(name = "IEnterpriseRoleDAO")
	protected IEnterpriseRoleDAO<EnterpriseRole> enterpriseRoleDAO;
	@Resource(name = "enterpriseLimitExtDAO")
	protected IEnterpriseLimitExtDAO enterpriseLimitExtDAO;
	@Resource(name = "enterpriseRoleExtDAO")
	protected IEnterpriseRoleExtDAO enterpriseRoleExtDAO;
	@Resource(name = "IEnterpriseUserRolesDAO")
	protected IEnterpriseUserRolesDAO<EnterpriseUserRoles> enterpriseUserRolesDAO;
	@Resource(name = "IEnterpriseRoleLimitDAO")
	protected IEnterpriseRoleLimitDAO<EnterpriseRoleLimit> enterpriseRoleLimitDAO;
	@Resource(name = "enterpriseExtDAO")
	protected IEnterpriseExtDAO enterpriseExtDAO;
	@Resource(name = "enterpriseUserExtDAO")
	protected IEnterpriseUserExtDAO enterpriseUserExtDAO;
	@Resource(name = "ISmsTemplateDAO")
	protected ISmsTemplateDAO<SmsTemplate> smsTemplateDAO;
	@Resource(name = "IEnterpriseUserFeeDAO")
	protected IEnterpriseUserFeeDAO<EnterpriseUserFee> enterpriseUserFeeDAO;
	@Resource(name = "ISystemLogDAO")
	protected ISystemLogDAO<SystemLog> systemLogDAO;
    @Resource(name = "IPropertiesDAO")
    protected IPropertiesDAO<Properties> propertiesDAO;

	protected void doCheckUserName(EnterpriseUserExt userInfo) {
		EnterpriseUserExample example = new EnterpriseUserExample();
		example.createCriteria().andUser_NameEqualTo(userInfo.getUser_Name());
		List<EnterpriseUser> list = enterpriseUserDAO.selectByExample(example);
		if (list != null && !list.isEmpty()) {
			throw new ServiceException(ResultStatus.USER_NAME_EXSITS);
		}
	}

	protected void doAddUserDefaultValue(EnterpriseUserExt userInfo) {
		userInfo.setStatus(getSystemMessages(SystemKey.USER_STATUS_NORMAL));
		userInfo.setAvailable_Amount(BigDecimal.ZERO);// 可用余额
		userInfo.setUsed_Amount(BigDecimal.ZERO);// 已用余额
		userInfo.setSent_Count(0);// 已发短信
		userInfo.setCreate_Date(new Date());
		userInfo.setWeb_Password(SecretUtil.MD5(userInfo.getPassword()));// MD5加密
		String httpSignKey = RandomUtil.randomStr(16) + DateTime.getString(new Date(), DateTime.Y_M_D_H_M_S_S_2);
		userInfo.setHttp_Sign_Key(SecretUtil.MD5(httpSignKey));
		userInfo.setTcp_Password(RandomUtil.randomStr(6));
		if (userInfo.getPriority_Level() == null) {
			userInfo.setPriority_Level(Priority.MIDDLE_LEVEL.value());// 优先级
		}
		// userInfo.setIs_Audit_Refund(true);//默认需要审核
		userInfo.setIs_Notify_Report(false);
		userInfo.setIs_Query_Report(false);
		userInfo.setFee_Type_Code(FeeType.SUBMIT.toString());// 计费类型
		if(userInfo.getAudit_Type_Code() == null){
			userInfo.setAudit_Type_Code(ContentAuditType.NO_AUDIT.toString());// 审核类型
		}
		userInfo.setNumber_Of_Audits(0);
		userInfo.setReturn_Unknown_Rate(0.00f);
		userInfo.setSuffix("");// 后缀
		userInfo.setLogin_Faild_Count(0);
		userInfo.setLogin_Faild_Lock_Count(5);
		// if(StringUtils.isEmpty(userInfo.getReal_Name())){
		// userInfo.setReal_Name(userInfo.getUser_Name());
		// }
		LimitRepeat limitRepeat = new LimitRepeat();
		// 同内容默认，10分钟1条
		limitRepeat.getContent().put(10, 1);
		// 同手机号，1分钟3条
		limitRepeat.getPhoneNos().put(1, 3);
		userInfo.setLimit_Repeat(JsonUtil.writeValueAsString(limitRepeat));
	}

	protected void doBindRole(EnterpriseUserExt userInfo) {
		// 插入默认角色
		EnterpriseRoleExample roleExample = new EnterpriseRoleExample();
		EnterpriseRoleExample.Criteria cri = roleExample.createCriteria();
		// 如果是自助注册用户绑定新用户角色
		if (DataSourceType.REGISTERED.toString().equals(userInfo.getDataSource())) {
			cri.andCodeEqualTo(Constant.NEWUSER_ROLE_CODE);
		} else {
			cri.andCodeEqualTo(Constant.DEFAULT_ROLE_CODE);
		}
		List<EnterpriseRole> enterpriseRoleList = this.enterpriseRoleDAO.selectByExample(roleExample);
		if (enterpriseRoleList != null && !enterpriseRoleList.isEmpty()) {
			EnterpriseUserRoles enterpriseUserRoles = new EnterpriseUserRoles();
			enterpriseUserRoles.setCreate_Date(new Date());
			enterpriseUserRoles.setEnterprise_User_Id(userInfo.getId());
			enterpriseUserRoles.setRole_Id(enterpriseRoleList.get(0).getId());
			this.enterpriseUserRolesDAO.insert(enterpriseUserRoles);
		}
	}

	protected EnterpriseExample assemblyEnterpriseConditon(EnterpriseExt condition) {
		EnterpriseExample example = new EnterpriseExample();
		EnterpriseExample.Criteria cri = example.createCriteria();
		cri.andStatusNotEqualTo(AccountStatus.DELETE.toString());
		if (!StringUtils.isEmpty(condition.getName())) {
			cri.andNameLike("%" + condition.getName() + "%");
		}
		if (condition.getBusiness_User_Id() != null) {
			cri.andBusiness_User_IdEqualTo(condition.getBusiness_User_Id());
		}
		if (condition.getId() != null) {
			cri.andIdEqualTo(condition.getId());
		}
		if (!StringUtils.isEmpty(condition.getStatus())) {
			cri.andStatusEqualTo(condition.getStatus());
		}
		if (!StringUtils.isEmpty(condition.getNo())) {
			cri.andNoEqualTo(condition.getNo());
		}
		if (!StringUtils.isEmpty(condition.getAgent_No())) {
			cri.andAgent_NoEqualTo(condition.getAgent_No());
		}
		if (!StringUtils.isEmpty(condition.getAuthentication_State_Code())) {
			cri.andAuthentication_State_CodeEqualTo(condition.getAuthentication_State_Code());
		}
		if (!StringUtils.isEmpty(condition.getDomain())) {
			cri.andDomainEqualTo(condition.getDomain());
		}
		if (condition.getMin_Available_Money() != null) {
			cri.andAvailable_AmountGreaterThanOrEqualTo(condition.getMin_Available_Money());
		}
		if (condition.getMax_Available_Money() != null) {
			cri.andAvailable_AmountLessThanOrEqualTo(condition.getMax_Available_Money());
		}
		if (condition.getCreate_Date() != null) {
			cri.andCreate_DateEqualTo(condition.getCreate_Date());
		}
		example.setOrderByClause(" id desc");
		example.setPagination(condition.getPagination());
		return example;
	}

	protected List<Map<String, Object>> queryEnterpriseListForExportPage(EnterpriseExt bean) {
		EnterpriseExample example = assemblyEnterpriseConditon(bean);
		return this.enterpriseExtDAO.queryEnterpriseListForExportPage(example);
	}

	protected void exportEnterpriseExcel(List<Map<String, Object>> beanList, ExportFileExt exportFile) {
		Object[][] titles = new Object[][] { { "Name", "企业名称", 4000 }, { "No", "企业编号", 4000 },
				{ "StatusName", "状态", 3000 },
				{ "AuthenticationStateCodeName", "认证状态", 3000 }, { "Used_Amount", "已消费", 4000 },
				{ "Available_Amount", "余额", 4000 }, { "Sent_Count", "发送条数", 4000 }, { "Description", "描述", 6000 },
				{ "Remark", "备注", 6000 }, { "Create_Date", "创建时间", 6000 } };
		String path = DatabaseCache.getCodeBySortCodeAndCode("system_env", "export_dir").getValue();
		exportAndInsert(exportFile, path, "企业列表导出", titles, beanList);// 导出数据
	}

	protected List<Map<String, Object>> queryEnterpriseUserListForExportPage(EnterpriseUserExt bean) {
		EnterpriseUserExample example = assemblyEnterpriseUserConditon(bean);
		return this.enterpriseUserExtDAO.queryEnterpriseUserListForExportPage(example);
	}

	protected EnterpriseUserExample assemblyEnterpriseUserConditon(EnterpriseUserExt condition) {
		EnterpriseUserExample example = new EnterpriseUserExample();
		EnterpriseUserExample.Criteria cri = example.createCriteria();
		cri.andStatusNotEqualTo(AccountStatus.DELETE.toString());
		if (!StringUtils.isEmpty(condition.getEnterprise_No())) {
			cri.andEnterprise_NoEqualTo(condition.getEnterprise_No());
		}
		if (!StringUtils.isEmpty(condition.getProduct_No())) {
			cri.andProduct_NoEqualTo(condition.getProduct_No());
		}
		if (!StringUtils.isEmpty(condition.getSuffix())) {
			cri.andSuffixEqualTo(condition.getSuffix());
		}
		if (!StringUtils.isEmpty(condition.getUser_Name())) {
			cri.andUser_NameEqualTo(condition.getUser_Name());
		}
		if (!StringUtils.isEmpty(condition.getTcp_User_Name())) {
			cri.andTcp_User_NameEqualTo(condition.getTcp_User_Name());
		}
		if (!StringUtils.isEmpty(condition.getStatus())) {
			cri.andStatusEqualTo(condition.getStatus());
		}
		if (condition.getId() != null) {
			cri.andIdEqualTo(condition.getId());
		}
		if (!StringUtils.isEmpty(condition.getReal_Name())) {
			cri.andReal_NameEqualTo(condition.getReal_Name());
		}
		if (condition.getEnterpriseNoList() != null) {
			cri.andEnterprise_NoIn(condition.getEnterpriseNoList());
		}
		if (condition.getCreate_Date() != null) {
			cri.andCreate_DateEqualTo(condition.getCreate_Date());
		}
		example.setPagination(condition.getPagination());
		example.setOrderByClause(" id desc");
		return example;
	}

	protected void exportEnterpriseUserExcel(List<Map<String, Object>> beanList, ExportFileExt exportFile) {
		Object[][] titles = new Object[][] { { "EnterpriseName", "企业名称", 4000 }, { "Real_Name", "用户名称", 4000 },
				{ "User_Name", "登录名", 4000 }, { "tcp_User_Name", "CMPP", 3000 }, { "StatusName", "账号状态", 2000 },
				{ "AuditTypeCodeName", "审核级别", 2000 }, { "Suffix", "签名", 3000 }, { "ProductName", "产品名称", 3000 },
				{ "Used_Amount", "已消费", 4000 }, { "Available_Amount", "余额", 4000 }, { "Sended_Count", "发送条数", 4000 },
				{ "Remark", "备注", 4000 }, { "Create_Date", "创建时间", 6000 } };
		String path = DatabaseCache.getCodeBySortCodeAndCode("system_env", "export_dir").getValue();
		exportAndInsert(exportFile, path, "企业用户导出", titles, beanList);// 导出数据
	}


	protected List<String> getTimeList() {
		List<String> dateList = new ArrayList<>();// 时间轴
		int second = DatabaseCache.getIntValueBySortCodeAndCode("system_env", "user_submit_speed_second", 30);
		DateTimeFormatter fmTime = DateTimeFormatter.ofPattern(DateTime.H_M_S_2);
		for (int i = second; i >= 0; i--) {
			dateList.add(LocalDateTime.now().minusSeconds(i).format(fmTime));
		}
		return dateList;
	}

    //属性配置
    protected void doUserProperties(EnterpriseUser old, EnterpriseUserExt newUser) {
        PropertiesExample example = new PropertiesExample();
        example.createCriteria()
                .andType_CodeEqualTo(PropertiesType.ENTERPRISE_USER.toString())
                .andType_Code_NumEqualTo(String.valueOf(old.getId()));
        List<Properties> list = propertiesDAO.selectByExample(example);
        if(ObjectUtils.isNotEmpty(list)){
            list.forEach(item ->{
                item.setId(null);
                item.setType_Code_Num(String.valueOf(newUser.getId()));
                item.setCreate_Date(new Date());
            });
            propertiesDAO.insertList(list);
        }
    }

    //资费配置
    protected void doUserFee(EnterpriseUser old, EnterpriseUserExt newUser) {
        EnterpriseUserFeeExample example = new EnterpriseUserFeeExample();
        example.createCriteria()
                .andEnterprise_NoEqualTo(old.getEnterprise_No())
                .andEnterprise_User_IdEqualTo(old.getId());
        List<EnterpriseUserFee> list = enterpriseUserFeeDAO.selectByExample(example);
        if(ObjectUtils.isNotEmpty(list)){
            list.forEach(item ->{
                item.setId(null);
                item.setEnterprise_User_Id(newUser.getId());
                item.setCreate_Date(new Date());
            });
            enterpriseUserFeeDAO.insertList(list);
        }
    }

    //字段
    protected void doUserField(EnterpriseUser old, EnterpriseUserExt newUser) {
        //修改
        if(StringUtils.isEmpty(newUser.getTrade_Type_Code())){
            newUser.setTrade_Type_Code(old.getTrade_Type_Code());
        }
        //限制配置
        newUser.setLimit_Repeat(old.getLimit_Repeat());
        //API配置
        newUser.setTcp_Max_Channel(old.getTcp_Max_Channel());
        newUser.setTcp_Submit_Speed(old.getTcp_Submit_Speed());
        newUser.setApi_Ip(old.getApi_Ip());
        //短信配置
        newUser.setProduct_No(old.getProduct_No());
        newUser.setPriority_Level(old.getPriority_Level());
        newUser.setSuffix(old.getSuffix());
        newUser.setFee_Type_Code(old.getFee_Type_Code());
        newUser.setSettlement_Type_Code(old.getSettlement_Type_Code());
        newUser.setNumber_Of_Audits(old.getNumber_Of_Audits());
        newUser.setAudit_Type_Code(old.getAudit_Type_Code());
        newUser.setReturn_Unknown_Rate(old.getReturn_Unknown_Rate());
        newUser.setSub_Code(old.getSub_Code());
        newUser.setMo_Sp_Type_Code(old.getMo_Sp_Type_Code());
        newUser.setBackup_Product_No(old.getBackup_Product_No());
        //回调配置
        newUser.setIs_Query_Report(old.getIs_Query_Report());
        newUser.setIs_Notify_Report(old.getIs_Notify_Report());
        newUser.setNotify_Report_Url(old.getNotify_Report_Url());
        newUser.setNotify_Mo_Url(old.getNotify_Mo_Url());
        enterpriseUserDAO.updateByPrimaryKeySelective(newUser);
    }

}
