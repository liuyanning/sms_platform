package com.hero.wireless.netway.handler;

import com.hero.wireless.enums.ProtocolType;
import io.netty.channel.ChannelPipeline;


/**
 * @version V3.0
 * @description: smgp的定制处理器
 * @author: ywj
 * @date: 2020年06月23日17:35
 **/
public class SmgpServerCustomHandler extends AbstractServerCustomHandler {

    @Override
    public void configPipelineAfterLogin(ChannelPipeline pipeline) {
        pipeline.addLast("SmgpServerBusinessHandler", GlobalHandler.SMGP_SERVER_BUSINESS_HANDLER);
    }

    @Override
    protected ProtocolType getProtocolType() {
        return ProtocolType.SMGP;
    }
}
