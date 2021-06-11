package com.hero.wireless.notify;

import java.io.Serializable;

public class ChuZhongSmsDeliverRequestMessage implements Serializable {

    private static final long serialVersionUID = 1L;

	private String vType;
	private String vCaller;
	private String vCallee;
	private String vSessionId;
	private String vStatus;
	private String vLastStatus;
	private String vCount;
	private String vNum;
	private String  vContent;
	
	public String getvType() {
		return vType;
	}
	public String getvCaller() {
		return vCaller;
	}
	public String getvCallee() {
		return vCallee;
	}
	public String getvSessionId() {
		return vSessionId;
	}
	public String getvStatus() {
		return vStatus;
	}
	public String getvLastStatus() {
		return vLastStatus;
	}
	public String getvCount() {
		return vCount;
	}
	public String getvNum() {
		return vNum;
	}
	public void setvType(String vType) {
		this.vType = vType;
	}
	public void setvCaller(String vCaller) {
		this.vCaller = vCaller;
	}
	public void setvCallee(String vCallee) {
		this.vCallee = vCallee;
	}
	public void setvSessionId(String vSessionId) {
		this.vSessionId = vSessionId;
	}
	public void setvStatus(String vStatus) {
		this.vStatus = vStatus;
	}
	public void setvLastStatus(String vLastStatus) {
		this.vLastStatus = vLastStatus;
	}
	public void setvCount(String vCount) {
		this.vCount = vCount;
	}
	public void setvNum(String vNum) {
		this.vNum = vNum;
	}
	
	public String getvContent() {
		return vContent;
	}
	public void setvContent(String vContent) {
		this.vContent = vContent;
	}
	@Override
	public String toString() {
		return "ChuZhongSmsDeliverRequestMessage [vType=" + vType + ", vCaller=" + vCaller
				+ ", vCallee=" + vCallee + ", vSessionId=" + vSessionId
				+ ", vStatus=" + vStatus + ", vLastStatus=" + vLastStatus
				+ ", vCount=" + vCount + ", vNum=" + vNum + ", vContent="
				+ vContent + "]";
	}
	
}
