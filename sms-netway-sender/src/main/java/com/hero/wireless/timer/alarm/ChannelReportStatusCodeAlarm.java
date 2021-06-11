package com.hero.wireless.timer.alarm;

import com.drondea.wireless.util.SuperLogger;
import com.hero.wireless.enums.AlarmType;
import com.hero.wireless.web.config.DatabaseCache;
import com.hero.wireless.web.entity.business.Alarm;
import com.hero.wireless.web.entity.business.AlarmLog;
import com.hero.wireless.web.entity.business.ext.AlarmExt;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.text.MessageFormat;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 通道回执状态码回执，当回执原生状态出现指定代码时预警
 *
 * @author gengjinbiao
 * @version V1.0
 * @date 2020年05月28日
 */
@Component
public class ChannelReportStatusCodeAlarm extends AlarmBase implements IAlarm {

    @Override
    @PostConstruct
    public boolean probe() {
        List<Alarm> list = getAlarmListByType(AlarmType.CHANNEL_REPORT_STATUS_CODE_ALARM.toString());
        list.forEach(item -> {
            alarmCheck(item);
        });
        return false;
    }

    public void alarmCheck(Alarm item) {
        long delay = item.getProbe_Time() * 60 * 1000;
        SCHEDULED_EXECUTOR_SERVICE.scheduleWithFixedDelay(() -> {
            Alarm alarm = getStartAlarmById(item.getId());
            if (alarm == null || DatabaseCache.getCodeList() == null) {
                return;
            }
            try {
                AlarmExt alarmExt = copyPropertiesAndAssembleArgument(alarm, delay);
                alarmExt.setChannel_No(alarm.getBind_Value());
                Integer num = timerExtDAO.getChannelReportStatusCode(alarmExt);
                Boolean result = num != null && num > 0;
                String alarmTemplate = DatabaseCache.getAlarmSmsTemplate(AlarmType.CHANNEL_REPORT_STATUS_CODE_ALARM.toString() + "_sms_template");
                String recoveryTemplate = DatabaseCache.getAlarmSmsTemplate(AlarmType.CHANNEL_REPORT_STATUS_CODE_ALARM.toString() + "_recovery_sms_template");
                String channelName = DatabaseCache.getChannelByNo(alarm.getBind_Value()) == null ? alarm.getBind_Value() : DatabaseCache.getChannelByNo(alarm.getBind_Value()).getName();
                alarmExt.setAlarm_Result(result);
                alarmExt.setAlarm_Email_Title("通道回执状态码预警");
                alarmExt.setAlarm_Content(MessageFormat.format(alarmTemplate, channelName, alarm.getThreshold_Value()));
                alarmExt.setRecovery_Email_Title("通道回执状态码恢复通知");
                alarmExt.setRecovery_Content(MessageFormat.format(recoveryTemplate, channelName, alarm.getThreshold_Value()));
                AlarmLog alarmLog = assemblyAlarmLog(alarmExt);
                alarmLog.setProbe_Value(alarmExt.getThreshold_Value());//探测值
                String des = alarmDescription("通道回执状态码预警", alarmLog, num + "条", null);
                alarmLog.setDescription(des);//描述
                executeAlarm(alarmExt, alarmLog);
            } catch (Exception e) {
                SuperLogger.error(e.getMessage(), e);
            }
        }, 10L, delay, TimeUnit.MILLISECONDS);
    }
}