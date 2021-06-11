package com.hero.wireless.web.util;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalCause;
import com.google.common.cache.RemovalListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

/**
 * @version V3.0.0
 * @description: 默认的存储，本地存储
 * @author: 刘彦宁
 * @date: 2020年06月15日10:29
 **/
public class LocalCache<T> {

    private final Logger logger = LoggerFactory.getLogger(LocalCache.class);

    private final Cache<String, T> cache;
    private final ConcurrentMap<String, T> map;

    public LocalCache(long seconds) {
        this.cache = CacheBuilder.newBuilder().
                expireAfterWrite(seconds, TimeUnit.SECONDS).removalListener(removalListener).build();
        this.map = cache.asMap();
    }

    public LocalCache(long seconds, RemovalListener<String, T> removalListener) {
        this.cache = CacheBuilder.newBuilder().
                expireAfterWrite(seconds, TimeUnit.SECONDS).removalListener(removalListener).build();
        this.map = cache.asMap();
    }

    public LocalCache(long seconds, int maxSize) {
        this.cache = CacheBuilder.newBuilder().maximumSize(maxSize).
                expireAfterWrite(seconds, TimeUnit.SECONDS).removalListener(removalListener).build();
        this.map = cache.asMap();
    }

    /**
     * 根据内存计算出size
     * @param seconds  失效时间
     * @param perSize  一条数据大小 byte
     * @param dividerSize  内存因子
     */
    public LocalCache(int seconds, int perSize, int dividerSize) {
        long totalSize = Runtime.getRuntime().totalMemory() / perSize / dividerSize;
        this.cache = CacheBuilder.newBuilder().maximumSize(totalSize).
                expireAfterWrite(seconds, TimeUnit.SECONDS).removalListener(removalListener).build();
        this.map = cache.asMap();
    }

    private final RemovalListener<String, T> removalListener = notification -> {
        RemovalCause cause = notification.getCause();
        String key = notification.getKey();
        T h = notification.getValue();
        switch (cause) {
            case EXPIRED:
                logger.trace("Cache EXPIRED key {}. value {}", key,
                        h.toString());
                return;
            case SIZE:
            case COLLECTED:
                logger.error("Default DBStore Lost cause by {}. value {}", cause,
                        h.toString());
            default:
                return;
        }
    };

    public T get(String key) {
        return map.get(key);
    }

    /**
     * 获取key相关的value，如果没有从load中拿来缓存
     * @param key
     * @param load
     * @return
     */
    public T get(String key, Callable<T> load) {
        try {
            return cache.get(key, load);
        } catch (Exception e) {
            logger.debug("LocalCache中找不到key:" + key);
        }
        return null;
    }

    public T put(String key, T value) {
        return map.put(key, value);
    }

    public void remove(String key) {
        map.remove(key);
    }

    public T putIfAbsent(String key, T value) {
        return map.putIfAbsent(key, value);
    }

    public void cleanUp() {
        cache.cleanUp();
    }

    public Cache<String, T> getCache() {
        return cache;
    }

    public ConcurrentMap<String, T> getMap() {
        return map;
    }
}
