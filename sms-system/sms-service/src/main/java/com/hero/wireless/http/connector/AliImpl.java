package com.hero.wireless.http.connector;

import com.drondea.wireless.util.SuperLogger;
import com.fasterxml.jackson.core.type.TypeReference;
import com.hero.wireless.enums.Charset;
import com.hero.wireless.enums.ReportStatus;
import com.hero.wireless.enums.SubmitStatus;
import com.hero.wireless.http.AbstractHttp;
import com.hero.wireless.json.JsonUtil;
import com.hero.wireless.notify.AliMo;
import com.hero.wireless.notify.AliReport;
import com.hero.wireless.okhttp.HttpClient;
import com.hero.wireless.web.config.DatabaseCache;
import com.hero.wireless.web.entity.business.Channel;
import com.hero.wireless.web.entity.business.Code;
import com.hero.wireless.web.entity.send.Inbox;
import com.hero.wireless.web.entity.send.Report;
import com.hero.wireless.web.entity.send.Submit;
import com.hero.wireless.web.util.QueueUtil;
import com.hero.wireless.web.util.SMSUtil;
import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 阿里短信接口
 *
 * @author lyh
 *
 */
public class AliImpl extends AbstractHttp {

	@Override
	public Object mo(ChannelReport channelReport) throws Exception {
		AliMo aliMo = (AliMo) channelReport.getData();

		List<AliMo> mos = aliMo.getAliMoList();
		mos.forEach(item -> {
			Inbox inbox = new Inbox();
			inbox.setChannel_No(channelReport.getChannelNo());
			inbox.setContent(item.getContent());
			inbox.setSP_Number(item.getDest_code());
			inbox.setPhone_No(item.getPhone_number());
			inbox.setCreate_Date(new Date());
			// 发送mq消息
			QueueUtil.saveMo(inbox);

		});
		return "success";
	}

	@Override
	public void report(Channel channel) {
	}

	@Override
	public Object report(ChannelReport channelReport) throws Exception {
		AliReport aliReport = (AliReport) channelReport.getData();

		List<AliReport> reports = aliReport.getAliReportList();
		reports.forEach(item -> {
			Report entity = new Report();
			entity.setChannel_No(channelReport.getChannelNo());
			String msgId = item.getBiz_id();
			entity.setChannel_Msg_Id(msgId);
			entity.setPhone_No(item.getPhone_number());
			String status = item.getErr_code();
			entity.setNative_Status(status);

			String seq = item.getSms_size();
			if (StringUtils.isNotBlank(seq)) {
				entity.setSequence(Integer.valueOf(seq));
			}
			if (item.isSuccess()) {
				entity.setStatus_Code(ReportStatus.SUCCESS.toString());
			} else {
				entity.setStatus_Code(ReportStatus.FAILD.toString());
			}
			entity.setStatus_Date(new Date());
			saveReport(entity);
		});
		return "success";
	}

