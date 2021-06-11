package com.hero.wireless.timer;

import com.drondea.wireless.util.DateTime;
import com.drondea.wireless.util.SuperLogger;
import com.hero.wireless.web.service.IStatisticsManage;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 统计send库的submit和report，按多字段分组
 * 	sms_real_time_statistics表数据
 */
@Component
public class SmsRealTimeStatisticsTask extends OperatDataBase {

	@Resource(name = "statisticsManage")
	private IStatisticsManage statisticsManage;

	//每个整点过1分执行
	@Scheduled(cron = "0 1 0/1 * * ?")
//	@PostConstruct
	public void execute() {
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date startDate = formatter.parse(DateTime.getLastHourTime(new Date(), 1));
			Date endDate = formatter.parse(DateTime.getLastHourTime(new Date(), 0));
			smsRealTimeStatisticsData(startDate,endDate);
		}catch (Exception e){
			e.printStackTrace();
			SuperLogger.error(e);
		}
	}

	private void smsRealTimeStatisticsData(Date startDate, Date endDate) {
		long startTime = System.currentTimeMillis();
		statisticsManage.insertSmsRealTimeStatisticsData(startDate, endDate);
		long endTime = System.currentTimeMillis();
		saveSystemLog("整点发送统计", startTime, endTime, "");
	}

}
