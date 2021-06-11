package com.hero.wireless.web.service;


import com.drondea.wireless.util.DateTime;
import com.drondea.wireless.util.SuperLogger;
import com.hero.wireless.enums.DataBaseType;
import com.hero.wireless.web.config.DatabaseCache;
import com.hero.wireless.web.service.base.BaseTaskManage;
import com.hero.wireless.web.service.param.TaskParam;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service("taskManage")
public class TaskManageImpl extends BaseTaskManage implements ITaskManage {


    /**
     * 数据统计定时（web端手动执行）
     *
     * @param task
     */
    @Override
    public void smsStatisticsDataFromWeb(TaskParam task) {
        checkSmsStatisticsData(task);
        Date minDate = task.getMinDate();
        //第二天0点
        Date maxDate = DateTime.getTheDayBeforeMinDate(minDate, 1);
        //执行
        new Thread(() -> {
            long start = System.currentTimeMillis();
            int total = doSmsStatisticsData(minDate, maxDate, task.getDatabase());
            long end = System.currentTimeMillis();
            SuperLogger.error("数据汇总执行完成,耗时：" + (end - start) / 1000 + "秒,数据：" + total + "条");
        }).start();
    }

    /**
     * 数据统计定时（timer自动执行）
     */
    @Override
    public int smsStatisticsDataFromTimer() {
        int dayData = DatabaseCache.getIntValueBySortCodeAndCode("sys_performance_setup", "report_acquisition_interval",
                4);
        Date startDate = DateTime.getTheDayBeforeMinDate(-dayData);
        Date endDate = DateTime.getTheDayBeforeMinDate(-dayData + 1);
        return doSmsStatisticsData(startDate, endDate, DataBaseType.SEND.toString());//默认从send库取数据
    }

    /**
     * 失败返还（web调用）
     */
    @Override
    public void smsSendFailedFromWeb(TaskParam task) {
        checkSmsSendFailed(task);
        //执行
        new Thread(() -> {
            long start = System.currentTimeMillis();
            int total = doSmsSendFailed(task.getDate());
            long end = System.currentTimeMillis();
            SuperLogger.error("失败返还执行完成,耗时：" + (end - start) / 1000 + "秒,数据：" + total + "条");
        }).start();
    }

    /**
     * 失败返还（timer调用）
     */
    @Override
    public int smsSendFailedFromTimer(String date) {
        return doSmsSendFailed(date);
    }

}
