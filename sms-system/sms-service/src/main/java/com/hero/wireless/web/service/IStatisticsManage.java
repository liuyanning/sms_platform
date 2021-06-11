package com.hero.wireless.web.service;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.hero.wireless.web.entity.business.ExportFile;
import com.hero.wireless.web.entity.business.PlatformSmsStatistics;
import com.hero.wireless.web.entity.business.ext.ExportFileExt;
import com.hero.wireless.web.entity.business.ext.PlatformSmsStatisticsExt;
import com.hero.wireless.web.entity.business.ext.SmsRealTimeStatisticsExt;
import com.hero.wireless.web.entity.business.ext.SmsStatisticsExt;
import com.hero.wireless.web.entity.send.ext.ReportExt;

public interface IStatisticsManage {

    /**
     * 查找发送失败数据
     *
     * @param smsStatisticsExt
     * @return
     */
    List<SmsStatisticsExt> querySmsSendFailedByExt(SmsStatisticsExt smsStatisticsExt);

    /**
     * 导出报表
     *
     * @param smsStatisticsExt
     * @return
     */
    List<Map<String , Object>> getSmsStatisticListForExport(SmsStatisticsExt smsStatisticsExt);

    /**
     * 导出报表
     *
     * @param platformSmsStatisticsExt
     * @return
     */
    List<Map<String , Object>> getPlatformStatisticListForExport(PlatformSmsStatisticsExt platformSmsStatisticsExt);

    /**
     * 导出报表
     *
     * @param smsStatisticsExt
     * @return
     */
    void exportSmsStatistic(String realPath, SmsStatisticsExt smsStatisticsExt, ExportFileExt exportFile,String exportType);

    /**
     * 报表统计数据条数
     *
     * @param smsStatisticsExt
     * @return
     */
    Map<String,Object> countSmsStatisticExtListByExt(SmsStatisticsExt smsStatisticsExt);

    /**
     * 报表统计(分页)
     *
     * @param smsStatisticsExt
     * @return
     */
    List<SmsStatisticsExt> getSmsStatisticExtListByExtPage(SmsStatisticsExt smsStatisticsExt);

    /**
     * 导出通道报表
     *
     * @param realPath
     * @param smsStatisticsExt
     * @param newExportFile
     * @return
     */
    void exportChannelStatisticList(String realPath, SmsStatisticsExt smsStatisticsExt, ExportFileExt newExportFile);

    /**
     * 导出企业报表
     *
     * @param realPath
     * @param smsStatisticsExt
     * @param newExportFile
     * @return
     */
    void exportEnterpriseStatisticList(String realPath, SmsStatisticsExt smsStatisticsExt, ExportFileExt newExportFile);


    /**
     * 导出商务报表
     *
     * @param realPath
     * @param smsStatisticsExt
     * @param newExportFile
     * @return
     */
    void exportBusinessStatisticList(String realPath, SmsStatisticsExt smsStatisticsExt, ExportFileExt newExportFile);

    /**
     * 导出短信日报表(客户平台)
     *
     * @param value
     * @param smsStatisticsExt
     * @param exportFile
     * @return
     */
    void exportSmsStatisticByEnterprise(String value, SmsStatisticsExt smsStatisticsExt, ExportFileExt exportFile);

    /**
     * 导出签名报表
     *
     * @param value
     * @param smsStatisticsExt
     * @param exportFile
     * @return
     */
    void exportSignatureStatisticList(String value, SmsStatisticsExt smsStatisticsExt, ExportFileExt exportFile);

    /**
     * 统计send库的submit和report，按多字段分组,插入smsRealTimeStatistics
     *
     * @param startDate
     * @param endDate
     * @return
     */
    void insertSmsRealTimeStatisticsData(Date startDate, Date endDate);

    /**
     * 保存各平台短信发送数据，插入PlatformSmsStatistics
     *
     * @param json
     * @return
     */
    int insertPlatformSmsStatisticsData(Map<String, String> json) throws IOException;

    /**
     * 导出各平台短信统计数据
     *
     * @param value
     * @param platformSmsStatisticsExt
     * @param exportFile
     * @param exportType
     * @return
     */
    void exportPlatformStatistic(String value, PlatformSmsStatisticsExt platformSmsStatisticsExt, ExportFileExt exportFile, String exportType);

    /**
     * 平台短信发送数据条数
     *
     * @param platformSmsStatisticsExt
     * @return
     */
    int countPlatformSmsStatisticsList(PlatformSmsStatisticsExt platformSmsStatisticsExt) throws ParseException;

    /**
     * 查询各平台短信发送数据
     *
     * @param platformSmsStatisticsExt
     * @return
     */
    List<PlatformSmsStatistics> queryPlatformSmsStatisticsList(PlatformSmsStatisticsExt platformSmsStatisticsExt) throws ParseException;


    /**
     * 短信管理》》短信日报列表数据
     *
     * @param statisticsExt
     * @return
     */
    List<SmsRealTimeStatisticsExt> querySmsRealTimeStatisticsGroupDataList(SmsRealTimeStatisticsExt statisticsExt) throws ParseException;

    /**
     * 短信管理》》短信日报列表数据条数
     *
     * @param statisticsExt
     * @return
     */
    Map<String,Object> countSmsRealTimeStatisticsGroupDataList(SmsRealTimeStatisticsExt statisticsExt) throws ParseException;

    /**
     * 三天之内 ==》导出列表
     *
     * @param value
     * @param statisticsExt
     * @param adminDefaultExportFile
     * @param platformFlagAdmin
     * @return
     */
    void exportsmsDailySendedStatisticList(String value, SmsRealTimeStatisticsExt statisticsExt, ExportFileExt adminDefaultExportFile, String platformFlagAdmin) throws ParseException;

    /**
     * 未知统计 
     *
     * @param statisticsExt
     * @return
     */
    List<SmsRealTimeStatisticsExt> queryUnknownStatisticList(SmsRealTimeStatisticsExt statisticsExt);

    /**
     *
     * @Title: queryReportNativeStatus
     * @Description: 查询send库回执里面的状态码明细
     * @author yjb
     * @param smsStatisticsExt
     * @return
     * @throws
     * @date 2020-09-25
     */
    List<ReportExt> queryReportNativeStatus(SmsStatisticsExt smsStatisticsExt);

}
