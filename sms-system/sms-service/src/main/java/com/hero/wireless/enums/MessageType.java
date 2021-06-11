package com.hero.wireless.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * 消息类型
 * 
 * @author volcano
 * @date 2019年9月12日下午11:16:11
 * @version V1.0
 */
public enum MessageType {
	SMS, MMS, VOICE, VIDEO, UNKNOW, RCS;
	private String value;

	private MessageType() {
		this.value = this.name().toLowerCase();
	}

	private MessageType(String value) {
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
	
	public static void main(String[] args) {
		System.out.println(MessageType.valueOf("SMS"));
	}
}
