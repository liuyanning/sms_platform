package com.hero.wireless.web.config;

import com.hero.wireless.sort.IBalanceAlarmService;
import com.hero.wireless.sort.IUpdateBalanceService;
import com.hero.wireless.web.entity.business.ext.EnterpriseUserExt;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 
 * 
 * @author volcano
 * @date 2019年11月16日下午7:32:48
 * @version V1.0
 */
@Service
public class UpdateBalanceTimer {

	private Lock chargeLock = new ReentrantLock();
	@Resource
	private IUpdateBalanceService updateBalanceService;
	@Resource
	private IBalanceAlarmService balanceAlarmService;

	@Scheduled(fixedDelay = 10 * 1000, initialDelay = 10 * 1000)
	public void update() {

		if(!chargeLock.tryLock()) {
			return;
		}
		try {
			DatabaseCache.getEnterpriseUserList(new EnterpriseUserExt()).forEach(item -> {
				updateBalanceService.updateUserCharging(item);
			});

			balanceAlarmService.enterpriseUserOrAgentBalanceAlarm();
		} finally {
			//释放锁
			chargeLock.unlock();
		}

	}

	@PreDestroy
	public void release() {
		//停机更新余额
		update();
	}
}
