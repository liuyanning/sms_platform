package com.hero.wireless.netty.handler;

import com.drondea.sms.message.cmpp.CmppDeliverRequestMessage;
import com.drondea.sms.message.cmpp.CmppReportRequestMessage;
import com.drondea.sms.message.sgip12.SgipReportRequestMessage;
import com.drondea.sms.message.smgp30.msg.SmgpDeliverRequestMessage;
import com.drondea.sms.message.smgp30.msg.SmgpReportMessage;
import com.drondea.sms.message.smpp34.SmppDeliverSmRequestMessage;
import com.drondea.wireless.util.CommonThreadPoolFactory;
import com.drondea.wireless.util.DateTime;
import com.drondea.wireless.util.SuperLogger;
import com.hero.wireless.enums.ProtocolType;
import com.hero.wireless.enums.ReportStatus;
import com.hero.wireless.sms.sender.service.SubmitCacheService;
import com.hero.wireless.web.entity.business.Channel;
import com.hero.wireless.web.entity.send.Report;
import com.hero.wireless.web.entity.send.Submit;
import com.hero.wireless.web.entity.send.ext.SubmitExt;
import com.hero.wireless.web.service.ISendManage;
import com.hero.wireless.web.util.*;
import org.apache.commons.lang3.ObjectUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @version V3.0.0
 * @description: 回执报告的包装类型
 * @author: 刘彦宁
 * @date: 2020年07月24日14:29
 **/
public class ReportWrap {
    private String msgId;
    private String phoneNo;
    private String statusCode;
    private String nativeStatus;
    private String description;
    private String spNumber;
    private Date statusDate = new Date();
    private String groupCode;
    private String channelNo;
    private String protocolTypeCode;
    private String doneTime;
    private String submitTime;
    private Integer channelId;

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getNativeStatus() {
        return nativeStatus;
    }

