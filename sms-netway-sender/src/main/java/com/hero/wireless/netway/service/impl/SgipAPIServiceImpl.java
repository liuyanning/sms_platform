package com.hero.wireless.netway.service.impl;

import com.drondea.sms.channel.ChannelSession;
import com.drondea.sms.common.SequenceNumber;
import com.drondea.sms.common.util.SgipSequenceNumber;
import com.drondea.sms.conf.sgip.SgipClientSocketConfig;
import com.drondea.sms.message.IMessage;
import com.drondea.sms.message.sgip12.SgipDeliverRequestMessage;
import com.drondea.sms.message.sgip12.SgipReportRequestMessage;
import com.drondea.sms.message.sgip12.SgipReportResponseMessage;
import com.drondea.sms.thirdparty.SmsDcs;
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
import org.springframework.stereotype.Component;

@Component
public class SgipAPIServiceImpl extends AbstractTcpService {

    @Override
    public String genLocalCacheKey(String bachNum, String userId) {
        return "sgip_" + bachNum + "_" + userId;
    }

    @Override
    public IMessage getReportMessage(ChannelSession channelSession, Report entity) {
        // 写入状态
        SgipReportRequestMessage request = new SgipReportRequestMessage();
        SgipClientSocketConfig configuration = (SgipClientSocketConfig) channelSession.getConfiguration();
        String phoneNo = entity.getPhone_No().replaceFirst("\\+", "");
        phoneNo = DatabaseCache.getUserCountryCodePhoneNo(entity.getEnterprise_User_Id(), phoneNo);
        SgipSequenceNumber sgipSequenceNumber = new SgipSequenceNumber(configuration.getNodeId(), channelSession.getSequenceNumber().next());
        request.getHeader().setSequenceNumber(sgipSequenceNumber);
        request.setUserNumber(phoneNo);

        String msgId = entity.getEnterprise_Msg_Id();
        if(StringUtils.isBlank(msgId)){
            return null;
        }
        request.setSubmitSequenceNumber(new SgipSequenceNumber(msgId));

        if (entity.getStatus_Code().equalsIgnoreCase(ReportStatus.SUCCESS.toString())) {
            request.setState((short) 0);
        }else{
            request.setState((short) 2);
            //人工审核判断,分拣出问题后description是null
            if (ReportStatus.REJECTD.equals(entity.getNative_Status())
                    || entity.getDescription() == null) {
                request.setErrorCode((short) 32);
            } else {
                request.setErrorCode(Short.parseShort(entity.getDescription()));
            }
        }

        ReportNotify reportNotify = SMSUtil.buildReportNotify(entity);
        request.setMessageResponseHandler(new AbstractReportResponseHandler(reportNotify, this) {
            @Override
            public void setResponseInfo(ReportNotify reportNotify, IMessage msgRequest, IMessage msgResponse) {
                SgipReportResponseMessage responseMessage = (SgipReportResponseMessage) msgResponse;
                reportNotify.setNotify_Response_Status(String.valueOf(responseMessage.getResult()));
            }
        });

        return request;
    }

    @Override
    public IMessage getMOMessage(ChannelSession channelSession, Inbox inbox) {
        // 写入上行
        SgipDeliverRequestMessage request = new SgipDeliverRequestMessage();

        SequenceNumber sequenceNumber = channelSession.getSequenceNumber();
        SgipClientSocketConfig configuration = (SgipClientSocketConfig) channelSession.getConfiguration();
        SgipSequenceNumber sgipSequenceNumber = new SgipSequenceNumber(configuration.getNodeId(), sequenceNumber.next());
        request.getHeader().setSequenceNumber(sgipSequenceNumber);
        if (StringUtils.isNotBlank(inbox.getCharset())) {
            request.setMsgContent(inbox.getContent(), new SmsDcs(SMSUtil.getGeneralDataCodingDcs(inbox.getCharset())));
        } else {
            request.setMsgContent(inbox.getContent());
        }
        String phoneNo = inbox.getPhone_No().replaceFirst("\\+", "");
        //过滤前的手机号
        phoneNo = DatabaseCache.getUserCountryCodePhoneNo(inbox.getEnterprise_User_Id(),phoneNo);
        request.setUserNumber(phoneNo);
        request.setSpNumber(configuration.getUserName() + StringUtils.defaultString(inbox.getSub_Code(), ""));

        EnterpriseUser enterpriseUser = DatabaseCache.getEnterpriseUserCachedById(Integer.parseInt(configuration.getId()));
        if (enterpriseUser == null) {
            inbox.setNotify_Status_Code(NotifyStatus.FAILD.toString());
            return null;
        }
        request.setSpNumber(getDestId(configuration.getUserName(), enterpriseUser.getMo_Sp_Type_Code(), inbox));

        ReportNotify reportNotify =  SMSUtil.buildReportNotify(inbox);
        request.setMessageResponseHandler(new AbstractMOResponseHandler(reportNotify, this) {
            @Override
            public void setResponseInfo(ReportNotify reportNotify, IMessage msgRequest, IMessage msgResponse) {
                SgipReportResponseMessage responseMessage = (SgipReportResponseMessage) msgResponse;
                reportNotify.setNotify_Response_Status("MO:" + responseMessage.getResult());
            }

            @Override
            public void setRequestInfo(ReportNotify reportNotify, IMessage msgRequest) {
                SgipDeliverRequestMessage request = (SgipDeliverRequestMessage) msgRequest;
                String msgContent = request.getMsgContent();
                reportNotify.setContent(msgContent);
                reportNotify.setContent_Length(msgContent.length());
            }
        });
        return request;
    }
}

