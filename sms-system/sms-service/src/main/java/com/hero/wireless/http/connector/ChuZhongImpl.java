package com.hero.wireless.http.connector;

import com.drondea.wireless.util.DateTime;
import com.drondea.wireless.util.ServiceException;
import com.drondea.wireless.util.SuperLogger;
import com.hero.wireless.enums.Charset;
import com.hero.wireless.enums.ReportStatus;
import com.hero.wireless.enums.SignaturePosition;
import com.hero.wireless.enums.SubmitStatus;
import com.hero.wireless.http.AbstractHttp;
import com.hero.wireless.notify.ChuZhongSmsDeliverRequestMessage;
import com.hero.wireless.okhttp.HttpClient;
import com.hero.wireless.web.config.DatabaseCache;
import com.hero.wireless.web.entity.business.Channel;
import com.hero.wireless.web.entity.send.Inbox;
import com.hero.wireless.web.entity.send.Report;
import com.hero.wireless.web.entity.send.Submit;
import com.hero.wireless.web.util.QueueUtil;
import com.hero.wireless.web.util.SMSUtil;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 
* Title: ChuZhongImpl  
* Description:触众短信接口实现类
* @author yjb
* @date 2020-04-30
 */
public class ChuZhongImpl extends AbstractHttp {
	@Override
	public void mo(Channel channel) {
	}

	@Override
	public Object mo(ChannelReport channelReport) throws Exception {
		ChuZhongSmsDeliverRequestMessage base = (ChuZhongSmsDeliverRequestMessage)channelReport.getData();
	        Inbox inbox = new Inbox();
	        inbox.setChannel_No(channelReport.getChannelNo());
	        inbox.setContent(base.getvContent());
	        inbox.setSP_Number(base.getvCallee());
	        inbox.setPhone_No(base.getvCaller());
	        inbox.setCreate_Date(new Date());
	        // 发送mq消息
	        QueueUtil.saveMo(inbox);
	        return "success";
	}
	
	@Override
	public void report(Channel channel) {
	}

	@Override
	public Object report(ChannelReport channelReport) throws Exception {
		ChuZhongSmsDeliverRequestMessage report = (ChuZhongSmsDeliverRequestMessage) channelReport.getData();
		Report entity = new Report();
		entity.setChannel_No(channelReport.getChannelNo());
		String msgId = report.getvSessionId();
		entity.setChannel_Msg_Id(msgId);
		entity.setPhone_No(report.getvCallee());
		String status = report.getvStatus();
		entity.setNative_Status(status);
		entity.setSP_Number(report.getvCaller());
		String seq = report.getvNum();
		if (StringUtils.isNotBlank(seq)) {
			entity.setSequence(Integer.valueOf(seq));
		}
		if (status.trim().equalsIgnoreCase("1")) {//短信状态（1：发送成功 2：提交成功 3：提交失败 4:发送失败）
			entity.setStatus_Code(ReportStatus.SUCCESS.toString());
		} else {
			entity.setStatus_Code(ReportStatus.FAILD.toString());
		}
		entity.setStatus_Date(new Date());
		saveReport(entity);
		return "success";
	}
	
	@Override
	public SubmitStatus submit(Submit submit) throws Exception {
		//这个接口每次只允许提交一个手机号码，接口文档接受手机号字段长度定义的是21位长度  所以这里判断一下
		if(submit.getPhone_No().length() > 21){
			SuperLogger.error("触众接口提交短信，手机号超长：" + submit.getPhone_No().length());
			return SubmitStatus.FAILD;
		}
		Channel channel = DatabaseCache.getChannelByNo(submit.getChannel_No());
		String requestStr = getRequestData(submit, channel);
		String postURL = submitUrl(channel);
		HttpClient httpClient = new HttpClient();
		httpClient.setCharset(Charset.UTF8.toString());
		Map<String, String> param = new HashMap<String, String>();
		param.put("postData", requestStr);
		String info = httpClient.post(postURL, param).string();
		SuperLogger.debug(info);
		if (StringUtils.isEmpty(info)) {
			return SubmitStatus.FAILD;
		}
		Document resultDocument = DocumentHelper.parseText(info);
		Element rootElement = resultDocument.getRootElement();
		Element head = (Element) rootElement.elements().get(0);
		String result = head.elementText("Result");
		String resultDesc = head.elementText("ResultDesc");
		if (result.equals("0")) {
			String sessionid = head.elementText("Sessionid");
			submit.setChannel_Msg_Id(sessionid);
			submit.setSubmit_Description("成功");
			return SubmitStatus.SUCCESS;
		} else {
			submit.setSubmit_Description(resultDesc);
			return SubmitStatus.FAILD;
		}
	}

