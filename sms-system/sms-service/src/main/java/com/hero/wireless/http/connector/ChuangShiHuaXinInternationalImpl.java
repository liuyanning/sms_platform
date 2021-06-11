package com.hero.wireless.http.connector;

import com.drondea.wireless.util.SecretUtil;
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

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

/**
 * 创世华信国际短信
 *
 * @author zly
 * @date 2019.09.09 2:46:30
 */
public class ChuangShiHuaXinInternationalImpl extends AbstractHttp {

	@Override
	public void mo(Channel channel) throws Exception {
		String url = moUrl(channel);
		Map<String, String> params = new HashMap<String, String>();
		params.put("userid", "");
		params.put("account", channel.getAccount());
		params.put("password", SecretUtil.MD5(channel.getPassword()).toUpperCase());
		params.put("action", "query");
		HttpClient httpClient = new HttpClient();
		httpClient.setCharset(Charset.UTF8.toString());
		httpClient.postAsync(url, params, new AbstractCallback(httpClient.getCharset()) {
			@Override
			public void ok(Call call, CharsetResponseBody response) throws Exception {
				String resultXml = response.string();
				SuperLogger.debug(resultXml);
				if (StringUtils.isEmpty(resultXml)) {
					return;
				}
				Document resultDocument = DocumentHelper.parseText(resultXml);
				Element rootElement = resultDocument.getRootElement();
				List<?> moElementList = rootElement.elements("callbox");
				if (ObjectUtils.isEmpty(moElementList)) {
					return;
				}
				moElementList.forEach((deliverElement) -> {
					Element callBoxElement = (Element) deliverElement;
					Inbox inbox = new Inbox();
					inbox.setChannel_No(channel.getNo());
					inbox.setSP_Number(callBoxElement.elementText("extno"));
					inbox.setPhone_No(callBoxElement.elementText("mobile"));
					String content = callBoxElement.elementText("content");
					inbox.setContent(content);
					inbox.setPull_Total(0);
					inbox.setPull_Date(new Date());
					inbox.setCreate_Date(new Date());
					saveMo(inbox);
				});
			}
		});
	}

	@Override
	public void report(Channel channel) throws Exception {
		String url = reportUrl(channel);
		Map<String, String> params = new HashMap<String, String>();
		params.put("userid", "");
		params.put("account", channel.getAccount());
		params.put("password", SecretUtil.MD5(channel.getPassword()).toUpperCase());
		params.put("statusNum", "1000");
		params.put("action", "query");
		HttpClient httpClient = new HttpClient();
		httpClient.setCharset(Charset.UTF8.toString());
		httpClient.postAsync(url, params, new AbstractCallback(httpClient.getCharset()) {
			@Override
			public void ok(Call call, CharsetResponseBody response) throws Exception {
				String resultXml = response.string();
				SuperLogger.debug(resultXml);
				if (StringUtils.isEmpty(resultXml)) {
					return;
				}
				Document resultDocument = DocumentHelper.parseText(resultXml);
				Element rootElement = resultDocument.getRootElement();
				List<?> reportElementList = rootElement.elements("statusbox");
				if (ObjectUtils.isEmpty(reportElementList)) {
					return;
				}
				reportElementList.forEach(item -> {
					Element report = (Element) item;
					Report entity = new Report();
					entity.setChannel_No(channel.getNo());
					String taskid = report.elementText("taskid");
					entity.setChannel_Msg_Id(taskid);
					entity.setPhone_No(report.elementText("mobile"));
					String status = report.elementText("status");
					entity.setNative_Status(status);
					if (status.trim().equals("10")) {
						entity.setStatus_Code(ReportStatus.SUCCESS.toString());
					} else {
						entity.setStatus_Code(ReportStatus.FAILD.toString());
					}
					entity.setRemark(report.elementText("errorcode"));
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
		param.put("userid", "");
		param.put("account", channel.getAccount());
		param.put("password", SecretUtil.MD5(channel.getPassword()).toUpperCase());
		param.put("mobile", submit.getPhone_No());
		param.put("content", Hex.encodeHexString(submit.getContent().getBytes("UNICODE")));
		param.put("sendTime", "");
		param.put("action", "send");
		param.put("extno", SMSUtil.getSpNumber(channel, submit.getSub_Code()));
		String info = httpClient.post(postURL, param).string();
		SuperLogger.debug(info);
		if (StringUtils.isEmpty(info)) {
			return SubmitStatus.FAILD;
		}
		Document resultDocument = DocumentHelper.parseText(info);
		Element rootElement = resultDocument.getRootElement();
		String taskID = rootElement.elementText("taskID");
		String result = rootElement.elementText("returnstatus");
		submit.setChannel_Msg_Id(taskID);
		submit.setSubmit_Description(rootElement.elementText("message"));
		if (result.equals("Success")) {
			return SubmitStatus.SUCCESS;
		} else {
			return SubmitStatus.FAILD;
		}
	}

	@Override
	public String balance(Channel channel) throws Exception {
		String url = balanceUrl(channel);
		Map<String, String> param = new HashMap<String, String>();
		param.put("userid", "");
		param.put("account", channel.getAccount());
		param.put("password", SecretUtil.MD5(channel.getPassword()).toUpperCase());
		param.put("action", "overage");
		HttpClient httpClient = new HttpClient();
		httpClient.setCharset(Charset.UTF8.toString());
		CharsetResponseBody response = httpClient.post(url, param);
		if (response == null) {
			return null;
		}
		String resultXml = response.string();
		if (StringUtils.isEmpty(resultXml)) {
			return null;
		}
		SuperLogger.debug(resultXml);
		Document resultDocument = DocumentHelper.parseText(resultXml);
		Element rootElement = resultDocument.getRootElement();
		if (ObjectUtils.isEmpty(rootElement)) {
			return null;
		}
		String result = rootElement.elementText("balance");
		return result;
	}
}
