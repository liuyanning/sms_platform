package com.hero.wireless.web.dao.send.ext;

import com.hero.wireless.web.dao.ext.IExtDAO;
import com.hero.wireless.web.dao.send.ISubmitDAO;
import com.hero.wireless.web.entity.base.ShardingBatchInsert;
import com.hero.wireless.web.entity.business.SmsStatistics;
import com.hero.wireless.web.entity.business.ext.SmsRealTimeStatisticsExt;
import com.hero.wireless.web.entity.business.ext.SmsStatisticsExt;
import com.hero.wireless.web.entity.ext.SqlStatisticsEntity;
import com.hero.wireless.web.entity.send.Submit;
import com.hero.wireless.web.entity.send.SubmitExample;
import com.hero.wireless.web.entity.send.ext.SubmitExt;

import java.util.List;
import java.util.Map;

public interface ISubmitExtDAO extends ISubmitDAO<SubmitExt>,IExtDAO<SubmitExt, SubmitExample> {

    SqlStatisticsEntity statisticsExtByExample(SubmitExample example);

    List<Map<String,Object>> querySubmitListForExportPage(SubmitExample example);

    int insertHistorySubmitBySelectSend(SubmitExample submitExt);

    int querySubmitUnknownListCount(SubmitExt condition);

    List<SubmitExt> querySubmitUnknownList(SubmitExt condition);

    List<Map<String, Object>> querySubmitReportUnknownListForExportPage(SubmitExt condition);

    List<SmsRealTimeStatisticsExt> querySmsRealTimeStatisticsSubmitData(SubmitExt submitExt);

    long selectSubmitMaxIdByExample(SubmitExample example);

    List<SmsRealTimeStatisticsExt> queryUnknownStatisticListSubmitData(SmsRealTimeStatisticsExt statisticsExt);

    List<String> querySubmitSmsUserIds(SubmitExt submitExt);

    int insertListSharding(ShardingBatchInsert batchInsert);

    /**
     * 定时任务：提交记录表数据汇总查询(send库)
     * @param smsStatisticsExt
     * @return
     */
    List<SmsStatistics>  getSubmitSmsStatisticsByExt(SmsStatisticsExt smsStatisticsExt);

    /**
     * 创建表
     * @param submitExt
     */
    int createSubmitTable(SubmitExt submitExt);

    /**
     * 导数据到历史表使用
     * @param example
     * @return
     */
    List<Submit> selectSubmitByLimit(SubmitExample example);

    /**
     * 历史表查询使用（遗留）
     * @param example
     * @return
     */
    List<Submit> selectSubmitHistoryByLimit(SubmitExample example);

    /**
     * 历史表查询使用V2（遗留）
     * @param submitExt
     * @return
     */
    List<Submit> selectSubmitHistoryByLimitV2(SubmitExt submitExt);

    List<SubmitExt> selectSendSpeedByExamplePage(SubmitExample example);

    /**
     * 查询提交总数和提交成功数
     * @param submitExt
     * @return
     */
    List<SubmitExt> querySendCountDetailFromSubmit(SubmitExt submitExt);

    /**
     * 删除表
     * @param submitExt
     */
    int dropTable(SubmitExt submitExt);

    /**
     * 查询待提交短信数据
     * @param example
     * @return
     */
    List<Submit> querySubmitAwaitList(SubmitExample example);

}