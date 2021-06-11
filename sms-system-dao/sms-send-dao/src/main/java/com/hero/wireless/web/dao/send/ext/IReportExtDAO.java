package com.hero.wireless.web.dao.send.ext;

import com.hero.wireless.web.dao.ext.IExtDAO;
import com.hero.wireless.web.dao.send.IReportDAO;
import com.hero.wireless.web.entity.base.ShardingBatchInsert;
import com.hero.wireless.web.entity.business.SmsStatistics;
import com.hero.wireless.web.entity.business.ext.SmsRealTimeStatisticsExt;
import com.hero.wireless.web.entity.business.ext.SmsStatisticsExt;
import com.hero.wireless.web.entity.ext.SqlStatisticsEntity;
import com.hero.wireless.web.entity.send.Report;
import com.hero.wireless.web.entity.send.ReportExample;
import com.hero.wireless.web.entity.send.ext.ReportExt;

import java.util.List;
import java.util.Map;

public interface IReportExtDAO extends IReportDAO<ReportExt>,IExtDAO<ReportExt, ReportExample> {
    void deleteReportByEntityList(List<Report> recordList);

    void updateReportList(List<Report> reportList);

    List<Map<String,Object>> queryReportListForExportPage(ReportExample example);
    
    @Override
    SqlStatisticsEntity statisticsExtByExample(ReportExample example);

    List<SmsRealTimeStatisticsExt> querySmsRealTimeStatisticsReportData(ReportExt reportExt);

    int insertHistoryReportBySelectSend(ReportExample selectExample);

    long selectReportMaxIdByExample(ReportExample selectExample);

    List<SmsRealTimeStatisticsExt> queryUnknownStatisticListReportData(SmsRealTimeStatisticsExt statisticsExt);

    int insertListSharding(ShardingBatchInsert<Report> shardingBatchInsert);

    /**
     * 定时任务：回执表数据汇总查询(send库)
     * @param smsStatisticsExt
     * @return
     */
    List<SmsStatistics>  getReportSmsStatisticsByExt(SmsStatisticsExt smsStatisticsExt);

    /**
     * 创建表
     * @param reportExt
     */
    int createReportTable(ReportExt reportExt);

    /**
     * 导数据查询
     * @param example
     * @return
     */
    List<Report> selectReportByLimit(ReportExample example);

    /**
     * history库查询
     * @param example
     * @return
     */
    List<Report> selectReportHistoryByLimit(ReportExample example);

    /**
     * history库查询,V2
     * @param reportExt
     * @return
     */
    List<Map<String, Object>> selectReportHistoryByLimitV2(ReportExt reportExt);

    /**
     * 查询回执错误码
     * @param reportExt
     * @return
     */
    List<ReportExt> queryReportNativeStatus(ReportExt reportExt);

    /**
     * 根据回执报告查询提交条数和发送成功条数
     * @param reportExt
     * @return
     */
    List<ReportExt> querySendCountDetailFromReport(ReportExt reportExt);

    /**
     * 删除表
     * @param reportExt
     */
    int dropTable(ReportExt reportExt);

    /**
     * 查询等待推送的状态报告
     * @param example
     * @return
     */
    List<Report> queryReportNotifyAwaitList(ReportExample example);

}