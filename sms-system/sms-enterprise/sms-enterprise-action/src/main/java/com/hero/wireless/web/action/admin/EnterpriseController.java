package com.hero.wireless.web.action.admin;

import com.drondea.wireless.config.Constant;
import com.drondea.wireless.config.ResultStatus;
import com.drondea.wireless.util.CertificateUtil;
import com.drondea.wireless.util.RandomUtil;
import com.drondea.wireless.util.ServiceException;
import com.drondea.wireless.util.SuperLogger;
import com.hero.wireless.enums.AuditStatus;
import com.hero.wireless.enums.AuthCode;
import com.hero.wireless.enums.DataSourceType;
import com.hero.wireless.json.LayUiJsonObjectFmt;
import com.hero.wireless.json.LayUiObjectMapper;
import com.hero.wireless.json.LayuiResultUtil;
import com.hero.wireless.json.SmsUIObjectMapper;
import com.hero.wireless.web.action.BaseEnterpriseController;
import com.hero.wireless.web.action.entity.BaseParamEntity;
import com.hero.wireless.web.action.interceptor.AvoidRepeatableCommitAnnotation;
import com.hero.wireless.web.config.DatabaseCache;
import com.hero.wireless.web.entity.base.Pagination;
import com.hero.wireless.web.entity.business.*;
import com.hero.wireless.web.entity.business.ext.EnterpriseExt;
import com.hero.wireless.web.entity.business.ext.EnterpriseUserExt;
import com.hero.wireless.web.entity.business.ext.SmsTemplateExt;
import com.hero.wireless.web.service.IChargeManage;
import com.hero.wireless.web.service.IEnterpriseManage;
import com.hero.wireless.web.service.ISendManage;
import com.hero.wireless.web.util.GlobalStringCache;
import com.hero.wireless.web.util.UploadUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 企业Action
 *
 * @author Administrator
 */
