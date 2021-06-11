package com.hero.wireless.web.util;

import java.util.concurrent.TimeUnit;

/**
 * @author liuyanning
 */
public class GlobalStringCache {

    /**
     * 缓存重复操作key
     * 最多缓存1个小时
     */
    private static ExpiringLocalCache<String> cache = new ExpiringLocalCache<>();

    public static Boolean getAndSet(String key, String value) {
        String result = cache.get(key);
        if (result != null) {
            return false;
        }
        cache.put(key, value);
        return true;
    }

    public static void put(String key, String value) {
        cache.put(key, value);
    }

    public static String get(String key) {
        return cache.get(key);
    }

    public static String putIfAbsent(String key, String value) {
        return cache.putIfAbsent(key, value);
    }

    public static void remove(String key) {
        cache.remove(key);
    }

    public static void expire(String key,long duration, TimeUnit timeUnit) {
        String pre = cache.get(key);
        if (pre == null) {
            return;
        }
        cache.setExpiration(key, duration, timeUnit);
    }

    public static void putAndExpire(String key, String value, long duration, TimeUnit timeUnit) {
        put(key, value);
        expire(key, duration, timeUnit);
    }
}
