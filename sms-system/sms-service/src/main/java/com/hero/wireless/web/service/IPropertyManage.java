package com.hero.wireless.web.service;

import com.hero.wireless.web.entity.business.Properties;
import com.hero.wireless.web.entity.business.ext.EnterpriseUserExt;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

/**
 * 属性表管理
 *
 * @author lyn
 * @date 2020年5月12日09:17:02
 * @version V2.2.5
 */
public interface IPropertyManage {

    /**
     * 查询属性列表
     * @param properties
     */
    @Cacheable(value = "properties_list", condition = "#p0.pagination==null")
    List<Properties> queryPropertiesList(Properties properties);

    /**
     * 删除属性配置
     * @param properties
     */
    @CacheEvict(value = "properties_list", allEntries = true)
    void deleteProperties(Properties properties);

    /**
     * 保存企业用户属性配置
     * @param enterpriseUser
     */
    @CacheEvict(value = "properties_list", allEntries = true)
    void addEnterpriseUserProperties(EnterpriseUserExt enterpriseUser);

    @CacheEvict(value = "properties_list", allEntries = true)
    void save(Properties properties);

    @CacheEvict(value = "properties_list", allEntries = true)
    void update(Properties properties);
}
