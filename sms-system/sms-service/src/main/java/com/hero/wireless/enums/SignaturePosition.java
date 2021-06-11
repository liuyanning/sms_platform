package com.hero.wireless.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * 签名位置
 * 
 * @author volcano
 * @date 2019年9月26日下午3:31:50
 * @version V1.0
 */
public enum SignaturePosition {
	// 后缀
	SUFFIX,
	// 前缀
	PREFIX;
	private String value;

	private SignaturePosition() {
		this.value = this.name().toLowerCase();
	}

	private SignaturePosition(String value) {
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
