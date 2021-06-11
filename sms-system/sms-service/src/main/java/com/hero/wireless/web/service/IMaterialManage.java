package com.hero.wireless.web.service;


import com.hero.wireless.web.entity.business.EnterpriseUser;
import com.hero.wireless.web.entity.business.MmsMaterial;
import com.hero.wireless.web.entity.business.MmsTemplate;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface IMaterialManage {

    /**
     * 素材列表
     *
     * @param material
     * @return
     */
    List<MmsMaterial> queryMmsMaterialList(MmsMaterial material);

    /**
     * 添加素材
     *
     * @param material
     * @param map
     * @return
     */
    void insertMaterial(MmsMaterial material, Map<String, String> map);

    /**
     * 删除素材
     *
     * @param ckIds
     * @param enterpriseUser
     * @return
     */
    void deleteMaterial(List<Integer> ckIds, EnterpriseUser enterpriseUser);

    /**
     * 审核素材
     *
     * @param approveStatus
     * @param ckIds
     * @return
     */
    void approveMmsMaterial(String approveStatus, List<Integer> ckIds);

    /**
     * 彩信模板列表
     * @param MmsTemplate
     * @return
     */
    @Cacheable(value = "mms_template_list", condition = "#p0.pagination==null")
    List<MmsTemplate> queryMmsTemplateList(MmsTemplate MmsTemplate);

    /**
     * 添加彩信模板
     * @param MmsTemplate
     * @return
     */
    @CacheEvict(value = "mms_template_list", allEntries = true)
    void addMmsTemplate(MmsTemplate MmsTemplate);

    /**
     * 批量删除彩信模板
     * @param ckIds
     * @param enterpriseUser
     * @return
     */
    void deleteMmsTemplate(List<Integer> ckIds, EnterpriseUser enterpriseUser);

    /**
     * 编辑彩信模板
     * @param MmsTemplate
     * @return
     */
    void editMmsTemplate(MmsTemplate MmsTemplate);

    /**
     * 更新彩信模板
     * @param template
     * @return
     */
    void updateMmsTemplateForChannelCode(MmsTemplate template);

    /**
     * 查询模板
     * @param id
     * @return
     */
     MmsTemplate queryMmsTemplate(Integer id);
}
