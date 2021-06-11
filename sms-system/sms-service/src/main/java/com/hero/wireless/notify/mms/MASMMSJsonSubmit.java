package com.hero.wireless.notify.mms;

import java.io.IOException;

import com.drondea.wireless.util.SuperLogger;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.hero.wireless.json.JsonUtil;
import com.hero.wireless.notify.JsonSubmit;

/**
 * 
* Title: MASMMSJsonSubmit  
* Description:
* @author yjb
* @date 2020-04-17
 */
public class MASMMSJsonSubmit extends JsonSubmit {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2636767632221474757L;
	/**系统分配或商家定义  是否必填：是*/
	private String appKey;
	/**模版编号   是否必填：是*/
	private String modeId;
	private String[] mobiles;
	/**发送使用的码号，需要在平台中提前分配   是否必填：否*/
	private String vsmsCode;
	/**发送状态通知地址   是否必填：否*/
	private String notifyUrl;
	/**发送时间戳   是否必填：否*/
	private String sendTime;
	/**必填参数，当前时间戳，如：1488786467    是否必填：是*/
	private String ts;
	/**必填参数，签名    是否必填：是*/
	private String sign;
	

	public String getAppKey() {
		return appKey;
	}


	public String getModeId() {
		return modeId;
	}

	public String[] getMobiles() {
		return mobiles;
	}


	public void setMobiles(String[] mobiles) {
		this.mobiles = mobiles;
	}


	public String getVsmsCode() {
		return vsmsCode;
	}


	public String getNotifyUrl() {
		return notifyUrl;
	}


	public String getSendTime() {
		return sendTime;
	}


	public String getTs() {
		return ts;
	}


	public String getSign() {
		return sign;
	}


	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}


	public void setModeId(String modeId) {
		this.modeId = modeId;
	}

	public void setVsmsCode(String vsmsCode) {
		this.vsmsCode = vsmsCode;
	}


	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}


	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}


	public void setTs(String ts) {
		this.ts = ts;
	}


	public void setSign(String sign) {
		this.sign = sign;
	}

	public static MASMMSJsonSubmit parseJson(String json) {
		try {
			return JsonUtil.NON_NULL.readValue(json, MASMMSJsonSubmit.class);
		} catch (JsonParseException e) {
			SuperLogger.error(e.getMessage());
		} catch (JsonMappingException e) {
			SuperLogger.error(e.getMessage());
		} catch (IOException e) {
			SuperLogger.error(e.getMessage());
		}
		return new MASMMSJsonSubmit();
	}
	
}
