package com.hero.wireless.web.util;

import net.jodah.expiringmap.ExpiringMap;

import java.util.concurrent.TimeUnit;

/**
 * @author liuyanning
 */
public class ExpiringLocalCache<T> {

    private final ExpiringMap<String, T> cache;

    public ExpiringLocalCache() {
        this.cache = ExpiringMap.builder().
                variableExpiration().build();
    }

    public ExpiringLocalCache(long seconds) {
        this.cache = ExpiringMap.builder().
                expiration(seconds, TimeUnit.SECONDS).
                variableExpiration().build();
    }

    public ExpiringLocalCache(long seconds, int maxSize) {
        this.cache = ExpiringMap.builder().maxSize(maxSize).
                expiration(seconds, TimeUnit.SECONDS).
                variableExpiration().build();
    }

    public T get(String key) {
        return cache.get(key);
    }

    public T put(String key, T value) {
        return cache.put(key, value);
    }

    public void remove(String key) {
        cache.remove(key);
    }

    public T put(String key, T value, long duration, TimeUnit timeUnit) {
        return cache.put(key, value, duration, timeUnit);
    }

    public T putIfAbsent(String key, T value, long duration, TimeUnit timeUnit) {
        T pre = get(key);
        if (pre != null) {
            return pre;
        }
        String lockKey = "expire:" + key;
        synchronized (lockKey.intern()) {
            pre = get(key);
            if (pre != null) {
                return pre;
            }
            return cache.put(key, value, duration, timeUnit);
        }
    }

    public T putIfAbsent(String key, T value) {
        return cache.putIfAbsent(key, value);
    }

    public void setExpiration(String key, long duration, TimeUnit timeUnit) {
        cache.setExpiration(key, duration, timeUnit);
    }
}
