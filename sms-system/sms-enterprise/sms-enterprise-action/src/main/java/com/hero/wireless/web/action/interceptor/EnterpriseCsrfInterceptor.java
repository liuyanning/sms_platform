package com.hero.wireless.web.action.interceptor;

import com.hero.wireless.web.config.DatabaseCache;
import com.hero.wireless.web.entity.business.Code;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.MalformedURLException;
import java.util.List;

/**
 * 权限验证拦截器
 * 
 * @author liuyanning
 * 
 */
public class EnterpriseCsrfInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) {
		//get不拦截
		if ("GET".equals(request.getMethod())) {
			return true;
		}
		String referer = request.getHeader("referer");
		if (referer == null) {
			// 状态置为404
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return false;
		}
		java.net.URL url;
		try {
			url = new java.net.URL(referer);
		} catch (MalformedURLException e) {
			// URL解析异常，也置为404
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return false;
		}

		String host = request.getServerName();
		// 首先判断请求域名和referer域名是否相同
		if (host.equals(url.getHost())) {
			return true;
		}

		List<? extends Code> refererWhiteList = DatabaseCache.getCodeListBySortCode("referer_white_list");
		if (ObjectUtils.isEmpty(refererWhiteList)) {
			return false;
		}
		// 如果不等，判断是否在白名单中
		boolean result = refererWhiteList.stream().anyMatch(item -> url.getHost().equals(item.getValue()));
		if (result) {
			return true;
		}

		response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        return false;
    }
}
