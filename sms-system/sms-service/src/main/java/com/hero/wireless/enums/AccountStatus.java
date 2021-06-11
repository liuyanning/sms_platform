package com.hero.wireless.enums;

/**
 * 账号状态
 * 
 * @author gengjinbiao
 * @date 2019年10月25日
 * @version V1.0
 */
public enum AccountStatus {
	// 锁定
	LOCKED("Locked"),
	// 正常
	NORMAL("Normal"),
	// 删除
	DELETE("Delete");

	private String value;

	private AccountStatus(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
        return value;
    }

}
