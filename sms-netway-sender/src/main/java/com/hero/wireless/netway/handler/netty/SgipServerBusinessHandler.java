package com.hero.wireless.netway.handler.netty;

import com.drondea.sms.channel.ChannelSession;
import com.drondea.sms.common.util.CommonUtil;
import com.drondea.sms.common.util.SystemClock;
import com.drondea.sms.message.sgip12.SgipSubmitRequestMessage;
import com.drondea.sms.message.sgip12.SgipSubmitResponseMessage;
import com.drondea.sms.session.sgip.SgipServerSession;
import com.drondea.sms.type.UserChannelConfig;
import com.drondea.wireless.util.CommonThreadPoolFactory;
import com.hero.wireless.enums.ProtocolType;
import com.hero.wireless.netty.sender.NettySender;
import com.hero.wireless.netway.config.InitSystemEnv;
import com.hero.wireless.netway.service.impl.AbstractTcpService;
import com.hero.wireless.netway.service.impl.SgipAPIServiceImpl;
import com.hero.wireless.web.entity.business.EnterpriseUser;
import com.hero.wireless.web.util.ApplicationContextUtil;
import com.hero.wireless.web.util.SMSUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;

import java.util.Date;

/**
 * @version V3.0.0
 * @description: sgip服务器端接收到消息业务处理
 * @author: liyuehai
 * @date: 2020年07月28日11:06
 **/
@ChannelHandler.Sharable
public class SgipServerBusinessHandler extends AbstractServerBusinessHandler {


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof SgipSubmitRequestMessage) {
            SgipSubmitRequestMessage requestMessage = (SgipSubmitRequestMessage) msg;
            ChannelSession channelSession = CommonUtil.getChannelSession(ctx.channel());
            SgipSubmitResponseMessage responseMessage = new SgipSubmitResponseMessage(requestMessage.getHeader());
            //用户提交时间
            long timestamp = SystemClock.now();
            //根据连接获取用户id
            SgipServerSession sgipServerSession = (SgipServerSession) channelSession;
            UserChannelConfig userChannelConfig = sgipServerSession.getUserChannelConfig();
            int userId = Integer.parseInt(userChannelConfig.getId());
            //响应提交
            NettySender.sendMessage(channelSession, responseMessage);

            EnterpriseUser enterpriseUser = InitSystemEnv.USER_INFOS.get(userId);
            CommonThreadPoolFactory.getInstance().getBizPoolExecutor().submit(() -> {
                String msgId = responseMessage.getHeader().getSequenceNumber().toString();
                String[] mobiles = requestMessage.getUserNumber();
                String subCode = requestMessage.getSpNumber();

                boolean isCacheAll = true;
                //2020年9月25日 改成本地缓存 刘彦宁
                if (requestMessage.isLongMsg()) {
                    isCacheAll = saveMsgId2Cache(requestMessage, msgId, userId, mobiles, subCode, timestamp);
                }

                //长信组装完毕
                if (isCacheAll) {
                    String charset = SMSUtil.getCharsetByByte(requestMessage.getMessageCoding().getValue());

                    doBizSubmit(requestMessage, enterpriseUser, mobiles, subCode, ctx, charset, msgId, ProtocolType.SGIP.toString(), new Date(timestamp));
                }
            });

        }
        super.channelRead(ctx, msg);
    }

    @Override
    protected AbstractTcpService getService() {
        return ApplicationContextUtil.getBean(SgipAPIServiceImpl.class);
    }
}
