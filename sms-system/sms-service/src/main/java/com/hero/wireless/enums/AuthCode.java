package com.hero.wireless.enums;

/**
 * 认证状态:00:未认证 01：认证通过 02：认证拒绝
 * 
 */
public enum AuthCode {
	// 未认证
    NOTAUTH("00"),
	// 认证通过
    PASSED("01"),
	// 认证拒绝
    REFUSED("02");

	private String value;

	private AuthCode(String value) {
		this.value = value;
	}

	public String toString() {
        return value;
    }

}
