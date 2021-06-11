package com.hero.wireless.remote.filter;

import java.util.Map;

abstract class AbstractPhoneNoFilterService implements IPhoneNoFilterService {

	public String url;
	public String account;
	public String signKey;

	@Override
	public void init(Map<String, String> configMap) {
		this.url = configMap.get("url") == null ? "" : configMap.get("url");
		this.account = configMap.get("account") == null ? "" : configMap.get("account");
		this.signKey = configMap.get("pwd") == null ? "" : configMap.get("pwd");
	}
}
