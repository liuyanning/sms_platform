package com.hero.wireless.web.dao.ibatis.send.ext;

import com.hero.wireless.web.dao.ibatis.MybatisBaseSendExtDao;
import com.hero.wireless.web.dao.send.ext.IReportExtDAO;
import com.hero.wireless.web.entity.base.ShardingBatchInsert;
import com.hero.wireless.web.entity.business.SmsStatistics;
import com.hero.wireless.web.entity.business.ext.SmsRealTimeStatisticsExt;
import com.hero.wireless.web.entity.business.ext.SmsStatisticsExt;
import com.hero.wireless.web.entity.ext.SqlStatisticsEntity;
import com.hero.wireless.web.entity.send.Report;
import com.hero.wireless.web.entity.send.ReportExample;
import com.hero.wireless.web.entity.send.ext.ReportExt;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository("reportExtDAO")
public class ReportExtDAOImpl extends MybatisBaseSendExtDao<ReportExt, ReportExample, Report> implements IReportExtDAO {

    @Override
    public void deleteReportByEntityList(List<Report> recordList) {
        getSqlSendSessionTemplate().delete("deleteReportByEntityList", recordList);
    }

    @Override
    public void updateReportList(List<Report> reportList) {
        getSqlSendSessionTemplate().update("updateReportList", reportList);
    }

    @Override
    public List<Map<String, Object>> queryReportListForExportPage(ReportExample example) {
        return getSqlSendSessionTemplate().selectList("queryReportListForExportPage", example);
    }

    @Override
    public SqlStatisticsEntity statisticsExtByExample(ReportExample example) {
        return getSqlSendSessionTemplate().selectOne("reportStatisticsExtByExample", example);
    }

    @Override
    public List<SmsRealTimeStatisticsExt> querySmsRealTimeStatisticsReportData(ReportExt reportExt) {
        return getSqlSendSessionTemplate().selectList("querySmsRealTimeStatisticsReportData", reportExt);
    }

    @Override
    public int insertHistoryReportBySelectSend(ReportExample example) {
        return getSqlSendSessionTemplate().insert("insertHistoryReportBySelectSend", example);
    }

    @Override
    public long selectReportMaxIdByExample(ReportExample example) {
        return getSqlSendSessionTemplate().selectOne("selectReportMaxIdByExample", example);
    }

    @Override
    public List<SmsRealTimeStatisticsExt> queryUnknownStatisticListReportData(SmsRealTimeStatisticsExt statisticsExt) {
        return getSqlSendSessionTemplate().selectList("queryUnknownStatisticListReportData", statisticsExt);
    }

    @Override
    public int insertListSharding(ShardingBatchInsert<Report> shardingBatchInsert) {
        return getSqlSendNoShardingSessionTemplate().insert("insertReportListSharding", shardingBatchInsert);
    }

    @Override
    public List<SmsStatistics> getReportSmsStatisticsByExt(SmsStatisticsExt smsStatisticsExt) {
        return getSqlSendSessionTemplate().selectList("getReportSmsStatisticsByExt",smsStatisticsExt);
    }

    @Override
    public int createReportTable(ReportExt reportExt) {
        return getSqlSendNoShardingSessionTemplate().update("createReportTable", reportExt);
    }

    @Override
    public List<Report> selectReportByLimit(ReportExample example) {
        return getSqlSendSessionTemplate().selectList("selectReportByLimit", example);
    }

    @Override
    public List<Report> selectReportHistoryByLimit(ReportExample example) {
        return getSqlSendNoShardingSessionTemplate().selectList("selectReportHistoryByLimit", example);
    }

    @Override
    public List<Map<String, Object>> selectReportHistoryByLimitV2(ReportExt reportExt) {
        return getSqlSendNoShardingSessionTemplate().selectList("selectReportHistoryByLimitV2", reportExt);
    }

    @Override
    public List<ReportExt> queryReportNativeStatus(ReportExt reportExt) {
        return getSqlSendSessionTemplate().selectList("queryReportNativeStatusFromSend", reportExt);
    }

    @Override
    public List<ReportExt> querySendCountDetailFromReport(ReportExt reportExt) {
        return getSqlSendSessionTemplate().selectList("querySendCountDetailFromReport", reportExt);
    }

    @Override
    public int dropTable(ReportExt reportExt) {
        return getSqlSendNoShardingSessionTemplate().update("dropReportTable", reportExt);
    }

    @Override
    public List<Report> queryReportNotifyAwaitList(ReportExample example) {
        return getSqlSendSessionTemplate().selectList("queryReportNotifyAwaitList", example);
    }

}
