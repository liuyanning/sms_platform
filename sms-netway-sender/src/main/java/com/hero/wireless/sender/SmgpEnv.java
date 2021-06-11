package com.hero.wireless.sender;

import com.drondea.sms.conf.ClientSocketConfig;
import com.drondea.sms.conf.smgp.SmgpClientSocketConfig;
import com.drondea.sms.session.AbstractClientSessionManager;
import com.drondea.sms.session.smgp.SmgpClientSessionManager;
import com.drondea.sms.type.SmgpConstants;
import com.hero.wireless.sender.handler.SmgpClientCustomHandler;
import com.hero.wireless.web.entity.business.Channel;
import com.hero.wireless.web.util.ChannelUtil;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.util.Map;

@Component("smgpEnv")
public class SmgpEnv extends AbstractTcpEnv {

    @Override
    protected ClientSocketConfig createClientSocketConfig(Channel channel) {
        Map<String, ChannelUtil.OtherParameter> parameterMap = ChannelUtil.getParameter(channel);
        int windowSize = ChannelUtil.getParameterIntValue(parameterMap, "window_size", 32);
        //滑动窗口建议值为16
        SmgpClientSocketConfig socketConfig = new SmgpClientSocketConfig(channel.getNo(),
                10 * 1000, windowSize, channel.getIp(), channel.getPort());

        socketConfig.setCharset(Charset.forName("utf-8"));
        socketConfig.setGroupName("smgp");
        socketConfig.setUserName(channel.getAccount());
        socketConfig.setPassword(channel.getPassword());
        socketConfig.setSpCode(channel.getSp_Number());
        socketConfig.setServiceId(ChannelUtil.getParameterValue(parameterMap, "service_id", "0000000000"));
        socketConfig.setVersion(SmgpConstants.VERSION_30);
        return socketConfig;
    }

    @Override
    protected AbstractClientSessionManager createSessionManager(Channel channel, ClientSocketConfig socketConfig) {
        SmgpClientCustomHandler smgpCustomHandler = new SmgpClientCustomHandler(channel);

        SmgpClientSessionManager sessionManager = new SmgpClientSessionManager(socketConfig, smgpCustomHandler);
        return sessionManager;
    }
}
