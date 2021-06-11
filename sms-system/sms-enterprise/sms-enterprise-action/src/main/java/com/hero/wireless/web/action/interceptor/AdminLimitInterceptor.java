package com.hero.wireless.web.action.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import com.alibaba.fastjson.JSONObject;
import com.drondea.wireless.util.SuperLogger;
import com.hero.wireless.json.LayUiJsonObjectFmt;
import com.hero.wireless.web.config.SystemKey;
import com.hero.wireless.web.entity.business.EnterpriseLimit;
import com.hero.wireless.web.entity.business.ext.EnterpriseUserExt;

/**
 * 权限验证拦截器
 * 
 * @author Enterpriseistrator
 * 
 */
public class AdminLimitInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
	    try{
            SuperLogger.debug("进入权限拦截器");
            String requestURI = request.getRequestURI();
            if("/".equals(requestURI)){
    			return true;
    		}
            int requestURISize = requestURI.length();
            HttpSession session = request.getSession();
            EnterpriseUserExt users = (EnterpriseUserExt) session
                    .getAttribute(SystemKey.ADMIN_USER.toString());
            List<EnterpriseLimit> limitList = users.getLimits();
            for (int i = 0; i < limitList.size(); i++) {
                String limitUrl = limitList.get(i).getUrl();
                if (StringUtils.isEmpty(limitUrl)) {
                    continue;
                }
                if (limitUrl.length() > requestURISize) {
                    continue;
                }
                String equalRequestURI = requestURI.substring(requestURISize
                        - limitUrl.length(), requestURISize);
                SuperLogger.debug("requestURI:" + equalRequestURI + "  limitURL:"
                        + limitUrl);
                if (equalRequestURI.equals(limitUrl)) {
                    return true;
                }
                continue;
            }
            LayUiJsonObjectFmt result = new LayUiJsonObjectFmt();
            result.setCode("-1");
            result.setMsg("无权限!");
            response.getWriter().write(JSONObject.toJSONString(result));
            return false;
        }catch (Exception e){
	        e.printStackTrace();
	    }
        return false;
    }
}
