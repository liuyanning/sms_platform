package com.hero.wireless.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * 预警类型
 *
 * @author gengjinbiao
 * @date 2019年11月9日
 * @version V1.0
 */
public enum AlarmType {

	/**账号余额预警*/
	ACCOUNT_BALANCE_ALARM,
	/**通道余额预警*/
	CHANNEL_BALANCE_ALARM,
	/**通道流速预警*/
	CHANNEL_VELOCITY_ALARM,
	/**通道提交成功率预警*/
	CHANNEL_SUBMIT_SUCCESS_RATE_ALARM,
	/**通道接收成功率预警*/
	CHANNEL_RECEPTION_SUCCESS_RATE_ALARM,
	/**通道回执率预警*/
	CHANNEL_RETURN_RATE_ALARM,
	/**通道5秒回执率预警*/
	CHANNEL_FIVE_SECOND_REPORT_RATE_ALARM,
	/**产品5秒回执率预警*/
	PRODUCT_FIVE_SECOND_REPORT_RATE_ALARM,
	/**通道回执状态码预警*/
	CHANNEL_REPORT_STATUS_CODE_ALARM,
	/**产品流速预警*/
	PRODUCT_VELOCITY_ALARM,
	/**产品提交成功率预警*/
	PRODUCT_SUBMIT_SUCCESS_RATE_ALARM,
	/**产品接收成功率预警*/
	PRODUCT_RECEPTION_SUCCESS_RATE_ALARM,
	/**产品回执率预警*/
	PRODUCT_RETURN_RATE_ALARM,
	/**服务器探测预警*/
	SERVER_STATUS_ALARM,
	/**待审核预警*/
	SENDED_APPROVE_INPUT_ALARM,
	/**短信审核完毕通知*/
	SENDED_APPROVE_INPUT_VELOCITY_ALARM,
	/**分拣队列数据堆积预警*/
	SORT_QUEUE_DATA_HEAP_UP_ALARM,
	/**提交发送队列数据堆积预警*/
	SUBMIT_QUEUE_DATA_HEAP_UP_ALARM,
	/**短信模板审核预警*/
    AUDIT_SMS_TEMPLATE_ALARM,
	/**用户户没有提交短信预警*/
	USER_NO_SUBMIT_SMS_ALARM;


	private String value;

	private AlarmType() {
		this.value = this.name().toLowerCase();
	}

	private AlarmType(String value) {
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
