package com.hero.wireless.timer.alarm;

import com.drondea.wireless.util.DateTime;
import com.drondea.wireless.util.SecretUtil;
import com.hero.wireless.enums.AlarmStatus;
import com.hero.wireless.enums.ProbeResult;
import com.hero.wireless.web.config.DatabaseCache;
import com.hero.wireless.web.dao.business.IAlarmLogDAO;
import com.hero.wireless.web.dao.business.IEnterpriseUserDAO;
import com.hero.wireless.web.dao.business.ISmsTemplateDAO;
import com.hero.wireless.web.dao.business.ext.ITimerExtDAO;
import com.hero.wireless.web.dao.send.IInputDAO;
import com.hero.wireless.web.dao.send.ISubmitDAO;
import com.hero.wireless.web.entity.business.Alarm;
import com.hero.wireless.web.entity.business.AlarmLog;
import com.hero.wireless.web.entity.business.EnterpriseUser;
import com.hero.wireless.web.entity.business.SmsTemplate;
import com.hero.wireless.web.entity.business.ext.AlarmExt;
import com.hero.wireless.web.entity.send.Input;
import com.hero.wireless.web.entity.send.Submit;
import com.hero.wireless.web.service.IBusinessManage;
import com.hero.wireless.web.service.INetwayManage;
import com.hero.wireless.web.service.INoticeManage;
import com.hero.wireless.web.service.ISendManage;
import com.hero.wireless.web.util.DingTalkUtil;
import com.hero.wireless.web.util.SMSUtil;
import com.hero.wireless.web.util.WeChatUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * @author gengjinbiao
 * @version V1.0
 * @date 2019年11月10日
 */
@Component
public class AlarmBase {
    //项目启动执行预警先初始化缓存,勿删！！！
    @Resource(name = "databaseCache")
    private DatabaseCache databaseCache;
    @Autowired
    protected INoticeManage noticeManage;
	@Autowired
    protected IBusinessManage businessManage;
    @Autowired
    protected INetwayManage netwayManage;
    @Resource(name = "sendManage")
    protected ISendManage sendManage;
    @Resource
    protected IAlarmLogDAO<AlarmLog> alarmLogDAO;
    @Resource(name = "timerExtDAO")
    protected ITimerExtDAO timerExtDAO;
    @Autowired
    protected IInputDAO<Input> inputDAO;
    @Autowired
    protected ISubmitDAO<Submit> submitDAO;
    @Autowired
    protected IEnterpriseUserDAO<EnterpriseUser> enterpriseUserDAO;
    @Resource(name = "ISmsTemplateDAO")
    protected ISmsTemplateDAO<SmsTemplate> smsTemplateDAO;

    protected final static ScheduledExecutorService SCHEDULED_EXECUTOR_SERVICE = Executors.newScheduledThreadPool(50);

    //获取指定类型预警任务列表
    protected List<Alarm> getAlarmListByType(String type){
        AlarmExt alarmExt = new AlarmExt();
        alarmExt.setType_Code(type);
        alarmExt.setStatus(AlarmStatus.START.toString());
        return businessManage.queryAlarmList(alarmExt);
    }

    //获取指定预警信息
	protected Alarm getStartAlarmById(Integer id){
        AlarmExt alarmExt = new AlarmExt();
        alarmExt.setId(id);
        alarmExt.setStatus(AlarmStatus.START.toString());
        List<Alarm> list = businessManage.queryAlarmList(alarmExt);
        if (list.size() < 1){
            return null;
        }
        return list.get(0);
    }

	protected AlarmExt copyPropertiesAndAssembleArgument(Alarm alarm, Long delay){
        AlarmExt alarmExt = new AlarmExt();
        BeanUtils.copyProperties(alarm,alarmExt);
        Date now = new Date();
        Date beforeDate = new Date(now.getTime() - delay);
        alarmExt.setMin_Submit_Date(beforeDate);
        alarmExt.setMax_Submit_Date(now);
        alarmExt.setMax_Create_Date(DateTime.getString(now));
        alarmExt.setMin_Create_Date(DateTime.getString(beforeDate));
        return alarmExt;
    }

