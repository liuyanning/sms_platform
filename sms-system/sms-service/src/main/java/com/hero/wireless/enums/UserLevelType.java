package com.hero.wireless.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * 用户级别
 * 
 * @author volcano
 * @date 2019年9月12日下午11:16:11
 * @version V1.0
 */
public enum UserLevelType {
    MASTER, SUB;
	private String value;

	private UserLevelType() {
		this.value = this.name().toLowerCase();
	}

	private UserLevelType(String value) {
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
