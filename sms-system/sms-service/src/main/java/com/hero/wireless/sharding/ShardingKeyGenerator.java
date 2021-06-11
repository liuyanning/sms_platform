package com.hero.wireless.sharding;

import com.hero.wireless.web.util.ApplicationContextUtil;
import org.apache.shardingsphere.sharding.spi.KeyGenerateAlgorithm;

/**
 * @version V3.0.0
 * @description: id生成
 * @author: 刘彦宁
 * @date: 2021年01月26日10:36
 **/
public class ShardingKeyGenerator {

    public static Long getKey() {
        KeyGenerateAlgorithm snowflakeAlgorithm = ApplicationContextUtil.getBean("snowflakeAlgorithm");
        return (Long) snowflakeAlgorithm.generateKey();
    }
}
