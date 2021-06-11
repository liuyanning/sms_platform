package com.hero.wireless.sender.handler.netty;

import com.drondea.sms.channel.ChannelSession;
import com.drondea.sms.common.util.CommonUtil;
import com.drondea.sms.message.sgip12.SgipDeliverRequestMessage;
import com.drondea.sms.message.sgip12.SgipDeliverResponseMessage;
import com.drondea.sms.message.sgip12.SgipReportRequestMessage;
import com.drondea.sms.message.sgip12.SgipReportResponseMessage;
import com.drondea.sms.session.AbstractServerSession;
import com.drondea.wireless.util.CommonThreadPoolFactory;
import com.drondea.wireless.util.SuperLogger;
import com.hero.wireless.netty.handler.ReportWrap;
import com.hero.wireless.netty.sender.NettySender;
import com.hero.wireless.netway.config.NetwayEnv;
import com.hero.wireless.sms.sender.statistics.SenderSpeedStatistics;
import com.hero.wireless.web.config.DatabaseCache;
import com.hero.wireless.web.entity.business.Channel;
import com.hero.wireless.web.entity.send.Inbox;
import com.hero.wireless.web.util.QueueUtil;
import com.hero.wireless.web.util.SMSUtil;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;

import java.util.Date;

/**
 * @version V3.0.0
 * @description: cmpp的客户端收到回执消息和MO业务处理
 * @author: 刘彦宁
 * @date: 2020年07月24日09:57
 **/
@ChannelHandler.Sharable
public class SgipServerBusinessHandler extends ChannelDuplexHandler {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof SgipDeliverRequestMessage) {
            SgipDeliverRequestMessage dp = (SgipDeliverRequestMessage) msg;
            ChannelSession channelSession = CommonUtil.getChannelSession(ctx.channel());
            CommonThreadPoolFactory.getInstance().getBizPoolExecutor().submit(() -> {
                if (dp.isMsgComplete()) {
                    try {
                        doMo(channelSession, dp);
                    } catch (Exception e) {
                        SuperLogger.error("deliver 出错了：" + e.getMessage());
                    }
                }
            });
            responseDeliver(channelSession, dp);
            return;
        }
        if (msg instanceof SgipReportRequestMessage) {
            SgipReportRequestMessage message = (SgipReportRequestMessage) msg;
            ChannelSession channelSession = CommonUtil.getChannelSession(ctx.channel());
            CommonThreadPoolFactory.getInstance().getBizPoolExecutor().submit(() -> {
                try {
                    doReport(channelSession, message);
                } catch (Exception e) {
                    SuperLogger.error("deliver 出错了：" + e.getMessage());
                }
            });
            responseReport(channelSession, message);
            return;
        }
        super.channelRead(ctx, msg);
    }

    /**
     * 响应回执
     *
     * @param channelSession
     * @param dp
     */
    protected void responseDeliver(ChannelSession channelSession, SgipDeliverRequestMessage dp) {
        SgipDeliverResponseMessage responseMessage = new SgipDeliverResponseMessage(dp.getHeader());
        responseMessage.setResult((short) 0);
        NettySender.sendMessage(channelSession, responseMessage);
    }

    protected void responseReport(ChannelSession channelSession, SgipReportRequestMessage dp) {
        SgipReportResponseMessage responseMessage = new SgipReportResponseMessage(dp.getHeader());
        responseMessage.setResult((short) 0);
        NettySender.sendMessage(channelSession, responseMessage);
    }

    // 处理MO
    private void doMo(ChannelSession channelSession, SgipDeliverRequestMessage dp) throws Exception {
        // 提取关键信息，其他数据json格式存储数据库
        SuperLogger.debug("*************SGIP接受的MO数据为：" + dp.toString());
        Inbox inbox = new Inbox();
        AbstractServerSession serverSession = (AbstractServerSession) channelSession;
        String id = serverSession.getUserChannelConfig().getId();
        inbox.setChannel_No(id);
        inbox.setPhone_No(dp.getUserNumber());
        inbox.setSP_Number(dp.getSpNumber());
        inbox.setContent(dp.getMsgContent());
        inbox.setCreate_Date(new Date());
        inbox.setCharset(SMSUtil.getCharsetByByte(dp.getMessageCoding().getValue()));//编码
        // 发送mq消息
        QueueUtil.saveMo(inbox);
    }

    /**
     * 长短信也会有多条状态报告
     *
     * @param dp
     */
    private void doReport(ChannelSession channelSession, SgipReportRequestMessage dp) {

        AbstractServerSession serverSession = (AbstractServerSession) channelSession;
        String id = serverSession.getUserChannelConfig().getId();
        Channel channel = DatabaseCache.getChannelCachedByNo(id);
        // 这里统计回执速度
        SenderSpeedStatistics.channelReportSpeedCount(NetwayEnv.LOCAL_IP, channel.getId());
        ReportWrap reportWrap = new ReportWrap(dp, channel);
        reportWrap.saveReport(3);
    }
}
