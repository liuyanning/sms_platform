package com.hero.wireless.timer;

import com.drondea.wireless.util.DateTime;
import com.hero.wireless.web.dao.business.ISystemLogDAO;
import com.hero.wireless.web.entity.business.SystemLog;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

@Component
public class OperatDataBase {

	@Resource(name = "ISystemLogDAO")
	private ISystemLogDAO<SystemLog> systemLogDAO;
	
	public void saveSystemLog(String tableName,long startTime,long endTime,String tips){
		SystemLog systemLog = new SystemLog();
		systemLog.setUser_Id(1);
		systemLog.setReal_Name("系统");
		systemLog.setUser_Name("system");
		systemLog.setModule_Name("定时任务");
		systemLog.setCreate_Date(new Date());
		systemLog.setOperate_Desc(tableName);
		systemLog.setSpecific_Desc("开始时间："+DateTime.getFormatDateByMS(startTime)+",结束时间："+DateTime.getFormatDateByMS(endTime)
				+",执行耗时="+DateTime.getFormatDurationByMS(endTime-startTime)+","+tips);
		systemLogDAO.insert(systemLog);
	}

}
