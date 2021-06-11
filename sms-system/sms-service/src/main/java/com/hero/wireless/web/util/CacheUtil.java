package com.hero.wireless.web.util;

import com.drondea.wireless.util.SuperLogger;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;

/**
 * @version V3.0.0
 * @description: 缓存工具类
 * @author: 刘彦宁
 * @date: 2021年03月18日11:17
 **/
public class CacheUtil {

    public static <T> T getMapCachedObj(String key, ConcurrentMap<String, T> map, Class<T> classZ) {
        T object = map.get(key);
        if (object != null) {
            return object;
        }
        try {
            object = classZ.newInstance();
        } catch (Exception e) {
            SuperLogger.error("init object failed" + e.getStackTrace());
        }
        T preObject = map.putIfAbsent(key, object);
        if (preObject != null) {
            object = preObject;
        }
        return object;
    }

    public static <T> Queue<T> getMapCachedQueue(String key, ConcurrentMap<String, Queue<T>> map) {
        Queue<T> object = map.get(key);
        if (object != null) {
            return object;
        }
        try {
            object = new ConcurrentLinkedQueue();
        } catch (Exception e) {
            SuperLogger.error("init object failed" + e.getStackTrace());
        }
        Queue<T> preObject = map.putIfAbsent(key, object);
        if (preObject != null) {
            object = preObject;
        }
        return object;
    }

    public static <T> List<T> getMapCachedList(String key, ConcurrentMap<String, List<T>> map) {
        List<T> object = map.get(key);
        if (object != null) {
            return object;
        }
        try {
            object = new ArrayList<>();
        } catch (Exception e) {
            SuperLogger.error("init object failed" + e.getStackTrace());
        }
        List<T> preObject = map.putIfAbsent(key, object);
        if (preObject != null) {
            object = preObject;
        }
        return object;
    }
}
