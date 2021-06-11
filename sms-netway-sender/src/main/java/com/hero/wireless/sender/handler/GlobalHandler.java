package com.hero.wireless.sender.handler;

import com.hero.wireless.sender.handler.netty.*;

/**
 * @version V3.0.0
 * @description: 全局单例的处理器
 * @author: 刘彦宁
 * @date: 2020年07月24日14:35
 **/
public class GlobalHandler {

    public final static CmppClientBusinessHandler CMPP_CLIENT_BUSINESS_HANDLER = new CmppClientBusinessHandler();
    public final static SgipServerBusinessHandler SGIP_SERVER_BUSINESS_HANDLER = new SgipServerBusinessHandler();
    public final static SgipClientBusinessHandler SGIP_CLIENT_BUSINESS_HANDLER = new SgipClientBusinessHandler();
    public final static SmgpClientBusinessHandler SMGP_CLIENT_BUSINESS_HANDLER = new SmgpClientBusinessHandler();
    public final static SmppClientBusinessHandler SMPP_CLIENT_BUSINESS_HANDLER = new SmppClientBusinessHandler();
}