    public void setNativeStatus(String nativeStatus) {
        this.nativeStatus = nativeStatus;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSpNumber() {
        return spNumber;
    }

    public void setSpNumber(String spNumber) {
        this.spNumber = spNumber;
    }

    public Date getStatusDate() {
        return statusDate;
    }

    public void setStatusDate(Date statusDate) {
        this.statusDate = statusDate;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public String getChannelNo() {
		return channelNo;
	}

	public void setChannelNo(String channelNo) {
		this.channelNo = channelNo;
	}

	public String getProtocolTypeCode() {
		return protocolTypeCode;
	}

	public void setProtocolTypeCode(String protocolTypeCode) {
		this.protocolTypeCode = protocolTypeCode;
	}

	public String getDoneTime() {
		return doneTime;
	}

	public void setDoneTime(String doneTime) {
		this.doneTime = doneTime;
	}

	public String getSubmitTime() {
		return submitTime;
	}

	public void setSubmitTime(String submitTime) {
		this.submitTime = submitTime;
	}

    public Integer getChannelId() {
        return channelId;
    }

    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }

    public ReportWrap() {
        super();
    }


    public String getChannelGroupCode(Channel channel){
        return channel.getGroup_Code();
    }

    public ReportWrap(CmppDeliverRequestMessage dp, Channel channel) {
        CmppReportRequestMessage reportMessage = dp.getReportRequestMessage();
        String result = reportMessage.getStat();
        this.setMsgId(reportMessage.getMsgId().toString());
        this.setNativeStatus(result);
        if ("DELIVRD".equalsIgnoreCase(result)) {
            this.setStatusCode(ReportStatus.SUCCESS.toString());
        } else {
            this.setStatusCode(ReportStatus.FAILD.toString());
        }
        this.setSpNumber(dp.getDestId());
        this.setGroupCode(getChannelGroupCode(channel));
        this.setProtocolTypeCode(ProtocolType.CMPP.toString());
        this.setSubmitTime(reportMessage.getSubmitTime());
        this.setDoneTime(reportMessage.getDoneTime());
        this.setPhoneNo(reportMessage.getDestterminalId());
        this.setChannelNo(channel.getNo());
        this.setChannelId(channel.getId());
    }


    public ReportWrap(SgipReportRequestMessage dp, Channel channel) {
        this.setMsgId(dp.getSubmitSequenceNumber().toString());
        this.setNativeStatus(String.format("State:%1$s,ErrorCode:%2$s", dp.getState(), dp.getErrorCode()));
        this.setDescription(String.valueOf(dp.getErrorCode()));
        if (dp.getState() == 0 && dp.getErrorCode() == 0) {
            this.setStatusCode(ReportStatus.SUCCESS.toString());
        } else {
            this.setStatusCode(ReportStatus.FAILD.toString());
        }
        this.setSpNumber(dp.getUserNumber());
        this.setGroupCode(getChannelGroupCode(channel));
        //errorcode 借助description存储
        this.setDescription(String.valueOf(dp.getErrorCode()));
        this.setProtocolTypeCode(ProtocolType.SGIP.toString());
        this.setPhoneNo(dp.getUserNumber());
        this.setChannelNo(channel.getNo());
        this.setChannelId(channel.getId());
    }

    public ReportWrap(SmgpDeliverRequestMessage dp, Channel channel) {
        SmgpReportMessage reportMessage = dp.getReport();
        String result = reportMessage.getStat();
        String err = reportMessage.getErr();
        this.setMsgId(reportMessage.getSmgpMsgId().toString());
        this.setNativeStatus(result+"@"+err);
        if ("DELIVRD".equalsIgnoreCase(result)) {
            this.setStatusCode(ReportStatus.SUCCESS.toString());
        } else {
            this.setStatusCode(ReportStatus.FAILD.toString());
        }
        this.setSpNumber(dp.getDestTermId());
        this.setGroupCode(getChannelGroupCode(channel));
        this.setDescription(err);//描述字段存放 Err错误代码表 6.2.63.2
        this.setProtocolTypeCode(ProtocolType.SMGP.toString());
        this.setSubmitTime(reportMessage.getSubTime());
        this.setDoneTime(reportMessage.getDoneTime());
        this.setPhoneNo(dp.getSrcTermId());
        this.setChannelNo(channel.getNo());
        this.setChannelId(channel.getId());
    }

    public ReportWrap(SmppDeliverSmRequestMessage dp, Channel channel) {
        String result = dp.getReportRequest().getStat();
        String id = String.valueOf(dp.getReportRequest().getId());
        this.setMsgId(id);
        this.setNativeStatus(result);
        Map<String, ChannelUtil.OtherParameter> channelParameter = ChannelUtil.getParameter(channel);
        String submitResponseIdRadix = ChannelUtil.getParameterValue(channelParameter, "submit_response_id_radix",
                "ignore");
        String reportIdRadix = ChannelUtil.getParameterValue(channelParameter, "report_id_radix", "ignore");
        // 如果submitResponseId是十六机制，reportId是十进制，需要把reportId转成十六进制
        // 以submitResponseId为准
        if (submitResponseIdRadix.equals("hex") && reportIdRadix.equals("oct")) {
            // 香港移动可以通过option获取
            // dp.getOptionalParameter((short) 0x001e).getValueAsString();
            this.setMsgId(Integer.toHexString(Integer.parseInt(id)).toUpperCase());
        } else if (submitResponseIdRadix.equals("oct") && reportIdRadix.equals("hex")) {
            this.setMsgId(String.valueOf(Integer.parseInt(id, 16)).toUpperCase());
        }
        if ("DELIVRD".equalsIgnoreCase(result)) {
            this.setStatusCode(ReportStatus.SUCCESS.toString());
        } else {
            this.setStatusCode(ReportStatus.FAILD.toString());
        }
        this.setSpNumber(dp.getDestinationAddr());
        this.setGroupCode(getChannelGroupCode(channel));
        this.setProtocolTypeCode(ProtocolType.SMPP.toString());
        this.setPhoneNo(dp.getSourceAddr());
        this.setChannelNo(channel.getNo());
        this.setChannelId(channel.getId());
    }

    private void buildReportAndNotify(String key, Submit submit) {
        //删除redis
        SubmitCacheService.removeSubmitInRedis(key);

        Report report = SMSUtil.buildReport(submit);
        report.setNative_Status(this.nativeStatus);
        report.setStatus_Code(this.statusCode);
        report.setStatus_Date(this.statusDate);
        report.setSP_Number(this.spNumber);
        report.setDescription(this.description);
        report.setCreate_Date(new Date());
        QueueUtil.saveReport(report);
    }

    /**
     * 先从本地缓存获取submit，没有的话mysql里面获取
     * @param retryTimes 重试次数
     */
    public void saveReport(int retryTimes) {
        //现在缓存里面拿没有拿到再去mysql里面拿，提高性能
        String key = CacheKeyUtil.genNewCacheSubmitedKey(this.msgId, this.groupCode);
        Submit submit = SubmitCacheService.SUBMIT_LOCAL_CACHE.get(key);
        if (submit == null) {
            ISendManage sendManage = ApplicationContextUtil
                    .getBean("sendManage");
            SubmitExt condition = new SubmitExt();
            condition.setMinSubmitDate(DateTime.addDay(-4));
            condition.setMaxSubmitDate(new Date());
            condition.setChannel_Msg_Id(this.msgId);
            condition.setGroup_Code(this.groupCode);
            List<SubmitExt> submitExts = sendManage.querySubmitListSharding(condition);
            if(ObjectUtils.isNotEmpty(submitExts)) {
                submit = submitExts.get(0);
            }
            if (submit == null) {
                if (retryTimes <= 0) { // 没有了重试机会
                    return;
                } else {
                    CommonThreadPoolFactory.getInstance().getScheduleExecutor().schedule(() -> {
                        saveReport(retryTimes - 1);
                    }, 1, TimeUnit.MINUTES);
                    SuperLogger.debug("查不到：" + key + "，等待1分钟后重试");
                    return;
                }
            }
        } else {
            //删除缓存
            SubmitCacheService.SUBMIT_LOCAL_CACHE.remove(key);
        }

        buildReportAndNotify(key, submit);
    }


    @Override
    public String toString() {
        return "ReportWrap [msgId=" + msgId + ", phoneNo=" + phoneNo + ", statusCode=" + statusCode
                + ", nativeStatus=" + nativeStatus + ", description=" + description + ", spNumber=" + spNumber
                + ", statusDate=" + statusDate + ", channelNo=" + channelNo
                + ", protocolTypeCode=" + protocolTypeCode + "]";
    }
}
