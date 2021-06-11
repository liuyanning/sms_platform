package com.hero.wireless.notify;

import com.hero.wireless.json.JsonUtil;

/**
 * 
* Title: MaiYuanJsonBase  
* Description:
* @author yjb
* @date 2020-04-07
 */
public class MaiYuanJsonBase extends JsonBase {


	/**
	 * 
	 */
	private static final long serialVersionUID = -4442814204852930994L;
	private String userid;
	private String action;
	private String statusNum;
	private String password;
	
	public static MaiYuanJsonBase parseJson(String json) throws Exception {
		return JsonUtil.NON_NULL.readValue(json, MaiYuanJsonBase.class);
	}

	public String getUserid() {
		return userid;
	}

	public String getAction() {
		return action;
	}

	public String getStatusNum() {
		return statusNum;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public void setStatusNum(String statusNum) {
		this.statusNum = statusNum;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public static void main(String[] args) throws Exception {
		MaiYuanJsonBase b = JsonUtil.NON_NULL.readValue("{\"account\":\"u\",\"sign\":\"p\"}", MaiYuanJsonBase.class);
		System.out.println(JsonUtil.NON_NULL.writeValueAsString(b));
	}
}
