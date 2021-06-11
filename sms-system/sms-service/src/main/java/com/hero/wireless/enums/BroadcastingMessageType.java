package com.hero.wireless.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 * Title: BroadcastingMessageType Description:广播消息任务类型
 * 
 * @author yjb
 * @date 2020-02-10
 */
public enum BroadcastingMessageType {

	SYSTEM_CACHE_DATA,
	/** 更新敏感字 */
	SENSITIVE_WORD_UPDATE,
	/** 导流策略*/
	DIVERSION_STRATEGY,
	
	/** 导流策略，主叫池，数据量太大，需要单独分离*/
	DIVERSION_STRATEGY_CALLER_POOL,

	/** 号码路由 */
	SMS_ROUTE_UPDATE,
	/** 白名单更新 **/
	SMS_WHITE_UPDATE;

	private String value;

	private BroadcastingMessageType() {
		this.value = this.name().toLowerCase();
	}

	private BroadcastingMessageType(String value) {
		this.value = value.toLowerCase();
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
