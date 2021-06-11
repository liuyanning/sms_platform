package com.hero.wireless.notify;

import com.hero.wireless.json.JsonUtil;

import java.io.Serializable;

public class MasMMSMo implements Serializable {

    private static final long serialVersionUID = 1L;

	private String srcId;

	private String mobile;

	private String moMsg;

	private Long moDate;

    public MasMMSMo(String srcId, String mobile, String moMsg, Long moDate) {
        this.srcId = srcId;
        this.mobile = mobile;
        this.moMsg = moMsg;
        this.moDate = moDate;
    }

    public MasMMSMo() {
		super();
	}

	public static MasMMSMo parseJson(String json) throws Exception {
		return JsonUtil.NON_NULL.readValue(json, MasMMSMo.class);
	}

    public String getSrcId() {
        return srcId;
    }

    public void setSrcId(String srcId) {
        this.srcId = srcId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMoMsg() {
        return moMsg;
    }

    public void setMoMsg(String moMsg) {
        this.moMsg = moMsg;
    }

    public Long getMoDate() {
        return moDate;
    }

    public void setMoDate(Long moDate) {
        this.moDate = moDate;
    }
}
