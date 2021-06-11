package com.hero.wireless.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * 预警状态
 * 
 * @author gengjinbiao
 * @date 2019年11月9日
 * @version V1.0
 */
public enum AlarmStatus {
	START, STOP;
	private String value;

	private AlarmStatus() {
		this.value = this.name().toLowerCase();
	}

	private AlarmStatus(String value) {
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
