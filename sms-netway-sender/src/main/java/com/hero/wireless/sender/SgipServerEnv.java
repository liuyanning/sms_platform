package com.hero.wireless.sender;

import com.drondea.sms.conf.sgip.SgipServerSocketConfig;
import com.drondea.sms.session.SessionManager;
import com.drondea.sms.session.sgip.SgipServerSessionManager;
import com.drondea.sms.type.UserChannelConfig;
import com.drondea.wireless.util.IpUtil;
import com.drondea.wireless.util.SuperLogger;
import com.hero.wireless.enums.ChannelStatus;
import com.hero.wireless.enums.ProtocolType;
import com.hero.wireless.netway.handler.SgipServerCustomHandler;
import com.hero.wireless.web.entity.business.Channel;
import com.hero.wireless.web.service.INetwayManage;
import com.hero.wireless.web.util.ChannelUtil;
import io.netty.util.ResourceLeakDetector;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author volcano
 * @version V1.0
 * @date 2019年10月19日下午2:29:00
 */
@Service
public class SgipServerEnv {
	//id前缀
	public static final String SERVER_CHILDID_PREX = "serverchild_";
	@Autowired
	private INetwayManage netwayManage;

	/**
	 * 用于保存用户相关资源和数据
	 */
	public final static Map<String, Channel> CHANNEL_INFOS = new ConcurrentHashMap<>();

	public final static Map<String, SgipServerSessionManager> SESSION_MANAGER_INFOS = new ConcurrentHashMap<>();


	/**
	 * sgip比较特殊的一点，客户端和服务端成对出现
	 *
	 * @param port
	 * @return
	 * @author volcano
	 * @date 2019年9月15日上午8:40:35
	 * @version V1.0
	 */
	public String serverId(int port) {
		return "sgip_server_" + port;
	}

	/**
	 *
	 * @param no
	 * @return
	 * @author volcano
	 * @date 2019年9月15日上午8:42:07
	 * @version V1.0
	 */
	public String serverChildId(String no) {
		return SERVER_CHILDID_PREX + no;
	}

	@Scheduled(cron = "0 0/1 * * * ?")
	public void add() {
		Channel condition = new Channel();
		condition.setProtocol_Type_Code(ProtocolType.SGIP.toString());
		//获取本地ip
		String localIp = IpUtil.getLocalIp();
		condition.setSender_Local_IP(localIp);
		condition.setStatus_Code(ChannelStatus.START.toString());
		List<Channel> channelList = netwayManage.queryChannelList(condition);
		channelList.forEach(channel -> {
			Map<String, ChannelUtil.OtherParameter> parameterMap = ChannelUtil.getParameter(channel);
			int localPort = ChannelUtil.getParameterIntValue(parameterMap, "local_port", 5671);

			//防止端口冲突
			if (SESSION_MANAGER_INFOS.get(serverId(localPort)) != null) {
				return;
			}
			SuperLogger.info("启动发送器ip：" + localIp + " 对应sgip服务器端");
			addServer(localPort);
		});
	}

	/**
	 *
	 * @param localPort
	 * @return
	 * @author volcano
	 * @date 2019年10月19日下午3:26:45
	 * @version V1.0
	 */
	private SessionManager addServer(int localPort) {
		ResourceLeakDetector.setLevel(ResourceLeakDetector.Level.ADVANCED);

		SgipServerSocketConfig socketConfig = new SgipServerSocketConfig(serverId(localPort), localPort);

		SgipServerCustomHandler customHandler = new SgipServerCustomHandler();

		SgipServerSessionManager sessionManager = new SgipServerSessionManager(userName -> {

			Channel condition = new Channel();
			condition.setProtocol_Type_Code(ProtocolType.SGIP.toString());
			condition.setAccount(userName);
			List<Channel> channelList = netwayManage.queryChannelList(condition);
			if (ObjectUtils.isEmpty(channelList)) {
				return null;
			}
			if (channelList.size() > 1) {
				SuperLogger.warn("sgip 账号重复,请检查数据=====>" + userName);
			}
			Channel channel = channelList.get(0);
//			Map<String, ChannelUtil.OtherParameter> parameterMap = ChannelUtil.getParameter(channel);
			UserChannelConfig userChannelConfig = new UserChannelConfig();
			userChannelConfig.setUserName(userName);
			userChannelConfig.setPassword(channel.getPassword());
//			userChannelConfig.setQpsLimit(ObjectUtils.defaultIfNull(channel.getSubmit_Speed(), 200));
			String channelNo = channel.getNo();
			//id存的是通道的编号
			userChannelConfig.setId(channelNo);

			//sgip暂时不要设置白名单
			//userChannelConfig.setValidIp(userInfo.getApi_Ip());
			//连接数限制和客户端一样
			userChannelConfig.setChannelLimit(ObjectUtils.defaultIfNull(channel.getMax_Concurrent_Total(), 3).shortValue());

			//缓存通道信息
			addChannelInfo(channelNo, channel);

			return userChannelConfig;
		}, socketConfig, customHandler);
		sessionManager.doOpen();
		addSessionManager(serverId(localPort), sessionManager);
		SuperLogger.debug("启动SGIP服务端，端口:" + localPort);
		return sessionManager;
	}

	public static void addChannelInfo(String channelNo, Channel channel) {
		CHANNEL_INFOS.put(channelNo, channel);
	}

	public void addSessionManager(String serverId, SgipServerSessionManager sessionManager) {
		SESSION_MANAGER_INFOS.put(serverId, sessionManager);
	}
}
