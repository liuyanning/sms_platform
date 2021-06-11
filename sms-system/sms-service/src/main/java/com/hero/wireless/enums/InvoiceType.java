package com.hero.wireless.enums;

/**
 * 优先级
 * 
 * @author volcano
 * @date 2019年9月18日上午5:56:00
 * @version V1.0
 */
public enum InvoiceType {
	// 增值税专票
    DEDICATED("00"),
	// 增值税普票
    GENERAL("01");
	private String value;

	private InvoiceType(String value) {
		this.value = value;
	}

	public String value() {
		return value;
	}

}
