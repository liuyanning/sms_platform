package com.hero.wireless.netway.service;

import com.drondea.sms.type.IValidator;
import com.drondea.sms.type.UserChannelConfig;
import com.drondea.wireless.util.SuperLogger;
import com.hero.wireless.enums.ProtocolType;
import com.hero.wireless.netway.config.InitSystemEnv;
import com.hero.wireless.web.config.DatabaseCache;
import com.hero.wireless.web.entity.business.EnterpriseUser;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * @version V3.0.0
 * @description: 网关抽象父类
 * @author: 刘彦宁
 * @date: 2021年02月20日10:20
 **/
public class TcpValidator implements IValidator {

    private ProtocolType protocolType;
    public TcpValidator(ProtocolType protocolType) {
        this.protocolType = protocolType;
    }

    /**
     * 启动回执和Mo的拉取程序
     * @param user
     */
    public void startMsgPuller(EnterpriseUser user) {
        int userId = user.getId();
        SuperLogger.debug("创建puller：" + userId);

        InitSystemEnv.addReportPuller(userId, protocolType);
    }

    @Override
    public UserChannelConfig getUserChannelConfig(String userName) {
        EnterpriseUser userInfo = DatabaseCache.getUserByCachedTcpUserName(userName);
        if (userInfo == null) {
            return null;
        }
        UserChannelConfig userChannelConfig = new UserChannelConfig();
        userChannelConfig.setUserName(userName);
        userChannelConfig.setPassword(userInfo.getTcp_Password());
        userChannelConfig.setQpsLimit(ObjectUtils.defaultIfNull(userInfo.getTcp_Submit_Speed(), 200));
        int userId = userInfo.getId();
        String userIdStr = String.valueOf(userId);
        userChannelConfig.setId(userIdStr);
        userChannelConfig.setValidIp(userInfo.getApi_Ip());
        userChannelConfig.setChannelLimit(ObjectUtils.defaultIfNull(userInfo.getTcp_Max_Channel(), 3));

        if (!ProtocolType.SGIP.equals(protocolType)) {
            Map<String, String> userProperties = DatabaseCache.getCachedUserProperties(userIdStr);
            //获取windowsize,设置推送MO和Report的窗口大小
            userChannelConfig.setWindowSize(Integer.parseInt(StringUtils.defaultIfEmpty(userProperties.get("Window_Size"),"32")));
            userChannelConfig.setWindowMonitorInterval(10 * 1000);
            userChannelConfig.setRequestExpiryTimeout(30 * 1000);
        }

        InitSystemEnv.addUserInfo(userId, userInfo);
        InitSystemEnv.addEnterprise(userId, DatabaseCache.getEnterpriseByNo(userInfo.getEnterprise_No()));

        if (ProtocolType.SGIP.equals(protocolType)) {
            startMsgPuller(DatabaseCache.getSgipUserExtInfo(userInfo));
        } else {
            startMsgPuller(userInfo);
        }
        return userChannelConfig;
    }

}
