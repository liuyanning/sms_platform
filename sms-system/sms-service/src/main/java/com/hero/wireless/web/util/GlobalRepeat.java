package com.hero.wireless.web.util;

import org.apache.commons.lang3.time.DateUtils;

import java.util.concurrent.TimeUnit;

/**
 * @author liuyanning
 */
public class GlobalRepeat {

    /**
     * 缓存重复操作key
     * 最多缓存1个小时
     */
    private static ExpiringLocalCache<Boolean> cache = new ExpiringLocalCache<>(DateUtils.MILLIS_PER_HOUR);

    public static boolean getAndSet(String key) {
        Boolean result = cache.get(key);
        if (result != null) {
            return false;
        }
        cache.put(key, true);
        return true;
    }

    public static void put(String key) {
        cache.put(key, true);
    }

    public static Boolean get(String key) {
        return cache.get(key);
    }

    public static Boolean putIfAbsent(String key) {
        return cache.putIfAbsent(key, true);
    }

    public static void remove(String key) {
        cache.remove(key);
    }

    public static void expire(String key,long duration, TimeUnit timeUnit) {
        Boolean pre = cache.get(key);
        if (pre == null) {
            return;
        }
        cache.setExpiration(key, duration, timeUnit);
    }
}
