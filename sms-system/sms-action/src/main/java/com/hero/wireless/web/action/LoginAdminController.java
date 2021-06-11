package com.hero.wireless.web.action;

import javax.annotation.Resource;

import com.hero.wireless.web.action.interceptor.OperateAnnotation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hero.wireless.web.config.DatabaseCache;
import com.hero.wireless.web.config.SystemKey;
import com.hero.wireless.web.entity.business.AdminUser;
import com.hero.wireless.web.exception.BaseException;
import com.hero.wireless.web.service.IUserManage;

/**
 * 
 * 
 * LoginAdminAction
 * @author 张丽阳
 * @createTime 2015年6月2日 下午5:50:06
 * @version 1.0.0
 *
 */
@Controller
@RequestMapping("/public/")
public class LoginAdminController extends BaseAdminController {

	private static final Log LOG = LogFactory.getLog(LoginAdminController.class);
	@Resource(name = "userManage")
	protected IUserManage userManage;

	@RequestMapping("login")
	@OperateAnnotation(moduleName = "运营平台用户登录", option = "用户登录系统")
	public String login(String validateCode, AdminUser adminUser) {
		adminUser.setCurrent_Login_IP(request.getRemoteHost());
		try {
			if (!checkValidateCode(validateCode)) {
				request.setAttribute("msg", "验证码错误！");
				return "forward:/public/admin/login.jsp";
			}
			request.getSession().setAttribute(SystemKey.ADMIN_USER.toString(),
					userManage.adminUserLogin(adminUser,validateCode));
            //登录超时时间
            String secondStr = DatabaseCache.getStringValueBySystemEnvAndCode("user_login_timeout","3600");//秒
            request.getSession().setMaxInactiveInterval(Integer.valueOf(secondStr));/*秒为单位*/
			return "redirect:/admin/index.jsp";
		} catch (BaseException be) {
			request.setAttribute("msg", be.getMessage());
            return "forward:/public/admin/login.jsp";
		}catch (RuntimeException be) {
			request.setAttribute("msg", be.getMessage());
            return "forward:/public/admin/login.jsp";
		}
	}

	@RequestMapping("logout")
	public String logout() {
		request.getSession().invalidate();
		LOG.debug("清除session");
		return "redirect:/public/admin/login.jsp";
	}
	// 验证码验证
	private boolean checkValidateCode(String validateCode) {
		String kaptchaExpected = (String) request.getSession().getAttribute(
				com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
		request.getSession().removeAttribute(
				com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
		if (validateCode == null
				|| !validateCode.equalsIgnoreCase(kaptchaExpected)) {
			return false;
		}
		return true;
	}

}