	private String getRequestData(Submit submit, Channel channel) throws NoSuchAlgorithmException {
		// Spid,Appid
		String[] accountInfo = channel.getAccount().split(",");
		//在通道配置的时候 extInfo1 = Ims,Key,TempletId
		String[] extInfo1 = extInfo1(channel).split(",");
		Document doc = DocumentHelper.createDocument();
		String timestamp = DateTime.getString(new Date(),DateTime.Y_M_D_H_M_S_2);
		Element returnsmsElement = doc.addElement("Request");
		Element headElement = returnsmsElement.addElement("Head");
		headElement.addElement("MethodName").setText("SmsSendByTemplet");
		headElement.addElement("Spid").setText(accountInfo[0]);
		headElement.addElement("Appid").setText(accountInfo[1]);
		headElement.addElement("Passwd").setText(sha1Encrypt(channel.getPassword()));
		headElement.addElement("Timestamp").setText(timestamp);
		headElement.addElement("Authenticator").setText(sha1Encrypt(timestamp+"SmsSendByTemplet"+accountInfo[0]+channel.getPassword()));
		Element bodyElement = returnsmsElement.addElement("Body");
		bodyElement.addElement("Ims").setText(extInfo1[0]);
		bodyElement.addElement("Key").setText(extInfo1[1]);
		bodyElement.addElement("CalleeNbr").setText(submit.getPhone_No());
		bodyElement.addElement("Sign").setText(getSign(submit.getContent()));
		boolean SignType = StringUtils.defaultIfEmpty(channel.getSignature_Position(), SignaturePosition.PREFIX.toString())
		.equalsIgnoreCase(SignaturePosition.PREFIX.toString());
		bodyElement.addElement("SignType").setText(SignType?"1":"0");
		bodyElement.addElement("TempletId").setText(extInfo1[2]);
		bodyElement.addElement("Value1").setText(SMSUtil.clearSignature(submit.getContent()));
		return doc.asXML();
	}
	
	private String getSign(String content) {
		String signature = SMSUtil.getSignature(content);
		if(StringUtils.isEmpty(signature)){
			throw new ServiceException("短信内容签名为空");
		}
		return signature.replaceAll("【", "").replaceAll("】", "");
	}

	@Override
	public String balance(Channel channel) throws Exception {
		return null;
	}
	
	// 使用我们的工具类SecretUtil.SHA1   请求接口报错 <ResultDesc>Spid or Passwd is error!</ResultDesc>   
	// 使用上游提供的这个方法不报错通过
	public static String sha1Encrypt(String Passwd) throws NoSuchAlgorithmException {
		MessageDigest alg = MessageDigest.getInstance("SHA-1");
		alg.update(Passwd.getBytes());
		byte[] bts = alg.digest();
		String result = "";
		String tmp = "";
		for (int i = 0; i < bts.length; i++) {
			tmp = (Integer.toHexString(bts[i] & 0xFF));
			if (tmp.length() == 1)
				result += "0";
			result += tmp;
		}
		return result;
	}
	
	
	public static void main(String[] args) {
		String internationalCodes = "86";
		internationalCodes = internationalCodes.replaceAll("\\+", "\\\\+");
		System.out.println(internationalCodes);
		String moblie = "+86135,+86136";
		System.out.println(moblie.replaceAll(internationalCodes, ""));
	}
}
