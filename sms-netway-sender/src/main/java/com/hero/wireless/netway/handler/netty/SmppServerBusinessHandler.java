package com.hero.wireless.netway.handler.netty;

import com.drondea.sms.channel.ChannelSession;
import com.drondea.sms.common.util.CommonUtil;
import com.drondea.sms.common.util.SystemClock;
import com.drondea.sms.message.smpp34.SmppSubmitSmRequestMessage;
import com.drondea.sms.message.smpp34.SmppSubmitSmResponseMessage;
import com.drondea.sms.session.smpp.SmppServerSession;
import com.drondea.sms.type.UserChannelConfig;
import com.drondea.wireless.util.CommonThreadPoolFactory;
import com.hero.wireless.enums.ProtocolType;
import com.hero.wireless.netty.sender.NettySender;
import com.hero.wireless.netway.config.InitSystemEnv;
import com.hero.wireless.netway.service.impl.AbstractTcpService;
import com.hero.wireless.netway.service.impl.SmppAPIServiceImpl;
import com.hero.wireless.web.entity.business.EnterpriseUser;
import com.hero.wireless.web.util.ApplicationContextUtil;
import com.hero.wireless.web.util.CodeUtil;
import com.hero.wireless.web.util.SMSUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;

import java.util.Date;

/**
 * @version V3.0.0
 * @description: smpp服务器端接收到消息业务处理
 * @author: 刘彦宁
 * @date: 2020年07月28日11:06
 **/
@ChannelHandler.Sharable
public class SmppServerBusinessHandler extends AbstractServerBusinessHandler {


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof SmppSubmitSmRequestMessage) {
            SmppSubmitSmRequestMessage requestMessage = (SmppSubmitSmRequestMessage) msg;
            ChannelSession channelSession = CommonUtil.getChannelSession(ctx.channel());
            SmppSubmitSmResponseMessage responseMessage = new SmppSubmitSmResponseMessage(requestMessage.getHeader());
            //用户提交时间
            long timestamp = SystemClock.now();
            //回复msgid,回执要使用
            responseMessage.setMessageId(CodeUtil.buildMsgNo());
            String mobile = requestMessage.getDestinationAddr();
            //根据连接信息获取用户id
            SmppServerSession smppServerSession = (SmppServerSession) channelSession;
            UserChannelConfig userChannelConfig = smppServerSession.getUserChannelConfig();
            int userId = Integer.parseInt(userChannelConfig.getId());
            String plusSign = "+";
            //增加判断，如果以+开头的电话号码直接失败
            if (mobile.startsWith(plusSign)) {
                responseMessage.getHeader().setCommandStatus(0x33);
                NettySender.sendMessage(channelSession, responseMessage);
                return;
            }
            NettySender.sendMessage(channelSession, responseMessage);

            EnterpriseUser enterpriseUser = InitSystemEnv.USER_INFOS.get(userId);
            CommonThreadPoolFactory.getInstance().getBizPoolExecutor().submit(() -> {
                String msgId = responseMessage.getMessageId();
                String subCode = requestMessage.getSourceAddr();
                boolean isCacheAll = true;
                //2020年9月25日 改成本地缓存 刘彦宁
                if (requestMessage.isLongMsg()) {
                    isCacheAll = saveMsgId2Cache(requestMessage, msgId, userId, new String[]{mobile}, subCode, timestamp);
                }

                //长信组装完毕
                if (isCacheAll) {
                    String dataCoding = SMSUtil.getSmppCharsetByByte((byte) requestMessage.getDataCoding());

                    doBizSubmit(requestMessage, enterpriseUser, new String[]{mobile}, subCode, ctx, dataCoding, msgId, ProtocolType.SMPP.toString(), new Date(timestamp));
                }
            });
        }
        super.channelRead(ctx, msg);
    }

    @Override
    protected AbstractTcpService getService() {
        return ApplicationContextUtil.getBean(SmppAPIServiceImpl.class);
    }
}
