package com.hero.wireless.web.action;


import com.drondea.wireless.util.SuperLogger;
import com.hero.wireless.web.entity.business.ext.AdminUserExt;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class AdminControllerBase extends BaseController {
    public final static String KEY_ADMIN_USER = "ADMIN_USER";
    public final static String BUSINESS_ROLE = "Business";
    public final static String SHOW_PHONE_NO_LIMIT = "001000";

    public static AdminUserExt currentAdmin() {
        return (AdminUserExt) ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest()
                .getSession().getAttribute(KEY_ADMIN_USER);
    }

    public static Integer currentBusinessUserId() {
        try {
            if(currentAdmin().getRoles().stream().anyMatch(item -> item.getCode().equalsIgnoreCase(BUSINESS_ROLE))){
                return currentAdmin().getId();
            }
        }catch (Exception e){
            SuperLogger.error(e.getMessage(), e);
            return 0;
        }
        return null;
    }

    public static boolean isBlurPhoneNo(){
        return currentAdmin().getLimits().stream().anyMatch(item-> item.getCode().equals(SHOW_PHONE_NO_LIMIT));
    }
}
