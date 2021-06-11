package com.hero.wireless.netway.handler;

import com.hero.wireless.netway.handler.netty.CmppServerBusinessHandler;
import com.hero.wireless.netway.handler.netty.SgipServerBusinessHandler;
import com.hero.wireless.netway.handler.netty.SmgpServerBusinessHandler;
import com.hero.wireless.netway.handler.netty.SmppServerBusinessHandler;

/**
 * @version V3.0.0
 * @description: 全局单例的处理器
 * @author: 刘彦宁
 * @date: 2020年07月24日14:35
 **/
public class GlobalHandler {

    public final static CmppServerBusinessHandler CMPP_SERVER_BUSINESS_HANDLER = new CmppServerBusinessHandler();
    public final static SmppServerBusinessHandler SMPP_SERVER_BUSINESS_HANDLER = new SmppServerBusinessHandler();
    public final static SgipServerBusinessHandler SGIP_SERVER_BUSINESS_HANDLER = new SgipServerBusinessHandler();
    public final static SmgpServerBusinessHandler SMGP_SERVER_BUSINESS_HANDLER = new SmgpServerBusinessHandler();
}
