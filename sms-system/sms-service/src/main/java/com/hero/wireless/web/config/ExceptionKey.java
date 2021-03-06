package com.hero.wireless.web.config;

/**
 * 异常Key
 * 
 * @author Volcano
 *
 */
public enum ExceptionKey {
	UNSUPPORTED_OPERATION_FUN("UNSUPPORTED_OPERATION_FUN"), // 方法没有实现或不支持
	USER_PASSWORD_ERROR("USER_PASSWORD_ERROR"), // 用户名或者密码错误!
	GOOGLE_VALIDATECODE_ERROR("GOOGLE_VALIDATECODE_ERROR"), // 谷歌验证码错误!（方法没有实现或不支持）
	USER_PASSWORD_ERROR_ALTER("USER_PASSWORD_ERROR_ALTER"),

	USER_LOGIN_ERROR("USER_LOGIN_ERROR"), // 用户登录错误
	ACCOUT_LOCKED("ACCOUT_LOCKED"), // 账户锁定
	ACCOUNT_UNKNOW_STATUS("ACCOUNT_UNKNOW_STATUS"), // 未知账户状态

	SERVICE_EXCEPTION("SERVICE_EXCEPTION"), // 业务异常

	ADMIN_USER_ROLE_0("ADMIN_USER_ROLE_0"), ADMIN_USER_ROLE_1("ADMIN_USER_ROLE_1"),
	ADMIN_USER_ROLE_2("ADMIN_USER_ROLE_2"), ADMIN_USER_ROLE_3("ADMIN_USER_ROLE_3"),
	ADMIN_USER_ROLE_4("ADMIN_USER_ROLE_4"), ADMIN_USER_ROLE_5("ADMIN_USER_ROLE_5"),
	ADMIN_USER_ROLE_6("ADMIN_USER_ROLE_6"),

	USER_NAME_EXSITS("USER_NAME_EXSITS"),

	CODE_EXSITS("CODE_EXSITS"), CODE_SORT_EXSITS("CODE_SORT_EXSITS"), SENSITIVE_WORD_EXSITS("SENSITIVE_WORD_EXSITS"),
	NO_VAILABLE_SMS_TOTAL("NO_VAILABLE_SMS_TOTAL"), OUT_MAX_BATCH_MOBILE_COUNT("OUT_MAX_BATCH_MOBILE_COUNT"),
	OUT_MAX_SHORT_MESSAGE_COUNT("OUT_MAX_SHORT_MESSAGE_COUNT"), BLACK_EXSITS("BLACK_EXSITS"),

	ROLE_CODE_EXSITS("ROLE_CODE_EXSITS"), LIMIT_CODE_EXSITS("LIMIT_CODE_EXSITS"),

	MOBILE_AREA_EXSITS("MOBILE_AREA_EXSITS"), GROUP_NAME_EXSITS("GROUP_NAME_EXSITS"),
	CONTACT_MOBILE_EXSITS("CONTACT_MOBILE_EXSITS"), ENTERPRISE_SECRET_EXISTS("ENTERPRISE_SECRET_EXISTS")// 企业秘钥已存在
	;

	private String strValue;

	private ExceptionKey(String strValue) {
		this.strValue = strValue.toLowerCase();
	}

	@Override
	public String toString() {
		return this.strValue;
	}
}
