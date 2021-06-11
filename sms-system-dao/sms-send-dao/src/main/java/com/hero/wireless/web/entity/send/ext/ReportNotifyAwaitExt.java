package com.hero.wireless.web.entity.send.ext;

import com.hero.wireless.web.entity.send.ReportNotify;
import com.hero.wireless.web.entity.send.ReportNotifyAwait;

import java.util.Date;

public class ReportNotifyAwaitExt extends ReportNotifyAwait {

	private static final long serialVersionUID = 1L;
	private Date minSubmitDate;
	private Date maxSubmitDate;
	private String tableSuffix;
    private String notifySubmitDate;
    private String notifyResponseDate;

    public Date getMinSubmitDate() {
        return minSubmitDate;
    }

    public void setMinSubmitDate(Date minSubmitDate) {
        this.minSubmitDate = minSubmitDate;
    }

    public Date getMaxSubmitDate() {
        return maxSubmitDate;
    }

    public void setMaxSubmitDate(Date maxSubmitDate) {
        this.maxSubmitDate = maxSubmitDate;
    }

    public String getTableSuffix() {
        return tableSuffix;
    }

    public void setTableSuffix(String tableSuffix) {
        this.tableSuffix = tableSuffix;
    }

    public String getNotifySubmitDate() {
        return notifySubmitDate;
    }

    public void setNotifySubmitDate(String notifySubmitDate) {
        this.notifySubmitDate = notifySubmitDate;
    }

    public String getNotifyResponseDate() {
        return notifyResponseDate;
    }

    public void setNotifyResponseDate(String notifyResponseDate) {
        this.notifyResponseDate = notifyResponseDate;
    }
}
