package com.hero.wireless.timer;

import com.hero.wireless.web.config.DatabaseCache;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author liuyanning
 */
@Service
public class CacheLoaderScheduler {

    @Resource(name = "databaseCache")
    private DatabaseCache databaseCache;

    @Scheduled(fixedDelay = 5 * 60 * 1000, initialDelay = 3 * 60 * 1000)
    public void update() {
        databaseCache.init();
        //加载敏感字
        DatabaseCache.refreshSensitiveWordLocalCache();
        //刷新导流策略
        DatabaseCache.refreshDiversionLocalCache();
        //加载号码路由
        DatabaseCache.refreshSmsRouteLocalCache();
        //加载白名单
        DatabaseCache.refreshWhiteListLocalCache();
        //加载黑名单
        DatabaseCache.refreshBlackListLocalCache();
    }

    @Scheduled(fixedDelay = 7 * 24 * 60 * 60 * 1000, initialDelay = 2 * 60 * 1000)
    public void updateMobileArea() {
        //加载手机号归属地
        DatabaseCache.refreshMobileAreaLocalCache();
    }
}
