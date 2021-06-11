package com.hero.wireless.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * 编码
 * 
 * @author volcano
 * @date 2019年9月12日下午11:16:32
 * @version V1.0
 */
public enum SubmitStatus {
	SUCCESS, FAILD, UNKNOW,
	// 分拣
	SORT_FAILD;
	private String value;

	private SubmitStatus() {
		this.value = this.name().toLowerCase();
	}

	private SubmitStatus(String value) {
		this.value = value.toLowerCase();
	}

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
