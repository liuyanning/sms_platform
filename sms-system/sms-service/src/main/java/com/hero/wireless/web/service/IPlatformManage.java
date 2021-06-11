package com.hero.wireless.web.service;

import com.hero.wireless.web.entity.business.Platform;
import java.util.List;

/**
 * 平台管理
 * 
 * @author Administrator
 *
 */
public interface IPlatformManage {
	/**
	 * 查询平台列表
	 * 
	 * @param platform
	 * @return
	 */
	List<Platform> queryPlatformList(Platform platform);

	/**
	 * 增加平台
	 * 
	 * @param platform
	 * @return
	 */
	int addPlatform(Platform platform);

	/**
	 * 修改企业
	 *
	 * @param platform
	 */
	int updateByPrimaryKeySelective(Platform platform);


	Platform queryPlatformById(Integer id);
}
