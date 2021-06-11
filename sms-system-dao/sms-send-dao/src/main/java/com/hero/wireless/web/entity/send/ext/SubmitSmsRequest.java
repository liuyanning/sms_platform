package com.hero.wireless.web.entity.send.ext;

public class SubmitSmsRequest {
	/**账号Enterprise_User_Info表的User_Name*/
	private String account;
	/**MD5后的密码Enterprise_User_Info表的Password*/
	private String md5Password;
	/**电话号码,多个时以","相隔*/
	private String phones;
	/**短信内容*/
	private String content;
	/**提交短号*/
	private String subcode;
	/**提交时间*/
	private String sendtime;
	/**下游提交uuid*/
	private String uuid;

	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getMd5Password() {
		return md5Password;
	}
	public void setMd5Password(String md5Password) {
		this.md5Password = md5Password;
	}
	public String getPhones() {
		return phones;
	}
	public void setPhones(String phones) {
		this.phones = phones;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getSubcode() {
		return subcode;
	}
	public void setSubcode(String subcode) {
		this.subcode = subcode;
	}
	public String getSendtime() {
		return sendtime;
	}
	public void setSendtime(String sendtime) {
		this.sendtime = sendtime;
	}
	
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	@Override
	public String toString() {
		return "SubmitSmsRequest [account=" + account + ", md5Password="
				+ md5Password + ", phones=" + phones + ", content=" + content
				+ ", subcode=" + subcode + ", sendtime=" + sendtime + ", uuid="
				+ uuid + "]";
	}
	
}
