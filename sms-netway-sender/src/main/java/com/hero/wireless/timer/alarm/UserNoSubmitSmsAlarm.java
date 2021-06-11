package com.hero.wireless.timer.alarm;

import com.drondea.wireless.util.DateTime;
import com.drondea.wireless.util.SuperLogger;
import com.hero.wireless.enums.AlarmType;
import com.hero.wireless.web.config.DatabaseCache;
import com.hero.wireless.web.entity.base.Pagination;
import com.hero.wireless.web.entity.business.Alarm;
import com.hero.wireless.web.entity.business.AlarmLog;
import com.hero.wireless.web.entity.business.EnterpriseUser;
import com.hero.wireless.web.entity.business.ext.AlarmExt;
import com.hero.wireless.web.entity.business.ext.EnterpriseUserExt;
import com.hero.wireless.web.entity.send.Submit;
import com.hero.wireless.web.entity.send.SubmitExample;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.text.MessageFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 客户无提交短信警报
 *
 * @author lyh
 * @version V1.0
 * @date 2020年10月30日
 */
@Component
public class UserNoSubmitSmsAlarm extends AlarmBase implements IAlarm {

    @Override
    @PostConstruct
    public boolean probe() {
        List<Alarm> list = getAlarmListByType(AlarmType.USER_NO_SUBMIT_SMS_ALARM.toString());
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
                if (alarm == null || StringUtils.isEmpty(alarm.getMonitor_Time())) {
                    return;
                }
                AlarmExt alarmExt = copyPropertiesAndAssembleArgument(alarm, delay);
                String[] timeArray = alarm.getMonitor_Time().split(",");
                Date start = DateTime.getDate(DateTime.getString(new Date(), DateTime.Y_M_D_1) + " " + timeArray[0]);
                Date end = DateTime.getDate(DateTime.getString(new Date(), DateTime.Y_M_D_1) + " " + timeArray[1]);
                if (start.after(alarmExt.getMin_Submit_Date()) || end.before(alarmExt.getMax_Submit_Date())) {
                    return;
                }
                EnterpriseUserExt user = new EnterpriseUserExt();
                user.setUser_Name(alarm.getBind_Value());
                List<EnterpriseUser> userList = DatabaseCache.getEnterpriseUserList(user);
                if (userList.size() != 1) {
                    return;
                }
                SubmitExample example = new SubmitExample();
                example.createCriteria().andEnterprise_User_IdEqualTo(userList.get(0).getId())
                        .andSubmit_DateBetween(alarmExt.getMin_Submit_Date(),alarmExt.getMax_Submit_Date());
                example.setPagination(new Pagination(0, 1));
                List<Submit> list = submitDAO.selectByExample(example);
                String alarmTemplate = DatabaseCache.getAlarmSmsTemplate(AlarmType.USER_NO_SUBMIT_SMS_ALARM.toString() + "_sms_template");
                String recoveryTemplate = DatabaseCache.getAlarmSmsTemplate(AlarmType.USER_NO_SUBMIT_SMS_ALARM.toString() + "_recovery_sms_template");
                alarmExt.setAlarm_Result(list.size() == 0);
                alarmExt.setAlarm_Email_Title("客户无提交短信通知");
                alarmExt.setAlarm_Content(MessageFormat.format(alarmTemplate, alarm.getBind_Value(), alarmExt.getMin_Create_Date() + "-" + alarmExt.getMax_Create_Date()));
                alarmExt.setRecovery_Email_Title("客户恢复提交短信通知");
                alarmExt.setRecovery_Content(MessageFormat.format(recoveryTemplate, alarm.getBind_Value()));
                AlarmLog alarmLog = assemblyAlarmLog(alarmExt);
                alarmLog.setProbe_Value("0");
                String des = alarmDescription("客户无提交短信预警", alarmLog, "", ",此时间段用户未提交短信");
                alarmLog.setDescription(des);
                executeAlarm(alarmExt, alarmLog);
            } catch (Exception e) {
                SuperLogger.error(e.getMessage(), e);
            }
        }, 10L, delay, TimeUnit.MILLISECONDS);
    }
}
