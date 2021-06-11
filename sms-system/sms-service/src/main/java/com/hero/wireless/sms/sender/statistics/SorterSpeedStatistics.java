package com.hero.wireless.sms.sender.statistics;

import com.drondea.sms.common.util.SystemClock;
import com.drondea.wireless.config.Constant;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @version V3.0.0
 * @description: 分拣速度统计
 * @author: 刘彦宁
 * @date: 2021年03月29日17:08
 **/
public class SorterSpeedStatistics extends BaseSpeedStatistics {

    /**
     * 分拣处理速度
     */
    public static ConcurrentHashMap<String, AtomicInteger> SORTER_SPEED_MAP = new ConcurrentHashMap<>();

    /**
     * 分拣速度
     * @param sorterIp
     */
    public static void sortSpeedCount(String sorterIp) {
        long seconds = getSimpleSecond(SystemClock.now());
        String sorterSpeedKey = getTotalKey(Constant.SORT_SPEED_DIR_NAME, sorterIp, seconds);
        AtomicInteger channelSubmitTotal = getCachedCounter(sorterSpeedKey, SORTER_SPEED_MAP);
        channelSubmitTotal.incrementAndGet();
    }

}
