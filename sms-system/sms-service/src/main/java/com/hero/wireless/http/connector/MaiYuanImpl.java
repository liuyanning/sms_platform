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
import okhttp3.Call;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 迈远接口 迈远编码 真是乱 GBK，UTF-8四个接口并不是统一的
 *
 * @author Lenovo
 *
 */
public class MaiYuanImpl extends AbstractHttp {
	@Override
	public void mo(Channel channel) {
		String url = moUrl(channel);
		String timestamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		Map<String, String> params = new HashMap<String, String>();
		String[] userInfos = channel.getAccount().split(",");
		String account = userInfos[0];
		String id = userInfos.length > 1 ? userInfos[1] : "";
		params.put("userid", id);
		params.put("timestamp", timestamp);
		params.put("sign", SecretUtil.MD5AddKey(account + channel.getPassword() + timestamp, "").toLowerCase());
		params.put("account", account);
		params.put("password", channel.getPassword());
		params.put("action", "query");
		HttpClient httpClient = new HttpClient();
        if (url.endsWith("GBK.aspx")) {
            httpClient.setCharset("GBK");
        } else {
            httpClient.setCharset(Charset.UTF8.toString());
        }
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
	public void report(Channel channel) {
		String url = reportUrl(channel);
		String timestamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		Map<String, String> params = new HashMap<String, String>();
		String[] userInfos = channel.getAccount().split(",");
		String account = userInfos[0];
		String id = userInfos.length > 1 ? userInfos[1] : "";
		params.put("userid", id);
		params.put("timestamp", timestamp);
		params.put("sign", SecretUtil.MD5AddKey(account + channel.getPassword() + timestamp, "").toLowerCase());
		params.put("account", account);
		params.put("password", channel.getPassword());
		params.put("action", "query");
		HttpClient httpClient = new HttpClient();
        if (url.endsWith("GBK.aspx")) {
            httpClient.setCharset("GBK");
        } else {
            httpClient.setCharset(Charset.UTF8.toString());
        }
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

					String mobile = report.elementText("mobile");
					entity.setPhone_No(mobile);
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
		if (postURL.endsWith("GBK.aspx")) {
			httpClient.setCharset("GBK");
		} else {
			httpClient.setCharset(Charset.UTF8.toString());
		}
		submit.setContent(setSignaturePre(submit.getContent()));
		String timestamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		Map<String, String> param = new HashMap<String, String>();
		String[] userInfos = channel.getAccount().split(",");
		String account = userInfos[0];
		String id = userInfos.length > 1 ? userInfos[1] : "";
		param.put("userid", id);
		param.put("timestamp", timestamp);
		param.put("sign", SecretUtil.MD5AddKey(account + channel.getPassword() + timestamp, "").toLowerCase());
		param.put("account", account);
		param.put("password", channel.getPassword());
		String mobile = submit.getPhone_No();
		param.put("mobile", mobile);
		param.put("content", submit.getContent());
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
		String result = null;
		String url = balanceUrl(channel);
		String timestamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		Map<String, String> params = new HashMap<String, String>();
		String[] userInfos = channel.getAccount().split(",");
		String account = userInfos[0];
		String id = userInfos.length > 1 ? userInfos[1] : "";
		params.put("userid", id);
		params.put("timestamp", timestamp);
		params.put("sign", SecretUtil.MD5(account + channel.getPassword() + timestamp).toLowerCase());
		params.put("account", account);
		params.put("password", channel.getPassword());
		params.put("action", "overage");
		HttpClient httpClient = new HttpClient();
		if (url.endsWith("GBK.aspx")) {
			httpClient.setCharset("GBK");
		} else {
			httpClient.setCharset(Charset.UTF8.toString());
		}
		CharsetResponseBody response = httpClient.post(url, params);
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
		result = String.format("余额：%1$s条,发送：%2$s条", rootElement.elementText("overage"),
				rootElement.elementText("sendTotal"));
		return result;
	}

	public static void main(String[] args) {
//		testSend();
		testReport();
//		testBalance();
//		String internationalCodes = "+86";
//		internationalCodes = internationalCodes.replaceAll("\\+", "\\\\+");
//		System.out.println(internationalCodes);
//		String moblie = "+86135,+86136";
//		System.out.println(moblie.replaceAll(internationalCodes, ""));
	}

	public static void testSend() {
		HttpClient httpClient = new HttpClient();
		httpClient.setCharset("utf-8");
		String postURL = "http://123.56.234.185:7878/sms.aspx?action=send";
		String id = "1050";
		String account = "blyjs";
		String password = "112233";
		String mobile = "17332958317";
		String content = "【小府仙】专门做53度酒，原厂报装品质保障，出厂价直销，每日100名可领取125ml品鉴有意者+xfx7658领取。退订回T";
		String timestamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		Map<String, String> params = new HashMap<String, String>();
		params.put("userid", id);
		params.put("timestamp", timestamp);
		params.put("sign", SecretUtil.MD5AddKey(account + password + timestamp, "").toLowerCase());
		params.put("account", account);
		params.put("password", password);
		params.put("action", "send");
		params.put("mobile", mobile);
		params.put("content", content);
		params.put("sendTime", "");
		params.put("extno", "");
		String info = httpClient.post(postURL, params).string();
		System.out.println(info);
	}

	public static void testReport() {
		HttpClient httpClient = new HttpClient();
		httpClient.setCharset("utf-8");
		String postURL = "http://123.56.234.185:7878/statusApi.aspx?action=query";
		String id = "1050";
		String account = "blyjs";
		String password = "112233";
		String timestamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		Map<String, String> params = new HashMap<String, String>();
		params.put("userid", id);
		params.put("timestamp", timestamp);
		params.put("sign", SecretUtil.MD5AddKey(account + password + timestamp, "").toLowerCase());
		params.put("account", account);
		params.put("password", password);
		params.put("action", "query");

		httpClient.postAsync(postURL, params, new AbstractCallback(httpClient.getCharset()) {
			@Override
			public void ok(Call call, CharsetResponseBody response) throws Exception {
				String resultXml = response.string();
				SuperLogger.debug(resultXml);
				System.out.println(resultXml);
				if (StringUtils.isEmpty(resultXml)) {
					return;
				}
				Document resultDocument = DocumentHelper.parseText(resultXml);
				Element rootElement = resultDocument.getRootElement();
				List<?> reportElementList = rootElement.elements("statusbox");
				if (ObjectUtils.isEmpty(reportElementList)) {
					return;
				}
			}
		});
	}

	public static void testBalance() {
		HttpClient httpClient = new HttpClient();
		httpClient.setCharset("utf-8");
		String postURL = "http://123.56.234.185:7878/sms.aspx?action=overage";
		String id = "1050";
		String account = "blyjs";
		String password = "112233";
		String timestamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		Map<String, String> params = new HashMap<String, String>();
		params.put("userid", id);
		params.put("timestamp", timestamp);
		params.put("sign", SecretUtil.MD5(account + password + timestamp).toLowerCase());
		params.put("account", account);
		params.put("password", password);
		params.put("action", "overage");

		CharsetResponseBody response = httpClient.post(postURL, params);
		if (response == null) {
			System.out.println("=========");
		}
		String resultXml = response.string();
		System.out.println(resultXml);
	}
}
