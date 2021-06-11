/**
 * Copyright (C), 2018-2020,drondea FileName: EnterpriseUserPriorityLevel Author:   20180512 Date:     2020/3/21 17:15
 * Description: 启用用户优先级 History:
 */
package com.hero.wireless.enums;

public enum Priority {
	// 优先级高
	HIGH_LEVEL(3),
	// 中
	MIDDLE_LEVEL(2),
	// 低
	LOW_LEVEL(1);

	int value = 2;

	Priority(int value) {
		this.value = value;
	}

	public int value() {
		return value;
	}

	public static Priority valueOf(Integer priority) {
		if (null == priority) {
			return MIDDLE_LEVEL;
		}
		if (priority <= 1) {
			return LOW_LEVEL;
		}
		if (priority >= 3) {
			return HIGH_LEVEL;
		}
		return MIDDLE_LEVEL;
	}
}
