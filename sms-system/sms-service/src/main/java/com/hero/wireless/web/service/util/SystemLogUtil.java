package com.hero.wireless.web.service.util;

import com.hero.wireless.enums.SessionStatus;
import com.hero.wireless.web.dao.business.ISystemLogDAO;
import com.hero.wireless.web.entity.business.Channel;
import com.hero.wireless.web.entity.business.EnterpriseUser;
import com.hero.wireless.web.entity.business.SystemLog;
import com.hero.wireless.web.entity.business.ext.EnterpriseUserExt;
import com.hero.wireless.web.util.SMSUtil;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Date;

/**
 * 系统日志工具类
 */
@Component
public class SystemLogUtil {

	private static SystemLogUtil systemLogUtil;

	@Resource(name = "ISystemLogDAO")
	private ISystemLogDAO<SystemLog> systemLogDAO;

	public static SystemLogUtil getInstance() {
		return systemLogUtil;
	}

	@PostConstruct
	public void init() {
		systemLogUtil = this;
	}

	public void saveLog(SystemLog systemLog) {
		systemLog.setCreate_Date(new Date());
		systemLogDAO.insert(systemLog);
	}

	/**
	 * 保存日志
	 * 
	 * @param user
	 * @param moduleName
	 * @param desc
	 * @param data
	 * @author volcano
	 * @date 2020年1月12日下午6:15:59
	 * @version V1.0
	 */
	public void saveLog(EnterpriseUser user, String moduleName, String desc, String data, String ip) {
		SystemLog systemLog = new SystemLog();
		systemLog.setUser_Id(user.getId());
		systemLog.setReal_Name(user.getReal_Name());
		systemLog.setUser_Name(user.getUser_Name());
		systemLog.setModule_Name(moduleName);
		systemLog.setOperate_Desc(desc);
		systemLog.setIp_Address(ip);
		systemLog.setSpecific_Desc(data);
		saveLog(systemLog);
	}


	public void saveLog(Channel channel, String status) {
		EnterpriseUser obj = new EnterpriseUser();
		obj.setId(0);
		obj.setReal_Name(channel.getName());
		obj.setUser_Name(channel.getNo());
		saveLog(obj, "system", "channel_login", status, SMSUtil.localIp());
	}

	/**
	 * 
	 * @param user
	 * @param status
	 * @author volcano
	 * @date 2020年1月12日下午8:38:32
	 * @version V1.0
	 */
	public void saveLog(EnterpriseUser user, SessionStatus status, String ip) {
		saveLog(user, "tcp", "enterprise_tcp_login", status.name(), ip);
	}

	public void saveLog(EnterpriseUserExt user, SessionStatus status, String ip) {
		saveLog(user, "tcp", "enterprise_tcp_login_sgip", status.name(), ip);
	}

	/**
	 * sgip特殊日志
	 * @param channel
	 * @param status
	 * @param ip
	 */
	public void saveLog(Channel channel, SessionStatus status, String ip) {
		EnterpriseUser obj = new EnterpriseUser();
		obj.setId(0);
		obj.setReal_Name(channel.getName());
		obj.setUser_Name(channel.getNo());
		saveLog(obj, "tcp", "channel_login", status.name(), ip);
	}

}
