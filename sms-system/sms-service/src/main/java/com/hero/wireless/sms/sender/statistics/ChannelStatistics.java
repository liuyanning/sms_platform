package com.hero.wireless.sms.sender.statistics;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @version V3.0.0
 * @description: 通道成功率统计类
 * @author: 刘彦宁
 * @date: 2021年03月17日17:38
 **/
public class ChannelStatistics {

    private static final Integer FIVE = 5;
    private static final Integer TEN = 10;
    /**
     * 总提交数量
     */
    private AtomicInteger submitCount = new AtomicInteger();
    /**
     * 5秒回执数量
     */
    private AtomicInteger report5Count = new AtomicInteger();
    /**
     * 10秒回执数量
     */
    private AtomicInteger report10Count = new AtomicInteger();
    /**
     * > 10 秒回执数量
     */
    private AtomicInteger reportOtherCount = new AtomicInteger();

    public void addSubmitCount() {
        this.submitCount.incrementAndGet();
    }

    public void addReportCount(int seconds) {
        if (seconds <= FIVE) {
            this.report5Count.incrementAndGet();
            return;
        }
        if (seconds <= TEN) {
            this.report10Count.incrementAndGet();
            return;
        }
        this.reportOtherCount.incrementAndGet();
    }

    public AtomicInteger getSubmitCount() {
        return submitCount;
    }

    public void setSubmitCount(AtomicInteger submitCount) {
        this.submitCount = submitCount;
    }

    public AtomicInteger getReport5Count() {
        return report5Count;
    }

    public void setReport5Count(AtomicInteger report5Count) {
        this.report5Count = report5Count;
    }

    public AtomicInteger getReport10Count() {
        return report10Count;
    }

    public void setReport10Count(AtomicInteger report10Count) {
        this.report10Count = report10Count;
    }

    public AtomicInteger getReportOtherCount() {
        return reportOtherCount;
    }

    public void setReportOtherCount(AtomicInteger reportOtherCount) {
        this.reportOtherCount = reportOtherCount;
    }
}
