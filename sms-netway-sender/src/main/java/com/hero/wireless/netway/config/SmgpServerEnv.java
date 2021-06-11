package com.hero.wireless.netway.config;

import com.drondea.sms.conf.smgp.SmgpServerSocketConfig;
import com.drondea.sms.session.smgp.SmgpServerSessionManager;
import com.drondea.sms.type.SmgpConstants;
import com.drondea.wireless.util.SuperLogger;
import com.hero.wireless.enums.ProtocolType;
import com.hero.wireless.netway.TcpMessageProvider;
import com.hero.wireless.netway.handler.SmgpServerCustomHandler;
import com.hero.wireless.netway.service.TcpValidator;
import com.hero.wireless.web.config.DatabaseCache;
import io.netty.util.ResourceLeakDetector;
import io.netty.util.ResourceLeakDetector.Level;
import org.springframework.stereotype.Component;

/**
 * smgp网关
 *
 * @author zly
 */
@Component
public class SmgpServerEnv {

    public static final String SMGP_SERVER_ID = "smgp_server";

    public SmgpServerSessionManager start() {
        ResourceLeakDetector.setLevel(Level.ADVANCED);

        // 获取配置端口，为空获取SMGP默认端口
        int port = DatabaseCache.getIntValueBySortCodeAndCode("netway_smgp",
                "netway_smgp_port", 8891);
        SmgpServerSocketConfig socketConfig = new SmgpServerSocketConfig(SMGP_SERVER_ID, port);

        //服务器端默认版本号3.0
        socketConfig.setVersion(SmgpConstants.VERSION_30);

        SmgpServerCustomHandler customHandler = new SmgpServerCustomHandler();

        SmgpServerSessionManager sessionManager = new SmgpServerSessionManager(new TcpValidator(ProtocolType.SMGP), socketConfig, customHandler);
        sessionManager.setMessageProvider(new TcpMessageProvider(ProtocolType.SMGP));
        sessionManager.doOpen();

        SuperLogger.debug("启动SMGP网关，端口:" + port);
        return sessionManager;
    }
}