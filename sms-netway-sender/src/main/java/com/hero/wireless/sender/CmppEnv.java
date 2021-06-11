package com.hero.wireless.sender;

import com.drondea.sms.conf.ClientSocketConfig;
import com.drondea.sms.conf.cmpp.CmppClientSocketConfig;
import com.drondea.sms.session.AbstractClientSessionManager;
import com.drondea.sms.session.cmpp.CmppClientSessionManager;
import com.drondea.sms.type.CmppConstants;
import com.hero.wireless.sender.handler.CmppClientCustomHandler;
import com.hero.wireless.web.entity.business.Channel;
import com.hero.wireless.web.util.ChannelUtil;
import com.hero.wireless.web.util.ChannelUtil.OtherParameter;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.util.Map;

/**
 * 移动网关系统环境
 *
 * @author Administrator
 */
@Component("cmppEnv")
public class CmppEnv extends AbstractTcpEnv {

    @Override
    protected ClientSocketConfig createClientSocketConfig(Channel channel) {
        Map<String, OtherParameter> parameterMap = ChannelUtil.getParameter(channel);
        //滑动窗口建议值为32
        int windowSize = ChannelUtil.getParameterIntValue(parameterMap, "window_size", 32);
        CmppClientSocketConfig socketConfig = new CmppClientSocketConfig(channel.getNo(),
                10 * 1000, windowSize, channel.getIp(), channel.getPort());

        socketConfig.setCharset(Charset.forName("utf-8"));
        socketConfig.setGroupName("cmpp");
        socketConfig.setUserName(channel.getAccount());
        socketConfig.setPassword(channel.getPassword());
        socketConfig.setSpCode(channel.getSp_Number());
        socketConfig.setServiceId(ChannelUtil.getParameterValue(parameterMap, "service_id", "0000000000"));
        // 协议版本
        socketConfig.setVersion(NumberUtils.toShort(channel.getVersion(), CmppConstants.VERSION_20));
        return socketConfig;
    }

    @Override
    protected AbstractClientSessionManager createSessionManager(Channel channel, ClientSocketConfig socketConfig) {
        CmppClientCustomHandler cmppCustomHandler = new CmppClientCustomHandler(channel);
        CmppClientSessionManager sessionManager = new CmppClientSessionManager(socketConfig, cmppCustomHandler);
        return sessionManager;
    }

}
