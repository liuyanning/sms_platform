package com.hero.wireless.http.connector;

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
import com.hero.wireless.web.entity.send.Inbox;
import com.hero.wireless.web.entity.send.Report;
import com.hero.wireless.web.entity.send.Submit;
import com.hero.wireless.web.util.SMSUtil;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

public class ShangXinTongImpl extends AbstractHttp {

	@Override
	public void mo(Channel channel) {
		String postURL = moUrl(channel);
		Map<String, String> params = new HashMap<String, String>();
		params.put("user", channel.getAccount());
		params.put("channel", "");
		params.put("pwd", channel.getPassword());
		HttpClient httpClient = new HttpClient();
		httpClient.postAsync(postURL, params, new AbstractCallback(httpClient.getCharset()) {
			@Override
			public void ok(Call call, CharsetResponseBody response) throws Exception {
				String resultString = response.string();
				SuperLogger.debug(resultString);
				if (StringUtils.isEmpty(resultString.trim())) {
					return;
				}
				Document submitXML = DocumentHelper.parseText(resultString);
				Element rootElement = submitXML.getRootElement();
				if (!rootElement.elementText("status").equals("0")) {
					return;
				}

				Element messElement = rootElement.element("mess");
				List<?> pmessElementList = messElement.elements("pmess");
				if (ObjectUtils.isEmpty(pmessElementList)) {
					return;
				}
				pmessElementList.forEach((item) -> {
					Inbox inbox = new Inbox();
					Element pmessElement = (Element) item;
					inbox.setChannel_No(channel.getNo());
					inbox.setSP_Number(pmessElement.elementText("receiver"));
					inbox.setPhone_No(pmessElement.elementText("sender"));
					String cont = pmessElement.elementText("cont");
					inbox.setContent(cont);
					inbox.setCreate_Date(new Date());
					saveMo(inbox);
				});
			}
		});
	}

	@Override
	public void report(Channel channel) {
		String postURL = reportUrl(channel);
		Map<String, String> params = new HashMap<String, String>();
		params.put("user", channel.getAccount());
		params.put("channel", "");
		params.put("pwd", channel.getPassword());
		HttpClient httpClient = new HttpClient();
		httpClient.postAsync(postURL, params, new AbstractCallback(httpClient.getCharset()) {
			@Override
			public void ok(Call call, CharsetResponseBody response) throws Exception {
				String resultString = response.string();
				SuperLogger.debug(resultString);
				if (StringUtils.isEmpty(resultString)) {
					return;
				}
				Document submitXML = DocumentHelper.parseText(resultString);
				Element rootElement = submitXML.getRootElement();
				Element receiptElement = rootElement.element("receipt");
				if (receiptElement == null) {
					return;
				}
				List<?> preceiptElementList = receiptElement.elements("preceipt");
				if (ObjectUtils.isEmpty(preceiptElementList)) {
					return;
				}
				preceiptElementList.forEach(item -> {
					Element preceiptElement = (Element) item;
					Report entity = new Report();
					entity.setChannel_No(channel.getNo());
					entity.setChannel_Msg_Id(preceiptElement.elementText("seq"));
					entity.setPhone_No(preceiptElement.elementText("phone"));
					entity.setNative_Status(preceiptElement.elementText("msg_flagdesc"));
					if ("2".equals(preceiptElement.elementText("msg_flag"))) {
						entity.setStatus_Code(ReportStatus.SUCCESS.toString());
					} else {
						entity.setStatus_Code(ReportStatus.FAILD.toString());
					}
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
		String cont = setSignaturePre(submit.getContent());
		if (StringUtils.isNotEmpty(extInfo2(channel)) && extInfo2(channel).equals("UrlEncode")) {
			cont = URLEncoder.encode(cont, Charset.UTF8.toString());
		}
		Map<String, String> params = new HashMap<String, String>();
		params.put("user", channel.getAccount());
		params.put("pwd", channel.getPassword());
		params.put("phonelist", submit.getPhone_No());
		params.put("cont", cont);
		params.put("extend", SMSUtil.getSpNumber(channel, submit.getSub_Code()));

		String submitResultString = httpClient.post(postURL, params).string();
		SuperLogger.debug(submitResultString);
		Document submitXML = DocumentHelper.parseText(submitResultString);
		Element rootElement = submitXML.getRootElement();
		String status = rootElement.elementText("status");
		submit.setChannel_Msg_Id(rootElement.elementText("seq"));
		submit.setSubmit_Description(rootElement.elementText("error"));
		if ("1".equals(status)) {
			return SubmitStatus.SUCCESS;
		} else {
			return SubmitStatus.FAILD;
		}
	}

	@Override
	public String balance(Channel channel) {
		String url = balanceUrl(channel);
		Map<String, String> params = new HashMap<String, String>();
		params.put("user", channel.getAccount());
		params.put("pwd", channel.getPassword());
		HttpClient httpClient = new HttpClient();
		httpClient.setCharset("GBK");
		String result = "";
		try {
			CharsetResponseBody response = httpClient.post(url, params);
			if (response == null)
				return "";
			result = response.string();
		} catch (Exception ex) {
			SuperLogger.error(ex.getMessage(), ex);
			result = ex.getMessage();
		}
		return result;
	}

}
