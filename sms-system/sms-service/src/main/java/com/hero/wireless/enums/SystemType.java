package com.hero.wireless.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * 系统类型
 * 
 * @author gengjinbiao
 * @date 2020年09月08日
 * @version V2.6
 */
public enum SystemType {
	ADMIN,
	AGENT,
	ENTERPRISE,
	NETWAY,
	SORT,
	SENDER,
	SAVE_DATA,
	TIMER;

	private String value;

	private SystemType() {
		this.value = this.name().toLowerCase();
	}

	private SystemType(String value) {
		this.value = value.toLowerCase();
	}

	public String toString() {
		return value;
	}
	public boolean equals(String value) {
		if (StringUtils.isEmpty(value)) {
			return false;
		}
		return this.value.equalsIgnoreCase(value);
	}
}
