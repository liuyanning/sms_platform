package com.hero.wireless.http.connector.mnp;

import com.drondea.wireless.util.RandomUtil;
import com.drondea.wireless.util.SuperLogger;
import com.hero.wireless.http.AbstractHttp;
import com.hero.wireless.http.bean.TransferPhoneResultBean;
import com.hero.wireless.json.JsonUtil;
import com.hero.wireless.okhttp.HttpClient;

import java.security.MessageDigest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Title: LouShangLouImp
 * Description: 携号转网手机号查询
 *
 * @author yjb
 * @date 2020-08-19
 */
public class LouShangLouImp extends AbstractHttp {

    @Override
    public Map<String, Integer> queryTransferPhone(String token, String mobiles, String url) {
        Map<String, String> params = new HashMap<String, String>();
        String randomString = RandomUtil.randomStr(8);
        params.put("randomStr", randomString);
        String timestamp = String.valueOf(System.currentTimeMillis());
        params.put("timestamp", timestamp);
        String encrypt = encrypt(token, timestamp, randomString);
        params.put("token", token);
        params.put("encryptStr", encrypt);
        params.put("mobiles", mobiles);
        HttpClient httpClient = new HttpClient();
        httpClient.setCharset("UTF-8");
        String info = httpClient.post(url, params).string();
        try {
            Map<String, Integer> resultMap = new HashMap<String, Integer>();
            TransferPhoneResultBean resultBean = JsonUtil.STANDARD.readValue(info, TransferPhoneResultBean.class);
            if (resultBean.getCode() == 0) {
                resultMap = resultBean.getData();
            }
            return resultMap;
        } catch (Exception e) {
            SuperLogger.error(e.getMessage(), e);
            return null;
        }

    }

    public String encrypt(String token, String timestamp, String randomStr) {
        try {
            String[] array = new String[]{token, timestamp, randomStr};
            StringBuffer sb = new StringBuffer();
            Arrays.sort(array);
            for (int i = 0; i < 3; i++) {
                sb.append(array[i]);
            }
            String str = sb.toString();
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(str.getBytes());
            byte[] digest = md.digest();

            StringBuffer hexstr = new StringBuffer();
            String shaHex = "";
            for (int i = 0; i < digest.length; i++) {
                shaHex = Integer.toHexString(digest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexstr.append(0);
                }
                hexstr.append(shaHex);
            }
            return hexstr.toString();
        } catch (Exception e) {
            SuperLogger.error("SHA1 encoder error", e);
            return null;
        }
    }
}
