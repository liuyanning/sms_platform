package com.hero.wireless.notify;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.drondea.wireless.util.SuperLogger;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.hero.wireless.json.JsonUtil;
import com.hero.wireless.web.entity.business.EnterpriseUser;

/**
 * 
 * 
 * @author volcano
 * @date 2019年9月21日上午6:16:12
 * @version V1.0
 */
public class JsonResponse implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2112700629768616813L;
	private String result;
	private String desc;
	private String sign;
	private String timestamp;

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

	public JsonResponse(String result, EnterpriseUser user) {
		super();
		this.result = result;
		this.desc = "成功";
		this.timestamp = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
		this.sign = NotifyUtil.genSign(user, timestamp);
	}

	public JsonResponse(String result, String desc) {
		this.result = result;
		this.setDesc(desc);
	}

	public JsonResponse() {
		// TODO Auto-generated constructor stub
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String toJson() {
		try {
			return JsonUtil.NON_NULL.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			SuperLogger.error(e.getMessage(), e);
		}
		return "";
	}
}
