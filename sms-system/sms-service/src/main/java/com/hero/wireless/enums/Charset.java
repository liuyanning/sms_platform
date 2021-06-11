package com.hero.wireless.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * 编码
 * 
 * @author volcano
 * @date 2019年9月12日下午11:16:32
 * @version V1.0
 */
public enum Charset {
	UTF8("UTF-8"), GBK, GB2312, ISO88591("ISO8859-1");
	private String value;

	private Charset() {
		this.value = this.name().toLowerCase();
	}

	private Charset(String value) {
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
