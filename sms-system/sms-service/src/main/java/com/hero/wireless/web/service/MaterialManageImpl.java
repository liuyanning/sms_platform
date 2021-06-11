package com.hero.wireless.web.service;


import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.hero.wireless.web.entity.business.EnterpriseUser;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.drondea.wireless.config.Constant;
import com.hero.wireless.web.dao.business.IMmsMaterialDAO;
import com.hero.wireless.web.dao.business.IMmsTemplateDAO;
import com.hero.wireless.web.entity.business.MmsMaterial;
import com.hero.wireless.web.entity.business.MmsMaterialExample;
import com.hero.wireless.web.entity.business.MmsTemplate;
import com.hero.wireless.web.entity.business.MmsTemplateExample;
import com.hero.wireless.web.util.CodeUtil;

@Service("materialManage")
public class MaterialManageImpl implements IMaterialManage {

    @Resource(name = "IMmsMaterialDAO")
    protected IMmsMaterialDAO<MmsMaterial> mmsMaterialDAO;
    @Resource(name = "IMmsTemplateDAO")
    protected IMmsTemplateDAO<MmsTemplate> mmsTemplateDAO;

    @Override
    public List<MmsMaterial> queryMmsMaterialList(MmsMaterial material) {
        MmsMaterialExample example = new MmsMaterialExample();
        MmsMaterialExample.Criteria cri = example.createCriteria();
        if (material.getId() != null){
            cri.andIdEqualTo(material.getId());
        }
        if (StringUtils.isNotEmpty(material.getEnterprise_No())){
            cri.andEnterprise_NoEqualTo(material.getEnterprise_No());
        }
        if (material.getEnterprise_User_Id() != null){
            cri.andEnterprise_User_IdEqualTo(material.getEnterprise_User_Id());
        }
        if (StringUtils.isNotEmpty(material.getMaterial_Code())){
            cri.andMaterial_CodeEqualTo(material.getMaterial_Code());
        }
        if (StringUtils.isNotEmpty(material.getMaterial_Type_Code())){
            cri.andMaterial_Type_CodeEqualTo(material.getMaterial_Type_Code());
        }
        if (StringUtils.isNotEmpty(material.getFormat())){
            cri.andFormatEqualTo(material.getFormat());
        }
        if (StringUtils.isNotEmpty(material.getApprove_Status())){
            cri.andApprove_StatusEqualTo(material.getApprove_Status());
        }
        example.setPagination(material.getPagination());
        example.setOrderByClause(" id desc ");
        return mmsMaterialDAO.selectByExamplePage(example);
    }

    @Override
    public void insertMaterial(MmsMaterial material, Map<String, String> map) {
        material.setUrl(map.get("url"));
        material.setSize(map.get("size"));
        material.setFormat(map.get("format"));
        material.setCreate_Date(new Date());
        material.setMaterial_Code(CodeUtil.buildNo());
        mmsMaterialDAO.insert(material);
    }

    @Override
    public void deleteMaterial(List<Integer> ckIds, EnterpriseUser enterpriseUser) {
        MmsMaterialExample example = new MmsMaterialExample();
        MmsMaterialExample.Criteria cri = example.createCriteria();
        cri.andIdIn(ckIds);
        if (StringUtils.isNotEmpty(enterpriseUser.getEnterprise_No())){
            cri.andEnterprise_NoEqualTo(enterpriseUser.getEnterprise_No());
        }
        if (enterpriseUser.getId()!= null){
            cri.andEnterprise_User_IdEqualTo(enterpriseUser.getId());
        }
        this.mmsMaterialDAO.deleteByExample(example);
    }

    @Override
    public void approveMmsMaterial(String approve_status, List<Integer> ckIds) {
        for (Integer id: ckIds){
            MmsMaterial material = new MmsMaterial();
            material.setId(id);
            material.setApprove_Status(approve_status);
            mmsMaterialDAO.updateByPrimaryKeySelective(material);
        }
    }
    @Override
    public MmsTemplate queryMmsTemplate(Integer id) {
        return mmsTemplateDAO.selectByPrimaryKey(id);
    }