@RequestMapping("/admin/")
@Controller
public class EnterpriseController extends BaseEnterpriseController {
    /**
     *
     */
    @Resource(name = "enterpriseManage")
    private IEnterpriseManage enterpriseManage;
    @Resource(name = "sendManage")
    private ISendManage sendManage;
    @Resource(name = "chargeManage")
    private IChargeManage iChargeManage;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setDisallowedFields("name");
    }

    @RequestMapping("enterprise_editPassword")
    @ResponseBody
    public LayUiJsonObjectFmt editPassword(String cp_newPassword, String oldPassword) {
        try {
            // rsa解密
            cp_newPassword = getPlainPassword(cp_newPassword);
            oldPassword = getPlainPassword(oldPassword);

            EnterpriseUser enterpriseUser = new EnterpriseUser();
            enterpriseUser.setId(getUserId());
            enterpriseUser.setUser_Name(getLoginEnterpriseUser().getUser_Name());
            enterpriseUser.setWeb_Password(cp_newPassword);
            enterpriseManage.editPassword(enterpriseUser, oldPassword);
        } catch (ServiceException be) {
            return LayuiResultUtil.fail(be.getMessage());
        } catch (Exception be) {
            return LayuiResultUtil.fail(be.getMessage());
        }
        return LayuiResultUtil.success();
    }

    @RequestMapping("enterprise_perUserlist")
    public String perUserlist() {
        setParam(request);
        return "/enterprise/user_list";
    }

    private void setParam(HttpServletRequest request) {
        String apiDocUrl = DatabaseCache.getStringValueBySystemEnvAndCode("api_doc_url", "https://www.showdoc.com.cn/872195016401251?page_id=4702298269132771");
        Code netwayHttpIp = DatabaseCache.getCodeBySortCodeAndCode("netway_http", "netway_http_ip");
        Code netwayHttpPort = DatabaseCache.getCodeBySortCodeAndCode("netway_http", "netway_http_port");
        Code netwayCmppIp = DatabaseCache.getCodeBySortCodeAndCode("netway_cmpp", "netway_cmpp_ip");
        Code netwayCmppPort = DatabaseCache.getCodeBySortCodeAndCode("netway_cmpp", "netway_cmpp_port");
        Code netwaySmppPort = DatabaseCache.getCodeBySortCodeAndCode("netway_smpp", "netway_smpp_port");
        Code netwaySgipPort=DatabaseCache.getCodeBySortCodeAndCode("netway_sgip", "netway_sgip_port");
        Code netwaySmgpPort=DatabaseCache.getCodeBySortCodeAndCode("netway_smgp", "netway_smgp_port");
        request.setAttribute("apiDocUrl", apiDocUrl);
        request.setAttribute("netwayHttpIp", netwayHttpIp);
        request.setAttribute("netwayHttpPort", netwayHttpPort);
        request.setAttribute("netwayCmppIp", netwayCmppIp);
        request.setAttribute("netwayCmppPort", netwayCmppPort);
        request.setAttribute("netwaySmppPort", netwaySmppPort);
        request.setAttribute("netwaySgipPort", netwaySgipPort);
        request.setAttribute("netwaySmgpPort", netwaySmgpPort);

    }

    @RequestMapping("enterprise_userList")
    @ResponseBody
    public String userList(EnterpriseUserExt enterpriseUserExt) {
        enterpriseUserExt.setEnterprise_No(getLoginEnterprise().getNo());
        List<EnterpriseUser> enterpriseUserInfoList = enterpriseManage.queryEnterpriseUserList(enterpriseUserExt);
        return new SmsUIObjectMapper().asSuccessString(enterpriseUserInfoList,
                enterpriseUserExt.getPagination());
    }

    /**
     * 增加企业用户
     *
     * @return
     */
    @RequestMapping("enterprise_addUser")
    @ResponseBody
    @AvoidRepeatableCommitAnnotation(systemModuleName = ENTERPRISE_PLATFORM + "enterprise_addUser")
    public LayUiJsonObjectFmt addUser(EnterpriseUserExt enterpriseUser) {
        try {
            if (enterpriseUser.getPassword() == null
                    || !enterpriseUser.getPassword().equals(enterpriseUser.getConfirmPassword())) {
                return LayuiResultUtil.fail("两次密码输入不一致");
            }
            enterpriseUser.setEnterprise_No(getLoginEnterprise().getNo());
            enterpriseUser.setRemark(this.getLoginRealName() + "添加");
            this.enterpriseManage.addEnterpriseUser(enterpriseUser);
            //初始化用户属性、产品、资费等。
            this.enterpriseManage.initUserProperties(getLoginEnterpriseUser(),enterpriseUser);
        } catch (ServiceException e) {
            return LayuiResultUtil.fail(e.getMessage());
        } catch (Exception e) {
            SuperLogger.error(e.getMessage(),e);
            return LayuiResultUtil.error(e);
        }
        return LayuiResultUtil.success();
    }

    /**
     * 验证电话是否已注册
     *
     * @return
     */
    @RequestMapping("enterprise_checkPhoneRepeat")
    @ResponseBody()
    public LayUiJsonObjectFmt checkAgentNameRepeat(EnterpriseUser userInfo) {
        EnterpriseUserExt user = new EnterpriseUserExt();
        user.setUser_Name(userInfo.getUser_Name());
        List<EnterpriseUser> userInfoList = enterpriseManage.queryEnterpriseUserList(user);
        if (userInfoList.size() > 0) {
            return LayuiResultUtil.fail("手机号已注册");
        }
        return LayuiResultUtil.success();
    }

    /**
     * 发送手机短信
     *
     * @return
     */
    @RequestMapping("enterprise_sendVerificationCode")
    @ResponseBody()
    public LayUiJsonObjectFmt sendVerificationCode(EnterpriseUserExt userInfo) {
        String kaptchaExpected = (String) request.getSession()
                .getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
        String graphValidateCode = userInfo.getGraphValidateCode();
        if (graphValidateCode == null || !graphValidateCode.equalsIgnoreCase(kaptchaExpected)) {
            return LayuiResultUtil.fail("图形验证码错误");
        }
        String verificationCode = RandomUtil.randomInt(4);
        StringBuffer msg = new StringBuffer();
        msg.append("您的验证码为：").append(verificationCode);
        if (userInfo.getUser_Name().length()>11){
            return LayuiResultUtil.fail("请输入一个手机号码");
        }
        this.sendManage.sendSMS(userInfo.getUser_Name(), msg.toString(),userInfo.getCountryCodeValue());
        GlobalStringCache.putAndExpire(userInfo.getUser_Name(), verificationCode, 60 * 10, TimeUnit.SECONDS);
        return LayuiResultUtil.success();
    }

    /**
     * 注册
     *
     * @return
     */
    @RequestMapping("enterprise_registerEnterpriseUser")
    @ResponseBody
    @AvoidRepeatableCommitAnnotation(systemModuleName = ENTERPRISE_PLATFORM + "enterprise_registerEnterpriseUser")
    public LayUiJsonObjectFmt registerEnterpriseUser(EnterpriseUserExt userInfo) {
        String verificationCode = GlobalStringCache.get(userInfo.getUser_Name());
        if (!StringUtils.isEmpty(verificationCode)) {
            if (!verificationCode.equals(userInfo.getSmsValidateCode())) {
                return LayuiResultUtil.fail("手机验证码错误");
            }
        } else {
            return LayuiResultUtil.fail("手机验证码已过期，请重新获取");
        }
        Enterprise enterprise = new Enterprise();
        enterprise.setName(userInfo.getEnterpriseName());
        enterprise.setPhone_No(userInfo.getUser_Name());
        enterprise.setAuthentication_State_Code(AuthCode.NOTAUTH.toString());
        enterprise.setData_Source(DataSourceType.REGISTERED.toString());
        enterprise = this.enterpriseManage.addEnterprise(enterprise);
        userInfo.setReal_Name(userInfo.getUser_Name());
        userInfo.setEnterprise_No(enterprise.getNo());
        userInfo.setRemark("自助注册");
        userInfo.setDataSource(DataSourceType.REGISTERED.toString());
        this.enterpriseManage.addEnterpriseUser(userInfo);
        return LayuiResultUtil.success();
    }

    /**
     * 企业认证前置
     *
     * @return
     */
    @RequestMapping("enterprise_preAuthEnterpriseInfo")
    public String preAuthEnterpriseInfo(Enterprise enterprise) {
        if (enterprise == null) {
            SuperLogger.error("enterprise is null");
            throw new NullPointerException("enterprise");
        }
        EnterpriseExt enterpriseExt = new EnterpriseExt();
        enterpriseExt.setId(getUserId());
        request.setAttribute("eBean", this.enterpriseManage.queryEnterpriseById(enterprise.getId()));
        return "/enterprise/auth";
    }

    /**
     * 企业认证
     *
     * @return
     */
    @RequestMapping("enterprise_authEnterpriseInfo")
    @ResponseBody()
    public LayUiJsonObjectFmt authEnterpriseInfo(
            @RequestParam(value = "uploadImg", required = false) MultipartFile uploadImg,
            @RequestParam(value = "idCardBefore", required = false) MultipartFile idCardBefore,
            @RequestParam(value = "idCardBack", required = false) MultipartFile idCardBack, EnterpriseExt enterpriseExt)
            throws Exception {
        enterpriseExt.setId(getUserId());
        if (uploadImg != null && !uploadImg.isEmpty()) {// 营业执照
            Map<String, String> map = UploadUtil.uploadFile(uploadImg, "authImgs");
            if (!"true".equals(map.get("status"))) {
                return LayuiResultUtil.fail("上传失败");
            }
            enterpriseExt.setBusiness_License_Picture_Url(map.get("url"));
        }
        if (idCardBefore != null && !idCardBefore.isEmpty()) {// 身份证正面
            Map<String, String> map = UploadUtil.uploadFile(idCardBefore, "idCards");
            if (!"true".equals(map.get("status"))) {
                return LayuiResultUtil.fail("上传失败");
            }
            enterpriseExt.setId_Card_Before_Picture_Url(map.get("url"));
        }
        if (idCardBack != null && !idCardBack.isEmpty()) {// 身份证背面
            Map<String, String> map = UploadUtil.uploadFile(idCardBack, "idCards");
            if (!"true".equals(map.get("status"))) {
                return LayuiResultUtil.fail("上传失败");
            }
            enterpriseExt.setId_Card_Back_Picture_Url(map.get("url"));
        }
        enterpriseManage.editEnterprise(enterpriseExt);
        // 通知客服
        String msg = "新用户:" + getLoginEnterpriseUser().getUser_Name() + "注册，请及时认证审核";
        String platformTelephone = DatabaseCache.getStringValueBySystemEnvAndCode("platform_telephone", "");
      //设置国际码，暂时默认86
        String countryCode="86";
        this.sendManage.sendSMS(platformTelephone, msg,countryCode);
        return LayuiResultUtil.success();
    }

    @RequestMapping("enterprise_smsTemplateList")
    @ResponseBody
    public String smsTemplateList(SmsTemplateExt smsTemplateExt) {
        smsTemplateExt.setEnterprise_No(getLoginEnterprise().getNo());
        smsTemplateExt.setEnterprise_User_Id(getLoginEnterpriseUser().getId());
        List<SmsTemplate> smsTemplateLists = enterpriseManage.querySmsTemplateList(smsTemplateExt);
        return new SmsUIObjectMapper().asSuccessString(smsTemplateLists, smsTemplateExt.getPagination());
    }

    @RequestMapping("enterprise_addSmsTemplate")
    @ResponseBody
    @AvoidRepeatableCommitAnnotation(systemModuleName = ENTERPRISE_PLATFORM + "enterprise_addSmsTemplate")
    public LayUiJsonObjectFmt addSmsTemplate(SmsTemplate smsTemplate) {
        try {
            smsTemplate.setEnterprise_No(this.getLoginEnterprise().getNo());
            smsTemplate.setEnterprise_User_Id(this.getUserId());
            smsTemplate.setApprove_Status(Constant.SMS_TEMPLAT_CHECK_STATUS_PENDING);
            this.enterpriseManage.addSmsTemplate(smsTemplate);
        } catch (ServiceException e) {
            return LayuiResultUtil.fail(e.getMessage());
        } catch (Exception e) {
            return LayuiResultUtil.fail(e.getMessage());
        }
        return LayuiResultUtil.success();
    }

    @RequestMapping("enterprise_preSmsTemplateEdit")
    public String preRoleEdit(BaseParamEntity entity) {
        if (entity.getCkIds() == null || entity.getCkIds().isEmpty()) {
            return "/admin/enterprise/sms_template_edit";
        }
        SmsTemplateExt condition = new SmsTemplateExt();
        condition.setId(entity.getCkIds().get(0));
        condition.setEnterprise_No(getLoginEnterprise().getNo());
        condition.setEnterprise_User_Id(getUserId());
        List<SmsTemplate> list = enterpriseManage.querySmsTemplateList(condition);
        request.setAttribute("smsTemplate", list.size() > 0 ? list.get(0) : null);
        return "/enterprise/sms_template_edit";
    }

    @RequestMapping("enterprise_editSmsTemplate")
    @ResponseBody
    public LayUiJsonObjectFmt editSmsTemplate(SmsTemplate smsTemplate) {
        try {
            smsTemplate.setEnterprise_No(getLoginEnterprise().getNo());
            smsTemplate.setEnterprise_User_Id(getUserId());
            this.enterpriseManage.editSmsTemplate(smsTemplate);
        } catch (ServiceException e) {
            return LayuiResultUtil.fail(e.getMessage());
        }
        return LayuiResultUtil.success();
    }

    @RequestMapping("enterprise_delSmsTemplate")
    @ResponseBody
    public LayUiJsonObjectFmt delSmsTemplate(BaseParamEntity entity) {
        try {
            this.enterpriseManage.deleteSmsTemplate(entity.getCkIds(), getAuthorityEnterpriseUserBean());
        } catch (ServiceException e) {
            return LayuiResultUtil.fail(e.getMessage());
        }
        return LayuiResultUtil.success();
    }

    @RequestMapping("enterprise_importSmsTemplate")
    @ResponseBody
    @AvoidRepeatableCommitAnnotation(systemModuleName = ENTERPRISE_PLATFORM + "enterprise_importSmsTemplate")
    public LayUiJsonObjectFmt importSmsTemplate(SmsTemplate smsTemplate, MultipartFile smsTemplateFile) {
        try {
            smsTemplate.setEnterprise_No(this.getLoginEnterprise().getNo());
            smsTemplate.setEnterprise_User_Id(getUserId());
            smsTemplate.setApprove_Status(AuditStatus.UNAUDITED.value());
            smsTemplate.setCreate_Date(new Date());
            this.enterpriseManage.importSmsTemplate(smsTemplate, smsTemplateFile);
        } catch (ServiceException e) {
            return LayuiResultUtil.fail(e.getMessage());
        } catch (Exception e) {
            return LayuiResultUtil.fail(e.getMessage());
        }
        return LayuiResultUtil.success();
    }

    // 主账户转账 前置
    @RequestMapping("enterprise_preUser2charge")
    public String preUser2charge(BaseParamEntity entity) {
        if (entity.getCkIds().size() != 1) {
            SuperLogger.error("请选择一条数据");
            request.setAttribute("error", "请选择一条数据");
            return "/errors/error";
        }
        if (getUserId() == entity.getCkIds().get(0)) {
            SuperLogger.error("不允许给自己划拨余额");
            request.setAttribute("error", "不允许给自己划拨余额");
            return "/errors/error";
        }
        EnterpriseUserExt user = new EnterpriseUserExt();
        user.setId(getUserId());
        request.setAttribute("masterMoney", getLoginEnterpriseUser().getAvailable_Amount());
        EnterpriseUserExt enterpriseUserExt = new EnterpriseUserExt();
        enterpriseUserExt.setId(entity.getCkIds().get(0));
        enterpriseUserExt.setEnterprise_No(getLoginEnterprise().getNo());
        List<EnterpriseUser> enterpriseUserList = this.enterpriseManage.queryEnterpriseUserList(enterpriseUserExt);
        if (!enterpriseUserList.isEmpty()) {
            request.setAttribute("enterpriseUser", enterpriseUserList.get(0));
        }
        return "/enterprise/user2charge";
    }

    // 主账户转账
    @RequestMapping("enterprise_user2charge")
    @ResponseBody
    @AvoidRepeatableCommitAnnotation(systemModuleName = ENTERPRISE_PLATFORM + "enterprise_user2charge")
    public LayUiJsonObjectFmt enterprise_user2charge(ChargeOrder chargeOrder) {
        try {
            iChargeManage.user2charge(chargeOrder, getLoginEnterpriseUser().getId());
        } catch (ServiceException e) {
            return LayuiResultUtil.fail(e.getMessage());
        } catch (Exception e) {
            return LayuiResultUtil.fail(e.getMessage());
        }
        return LayuiResultUtil.success();
    }
    /**
     * 资费列表
     *
     * @param
     * @return
     */
    @RequestMapping("enterprise_enterpriseUserFeeList")
    @ResponseBody
    public String enterpriseUserFeeList(EnterpriseUserFee enterpriseUserFee) {
        enterpriseUserFee.setEnterprise_User_Id(getUserId());
        if(enterpriseUserFee.getPagination()==null){
            enterpriseUserFee.setPagination(new Pagination(5));
        }
        List<EnterpriseUserFee> enterpriseUserFeeList = enterpriseManage.queryEnterpriseUserFeeList(enterpriseUserFee);
        return new LayUiObjectMapper().asSuccessString(enterpriseUserFeeList, enterpriseUserFee.getPagination().getTotalCount());
    }

    private String getPlainPassword(String encryptPassword) {
        //rsa解密
        String passwordPrivateKey = DatabaseCache.getStringValueBySystemEnvAndCode("password_private_key", "");
        String plainText = null;
        if (StringUtils.isNotBlank(passwordPrivateKey)) {
            plainText = CertificateUtil.decryptByPrivateKey(encryptPassword, passwordPrivateKey);
            if (StringUtils.isEmpty(plainText)) {
                SuperLogger.error("密码解密错误，请检查私钥");
                throw new ServiceException(ResultStatus.ERROR);
            }
        }
        return plainText;
    }
}
