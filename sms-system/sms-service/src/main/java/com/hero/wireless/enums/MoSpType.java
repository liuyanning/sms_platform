package com.hero.wireless.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * MO sp号码 格式
 * 
 * @author volcano
 * @date 2019年11月22日下午1:02:47
 * @version V1.0
 */
public enum MoSpType {
	//虚拟码号+导流码号+用户码号+自定义
	FULL("00"),
	//虚拟码号+自定义
	VIRTUAL_CUSTOM("01");
	private String value;

	private MoSpType() {
		this.value = this.name().toLowerCase();
	}

	private MoSpType(String value) {
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

	public static void main(String[] args) {
		System.out.println(MoSpType.valueOf("SMS"));
	}
}
