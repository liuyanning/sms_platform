package com.hero.wireless.sender.handler.netty;

import com.drondea.sms.channel.ChannelSession;
import com.drondea.sms.common.util.CommonUtil;
import com.drondea.sms.session.AbstractClientSessionManager;
import com.drondea.sms.type.DefaultEventGroupFactory;
import com.hero.wireless.enums.SessionStatus;
import com.hero.wireless.netway.config.NetwayEnv;
import com.hero.wireless.web.config.DatabaseCache;
import com.hero.wireless.web.dao.business.IChannelDAO;
import com.hero.wireless.web.entity.business.Channel;
import com.hero.wireless.web.service.util.SystemLogUtil;
import com.hero.wireless.web.util.ApplicationContextUtil;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;

import java.util.Date;
import java.util.concurrent.ScheduledExecutorService;

/**
 * @version V3.0.0
 * @description: 客户端业务处理
 * @author: 刘彦宁
 * @date: 2020年07月24日09:26
 **/
public class AbstractClientBusinessHandler extends ChannelDuplexHandler {

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt == (Integer) ChannelSession.STATE_LOGIN_SUCCESS) {
            ChannelSession channelSession = CommonUtil.getChannelSession(ctx.channel());
            String channelId = channelSession.getConfiguration().getId();
            ScheduledExecutorService executor = DefaultEventGroupFactory.getInstance().getScheduleExecutor();
            executor.submit(() -> {
                modifySessionStatus(SessionStatus.LOGIN_COMPLETE, channelId);
            });
        }
        super.userEventTriggered(ctx, evt);
    }

    /**
     * @param status
     * @author volcano
     * @date 2019年9月28日上午2:08:00
     * @version V1.0
     */
    public void modifySessionStatus(SessionStatus status, String no) {
        modifySessionStatus(status.name(), no);
    }

    public void modifySessionStatus(String status, String no) {
        if (status.equalsIgnoreCase(SessionStatus.MAX_CHANNELS_ERROR.toString())
                || status.equalsIgnoreCase(SessionStatus.LOGIN_SUCCESS.toString())) {
            return;
        }
        AbstractClientSessionManager clientManager = NetwayEnv.getClientManager(no);
        int connectedNum = 0;
        if (clientManager != null) {
            connectedNum = clientManager.getSessionSize();
        }
        IChannelDAO<?> dao = ApplicationContextUtil.getBean("channelExtDAO");
        Channel updateSessionStatus = new Channel();
        Channel channel = DatabaseCache.getChannelCachedByNo(no);
        updateSessionStatus.setId(channel.getId());
        updateSessionStatus.setSession_Status(String.format("(%1$s)%2$s", connectedNum, status));
        updateSessionStatus.setSession_Status_Date(new Date());
        dao.updateByPrimaryKeySelective(updateSessionStatus);
        // 通道登录日志保存
        SystemLogUtil.getInstance().saveLog(channel, status);
    }

}
