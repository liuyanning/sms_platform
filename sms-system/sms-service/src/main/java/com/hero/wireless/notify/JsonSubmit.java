package com.hero.wireless.notify;

import com.hero.wireless.json.JsonUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 版本2
* 
* @author volcano
* @date 2019年9月19日上午8:27:09
* @version V1.0
 */
public class JsonSubmit implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6951209340862325413L;
	/*** 特殊命名，兼容老版api接口 **/
	private String enterprise_no;
	private String account;
	private String password;
	private String clientIp;
	private String protocolType;
	private String messageType;
	private String phones;
	private String content;
	private String subcode;
	private String sendtime;
	private String sign;
	private String timestamp;
	private String countrycode;
	private String charset;
	/**
	 * 下游自定义Id
	 */
	private String customId;

	private List<JsonMessage> messageList = new ArrayList<JsonMessage>();

	public String getClientIp() {
		return clientIp;
	}

	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}

	public String getProtocolType() {
		return protocolType;
	}

	public void setProtocolType(String protocolType) {
		this.protocolType = protocolType;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhones() {
		return phones;
	}

	public void setPhones(String phones) {
		this.phones = phones;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSubcode() {
		return subcode;
	}

	public void setSubcode(String subcode) {
		this.subcode = subcode;
	}

	public String getSendtime() {
		return sendtime;
	}

	public void setSendtime(String sendtime) {
		this.sendtime = sendtime;
	}

	public String getEnterprise_no() {
		return enterprise_no;
	}

	public void setEnterprise_no(String enterprise_no) {
		this.enterprise_no = enterprise_no;
	}

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

    public String getCountrycode() {
        return countrycode;
    }

    public void setCountrycode(String countrycode) {
        this.countrycode = countrycode;
    }

    public List<JsonMessage> getMessageList() {
		return messageList;
	}

	public void setMessageList(List<JsonMessage> messageList) {
		this.messageList = messageList;
	}

	public static JsonSubmit parseJson(String json) throws Exception {
		return JsonUtil.NON_NULL.readValue(json, JsonSubmit.class);
	}

	public String getCustomId() {
		return customId;
	}

	public void setCustomId(String customId) {
		this.customId = customId;
	}
}
