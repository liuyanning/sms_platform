package com.hero.wireless.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * 产品状态
 * 
 */
public enum ProductStatus {
	START, STOP;
	private String value;

	private ProductStatus() {
		this.value = this.name().toLowerCase();
	}

	private ProductStatus(String value) {
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
