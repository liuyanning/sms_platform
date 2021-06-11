package com.drondea.wireless.util;

import java.text.MessageFormat;

import com.alibaba.fastjson.JSONObject;
import com.drondea.wireless.config.ResultStatus;

/**
 * 
 * 
 * ServiceException
 * 
 * @author
 * @createTime 2016年9月14日 下午6:13:37
 * @version 1.0.0
 * 
 */
public class ServiceException extends RuntimeException {
	private static final long serialVersionUID = -4806743244492638606L;
	private String errorCode;
	private String message;

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public ServiceException(String errorCode, String message) {
		this.errorCode = errorCode;
		this.message = message;
	}

	public ServiceException(String message) {
		this.errorCode = message;
		this.message = message;
	}

	public ServiceException(ResultStatus resultStatus) {
		this.errorCode = resultStatus.getCode();
		this.message = resultStatus.getMessage();
	}
	
	public ServiceException(ResultStatus resultStatus, String content) {
		this.errorCode = resultStatus.getCode();
		this.message = MessageFormat.format(resultStatus.getMessage(), content);
	}

	public String toJson() {
		JSONObject json = new JSONObject();
		json.put("code", getErrorCode());
		json.put("message", getMessage());
		return json.toString();
	}

}
