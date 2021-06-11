package com.hero.wireless.timer.alarm;

import com.drondea.wireless.util.DateTime;
import com.drondea.wireless.util.SuperLogger;
import com.hero.wireless.enums.AlarmType;
import com.hero.wireless.web.config.DatabaseCache;
import com.hero.wireless.web.entity.business.Alarm;
import com.hero.wireless.web.entity.business.AlarmLog;
import com.hero.wireless.web.entity.business.ext.AlarmExt;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.text.MessageFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 通道余额报警
 * @author gengjinbiao
 * @version V1.0
 * @date 2019年11月9日
 */
@Component
public class ChannelBalanceAlarm extends AlarmBase implements IAlarm{

	@Override
	@PostConstruct
	public boolean probe() {
		List<Alarm> list = getAlarmListByType(AlarmType.CHANNEL_BALANCE_ALARM.toString());
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
            if (alarm == null) return;
            String data = netwayManage.channelBalance(alarm.getBind_Value());
            Double balance ;
            try{
                Pattern pattern = Pattern.compile("(\\-|\\+?)[0-9|-|+|.]+");
                Matcher matcher = pattern.matcher(data);
                if (matcher.find()) {
                    balance = Double.valueOf(matcher.group(0));
                }else {
                    SuperLogger.error("++++++++========余额查询返回数据数据格式异常："+data);
                    return;
                }
            }catch (Exception e){
                e.printStackTrace();
                SuperLogger.error("++++++++========余额查询返回数据异常："+data+"异常："+e);
                return;
            }
            Boolean result = balance <= Double.valueOf(alarm.getThreshold_Value());
            String alarmTemplate = DatabaseCache.getAlarmSmsTemplate(AlarmType.CHANNEL_BALANCE_ALARM.toString()+ "_sms_template");
            String recoveryTemplate = DatabaseCache.getAlarmSmsTemplate(AlarmType.CHANNEL_BALANCE_ALARM.toString()+ "_recovery_sms_template");
            //组装参数
            try{
                AlarmExt alarmExt = new AlarmExt();
                BeanUtils.copyProperties(alarm,alarmExt);
                String channelName = DatabaseCache.getChannelByNo(alarm.getBind_Value())==null?alarm.getBind_Value():DatabaseCache.getChannelByNo(alarm.getBind_Value()).getName();
                alarmExt.setAlarm_Result(result);
                alarmExt.setAlarm_Email_Title("通道余额预警提醒");
                alarmExt.setAlarm_Content(MessageFormat.format(alarmTemplate , channelName));
                alarmExt.setRecovery_Email_Title("通道余额恢复正常提醒");
                alarmExt.setRecovery_Content(MessageFormat.format(recoveryTemplate , channelName));
                alarmExt.setMax_Create_Date(DateTime.getString(new Date()));
                alarmExt.setMin_Create_Date(DateTime.getCurentTimeBeforeMinutes(item.getProbe_Time()));
                //预警记录
                AlarmLog alarmLog = assemblyAlarmLog(alarmExt);
                alarmLog.setProbe_Value(balance+"");//探测值
                String des = alarmDescription("通道余额预警",alarmLog,balance+"",null);
                alarmLog.setDescription(des);//描述
                //执行
                executeAlarm(alarmExt,alarmLog);
            }catch (Exception e){
                SuperLogger.error(e.getMessage(),e);
            }
		}, 10L, delay, TimeUnit.MILLISECONDS);
	}



}
