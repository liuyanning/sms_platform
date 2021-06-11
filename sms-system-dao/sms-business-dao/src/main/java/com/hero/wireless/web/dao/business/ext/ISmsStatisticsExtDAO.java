package com.hero.wireless.web.dao.business.ext;

import com.hero.wireless.web.dao.business.ISmsStatisticsDAO;
import com.hero.wireless.web.dao.ext.IExtDAO;
import com.hero.wireless.web.entity.business.SmsStatisticsExample;
import com.hero.wireless.web.entity.business.ext.SmsStatisticsExt;

import java.util.List;
import java.util.Map;

public interface ISmsStatisticsExtDAO extends ISmsStatisticsDAO<SmsStatisticsExt>, IExtDAO<SmsStatisticsExt, SmsStatisticsExample> {

    List<SmsStatisticsExt> querySmsSendFailedByExt(SmsStatisticsExt smsStatisticsExt);

    List<Map<String, Object>> getSmsStatisticListForExport(SmsStatisticsExt smsStatisticsExt);

    Map<String, Object> countSmsStatisticExtListByExt(SmsStatisticsExt smsStatisticsExt);

    List<SmsStatisticsExt> getSmsStatisticExtListByExtPage(SmsStatisticsExt smsStatisticsExt);

//    /**
//     * 定时任务：提交记录表数据汇总查询(send库)
//     * @param smsStatisticsExt
//     * @return
//     */
//	List<SmsStatistics>  getSubmitSmsStatisticsByExt(SmsStatisticsExt smsStatisticsExt);

//	/**
//	 * 定时任务：回执表数据汇总查询(send库)
//	 * @param smsStatisticsExt
//	 * @return
//	 */
//	List<SmsStatistics>  getReportSmsStatisticsByExt(SmsStatisticsExt smsStatisticsExt);

//    /**
//     * 定时任务：提交记录表数据汇总查询(history库,web端调用)
//     * @param smsStatisticsExt
//     * @return
//     */
//    List<SmsStatistics> getSubmitSmsStatisticsByExtFromHistory(SmsStatisticsExt smsStatisticsExt);
//
//    /**
//     * 定时任务：回执表数据汇总查询(history库,web端调用)
//     * @param smsStatisticsExt
//     * @return
//     */
//    List<SmsStatistics> getReportSmsStatisticsByExtFromHistory(SmsStatisticsExt smsStatisticsExt);
}
