package com.hero.wireless.sender.handler;

import com.drondea.sms.channel.ChannelSession;
import com.drondea.sms.message.IMessage;
import com.drondea.sms.session.sgip.SgipServerSession;
import com.drondea.sms.type.DefaultEventGroupFactory;
import com.drondea.sms.type.ICustomHandler;
import com.drondea.sms.type.UserChannelConfig;
import com.hero.wireless.enums.SessionStatus;
import com.hero.wireless.sender.SgipServerEnv;
import com.hero.wireless.web.service.util.SystemLogUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.ChannelPromise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.concurrent.ScheduledExecutorService;


/**
 * @version V3.0.0
 * @description: sgip的定制处理器
 * @author: liyuehai
 * @date: 2020年06月23日17:35
 **/
public class SgipServerCustomHandler extends ICustomHandler {

    private static final Logger logger = LoggerFactory.getLogger(SgipServerCustomHandler.class);

    @Override
    public void fireUserLogin(Channel channel, ChannelSession channelSession) {

        logger.debug("上游sgip连接登录了");

        SgipServerSession sgipServerSession = (SgipServerSession) channelSession;
        UserChannelConfig userChannelConfig = sgipServerSession.getUserChannelConfig();

        com.hero.wireless.web.entity.business.Channel bischannel = SgipServerEnv.CHANNEL_INFOS.get(userChannelConfig.getId());
        String clientIp = ((InetSocketAddress) channel.remoteAddress())
                .getAddress().getHostAddress();
        //保存sgip登录成功日志
        ScheduledExecutorService executor = DefaultEventGroupFactory.getInstance().getScheduleExecutor();
        executor.submit(() -> {
            //保存日志
            SystemLogUtil.getInstance().saveLog(bischannel,
                    SessionStatus.LOGIN_SUCCESS, clientIp);
        });
    }

    @Override
    public void channelClosed(ChannelSession channelSession) {

    }

    @Override
    public void configPipelineAfterLogin(ChannelPipeline pipeline) {
        //sgip 特殊，deliver和report是用服务器端监听
        pipeline.addLast("SgipServerBusinessHandler", GlobalHandler.SGIP_SERVER_BUSINESS_HANDLER);
    }

    @Override
    public void responseMessageExpired(Integer sequenceId, IMessage request) {
        logger.debug("短信超时处理 {}", sequenceId);
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
        SgipServerSession sgipServerSession = (SgipServerSession) channelSession;
        UserChannelConfig userChannelConfig = sgipServerSession.getUserChannelConfig();

        com.hero.wireless.web.entity.business.Channel bischannel = SgipServerEnv.CHANNEL_INFOS.get(userChannelConfig.getId());
        //保存登录失败日志
        String clientIp = ((InetSocketAddress) channelSession.getChannel())
                .getAddress().getHostAddress();
        ScheduledExecutorService executor = DefaultEventGroupFactory.getInstance().getScheduleExecutor();
        executor.submit(() -> {
            //保存日志
            SystemLogUtil.getInstance().saveLog(bischannel,
                    SessionStatus.valueOf((byte) status), clientIp);
        });
    }

}
