package com.hero.wireless.web.service.base;
public class QueueDataDetail {
    /**消费数据*/
    private long consumeTotal;
    /**总数据*/
    private long productTotal;
    /**未消费数据*/
    private long remainTotal;
    /**重试数据*/
    private long retryTotal;
    private long retryRemainTotal;

    public long getConsumeTotal() {
        return consumeTotal;
    }
    public long getProductTotal() {
        return productTotal;
    }
    public long getRemainTotal() {
        return remainTotal;
    }
    public void setConsumeTotal(long consumeTotal) {
        this.consumeTotal = consumeTotal;
    }
    public void setProductTotal(long productTotal) {
        this.productTotal = productTotal;
    }
    public void setRemainTotal(long remainTotal) {
        this.remainTotal = remainTotal;
    }

    public long getRetryTotal() {
        return retryTotal;
    }

    public void setRetryTotal(long retryTotal) {
        this.retryTotal = retryTotal;
    }

    public long getRetryRemainTotal() {
        return retryRemainTotal;
    }

    public void setRetryRemainTotal(long retryRemainTotal) {
        this.retryRemainTotal = retryRemainTotal;
    }
}