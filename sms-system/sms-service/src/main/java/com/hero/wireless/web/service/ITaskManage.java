package com.hero.wireless.web.service;

import com.hero.wireless.web.service.param.TaskParam;

/**
 *
 * 定时任务
 * @author Administrator
 *
 */
public interface ITaskManage {


    /**
     * 数据统计（web调用）
     */
    void smsStatisticsDataFromWeb(TaskParam task);

    /**
     * 数据统计（timer调用）
     */
    int smsStatisticsDataFromTimer();

    /**
     * 失败返还（web调用）
     */
    void smsSendFailedFromWeb(TaskParam task);

    /**
     * 失败返还（timer调用）
     */
    int smsSendFailedFromTimer(String date);


}