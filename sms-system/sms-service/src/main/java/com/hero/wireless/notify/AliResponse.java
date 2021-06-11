package com.hero.wireless.notify;

import com.drondea.wireless.util.SuperLogger;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.hero.wireless.json.JsonUtil;

import java.io.Serializable;

public class AliResponse implements Serializable {


	/**
	 *
	 */
	private static final long serialVersionUID = 3438038867535507781L;

	private int code;
	private String msg;

	public AliResponse(int code, String msg) {
		super();
		this.code = code;
		this.msg = msg;
	}
	public AliResponse() {
		super();
	}
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String toJson() {
		try {
			return JsonUtil.NON_NULL.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			SuperLogger.error(e.getMessage(), e);
		}
		return "";
	}

	public static void main(String[] args) {
		AliResponse jsonAliReportResponse = new AliResponse(0, "接收成功");
		System.out.println(jsonAliReportResponse.toJson());
	}
}
