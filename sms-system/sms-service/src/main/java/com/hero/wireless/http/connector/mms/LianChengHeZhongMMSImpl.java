package com.hero.wireless.http.connector.mms;

import com.drondea.wireless.util.DateTime;
import com.drondea.wireless.util.RandomUtil;
import com.drondea.wireless.util.SecretUtil;
import com.drondea.wireless.util.SuperLogger;
import com.fasterxml.jackson.core.type.TypeReference;
import com.hero.wireless.enums.Charset;
import com.hero.wireless.enums.SubmitStatus;
import com.hero.wireless.http.AbstractHttp;
import com.hero.wireless.http.bean.LianChengHeZhongApprovalPendingMMS;
import com.hero.wireless.json.JsonUtil;
import com.hero.wireless.notify.JsonLianChengHeZhongMMSReport;
import com.hero.wireless.okhttp.CharsetResponseBody;
import com.hero.wireless.okhttp.HttpClient;
import com.hero.wireless.web.config.DatabaseCache;
import com.hero.wireless.web.entity.business.Channel;
import com.hero.wireless.web.entity.send.Report;
import com.hero.wireless.web.entity.send.Submit;

import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

/**
 * 
* Title: LianChengHeZhongMMSImpl  
* Description:联诚合众数字短信实现类(彩信)
* @author yjb
* @date 2019-12-28
 */
public class LianChengHeZhongMMSImpl extends AbstractHttp {
	
	@Override
	public Object report(ChannelReport channelReport) throws Exception {
		JsonLianChengHeZhongMMSReport base = (JsonLianChengHeZhongMMSReport)channelReport.getData();
		Report report = new Report();
		report.setCreate_Date(new Date());
		report.setStatus_Code(base.getState());
		report.setNative_Status(base.getState());
		report.setRemark(base.getErrorCode());
		report.setStatus_Date(new Date());
		saveReport(report);
		return "0";
	};
	
	@Override
	public void report(Channel channel) {
		
	}

	@Override
	public SubmitStatus submit(Submit submit) throws Exception {
		
		Channel channel = DatabaseCache.getChannelByNo(submit.getChannel_No());
		String signStr =  channel.getPassword() + "/api/send?";
		String postURL = submitUrl(channel);
		HttpClient httpClient = new HttpClient();
		httpClient.setCharset(Charset.UTF8.toString());
		String timestamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		TreeMap<String, String> params = new TreeMap<String, String>();
        params.put("username", channel.getAccount());
        params.put("sequenceNumber", timestamp + RandomUtil.randomInt(6));
        params.put("userNumber", submit.getPhone_No());
        params.put("id", submit.getContent());
        params.put("timestamp",timestamp);
        String sign = createSign(params, signStr);
        params.put("sign",sign);
        String httpContent = JsonUtil.STANDARD.writeValueAsString(params);
        SuperLogger.debug("请求报文："+httpContent);
        CharsetResponseBody response = httpClient.post(postURL, httpContent,HttpClient.MEDIA_TYPE_JSON);
		if(response != null && !StringUtils.isEmpty(response.string())){
			SuperLogger.debug("响应报文："+response.string());
            Map<String, String> resultMap = JsonUtil.STANDARD.readValue(response.string(), new TypeReference<Map<String, String>>() {});
            String httpResult = resultMap.get("result");
            submit.setChannel_Msg_Id(resultMap.get("sequenceNumber"));
    		submit.setSubmit_Description(resultMap.get("desc"));
            if("0".equals(httpResult)){//0:成功，-1：失败
        		return SubmitStatus.SUCCESS;
            }else{
            	
        		return SubmitStatus.FAILD;
            }
        }
		return SubmitStatus.FAILD;
	}

	@Override
	public void mo(Channel channel) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String balance(Channel channel) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	public String createApprovalPendingMMS(LianChengHeZhongApprovalPendingMMS bean) throws Exception {
		Channel channel = DatabaseCache.getChannelByNo(bean.getChannelNo());
		String url = extInfo1(channel);
		String signStr =  channel.getPassword() + "/api/dyCheckSave?";
        String timestamp = DateTime.getString(new Date(),DateTime.Y_M_D_H_M_S_S_2);
        TreeMap<String, String> params = new TreeMap<String, String>();
        params.put("username", channel.getAccount());
        params.put("openTd", bean.getOpenTd());
        params.put("type", bean.getType());// 15 图片  2生活
        params.put("title", bean.getTitle());
        params.put("timestamp",timestamp);
        String sign = createSign(params, signStr);
        params.put("sign",sign);
        params.put("param", bean.getParam());
        Map<String, String> resultMap =  new HashMap<String, String>();
        try {
            HttpClient httpClient = new HttpClient();
            httpClient.setCharset("UTF-8");
            String httpContent = JsonUtil.STANDARD.writeValueAsString(params);
            SuperLogger.debug("请求参数："+httpContent);
            CharsetResponseBody response = httpClient.post(url, httpContent,HttpClient.MEDIA_TYPE_JSON);
            if(response != null && !StringUtils.isEmpty(response.string())){
                resultMap = JsonUtil.STANDARD.readValue(response.string(), new TypeReference<Map<String, String>>() {});
            } else {
            	resultMap.put("result", "-1");
            	resultMap.put("desc", "请求失败");
            }
        } catch (Exception e) {
        	resultMap.put("result", "-1");
        	resultMap.put("desc", "程序异常");
        	SuperLogger.error(e.getMessage());
        }
		return JsonUtil.STANDARD.writeValueAsString(resultMap);
	}

	private String createSign(TreeMap<String, String> params, String signStr) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(signStr);
		for (Entry<String, String> entry : params.entrySet()) {
			stringBuilder.append(entry.getKey()).append('=')
					.append(entry.getValue()).append('&');
		}
		signStr = stringBuilder.toString();
		if (signStr.endsWith("&")) {
			signStr = signStr.substring(0, signStr.length() - 1);
		}
		String sign = SecretUtil.MD5(signStr).toLowerCase();
		return sign;
	}
}