    //执行通知任务，更新预警表信息
    protected void executeAlarm(AlarmExt alarmExt, AlarmLog alarmLog){
        Alarm updateBean = new Alarm();
        updateBean.setId(alarmExt.getId());
        if (alarmExt.isAlarm_Result()) {//异常
            doAlarmAbnormal(updateBean,alarmExt,alarmLog);
        }else {//正常
            doAlarmNormal(updateBean,alarmExt);
        }
        updateBean.setLast_Probe_Date(new Date());
        businessManage.updateByPrimaryKeySelective(updateBean);
    }

    //正常
    private void doAlarmNormal(Alarm update,AlarmExt alarmExt) {
        update.setProbe_Result(ProbeResult.NORMAL.toString());
        update.setAlarm_Total(0);
        //状态恢复通知，如果是开启的就通知一次
        if (ProbeResult.ABNORMAL.equals(alarmExt.getProbe_Result())
                && (alarmExt.getRecovery_Notify()== null ? false : alarmExt.getRecovery_Notify())){
            if (StringUtils.isNotEmpty(alarmExt.getPhone_Nos())){
                sendSms(alarmExt.getPhone_Nos(), alarmExt.getRecovery_Content());
            }
            if(StringUtils.isNotEmpty(alarmExt.getEmails())){
                noticeManage.sendEmail(alarmExt.getEmails(), alarmExt.getRecovery_Email_Title(), alarmExt.getRecovery_Content());
            }
            if (StringUtils.isNotEmpty(alarmExt.getWechat())){
                WeChatUtil.sendMsg(Integer.valueOf(alarmExt.getWechat()), alarmExt.getRecovery_Content());
            }
            if(StringUtils.isNotEmpty(alarmExt.getDing_Talk())){
                DingTalkUtil.sendMsg(Integer.valueOf(alarmExt.getDing_Talk()), alarmExt.getRecovery_Content());
            }
        }
    }

    //异常
    private void doAlarmAbnormal(Alarm update,AlarmExt alarmExt, AlarmLog alarmLog) {
        update.setProbe_Result(ProbeResult.ABNORMAL.toString());
        if (alarmExt.getAlarm_Total() >= alarmExt.getMax_Alarm_Total()) {
            return;//预警次数超过最大次数
        }
        Boolean notifySms = false;
        Boolean notifyEmail = false;
        Boolean notifyWeChat = false;
        Boolean notifyDingTalk = false;
        String description = alarmLog.getDescription();
        description += ",通知方式:";
        if (StringUtils.isNotEmpty(alarmExt.getPhone_Nos())) {
            notifySms = sendSms(alarmExt.getPhone_Nos(), alarmExt.getAlarm_Content());
            description += notifySms?"【短信】":"";
        }
        if (StringUtils.isNotEmpty(alarmExt.getEmails())) {
            notifyEmail = noticeManage.sendEmail(alarmExt.getEmails(), alarmExt.getAlarm_Email_Title(), alarmExt.getAlarm_Content());
            description += notifyEmail?"【邮件】":"";
        }
        if (StringUtils.isNotEmpty(alarmExt.getWechat())){
            notifyWeChat = WeChatUtil.sendMsg(Integer.valueOf(alarmExt.getWechat()), alarmExt.getAlarm_Content());
            description += notifyWeChat?"【微信】":"";
        }
        if(StringUtils.isNotEmpty(alarmExt.getDing_Talk())){
            notifyDingTalk = DingTalkUtil.sendMsg(Integer.valueOf(alarmExt.getDing_Talk()), alarmExt.getAlarm_Content());
            description += notifyDingTalk?"【钉钉】":"";
        }
        if (notifySms || notifyEmail || notifyWeChat || notifyDingTalk) {
            update.setAlarm_Total(alarmExt.getAlarm_Total() + 1);
        }
        alarmLog.setDescription(description);
        alarmLog.setCreate_Date(new Date());
        alarmLogDAO.insert(alarmLog);
    }

