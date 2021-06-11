package com.hero.wireless.netway.handler;

import com.drondea.sms.message.IMessage;
import com.drondea.sms.type.IMessageResponseHandler;
import com.drondea.wireless.util.ServiceException;
import com.hero.wireless.enums.NotifyStatus;
import com.hero.wireless.netway.service.impl.AbstractTcpService;
import com.hero.wireless.web.entity.send.ReportNotify;

import java.util.Date;

/**
 * @version V3.0.0
 * @description:
 * @author: 刘彦宁
 * @date: 2021年02月19日15:48
 **/
public abstract class AbstractReportResponseHandler implements IMessageResponseHandler {

    private ReportNotify reportNotify;
    private AbstractTcpService tcpService;

    public AbstractReportResponseHandler(ReportNotify reportNotify, AbstractTcpService tcpService) {
        this.reportNotify = reportNotify;
        this.tcpService = tcpService;
    }

    /**
     * 设置每个协议的返回信息
     * @param reportNotify
     * @param msgRequest
     * @param msgResponse
     */
    public abstract void setResponseInfo(ReportNotify reportNotify, IMessage msgRequest, IMessage msgResponse);

    @Override
    public void messageComplete(IMessage msgRequest, IMessage msgResponse) {
        setResponseInfo(reportNotify, msgRequest, msgResponse);
        reportNotify.setNotify_Submit_Date(new Date(msgRequest.getSendTimeStamp()));
        reportNotify.setNotify_Response_Date(new Date());
        reportNotify.setNotify_Status_Code(NotifyStatus.SUCCESS.toString());
        tcpService.saveReportNotify(reportNotify);
    }

    @Override
    public void messageExpired(String key, IMessage msgRequest) {
        reportNotify.setNotify_Submit_Date(new Date(msgRequest.getSendTimeStamp()));
        reportNotify.setNotify_Response_Status("Expired");
        reportNotify.setNotify_Status_Code(NotifyStatus.FAILD.toString());
        tcpService.saveReportNotify(reportNotify);
    }

    @Override
    public void sendMessageFailed(IMessage msgRequest) {
        reportNotify.setNotify_Submit_Date(new Date(msgRequest.getSendTimeStamp()));
        reportNotify.setNotify_Response_Status("sendFailed");
        reportNotify.setNotify_Status_Code(NotifyStatus.FAILD.toString());
        tcpService.saveReportNotify(reportNotify);
        throw new ServiceException("通知失败========>" + reportNotify.getEnterprise_Msg_Id());
    }
}
