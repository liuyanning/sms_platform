package com.hero.wireless.netty.sender;

import com.drondea.sms.channel.ChannelSession;
import com.drondea.sms.message.IMessage;
import com.drondea.wireless.util.ServiceException;
import io.netty.util.concurrent.EventExecutor;

/**
 * @version V3.0.0
 * @description: netty 发送数据
 * @author: 刘彦宁
 * @date: 2020年07月25日11:55
 **/
public class NettySender {

    /**
     * 放到netty中发送，在用户线程中发送有问题
     * @param channelSession
     * @param iMessage
     */
    public static void sendMessage(ChannelSession channelSession, IMessage iMessage) throws ServiceException {
        if (!channelSession.getChannel().isActive()) {
            throw new ServiceException("通道已关闭不能发送了");
        }
        EventExecutor executor = channelSession.getChannel().pipeline().firstContext().executor();
        executor.submit(() -> {
            channelSession.sendMessage(iMessage);
        });
    }
}
