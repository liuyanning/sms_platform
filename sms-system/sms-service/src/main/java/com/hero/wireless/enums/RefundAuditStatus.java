package com.hero.wireless.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * 失败返还是否审核
 */
public enum RefundAuditStatus {
	/** 是 */
	TRUE,
	/** 否 */
	FALSE
	;
	private String value;

	private RefundAuditStatus() {
		this.value = this.name().toLowerCase();
	}

	private RefundAuditStatus(String value) {
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
