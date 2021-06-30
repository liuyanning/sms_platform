package com.hero.wireless.sender;

import com.drondea.sms.channel.ChannelSession;
import com.drondea.sms.conf.ClientSocketConfig;
import com.drondea.sms.message.IMessage;
import com.drondea.sms.message.MessageProvider;
import com.drondea.sms.session.AbstractClientSessionManager;
import com.hero.wireless.enums.ChannelStatus;
import com.hero.wireless.enums.ProtocolType;
import com.hero.wireless.sms.sender.service.AbstractTcpSenderService;
import com.hero.wireless.web.entity.business.Channel;
import com.hero.wireless.web.entity.send.Submit;
import com.hero.wireless.web.util.ApplicationContextUtil;
import com.hero.wireless.web.util.ChannelUtil;
import com.hero.wireless.web.util.SMSUtil;
import org.apache.commons.lang3.ObjectUtils;

import java.util.Date;
import java.util.Map;

/**
 * @version V3.0.0
 * @description: 挂起数据处理
 * @author: 刘彦宁
 * @date: 2021年02月19日10:08
 **/
public class SenderUtil {

    /**
     * 将submit转换成要提交的tcp协议对象
     * @param channel
     * @param submit
     * @param channelSession
     * @return
     */
    public static IMessage getTcpSendMsg(Channel channel, Submit submit, ChannelSession channelSession) {
        if (!channelSession.getChannel().isActive()) {
            return null;
        }
        // 设置发送器Ip
        submit.setSender_Local_IP(channel.getSender_Local_IP());

        AbstractTcpSenderService service = (AbstractTcpSenderService) ApplicationContextUtil
                .getBean(ProtocolType.getSenderServiceClasze(channel.getProtocol_Type_Code()));
        return service.getSubmitMessage(channel, submit, channelSession);
    }

    /**
     * 通道是否是挂起状态
     * @param channel
     * @return
     */
    public static boolean isChannelHold(Channel channel) {
        return channel.getStatus_Code().equalsIgnoreCase(ChannelStatus.HOLD.toString());
    }

    /**
     * 重新调整连接的数量、速度和窗口大小
     * @param sessionManager
     * @param channel
     */
    public static void resetChannelParam(AbstractClientSessionManager sessionManager, Channel channel) {

        resetProviderChannel(sessionManager, channel);

        ClientSocketConfig socketConfig = (ClientSocketConfig) sessionManager.getSocketConfig();
        //http的是null
        if (socketConfig == null) {
            return;
        }
        short channelSize = ObjectUtils.defaultIfNull(channel.getMax_Concurrent_Total(), 1).shortValue();
        //调整连接数
        if (socketConfig.getChannelSize() != channelSize) {
            socketConfig.setChannelSize(channelSize);
        }

        //发送限速
        Integer submitSpeed = ObjectUtils.defaultIfNull(channel.getSubmit_Speed(), 200);
        if (socketConfig.getQpsLimit() != submitSpeed) {
            sessionManager.resetSpeed(submitSpeed);
        }

        //调整窗口
        Map<String, ChannelUtil.OtherParameter> parameterMap = ChannelUtil.getParameter(channel);
        int windowSize = ChannelUtil.getParameterIntValue(parameterMap, "window_size", 32);
        if (socketConfig.getWindowSize() != windowSize) {
            sessionManager.resetWindowSize(windowSize);
        }
    }

    /**
     * 调整messageProvider的channel属性
     * @param sessionManager
     * @param channel
     */
    private static void resetProviderChannel(AbstractClientSessionManager sessionManager, Channel channel) {
        MessageProvider messageProvider = sessionManager.getMessageProvider();
        //状态改变
        if (!(messageProvider instanceof TcpMessageProvider)) {
            return;
        }
        Channel channelOld = ((TcpMessageProvider) messageProvider).getChannel();
        if (channelOld.getStatus_Code().equals(channel.getStatus_Code())) {
            return;
        }
        //更改通道状态
        channelOld.setStatus_Code(channel.getStatus_Code());
    }

    public static void saveHoldMessage(Submit submit, Channel channel) {
        //2021.01.28 通道挂起状态，直接返回失败，消费掉mq
        submit.setChannel_No(channel.getNo());
        // 提交时间
        submit.setSubmit_Date(submit.getSubmit_Date() == null ? new Date() : submit.getSubmit_Date());
        // 响应时间
        submit.setSubmit_Response_Date(new Date());
        submit.setSubmit_Description("取消");
        SMSUtil.saveFailedReports(submit,true);
    }
}
