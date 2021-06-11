package com.hero.wireless.web.dao.ibatis.send.ext;

import com.hero.wireless.web.dao.ibatis.MybatisBaseSendExtDao;
import com.hero.wireless.web.dao.send.ext.IReportNotifyExtDAO;
import com.hero.wireless.web.entity.base.ShardingBatchInsert;
import com.hero.wireless.web.entity.send.ReportNotify;
import com.hero.wireless.web.entity.send.ReportNotifyExample;
import com.hero.wireless.web.entity.send.ext.ReportNotifyExt;
import org.springframework.stereotype.Repository;

/**
 * @author liuyanning
 */
@Repository("reportNotifyExtDAO")
public class ReportNotifyExtDAOImpl extends MybatisBaseSendExtDao<ReportNotifyExt, ReportNotifyExample, ReportNotify> implements IReportNotifyExtDAO {

    @Override
    public int insertListSharding(ShardingBatchInsert<ReportNotify> shardingBatchInsert) {
        return getSqlSendNoShardingSessionTemplate().insert("insertReportNotifyListSharding", shardingBatchInsert);
    }

    @Override
    public int dropTable(ReportNotifyExt reportNotifyExt) {
        return getSqlSendNoShardingSessionTemplate().update("dropTable", reportNotifyExt);
    }

    @Override
    public int createReportNotifyTable(ReportNotifyExt reportNotifyExt) {
        return getSqlSendNoShardingSessionTemplate().update("createReportNotifyTable", reportNotifyExt);
    }
}
