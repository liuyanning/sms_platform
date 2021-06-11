package com.hero.wireless.sort;

import com.hero.wireless.web.entity.send.Input;

/**
 * 分拣信息业务类
 * 
 * @author Administrator
 * 
 */
public interface ISortSMSService {
	/**
	 * 分拣信息
	 */
	void sort(Input input) throws Exception;
}
