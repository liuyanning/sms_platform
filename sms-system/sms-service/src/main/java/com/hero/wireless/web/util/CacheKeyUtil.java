package com.hero.wireless.web.util;

import org.apache.commons.lang3.StringUtils;

public class CacheKeyUtil {

    public static String genNewCacheSubmitedKey(String channelSubMsgNo, String groupCode) {
        return channelSubMsgNo + (StringUtils.isEmpty(groupCode) ? "" : ("_" + groupCode));
    }
}
