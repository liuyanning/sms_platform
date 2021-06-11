package com.hero.wireless.enums;

/**
 * 企业数据来源：00：平台添加 01：自主注册  02:工单派单
 */
public enum DataSourceType {
	// 平台添加
    PLATFORMADD("00"),
	// 自主注册
    REGISTERED("01"),
	// 工单派单
	WORKORDER("02");

	private String value;

	private DataSourceType(String value) {
		this.value = value;
	}

	public String toString() {
        return value;
    }

}
