package com.hero.wireless.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * 结算类型
 * 
 * @author volcano
 * @date 2019年9月15日上午6:15:07
 * @version V1.0
 */
public enum SettlementType {
	// 预付费
	PREPAID,
	// 后付费
	POSTPAID;
	private String value;

	private SettlementType() {
		this.value = this.name().toLowerCase();
	}

	private SettlementType(String value) {
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
