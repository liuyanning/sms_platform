package com.hero.wireless.web.dao.business.ext;


import com.hero.wireless.web.entity.business.ext.AlarmExt;

public interface ITimerExtDAO {

	/**
	 *  回执条数(成功)
	 * @param alarmExt
	 * @return
	 */
	Integer getReportSuccessTotalForReception(AlarmExt alarmExt);

	/**
	 * 回执条数(成功+失败)
	 * @param alarmExt
	 * @return
	 */
	Integer getReportTotalForReturnRate(AlarmExt alarmExt);

	/**
	 * 获取提交成功总条数（成功）
	 * @param alarmExt
	 * @return
	 */
	Integer getSubmitSuccessTotal(AlarmExt alarmExt);

	/**
	 * 获取提交总条数（成功+失败+分拣失败）
	 * @param alarmExt
	 * @return
	 */
	Integer getSubmitTotal(AlarmExt alarmExt);

	/**
	 * 获取发送流量提交总条数（成功+失败）
	 * @param alarmExt
	 * @return
	 */
	Integer getSubmitTotalForVelocity(AlarmExt alarmExt);

	/**
	 * 获取通道回执状态码
	 * @param alarmExt
	 * @return
	 */
    Integer getChannelReportStatusCode(AlarmExt alarmExt);
}
