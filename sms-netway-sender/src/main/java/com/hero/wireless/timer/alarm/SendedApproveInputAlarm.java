package com.hero.wireless.timer.alarm;

import com.drondea.wireless.util.SuperLogger;
import com.hero.wireless.enums.AlarmStatus;
import com.hero.wireless.enums.AlarmType;
import com.hero.wireless.enums.ContentAuditStatus;
import com.hero.wireless.web.config.DatabaseCache;
import com.hero.wireless.web.entity.business.Alarm;
import com.hero.wireless.web.entity.business.AlarmLog;
import com.hero.wireless.web.entity.business.ext.AlarmExt;
import com.hero.wireless.web.entity.send.Input;
import com.hero.wireless.web.entity.send.InputExample;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class SendedApproveInputAlarm extends AlarmBase implements IAlarm {

    @Override
    @PostConstruct
    public boolean probe() {
        List<Alarm> list = getAlarmListByType(AlarmType.SENDED_APPROVE_INPUT_ALARM.toString());
        list.forEach(item -> {
            alarmCheck(item);
        });
        return false;
    }
    //任务调度
    public void alarmCheck(Alarm item) {
        long delay = item.getProbe_Time() * 60 * 1000;
        SCHEDULED_EXECUTOR_SERVICE.scheduleWithFixedDelay(() -> {
            AlarmExt condition = new AlarmExt();
            condition.setId(item.getId());
            condition.setStatus(AlarmStatus.START.toString());
            Alarm alarm = getStartAlarmById(item.getId());
            if (alarm == null) {
                return;
            }
            try {
                InputExample example = new InputExample();
                    example.createCriteria().andAudit_Status_CodeEqualTo(ContentAuditStatus.AUDITING.toString());
                    List<Input> inputList = inputDAO.selectByExample(example);
                Boolean result= inputList.size()> 0;
                String alarmTemplate = DatabaseCache.getAlarmSmsTemplate(AlarmType.SENDED_APPROVE_INPUT_ALARM.toString() + "_sms_template");
                String recoveryTemplate = DatabaseCache.getAlarmSmsTemplate(AlarmType.SENDED_APPROVE_INPUT_VELOCITY_ALARM.toString()+ "_recovery_sms_template");
                //组装参数
                AlarmExt alarmExt = new AlarmExt();
                BeanUtils.copyProperties(alarm, alarmExt);
                alarmExt.setAlarm_Result(result);
                alarmExt.setAlarm_Email_Title("短信审核预警");
                alarmExt.setAlarm_Content(alarmTemplate==null?"有待审短信需要审核":alarmTemplate);
                alarmExt.setRecovery_Content(recoveryTemplate==null?"待审短信处理完毕":recoveryTemplate);
                alarmExt.setRecovery_Email_Title("短信审核完毕通知");
                //预警记录
                AlarmLog alarmLog = assemblyAlarmLog(alarmExt);
                alarmLog.setProbe_Value(inputList.size()+"");//探测值
                String des = alarmDescription("短信审核预警",alarmLog
                        ,inputList.size()+"",null);
                alarmLog.setDescription(des);//描述
                //执行
                executeAlarm(alarmExt,alarmLog);
            }catch (Exception e){
                SuperLogger.error(e.getMessage(),e);
            }
        }, 10L, delay, TimeUnit.MILLISECONDS);
    }
}
