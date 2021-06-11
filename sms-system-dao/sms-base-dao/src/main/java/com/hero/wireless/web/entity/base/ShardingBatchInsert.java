package com.hero.wireless.web.entity.base;

import java.util.List;

/**
 * 批量插入分库分表的对象
 * @author liuyanning
 */
public class ShardingBatchInsert<T> {

    String tableSuffix;
    List<T> dataList;

    public ShardingBatchInsert(String tableSuffix, List<T> dataList) {
        this.tableSuffix = tableSuffix;
        this.dataList = dataList;
    }

    public String getTableSuffix() {
        return tableSuffix;
    }

    public void setTableSuffix(String tableSuffix) {
        this.tableSuffix = tableSuffix;
    }

    public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }
}
