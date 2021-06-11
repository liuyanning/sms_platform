package com.hero.wireless.notify;

import java.io.Serializable;

import com.hero.wireless.json.JsonUtil;

public class JsonLianChengHeZhongMMSReport implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5544280615141671317L;

	private String submitSequenceNumber;

	private String phone;

	private String state;

	private String userNumber;

	private String errorCode;

	public JsonLianChengHeZhongMMSReport(String submitSequenceNumber, String phone, String state, String userNumber, String errorCode) {
		super();
		this.submitSequenceNumber = submitSequenceNumber;
		this.phone = phone;
		this.state = state;
		this.userNumber = userNumber;
		this.errorCode = errorCode;
	}

	public JsonLianChengHeZhongMMSReport() {
		super();
	}

	public static JsonLianChengHeZhongMMSReport parseJson(String json) throws Exception {
		return JsonUtil.NON_NULL.readValue(json, JsonLianChengHeZhongMMSReport.class);
	}

	public String getSubmitSequenceNumber() {
		return submitSequenceNumber;
	}

	public String getPhone() {
		return phone;
	}

	public String getState() {
		return state;
	}

	public String getUserNumber() {
		return userNumber;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setSubmitSequenceNumber(String submitSequenceNumber) {
		this.submitSequenceNumber = submitSequenceNumber;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void setUserNumber(String userNumber) {
		this.userNumber = userNumber;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public static void main(String[] args) throws Exception {
		JsonLianChengHeZhongMMSReport b = JsonUtil.NON_NULL.readValue("{\"submitSequenceNumber\":\"2070143\",\"phone\":\"155****2925\",\"state\":\"1\",\"userNumber\":\"155****2925\"}", JsonLianChengHeZhongMMSReport.class);
		System.out.println(JsonUtil.NON_NULL.writeValueAsString(b));
	}
}
