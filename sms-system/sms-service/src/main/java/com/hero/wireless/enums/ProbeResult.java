package com.hero.wireless.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * 探测结果
 * 
 * @author gengjinbiao
 * @date 2019年11月9日
 * @version V1.0
 */
public enum ProbeResult {
	NORMAL, ABNORMAL;
	private String value;

	private ProbeResult() {
		this.value = this.name().toLowerCase();
	}

	private ProbeResult(String value) {
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
