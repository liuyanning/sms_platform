package com.hero.wireless.netway.handler;

import com.drondea.sms.channel.ChannelSession;
import com.drondea.sms.message.IMessage;
import com.drondea.sms.session.AbstractServerSession;
import com.drondea.sms.session.AbstractServerSessionManager;
import com.drondea.sms.type.DefaultEventGroupFactory;
import com.drondea.sms.type.ICustomHandler;
import com.drondea.sms.type.UserChannelConfig;
import com.drondea.wireless.config.Constant;
import com.hero.wireless.enums.ProtocolType;
import com.hero.wireless.enums.SessionStatus;
import com.hero.wireless.netway.config.InitSystemEnv;
import com.hero.wireless.web.entity.business.EnterpriseUser;
import com.hero.wireless.web.service.util.SystemLogUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.stream.Collectors;

/**
 * @version V3.0.0
 * @description: netty服务端抽象处理
 * @author: 刘彦宁
 * @date: 2021年03月08日14:27
 **/
public abstract class AbstractServerCustomHandler extends ICustomHandler {

    private static final Logger logger = LoggerFactory.getLogger(AbstractServerCustomHandler.class);

    /**
     * 获取协议
     * @return
     */
    protected abstract ProtocolType getProtocolType();

    @Override
    public void fireUserLogin(Channel channel, ChannelSession channelSession) {

        AbstractServerSession serverSession = (AbstractServerSession) channelSession;
        UserChannelConfig userChannelConfig = serverSession.getUserChannelConfig();
        int id = Integer.parseInt(userChannelConfig.getId());
        logger.debug("用户{}登录了", id);
        EnterpriseUser user = InitSystemEnv.USER_INFOS.get(id);
        String clientIp = ((InetSocketAddress) channel.remoteAddress())
                .getAddress().getHostAddress();

        AbstractServerSessionManager sessionManager = (AbstractServerSessionManager) channelSession.getSessionManager();

        ScheduledExecutorService executor = DefaultEventGroupFactory.getInstance().getScheduleExecutor();
        executor.submit(() -> {
            //保存日志
            SystemLogUtil.getInstance().saveLog(user,
                    SessionStatus.LOGIN_SUCCESS, clientIp);
            countConnection(sessionManager, userChannelConfig.getUserName());
        });
    }

    private String getUserKey(String tcpUserName, ProtocolType protocolType) {
        return Constant.REDIS_ONLINE_USER_DIR_NAME + ":" + tcpUserName + "_" + protocolType.toString() + "_" + InitSystemEnv.LOCAL_IP;
    }

    private void countConnection(AbstractServerSessionManager abstractServerSessionManager, String tcpUserName) {
        if (abstractServerSessionManager == null) {
            return;
        }
        List<ChannelSession> userSessions = abstractServerSessionManager.getUserSessions(tcpUserName);
        if (userSessions == null) {
            return;
        }

        Map<String, Long> userIpCounting = userSessions.stream().collect(Collectors.groupingBy(userSession -> {
            Channel channel = userSession.getChannel();
            if (channel == null) {
                return "unknown";
            }
            return ((InetSocketAddress) channel.remoteAddress())
                    .getAddress().getHostAddress();
        }, Collectors.counting()));

    }

    @Override
    public void channelClosed(ChannelSession channelSession) {
        AbstractServerSession serverSession = (AbstractServerSession) channelSession;
        UserChannelConfig userChannelConfig = serverSession.getUserChannelConfig();

        AbstractServerSessionManager sessionManager = (AbstractServerSessionManager) channelSession.getSessionManager();

        ScheduledExecutorService executor = DefaultEventGroupFactory.getInstance().getScheduleExecutor();
        executor.submit(() -> {
            //更新用户登录信息
            countConnection(sessionManager, userChannelConfig.getUserName());
        });
    }

    @Override
    public void responseMessageExpired(Integer sequenceId, IMessage request) {
        logger.error("短信超时处理 {}", sequenceId);
    }

    @Override
    public void slidingWindowException(ChannelSession session, ChannelHandlerContext ctx, IMessage message, ChannelPromise promise, Exception exception) {
        logger.error("slidingWindowException", exception);
    }

    @Override
    public boolean customLoginValidate(IMessage message, UserChannelConfig channelConfig, Channel channel) {
        return true;
    }

    @Override
    public void failedLogin(ChannelSession channelSession, IMessage iMessage, long status) {
        AbstractServerSession serverSession = (AbstractServerSession) channelSession;
        UserChannelConfig userChannelConfig = serverSession.getUserChannelConfig();
        int id = Integer.parseInt(userChannelConfig.getId());

        EnterpriseUser user = InitSystemEnv.USER_INFOS.get(id);
        String clientIp = ((InetSocketAddress) channelSession.getChannel())
                .getAddress().getHostAddress();
        ScheduledExecutorService executor = DefaultEventGroupFactory.getInstance().getScheduleExecutor();
        executor.submit(() -> {
            //保存日志
            SystemLogUtil.getInstance().saveLog(user,
                    SessionStatus.valueOf((byte) status), clientIp);
        });
    }
}
