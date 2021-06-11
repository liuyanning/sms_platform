package com.hero.wireless.timer.alarm;

import com.drondea.wireless.util.SuperLogger;
import com.hero.wireless.enums.AlarmType;
import com.hero.wireless.web.config.DatabaseCache;
import com.hero.wireless.web.entity.business.Alarm;
import com.hero.wireless.web.entity.business.AlarmLog;
import com.hero.wireless.web.entity.business.ext.AlarmExt;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 通道流量报警: （提交成功+提交失败）/ 时间间隔（秒数）跟 阈值做比较
 * @author gengjinbiao
 * @version V1.0
 * @date 2019年11月10日
 */
@Component
public class ChannelVelocityAlarm extends AlarmBase implements IAlarm {

	@Override
	@PostConstruct
	public boolean probe() {
		List<Alarm> list = getAlarmListByType(AlarmType.CHANNEL_VELOCITY_ALARM.toString());
		list.forEach(item -> {
			alarmCheck(item);
		});
		return false;
	}

    //任务调度
	public void alarmCheck(Alarm item){
	    long delay = item.getProbe_Time()*60*1000;
		SCHEDULED_EXECUTOR_SERVICE.scheduleWithFixedDelay(() -> {
			Alarm alarm = getStartAlarmById(item.getId());
            if (alarm == null) {
				return;
			}
			try{
				AlarmExt alarmExt = copyPropertiesAndAssembleArgument(alarm,delay);
				alarmExt.setChannel_No(alarm.getBind_Value());
				Integer count = timerExtDAO.getSubmitTotalForVelocity(alarmExt);
				Double unitCount = new BigDecimal(count==null?0:count).divide(new BigDecimal(alarmExt.getProbe_Time()*60), 4, BigDecimal.ROUND_HALF_UP).doubleValue();
				Boolean result = unitCount < Double.valueOf(alarm.getThreshold_Value());
				String alarmTemplate = DatabaseCache.getAlarmSmsTemplate(AlarmType.CHANNEL_VELOCITY_ALARM.toString()+ "_sms_template");
				String recoveryTemplate = DatabaseCache.getAlarmSmsTemplate(AlarmType.CHANNEL_VELOCITY_ALARM.toString()+ "_recovery_sms_template");
				//组装参数
				String channelName = DatabaseCache.getChannelByNo(alarm.getBind_Value())==null?alarm.getBind_Value():DatabaseCache.getChannelByNo(alarm.getBind_Value()).getName();
				alarmExt.setAlarm_Result(result);
				alarmExt.setAlarm_Email_Title("通道流量预警");
				alarmExt.setAlarm_Content(MessageFormat.format( alarmTemplate, channelName, alarm.getThreshold_Value()));
				alarmExt.setRecovery_Email_Title("通道流量恢复通知");
				alarmExt.setRecovery_Content(MessageFormat.format( recoveryTemplate, channelName, alarm.getThreshold_Value()));
                //预警记录
                AlarmLog alarmLog = assemblyAlarmLog(alarmExt);
                alarmLog.setProbe_Value(unitCount+"");//探测值
                String des = alarmDescription("通道流量预警",alarmLog
                        ,unitCount+"",null);
                alarmLog.setDescription(des);//描述
                //执行
                executeAlarm(alarmExt,alarmLog);
			}catch (Exception e){
                SuperLogger.error(e.getMessage(),e);
			}
		}, 10L, delay, TimeUnit.MILLISECONDS);
	}


}
