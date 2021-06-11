package com.hero.wireless.netway.config;

import com.hero.wireless.enums.ProtocolType;

/**
 * @version V3.0.0
 * @description: 服务器端用户key生成
 * @author: 刘彦宁
 * @date: 2020年07月27日15:52
 **/
public class ServerUserProperties {

    public static String getUserMOKey(int userId, ProtocolType protocolType) {
        return userId + "_" + protocolType + "_mo";
    }

    public static String getUserReportKey(int userId, ProtocolType protocolType) {
        return userId + "_" +protocolType+ "_report";
    }
}
