package com.hero.wireless.timer.alarm;

import com.drondea.wireless.util.SuperLogger;
import com.hero.wireless.enums.AlarmType;
import com.hero.wireless.enums.ProbeResult;
import com.hero.wireless.web.config.DatabaseCache;
import com.hero.wireless.web.entity.business.Alarm;
import com.hero.wireless.web.entity.business.AlarmLog;
import com.hero.wireless.web.entity.business.ext.AlarmExt;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

/**
 * 用户余额报警
 * @author lyh
 * @version V1.0
 * @date 2021年04月23日
 */
@Component
public class AccountBalanceAlarm extends AlarmBase {

    //任务调度
    public void alarmCheck(Alarm alarm) {
        try{
            Boolean result = ProbeResult.ABNORMAL.toString().equals(alarm.getDescription());
            String alarmTemplate = DatabaseCache.getAlarmSmsTemplate(alarm.getType_Code()+ "_sms_template");
            String recoveryTemplate = DatabaseCache.getAlarmSmsTemplate(alarm.getType_Code()+ "_recovery_sms_template");
            //组装参数
            AlarmExt alarmExt = new AlarmExt();
            Alarm alm = getStartAlarmById(alarm.getId());
            if (alm == null) {
                return;
            }
            BeanUtils.copyProperties(alm,alarmExt);
            alarmExt.setAlarm_Result(result);
            alarmExt.setAlarm_Email_Title( "账号余额不足通知");
            alarmExt.setAlarm_Content(MessageFormat.format(alarmTemplate , alarm.getBind_Value()));
            alarmExt.setRecovery_Email_Title( "账号余额恢复通知");
            alarmExt.setRecovery_Content(MessageFormat.format(recoveryTemplate , alarm.getBind_Value()));
            //预警记录
            AlarmLog alarmLog = assemblyAlarmLog(alarmExt);
            alarmLog.setProbe_Value(alarm.getRemark());//探测值
            String des = alarmDescription("账号余额预警", alarmLog, alarm.getRemark(),null);
            alarmLog.setDescription(des);//描述
            //执行
            executeAlarm(alarmExt,alarmLog);
        }catch (Exception e){
            SuperLogger.error(e.getMessage(),e);
        }
    }
}
