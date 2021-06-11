package com.hero.wireless.web.service;

import java.util.Map;

/**
 * @version V3.0.0
 * @description: 分拣业务
 * @author: 刘彦宁
 * @date: 2021年04月02日13:55
 **/
public interface ISortManage {

    /**
     * 单个分拣的处理速度
     * @param ip
     * @return
     */
    Map<String, Object> sorterSpeed(String ip);

    /**
     * 所有分拣服务器的总处理速度
     * @return
     */
    Map<String, Object> sorterTotalSpeed();

    /**
     * 分拣的峰值
     * @return
     * @param ip
     */
    Integer sorterMaxSpeed(String ip);
}
