package com.hero.wireless.web.dao.ibatis.send.ext;

import com.hero.wireless.web.dao.ibatis.MybatisBaseSendExtDao;
import com.hero.wireless.web.dao.send.ext.ISubmitExtDAO;
import com.hero.wireless.web.entity.base.ShardingBatchInsert;
import com.hero.wireless.web.entity.business.SmsStatistics;
import com.hero.wireless.web.entity.business.ext.SmsRealTimeStatisticsExt;
import com.hero.wireless.web.entity.business.ext.SmsStatisticsExt;
import com.hero.wireless.web.entity.ext.SqlStatisticsEntity;
import com.hero.wireless.web.entity.send.Submit;
import com.hero.wireless.web.entity.send.SubmitExample;
import com.hero.wireless.web.entity.send.ext.SubmitExt;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository("submitExtDAO")
public class SubmitExtDAOImpl extends MybatisBaseSendExtDao<SubmitExt, SubmitExample, Submit> implements ISubmitExtDAO {

    @SuppressWarnings("unchecked")
    @Override
    public SqlStatisticsEntity statisticsExtByExample(SubmitExample example) {
        return getSqlSendSessionTemplate().selectOne("submitStatisticsExtByExample", example);
    }

    @Override
    public List<Map<String, Object>> querySubmitListForExportPage(SubmitExample example) {
        return getSqlSendSessionTemplate().selectList("querySubmitListForExportPage", example);
    }

    @Override
    public int insertHistorySubmitBySelectSend(SubmitExample submitExt) {
        return getSqlSendSessionTemplate().insert("insertHistorySubmitBySelectSend", submitExt);
    }

    @Override
    public List<SubmitExt> querySubmitUnknownList(SubmitExt condition) {
        return getSqlSendSessionTemplate().selectList("querySubmitUnknownList", condition);
    }

    @Override
    public List<Map<String, Object>> querySubmitReportUnknownListForExportPage(SubmitExt condition) {
        return getSqlSendSessionTemplate().selectList(
                "querySubmitReportUnknownListForExportPage", condition);
    }

    @Override
    public int querySubmitUnknownListCount(SubmitExt condition) {
        return getSqlSendSessionTemplate().selectOne("querySubmitUnknownListCount", condition);
    }

    @Override
    public List<SmsRealTimeStatisticsExt> querySmsRealTimeStatisticsSubmitData(SubmitExt condition) {
        return getSqlSendSessionTemplate().selectList("querySmsRealTimeStatisticsSubmitData", condition);
    }

    @Override
    public long selectSubmitMaxIdByExample(SubmitExample example) {
        return getSqlSendSessionTemplate().selectOne("selectSubmitMaxIdByExample", example);
    }

    @Override
    public List<SmsRealTimeStatisticsExt> queryUnknownStatisticListSubmitData(SmsRealTimeStatisticsExt condition) {
        return getSqlSendSessionTemplate().selectList("queryUnknownStatisticListSubmitData", condition);
    }

    @Override
    public List<String> querySubmitSmsUserIds(SubmitExt example) {
        return getSqlSendSessionTemplate().selectList("querySubmitSmsUserIds", example);
    }

    @Override
    public int insertListSharding(ShardingBatchInsert batchInsert) {
        return getSqlSendNoShardingSessionTemplate().insert("insertSubmitListSharding", batchInsert);
    }

    @Override
    public List<SmsStatistics> getSubmitSmsStatisticsByExt(SmsStatisticsExt smsStatisticsExt) {
        return getSqlSendSessionTemplate().selectList("getSubmitSmsStatisticsByExt", smsStatisticsExt);
    }

    @Override
    public int createSubmitTable(SubmitExt submitExt) {
        return getSqlSendNoShardingSessionTemplate().update("createSubmitTable", submitExt);
    }

    @Override
    public List<Submit> selectSubmitByLimit(SubmitExample example) {
        return getSqlSendSessionTemplate().selectList("selectSubmitByLimit", example);
    }

    @Override
    public List<Submit> selectSubmitHistoryByLimit(SubmitExample example) {
        return getSqlSendNoShardingSessionTemplate().selectList("selectSubmitHistoryByLimit", example);
    }

    @Override
    public List<Submit> selectSubmitHistoryByLimitV2(SubmitExt submitExt) {
        return getSqlSendNoShardingSessionTemplate().selectList("selectSubmitHistoryByLimitV2", submitExt);
    }

    @Override
    public List<SubmitExt> selectSendSpeedByExamplePage(SubmitExample example) {
        return getSqlSendSessionTemplate().selectList("querySendSpeedByExamplePage", example);
    }

    @Override
    public List<SubmitExt> querySendCountDetailFromSubmit(SubmitExt submitExt) {
        return getSqlSendSessionTemplate().selectList("querySendCountDetailFromSubmit", submitExt);
    }

    @Override
    public int dropTable(SubmitExt submitExt) {
        return getSqlSendNoShardingSessionTemplate().update("dropSubmitTable", submitExt);
    }

    @Override
    public List<Submit> querySubmitAwaitList(SubmitExample example) {
        return getSqlSendSessionTemplate().selectList("querySubmitAwaitList", example);
    }
}
