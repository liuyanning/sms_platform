package com.hero.wireless.http.bean;

public class LianChengHeZhongApprovalPendingMMS {
	
	private String channelNo;
	//发送内容参数，json 格式
	private String param;
	//标题
	private String title;
	//是否开启退订(1 开启,其他不开启 注：如传则不可为空)
	private String openTd;
	//数字短信类型：2 ： 生活
	private String type;

	public String getParam() {
		return param;
	}

	public String getTitle() {
		return title;
	}

	public String getOpenTd() {
		return openTd;
	}

	public String getType() {
		return type;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setOpenTd(String openTd) {
		this.openTd = openTd;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getChannelNo() {
		return channelNo;
	}

	public void setChannelNo(String channelNo) {
		this.channelNo = channelNo;
	}
	
}
