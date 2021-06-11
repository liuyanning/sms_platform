package com.hero.wireless.netway.handler;

import com.drondea.sms.channel.ChannelSession;
import com.drondea.sms.message.IMessage;
import com.drondea.sms.message.sgip12.SgipBindResponseMessage;
import com.drondea.sms.type.DefaultEventGroupFactory;
import com.drondea.sms.type.ICustomHandler;
import com.drondea.sms.type.UserChannelConfig;
import com.hero.wireless.enums.SessionStatus;
import com.hero.wireless.web.entity.business.ext.EnterpriseUserExt;
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
public class SgipClientCustomHandler extends ICustomHandler {

    private static final Logger logger = LoggerFactory.getLogger(SgipClientCustomHandler.class);

    private EnterpriseUserExt enterpriseUser;

    public SgipClientCustomHandler(EnterpriseUserExt enterpriseUser) {
        this.enterpriseUser = enterpriseUser;
    }

    @Override
    public void fireUserLogin(Channel channel, ChannelSession channelSession) {

        //注册session可写状态监听器
        channelSession.setSessionEventHandler((writable) -> {
            logger.debug("可写状态发生改变：{}", writable);
        });

        logger.debug("用户登录成功--开始发送短信");

        String remotIp = ((InetSocketAddress) channel.remoteAddress())
                .getAddress().getHostAddress();

        ScheduledExecutorService executor = DefaultEventGroupFactory.getInstance().getScheduleExecutor();
        executor.submit(() -> {
            //保存日志
            SystemLogUtil.getInstance().saveLog(enterpriseUser,
                    SessionStatus.LOGIN_SUCCESS, remotIp);
        });
    }

    @Override
    public void channelClosed(ChannelSession channelSession) {

    }

    @Override
    public void configPipelineAfterLogin(ChannelPipeline pipeline) {
        //sgip的回执和上行短信处理
        //pipeline.addLast("sgipClientBusinessHandler", GlobalHandler.SGIP_CLIENT_BUSINESS_HANDLER);
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
        SgipBindResponseMessage responseMessage = (SgipBindResponseMessage) message;
        ScheduledExecutorService executor = DefaultEventGroupFactory.getInstance().getScheduleExecutor();
        String remotIp = ((InetSocketAddress) channel.remoteAddress())
                .getAddress().getHostAddress();
        executor.submit(() -> {
            //保存日志
            SystemLogUtil.getInstance().saveLog(enterpriseUser,
                    SessionStatus.valueOf((byte) responseMessage.getResult()), remotIp);
        });
        return true;
    }

}
