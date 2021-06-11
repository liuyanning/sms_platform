package com.hero.wireless.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * 短信内容审核类型
 * 
 * @author volcano
 * @date 2019年9月13日上午2:22:21
 * @version V1.0
 */
public enum ContentAuditType {
	/** 审核 */
	AUDIT,
	/** 免审 */
	NO_AUDIT,
	/** 模板审核 */
	TEMPLATE,
	;
	private String value;

	private ContentAuditType() {
		this.value = this.name().toLowerCase();
	}

	private ContentAuditType(String value) {
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
