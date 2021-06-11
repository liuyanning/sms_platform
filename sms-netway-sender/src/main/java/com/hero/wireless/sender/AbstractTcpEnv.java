package com.hero.wireless.sender;

import com.drondea.sms.conf.ClientSocketConfig;
import com.drondea.sms.session.AbstractClientSessionManager;
import com.drondea.sms.session.sgip.SgipClientSessionManager;
import com.drondea.sms.type.SignatureDirection;
import com.drondea.wireless.util.SuperLogger;
import com.hero.wireless.enums.ChannelStatus;
import com.hero.wireless.enums.SignaturePosition;
import com.hero.wireless.enums.SignatureType;
import com.hero.wireless.netway.config.NetwayEnv;
import com.hero.wireless.web.entity.business.Channel;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * @version V3.0.0
 * @description: tcp协议的客户端创建
 * @author: 刘彦宁
 * @date: 2021年02月18日16:48
 **/
abstract class AbstractTcpEnv {

    /**
     * 创建ClientSocketConfig配置对象
     * @param channel
     * @return
     */
    protected abstract ClientSocketConfig createClientSocketConfig(Channel channel);

    /**
     * 创建ClientSessionManager对象
     * @param channel
     * @param socketConfig
     * @return
     */
    protected abstract AbstractClientSessionManager createSessionManager(Channel channel, ClientSocketConfig socketConfig);

    /**
     * tcp的共有参数
     * @param socketConfig
     * @param channel
     */
    private void setClientSocketConfig(ClientSocketConfig socketConfig, Channel channel) {
        short channelSize = ObjectUtils.defaultIfNull(channel.getMax_Concurrent_Total(), 1).shortValue();
        socketConfig.setChannelSize(channelSize);
        socketConfig.setCountersEnabled(true);
        socketConfig.setWindowMonitorInterval(10 * 1000);
        //设置响应超时时间
        socketConfig.setRequestExpiryTimeout(30 * 1000);
        //发送限速
        Integer submitSpeed = ObjectUtils.defaultIfNull(channel.getSubmit_Speed(), 200);
        socketConfig.setQpsLimit(submitSpeed);

        //签名方向
        if (StringUtils.defaultIfEmpty(channel.getSignature_Direction_Code(), SignatureType.CUSTOMIZE.toString())
                .equalsIgnoreCase(SignatureType.CHANNEL_FIXED.toString())) {
            socketConfig.setSignatureDirection(SignatureDirection.CHANNEL_FIXED);
        } else {
            socketConfig.setSignatureDirection(SignatureDirection.CUSTOM);
        }

        //签名位置
        if (StringUtils.defaultIfEmpty(channel.getSignature_Position(), SignaturePosition.PREFIX.toString())
                .equalsIgnoreCase(SignaturePosition.PREFIX.toString())) {
            socketConfig.setSignaturePosition(com.drondea.sms.type.SignaturePosition.PREFIX);
        } else {
            socketConfig.setSignaturePosition(com.drondea.sms.type.SignaturePosition.SUFFIX);
        }
    }

    public AbstractClientSessionManager createClient(Channel channel, NetwayEnv netwayEnv) {

        ClientSocketConfig socketConfig = createClientSocketConfig(channel);

        //设置公共参数
        setClientSocketConfig(socketConfig, channel);

        AbstractClientSessionManager sessionManager = createSessionManager(channel, socketConfig);

        //消息拉取的对象（新）
        sessionManager.setMessageProvider(new TcpMessageProvider(channel));

        //sgip不需要创建连接，由业务控制有消息的时候创建
        if (sessionManager instanceof SgipClientSessionManager) {
            return sessionManager;
        }

        if (channel.getStatus_Code().equalsIgnoreCase("Start")
                || channel.getStatus_Code().equalsIgnoreCase(ChannelStatus.HOLD.toString())) {
            //创建链接
            sessionManager.doOpen();
            SuperLogger.debug("=======启动"+ channel.getProtocol_Type_Code() + "通道=====" + channel.getName() + "成功=======");

            //启动定时任务
            sessionManager.doCheckSessions();
        }

        return sessionManager;
    }
}
