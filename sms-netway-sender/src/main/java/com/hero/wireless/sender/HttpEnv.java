package com.hero.wireless.sender;

import com.hero.wireless.netway.config.NetwayEnv;
import com.hero.wireless.sender.handler.HttpClentSessionManager;
import com.hero.wireless.web.entity.business.Channel;
import org.springframework.stereotype.Component;

/**
 * 移动网关系统环境
 *
 * @author Administrator
 */
@Component("httpEnv")
public class HttpEnv {

    public HttpClentSessionManager createClient(Channel channel, NetwayEnv netwayEnv) {
        HttpClentSessionManager httpClentSessionManager = new HttpClentSessionManager(netwayEnv, channel);
        httpClentSessionManager.doOpen();
        return httpClentSessionManager;
    }

}
