package com.hero.wireless.notify;

import com.hero.wireless.json.JsonUtil;

import java.io.Serializable;

public class MasMMSReport implements Serializable {

    private static final long serialVersionUID = 1L;

	private String taskId;

	private String mobile;

	private String status;//必填参数，发送结果 4 成功 5 失败

	private String errorCode;//非必填参数，错误编码

    public MasMMSReport(String taskId, String mobile, String status, String errorCode) {
        this.taskId = taskId;
        this.mobile = mobile;
        this.status = status;
        this.errorCode = errorCode;
    }

    public MasMMSReport() {
		super();
	}

	public static MasMMSReport parseJson(String json) throws Exception {
		return JsonUtil.NON_NULL.readValue(json, MasMMSReport.class);
	}

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}
