package com.hero.wireless.notify;

import com.hero.wireless.web.entity.business.EnterpriseUser;

/**
 * 
 * 
 * @author volcano
 * @date 2019年9月21日上午6:24:03
 * @version V1.0
 */
public class JsonSubmitResponse extends JsonResponse {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3477523140747515343L;
	private String msgid;

	public JsonSubmitResponse(String result, EnterpriseUser user, String msgid) {
		super(result, user);
		this.msgid = msgid;
	}
	
	public JsonSubmitResponse(){
		super();
	}
	
	public String getMsgid() {
		return msgid;
	}

	public void setMsgid(String msgid) {
		this.msgid = msgid;
	}

}