	@Override
	public SubmitStatus submit(Submit submit) throws Exception {
		Channel channel = DatabaseCache.getChannelByNo(submit.getChannel_No());
		String url = submitUrl(channel);


		String accessKeyId = channel.getAccount();
		String accessSecret = channel.getPassword();

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		df.setTimeZone(new SimpleTimeZone(0, "GMT"));// 这里一定要设置GMT时区
		Map<String, String> paras = new HashMap<String, String>();

		//固定参数 签名方式：HMAC-SHA1
		paras.put("SignatureMethod", "HMAC-SHA1");
		paras.put("SignatureNonce", UUID.randomUUID().toString());
		paras.put("AccessKeyId", accessKeyId);
		//固定值 签名算法版本：1.0
		paras.put("SignatureVersion", "1.0");
		paras.put("Timestamp", df.format(new Date()));
		//返回参数格式（可选JSON/XML）
		paras.put("Format", "JSON");
		//API的版本，固定值
		paras.put("Version", "2017-05-25");
		//API支持的RegionID,固定值
		paras.put("RegionId", "cn-hangzhou");
		//API的命名，固定值
		paras.put("Action", "SendSms");
		//业务API参数
		paras.put("PhoneNumbers", submit.getPhone_No());
		//根据短信内容获取签名
		String signature = SMSUtil.getSignature(submit.getContent());
		if(StringUtils.isNoneEmpty(signature)){
			paras.put("SignName", signature.substring(1,signature.length()-1));
		}
		//去除签名，去头或者去尾
		String content = SMSUtil.clearSignature(submit.getContent());
		List<? extends Code> aliChannel_templateCode = DatabaseCache.getCodeListBySortCode("aliChannelNo_"+submit.getChannel_No());
		//设置模板和模板中的变量
		for (Code code : aliChannel_templateCode) {
			String template = StringUtils.trim(code.getValue()).replaceAll("\\$\\{.*?\\}", "(.*?)");
			Pattern p =Pattern.compile(template);
			Matcher m=p.matcher(content);
			//判断内容和某个模板匹配
			if(m.matches()){
				paras.put("TemplateCode", code.getCode());
				String patternStr = "\\$\\{(.*?)}";
				Pattern pattern = Pattern.compile(patternStr);
				Matcher m1 = pattern.matcher(code.getValue());
				int index = 1;
				Map<String,String> map = new HashMap<String,String>();
				while (m1.find()){
					map.put(m1.group(1), m.group(index));
				}
				//模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
				//request.setTemplateParam("{\"name\":\"Tom\", \"code\":\"123\"}");
				paras.put("TemplateParam", JsonUtil.writeValueAsString(map));
				break;
			}
		}
		//上行短信扩展码(无特殊需求用户请忽略此字段)
		paras.put("SmsUpExtendCode", SMSUtil.getSpNumber(channel, submit.getSub_Code()));
		paras.put("OutId", submit.getChannel_No());
		//参数KEY排序
		TreeMap<String, String> sortParas = new TreeMap<String, String>();
		sortParas.putAll(paras);
		//构造待签名的字符串
		Iterator<String> it = sortParas.keySet().iterator();
		StringBuffer sortQueryStringTmp = new StringBuffer();
		while (it.hasNext()) {
			String key = it.next();
			sortQueryStringTmp.append("&").append(specialUrlEncode(key)).append("=").append(specialUrlEncode(paras.get(key)));
		}
		String sortedQueryString = sortQueryStringTmp.substring(1);// 去除第一个多余的&符号
		StringBuffer stringToSign = new StringBuffer();
		stringToSign.append("POST").append("&");
		stringToSign.append(specialUrlEncode("/")).append("&");
		stringToSign.append(specialUrlEncode(sortedQueryString));
		String sign = sign(accessSecret + "&", stringToSign.toString());
		//签名最后也要做特殊URL编码
		signature = specialUrlEncode(sign);
		paras.put("Signature", signature);
		HttpClient httpClient = new HttpClient();
		httpClient.setCharset(Charset.UTF8.toString());
		String result = httpClient.post(url, paras).string();
		SuperLogger.debug(result);
		Map<String, String> resultMap = JsonUtil.STANDARD.readValue(result, new TypeReference<Map<String, String>>() {
		});
		String msgId = resultMap.get("BizId");
		String code = resultMap.get("Code");
		submit.setChannel_Msg_Id(msgId);
		submit.setSubmit_Description(resultMap.get("Message"));
		if (code.equals("OK")) {
			return SubmitStatus.SUCCESS;
		} else {
			return SubmitStatus.FAILD;
		}
	}

	@Override
	public String balance(Channel channel) {
		return null;
	}
	public static String specialUrlEncode(String value) throws Exception {
		return java.net.URLEncoder.encode(value, "UTF-8").replace("+", "%20").replace("*", "%2A").replace("%7E", "~");
	}
	public static String sign(String accessSecret, String stringToSign) throws Exception {
		javax.crypto.Mac mac = javax.crypto.Mac.getInstance("HmacSHA1");
		mac.init(new javax.crypto.spec.SecretKeySpec(accessSecret.getBytes("UTF-8"), "HmacSHA1"));
		byte[] signData = mac.doFinal(stringToSign.getBytes("UTF-8"));
		return new sun.misc.BASE64Encoder().encode(signData);
	}
}
