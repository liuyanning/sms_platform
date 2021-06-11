package com.hero.wireless.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * 素材审核状态
 * 
 * @author volcano
 * @date 2019年9月18日上午5:56:00
 * @version V1.0
 */
public enum MaterialApproveStatus {
	// 未审核
    UNAUDITED("0"),
	// 待送审
    WAITINGSENDAUDIT("1"),
	// 拒绝
    REFUSE("2"),
    // 送审核
    SENDAUDIT("3"),
    // 通过
    PASS("4");
	private String value;

	private MaterialApproveStatus(String value) {
		this.value = value;
	}

	public String value() {
		return value;
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
