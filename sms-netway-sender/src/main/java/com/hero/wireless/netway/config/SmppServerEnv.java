package com.hero.wireless.netway.config;

import com.drondea.sms.conf.smpp.SmppServerSocketConfig;
import com.drondea.sms.session.smpp.SmppServerSessionManager;
import com.drondea.wireless.util.SuperLogger;
import com.hero.wireless.enums.ProtocolType;
import com.hero.wireless.netway.TcpMessageProvider;
import com.hero.wireless.netway.handler.SmppServerCustomHandler;
import com.hero.wireless.netway.service.TcpValidator;
import com.hero.wireless.web.config.DatabaseCache;
import org.springframework.stereotype.Component;

/**
 * @author volcano
 * @version V1.0
 * @date 2019年11月28日下午9:46:21
 */
@Component
public class SmppServerEnv {
    public static final String SMPP_SERVER_ID = "smpp_server";

    public SmppServerSessionManager start() {

        // 获取配置端口，为空获取SMPP默认端口
        int port = DatabaseCache.getIntValueBySortCodeAndCode("smpp_netway",
                "smpp_server_port", 7888);
        SmppServerSocketConfig socketConfig = new SmppServerSocketConfig(SMPP_SERVER_ID, port);

        SmppServerCustomHandler customHandler = new SmppServerCustomHandler();

        SmppServerSessionManager sessionManager = new SmppServerSessionManager(new TcpValidator(ProtocolType.SMPP), socketConfig, customHandler);
        sessionManager.setMessageProvider(new TcpMessageProvider(ProtocolType.SMPP));
        sessionManager.doOpen();

        SuperLogger.debug("启动SMPP网关，端口:" + port);
        return sessionManager;
    }
}
