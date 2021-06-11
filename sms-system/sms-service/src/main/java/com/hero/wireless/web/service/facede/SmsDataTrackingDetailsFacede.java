package com.hero.wireless.web.service.facede;

import com.hero.wireless.web.entity.send.ReportNotify;
import com.hero.wireless.web.entity.send.ext.InputLogExt;
import com.hero.wireless.web.entity.send.ext.ReportExt;
import com.hero.wireless.web.entity.send.ext.SubmitExt;
import java.util.List;

public class SmsDataTrackingDetailsFacede {
    private List<InputLogExt> inputLogList;
    private List<SubmitExt> submitList;
    private List<ReportExt> reportList;
    private List<ReportNotify> notifyList;

    public SmsDataTrackingDetailsFacede() {
    }

    public SmsDataTrackingDetailsFacede(List<InputLogExt> inputLogList, List<SubmitExt> submitList,List<ReportExt> reportList, List<ReportNotify> notifyList){
        this.inputLogList= inputLogList;
        this.submitList= submitList;
        this.reportList= reportList;
        this.notifyList= notifyList;
    }

    public List<InputLogExt> getInputLogList() {
        return inputLogList;
    }

    public void setInputLogList(List<InputLogExt> inputLogList) {
        this.inputLogList = inputLogList;
    }

    public List<SubmitExt> getSubmitList() {
        return submitList;
    }

    public void setSubmitList(List<SubmitExt> submitList) {
        this.submitList = submitList;
    }

    public List<ReportExt> getReportList() {
        return reportList;
    }

    public void setReportList(List<ReportExt> reportList) {
        this.reportList = reportList;
    }

    public List<ReportNotify> getNotifyList() {
        return notifyList;
    }

    public void setNotifyList(List<ReportNotify> notifyList) {
        this.notifyList = notifyList;
    }


}
