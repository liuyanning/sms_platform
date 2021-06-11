package com.hero.wireless.timer.alarm;

import com.drondea.wireless.config.Constant;
import com.drondea.wireless.util.SuperLogger;
import com.hero.wireless.enums.AlarmType;
import com.hero.wireless.enums.ProbeResult;
import com.hero.wireless.okhttp.CharsetResponseBody;
import com.hero.wireless.okhttp.HttpClient;
import com.hero.wireless.web.config.DatabaseCache;
import com.hero.wireless.web.entity.business.Alarm;
import com.hero.wireless.web.entity.business.AlarmLog;
import com.hero.wireless.web.entity.business.ext.AlarmExt;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 服务器状态报警
 * 
 * @author gengjinbiao
 * @version V1.0
 * @date 2019年11月10日
 */
@Component
public class ServerStatusAlarm extends AlarmBase implements IAlarm {

	@Override
	@PostConstruct
	public boolean probe() {
		List<Alarm> list = getAlarmListByType(AlarmType.SERVER_STATUS_ALARM.toString());
		list.forEach(item -> {
			alarmCheck(item);
		});
		return false;
	}

	// 任务调度
	public void alarmCheck(Alarm item) {
		long delay = item.getProbe_Time() * 60 * 1000;
		SCHEDULED_EXECUTOR_SERVICE.scheduleWithFixedDelay(() -> {
			Alarm alarm = getStartAlarmById(item.getId());
			if (alarm == null) {
				return;
			}
			try {
				execute(alarm);
			} catch (Exception e) {
				e.printStackTrace();
				SuperLogger.error(e);
			}
		}, 10000L, delay, TimeUnit.MILLISECONDS);
	}

	// 执行任务
	private void execute(Alarm alarm) {
		String addrs = alarm.getBind_Value();
		if (addrs == null) {
			return;
		}
		Alarm updateBean = new Alarm();
		updateBean.setId(alarm.getId());
		updateBean.setProbe_Result(ProbeResult.NORMAL.toString());
		Arrays.asList(addrs.split(Constant.MUTL_MOBILE_SPLIT)).forEach(item -> {
			HttpClient client = new HttpClient();
			if (StringUtils.isEmpty(item)) {
				return;
			}
			String testUrl = "http://" + item.trim() + "/test.jsp";
			CharsetResponseBody responseBody = client.get(testUrl, new HashMap<String, String>());
			String alarmTemplate = DatabaseCache
					.getAlarmSmsTemplate(AlarmType.SERVER_STATUS_ALARM.toString() + "_sms_template");
			String recoveryTemplate = DatabaseCache
					.getAlarmSmsTemplate(AlarmType.SERVER_STATUS_ALARM.toString() + "_recovery_sms_template");
			Boolean result = responseBody == null?true:false;
			//组装参数
			AlarmExt alarmExt = new AlarmExt();
			BeanUtils.copyProperties(alarm,alarmExt);
			alarmExt.setAlarm_Result(result);
			alarmExt.setAlarm_Email_Title("服务器状态异常通知");
			alarmExt.setAlarm_Content(MessageFormat.format(alarmTemplate , alarm.getBind_Value()));
			alarmExt.setRecovery_Email_Title("服务器状态恢复通知");
			alarmExt.setRecovery_Content(MessageFormat.format(recoveryTemplate , alarm.getBind_Value()));
            //预警记录
            AlarmLog alarmLog = assemblyAlarmLog(alarmExt);
            alarmLog.setProbe_Value("false");//探测值
            StringBuffer logBuf = new StringBuffer();
            logBuf.append("预警描述："+alarmExt.getDescription());
            logBuf.append("探测结果：访问异常");
            String des = alarmDescription("服务器探测预警",alarmLog
                    ,"",logBuf.toString());
            alarmLog.setDescription(des);//描述
            //执行
            executeAlarm(alarmExt,alarmLog);
		});
	}
}
