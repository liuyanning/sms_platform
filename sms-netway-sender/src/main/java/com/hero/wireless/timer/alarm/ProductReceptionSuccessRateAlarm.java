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
 * 产品接收成功率预警
 * 产品接收成功率预警 = 接收成功/提交成功
 * @author gengjinbiao
 * @version V1.0
 * @date 2019年11月10日
 */
@Component
public class ProductReceptionSuccessRateAlarm extends AlarmBase implements IAlarm {

	@Override
	@PostConstruct
	public boolean probe() {
		List<Alarm> list = getAlarmListByType(AlarmType.PRODUCT_RECEPTION_SUCCESS_RATE_ALARM.toString());
		list.forEach(item -> {
			alarmCheck(item);
		});
		return false;
	}

    //任务调度
	public void alarmCheck(Alarm item){
	    long delay = item.getProbe_Time()*60*1000;
		SCHEDULED_EXECUTOR_SERVICE.scheduleWithFixedDelay(() -> {
			try{
				Alarm alarm = getStartAlarmById(item.getId());
				if (alarm == null) {
					return;
				}
				AlarmExt alarmExt = copyPropertiesAndAssembleArgument(alarm,delay);
				alarmExt.setProduct_No(alarm.getBind_Value());
				Integer submitSuccessTotal = timerExtDAO.getSubmitSuccessTotal(alarmExt);
                if (submitSuccessTotal == null || submitSuccessTotal == 0){
                    return;
                }
                Integer successTotal = timerExtDAO.getReportSuccessTotalForReception(alarmExt);
                Double rate = getRate(successTotal, submitSuccessTotal);
				Boolean result = rate < Double.valueOf(alarm.getThreshold_Value());
				String alarmTemplate = DatabaseCache.getAlarmSmsTemplate(AlarmType.PRODUCT_RECEPTION_SUCCESS_RATE_ALARM.toString()+ "_sms_template");
				String recoveryTemplate = DatabaseCache.getAlarmSmsTemplate(AlarmType.PRODUCT_RECEPTION_SUCCESS_RATE_ALARM.toString()+ "_recovery_sms_template");
				//组装参数
				String productName = DatabaseCache.getProductByNo(alarmExt.getBind_Value()).getName();
				alarmExt.setAlarm_Result(result);
				alarmExt.setAlarm_Email_Title("产品接收成功率预警");
				alarmExt.setAlarm_Content(MessageFormat.format( alarmTemplate==null?"":alarmTemplate, productName, alarm.getThreshold_Value()));
				alarmExt.setRecovery_Email_Title("产品接收成功率恢复通知");
				alarmExt.setRecovery_Content(MessageFormat.format( recoveryTemplate==null?"":recoveryTemplate, productName, alarm.getThreshold_Value()));
                //预警记录
                AlarmLog alarmLog = assemblyAlarmLog(alarmExt);
                alarmLog.setProbe_Value(String.valueOf(rate));//探测值
                StringBuffer logBuf = new StringBuffer();
                logBuf.append(",产品提交成功"+defaultString(submitSuccessTotal)+"条,");
                logBuf.append("接收成功"+defaultString(successTotal)+"条,");
                logBuf.append("接收成功率 "+(rate)+"%");
                String des = alarmDescription("产品接收成功率预警",alarmLog
                        ,rate+"%",logBuf.toString());
                alarmLog.setDescription(des);//描述
                //执行
                executeAlarm(alarmExt,alarmLog);
			}catch (Exception e){
                SuperLogger.error(e.getMessage(),e);
			}
		}, 10L, delay, TimeUnit.MILLISECONDS);
	}


}
