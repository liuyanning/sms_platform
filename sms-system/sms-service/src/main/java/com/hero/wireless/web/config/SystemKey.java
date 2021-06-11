package com.hero.wireless.web.config;

/**
 * 系统常量
 * 
 * @author Volcano
 * 
 */
public enum SystemKey {
	USER_STATUS_NORMAL("USER_STATUS_NORMAL"),
	ADMIN_USER("ADMIN_USER"),
	ENTERPRISE_INFO("ENTERPRISE_INFO"),
	AGENT_INFO("AGENT_INFO"),
	MONITOR_INFO("MONITOR_INFO"),
	DEFAULT_PAGE_SIZE("DEFAULT_PAGE_SIZE"),
	ADMIN_SUBMIT_ENTERPRISE_NO("ADMIN_SUBMIT_ENTERPRISE_NO"),
	ADMIN_SUBMIT_USER_ID("ADMIN_SUBMIT_USER_ID"),
	ADMIN_USER_ROLE_0("ADMIN_USER_ROLE_0"),
	RMI_SORT_SMS_URL("RMI_SORT_SMS_URL"),
	WEB_SERVICE_URL("WEB_SERVICE_URL"),
	MD5_KEY("MD5_KEY"),
	SOLR_URL("SOLR_URL");
	private String strValue;

	private SystemKey(String strValue) {
		this.strValue = strValue;
	}

	@Override
	public String toString() {
		return this.strValue;
	}
}
