package com.hero.wireless.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * 短信内容审核状态
 * 
 * @author volcano
 * @date 2019年9月13日上午2:22:21
 * @version V1.0
 */
public enum ContentAuditStatus {
	/** 通过 */
	PASSED,
	/** 驳回 */
	REJECT,
	/** 审核中 */
	AUDITING,
	;
	private String value;

	private ContentAuditStatus() {
		this.value = this.name().toLowerCase();
	}

	private ContentAuditStatus(String value) {
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
