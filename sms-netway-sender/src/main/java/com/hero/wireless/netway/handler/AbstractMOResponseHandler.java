package com.hero.wireless.netway.handler;

import com.drondea.sms.message.IMessage;
import com.drondea.sms.type.IMessageResponseHandler;
import com.drondea.wireless.util.ServiceException;
import com.hero.wireless.enums.NotifyStatus;
import com.hero.wireless.netway.service.impl.AbstractTcpService;
import com.hero.wireless.web.entity.send.ReportNotify;
import com.hero.wireless.web.util.CopyUtil;

import java.util.Date;

/**
 * @version V3.0.0
 * @description:
 * @author: 刘彦宁
 * @date: 2021年02月19日15:48
 **/
public abstract class AbstractMOResponseHandler implements IMessageResponseHandler {

    private ReportNotify reportNotify;
    private AbstractTcpService tcpService;

    public AbstractMOResponseHandler(ReportNotify reportNotify, AbstractTcpService tcpService) {
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

    /**
     * 设置请求信息
     * @param reportNotify
     * @param msgRequest
     */
    public abstract void setRequestInfo(ReportNotify reportNotify, IMessage msgRequest);

    @Override
    public void messageComplete(IMessage msgRequest, IMessage msgResponse) {
        ReportNotify cloneNotify = new ReportNotify();
        CopyUtil.NOTIFY_NOTIFY_COPIER.copy(reportNotify, cloneNotify, null);
        setRequestInfo(cloneNotify, msgRequest);
        setResponseInfo(cloneNotify, msgRequest, msgResponse);
        cloneNotify.setNotify_Submit_Date(new Date(msgRequest.getSendTimeStamp()));
        cloneNotify.setNotify_Response_Date(new Date());
        cloneNotify.setNotify_Status_Code(NotifyStatus.SUCCESS.toString());
        tcpService.saveReportNotify(cloneNotify);
    }

    @Override
    public void messageExpired(String key, IMessage msgRequest) {
        ReportNotify cloneNotify = new ReportNotify();
        CopyUtil.NOTIFY_NOTIFY_COPIER.copy(reportNotify, cloneNotify, null);
        setRequestInfo(cloneNotify, msgRequest);
        cloneNotify.setNotify_Submit_Date(new Date(msgRequest.getSendTimeStamp()));
        cloneNotify.setNotify_Response_Status("Expired");
        cloneNotify.setNotify_Response_Date(new Date());
        cloneNotify.setNotify_Status_Code("MO:" + NotifyStatus.FAILD.toString());
        tcpService.saveReportNotify(cloneNotify);
    }

    @Override
    public void sendMessageFailed(IMessage msgRequest) {
        ReportNotify cloneNotify = new ReportNotify();
        CopyUtil.NOTIFY_NOTIFY_COPIER.copy(reportNotify, cloneNotify, null);
        setRequestInfo(cloneNotify, msgRequest);
        cloneNotify.setNotify_Submit_Date(new Date(msgRequest.getSendTimeStamp()));
        cloneNotify.setNotify_Response_Status("sendFailed");
        cloneNotify.setNotify_Status_Code("MO:" + NotifyStatus.FAILD.toString());
        cloneNotify.setNotify_Response_Date(new Date());
        tcpService.saveReportNotify(cloneNotify);
        throw new ServiceException("MO通知失败========>" + cloneNotify.getMsg_Batch_No());
    }
}
