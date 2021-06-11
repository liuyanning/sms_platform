package com.hero.wireless.web.util;

import org.apache.commons.lang.StringUtils;

/**
 * @version V3.0.0
 * @description: 白名单数据库存储结构
 * @author: 刘彦宁
 * @date: 2020年11月02日17:10
 **/
public class WhiteListUtil {

    private static final String SPLIT_STR = "_";

    /**
     * 创建白名单redis中存的值
     * @param phoneNo
     * @param poolCode
     * @param enterpriseNo
     * @return
     */
    private static String genWhiteListRedisValue(String phoneNo, String poolCode, String enterpriseNo) {
        StringBuffer stringBuffer = new StringBuffer(phoneNo).append(SPLIT_STR);
        if (StringUtils.isNotBlank(poolCode)) {
            stringBuffer.append(poolCode).append(SPLIT_STR);
        }
        if (StringUtils.isNotBlank(enterpriseNo)) {
            stringBuffer.append(enterpriseNo);
        }
        return stringBuffer.toString();
    }

    /**
     * 本地缓存白名单的key
     * @param phoneNo
     * @param poolCode
     * @param enterpriseNo
     * @return
     */
    public static String getWhiteListKey(String phoneNo, String poolCode, String enterpriseNo) {
        return genWhiteListRedisValue(phoneNo, poolCode, enterpriseNo);
    }
}
