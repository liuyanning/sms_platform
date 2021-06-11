package com.hero.wireless.web.action;

import com.hero.wireless.web.config.SystemKey;
import com.hero.wireless.web.entity.business.Enterprise;
import com.hero.wireless.web.entity.business.EnterpriseUser;
import com.hero.wireless.web.entity.business.ext.EnterpriseUserExt;
import com.hero.wireless.web.entity.business.ext.ExportFileExt;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class BaseEnterpriseController extends BaseController {

    public final static String SHOW_PHONE_NO_LIMIT = "001000";
    public Enterprise getLoginEnterprise() {
        return (Enterprise) ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession().getAttribute(SystemKey.ENTERPRISE_INFO.toString());
    }

    public EnterpriseUser getLoginEnterpriseUser() {
        return (EnterpriseUser) ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession().getAttribute(SystemKey.ADMIN_USER.toString());
    }
    public static EnterpriseUserExt currentEnterpriseUser() {
        return (EnterpriseUserExt) ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest()
                .getSession().getAttribute(SystemKey.ADMIN_USER.toString());
    }

    public String getLoginRealName() {
        return this.getLoginEnterpriseUser().getReal_Name();
    }

    public int getUserId() {
        return this.getLoginEnterpriseUser().getId();
    }

    public ExportFileExt getEnterpriseDefaultExportFile() {
        ExportFileExt exportFile = new ExportFileExt();
        exportFile.setEnterprise_No(getLoginEnterprise().getNo());
        exportFile.setAgent_No("0");//无论有没有代理商  企业导出只看Enterprise_No 跟 user_Id
        exportFile.setUser_Id(this.getUserId());
        exportFile.setBlur(isBlurPhoneNo());
        return exportFile;
    }

    public EnterpriseUser getAuthorityEnterpriseUserBean() {
        EnterpriseUser enterpriseUser = new EnterpriseUser();
        enterpriseUser.setEnterprise_No(getLoginEnterprise().getNo());
        enterpriseUser.setId(getLoginEnterpriseUser().getId());
        return enterpriseUser;
    }
    public static boolean isBlurPhoneNo(){
        return currentEnterpriseUser().getLimits().stream().anyMatch(item-> item.getCode().equals(SHOW_PHONE_NO_LIMIT));
    }
}
