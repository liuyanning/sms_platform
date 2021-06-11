package com.hero.wireless.notify;

import com.hero.wireless.json.JsonUtil;

import java.io.Serializable;
import java.util.List;

public class AliMo implements Serializable {

    private static final long serialVersionUID = -1710883604215965001L;

    private List<AliMo> aliMoList;

    private String phone_number;

	private String send_time;

	private String content;

	private String sign_name;

    private String dest_code;

    private int sequence_id;

    public AliMo(String phone_number, String send_time, String content, String sign_name, String dest_code, int sequence_id) {
        this.phone_number = phone_number;
        this.send_time = send_time;
        this.content = content;
        this.sign_name = sign_name;
        this.dest_code = dest_code;
        this.sequence_id = sequence_id;
    }

    public AliMo() {
		super();
	}

	public static AliMo parseJson(String json) throws Exception {
		return JsonUtil.NON_NULL.readValue(json, AliMo.class);
	}

    public List<AliMo> getAliMoList() {
        return aliMoList;
    }

    public void setAliMoList(List<AliMo> aliMoList) {
        this.aliMoList = aliMoList;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSign_name() {
        return sign_name;
    }

    public void setSign_name(String sign_name) {
        this.sign_name = sign_name;
    }

    public String getDest_code() {
        return dest_code;
    }

    public void setDest_code(String dest_code) {
        this.dest_code = dest_code;
    }

    public int getSequence_id() {
        return sequence_id;
    }

    public void setSequence_id(int sequence_id) {
        this.sequence_id = sequence_id;
    }
}
