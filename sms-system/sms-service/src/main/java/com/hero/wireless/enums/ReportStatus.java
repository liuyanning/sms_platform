package com.hero.wireless.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * 编码
 * 
 * @author volcano
 * @date 2019年9月12日下午11:16:32
 * @version V1.0
 */
public enum ReportStatus {
	SUCCESS, FAILD, UNKNOWN, REJECTD;
	private String value;

	private ReportStatus() {
		this.value = this.name().toLowerCase();
	}

	private ReportStatus(String value) {
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
		return this.name().equals(value);
	}
}
