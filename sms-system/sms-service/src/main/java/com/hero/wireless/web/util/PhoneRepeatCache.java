package com.hero.wireless.web.util;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author liuyanning
 */
public class PhoneRepeatCache {

    private static ExpiringLocalCache<AtomicInteger> repeatCache = new ExpiringLocalCache<>();

    public static int increment(String key) {
        AtomicInteger pre = repeatCache.get(key);
        if (pre != null) {
            return pre.incrementAndGet();
        }
        String lockKey = "r:" + key;
        synchronized (lockKey.intern()) {
            pre = repeatCache.get(key);
            if (pre != null) {
                return pre.incrementAndGet();
            }
            repeatCache.put(key, new AtomicInteger(1));
            return 1;
        }
    }

    public static int get(String key) {
        AtomicInteger pre = repeatCache.get(key);
        if (pre != null) {
            return pre.intValue();
        }
        return 0;
    }

    public static void remove(String key) {
        repeatCache.remove(key);
    }

    public static void expire(String key,int minute, TimeUnit timeUnit) {
        AtomicInteger pre = repeatCache.get(key);
        if (pre == null) {
            return;
        }
        repeatCache.setExpiration(key, minute, timeUnit);
    }
}
