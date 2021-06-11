package com.hero.wireless.notify;

public enum Constants {
	ACCOUNT, PASSWORD, MESSAGE, PHONES, CONTENT, SUBCODE, SENDTIME, MSGID, SOURCE_TYEP_CODE, CLIENT_IP, RESULT, DESC,
	REPORTS, REPORT, SIGN, COUNTRYCODE, CHARSET;
	public String toString() {
		return this.name().toLowerCase();
	}
}
