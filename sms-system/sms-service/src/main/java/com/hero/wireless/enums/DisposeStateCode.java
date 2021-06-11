package com.hero.wireless.enums;

/**
 * 优先级
 * 
 * @author volcano
 * @date 2019年9月18日上午5:56:00
 * @version V1.0
 */
public enum DisposeStateCode {
	// 未处理
    UNDISPOSED("00"),
	// 已处理
    DISPOSED("01"),
	// 拒绝
    REFUSE("02");
	private String value;

	private DisposeStateCode(String value) {
		this.value = value;
	}

	public String value() {
		return value;
	}

}
