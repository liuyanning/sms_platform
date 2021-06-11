/**
 * 防屏码 目前应用于国际短信业务，会在内容中加入设定的内容，防止手机拦截
 * <p>
 * Copyright (C), 2018-2020,drondea FileName: PreventShieldFilter Author:   20180512 Date:     2020/3/9 15:43
 * Description: 防屏过滤器 History:
 */
package com.hero.wireless.sender.filter;

import com.drondea.wireless.util.RandomUtil;
import com.drondea.wireless.util.SuperLogger;
import com.hero.wireless.enums.ProductChannelDiversionType;
import com.hero.wireless.json.JsonUtil;
import com.hero.wireless.web.config.DatabaseCache;
import com.hero.wireless.web.entity.business.Channel;
import com.hero.wireless.web.entity.business.ProductChannelsDiversion;
import com.hero.wireless.web.entity.send.Submit;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.text.MessageFormat;
import java.util.List;

public class PreventShieldFilterHandler extends Handler {

    @Override
    public Submit doFilter(Channel channel, Submit submit) {
        if (!DatabaseCache.getStringValueBySystemEnvAndCode("prevent_shield_filter", "close").equals("open")) {
            return submit;
        }
        try {
            List<ProductChannelsDiversion> diversionList = getDiversionStrategyRule(channel, submit,
                    ProductChannelDiversionType.PREVENT_SHIELD_CODE.toString());

            if (ObjectUtils.isEmpty(diversionList)) {
                return submit;
            }
            // 获取规则
            PreventShieldCodeConfig preventShieldCodeConfig = JsonUtil.readValue(
                    diversionList.get(0).getStrategy_Rule(), PreventShieldCodeConfig.class);
            return makeContent(preventShieldCodeConfig, submit);
        } catch (Exception e) {
            SuperLogger.error(e.getMessage(), e);
            return submit;
        }

    }

    private Submit makeContent(PreventShieldCodeConfig preventShieldCodeConfig, Submit submit) {
        String content = submit.getContent();
        String templateContent = preventShieldCodeConfig.getTemplateContent();
        int randomLength = NumberUtils.toInt(preventShieldCodeConfig.getRandomLength(), 0);
        if (content.length() + randomLength + templateContent.length() > 69) {
            return submit;
        }
        if (preventShieldCodeConfig.getPosition().equalsIgnoreCase("prefix")) {
            content = templateContent + content;
        }
        if (preventShieldCodeConfig.getPosition().equalsIgnoreCase("suffix")) {
            content = content + templateContent;
        }
        if (preventShieldCodeConfig.getTypeCode().equalsIgnoreCase("character")) {
            content = MessageFormat.format(content, RandomUtil.randomCharacter(randomLength));
        }
        if (preventShieldCodeConfig.getTypeCode().equalsIgnoreCase("compose")) {
            content = MessageFormat.format(content, RandomUtil.randomStr(randomLength));
        }
        if (preventShieldCodeConfig.getTypeCode().equalsIgnoreCase("number")) {
            content = MessageFormat.format(content, RandomUtil.randomInt(randomLength));
        }
        if (preventShieldCodeConfig.getTypeCode().equalsIgnoreCase("lastStr") && randomLength <= submit.getPhone_No()
                .length()) {
            content = MessageFormat.format(content,
                    submit.getPhone_No().substring(submit.getPhone_No().length() - randomLength));
        }
        submit.setContent(content);
        return submit;
    }

    private class PreventShieldCodeConfig {

        // 添加位置  {"position":"prefix", "typeCode":"character", "randomLength":"2"}
        private String position;
        // 添加类型，数字，字母组合，被叫号码后6位
        private String typeCode;
        // 随机数长度
        private String randomLength;
        // 模板内容
        private String templateContent;

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public String getTypeCode() {
            return typeCode;
        }

        public void setTypeCode(String typeCode) {
            this.typeCode = typeCode;
        }

        public String getRandomLength() {
            return randomLength;
        }

        public void setRandomLength(String randomLength) {
            this.randomLength = randomLength;
        }

        public String getTemplateContent() {
            return templateContent;
        }

        public void setTemplateContent(String templateContent) {
            this.templateContent = templateContent;
        }
    }

}
