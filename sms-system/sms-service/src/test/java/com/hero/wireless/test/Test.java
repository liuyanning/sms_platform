package com.hero.wireless.test;

import org.apache.commons.lang3.StringUtils;

/**
 * @version V3.0.0
 * @description: 测试
 * @author: 刘彦宁
 * @date: 2020年07月25日17:14
 **/
public class Test {
    public static void main(String[] args) {
        String test = "【翔龙福满楼(齐鲁园店）】您的18853922850会员卡于2020年07月25日 15时33分消费0元,卡余额81.00元。翔龙福满楼（齐鲁园店）。(翔龙福满楼)".replaceFirst("【翔龙福满楼\\(齐鲁园店）】", "");
        test = StringUtils.replace("【翔龙福满楼(齐鲁园店）】您的", "【翔龙福满楼(齐鲁园店）】", "");
        System.out.println(test);
    }
}
