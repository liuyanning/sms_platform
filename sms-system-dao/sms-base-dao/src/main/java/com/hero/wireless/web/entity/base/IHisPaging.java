package com.hero.wireless.web.entity.base;

/**
 * @author liuyanning
 */
public interface IHisPaging {
    String getPagingState();

    void setPagingState(String pagingState);

    Integer getPageSize();

    void setPageSize(Integer pageSize);
}
