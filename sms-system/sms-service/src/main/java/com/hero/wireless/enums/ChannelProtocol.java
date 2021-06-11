package com.hero.wireless.enums;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * 通道协议
 * 
 * @author volcano
 * @date 2019年9月12日下午11:16:40
 * @version V1.0
 */
public enum ChannelProtocol {
	HTTP, CMPP, SGIP, SMGP, SMPP;
	private String value;

	private ChannelProtocol() {
		this.value = this.name().toLowerCase();
	}

	private ChannelProtocol(String value) {
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
		if (!BooleanUtils.toBooleanDefaultIfNull(false, false)) {
			System.out.println(1);
		}

	}
}
