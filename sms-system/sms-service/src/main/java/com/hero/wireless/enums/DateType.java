package com.hero.wireless.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * 日期类型
 * 
 * @author lyh
 * @date 2020年6月05日上午9:36:08
 * @version V1.0
 */
public enum DateType {
	//日
	DAY,
	//周
	WEEK,
	//月
	MONTH,
	//季度
	QUARTER,
	//年
	YEAR;
	private String value;

	private DateType() {
		this.value = this.name().toLowerCase();
	}

	private DateType(String value) {
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
