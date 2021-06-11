package com.hero.wireless.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * 通道状态
 * 
 * @author volcano
 * @date 2019年9月15日上午1:27:20
 * @version V1.0
 */
public enum ChannelStatus {
	START, STOP, HOLD;
	private String value;

	private ChannelStatus() {
		this.value = this.name().toLowerCase();
	}

	private ChannelStatus(String value) {
		this.value = value.toLowerCase();
	}

	@Override
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
