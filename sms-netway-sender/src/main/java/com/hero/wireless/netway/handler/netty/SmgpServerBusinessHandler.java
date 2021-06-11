package com.hero.wireless.netway.handler.netty;

import com.drondea.sms.channel.ChannelSession;
import com.drondea.sms.common.util.CommonUtil;
import com.drondea.sms.common.util.SmgpMsgId;
import com.drondea.sms.common.util.SystemClock;
import com.drondea.sms.message.smgp30.msg.SmgpSubmitRequestMessage;
import com.drondea.sms.message.smgp30.msg.SmgpSubmitResponseMessage;
import com.drondea.sms.session.smgp.SmgpServerSession;
import com.drondea.sms.type.UserChannelConfig;
import com.drondea.wireless.util.CommonThreadPoolFactory;
import com.hero.wireless.enums.ProtocolType;
import com.hero.wireless.netty.sender.NettySender;
import com.hero.wireless.netway.config.InitSystemEnv;
import com.hero.wireless.netway.service.impl.AbstractTcpService;
import com.hero.wireless.netway.service.impl.SmgpAPIServiceImpl;
import com.hero.wireless.web.entity.business.EnterpriseUser;
import com.hero.wireless.web.util.ApplicationContextUtil;
import com.hero.wireless.web.util.SMSUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;

import java.util.Date;

/**
 * @version V3.0
 * @description: smgp服务器端接收到消息业务处理
 * @author: ywj
 * @date: 2020年07月28日11:06
 **/
@ChannelHandler.Sharable
public class SmgpServerBusinessHandler extends AbstractServerBusinessHandler {


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof SmgpSubmitRequestMessage) {
            SmgpSubmitRequestMessage requestMessage = (SmgpSubmitRequestMessage) msg;
            ChannelSession channelSession = CommonUtil.getChannelSession(ctx.channel());
            SmgpSubmitResponseMessage responseMessage = new SmgpSubmitResponseMessage(requestMessage.getHeader());
            //全局唯一回执要用
            responseMessage.setSmgpMsgId(new SmgpMsgId());
            //用户提交时间
            long timestamp = SystemClock.now();
            //根据连接获取用户id
            SmgpServerSession smgpServerSession = (SmgpServerSession) channelSession;
            UserChannelConfig userChannelConfig = smgpServerSession.getUserChannelConfig();
            int userId = Integer.parseInt(userChannelConfig.getId());
            NettySender.sendMessage(channelSession, responseMessage);
            EnterpriseUser enterpriseUser = InitSystemEnv.USER_INFOS.get(userId);

            CommonThreadPoolFactory.getInstance().getBizPoolExecutor().submit(() -> {
                String msgId = responseMessage.getSmgpMsgId().toString();
                String[] mobiles = requestMessage.getDestTermIdArray();
                String subCode = requestMessage.getSrcTermId();
                boolean isCacheAll = true;
                //2020年9月25日 改成本地缓存 刘彦宁
                if (requestMessage.isLongMsg()) {
                    isCacheAll = saveMsgId2Cache(requestMessage, msgId, userId, mobiles, subCode, timestamp);
                }
                //长信组装完毕
                if (isCacheAll) {
                    String charset = SMSUtil.getCharsetByByte(requestMessage.getMsgFmt().getValue());

                    doBizSubmit(requestMessage, enterpriseUser, mobiles, subCode, ctx, charset, msgId, ProtocolType.SMGP.toString(), new Date(timestamp));

                }
            });

        }
        super.channelRead(ctx, msg);
    }

    @Override
    protected AbstractTcpService getService() {
        return ApplicationContextUtil.getBean(SmgpAPIServiceImpl.class);
    }

}
