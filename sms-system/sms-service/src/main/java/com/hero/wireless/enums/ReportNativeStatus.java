package com.hero.wireless.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * 自定义状态，返回给客户端
 * 
 * @author volcano
 * @date 2019年9月13日上午12:11:35
 * @version V1.0
 */
public enum ReportNativeStatus {
	DELIVRD, UNDELIVRD, 
	DD0001, 
	DD0002, 
	DD0003, 
	DD0004, 
	DD0005, 
	DD0006,
	DD0007,
	DD0008, 
	DD0009,
	DD0010, 
	DD0011, //归属地
	DD0012, //固定签名通道无签名
	DD0099;
	public boolean equals(String value) {
		if (StringUtils.isEmpty(value)) {
			return false;
		}
		return this.name().equals(value);
	}

	public String toString() {
		return this.name();
	}
}
