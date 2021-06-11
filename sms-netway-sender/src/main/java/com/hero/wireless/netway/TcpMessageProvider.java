package com.hero.wireless.netway;

import com.drondea.sms.channel.ChannelSession;
import com.drondea.sms.common.util.CommonUtil;
import com.drondea.sms.message.ILongSMSMessage;
import com.drondea.sms.message.IMessage;
import com.drondea.sms.message.MessageProvider;
import com.drondea.sms.session.AbstractServerSession;
import com.drondea.sms.type.UserChannelConfig;
import com.drondea.wireless.util.SuperLogger;
import com.hero.wireless.enums.ProtocolType;
import com.hero.wireless.netway.service.impl.*;
import com.hero.wireless.web.entity.send.Inbox;
import com.hero.wireless.web.entity.send.Report;
import com.hero.wireless.web.util.ApplicationContextUtil;
import com.hero.wireless.web.util.QueueUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @version V3.0.0
 * @description: 拉取消息的接口实现
 * @author: 刘彦宁
 * @date: 2020年12月17日10:21
 **/
public class TcpMessageProvider implements MessageProvider {

    private final ProtocolType protocolType;
    private AbstractTcpService tcpService;

    public TcpMessageProvider(ProtocolType protocolType) {
        this.protocolType = protocolType;

        switch (protocolType) {
            case CMPP:
                this.tcpService = ApplicationContextUtil.getBean(CmppAPIServiceImpl.class);
                break;
            case SMGP:
                this.tcpService = ApplicationContextUtil.getBean(SmgpAPIServiceImpl.class);
                break;
            case SGIP:
                this.tcpService = ApplicationContextUtil.getBean(SgipAPIServiceImpl.class);
                break;
            case SMPP:
                this.tcpService = ApplicationContextUtil.getBean(SmppAPIServiceImpl.class);
                break;
            default:
                break;
        }
    }

    public Integer getUserId(ChannelSession channelSession) {
        AbstractServerSession serverSession = (AbstractServerSession) channelSession;
        UserChannelConfig userChannelConfig = serverSession.getUserChannelConfig();
        return Integer.parseInt(userChannelConfig.getId());
    }

    @Override
    public List<IMessage> getTcpMessages(ChannelSession channelSession) {

        Integer userId = getUserId(channelSession);

        List<IMessage> requestMsg = new ArrayList<>();

        //获取回执消息
        pullReportMessageFromMQ(userId, channelSession, requestMsg);

        pullMOMessageFromMQ(userId, channelSession, requestMsg);

        return requestMsg;
    }

    @Override
    public void responseMessageMatchFailed(String key, IMessage response) {
        SuperLogger.error("responseMessageMatchFailed :" +  response);
    }

    public void pullReportMessageFromMQ(Integer userId, ChannelSession channelSession, List<IMessage> requestMsg) {

        Report report = QueueUtil.getUserReport(protocolType, userId);
        if (report == null) {
            return;
        }

        //回执不需要处理长短信
        IMessage reportMessage = tcpService.getReportMessage(channelSession, report);
        if (reportMessage == null) {
            return;
        }
        requestMsg.add(reportMessage);

    }

    public void pullMOMessageFromMQ(Integer userId, ChannelSession channelSession, List<IMessage> requestMsg) {

        Inbox inbox = QueueUtil.getUserMO(protocolType, userId);
        if (inbox == null) {
            return;
        }

        IMessage moMessage = tcpService.getMOMessage(channelSession, inbox);
        try {
            List<IMessage> longMsgToEntity = CommonUtil.getLongMsgSlices((ILongSMSMessage) moMessage,
                    channelSession.getConfiguration(), channelSession.getSequenceNumber());
            requestMsg.addAll(longMsgToEntity);
        } catch (Exception e) {
            SuperLogger.error("pullMOMessageFromMQ" , e);
        }
    }
}
