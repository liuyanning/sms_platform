package com.hero.wireless.sort;

import com.hero.wireless.web.config.DatabaseCache;
import com.hero.wireless.web.entity.business.Enterprise;
import com.hero.wireless.web.entity.business.EnterpriseUser;
import com.hero.wireless.web.util.SMSUtil;
import org.apache.commons.lang3.ObjectUtils;

/**
 * @version V3.0.0
 * @description: 初始化企业信息
 * @author: 刘彦宁
 * @date: 2020年09月17日14:26
 **/
public class EnterpriseSetter {

    /**
     * 初始化企业信息
     *
     * @param
     * @return
     */
    public static boolean initEnterpriseInfo(SortContext context) {
        //企业不校验余额，可以本地缓存
        Enterprise enterprise = DatabaseCache.getEnterpriseCachedByNo(context.getInput().getEnterprise_No());
        if (ObjectUtils.isEmpty(enterprise)) {
            return false;
        }
        context.setEnterprise(enterprise);
        Integer userId = context.getInput().getEnterprise_User_Id();

        EnterpriseUser user = DatabaseCache.getEnterpriseUserCachedById(userId);
        context.setEnterpriseUser(user);

        context.setBlackSwitch(DatabaseCache.isUserBlackSwitch(String.valueOf(user.getId())));

        context.setSortLimitRepeat(new SortLimitRepeat(context));
        return true;
    }
}
