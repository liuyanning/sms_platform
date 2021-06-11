package com.hero.wireless.timer.alarm;

import com.drondea.wireless.util.SuperLogger;
import com.hero.wireless.enums.AlarmType;
import com.hero.wireless.enums.AuditStatus;
import com.hero.wireless.web.config.DatabaseCache;
import com.hero.wireless.web.entity.business.Alarm;
import com.hero.wireless.web.entity.business.AlarmLog;
import com.hero.wireless.web.entity.business.SmsTemplateExample;
import com.hero.wireless.web.entity.business.ext.AlarmExt;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.text.MessageFormat;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 短信模板审核预警：查询待审模板总数，大于阈值则预警
 *
 * @author ywj
 */
@Component
public class AuditSmsTemplateAlarm extends AlarmBase implements IAlarm {

    @Override
    @PostConstruct
    public boolean probe() {
        List<Alarm> list = getAlarmListByType(AlarmType.AUDIT_SMS_TEMPLATE_ALARM.toString());
        list.forEach(item -> {
            alarmCheck(item);
        });
        return false;
    }

    public void alarmCheck(Alarm item) {
        long delay = item.getProbe_Time() * 60 * 1000;
        SCHEDULED_EXECUTOR_SERVICE.scheduleAtFixedRate(() -> {
            try {
                Alarm alarm = getStartAlarmById(item.getId());
                if (alarm == null) {
                    return;
                }
                SmsTemplateExample example = new SmsTemplateExample();
                example.createCriteria().andApprove_StatusEqualTo(AuditStatus.UNAUDITED.toString());
                int count = smsTemplateDAO.countByExample(example);
                boolean result = count > Integer.valueOf(alarm.getThreshold_Value());
                String alarmTemplate = DatabaseCache.getAlarmSmsTemplate(AlarmType.AUDIT_SMS_TEMPLATE_ALARM.toString()+ "_sms_template");
                String recoveryTemplate = DatabaseCache.getAlarmSmsTemplate(AlarmType.AUDIT_SMS_TEMPLATE_ALARM.toString()+ "_recovery_sms_template");
                AlarmExt alarmExt = new AlarmExt();
                BeanUtils.copyProperties(alarm,alarmExt);
                alarmExt.setAlarm_Result(result);
                alarmExt.setAlarm_Email_Title("短信模板审核预警通知");
                alarmExt.setAlarm_Content(MessageFormat.format(alarmTemplate, count));
                alarmExt.setRecovery_Email_Title("短信模板审核恢复通知");
                alarmExt.setRecovery_Content(recoveryTemplate);
                AlarmLog alarmLog = assemblyAlarmLog(alarmExt);
                alarmLog.setProbe_Value(count+"");
                String des = alarmDescription("短信模板审核预警", alarmLog, count+"", null);
                alarmLog.setDescription(des);
                executeAlarm(alarmExt, alarmLog);
            } catch (Exception e) {
                SuperLogger.error(e.getMessage(), e);
            }
        }, 10L, delay, TimeUnit.MILLISECONDS);
    }
}
