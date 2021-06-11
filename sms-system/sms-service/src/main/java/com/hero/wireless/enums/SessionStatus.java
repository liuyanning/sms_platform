package com.hero.wireless.enums;

import com.drondea.wireless.util.SuperLogger;
import org.apache.commons.lang3.StringUtils;

/**
 * 通道tcp会话状态
 * 
 * @author volcano
 * @date 2019年9月28日上午2:05:00
 * @version V1.0
 */
public enum SessionStatus {
	CONNECTED, DISCONNECT,
	// 登录成功
	LOGIN_SUCCESS,
	//登录完成
	LOGIN_COMPLETE,
	// 消息结构错误
	MESSAGE_ERROR,
	// 非法源地址
	SOURCE_IP_ERROR,
	// 认证错
	AUTHENTICATOR_ERROR,
	// 版本错
	VERSION_ERROR,
	// TCP错误
	TCP_TIME_OUT,
	// TCP参数错误
	TCP_PARAM_ERROR,
	// 最大连接数错误
	MAX_CHANNELS_ERROR,
	SYSTEM_BUSY,
	// 其他错误
	OTHER_ERROR,
	//
	;
	private String value;

	private SessionStatus() {
		this.value = this.name().toLowerCase();
	}

	private SessionStatus(String value) {
		this.value = value.toLowerCase();
	}

	@Override
	public String toString() {
		return value;
	}

	public static SessionStatus valueOf(byte v) {
		switch (v) {
			case 0:
				return LOGIN_SUCCESS;
			case 1:
				return MESSAGE_ERROR;
			case 2:
				return SOURCE_IP_ERROR;
			case 3:
				return AUTHENTICATOR_ERROR;
			case 4:
				return VERSION_ERROR;
			case 21:
				return MAX_CHANNELS_ERROR;
			default: {
				SuperLogger.error("登录失败，返回值：" + v);
				return OTHER_ERROR;
			}
		}
	}

	public static SessionStatus valueOfSMGP(byte v) {
		switch (v) {
			case 0:
				return LOGIN_SUCCESS;
			case 1:
				return SYSTEM_BUSY;
			case 2:
				return MAX_CHANNELS_ERROR;
			case 20:
				return SOURCE_IP_ERROR;
			case 21:
				return AUTHENTICATOR_ERROR;
			case 22:
				return VERSION_ERROR;
			default: {
				SuperLogger.error("登录失败，返回值：" + v);
				return OTHER_ERROR;
			}
		}
	}

	public static SessionStatus valueOfSMPP(byte v) {
		switch (v) {
			case 0:
				return LOGIN_SUCCESS;
			case 15:
				return SYSTEM_BUSY;
			case 10:
				return SOURCE_IP_ERROR;
			case 13:
				return MAX_CHANNELS_ERROR;
			case 14:
				return AUTHENTICATOR_ERROR;
			default: {
				SuperLogger.error("登录失败，返回值：" + v);
				return OTHER_ERROR;
			}
		}
	}

	public boolean equals(String value) {
		if (StringUtils.isEmpty(value)) {
			return false;
		}
		return this.value.equalsIgnoreCase(value);
	}

	public static void main(String[] args) {
		System.out.println(SessionStatus.valueOf((byte) 3));
	}
}
