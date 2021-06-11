package com.hero.wireless.web.dao.send.ext;

import com.hero.wireless.web.dao.ext.IExtDAO;
import com.hero.wireless.web.dao.send.IInputLogDAO;
import com.hero.wireless.web.entity.base.ShardingBatchInsert;
import com.hero.wireless.web.entity.ext.SqlStatisticsEntity;
import com.hero.wireless.web.entity.send.InputLog;
import com.hero.wireless.web.entity.send.InputLogExample;
import com.hero.wireless.web.entity.send.ext.InputLogExt;

import java.util.List;
import java.util.Map;


public interface IInputLogExtDAO extends IInputLogDAO<InputLogExt>,IExtDAO<InputLogExt, InputLogExample> {

    List<InputLogExt> queryStatisticsListByExt(InputLogExt inputLogExt);

    int countStatisticsListByExt(InputLogExt inputLogExt);

    @Override
    SqlStatisticsEntity statisticsExtByExample(InputLogExample example);

    SqlStatisticsEntity statisticsExtByExt(InputLogExt inputLogExt);

    List<Map<String,Object>> queryInputLogListForExportPage(InputLogExample example);

    int insertHistoryInputLogBySelectSend(InputLogExample inputLogExt);

    long selectInputLogMaxIdByExample(InputLogExample example);

    int insertListSharding(ShardingBatchInsert<InputLog> shardingBatchInse);

    /**
     * 创建表
     * @param inputLogExt
     */
    int createInputLogTable(InputLogExt inputLogExt);

    /**
     * 导数据查询
     * @param example
     * @return
     */
    List<InputLog> selectInputLogByLimit(InputLogExample example);

    /**
     * history库数据查询, 3.0历史库迁移到lindorm使用
     * @param example
     * @return
     */
    List<InputLog> selectInputLogHistoryByLimit(InputLogExample example);

    /**
     * history库数据查询V2数据结构， 2.0历史库迁移到lindorm使用
     * @param example
     * @return
     */
    List<InputLog> selectInputLogHistoryByLimitV2(InputLogExample example);

    /**
     * 删除表
     * @param inputLogExt
     */
    int dropTable(InputLogExt inputLogExt);
}
