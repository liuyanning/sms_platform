package com.hero.wireless.netway.config;

import com.drondea.sms.conf.cmpp.CmppServerSocketConfig;
import com.drondea.sms.session.cmpp.CmppServerSessionManager;
import com.drondea.sms.type.CmppConstants;
import com.drondea.wireless.util.SuperLogger;
import com.hero.wireless.enums.ProtocolType;
import com.hero.wireless.netway.TcpMessageProvider;
import com.hero.wireless.netway.handler.CmppServerCustomHandler;
import com.hero.wireless.netway.service.TcpValidator;
import com.hero.wireless.web.config.DatabaseCache;
import io.netty.util.ResourceLeakDetector;
import io.netty.util.ResourceLeakDetector.Level;
import org.springframework.stereotype.Component;

/**
 * cmpp网关
 *
 * @author zly
 */
@Component
public class CmppServerEnv {
    public static final String CMPP_SERVER_ID = "cmpp_server";

    public CmppServerSessionManager start() {
        ResourceLeakDetector.setLevel(Level.ADVANCED);

        // 获取配置端口，为空获取CMPP默认端口
        int port = DatabaseCache.getIntValueBySortCodeAndCode("cmpp_netway",
                "cmpp_server_port", 7891);
        CmppServerSocketConfig socketConfig = new CmppServerSocketConfig(CMPP_SERVER_ID, port);

        //服务器端默认版本号2.0
        socketConfig.setVersion(CmppConstants.VERSION_20);

        CmppServerCustomHandler customHandler = new CmppServerCustomHandler();

        CmppServerSessionManager sessionManager = new CmppServerSessionManager(new TcpValidator(ProtocolType.CMPP), socketConfig, customHandler);
        sessionManager.setMessageProvider(new TcpMessageProvider(ProtocolType.CMPP));
        sessionManager.doOpen();

        SuperLogger.debug("启动CMPP网关，端口:" + port);
        return sessionManager;
    }
}