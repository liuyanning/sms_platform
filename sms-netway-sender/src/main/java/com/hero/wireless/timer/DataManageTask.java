package com.hero.wireless.timer;

import com.drondea.wireless.util.DateTime;
import com.drondea.wireless.util.SuperLogger;
import com.hero.wireless.web.config.DatabaseCache;
import com.hero.wireless.web.dao.business.ISmsRealTimeStatisticsDAO;
import com.hero.wireless.web.dao.business.ISystemLogDAO;
import com.hero.wireless.web.entity.base.Pagination;
import com.hero.wireless.web.entity.business.SmsRealTimeStatisticsExample;
import com.hero.wireless.web.entity.business.SystemLog;
import com.hero.wireless.web.entity.business.SystemLogExample;
import com.hero.wireless.web.service.ITaskManage;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 统计
 *
 * @author gengjinbiao 2019-08-07
 */
@Component
public class DataManageTask extends OperatDataBase {

    @Resource(name = "ISystemLogDAO")
    private ISystemLogDAO<SystemLog> systemLogDAO;
    @Resource(name = "taskManage")
    private ITaskManage taskManage;
    @Resource(name = "ISmsRealTimeStatisticsDAO")
    private ISmsRealTimeStatisticsDAO smsRealTimeStatisticsDAO;

    /**
     * 每天3点5分执行
     */
    @Scheduled(cron = "0 5 3 * * ?")
    public void execute() {
        // 统计
        smsStatisticsData();
        // 返还发送失败
        smsSendFailed();

        // 删除日志数据
        new Thread(this::removeSystemLogData).start();
        // 删除SmsRealTimeStatistics
        new Thread(this::removeSmsRealTimeStatisticsData).start();
    }

    /**
     * 数据汇总
     * 1、获得当前时间的前一天0点 <br>
     * 2、每次加30分钟 <br>
     * 3、循环去查询 <br>
     * 4、将查询出来的数据放到map中 <br>
     * 5、将数据汇总 <br>
     * 6、将汇总的数据插入到数据库
     */
    private void smsStatisticsData() {
        try {
            long startTime = System.currentTimeMillis();
            int total = taskManage.smsStatisticsDataFromTimer();
            long endTime = System.currentTimeMillis();
            saveSystemLog("短信日报表数据汇总", startTime, endTime, "数据条数：" + total);
        } catch (Exception e) {
            SuperLogger.error("数据汇总定时异常：", e);
        }
    }

    //失败返还
    public void smsSendFailed() {
        try {
            long startTime = System.currentTimeMillis();// 开始时间
            int dayData = DatabaseCache.getIntValueBySortCodeAndCode("sys_performance_setup",
                    "report_acquisition_interval", 4);
            Date date = DateTime.getTheDayBeforeMinDate(-dayData);
            String dateStr = DateTime.getString(date, DateTime.Y_M_D_1);
            int total = taskManage.smsSendFailedFromTimer(dateStr);
            long endTime = System.currentTimeMillis();// 结束时间
            saveSystemLog("失败返还定时：SmsSendFailedMigrater ", startTime, endTime, "返还数据所属日期：" + DateTime.getString(date, DateTime.Y_M_D_1) + "数据条数：" + total);
        } catch (Exception e) {
            SuperLogger.error("失败返还异常", e);
        }
    }

    //删除系统日志定时
    private void removeSystemLogData() {
        try {
            long startTime = System.currentTimeMillis();
            int logstorageday = DatabaseCache.getIntValueBySortCodeAndCode("sys_performance_setup", "logstorageday", 30);
            int MAX_PAGE_SIZE = DatabaseCache.getIntValueBySortCodeAndCode("sys_performance_setup", "datamigratepagesize", 500);
            while (true) {
                SystemLogExample sourceExample = new SystemLogExample();
                SystemLogExample.Criteria sourceCriteria = sourceExample.createCriteria();
                sourceCriteria.andCreate_DateLessThan(
                        DateTime.getTheDayBeforeMinDate(-logstorageday));
                Pagination pagination = new Pagination(MAX_PAGE_SIZE);
                sourceExample.setPagination(pagination);
                if (systemLogDAO.deleteByExample(sourceExample) == 0) {
                    break;
                }
            }
            long endTime = System.currentTimeMillis();
            saveSystemLog("system_log表数据清除数据", startTime, endTime, "");
        } catch (Exception e) {
            SuperLogger.error("删除系统日志异常", e);
        }
    }

    //删除SmsRealTimeStatistics定时
    private void removeSmsRealTimeStatisticsData() {
        try {
            long startTime = System.currentTimeMillis();
            int DATA_MIGRATE_DAY = DatabaseCache.getIntValueBySortCodeAndCode("sys_performance_setup", "data_migrate_day", -3);
            Date maxDate = DateTime.getTheDayBeforeMinDate(DATA_MIGRATE_DAY);
            SmsRealTimeStatisticsExample sourceExample = new SmsRealTimeStatisticsExample();
            SmsRealTimeStatisticsExample.Criteria sourceCriteria = sourceExample.createCriteria();
            sourceCriteria.andSubmit_DateLessThan(maxDate);
            smsRealTimeStatisticsDAO.deleteByExample(sourceExample);
            long endTime = System.currentTimeMillis();
            saveSystemLog("SmsRealTimeStatistics表数据清除数据", startTime, endTime, "");
        } catch (Exception e) {
            SuperLogger.error("删除SmsRealTimeStatistics异常", e);
        }
    }

}
