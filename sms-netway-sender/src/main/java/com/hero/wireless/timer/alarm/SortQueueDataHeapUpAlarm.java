package com.hero.wireless.timer.alarm;

import com.drondea.wireless.util.SuperLogger;
import com.hero.wireless.enums.AlarmType;
import com.hero.wireless.web.config.DatabaseCache;
import com.hero.wireless.web.entity.business.Alarm;
import com.hero.wireless.web.entity.business.AlarmLog;
import com.hero.wireless.web.entity.business.ext.AlarmExt;
import com.hero.wireless.web.service.base.QueueDetail;
import com.hero.wireless.web.service.base.QueueParamEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Title: SortQueueDataHeapUpAlarm
 * Description:队列数据堆积预警
 *
 * @author yjb
 * @date 2020-08-05
 */
@Component
public class SortQueueDataHeapUpAlarm extends AlarmBase implements IAlarm {

    @Override
    @PostConstruct
    public boolean probe() {
        List<Alarm> list = getAlarmListByType(AlarmType.SORT_QUEUE_DATA_HEAP_UP_ALARM.toString());
        list.forEach(item -> {
            alarmCheck(item);
        });
        return false;
    }

    public void alarmCheck(Alarm item) {
        long delay = item.getProbe_Time() * 60 * 1000;
        SCHEDULED_EXECUTOR_SERVICE.scheduleWithFixedDelay(() -> {
            Alarm alarm = getStartAlarmById(item.getId());
            if (alarm == null) {
                return;
            }
            QueueParamEntity entity = new QueueParamEntity();
            entity.setPlatformTag("sorter");
//            List<QueueDetail> resultData = sendManage.queryQueueMessageByTopic(entity);
            List<QueueDetail> resultData = new ArrayList<>();
            try {
                Long threshold = Long.parseLong(alarm.getThreshold_Value());
                if (resultData.size() == 0) {
                    return;
                }
                Long remainTotal = resultData.get(0).getQueueDataDetail().getRemainTotal();
                Boolean result = threshold.compareTo(remainTotal) == -1;
                String alarmTemplate = DatabaseCache.getAlarmSmsTemplate(AlarmType.SORT_QUEUE_DATA_HEAP_UP_ALARM.toString() + "_sms_template");
                String recoveryTemplate = DatabaseCache.getAlarmSmsTemplate(AlarmType.SORT_QUEUE_DATA_HEAP_UP_ALARM.toString() + "_recovery_sms_template");
                if (StringUtils.isEmpty(alarmTemplate) || StringUtils.isEmpty(recoveryTemplate)) {
                    return;
                }
                AlarmExt alarmExt = new AlarmExt();
                BeanUtils.copyProperties(alarm, alarmExt);
                alarmExt.setAlarm_Result(result);
                alarmExt.setAlarm_Email_Title("分拣队列有消息堆积");
                alarmExt.setAlarm_Content(MessageFormat.format(alarmTemplate, remainTotal));
                alarmExt.setRecovery_Email_Title("分拣队列数据恢复正常");
                alarmExt.setRecovery_Content(recoveryTemplate);
                AlarmLog alarmLog = assemblyAlarmLog(alarmExt);
                alarmLog.setProbe_Value(remainTotal + "");//探测值
                String des = alarmDescription("分拣队列数据堆积预警", alarmLog, remainTotal + "", null);
                alarmLog.setDescription(des);//描述
                executeAlarm(alarmExt, alarmLog);
            } catch (Exception e) {
                SuperLogger.error(e.getMessage(), e);
            }
        }, 10L, delay, TimeUnit.MILLISECONDS);
    }
}
