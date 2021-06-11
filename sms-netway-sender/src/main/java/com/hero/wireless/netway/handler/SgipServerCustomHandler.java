package com.hero.wireless.netway.handler;

import com.hero.wireless.enums.ProtocolType;
import io.netty.channel.ChannelPipeline;


/**
 * @version V3.0.0
 * @description: sgip的定制处理器
 * @author: liyuehai
 * @date: 2020年06月23日17:35
 **/
public class SgipServerCustomHandler extends AbstractServerCustomHandler {

    @Override
    public void configPipelineAfterLogin(ChannelPipeline pipeline) {
        pipeline.addLast("SgipServerBusinessHandler", GlobalHandler.SGIP_SERVER_BUSINESS_HANDLER);
    }

    @Override
    protected ProtocolType getProtocolType() {
        return ProtocolType.SGIP;
    }
}
