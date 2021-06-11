package com.hero.wireless.netway.service.impl;

import com.drondea.sms.channel.ChannelSession;
import com.drondea.sms.common.util.MsgId;
import com.drondea.sms.common.util.SystemClock;
import com.drondea.sms.message.IMessage;
import com.drondea.sms.message.cmpp.CmppDeliverRequestMessage;
import com.drondea.sms.message.cmpp.CmppDeliverResponseMessage;
import com.drondea.sms.message.cmpp.CmppReportRequestMessage;
import com.drondea.sms.session.cmpp.CmppServerSession;
import com.drondea.sms.thirdparty.SmsDcs;
import com.drondea.sms.type.UserChannelConfig;
import com.drondea.wireless.util.SuperLogger;
import com.hero.wireless.enums.NotifyStatus;
import com.hero.wireless.enums.ReportStatus;
import com.hero.wireless.netway.handler.AbstractMOResponseHandler;
import com.hero.wireless.netway.handler.AbstractReportResponseHandler;
import com.hero.wireless.web.config.DatabaseCache;
import com.hero.wireless.web.entity.business.EnterpriseUser;
import com.hero.wireless.web.entity.send.Inbox;
import com.hero.wireless.web.entity.send.Report;
import com.hero.wireless.web.entity.send.ReportNotify;
import com.hero.wireless.web.util.SMSUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.stereotype.Component;

@Component
public class CmppAPIServiceImpl extends AbstractTcpService {

    @Override
    public String genLocalCacheKey(String bachNum, String userId) {
        return "cmpp2_" + bachNum + "_" + userId;
    }

    @Override
    public IMessage getReportMessage(ChannelSession channelSession, Report entity) {

        // 写入状态
        CmppDeliverRequestMessage request = new CmppDeliverRequestMessage();
        request.setMsgId(new MsgId());
        request.getHeader().setSequenceId(channelSession.getSequenceNumber().next());
        String phoneNo = entity.getPhone_No().replaceFirst("\\+", "");
        //过滤前的手机号
        phoneNo = DatabaseCache.getUserCountryCodePhoneNo(entity.getEnterprise_User_Id(), phoneNo);
        // 源终端MSISDN号码（状态报告时填为CMPP_SUBMIT消息的目的终端号码）
        request.setSrcTerminalId(phoneNo);
        CmppReportRequestMessage reportRequestMessage = new CmppReportRequestMessage();
        reportRequestMessage.setDestterminalId(phoneNo);
        String msgId = entity.getEnterprise_Msg_Id();
        if(StringUtils.isBlank(msgId)){
            SuperLogger.warn(entity);
            return null;
        }
        reportRequestMessage.setMsgId(new MsgId(msgId));
        String submitTime = DateFormatUtils.format(entity.getSubmit_Date(), "yyMMddHHmm");
        String t = DateFormatUtils.format(SystemClock.now(), "yyMMddHHmm");
        reportRequestMessage.setSubmitTime(submitTime);
        reportRequestMessage.setDoneTime(t);
        reportRequestMessage.setStat(StringUtils.defaultIfEmpty(entity.getNative_Status(), entity.getStatus_Code()));
        if (entity.getStatus_Code().equalsIgnoreCase(ReportStatus.SUCCESS.toString())) {
            reportRequestMessage.setStat("DELIVRD");
        }
        reportRequestMessage.setSmscSequence(0);// 取自SMSC发送状态报告的消息体中的消息标识。???
        request.setReportRequestMessage(reportRequestMessage);
        request.setRegisteredDelivery((short) 1);

        UserChannelConfig channelConfig = ((CmppServerSession) channelSession).getUserChannelConfig();
        request.setDestId(
                channelConfig.getUserName() + StringUtils.defaultString(entity.getSub_Code(), ""));

        ReportNotify reportNotify = SMSUtil.buildReportNotify(entity);
        request.setMessageResponseHandler(new AbstractReportResponseHandler(reportNotify, this) {
            @Override
            public void setResponseInfo(ReportNotify reportNotify, IMessage msgRequest, IMessage msgResponse) {
                CmppDeliverResponseMessage responseMessage = (CmppDeliverResponseMessage) msgResponse;
                reportNotify.setNotify_Response_Status(String.valueOf(responseMessage.getResult()));
            }
        });
        return request;
    }

    @Override
    public IMessage getMOMessage(ChannelSession channelSession, Inbox inbox) {
        // 写入上行
        CmppDeliverRequestMessage request = new CmppDeliverRequestMessage();
        request.setMsgId(new MsgId());
        request.getHeader().setSequenceId(channelSession.getSequenceNumber().next());
        if (StringUtils.isNotBlank(inbox.getCharset())) {
            request.setMsgContent(inbox.getContent(), new SmsDcs(SMSUtil.getGeneralDataCodingDcs(inbox.getCharset())));
        } else {
            request.setMsgContent(inbox.getContent());
        }

        String phoneNo = inbox.getPhone_No().replaceFirst("\\+", "");
        //过滤前的手机号
        phoneNo = DatabaseCache.getUserCountryCodePhoneNo(inbox.getEnterprise_User_Id(),phoneNo);
        request.setSrcTerminalId(phoneNo);

        UserChannelConfig channelConfig = ((CmppServerSession) channelSession).getUserChannelConfig();
        EnterpriseUser enterpriseUser = DatabaseCache.getEnterpriseUserCachedById(Integer.parseInt(channelConfig.getId()));
        if (enterpriseUser == null) {
            inbox.setNotify_Status_Code(NotifyStatus.FAILD.toString());
            return null;
        }
        request.setDestId(getDestId(channelConfig.getUserName(), enterpriseUser.getMo_Sp_Type_Code(), inbox));

        ReportNotify reportNotify =  SMSUtil.buildReportNotify(inbox);
        request.setMessageResponseHandler(new AbstractMOResponseHandler(reportNotify, this) {
            @Override
            public void setResponseInfo(ReportNotify reportNotify, IMessage msgRequest, IMessage msgResponse) {
                CmppDeliverResponseMessage responseMsg = (CmppDeliverResponseMessage) msgResponse;
                reportNotify.setNotify_Response_Status("MO:" + responseMsg.getResult());
            }

            @Override
            public void setRequestInfo(ReportNotify reportNotify, IMessage msgRequest) {
                CmppDeliverRequestMessage requestMsg = (CmppDeliverRequestMessage) msgRequest;
                String content = requestMsg.getMsgContent();
                reportNotify.setContent(content);
                reportNotify.setContent_Length(content.length());
            }
        });

        return request;
    }
}
