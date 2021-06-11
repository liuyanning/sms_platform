package com.hero.wireless.sort;

import com.drondea.wireless.config.Constant;
import com.hero.wireless.enums.*;
import com.hero.wireless.web.config.DatabaseCache;
import com.hero.wireless.web.entity.business.Alarm;
import com.hero.wireless.web.entity.business.EnterpriseUser;
import com.hero.wireless.web.entity.business.ext.AlarmExt;
import com.hero.wireless.web.service.IBusinessManage;
import com.hero.wireless.web.util.QueueUtil;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * @version V3.0.0
 * @description: 余额预警实现
 * @author: lyh
 * @date: 2021年04月21日15:10
 **/
@Service
public class BalanceAlarmServiceImpl implements IBalanceAlarmService {

    @Autowired
    protected IBusinessManage businessManage;

    @Override
    public void enterpriseUserOrAgentBalanceAlarm() {
        //用户余额预警
        List<Alarm> list = getAlarmListByType(AlarmType.ACCOUNT_BALANCE_ALARM.toString());
        list.forEach(alarm -> {
            if (alarm == null) return;
            EnterpriseUser enterpriseUser = DatabaseCache.getEnterpriseUserByNoAndUserName(null, alarm.getBind_Value());
            if(ObjectUtils.isEmpty(enterpriseUser) || !enterpriseUser.getStatus().equals(Constant.ENTERPRISE_INFO_STATUS_NORMAL)) return;
            BigDecimal availableAmount = enterpriseUser.getAvailable_Amount();
            notifyBalanceAlarmMQ(alarm, availableAmount);
        });
    }
    //获取指定类型预警任务列表
    private List<Alarm> getAlarmListByType(String type){
        AlarmExt alarmExt = new AlarmExt();
        alarmExt.setType_Code(type);
        alarmExt.setStatus(AlarmStatus.START.toString());
        return businessManage.queryAlarmList(alarmExt);
    }
    private void notifyBalanceAlarmMQ(Alarm alarm,BigDecimal availableAmount){
        BigDecimal threshold = new BigDecimal(alarm.getThreshold_Value());
        Boolean result = availableAmount.compareTo(threshold) == -1;
        alarm.setRemark(availableAmount.toString());
        if(result){
            alarm.setDescription(ProbeResult.ABNORMAL.toString());
            QueueUtil.notifyAlarm(alarm, Priority.MIDDLE_LEVEL.value());
        }else{
            alarm.setDescription(ProbeResult.NORMAL.toString());
            QueueUtil.notifyAlarm(alarm, Priority.MIDDLE_LEVEL.value());
        }
    }
}
