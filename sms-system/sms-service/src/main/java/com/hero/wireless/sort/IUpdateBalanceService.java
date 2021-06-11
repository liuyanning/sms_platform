package com.hero.wireless.sort;

import com.google.common.util.concurrent.AtomicDouble;
import com.hero.wireless.web.entity.business.EnterpriseUser;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @version V3.0.0
 * @description: 更新余额业务
 * @author: 刘彦宁
 * @date: 2021年04月19日18:17
 **/
public interface IUpdateBalanceService {

    /**
     * 更新企业和用户的余额
     * @param user
     * @return
     */
    int updateUserCharging(EnterpriseUser user);

    /**
     * 获取单个用户分拣成功计费条数
     * @param key
     * @return
     */
    AtomicInteger getCachedCounter(String key);

    /**
     * 获取单个用户计费金额
     * @param key
     * @return
     */
    AtomicDouble getCachedUserFee(String key);
}
