package com.hero.wireless.netway.service;

import com.drondea.sms.channel.ChannelSession;
import com.drondea.sms.message.IMessage;
import com.hero.wireless.web.entity.send.Inbox;
import com.hero.wireless.web.entity.send.Input;
import com.hero.wireless.web.entity.send.Report;

/**
 * 网关业务
 *
 * @author Lenovo
 */
public interface ITcpService {

    /**
     * 下游提交保存input
     * @param input
     * @return
     * @throws Exception
     */
    int saveInput(Input input) throws Exception;

    /**
     * 获取用户的回执推送信息
     * @param channelSession
     * @param entity
     * @return
     */
    IMessage getReportMessage(ChannelSession channelSession, Report entity);

    /**
     * 获取上行推送信息
     * @param channelSession
     * @param inbox
     * @return
     */
    IMessage getMOMessage(ChannelSession channelSession, Inbox inbox);
}
