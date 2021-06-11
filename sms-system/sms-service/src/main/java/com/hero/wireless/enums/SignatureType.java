package com.hero.wireless.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * 签名类型
 * 
 * @author volcano
 * @date 2019年10月2日上午8:30:20
 * @version V1.0
 */
public enum SignatureType {
	// 自定义
	CUSTOMIZE,
	// 通道固定
	CHANNEL_FIXED;
	private String value;

	private SignatureType() {
		this.value = this.name().toLowerCase();
	}

	private SignatureType(String value) {
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