    private Boolean sendSms(String phoneNos, String alarmContent) {
		 String url = DatabaseCache.getStringValueBySystemEnvAndCode("platform_alarm_submit_url",null);//http://ip/json/submit
         String enterpriseNo = DatabaseCache.getStringValueBySystemEnvAndCode("platform_alarm_enterprise_no", null);
         String account = DatabaseCache.getStringValueBySystemEnvAndCode("platform_alarm_account", null);
         String httpSignKey = DatabaseCache.getStringValueBySystemEnvAndCode("platform_alarm_http_sign_Key", null);
         String subCode = DatabaseCache.getStringValueBySystemEnvAndCode("platform_alarm_sub_code", "666");
         String timestamp = DateTime.getString(new Date(),DateTime.Y_M_D_H_M_S_S_2);
         String sign = SecretUtil.MD5(enterpriseNo+account+timestamp+httpSignKey);
         
         Map<String, String> params = new HashMap<>();
         params.put("enterprise_no", enterpriseNo);
         params.put("account", account);
         params.put("phones", phoneNos);
         params.put("content", alarmContent);
         params.put("timestamp", timestamp);//yyyyMMddHHmmssSSS
         params.put("sign", sign);//签名
         params.put("subcode",subCode);
		return SMSUtil.sendSms(url,params,"utf-8");
	}

	protected Double getRate(Integer molecule, Integer denominator){
        Double rate = new BigDecimal(molecule==null?0:molecule).divide(new BigDecimal(denominator==null?1:(denominator==0?1:denominator)), 4, BigDecimal.ROUND_HALF_UP)
                .multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        return rate;
    }

    /**
     * 组装 AlarmLog
     * @param alarmExt
     */
    protected AlarmLog assemblyAlarmLog(AlarmExt alarmExt){
        AlarmLog alarmLog= new AlarmLog();
        alarmLog.setAlarm_Id(alarmExt.getId());
        alarmLog.setType_Code(alarmExt.getType_Code());
        alarmLog.setBind_Value(alarmExt.getBind_Value());
        alarmLog.setThreshold_Value(alarmExt.getThreshold_Value());
        String minDate = alarmExt.getMin_Create_Date();
        String maxDate = alarmExt.getMax_Create_Date();
        if(StringUtils.isEmpty(minDate)){
            minDate = DateTime.getCurentTimeBeforeMinutes(alarmExt.getProbe_Time());
        }
        if(StringUtils.isEmpty(maxDate)){
            maxDate = DateTime.getString();
        }
        alarmLog.setBegin_Date(DateTime.getDate(minDate));
        alarmLog.setEnd_Date(DateTime.getDate(maxDate));
        alarmLog.setProbe_Result(alarmExt.isAlarm_Result()?
                ProbeResult.ABNORMAL.toString(): ProbeResult.NORMAL.toString());
        alarmLog.setPhone_Nos(alarmExt.getPhone_Nos());
        alarmLog.setEmails(alarmExt.getEmails());
        return alarmLog;
    }

    /**
     * 组装告警日志描述
     * @param alarmName 告警名称
     * @param log 告警日志
     * @param value 探测值
     * @param other 其他描述
     * @return
     */
    protected String alarmDescription(String alarmName, AlarmLog log, String value, String other) {
        Date beginDate = log.getBegin_Date();
        Date endDate = log.getEnd_Date();
        StringBuffer logBuf = new StringBuffer();
        logBuf.append(alarmName);
        logBuf.append("【");
        logBuf.append("开始时间："+ DateTime.getString(beginDate == null?new Date():beginDate));
        logBuf.append(",结束时间："+ DateTime.getString(endDate == null?new Date():endDate));
        logBuf.append(",预警阈值 "+ log.getThreshold_Value());
        logBuf.append(",探测值"+ value);
        if(StringUtils.isNotEmpty(other)){
            logBuf.append(other);
        }
        logBuf.append("】");
        return logBuf.toString();
    }

    protected String defaultString(Object data) {
        return data == null?"0":data.toString();
    }

}
