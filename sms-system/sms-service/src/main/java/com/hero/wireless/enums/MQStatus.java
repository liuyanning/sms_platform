package com.hero.wireless.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * mq服务器的状态
 * 
 */
public enum MQStatus {
	START, STOP;
	private String value;

	private MQStatus() {
		this.value = this.name().toLowerCase().toString();
	}

	private MQStatus(String value) {
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
