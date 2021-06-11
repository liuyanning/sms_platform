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
 * 钉钉消息
 */
public class DingTalkUtil {

    /**
     * 发送消息接口
     *
     * @param partyId 部门ID。
     * @param content 消息内容
     * @return
     */
    public static boolean sendMsg(Integer partyId, String content) {
        try {
            String url = "https://oapi.dingtalk.com/message/send?access_token=" + getToken();
            int dingAgentId = DatabaseCache.getIntValueBySortCodeAndCode("msg_notify", "ding_agent_id", 0);
            HttpClient httpClient = new HttpClient();
            Map<String, String> text = new HashMap<>();
            text.put("content", content);
            Map<String, Object> param = new HashMap<>();
            param.put("toparty", partyId);
            param.put("msgtype", "text");//文本消息
            param.put("agentid", dingAgentId);//应用id
            param.put("text", text);
            String response = httpClient.post(url, JsonUtil.writeValueAsString(param), HttpClient.MEDIA_TYPE_JSON).string();
            Map<String, String> result = JsonUtil.STANDARD.readValue(response, new TypeReference<Map<String, String>>() {
            });
            if (!"0".equals(result.get("errcode"))){
                SuperLogger.warn("钉钉消息发送失败："+result);
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
            String appKey = DatabaseCache.getStringValueBySortCodeAndCode("msg_notify", "ding_app_key", "");
            String key = "token_ding_" + appKey;
            String token = GlobalStringCache.get(key);
            if (StringUtils.isNotEmpty(token)) {
                return token;
            }
            String appSecret = DatabaseCache.getStringValueBySortCodeAndCode("msg_notify", "ding_app_secret", "");
            String url = "https://oapi.dingtalk.com/gettoken";
            HttpClient httpClient = new HttpClient();
            Map<String, String> param = new HashMap<>();
            param.put("appkey", appKey);
            param.put("appsecret", appSecret);
            String response = httpClient.get(url, param).string();
            Map<String, String> result = JsonUtil.STANDARD.readValue(response, new TypeReference<Map<String, String>>() {
            });
            String errCode = result.get("errcode");
            if (!"0".equals(errCode)) {
                SuperLogger.error("发送钉钉消息获取token出错，上游返回：" + result);
                return null;
            }
            token = result.get("access_token");
            GlobalStringCache.putAndExpire(key, token, 7000L, TimeUnit.SECONDS);
            return token;
        } catch (Exception e) {
            SuperLogger.error(e.getMessage(), e);
        }
        return null;
    }
}


