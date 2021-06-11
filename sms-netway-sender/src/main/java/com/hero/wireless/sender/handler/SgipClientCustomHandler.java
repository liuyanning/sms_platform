package com.hero.wireless.sender.handler;

import com.drondea.sms.channel.ChannelSession;
import com.drondea.sms.message.IMessage;
import com.drondea.sms.message.sgip12.SgipBindResponseMessage;
import com.drondea.sms.type.DefaultEventGroupFactory;
import com.drondea.sms.type.ICustomHandler;
import com.drondea.sms.type.UserChannelConfig;
import com.hero.wireless.enums.SessionStatus;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.ChannelPromise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ScheduledExecutorService;


/**
 * @version V3.0.0
 * @description: sgip的定制处理器
 * @author: liyuehai
 * @date: 2020年06月23日17:35
 **/
public class SgipClientCustomHandler extends ICustomHandler {

    private static final Logger logger = LoggerFactory.getLogger(SgipClientCustomHandler.class);

    private com.hero.wireless.web.entity.business.Channel bizChannel;

    public SgipClientCustomHandler(com.hero.wireless.web.entity.business.Channel bizChannel) {
        this.bizChannel = bizChannel;
    }

    @Override
    public void fireUserLogin(Channel channel, ChannelSession channelSession) {
        logger.debug("sgip通道登录成功--开始发送短信");
    }

    @Override
    public void channelClosed(ChannelSession channelSession) {
        GlobalHandler.SGIP_CLIENT_BUSINESS_HANDLER.modifySessionStatus(SessionStatus.DISCONNECT, bizChannel.getNo());
    }

    @Override
    public void configPipelineAfterLogin(ChannelPipeline pipeline) {
        //sgip的回执和上行短信处理
        pipeline.addLast("sgipClientBusinessHandler", GlobalHandler.SGIP_CLIENT_BUSINESS_HANDLER);
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
        ScheduledExecutorService executor = DefaultEventGroupFactory.getInstance().getScheduleExecutor();
        executor.submit(() -> {
            SgipBindResponseMessage responseMessage = (SgipBindResponseMessage) message;
            GlobalHandler.SGIP_CLIENT_BUSINESS_HANDLER.modifySessionStatus(SessionStatus.valueOf((byte) responseMessage.getResult()),
                    bizChannel.getNo());
        });
        return true;
    }

}
