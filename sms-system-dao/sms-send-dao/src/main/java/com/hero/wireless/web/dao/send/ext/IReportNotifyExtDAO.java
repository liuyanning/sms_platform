package com.hero.wireless.web.dao.send.ext;

import com.hero.wireless.web.dao.ext.IExtDAO;
import com.hero.wireless.web.dao.send.IReportNotifyDAO;
import com.hero.wireless.web.entity.base.ShardingBatchInsert;
import com.hero.wireless.web.entity.send.ReportNotify;
import com.hero.wireless.web.entity.send.ReportNotifyExample;
import com.hero.wireless.web.entity.send.ext.ReportNotifyExt;

/**
 * @author liuyanning
 */
public interface IReportNotifyExtDAO extends IReportNotifyDAO<ReportNotifyExt>,IExtDAO<ReportNotifyExt, ReportNotifyExample> {
    /**
     * 分库分表插入ReportNotify
     * @param shardingBatchInsert
     * @return
     */
    int insertListSharding(ShardingBatchInsert<ReportNotify> shardingBatchInsert);

    /**
     * 删除表
     * @param reportNotifyExt
     */
    int dropTable(ReportNotifyExt reportNotifyExt);


    /**
     * 创建表
     * @param reportNotifyExt
     */
    int createReportNotifyTable(ReportNotifyExt reportNotifyExt);

}