package com.hero.wireless.netway.handler.netty;

import com.drondea.sms.channel.ChannelSession;
import com.drondea.sms.common.util.CommonUtil;
import com.drondea.sms.common.util.MsgId;
import com.drondea.sms.common.util.SystemClock;
import com.drondea.sms.message.cmpp.CmppSubmitRequestMessage;
import com.drondea.sms.message.cmpp.CmppSubmitResponseMessage;
import com.drondea.sms.session.cmpp.CmppServerSession;
import com.drondea.sms.type.CmppConstants;
import com.drondea.sms.type.UserChannelConfig;
import com.drondea.wireless.util.CommonThreadPoolFactory;
import com.hero.wireless.enums.ProtocolType;
import com.hero.wireless.netty.sender.NettySender;
import com.hero.wireless.netway.config.InitSystemEnv;
import com.hero.wireless.netway.service.impl.AbstractTcpService;
import com.hero.wireless.netway.service.impl.CmppAPIServiceImpl;
import com.hero.wireless.web.entity.business.EnterpriseUser;
import com.hero.wireless.web.util.ApplicationContextUtil;
import com.hero.wireless.web.util.SMSUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;

import java.util.Date;

/**
 * @version V3.0.0
 * @description: cmpp服务器端接收到消息业务处理
 * @author: 刘彦宁
 * @date: 2020年07月28日11:06
 **/
@ChannelHandler.Sharable
public class CmppServerBusinessHandler extends AbstractServerBusinessHandler {


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof CmppSubmitRequestMessage) {
            CmppSubmitRequestMessage requestMessage = (CmppSubmitRequestMessage) msg;
            ChannelSession channelSession = CommonUtil.getChannelSession(ctx.channel());
            CmppSubmitResponseMessage responseMessage = new CmppSubmitResponseMessage(requestMessage.getHeader());
            //msgid 响应，回执要用
            responseMessage.setMsgId(new MsgId());
            //用户提交时间
            long timestamp = SystemClock.now();

            //根据连接获取用户id
            CmppServerSession cmppServerSession = (CmppServerSession) channelSession;
            UserChannelConfig userChannelConfig = cmppServerSession.getUserChannelConfig();
            int userId = Integer.parseInt(userChannelConfig.getId());
            //响应
            NettySender.sendMessage(channelSession, responseMessage);

            EnterpriseUser enterpriseUser = InitSystemEnv.USER_INFOS.get(userId);

            short version = userChannelConfig.getVersion();
            String versionStr = getCmppVersion(version);

            CommonThreadPoolFactory.getInstance().getBizPoolExecutor().submit(() -> {
                String msgId = responseMessage.getMsgId().toString();
                String[] mobiles = requestMessage.getDestTerminalId();
                String subCode = requestMessage.getSrcId();

                boolean isCacheAll = true;
                if (requestMessage.isLongMsg()) {
                    isCacheAll = saveMsgId2Cache(requestMessage, msgId, userId, mobiles, subCode, timestamp);
                }

                if (isCacheAll) {
                    String charset = SMSUtil.getCharsetByByte(requestMessage.getMsgFmt().getValue());

                    doBizSubmit(requestMessage, enterpriseUser, mobiles, subCode, ctx, charset, msgId, versionStr, new Date(timestamp));
                }
            });
        }
        super.channelRead(ctx, msg);
    }

    private String getCmppVersion(short version){
        if (version < CmppConstants.VERSION_30) {
            return ProtocolType.CMPP2.toString();
        }
        return ProtocolType.CMPP3.toString();
    }

    @Override
    protected AbstractTcpService getService() {
        return ApplicationContextUtil.getBean(CmppAPIServiceImpl.class);
    }
}
