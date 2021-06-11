package com.hero.wireless.notify;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hero.wireless.json.JsonUtil;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

public class AliReport implements Serializable {



	/**
	 *
	 */
	private static final long serialVersionUID = 4181210931897887201L;

	private List<AliReport> aliReportList;

	private String phone_number;

	private String send_time;

	private String report_time;

	private boolean success;

	private String err_code;

	private String err_msg;

	private String sms_size;

	private String biz_id;

	private String out_id;

	public AliReport(String phone_number, String send_time, String report_time, boolean success, String err_code, String err_msg, String sms_size, String biz_id, String out_id) {
		super();
		this.phone_number = phone_number;
		this.send_time = send_time;
		this.report_time = report_time;
		this.success = success;
		this.err_code = err_code;
		this.err_msg = err_msg;
		this.sms_size = sms_size;
		this.biz_id = biz_id;
		this.out_id = out_id;
	}

	public AliReport() {
		super();
	}

	public static AliReport parseJson(String json) throws Exception {
		return JsonUtil.NON_NULL.readValue(json, AliReport.class);
	}

	public List<AliReport> getAliReportList() {
		return aliReportList;
	}

	public void setAliReportList(List<AliReport> aliReportList) {
		this.aliReportList = aliReportList;
	}

	public String getPhone_number() {
		return phone_number;
	}

	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}

	public String getSend_time() {
		return send_time;
	}

	public void setSend_time(String send_time) {
		this.send_time = send_time;
	}

	public String getReport_time() {
		return report_time;
	}

	public void setReport_time(String report_time) {
		this.report_time = report_time;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getErr_code() {
		return err_code;
	}

	public void setErr_code(String err_code) {
		this.err_code = err_code;
	}

	public String getErr_msg() {
		return err_msg;
	}

	public void setErr_msg(String err_msg) {
		this.err_msg = err_msg;
	}

	public String getSms_size() {
		return sms_size;
	}

	public void setSms_size(String sms_size) {
		this.sms_size = sms_size;
	}

	public String getBiz_id() {
		return biz_id;
	}

	public void setBiz_id(String biz_id) {
		this.biz_id = biz_id;
	}

	public String getOut_id() {
		return out_id;
	}

	public void setOut_id(String out_id) {
		this.out_id = out_id;
	}

	public static void main(String[] args) throws IOException {
		String jsonArray = "[\n" +
				"  {\n" +
				"    \"phone_number\" : \"13900000001\",\n" +
				"    \"send_time\" : \"2017-01-01 00:00:00\",\n" +
				"    \"report_time\" : \"2017-01-01 00:00:00\",\n" +
				"    \"success\" : true,\n" +
				"    \"err_code\" : \"DELIVERED\",\n" +
				"    \"err_msg\" : \"用户接收成功\",\n" +
				"    \"sms_size\" : \"1\",\n" +
				"    \"biz_id\" : \"12345\",\n" +
				"    \"out_id\" : \"67890\"\n" +
				"  },\n" +
				"  {\n" +
				"    \"phone_number\" : \"13900000002\",\n" +
				"    \"send_time\" : \"2017-01-01 00:00:00\",\n" +
				"    \"report_time\" : \"2017-01-01 00:00:00\",\n" +
				"    \"success\" : true,\n" +
				"    \"err_code\" : \"DELIVERED\",\n" +
				"    \"err_msg\" : \"用户接ddd收成功\",\n" +
				"    \"sms_size\" : \"2\",\n" +
				"    \"biz_id\" : \"ddd\",\n" +
				"    \"out_id\" : \"eee\"\n" +
				"  }\n" +
				"]";
		ObjectMapper mapper = new ObjectMapper();
		List<AliReport> aliReportList = mapper.readValue(jsonArray,new TypeReference<List<AliReport>>() { });
		AliReport base = new AliReport();
		base.setAliReportList(aliReportList);
		System.out.printf(base.getAliReportList().toString());
	}
}
