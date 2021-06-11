package com.hero.wireless.notify;

import com.hero.wireless.json.JsonUtil;

/**
 * 
* Title: MaiYuanJsonSubmit  
* Description:
* @author yjb
* @date 2020-04-06
 */
public class MaiYuanJsonSubmit extends JsonSubmit {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8365837043576203080L;
	private String userid;
	private String mobile;
	private String action;
	private String extno;
	private String sendTime;

	public String getUserid() {
		return userid;
	}

	public String getMobile() {
		return mobile;
	}

	public String getAction() {
		return action;
	}

	public String getExtno() {
		return extno;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public void setExtno(String extno) {
		this.extno = extno;
	}

	public String getSendTime() {
		return sendTime;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}

	public static MaiYuanJsonSubmit parseJson(String json) throws Exception {
		return JsonUtil.NON_NULL.readValue(json, MaiYuanJsonSubmit.class);
	}
}
