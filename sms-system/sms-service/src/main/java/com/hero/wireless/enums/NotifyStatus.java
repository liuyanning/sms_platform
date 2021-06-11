package com.hero.wireless.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * 通知状态
 * 
 * @author volcano
 * @date 2019年9月12日下午11:16:32
 * @version V1.0
 */
public enum NotifyStatus {
	SUCCESS, FAILD, UNNOTIFY;
	private String value;

	private NotifyStatus() {
		this.value = this.name().toLowerCase();
	}

	private NotifyStatus(String value) {
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
