package com.hero.wireless.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * 充值类型
 */
public enum ChargeType {
    ZFB_F2F_SWEEP_PAY //支付宝
    , NATIVE //微信
    , FAILEDTORETURN //失败返还
    , ARREARS //线下欠款
    , TRANSFER //转账
    , CHECK //支票
    , CASH //现金
    , USER2USER //余额划拨
    ;
	private String value;

	private ChargeType() {
		this.value = this.name().toLowerCase();
	}

	private ChargeType(String value) {
		this.value = value.toLowerCase();
	}

	public String toString() {
		return value.toLowerCase();
	}

	public boolean equals(String value) {
		if (StringUtils.isEmpty(value)) {
			return false;
		}
		return this.value.equalsIgnoreCase(value);
	}
}
