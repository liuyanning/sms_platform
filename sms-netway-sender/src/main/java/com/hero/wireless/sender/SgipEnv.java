package com.hero.wireless.sender;

import com.drondea.sms.conf.ClientSocketConfig;
import com.drondea.sms.conf.sgip.SgipClientSocketConfig;
import com.drondea.sms.session.AbstractClientSessionManager;
import com.drondea.sms.session.sgip.SgipClientSessionManager;
import com.hero.wireless.sender.handler.SgipClientCustomHandler;
import com.hero.wireless.web.entity.business.Channel;
import com.hero.wireless.web.util.ChannelUtil;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.util.Map;

/**
 * 联通区别于 移动 电信 ，上行和状态报告需要本地服务
 *
 * @author Lenovo
 */
@Component("sgipEnv")
public class SgipEnv extends AbstractTcpEnv {

    @Override
    protected ClientSocketConfig createClientSocketConfig(Channel channel) {
        Map<String, ChannelUtil.OtherParameter> parameterMap = ChannelUtil.getParameter(channel);
        int windowSize = ChannelUtil.getParameterIntValue(parameterMap, "window_size", 32);
        //滑动窗口建议值为16
        SgipClientSocketConfig socketConfig = new SgipClientSocketConfig(channel.getNo(),
                10 * 1000, windowSize, channel.getIp(), channel.getPort());

        socketConfig.setCharset(Charset.forName("utf-8"));
        socketConfig.setGroupName("sgip");
        socketConfig.setUserName(channel.getAccount());
        socketConfig.setPassword(channel.getPassword());
        socketConfig.setLoginType((short) 1);
        socketConfig.setNodeId(Long.parseLong(ChannelUtil.getParameterValue(parameterMap, "node_id", "0")));
        //设置超时时间，没有消息读写就关闭连接
        socketConfig.setIdleTime(55);
        return socketConfig;
    }

    @Override
    protected AbstractClientSessionManager createSessionManager(Channel channel, ClientSocketConfig socketConfig) {
        SgipClientCustomHandler sgipCustomHandler = new SgipClientCustomHandler(channel);

        SgipClientSessionManager sessionManager = new SgipClientSessionManager(socketConfig, sgipCustomHandler);
        return sessionManager;
    }
}
