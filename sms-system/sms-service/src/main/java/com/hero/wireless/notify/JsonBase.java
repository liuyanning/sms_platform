package com.hero.wireless.notify;

import java.io.Serializable;

import com.hero.wireless.json.JsonUtil;

public class JsonBase implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8547674607084980692L;
	/*** 特殊命名，兼容老版api接口 **/
	private String enterprise_no;

	private String account;

	private String sign;

	private String clientIp;

	private String timestamp;

	public JsonBase(String enterprise_no, String account, String timestamp) {
		super();
		this.enterprise_no = enterprise_no;
		this.account = account;
		this.timestamp = timestamp;
	}

	public JsonBase() {
		super();
	}

	public static JsonBase parseJson(String json) throws Exception {
		return JsonUtil.NON_NULL.readValue(json, JsonBase.class);
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getClientIp() {
		return clientIp;
	}

	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}

	public String getEnterprise_no() {
		return enterprise_no;
	}

	public void setEnterprise_no(String enterprise_no) {
		this.enterprise_no = enterprise_no;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public static void main(String[] args) throws Exception {
		JsonBase b = JsonUtil.NON_NULL.readValue("{\"account\":\"u\",\"sign\":\"p\"}", JsonBase.class);
		System.out.println(JsonUtil.NON_NULL.writeValueAsString(b));
	}
}
