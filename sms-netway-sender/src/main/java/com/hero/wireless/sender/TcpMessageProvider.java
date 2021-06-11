package com.hero.wireless.sender;

import com.drondea.sms.channel.ChannelSession;
import com.drondea.sms.common.util.CommonUtil;
import com.drondea.sms.message.ILongSMSMessage;
import com.drondea.sms.message.IMessage;
import com.drondea.sms.message.MessageProvider;
import com.drondea.wireless.util.SuperLogger;
import com.hero.wireless.web.entity.business.Channel;
import com.hero.wireless.web.entity.send.Submit;
import com.hero.wireless.web.util.QueueUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @version V3.0.0
 * @description: 提供发送包的接口
 * @author: 刘彦宁
 * @date: 2020年11月18日12:18
 **/
public class TcpMessageProvider implements MessageProvider {

    private Channel channel;

    public TcpMessageProvider(Channel channel) {
        this.channel = channel;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    @Override
    public List<IMessage> getTcpMessages(ChannelSession channelSession) {
        return pullMessageFromTopic(channelSession, channel.getNo());
    }

    @Override
    public void responseMessageMatchFailed(String requestKey, IMessage response) {
        //未匹配成功submit处理

    }

    /**
     * 返回是否存在消息,此处是立即返回，回调是异步执行
     *
     * @param channelNo           通道编号
     * @return
     */
    public List<IMessage> pullMessageFromTopic(ChannelSession channelSession, String channelNo) {

        List<IMessage> requestMsg = new ArrayList<>();
        //根据优先级获取
        Submit submit = QueueUtil.getSubmit(channelNo);
        if (submit == null) {
            return requestMsg;
        }

        //2021.01.28 通道挂起状态，直接返回失败，消费掉mq
        if(submit != null && SenderUtil.isChannelHold(channel)){
            SenderUtil.saveHoldMessage(submit, channel);
            return requestMsg;
        }

        //根据submit对象获取要提交的短信
        IMessage submitRequest = SenderUtil.getTcpSendMsg(channel, submit, channelSession);
        if (submitRequest == null) {
            return requestMsg;
        }

        try {
            List<IMessage> longMsgToEntity = CommonUtil.getLongMsgSlices((ILongSMSMessage) submitRequest,
                    channelSession.getConfiguration(), channelSession.getSequenceNumber());
            requestMsg.addAll(longMsgToEntity);
        } catch (Exception e) {
            SuperLogger.error("pullMessageFromTopic" , e);
        }
        return requestMsg;
    }

}
