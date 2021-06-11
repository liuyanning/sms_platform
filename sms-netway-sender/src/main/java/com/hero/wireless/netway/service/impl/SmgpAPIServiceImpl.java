package com.hero.wireless.netway.service.impl;

import com.drondea.sms.channel.ChannelSession;
import com.drondea.sms.common.util.SmgpMsgId;
import com.drondea.sms.common.util.SystemClock;
import com.drondea.sms.message.IMessage;
import com.drondea.sms.message.smgp30.msg.SmgpDeliverRequestMessage;
import com.drondea.sms.message.smgp30.msg.SmgpDeliverResponseMessage;
import com.drondea.sms.message.smgp30.msg.SmgpReportMessage;
import com.drondea.sms.session.smgp.SmgpServerSession;
import com.drondea.sms.thirdparty.SmsDcs;
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

@Component
public class SmgpAPIServiceImpl extends AbstractTcpService {

    @Override
    public String genLocalCacheKey(String bachNum, String userId) {
        return "smgp_" + bachNum + "_" + userId;
    }

    @Override
    public IMessage getReportMessage(ChannelSession channelSession, Report entity) {
        // 写入状态
        SmgpDeliverRequestMessage deliver = new SmgpDeliverRequestMessage();
        deliver.getHeader().setSequenceId(channelSession.getSequenceNumber().next());
        String phoneNo = entity.getPhone_No().replaceFirst("\\+", "");
        // 源终端MSISDN号码（状态报告时填为SMGP_SUBMIT消息的目的终端号码）
        phoneNo = DatabaseCache.getUserCountryCodePhoneNo(entity.getEnterprise_User_Id(), phoneNo);
        deliver.setSrcTermId(phoneNo);
        SmgpReportMessage smgpReportMessage = new SmgpReportMessage();

        String msgId = entity.getEnterprise_Msg_Id();
        if(StringUtils.isBlank(msgId)){
            return null;
        }
        SmgpMsgId smgpMsgId = new SmgpMsgId(msgId);
        // 2021.3.3 deliver的SmgpMsgId跟 report的SmgpMsgId 保持一致。
        deliver.setSmgpMsgId(smgpMsgId);
        smgpReportMessage.setSmgpMsgId(smgpMsgId);
        String time = DateFormatUtils.format(SystemClock.now(), "yyMMddHHmm");
        String submitTime = DateFormatUtils.format(entity.getSubmit_Date(), "yyMMddHHmm");
        smgpReportMessage.setSub("001");
        smgpReportMessage.setDlvrd("001");
        smgpReportMessage.setSubTime(submitTime);
        smgpReportMessage.setDoneTime(time);
        if (entity.getStatus_Code().equalsIgnoreCase(ReportStatus.SUCCESS.toString())) {
            smgpReportMessage.setStat("DELIVRD");
            smgpReportMessage.setErr("000");
        } else {
            String nativeStatus = StringUtils.defaultIfEmpty(entity.getNative_Status(), entity.getStatus_Code());
            String err = "";
            if(nativeStatus != null && nativeStatus.contains("@")){
                String[] split = nativeStatus.split("@");
                if (split.length > 0) {
                    nativeStatus = split[0];
                }
                if (split.length > 1) {
                    err = split[1];
                }
            }
            //审核拒绝
            if(ReportStatus.REJECTD.equals(nativeStatus)){
                err = "888";
            }
            smgpReportMessage.setStat(nativeStatus);
            smgpReportMessage.setErr(err == null ? "" : err);
        }
        smgpReportMessage.setTxt("");
        deliver.setReport(smgpReportMessage);

        UserChannelConfig channelConfig = ((SmgpServerSession) channelSession).getUserChannelConfig();
        deliver.setDestTermId(
                channelConfig.getUserName() + StringUtils.defaultString(entity.getSub_Code(), ""));

        ReportNotify reportNotify =  SMSUtil.buildReportNotify(entity);
        deliver.setMessageResponseHandler(new AbstractReportResponseHandler(reportNotify, this) {
            @Override
            public void setResponseInfo(ReportNotify reportNotify, IMessage msgRequest, IMessage msgResponse) {
                SmgpDeliverResponseMessage responseMessage = (SmgpDeliverResponseMessage) msgResponse;
                reportNotify.setNotify_Response_Status(String.valueOf(responseMessage.getStatus()));
            }
        });
        return deliver;
    }

    @Override
    public IMessage getMOMessage(ChannelSession channelSession, Inbox inbox) {
        // 写入上行
        SmgpDeliverRequestMessage request = new SmgpDeliverRequestMessage();
        request.setSmgpMsgId(new SmgpMsgId(channelSession.getSequenceNumber().next()));
        request.getHeader().setSequenceId(channelSession.getSequenceNumber().next());
        if (StringUtils.isNotBlank(inbox.getCharset())) {
            request.setMsgContent(inbox.getContent(), new SmsDcs(SMSUtil.getGeneralDataCodingDcs(inbox.getCharset())));
        } else {
            request.setMsgContent(inbox.getContent());
        }
        String phoneNo = inbox.getPhone_No().replaceFirst("\\+", "");
        //过滤前的手机号
        phoneNo = DatabaseCache.getUserCountryCodePhoneNo(inbox.getEnterprise_User_Id(), phoneNo);
        request.setSrcTermId(phoneNo);

        UserChannelConfig channelConfig = ((SmgpServerSession) channelSession).getUserChannelConfig();
        EnterpriseUser enterpriseUser = DatabaseCache.getEnterpriseUserCachedById(Integer.parseInt(channelConfig.getId()));
        if (enterpriseUser == null) {
            inbox.setNotify_Status_Code(NotifyStatus.FAILD.toString());
            return null;
        }
        request.setDestTermId(getDestId(channelConfig.getUserName(), enterpriseUser.getMo_Sp_Type_Code(), inbox));

        ReportNotify reportNotify =  SMSUtil.buildReportNotify(inbox);
        request.setMessageResponseHandler(new AbstractMOResponseHandler(reportNotify, this) {
            @Override
            public void setResponseInfo(ReportNotify reportNotify, IMessage msgRequest, IMessage msgResponse) {
                SmgpDeliverResponseMessage responseMessage = (SmgpDeliverResponseMessage) msgResponse;
                reportNotify.setNotify_Response_Status("MO:" + responseMessage.getStatus());
            }

            @Override
            public void setRequestInfo(ReportNotify reportNotify, IMessage msgRequest) {
                SmgpDeliverRequestMessage request = (SmgpDeliverRequestMessage) msgRequest;
                String msgContent = request.getMsgContent();
                reportNotify.setContent(msgContent);
                reportNotify.setContent_Length(msgContent.length());
            }
        });

        return request;
    }
}
