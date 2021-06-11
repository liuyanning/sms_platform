package com.hero.wireless.web.util;

import com.hero.wireless.web.config.DatabaseCache;
import com.hero.wireless.web.entity.business.Channel;
import com.hero.wireless.web.entity.business.Enterprise;
import com.hero.wireless.web.entity.business.EnterpriseUser;
import org.apache.commons.lang3.StringUtils;

import java.util.function.Function;

/**
 * @version V3.0.0
 * @description: 翻译excel扩展字段
 * @author: 刘彦宁
 * @date: 2021年01月29日06:15
 **/
public class ExcelTranslator {

    public static Function<String, String> enterpriseNameFunction = inputStr -> {
        Enterprise enterprise = DatabaseCache.getEnterpriseCachedByNo(inputStr);
        if (enterprise == null) {
            return null;
        }
        return enterprise.getName();
    };

    public static Function<Integer, String> enterpriseUserRealNameFunction = input -> {
        EnterpriseUser enterpriseUser = DatabaseCache.getEnterpriseUserById(input);
        if (enterpriseUser == null) {
            return null;
        }
        return enterpriseUser.getReal_Name();
    };

    public static Function<String, String> channelNameFunction = inputStr -> {
        Channel channel = DatabaseCache.getChannelCachedByNo(inputStr);
        if (channel == null) {
            return null;
        }
        return channel.getName();
    };

    public static Function<String, Integer> contentLengthFunction = inputStr -> {
        if (StringUtils.isEmpty(inputStr)) {
            return 0;
        }
        return inputStr.length();
    };

    public static Function<Boolean, String> isLMSFunction = inputBoolean -> {
        if (inputBoolean == null || !inputBoolean ) {
            return "否";
        }
        return "是";
    };
}
