package com.hero.wireless.sms.sender.statistics;

import com.drondea.wireless.config.Constant;
import com.hero.wireless.web.util.CacheUtil;
import com.hero.wireless.web.util.LocalCache;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @version V3.0.0
 * @description: 统计基础类
 * @author: 刘彦宁
 * @date: 2021年03月29日17:16
 **/
public class BaseSpeedStatistics {


    /**
     * 根据前缀、业务id、时间生成key
     * @param preKey
     * @param id
     * @param seconds
     * @return
     */
    public static String getSingleKey(String preKey, int id, long seconds) {
        return preKey + ":" + id + "_" + seconds;
    }

    /**
     * 根据前缀、服务器ip、时间生成key
     * @param preKey
     * @param ip
     * @param seconds
     * @return
     */
    public static String getTotalKey(String preKey, String ip, long seconds) {
        return preKey + ":" + ip + "_" + seconds;
    }

    /**
     * 根据前缀、服务器IP生成key
     * @param preKey
     * @param ip
     * @return
     */
    public static String getMaxKey(String preKey, String ip) {
        return preKey + "_" + ip;
    }

    /**
     * 计算通道回执率统计的key
     * @param timestamp
     * @param channelId
     * @return
     */
    public static String getChannelStatisticsKey(long timestamp, Integer channelId) {
        return  Constant.CHANNEL_STATISTICS_DIR_NAME + ":" + channelId + "_" + getSecondPeriod(timestamp);
    }

    /**
     * 把时间戳按照5秒分组
     * @param timestamp
     * @return
     */
    public static long getSecondPeriod(long timestamp) {
        return timestamp / 1000 / 5 * 5;
    }

    protected static AtomicInteger getCachedCounter(String key, ConcurrentHashMap<String, AtomicInteger> map) {
        return CacheUtil.getMapCachedObj(key, map, AtomicInteger.class);
    }

    protected static ChannelStatistics getCachedStatistics(String key, LocalCache<ChannelStatistics> localCache) {
        ConcurrentMap<String, ChannelStatistics> map = localCache.getMap();
        return CacheUtil.getMapCachedObj(key, map, ChannelStatistics.class);
    }

    public static long getSimpleSecond(long timeStamp) {
        return timeStamp / 1000;
    }

    /**
     * 从key中解析出当前时间戳（秒）
     * @param key
     * @return
     */
    public static long getSecondsFromKey(String key) {
        return Long.valueOf(key.split("_")[1]);
    }


}
