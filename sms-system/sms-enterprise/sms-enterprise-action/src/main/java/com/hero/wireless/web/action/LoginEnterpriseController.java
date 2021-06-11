package com.hero.wireless.web.action;

import com.drondea.wireless.util.ServiceException;
import com.drondea.wireless.util.SuperLogger;
import com.hero.wireless.web.config.DatabaseCache;
import com.hero.wireless.web.config.SystemKey;
import com.hero.wireless.web.entity.business.Code;
import com.hero.wireless.web.entity.business.Enterprise;
import com.hero.wireless.web.entity.business.EnterpriseUser;
import com.hero.wireless.web.entity.business.ext.EnterpriseExt;
import com.hero.wireless.web.service.IEnterpriseManage;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class LoginEnterpriseController extends LoginAdminController {

	@Resource(name = "enterpriseManage")
	private IEnterpriseManage enterpriseManage;
	
	@RequestMapping("/userLogin")
	public String userLogin() {
		return "redirect:" + getDomainUrl();
	}
	
	@RequestMapping("/enterpriseLogin")
	public String login(String validateCode , EnterpriseUser enterpriseUser) {
		try {
			if (!checkValidateCode(validateCode)) {
				request.setAttribute("msg", "验证码错误！");
				return "forward:" + getDomainUrl();
			}
			enterpriseUser = enterpriseManage.userLogin(enterpriseUser);
			request.getSession().setAttribute(SystemKey.ADMIN_USER.toString(),
					enterpriseUser);
			Enterprise enterprise = enterpriseManage.queryEnterpriseByNo(enterpriseUser.getEnterprise_No());
			EnterpriseExt enterpriseExt = new EnterpriseExt();
			BeanUtils.copyProperties(enterprise, enterpriseExt);
			if (StringUtils.isNotBlank(enterpriseExt.getStatus())){
				Code code = DatabaseCache.getCodeBySortCodeAndCode("003",enterpriseExt.getStatus());
				if(null != code){
					SuperLogger.debug(code.getName());
				}
				enterpriseExt.setStatus_Name(DatabaseCache.getCodeBySortCodeAndCode("003",enterpriseExt.getStatus()).getName());
			}
			if (StringUtils.isNotBlank(enterpriseExt.getAuthentication_State_Code())){
				enterpriseExt.setAuthentication_State_Code_Name(DatabaseCache.getCodeBySortCodeAndCode("authentication_State",enterpriseExt.getAuthentication_State_Code()).getName());
			}
			request.getSession().setAttribute(SystemKey.ENTERPRISE_INFO.toString(),enterpriseExt);
			String indexUrl = DatabaseCache.getStringValueBySortCodeAndCode("page_configuration", "enterprise_index_home", "/admin/index/admin_index.jsp");
			return "redirect:" + indexUrl;
		} catch (ServiceException se) {
			request.setAttribute("msg", se.getMessage());
            return "forward:"+getDomainUrl();
		} catch (Exception e) {
			SuperLogger.error("登录异常", e);
			request.setAttribute("msg", "登录异常，请联系管理员");
            return "forward:"+getDomainUrl();
		}
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


	@RequestMapping("/enterpriseLogout")
	public String logout() {
		request.getSession().invalidate();
		return "redirect:"+getDomainUrl();
	}
	
	/**
	 * 根据域名查询企业域名设置url
	 */
	private String getDomainUrl() {
		//用户域名
		String userDomain = request.getServerName();
		EnterpriseExt enterpriseExt = new EnterpriseExt();
		enterpriseExt.setDomain(userDomain);
		List<Enterprise> enterprises = enterpriseManage.queryEnterpriseList(enterpriseExt);
		if(enterprises!=null&&!enterprises.isEmpty()) {
			return enterprises.get(0).getLogin_Url();
		}
		return DatabaseCache.getStringValueBySortCodeAndCode("page_configuration","enterprise_login_url", "/public/admin/login.jsp");
	}

}
