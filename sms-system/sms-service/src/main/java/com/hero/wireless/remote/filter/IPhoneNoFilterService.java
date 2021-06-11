package com.hero.wireless.remote.filter;

import java.util.Map;

public interface IPhoneNoFilterService {
	
	/**
	 * 
	 * 初始化方法
	 * 
	 * @param configMap
	 */
	void init(Map<String, String> configMap);

	/**
	 * 
	 * 过滤手机号码是否是在黑名单池中
	 * 
	 * @param mobile
	 * @return
	 */
    boolean filterPhoneNo(String mobile);

}
