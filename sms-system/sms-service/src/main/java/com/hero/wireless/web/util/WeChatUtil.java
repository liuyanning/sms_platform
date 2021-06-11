package com.hero.wireless.web.util;

import com.drondea.wireless.util.SuperLogger;
import com.fasterxml.jackson.core.type.TypeReference;
import com.hero.wireless.json.JsonUtil;
import com.hero.wireless.okhttp.HttpClient;
import com.hero.wireless.web.config.DatabaseCache;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 企业微信消息
 */
public class WeChatUtil {

    /**
     * 发送消息接口
     *
     * @param partyId 部门id
     * @param content 消息内容
     * @return
     */
    public static boolean sendMsg(Integer partyId, String content) {
        try {
            int wxAgentId = DatabaseCache.getIntValueBySortCodeAndCode("msg_notify", "wx_agent_id", 0);
            String url = "https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token=" + getToken();
            HttpClient httpClient = new HttpClient();
            Map<String, String> text = new HashMap<>();
            text.put("content", content);
            Map<String, Object> param = new HashMap<>();
            param.put("toparty", partyId);
            param.put("msgtype", "text");//文本消息
            param.put("agentid", wxAgentId);//应用id
            param.put("text", text);
            param.put("enable_duplicate_check", 0);//表示是否开启重复消息检查，0表示否，1表示是，默认0
            param.put("duplicate_check_interval", 1800);//默认1800s，最大不超过4小时
            String response = httpClient.post(url, JsonUtil.writeValueAsString(param), HttpClient.MEDIA_TYPE_JSON).string();
            Map<String, String> result = JsonUtil.STANDARD.readValue(response, new TypeReference<Map<String, String>>() {
            });
            if (!"0".equals(result.get("errcode"))){
                SuperLogger.warn("微信消息发送失败："+result);
                return false;
            }
            return true;
        } catch (Exception e) {
            SuperLogger.error(e.getMessage(), e);
        }
        return false;
    }

    /**
     * 获取token
     */
    private static String getToken() {
        try {
            String corpId = DatabaseCache.getStringValueBySortCodeAndCode("msg_notify", "wx_corp_id", "");
            String key = "token_wechat_" + corpId;
            String token = GlobalStringCache.get(key);
            if (StringUtils.isNotEmpty(token)) {
                return token;
            }
            String corpSecret = DatabaseCache.getStringValueBySortCodeAndCode("msg_notify", "wx_corp_secret", "");
            String url = "https://qyapi.weixin.qq.com/cgi-bin/gettoken";
            HttpClient httpClient = new HttpClient();
            Map<String, String> param = new HashMap<>();
            param.put("corpid", corpId);
            param.put("corpsecret", corpSecret);
            String response = httpClient.get(url, param).string();
            Map<String, String> result = JsonUtil.STANDARD.readValue(response, new TypeReference<Map<String, String>>() {
            });
            String errCode = result.get("errcode");
            if (!"0".equals(errCode)) {
                SuperLogger.error("发送微信消息获取token出错，上游返回：" + result);
                return null;
            }
            token = result.get("access_token");
            GlobalStringCache.putAndExpire(key, token, 7200L, TimeUnit.SECONDS);
            return token;
        } catch (Exception e) {
            SuperLogger.error(e.getMessage(), e);
        }
        return null;
    }
}
