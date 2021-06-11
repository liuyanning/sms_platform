package com.hero.wireless.enums;

/**
 * 表名
 */
public enum TableType {
    INPUT_LOG("input_log"),
    REPORT("report"),
    SUBMIT("submit");

	private String value;

	private TableType(String value) {
		this.value = value;
	}

	public String toString() {
        return value;
    }

}
