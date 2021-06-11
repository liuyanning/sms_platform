package com.hero.wireless.sms.sender.service;

import com.hero.wireless.web.entity.send.Submit;
import com.hero.wireless.web.util.LocalCache;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @version V3.0.0
 * @description: redis批量保存
 * @author: 刘彦宁
 * @date: 2020年11月19日13:34
 **/
@Service
public class SubmitCacheService {

    /**
     * 本地缓存submit提高性能,1/3的内存
     */
    public static final LocalCache<Submit> SUBMIT_LOCAL_CACHE = new LocalCache<>(600, 1024, 3);

    public static final Queue<RedisEntity> INSERT_SET_QUEUE = new ConcurrentLinkedQueue<>();

    public static Queue<String> REMOVE_REDIS_KEY_QUEUE = new ConcurrentLinkedQueue<>();

    /**
     * 批量保存redis
     */
    public static void saveBatchReportRedis(String key, Submit submit) {
        //批量放入redis
        INSERT_SET_QUEUE.offer(new RedisEntity(key, submit));
    }

    public static void saveSubmit2Local(String key, Submit submited) {
        //提升性能本地缓存
        SUBMIT_LOCAL_CACHE.put(key, submited);
    }

    public static void removeSubmitInRedis(String key) {
        REMOVE_REDIS_KEY_QUEUE.offer(key);
    }

    public static boolean removeEntityInInsertQueue(String key) {
        return INSERT_SET_QUEUE.remove(new RedisEntity(key, null));
    }

    public static boolean removeKeyInDeleteQueue(String key) {
        return REMOVE_REDIS_KEY_QUEUE.remove(key);
    }

    public static class RedisEntity {
        private String key;
        private Submit submit;

        public RedisEntity(String key, Submit submit) {
            this.key = key;
            this.submit = submit;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public Submit getSubmit() {
            return submit;
        }

        public void setSubmit(Submit submit) {
            this.submit = submit;
        }

        @Override
        public boolean equals(Object obj) {
            return StringUtils.isEmpty(key) ? false :key.equals(((RedisEntity)obj).getKey());
        }

        @Override
        public int hashCode() {
            return (key == null) ? 0 : key.hashCode();
        }
    }
}
