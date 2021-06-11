package com.hero.wireless.web.util;

import com.hero.wireless.web.config.DatabaseCache;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @version V3.0.0
 * @description: 白名单数据库存储结构
 * @author: 刘彦宁
 * @date: 2020年11月02日17:10
 **/
public class BlackListUtil {

    private static final String SPLIT_STR = "_";

    /**
     * 添加黑名单
     *
     * @param poolCode
     * @param enterpriseNo
     */
    public static boolean addBlackList(String phoneNo, String poolCode, String enterpriseNo, Integer enterpriseUserId) {
        List<String> blackList = CacheUtil.getMapCachedList(phoneNo, DatabaseCache.SMS_BLACK_MAP);
        if (blackList == null) {
            blackList = new ArrayList<>();
        }
        String blackListRedisValue = genBlackListRedisValue(phoneNo, poolCode, enterpriseNo, enterpriseUserId);
        if (blackList.contains(blackListRedisValue)) {
            return true;
        }
        blackList.add(blackListRedisValue);
        return true;
    }

    /**
     * 删除黑名单
     * @param phoneNo
     * @param poolCode
     * @param enterpriseNo
     */
    public static void removeBlackList(String phoneNo, String poolCode, String enterpriseNo, Integer enterpriseUserId) {
        List<String> blackList =  CacheUtil.getMapCachedList(phoneNo, DatabaseCache.SMS_BLACK_MAP);
        if(blackList == null) {
            return;
        }
        String genBlackListRedisValue = genBlackListRedisValue(phoneNo, poolCode, enterpriseNo, enterpriseUserId);
        blackList.remove(genBlackListRedisValue);
        //如果清空了就删除key
        if(blackList.size() == 0) {
            DatabaseCache.SMS_BLACK_MAP.remove(phoneNo);
            return;
        }
        DatabaseCache.SMS_BLACK_MAP.put(phoneNo, blackList);
    }

    /**
     * 根据传入参数判断是否是黑名单
     * @param phoneNo
     * @param poolCode
     * @param enterpriseNo
     * @param enterpriseUserId
     * @param isBlackFilter 是否要根据拦截策略配置进行黑名单判断
     * @param isAutoBlackFilter 是否要根据自动回T加入黑名单进行判断
     * @return
     */
    public static boolean isBlack(String phoneNo, String poolCode, String enterpriseNo, Integer enterpriseUserId,
                                  boolean isBlackFilter, boolean isAutoBlackFilter) {

        if (!isBlackFilter && !isAutoBlackFilter) {
            return false;
        }

        List<String> blackList = DatabaseCache.SMS_BLACK_MAP.get(phoneNo);
        if (blackList == null) {
            return false;
        }
        //回T自动加入黑名单判断,不需要加黑名单池参数
        if (isAutoBlackFilter) {
            String global = genBlackListRedisValue(phoneNo, null, enterpriseNo, enterpriseUserId);
            if (blackList.contains(global)) {
                return true;
            }
        }
        //如果不要拦截策略判断返回非黑名单
        if (!isBlackFilter) {
            return false;
        }

        StringBuffer stringBuffer = new StringBuffer();
        //全局黑名单
        if (blackList.contains(stringBuffer.toString())) {
            return true;
        }
        //企业全局黑名单
        StringBuffer enterpriseBuffer = new StringBuffer(enterpriseNo).append(SPLIT_STR);
        if (blackList.contains(enterpriseBuffer.toString())) {
            return true;
        }
        //黑名单池
        if (StringUtils.isNotBlank(poolCode)) {
            stringBuffer.append(poolCode).append(SPLIT_STR);
        }
        if (blackList.contains(stringBuffer.toString())) {
            return true;
        }
        //黑名单池+企业
        if (StringUtils.isNotBlank(enterpriseNo)) {
            stringBuffer.append(enterpriseNo).append(SPLIT_STR);
        }
        if (blackList.contains(stringBuffer.toString())) {
            return true;
        }

        return false;
    }

    /**
     * 创建白名单redis中存的值
     * @param phoneNo
     * @param poolCode
     * @param enterpriseNo
     * @return
     */
    private static String genBlackListRedisValue(String phoneNo, String poolCode, String enterpriseNo, Integer enterpriseUserId){
        StringBuffer stringBuffer = new StringBuffer();
        if (StringUtils.isNotBlank(poolCode)) {
            stringBuffer.append(poolCode).append(SPLIT_STR);
        }
        if (StringUtils.isNotBlank(enterpriseNo)) {
            stringBuffer.append(enterpriseNo).append(SPLIT_STR);
        }
        if (enterpriseUserId != null) {
            stringBuffer.append(enterpriseUserId);
        }
        return stringBuffer.toString();
    }

}
