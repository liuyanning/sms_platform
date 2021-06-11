package com.hero.wireless.enums;

/**
 * 数据库
 */
public enum DataBaseType {
    BUSINESS("s_business"),
    CUSTOMIZE("s_customize"),
    SEND("s_send"),
    HISTORY("s_history");

	private String value;

	private DataBaseType(String value) {
		this.value = value;
	}

	public String toString() {
        return value;
    }

}
