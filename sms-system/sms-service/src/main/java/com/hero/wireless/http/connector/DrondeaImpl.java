package com.hero.wireless.http.connector;

import com.drondea.wireless.util.SuperLogger;
import com.fasterxml.jackson.core.type.TypeReference;
import com.hero.wireless.enums.Charset;
import com.hero.wireless.enums.ReportStatus;
import com.hero.wireless.enums.SubmitStatus;
import com.hero.wireless.http.AbstractHttp;
import com.hero.wireless.json.JsonUtil;
import com.hero.wireless.notify.NotifyUtil;
import com.hero.wireless.okhttp.HttpClient;
import com.hero.wireless.web.config.DatabaseCache;
import com.hero.wireless.web.entity.business.Channel;
import com.hero.wireless.web.entity.send.Inbox;
import com.hero.wireless.web.entity.send.Report;
import com.hero.wireless.web.entity.send.Submit;
import com.hero.wireless.web.util.SMSUtil;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class DrondeaImpl extends AbstractHttp {
	public static class DrondeaReport implements Serializable {
		/**
		 *
		 */
		private static final long serialVersionUID = -4574606626303174484L;
		List<Map<String, String>> report;

		public DrondeaReport() {
			super();
		}

		public List<Map<String, String>> getReport() {
			return report;
		}

		public void setReport(List<Map<String, String>> report) {
			this.report = report;
		}

	}

	@Override
	public void mo(Channel channel) throws Exception {
	}

	@Override
	public void report(Channel channel) throws Exception {
	}

	@Override
	public Object mo(ChannelReport channelReport) throws Exception {
		DrondeaReport drondeaReport = JsonUtil.NON_NULL.readValue(channelReport.getData().toString(),
				DrondeaReport.class);
		List<Map<String, String>> reports = drondeaReport.getReport();
		reports.forEach(item -> {
			Inbox inbox = new Inbox();
			inbox.setChannel_No(channelReport.getChannelNo());
			inbox.setSP_Number(item.get("port"));
			inbox.setPhone_No(item.get("phone"));
			String content = item.get("content");
			inbox.setContent(content);
			inbox.setPull_Total(0);
			inbox.setCreate_Date(new Date());
			saveMo(inbox);
		});
		return "0";
	}

	@Override
	public Object report(ChannelReport channelReport) throws Exception {
		DrondeaReport drondeaReport = JsonUtil.NON_NULL.readValue(channelReport.getData().toString(),
				DrondeaReport.class);
		List<Map<String, String>> reports = drondeaReport.getReport();
		reports.forEach(item -> {
			Report entity = new Report();
			entity.setChannel_No(channelReport.getChannelNo());
			String msgId = item.get("msgid");
			entity.setChannel_Msg_Id(msgId);
			entity.setPhone_No(item.get("phone"));
			String status = item.get("result");
			entity.setNative_Status(status);
			entity.setSP_Number(item.get("netway_code"));
			String seq = item.get("seq");
			if (StringUtils.isNotBlank(seq)) {
				entity.setSequence(Integer.valueOf(seq));
			}
			if (status.trim().equalsIgnoreCase("Success")) {
				entity.setStatus_Code(ReportStatus.SUCCESS.toString());
			} else {
				entity.setStatus_Code(ReportStatus.FAILD.toString());
			}
			entity.setStatus_Date(new Date());
			saveReport(entity);
		});
		return "0";
	}

	@Override
	public SubmitStatus submit(Submit submit) throws Exception {
		Channel channel = DatabaseCache.getChannelCachedByNo(submit.getChannel_No());
		String postURL = submitUrl(channel);
		Map<String, Object> dataMap = new HashMap<>();
		String[] accountInfo = channel.getAccount().split(",");
		dataMap.put("enterprise_no", accountInfo[0]);
		dataMap.put("account", accountInfo[1]);
		dataMap.put("timestamp", timestamp());
		dataMap.put("sign", NotifyUtil.genSign(accountInfo[0], accountInfo[1], dataMap.get("timestamp").toString(),
				channel.getPassword()));
		dataMap.put("phones", submit.getPhone_No());
		dataMap.put("content", submit.getContent());
		dataMap.put("subcode", SMSUtil.getSpNumber(channel, submit.getSub_Code()));
		HttpClient httpClient = new HttpClient();
		httpClient.setCharset(Charset.UTF8.toString());
		String content = JsonUtil.STANDARD.writeValueAsString(dataMap);

		String result = httpClient.post(postURL, content, HttpClient.MEDIA_TYPE_JSON).string();
		SuperLogger.debug(result);
		Map<String, String> resultMap = JsonUtil.STANDARD.readValue(result, new TypeReference<Map<String, String>>() {
		});
		String msgId = resultMap.get("msgid");
		String status = resultMap.get("result");
		submit.setChannel_Msg_Id(msgId);
		submit.setSubmit_Description(resultMap.get("desc"));
		if (status.equals("0")) {
			return SubmitStatus.SUCCESS;
		} else {
			return SubmitStatus.FAILD;
		}
	}

	@Override
	public String balance(Channel channel) throws Exception {
		Map<String, String> baseMap = new HashMap<>();
		String[] accountInfo = channel.getAccount().split(",");
		baseMap.put("enterprise_no", accountInfo[0]);
		baseMap.put("account", accountInfo[1]);
		baseMap.put("timestamp", timestamp());
		baseMap.put("sign", NotifyUtil.genSign(accountInfo[0], accountInfo[1], baseMap.get("timestamp").toString(),
				channel.getPassword()));
		Request request = new Request.Builder().url(balanceUrl(channel)).post(
				FormBody.create(MediaType.parse("application/json"), JsonUtil.STANDARD.writeValueAsBytes(baseMap)))
				.build();
		Call call = new OkHttpClient().newBuilder().build().newCall(request);
		String jsonStr = call.execute().body().string();
		Map<String, String> resultMap = JsonUtil.STANDARD.readValue(jsonStr, new TypeReference<Map<String, String>>() {
		});
		return String.format("余额：%1$s元，已发送：%2$s条", resultMap.get("balance"), resultMap.get("sended"));
	}

	public String timestamp() {
		return new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
	}

	public static void testSubmit() throws Exception {
		String timestamp = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
		String postURL = "http://api.yzh.drondea.com/json/submit";
		Map<String, Object> dataMap = new HashMap<>();
		String[] accountInfo = "EN0924203956JECS44,drondea".split(",");
		dataMap.put("enterprise_no", accountInfo[0]);
		dataMap.put("account", accountInfo[1]);
		dataMap.put("timestamp", timestamp);
		dataMap.put("sign", NotifyUtil.genSign(accountInfo[0], accountInfo[1], dataMap.get("timestamp").toString(),
				"ACA0016D4S4B0133"));
		dataMap.put("phones", "15081186864");
		dataMap.put("content", "您的短信验证码:3232321【庄点科技】");
		dataMap.put("subcode", StringUtils.defaultIfEmpty("", ""));
		HttpClient httpClient = new HttpClient();
		httpClient.setCharset("utf-8");
		String content = JsonUtil.STANDARD.writeValueAsString(dataMap);
		String result = httpClient.post(postURL, content, HttpClient.MEDIA_TYPE_JSON).string();
		System.out.println(result);
	}

	public static void main(String[] args) throws Exception {
		// testBalance();
		testSubmit();
//		String json = "{\"result\":\"0\",\"desc\":\"成功\",\"sign\":\"688F009207F6D83F817B8C52DBCE455B\",\"timestamp\":\"20190925024648177\",\"report\":[{\"result\":\"success\",\"phone\":\"15081186864\",\"netway_code\":\"10690\",\"msgid\":\"1FE1816D649A5279\",\"seq\":\"1\"}]}\r\n";
//		DrondeaReport drondeaReport = JsonUtil.NON_NULL.readValue(json, DrondeaReport.class);
//		List<Map<String, String>> reports = drondeaReport.getReport();
//		reports.forEach(item -> {
//			Report entity = new Report();
//			entity.setChannel_No("12121");
//			String msgId = item.get("msgid");
//			entity.setChannel_Sub_Msg_No(msgId);
//			entity.setPhone_No(item.get("phone"));
//			String status = item.get("result");
//			entity.setNative_Status(status);
//			entity.setSP_Number(item.get("netway_code"));
//			String seq = item.get("seq");
//			if (StringUtils.isNotBlank(seq)) {
//				entity.setSequence(seq);
//			}
//			if (status.trim().equalsIgnoreCase("Success")) {
//				entity.setStatus_Code(ReportStatus.SUCCESS.toString());
//			} else {
//				entity.setStatus_Code(ReportStatus.FAILD.toString());
//			}
//			entity.setStatus_Date(new Date());
//			MQUtil.notifySaveReport(entity);
//		});
		// System.out.println(report.getReport().get(0).get("result").toString());
	}

}
