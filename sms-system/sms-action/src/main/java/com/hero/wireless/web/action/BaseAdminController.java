package com.hero.wireless.web.action;

import java.util.Enumeration;

import com.hero.wireless.web.entity.business.*;
import com.hero.wireless.web.entity.business.ext.ExportFileExt;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.drondea.wireless.util.SuperLogger;
import com.hero.wireless.web.config.MessagesManger;
import com.hero.wireless.web.config.SystemKey;

public class BaseAdminController extends BasePaginationController {
    private String serviceMessage;
	protected final String SUCCESS_CLOSE = "success_close";
	private int pageNum;
	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
		this.setPageIndex(pageNum);
	}

	public String getSUCCESS_CLOSE() {
		return SUCCESS_CLOSE;
	}

	public String getServiceMessage() {
		return serviceMessage;
	}

	public void setServiceMessage(String serviceMessage) {
		this.serviceMessage = serviceMessage;
	}

	public AdminUser getLoginUser() {
		return (AdminUser)((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession().getAttribute(SystemKey.ADMIN_USER.toString());
	}

	protected String input(String message) {
		try {
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(message);
		} catch (Exception e) {
		}
		return null;
	}

	public String getLoginRealName() {
		return this.getLoginUser().getReal_Name();
	}

	public int getUserId() {
		return this.getLoginUser().getId();
	}

	public Enterprise getLoginEnterprise() {
		Enterprise info = new Enterprise();
		info.setNo(MessagesManger
				.getSystemMessages(SystemKey.ADMIN_SUBMIT_ENTERPRISE_NO));
		return info;
	}

	public EnterpriseUser getLoginEnterpriseUser() {
		EnterpriseUser userInfo = new EnterpriseUser();
		userInfo.setId(Integer.parseInt(MessagesManger
				.getSystemMessages(SystemKey.ADMIN_SUBMIT_USER_ID)));
		return userInfo;
	}

	public ExportFileExt getAdminDefaultExportFile() {
        ExportFileExt exportFile = new ExportFileExt();
        exportFile.setEnterprise_No("0");
        exportFile.setAgent_No("0");
        exportFile.setUser_Id(this.getUserId());
		exportFile.setBlur(AdminControllerBase.isBlurPhoneNo());
		return exportFile;
	}


	protected void printRequest() {
		Enumeration<String> e=request.getParameterNames();
		StringBuffer keyValue=new StringBuffer();
		while(e.hasMoreElements())
		{
			String key=e.nextElement();
			keyValue.append("key:").append(key).append(",").append("value:") .append(request.getParameter(key)).append(";");
		}
		SuperLogger.debug(keyValue);
	}
}
