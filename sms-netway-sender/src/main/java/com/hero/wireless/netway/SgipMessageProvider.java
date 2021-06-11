package com.hero.wireless.netway;

import com.drondea.sms.channel.ChannelSession;
import com.hero.wireless.enums.ProtocolType;
import com.hero.wireless.web.entity.business.EnterpriseUser;

/**
 * @version V3.0.0
 * @description: sgip的拉取消息提供者
 * @author: 刘彦宁
 * @date: 2020年12月21日18:24
 **/
public class SgipMessageProvider extends TcpMessageProvider {

    private EnterpriseUser enterpriseUser;

    public SgipMessageProvider(EnterpriseUser enterpriseUser) {
        super(ProtocolType.SGIP);
        this.enterpriseUser = enterpriseUser;
    }

    @Override
    public Integer getUserId(ChannelSession channelSession) {
        return enterpriseUser.getId();
    }

}
