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
* Title: JsonLianChengHeZhongReportResponse  
* Description:
* @author yjb
* @date 2020-01-03
 */
public class JsonLianChengHeZhongReportResponse implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5457482289382523347L;
	
	private String result;
	private String sequenceNumber;

	public JsonLianChengHeZhongReportResponse(String result, String sequenceNumber) {
		super();
		this.result = result;
		this.sequenceNumber = sequenceNumber;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getSequenceNumber() {
		return sequenceNumber;
	}

	public void setSequenceNumber(String sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
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
