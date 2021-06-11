package com.hero.wireless.web.dao.ibatis.send.ext;

import com.hero.wireless.web.dao.ibatis.MybatisBaseSendExtDao;
import com.hero.wireless.web.dao.send.ext.IInputLogExtDAO;
import com.hero.wireless.web.entity.base.ShardingBatchInsert;
import com.hero.wireless.web.entity.ext.SqlStatisticsEntity;
import com.hero.wireless.web.entity.send.InputLog;
import com.hero.wireless.web.entity.send.InputLogExample;
import com.hero.wireless.web.entity.send.ext.InputLogExt;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository("inputLogExtDAO")
public class InputLogExtDAOImpl extends MybatisBaseSendExtDao<InputLogExt, InputLogExample, InputLog> implements
        IInputLogExtDAO {
    @Override
    public List<InputLogExt> queryStatisticsListByExt(InputLogExt inputLogExt) {
        List<InputLogExt> list = getSqlSendSessionTemplate().selectList("queryStatisticsListByExt", inputLogExt);
        return list;
    }

    @Override
    public int countStatisticsListByExt(InputLogExt inputLogExt) {
        return getSqlSendSessionTemplate().selectOne("countStatisticsListByExt", inputLogExt);
    }

    @Override
    public SqlStatisticsEntity statisticsExtByExt(InputLogExt inputLogExt) {
        return null;
    }

    @Override
    public SqlStatisticsEntity statisticsExtByExample(InputLogExample example) {
        return getSqlSendSessionTemplate().selectOne("statisticsExtByExample", example);
    }

    @Override
    public List<Map<String, Object>> queryInputLogListForExportPage(InputLogExample example) {
        return getSqlSendSessionTemplate().selectList("queryInputLogListForExportPage", example);
    }

    @Override
    public int insertHistoryInputLogBySelectSend(InputLogExample inputLogExt) {
        return getSqlSendSessionTemplate().insert("insertHistoryInputLogBySelectSend", inputLogExt);
    }

    @Override
    public long selectInputLogMaxIdByExample(InputLogExample example) {
        return getSqlSendSessionTemplate().selectOne("selectInputLogMaxIdByExample", example);
    }

    @Override
    public int insertListSharding(ShardingBatchInsert<InputLog> shardingBatchInse) {
        return getSqlSendNoShardingSessionTemplate().insert("insertInputLogListSharding", shardingBatchInse);
    }

    @Override
    public int createInputLogTable(InputLogExt inputLogExt) {
        return getSqlSendNoShardingSessionTemplate().update("createInputLogTable", inputLogExt);
    }

    @Override
    public List<InputLog> selectInputLogByLimit(InputLogExample example) {
        return getSqlSendSessionTemplate().selectList("selectInputLogByLimit", example);
    }

    @Override
    public List<InputLog> selectInputLogHistoryByLimit(InputLogExample example) {
        return getSqlSendNoShardingSessionTemplate().selectList("selectInputLogHistoryByLimit", example);
    }

    @Override
    public List<InputLog> selectInputLogHistoryByLimitV2(InputLogExample example) {
        return getSqlSendNoShardingSessionTemplate().selectList("selectInputLogHistoryByLimitV2", example);
    }

    @Override
    public int dropTable(InputLogExt inputLogExt) {
        return getSqlSendNoShardingSessionTemplate().update("dropInputLogTable", inputLogExt);
    }
}
