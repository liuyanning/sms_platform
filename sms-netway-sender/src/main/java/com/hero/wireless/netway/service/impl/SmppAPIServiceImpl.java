package com.hero.wireless.netway.service.impl;

import com.drondea.sms.channel.ChannelSession;
import com.drondea.sms.common.util.SystemClock;
import com.drondea.sms.message.IMessage;
import com.drondea.sms.message.smpp34.SmppDeliverSmRequestMessage;
import com.drondea.sms.message.smpp34.SmppDeliverSmResponseMessage;
import com.drondea.sms.message.smpp34.SmppReportRequestMessage;
import com.drondea.sms.session.smpp.SmppServerSession;
import com.drondea.sms.type.UserChannelConfig;
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

/**
 * @author volcano
 * @version V1.0
 * @date 2019年11月28日下午10:54:56
 */
@Component
public class SmppAPIServiceImpl extends AbstractTcpService {

    @Override
    public String genLocalCacheKey(String bachNum, String userId) {
        return "smpp_" + bachNum + "_" + userId;
    }

    @Override
    public IMessage getReportMessage(ChannelSession channelSession, Report entity) {
        // 写入状态
        SmppDeliverSmRequestMessage request = new SmppDeliverSmRequestMessage();
        request.getHeader().setSequenceNumber(channelSession.getSequenceNumber().next());
        String countryCode = entity.getCountry_Code();
        String phoneNo = countryCode + entity.getPhone_No();
        //过滤前的手机号
        phoneNo = DatabaseCache.getUserCountryCodePhoneNo(entity.getEnterprise_User_Id(), phoneNo);
        request.setSourceAddrTon((short) 1);
        request.setSourceAddrNpi((short) 1);
        request.setSourceAddr(phoneNo);

        String msgId = entity.getEnterprise_Msg_Id();
        if(StringUtils.isBlank(msgId)){
            return null;
        }
        SmppReportRequestMessage reportRequestMessage = new SmppReportRequestMessage();
        reportRequestMessage.setId(msgId);
        String t = DateFormatUtils.format(SystemClock.now(), "yyMMddHHmm");
        String submitTime = DateFormatUtils.format(entity.getSubmit_Date(), "yyMMddHHmm");
        reportRequestMessage.setSubmit_date(submitTime);
        reportRequestMessage.setDone_date(t);
        reportRequestMessage.setStat(StringUtils.defaultIfEmpty(entity.getNative_Status(), entity.getStatus_Code()));
        if (entity.getStatus_Code().equalsIgnoreCase(ReportStatus.SUCCESS.toString())) {
            reportRequestMessage.setStat("DELIVRD");
        }
        request.setReportRequest(reportRequestMessage);

        UserChannelConfig channelConfig = ((SmppServerSession) channelSession).getUserChannelConfig();
        request.setDestAddrTon((short) 1);
        request.setDestAddrNpi((short) 1);
        request.setDestinationAddr(channelConfig.getUserName() + StringUtils.defaultString(entity.getSub_Code(), ""));
        request.setRegisteredDelivery((short) 1);


        ReportNotify reportNotify =  SMSUtil.buildReportNotify(entity);
        request.setMessageResponseHandler(new AbstractReportResponseHandler(reportNotify, this) {
            @Override
            public void setResponseInfo(ReportNotify reportNotify, IMessage msgRequest, IMessage msgResponse) {
                SmppDeliverSmResponseMessage responseMessage = (SmppDeliverSmResponseMessage) msgResponse;
                reportNotify.setNotify_Response_Status(String.valueOf(responseMessage.getHeader().getCommandStatus()));
            }
        });
        return  request;
    }

    @Override
    public IMessage getMOMessage(ChannelSession channelSession, Inbox inbox) {

        SmppDeliverSmRequestMessage request = new SmppDeliverSmRequestMessage();
        request.getHeader().setSequenceNumber(channelSession.getSequenceNumber().next());
        if (StringUtils.isNotBlank(inbox.getCharset())) {
            byte charSet = SMSUtil.getGeneralDataCodingDcs(inbox.getCharset());
            request.setMsgContent(inbox.getContent(), charSet);
        } else {
            request.setMsgContent(inbox.getContent());
        }

        //todo inbox要不要国家码
        String phoneNo = inbox.getPhone_No().replaceFirst("\\+", "");
        //过滤前的手机号
        phoneNo = DatabaseCache.getUserCountryCodePhoneNo(inbox.getEnterprise_User_Id(),phoneNo);
        request.setSourceAddrTon((short) 1);
        request.setSourceAddrNpi((short) 1);
        request.setSourceAddr(phoneNo);
        UserChannelConfig channelConfig = ((SmppServerSession) channelSession).getUserChannelConfig();

        request.setDestAddrTon((short) 1);
        request.setDestAddrNpi((short) 1);

        EnterpriseUser enterpriseUser = DatabaseCache.getEnterpriseUserCachedById(Integer.parseInt(channelConfig.getId()));
        if (enterpriseUser == null) {
            inbox.setNotify_Status_Code(NotifyStatus.FAILD.toString());
            return null;
        }
        request.setDestinationAddr(getDestId(channelConfig.getUserName(), enterpriseUser.getMo_Sp_Type_Code(), inbox));

        ReportNotify reportNotify =  SMSUtil.buildReportNotify(inbox);
        request.setMessageResponseHandler(new AbstractMOResponseHandler(reportNotify, this) {
            @Override
            public void setResponseInfo(ReportNotify reportNotify, IMessage msgRequest, IMessage msgResponse) {
                SmppDeliverSmResponseMessage responseMessage = (SmppDeliverSmResponseMessage) msgResponse;
                reportNotify.setNotify_Response_Status("MO:" + responseMessage.getHeader().getCommandStatus());
            }

            @Override
            public void setRequestInfo(ReportNotify reportNotify, IMessage msgRequest) {
                SmppDeliverSmRequestMessage request = (SmppDeliverSmRequestMessage) msgRequest;
                String msgContent = request.getMsgContent();
                reportNotify.setContent(msgContent);
                reportNotify.setContent_Length(msgContent.length());
            }
        });
        return request;
    }
}
