/**
 * Copyright (C), 2018-2020,drondea FileName: Handler Author:   20180512 Date:     2020/3/29 0:13 Description: 责任链模式
 * History:
 */
package com.hero.wireless.sender.filter;

import com.drondea.wireless.util.SuperLogger;
import com.hero.wireless.web.config.DatabaseCache;
import com.hero.wireless.web.entity.business.*;
import com.hero.wireless.web.entity.send.Submit;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Handler {

    protected Handler nextHandler = null;

    public abstract Submit doFilter(Channel channel, Submit submit);

    public void setNextHandler(Handler nextHandler) {
        this.nextHandler = nextHandler;
    }

    protected List<ProductChannelsDiversion> getDiversionStrategyRule(Channel channel, Submit submit,
                                                                      String diversionType) {

        //增加优化处理如果是单个号码（非http）的分拣已经拿到productChannelId，直接处理即可
        List<ProductChannelsDiversion> productChannelsDiversionList = new ArrayList();
        String[] phoneNos = submit.getPhone_No().split(",");
        Integer productChannelId = submit.getProduct_Channel_Id();
        if (phoneNos.length == 1 && productChannelId > 0) {
            // 通过通道产品id查询通道规则
            List<ProductChannelsDiversion> queryProductChannelsDiversionList = DatabaseCache.DIVERSION_MAP
                    .get(productChannelId + "," + diversionType);
            if (!ObjectUtils.isEmpty(queryProductChannelsDiversionList)) {
                productChannelsDiversionList.addAll(queryProductChannelsDiversionList);
            }
            return productChannelsDiversionList;
        }

        // 通过提交用户id查询产品
        EnterpriseUser user = DatabaseCache.getEnterpriseUserCachedById(submit.getEnterprise_User_Id());
        if (user == null) {
            return null;
        }
        Product product = DatabaseCache.getProductCachedByNo(user.getProduct_No());
        if (product == null) {
            return null;
        }

        Arrays.asList(phoneNos).forEach(phoneNo -> {
            SmsRoute route = DatabaseCache.getSmsRouteByNumber(phoneNo, submit.getCountry_Code());
            if (null == route) {
                return;
            }
            // 通过产品编号和通道编号获取产品通道
            ProductChannels productChannels = DatabaseCache.getProductCachedChannels(product.getNo()).stream()
                    .filter(item -> item.getChannel_No().equalsIgnoreCase(channel.getNo())
                            && StringUtils.isNotEmpty(route.getRoute_Name_Code())
                            && route.getRoute_Name_Code().equalsIgnoreCase(item.getOperator()))
                    .findFirst().orElse(null);
            if (productChannels == null) {
                SuperLogger.warn("没有找到产品通道" + channel.getNo() + "运营商" + route.getRoute_Name_Code());
                return;
            }
            // 通过通道产品id查询通道规则
            List<ProductChannelsDiversion> queryProductChannelsDiversionList = DatabaseCache.DIVERSION_MAP
                    .get(productChannels.getId() + "," + diversionType);
            if (ObjectUtils.isEmpty(queryProductChannelsDiversionList)) {
                return;
            }
            productChannelsDiversionList.addAll(queryProductChannelsDiversionList);

        });
        return productChannelsDiversionList;
    }
}