    @Override
    public List<MmsTemplate> queryMmsTemplateList(MmsTemplate condition) {
        MmsTemplateExample example = assemblyMmsTemplateListConditon(condition);
        example.setPagination(condition.getPagination());
        example.setOrderByClause(" id desc ");
        return mmsTemplateDAO.selectByExamplePage(example);
    }

    protected MmsTemplateExample assemblyMmsTemplateListConditon(MmsTemplate mmsTemplate) {
        MmsTemplateExample example=new MmsTemplateExample();
        MmsTemplateExample.Criteria criteria=example.createCriteria();
        if (null!=mmsTemplate.getId()){
            criteria.andIdEqualTo(mmsTemplate.getId());
        }
        if (StringUtils.isNotEmpty(mmsTemplate.getEnterprise_No())){
            criteria.andEnterprise_NoEqualTo(mmsTemplate.getEnterprise_No());
        }
        if (null!=mmsTemplate.getEnterprise_User_Id()){
            criteria.andEnterprise_User_IdEqualTo(mmsTemplate.getEnterprise_User_Id());
        }
        if (StringUtils.isNotEmpty(mmsTemplate.getTemplate_Name())){
            criteria.andTemplate_NameLike("%"+mmsTemplate.getTemplate_Name()+"%");
        }
        if (StringUtils.isNotEmpty(mmsTemplate.getTemplate_Code())){
            criteria.andTemplate_CodeEqualTo(mmsTemplate.getTemplate_Code());
        }
        if (StringUtils.isNotEmpty(mmsTemplate.getChannel_Template_Code())){
            criteria.andChannel_Template_CodeEqualTo(mmsTemplate.getChannel_Template_Code());
        }
        if (StringUtils.isNotEmpty(mmsTemplate.getTemplate_Content())){
            criteria.andTemplate_ContentEqualTo(mmsTemplate.getTemplate_Content());
        }
        if (StringUtils.isNotEmpty(mmsTemplate.getTrade_Type_Code())){
            criteria.andTrade_Type_CodeEqualTo(mmsTemplate.getTrade_Type_Code());
        }
        if (StringUtils.isNotEmpty(mmsTemplate.getApprove_Status())){
            criteria.andApprove_StatusEqualTo(mmsTemplate.getApprove_Status());
        }
        if (StringUtils.isNotEmpty(mmsTemplate.getDescription())){
            criteria.andDescriptionEqualTo(mmsTemplate.getDescription());
        }
        if (StringUtils.isNotEmpty(mmsTemplate.getRemark())){
            criteria.andRemarkEqualTo(mmsTemplate.getRemark());
        }
        example.setPagination(mmsTemplate.getPagination());
        return example;
    }

    @Override
    public void addMmsTemplate(MmsTemplate mmsTemplate) {
        mmsTemplate.setCreate_Date(new Date());
        mmsTemplate.setApprove_Status(Constant.SMS_TEMPLAT_CHECK_STATUS_PENDING);
        mmsTemplate.setTemplate_Code(CodeUtil.buildNo());
        mmsTemplateDAO.insert(mmsTemplate);
    }

    @Override
    public void deleteMmsTemplate(List<Integer> ckIds, EnterpriseUser enterpriseUser) {
        MmsTemplateExample example = new MmsTemplateExample();
        MmsTemplateExample.Criteria cri = example.createCriteria();
        cri.andIdIn(ckIds);
        if (StringUtils.isNotEmpty(enterpriseUser.getEnterprise_No())){
            cri.andEnterprise_NoEqualTo(enterpriseUser.getEnterprise_No());
        }
        if (enterpriseUser.getId()!= null){
            cri.andEnterprise_User_IdEqualTo(enterpriseUser.getId());
        }
        mmsTemplateDAO.deleteByExample(example);
    }

    @Override
    public void editMmsTemplate(MmsTemplate mmsTemplate) {
        mmsTemplateDAO.updateByPrimaryKeySelective(mmsTemplate);
    }

    @Override
    public void updateMmsTemplateForChannelCode(MmsTemplate template) {
        MmsTemplateExample example = new MmsTemplateExample();
        MmsTemplateExample.Criteria cri = example.createCriteria();
        cri.andChannel_Template_CodeEqualTo(template.getChannel_Template_Code());
        mmsTemplateDAO.updateByExampleSelective(template,example);
    }

}
