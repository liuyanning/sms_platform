package com.hero.wireless.http.connector.mms;

import com.drondea.wireless.util.Base64Utils;
import com.drondea.wireless.util.SuperLogger;
import com.hero.wireless.enums.Charset;
import com.hero.wireless.enums.ReportStatus;
import com.hero.wireless.enums.SubmitStatus;
import com.hero.wireless.http.AbstractHttp;
import com.hero.wireless.okhttp.AbstractCallback;
import com.hero.wireless.okhttp.CharsetResponseBody;
import com.hero.wireless.okhttp.HttpClient;
import com.hero.wireless.web.config.DatabaseCache;
import com.hero.wireless.web.entity.business.Channel;
import com.hero.wireless.web.entity.send.Report;
import com.hero.wireless.web.entity.send.Submit;
import com.hero.wireless.web.util.SMSUtil;

import org.apache.commons.lang3.ObjectUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

/**
 * 
* Title: JinHongMMSImpl  
* Description:金鸿数字短信
* @author yjb
* @date 2019-11-27
 */
public class JinHongMMSImpl extends AbstractHttp {
	@Override
	public void report(Channel channel) throws Exception {
		String url = reportUrl(channel);
		Map<String, String> params = new HashMap<String, String>();
		params.put("userid", channel.getAccount());
		params.put("account", channel.getAccount());
		params.put("password", channel.getPassword());
		params.put("count", "500");
		HttpClient httpClient = new HttpClient();
		httpClient.setCharset(Charset.UTF8.toString());
		httpClient.postAsync(url, params, new AbstractCallback(httpClient.getCharset()) {
			@Override
			public void ok(Call call, CharsetResponseBody response) throws Exception {
				String resultXml = response.string();
				SuperLogger.debug(resultXml);
				Document resultDocument = DocumentHelper.parseText(resultXml);
				Element rootElement = resultDocument.getRootElement();
				String result = rootElement.elementText("result");
				if(!result.equals("0")){
					return;
				}
				Element reportt = rootElement.element("report");
	    		List<?> reportElementList = reportt.elements("item");
				if (ObjectUtils.isEmpty(reportElementList)) {
					return;
				}
				reportElementList.forEach(item -> {
					Element report = (Element) item;
					Report entity = new Report();
					entity.setChannel_No(channel.getNo());
					String mmsid = report.elementText("mmsid");
					entity.setChannel_Msg_Id(mmsid);
					entity.setPhone_No(report.elementText("mobile"));
					String status = report.elementText("status");
					entity.setNative_Status(status);
					if (status.trim().equals("0")) {
						entity.setStatus_Code(ReportStatus.SUCCESS.toString());
					} else {
						entity.setStatus_Code(ReportStatus.FAILD.toString());
					}
					entity.setRemark(report.elementText("desc"));
					saveReport(entity);
				});
			}
		});
	}

	@Override
	public SubmitStatus submit(Submit submit) throws Exception {
		Channel channel = DatabaseCache.getChannelByNo(submit.getChannel_No());
		String postURL = submitUrl(channel);
		HttpClient httpClient = new HttpClient();
		httpClient.setCharset(Charset.UTF8.toString());
		Map<String, String> param = new HashMap<String, String>();
		param.put("userid", channel.getAccount());
		param.put("account", channel.getAccount());
		param.put("password", channel.getPassword());
		param.put("mobile", submit.getPhone_No());
		param.put("content", Base64Utils.encode(URLEncoder.encode(submit.getContent(),"UTF-8")));
		param.put("exno", SMSUtil.getSpNumber(channel, submit.getSub_Code()));
		String info = httpClient.post(postURL, param).string();
		SuperLogger.debug(info);
		Document resultDocument = DocumentHelper.parseText(info);
		Element rootElement = resultDocument.getRootElement();
		String result = rootElement.elementText("result");
		if(!result.equals("0")){
			SuperLogger.debug("金鸿数字短信提交失败,失败码:"+result);
			return SubmitStatus.FAILD;
		}
		String mmsid = rootElement.elementText("mmsid");
		submit.setChannel_Msg_Id(mmsid);
		submit.setSubmit_Description(result);
		if (result.equals("0")) {
			return SubmitStatus.SUCCESS;
		} else {
			return SubmitStatus.FAILD;
		}
	}

	@Override
	public String balance(Channel channel) throws Exception {
		String url = balanceUrl(channel);
		Map<String, String> params = new HashMap<String, String>();
		params.put("action", "querybalance");
		params.put("userid", channel.getAccount());
		params.put("account", channel.getAccount());
		params.put("password", channel.getPassword());
		HttpClient httpClient = new HttpClient();
		httpClient.setCharset(Charset.UTF8.toString());
		String info = httpClient.post(url, params).string();
		SuperLogger.debug(info);
		Document resultDocument = DocumentHelper.parseText(info);
		Element rootElement = resultDocument.getRootElement();
		String result = rootElement.elementText("result");
		if(!result.equals("0")){
			return null;
		}
		String balance = rootElement.elementText("desc");
		result = String.format("余额：%1$s条", balance);
		return result;
	}

	@Override
	public void mo(Channel channel) throws Exception {
		// TODO Auto-generated method stub
		
	}
}
