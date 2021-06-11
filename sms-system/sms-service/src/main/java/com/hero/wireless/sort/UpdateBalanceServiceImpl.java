package com.hero.wireless.sort;

import com.google.common.util.concurrent.AtomicDouble;
import com.hero.wireless.web.config.DatabaseCache;
import com.hero.wireless.web.dao.business.IEnterpriseDAO;
import com.hero.wireless.web.dao.business.IEnterpriseUserDAO;
import com.hero.wireless.web.entity.business.*;
import com.hero.wireless.web.util.CacheUtil;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

/**
 * @version V3.0.0
 * @description: 更新余额实现
 * @author: 刘彦宁
 * @date: 2021年04月19日18:18
 **/
@Service
public class UpdateBalanceServiceImpl implements IUpdateBalanceService {

    /**
     * 单个用户分拣成功计费条数
     */
    public static ConcurrentHashMap<String, AtomicInteger> SINGLE_USER_SORT_COUNT_MAP = new ConcurrentHashMap<>();
    /**
     * 单个用户计费金额
     */
    public static ConcurrentHashMap<String, AtomicDouble> SINGLE_USER_SORT_FEE_MAP = new ConcurrentHashMap<>();

    @Resource
    private IEnterpriseUserDAO<EnterpriseUser> enterpriseUserDAO;
    @Resource
    private IEnterpriseDAO<Enterprise> enterpriseDAO;

    /**
     *
     * @author volcano
     * @date 2019年11月15日下午11:18:03
     * @version V1.0
     */
    public static class Charging {
        private double saleFee;
        private long sentCount;

        private Charging( double saleFee, long sentCount) {
            super();
            this.saleFee = saleFee;
            this.sentCount = sentCount;
        }

        public double getSaleFee() {
            return saleFee;
        }

        public void setSaleFee(double saleFee) {
            this.saleFee = saleFee;
        }

        public long getSentCount() {
            return sentCount;
        }

        public void setSentCount(long sentCount) {
            this.sentCount = sentCount;
        }
    }


    private int updateEnterpriseBalance(Enterprise enterprise, Charging record) {
        EnterpriseExample enterpriseExample = new EnterpriseExample();
        enterpriseExample.setDataLock(" FOR UPDATE ");
        enterpriseExample.createCriteria().andIdEqualTo(enterprise.getId());
        Enterprise latestEnterprise = this.enterpriseDAO.selectByExample(enterpriseExample).get(0);
        Enterprise updateEnterprise = new Enterprise();
        updateEnterprise.setId(latestEnterprise.getId());
        updateEnterprise
                .setUsed_Amount(latestEnterprise.getUsed_Amount().add(BigDecimal.valueOf(record.getSaleFee())));
        updateEnterprise.setSent_Count(
                (int) (ObjectUtils.defaultIfNull(latestEnterprise.getSent_Count(), 0) + record.getSentCount()));
        updateEnterprise.setAvailable_Amount(
                latestEnterprise.getAvailable_Amount().subtract(BigDecimal.valueOf(record.getSaleFee())));
        int rows = enterpriseDAO.updateByPrimaryKeySelective(updateEnterprise);
        // RedisKeyUtil.deleteEnterprise(latestEnterprise);
        return rows;
    }

    private int updateUserBalance(EnterpriseUser user, Charging record) {
        EnterpriseUserExample userExample = new EnterpriseUserExample();
        userExample.setDataLock(" FOR UPDATE");
        userExample.createCriteria().andIdEqualTo(user.getId());
        EnterpriseUser latestUserInfo = this.enterpriseUserDAO.selectByExample(userExample).get(0);
        EnterpriseUser updateUser = new EnterpriseUser();
        System.out.println("更新" + user.getId());
        updateUser.setId(user.getId());
        updateUser.setUsed_Amount(latestUserInfo.getUsed_Amount().add(BigDecimal.valueOf(record.getSaleFee())));
        updateUser.setSent_Count(
                (int) (ObjectUtils.defaultIfNull(latestUserInfo.getSent_Count(), 0) + record.getSentCount()));
        updateUser.setAvailable_Amount(
                latestUserInfo.getAvailable_Amount().subtract(BigDecimal.valueOf(record.getSaleFee())));
        // 更新缓存的
        int rows = enterpriseUserDAO.updateByPrimaryKeySelective(updateUser);
        // RedisKeyUtil.deleteUser(latestUserInfo);
        return rows;
    }


    @Override
    @Transactional(transactionManager = "txBusinessManager", rollbackFor = Exception.class)
    public int updateUserCharging(EnterpriseUser user) {
        return this.updateCharging(String.valueOf(user.getId()), (record) -> {
            //更新用户
            int updateUserRows = updateUserBalance(user, record);
            //更新企业
            Enterprise enterprise = DatabaseCache.getEnterpriseCachedByNo(user.getEnterprise_No());
            if (enterprise == null) {
                return updateUserRows;
            }
            int updateEnterpriseRows = updateEnterpriseBalance(enterprise, record);
            return updateUserRows + updateEnterpriseRows;
        });
    }

    protected int updateCharging(String identity, Function<Charging, Integer> updateFun) {
        AtomicDouble cachedUserFee = getCachedUserFee(identity);
        AtomicInteger cachedCounter = getCachedCounter(identity);

        if(cachedUserFee.doubleValue() == 0 && cachedCounter.intValue() == 0) {
            return 0;
        }
        Charging charging = new Charging(cachedUserFee.doubleValue(), cachedCounter.longValue());
        int rows = updateFun.apply(charging);
        cachedUserFee.addAndGet(-cachedUserFee.doubleValue());
        cachedCounter.addAndGet(-cachedCounter.intValue());
        return rows;
    }

    @Override
    public AtomicInteger getCachedCounter(String key) {
        return CacheUtil.getMapCachedObj(key, SINGLE_USER_SORT_COUNT_MAP, AtomicInteger.class);
    }

    @Override
    public AtomicDouble getCachedUserFee(String key) {
        return CacheUtil.getMapCachedObj(key, SINGLE_USER_SORT_FEE_MAP, AtomicDouble.class);
    }
}
