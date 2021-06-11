package com.hero.wireless.web.entity.base;

/**
 * @version V3.0.0
 * @description: 分片的分页对象
 * @author: 刘彦宁
 * @date: 2021年01月21日09:29
 **/
public class ShardingPagination extends Pagination {

    int limitBegin;

    public ShardingPagination(int limitBegin, int pageSize){
        this.limitBegin = limitBegin;
        setPageSize(pageSize);
    }



    public int getLimitBegin() {
        return limitBegin;
    }

    public void setLimitBegin(int limitBegin) {
        this.limitBegin = limitBegin;
    }

    @Override
    public int getFirstResult() {
        return limitBegin;
    }
}
