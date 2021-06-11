package com.hero.wireless.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * 通道产品导流策略类型 ProductChannelDiversionType.java
 *
 * @author wjl
 * @date 2020年2月9日下午12:21:52
 */
public enum ProductChannelDiversionType {

	PHONE_NO_POLL("号码池", "PHONE_NO_POLL"),
	SIGNATURE("签名导流 ", "SIGNATURE"),
	INCLUDE_KEYWORD("包含关键字", "INCLUDE_KEYWORD"),
	EXCLUDE_KEYWORD("排除关键字", "EXCLUDE_KEYWORD"),
	SMS_LENGTH_LIMIT("长度限制", "SMS_LENGTH_LIMIT"),
	INTERVAL_LIMIT("频率限制", "INTERVAL_LIMIT"),
	AREAS("区域限制", "AREAS"),
	PREVENT_SHIELD_CODE("防屏码", "PREVENT_SHIELD_CODE"),
	PHONE_NO_CHECK("空号检测", "PHONE_NO_CHECK");
	
	private String name;

	private String value;

	ProductChannelDiversionType(String name, String value) {
		this.name = name;
		this.value = value;
	}

	public String toString() {
		return value.toLowerCase();
	}
	
	public String getName() {
		return name;
	}

	public boolean equals(String name, String value) {
		if (StringUtils.isEmpty(value)) {
			return false;
		}
		return this.value.equalsIgnoreCase(value);
	}
}
