package com.hero.wireless.sender.handler;

import com.drondea.sms.channel.ChannelSession;
import com.drondea.sms.channel.IChannelSessionCounters;
import com.drondea.sms.connector.IConnector;
import com.drondea.sms.session.AbstractClientSessionManager;
import com.hero.wireless.netway.config.NetwayEnv;
import com.hero.wireless.sender.MessageSender;
import com.hero.wireless.web.entity.business.Channel;
import io.netty.channel.ChannelHandlerContext;

/**
 * @version V3.0.0
 * @description: http提交器
 * @author: 刘彦宁
 * @date: 2020年09月05日10:03
 **/
public class HttpClentSessionManager extends AbstractClientSessionManager {

    private Channel bizChannel;
    public volatile boolean status;

    public HttpClentSessionManager(NetwayEnv netwayEnv, Channel bizChannel) {
        super(null, null);
        this.bizChannel = bizChannel;
    }

    @Override
    protected IConnector getConnector() {
        return null;
    }

    @Override
    public ChannelSession createSession(ChannelHandlerContext channelHandlerContext) {
        return null;
    }

    @Override
    public void doOpen() {
        status = true;
        //从队列获取消息然后发送
        MessageSender.pullHttpMessage(bizChannel, 0, this);
    }


    @Override
    public void doClose() {
        status = false;
    }


    @Override
    public IChannelSessionCounters createSessionCounters() {
        return null;
    }
}
