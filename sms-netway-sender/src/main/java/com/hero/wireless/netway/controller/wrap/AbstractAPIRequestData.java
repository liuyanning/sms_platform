package com.hero.wireless.netway.controller.wrap;

import com.hero.wireless.notify.JsonBase;
import com.hero.wireless.notify.JsonSubmit;
import com.hero.wireless.web.config.DatabaseCache;
import com.hero.wireless.web.entity.business.EnterpriseUser;
import com.hero.wireless.web.entity.business.ext.EnterpriseUserExt;

import java.util.List;

public abstract class AbstractAPIRequestData {

    public abstract boolean checkSign() throws Exception;

    public abstract JsonSubmit assembleSubmitRequestData() throws Exception;

    public abstract JsonBase assembleBalaceRequestData() throws Exception;

    public abstract JsonBase assembleReportRequestData() throws Exception;

    public abstract JsonBase assembleMoRequestData() throws Exception;

    public EnterpriseUser getEnterpriseUser(String enterpriseNo, String userId, String account) {
        EnterpriseUserExt enterpriseUserExt = new EnterpriseUserExt();
        enterpriseUserExt.setEnterprise_No(enterpriseNo);
        enterpriseUserExt.setId(userId == null ? null : Integer.valueOf(userId));
        enterpriseUserExt.setUser_Name(account);
        List<EnterpriseUser> userList = DatabaseCache.getEnterpriseUserList(enterpriseUserExt);
        EnterpriseUser user = userList.size() == 0 ? null : userList.get(0);
        return user;
    }

}
