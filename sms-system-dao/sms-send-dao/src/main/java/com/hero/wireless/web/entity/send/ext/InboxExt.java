package com.hero.wireless.web.entity.send.ext;

import com.hero.wireless.web.entity.send.Inbox;

public class InboxExt extends Inbox {

    private String minSubmitDate;
    private String maxSubmitDate;

    public String getMinSubmitDate() {
        return minSubmitDate;
    }

    public void setMinSubmitDate(String minSubmitDate) {
        this.minSubmitDate = minSubmitDate;
    }

    public String getMaxSubmitDate() {
        return maxSubmitDate;
    }

    public void setMaxSubmitDate(String maxSubmitDate) {
        this.maxSubmitDate = maxSubmitDate;
    }
}
