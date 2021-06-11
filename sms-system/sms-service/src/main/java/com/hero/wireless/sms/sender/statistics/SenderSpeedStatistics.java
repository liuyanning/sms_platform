package com.hero.wireless.sms.sender.statistics;

import com.drondea.sms.common.util.SystemClock;
import com.drondea.wireless.config.Constant;
import com.hero.wireless.web.entity.business.Channel;
import com.hero.wireless.web.entity.send.Report;
import com.hero.wireless.web.entity.send.Submit;
import com.hero.wireless.web.util.LocalCache;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @version V3.0.0
 * @description: 发送速度统计
 * @author: 刘彦宁
 * @date: 2021年03月01日13:47
 **/
public class SenderSpeedStatistics extends BaseSpeedStatistics {

    /**
     * 回执统计超时时间，超时后不统计
     */
    public static int REPORT_TIMOUT_SECONDS = 5 * 60;

    /**
     * 单个通道发送速度
     */
    public static ConcurrentHashMap<String, AtomicInteger> SINGLE_CHANNEL_SUBMIT_SPEED = new ConcurrentHashMap<>();
    /**
     * 所有通道发送总速度
     */
    public static ConcurrentHashMap<String, AtomicInteger> CHANNEL_SUBMIT_SPEED = new ConcurrentHashMap<>();

    /**
     * 单个通道回执速度
     */
    public static ConcurrentHashMap<String, AtomicInteger> SINGLE_CHANNEL_REPORT_SPEED = new ConcurrentHashMap<>();
    /**
     * 所有通道回执速度
     */
    public static ConcurrentHashMap<String, AtomicInteger> CHANNEL_REPORT_SPEED = new ConcurrentHashMap<>();

    public static LocalCache<ChannelStatistics> CHANNEL_STATISTICS = new LocalCache<>(REPORT_TIMOUT_SECONDS);


    public static void channelSubmitSpeedCount(String senderIp, int channelId, int num) {
        long seconds = getSimpleSecond(SystemClock.now());
        String channelSubmitSpeedKey = getTotalKey(Constant.CHANNEL_SUBMIT_SPEED_DIR_NAME, senderIp, seconds);
        AtomicInteger channelSubmitTotal = getCachedCounter(channelSubmitSpeedKey, CHANNEL_SUBMIT_SPEED);
        channelSubmitTotal.addAndGet(num);

        String singleChannelSubmitSpeedKey = getSingleKey( Constant.CHANNEL_SUBMIT_SPEED_DIR_NAME, channelId, seconds);
        AtomicInteger singleChannelSubmitTotal = getCachedCounter(singleChannelSubmitSpeedKey, SINGLE_CHANNEL_SUBMIT_SPEED);
        singleChannelSubmitTotal.addAndGet(num);
    }

    public static void channelReportSpeedCount(String senderIp, int channelId) {
        long seconds = getSimpleSecond(SystemClock.now());
        String channelReportSpeedKey = getTotalKey(Constant.CHANNEL_REPORT_SPEED_DIR_NAME, senderIp, seconds);
        AtomicInteger channelReportTotal = getCachedCounter(channelReportSpeedKey, CHANNEL_REPORT_SPEED);
        channelReportTotal.incrementAndGet();
        String singleChannelReportSpeedKey = getSingleKey(Constant.CHANNEL_REPORT_SPEED_DIR_NAME, channelId, seconds);
        AtomicInteger singleChannelReportTotal = getCachedCounter(singleChannelReportSpeedKey, SINGLE_CHANNEL_REPORT_SPEED);
        singleChannelReportTotal.incrementAndGet();
    }

    public static void channelSubmitSuccessCount(Channel channel, Submit submit) {
        //根据通道id和发送时间计算出key
        String key = getChannelStatisticsKey(submit.getSubmit_Response_Date().getTime(), channel.getId());
        //根据key获取对象
        ChannelStatistics cachedStatistics = getCachedStatistics(key, CHANNEL_STATISTICS);
        //增加提交数量
        cachedStatistics.addSubmitCount();
    }

    public static void channelReportNumCount(Integer channelId, Submit submit, Report report) {
        long submitTime = submit.getSubmit_Response_Date().getTime();
        //根据回执时间计算时长
        long reportTime = report.getStatus_Date().getTime();
        //回执耗时，毫秒
        long timePass = reportTime - submitTime;
        if (timePass > REPORT_TIMOUT_SECONDS * 1000) {
            return;
        }

        //根据通道id和发送时间计算出key
        String key = getChannelStatisticsKey(submitTime, channelId);
        //根据key获取对象
        ChannelStatistics cachedStatistics = CHANNEL_STATISTICS.get(key);
        if (cachedStatistics != null) {
            cachedStatistics.addReportCount((int) (timePass / 1000));
        }

    }

}
