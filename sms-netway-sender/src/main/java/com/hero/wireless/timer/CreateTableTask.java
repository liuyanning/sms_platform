package com.hero.wireless.timer;

import com.drondea.wireless.util.DateTime;
import com.hero.wireless.sms.sender.service.TableDDLService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Date;

/**
 * @version V3.0.0
 * @description: 定时创建分片表
 * @author: 刘彦宁
 * @date: 2021年02月01日11:32
 **/
@Component
public class CreateTableTask {

    @Resource
    private TableDDLService tableDDLService;

    /**
     * 每天6点10分执行,创建下周一到下周日的send库中分片的表
     */
    @Scheduled(cron = "0 10 6 * * ?")
    public void execute() {
        // 生成下周的表，按天分片
        createTableNextWeek();
    }

    /**
     * 项目启动创建近10天的表
     */
    @PostConstruct
    private void initCreateTable() {
        Date nextWeekMonday =  DateTime.addDay(new Date(), -3);
        for (int i = 0; i < 10; i++) {
            Date nextDay = DateTime.addDay(nextWeekMonday, i + 1);
            createSenderTable(DateTime.formatDate(nextDay, DateTime.Y_M_D_2));
        }
    }


    private void createTableNextWeek() {
        Date nextWeekMonday = DateTime.getNextWeekMonday(new Date());
        createSenderTable(DateTime.formatDate(nextWeekMonday, DateTime.Y_M_D_2));
        for (int i = 0; i < 6; i++) {
            Date nextDay = DateTime.addDay(nextWeekMonday, i + 1);
            createSenderTable(DateTime.formatDate(nextDay, DateTime.Y_M_D_2));
        }
    }

    /**
     * 创建sender库的表
     * @param tableSuffix
     */
    private void createSenderTable(String tableSuffix) {
        tableDDLService.createInputLogTable(tableSuffix);
        tableDDLService.createSubmitTable(tableSuffix);
        tableDDLService.createReportTable(tableSuffix);
        tableDDLService.createReportNotifyTable(tableSuffix);
    }

}
