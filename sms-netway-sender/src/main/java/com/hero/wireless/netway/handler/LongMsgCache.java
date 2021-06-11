package com.hero.wireless.netway.handler;

import com.drondea.wireless.util.SuperLogger;
import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @version V3.0.0
 * @description: 缓存长短信片段信息
 * @author: 刘彦宁
 * @date: 2020年09月28日15:37
 **/
public class LongMsgCache {

    private String[] msgIds;
    private String[] mobiles;
    private String subCode;
    private Long timestamp;
    private AtomicInteger sumAtomic = new AtomicInteger();
    private String content;
    private int totalMsg;

    public LongMsgCache(int totalMsg) {
        msgIds = new String[totalMsg];
        this.totalMsg = totalMsg;
    }

    public boolean saveCachedMsgId(int index, String msgId) {
        if (index >= msgIds.length) {
            SuperLogger.error("array out of bond " + msgId + ":" + index);
            return false;
        }
        msgIds[index] = msgId;
        return sumAtomic.incrementAndGet() == totalMsg;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMsgIdStr() {
        if (msgIds == null) {
            return "";
        }
        return StringUtils.join(msgIds, ",");
    }

    public String[] getMobiles() {
        return mobiles;
    }

    public void setMobiles(String[] mobiles) {
        this.mobiles = mobiles;
    }

    public String getSubCode() {
        return subCode;
    }

    public void setSubCode(String subCode) {
        this.subCode = subCode;
    }

    public String[] getMsgIds() {
        return msgIds;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}
