package com.hero.wireless.sender;

import com.drondea.sms.conf.ClientSocketConfig;
import com.drondea.sms.conf.smpp.SmppClientSocketConfig;
import com.drondea.sms.session.AbstractClientSessionManager;
import com.drondea.sms.session.smpp.SmppClientSessionManager;
import com.drondea.sms.type.SmppConstants;
import com.hero.wireless.sender.handler.SmppClientCustomHandler;
import com.hero.wireless.web.entity.business.Channel;
import com.hero.wireless.web.util.ChannelUtil;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.util.Map;

/**
 * smpp环境
 *
 * @author gengjinbiao
 * @version V1.0
 * @date 2019年7月27日上午11:37:09
 */
@Component("smppEnv")
public class SmppEnv extends AbstractTcpEnv {

    @Override
    protected ClientSocketConfig createClientSocketConfig(Channel channel) {
        Map<String, ChannelUtil.OtherParameter> parameterMap = ChannelUtil.getParameter(channel);
        int windowSize = ChannelUtil.getParameterIntValue(parameterMap, "window_size", 32);
        SmppClientSocketConfig socketConfig = new SmppClientSocketConfig(channel.getNo(),
                10 * 1000, windowSize, channel.getIp(), channel.getPort());
        socketConfig.setCharset(Charset.forName("utf-8"));
        socketConfig.setGroupName("smpp");
        socketConfig.setSystemId(channel.getAccount());
        socketConfig.setPassword(channel.getPassword());
        socketConfig.setVersion(SmppConstants.VERSION_3_4);
        return socketConfig;
    }

    @Override
    protected AbstractClientSessionManager createSessionManager(Channel channel, ClientSocketConfig socketConfig) {
        SmppClientCustomHandler smppCustomHandler = new SmppClientCustomHandler(channel);
        SmppClientSessionManager sessionManager = new SmppClientSessionManager(socketConfig, smppCustomHandler);
        return sessionManager;
    }
}
