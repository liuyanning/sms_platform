package com.hero.wireless.timer;

import com.drondea.wireless.util.SuperLogger;
import com.hero.wireless.enums.ContentAuditStatus;
import com.hero.wireless.enums.Priority;
import com.hero.wireless.web.config.DatabaseCache;
import com.hero.wireless.web.dao.send.IInputDAO;
import com.hero.wireless.web.entity.business.EnterpriseUser;
import com.hero.wireless.web.entity.send.Input;
import com.hero.wireless.web.entity.send.InputExample;
import com.hero.wireless.web.util.QueueUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * 短信定时
 * 
 * @author Lenovo
 *
 */
@Component
public class ContentTimer {
	@Autowired
	private IInputDAO<Input> inputDAO;

	@Scheduled(fixedDelay = 5 * 1000, initialDelay = 5 * 1000)
	public void initTimer() {
		SuperLogger.debug("扫描定时短信");
		InputExample example = new InputExample();
		example.createCriteria().andSend_TimeLessThan(new Date()).
				andAudit_Status_CodeEqualTo(ContentAuditStatus.PASSED.toString());
		List<Input> list = inputDAO.selectByExample(example);
		list.forEach(item -> {
			doTask(item);
		});
	}

	/**
	 * 执行定时任务
	 *
	 * @param input
	 */
	public void doTask(Input input) {
		inputDAO.deleteByPrimaryKey(input.getId());
		SuperLogger.debug("通知分拣=========================>" + input.getMsg_Batch_No());
		EnterpriseUser enterpriseUser = DatabaseCache.getEnterpriseUserCachedById(input.getEnterprise_User_Id());
		if (null == enterpriseUser){
			// 正常的不会走到这里的，如果走到这里说明不正常
			SuperLogger.warn("不正常，定时短信中没有企业用户信息,没有用户信息默认优先级低");
			enterpriseUser = new EnterpriseUser();
			enterpriseUser.setPriority_Level(Priority.LOW_LEVEL.value());
		}
		QueueUtil.notifySorter(input, enterpriseUser.getPriority_Level());
	}
}
