package com.hero.wireless.sms.sender.statistics;

public class ChannelStatisticsExt extends ChannelStatistics {

    private String time;

    /**
     * 5秒回执率
     */
    private double rate5;
    /**
     * 10秒回执率
     */
    private double rate10;
    /**
     * >10秒回执率
     */
    private double rateOther;

    private double rateTotal;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double getRate5() {
        return rate5;
    }

    public void setRate5(double rate5) {
        this.rate5 = rate5;
    }

    public double getRate10() {
        return rate10;
    }

    public void setRate10(double rate10) {
        this.rate10 = rate10;
    }

    public double getRateOther() {
        return rateOther;
    }

    public void setRateOther(double rateOther) {
        this.rateOther = rateOther;
    }

    public double getRateTotal() {
        return rateTotal;
    }

    public void setRateTotal(double rateTotal) {
        this.rateTotal = rateTotal;
    }
}
