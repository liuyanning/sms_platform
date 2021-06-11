package com.hero.wireless.web.action.admin;

import com.drondea.wireless.util.ServiceException;
import com.drondea.wireless.util.SuperLogger;
import com.hero.wireless.enums.AuditStatus;
import com.hero.wireless.enums.MaterialType;
import com.hero.wireless.json.LayUiJsonObjectFmt;
import com.hero.wireless.json.LayUiObjectMapper;
import com.hero.wireless.json.LayuiResultUtil;
import com.hero.wireless.web.action.BaseEnterpriseController;
import com.hero.wireless.web.action.entity.BaseParamEntity;
import com.hero.wireless.web.action.interceptor.AvoidRepeatableCommitAnnotation;
import com.hero.wireless.web.config.DatabaseCache;
import com.hero.wireless.web.entity.business.MmsMaterial;
import com.hero.wireless.web.entity.business.MmsTemplate;
import com.hero.wireless.web.entity.business.ext.SmsRouteExt;
import com.hero.wireless.web.service.IBusinessManage;
import com.hero.wireless.web.service.IMaterialManage;
import com.hero.wireless.web.util.UploadUtil;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

@Controller
@RequestMapping("/admin/")
public class BusinessController extends BaseEnterpriseController {

    @Resource(name = "materialManage")
    private IMaterialManage materialManage;
    @Resource(name = "businessManage")
    private IBusinessManage businessManage;

    /**
     * 素材列表
     */
    @RequestMapping("business_materialList")
    @ResponseBody
    public String materialList(MmsMaterial material) {
        material.setEnterprise_No(getLoginEnterprise().getNo());
        material.setEnterprise_User_Id(getUserId());
        List<MmsMaterial> list = materialManage.queryMmsMaterialList(material);
        return new LayUiObjectMapper().asSuccessString(list, material.getPagination().getTotalCount());
    }

    /**
     * 添加素材前置
     */
    @RequestMapping("business_perAddMaterial")
    public ModelAndView perAddMaterial() {
        ModelAndView mv = new ModelAndView("/business/material_add");
        String maxMaterialSize = DatabaseCache.getStringValueBySystemEnvAndCode("material_max_size", "1945");
        mv.addObject("maxMaterialSize", maxMaterialSize);
        return mv;
    }

    /**
     * 添加素材
     */
    @RequestMapping("business_addMaterial")
    @ResponseBody
    @AvoidRepeatableCommitAnnotation(systemModuleName = ENTERPRISE_PLATFORM + "business_addMaterial")
    public LayUiJsonObjectFmt addMaterial(MmsMaterial material, @RequestParam(value = "file", required = true) MultipartFile file) throws IOException {
        try {
            material.setEnterprise_No(getLoginEnterprise().getNo());
            material.setEnterprise_User_Id(getUserId());
            material.setApprove_Status(AuditStatus.UNAUDITED.value());
            Long maxSize = Long.valueOf(DatabaseCache.getStringValueBySystemEnvAndCode("material_max_size", "1945")) * 1024L;
            if (file.getSize() > maxSize) {
                return LayuiResultUtil.fail("上传的文件太大");
            }
            String path = "material" + File.separator + material.getEnterprise_No();
            Map<String, String> map = UploadUtil.uploadFile(file, path);
            if (!"true".equals(map.get("status"))) {
                return LayuiResultUtil.fail("上传失败");
            }
            materialManage.insertMaterial(material, map);
        } catch (ServiceException e) {
            return LayuiResultUtil.fail("上传失败");
        } catch (Exception e) {
            SuperLogger.error(e.getMessage(), e);
            return LayuiResultUtil.fail("上传错误");
        }
        return LayuiResultUtil.success("操作成功");
    }

    /**
     * 删除素材
     */
    @RequestMapping("business_delMaterial")
    @ResponseBody
    public LayUiJsonObjectFmt delMaterial(BaseParamEntity entity) {
        if (entity.getCkIds() == null || entity.getCkIds().size() < 1) {
            throw new ServiceException("至少选择一条数据");
        }
        materialManage.deleteMaterial(entity.getCkIds(), getAuthorityEnterpriseUserBean());
        return LayuiResultUtil.success("操作成功");
    }

    /**
     * 选择素材
     *
     * @param material
     * @param comeType
     * @return
     */
    @RequestMapping("business_preSelectMaterial")
    public String preSelectMaterial(MmsMaterial material, String comeType) {
        material.setEnterprise_No(getLoginEnterprise().getNo());
        material.setEnterprise_User_Id(getUserId());
        List<MmsMaterial> pictureList = new ArrayList<>();
        List<MmsMaterial> videoList = new ArrayList<>();
        List<MmsMaterial> audioList = new ArrayList<>();
        List<MmsMaterial> materialList = materialManage.queryMmsMaterialList(material);
        if (materialList != null && !materialList.isEmpty()) {
            materialList.forEach(item -> {
                item.setUrl(item.getUrl().replaceAll("\\\\", "/"));
                if (MaterialType.PICTURE.equals(item.getMaterial_Type_Code())) {
                    pictureList.add(item);
                } else if (MaterialType.VIDEO.equals(item.getMaterial_Type_Code())) {
                    videoList.add(item);
                } else if (MaterialType.AUDIO.equals(item.getMaterial_Type_Code())) {
                    audioList.add(item);
                }
            });
        }
        request.setAttribute("pictureList", pictureList);
        request.setAttribute("videoList", videoList);
        request.setAttribute("audioList", audioList);
        request.setAttribute("comeType", comeType);
        return "/business/mms_select_material";
    }

    /**
     * 彩信模板列表查询
     *
     * @param
     * @return
     */
    @RequestMapping("business_mmsTemplateList")
    @ResponseBody
    public String mmsTemplateList(MmsTemplate mmsTemplate) {
        mmsTemplate.setEnterprise_No(getLoginEnterprise().getNo());
        mmsTemplate.setEnterprise_User_Id(getUserId());
        List<MmsTemplate> mmsTemplates = materialManage.queryMmsTemplateList(mmsTemplate);
        return new LayUiObjectMapper().asSuccessString(mmsTemplates, mmsTemplate.getPagination().getPageCount());
    }

    /**
     * 添加彩信模板前置
     *
     * @return
     */
    @RequestMapping("business_perAddMmsTemplate")
    public String perAddMmsTemplate() {
        String maxSize = DatabaseCache.getStringValueBySystemEnvAndCode("mms_template_max_size", "1945");
        request.setAttribute("maxSize", maxSize);
        return "/business/mms_template_add";
    }

    /**
     * 添加彩信模板
     *
     * @param mmsTemplate
     * @return
     */
    @RequestMapping("business_addMmsTemplate")
    @ResponseBody
    @AvoidRepeatableCommitAnnotation(systemModuleName = ENTERPRISE_PLATFORM + "business_addMmsTemplate")
    public LayUiJsonObjectFmt addMmsTemplate(MmsTemplate mmsTemplate) {
        try {
            mmsTemplate.setEnterprise_No(getLoginEnterprise().getNo());
            mmsTemplate.setEnterprise_User_Id(getUserId());
            this.materialManage.addMmsTemplate(mmsTemplate);
        } catch (ServiceException e) {
            return LayuiResultUtil.fail(e.getMessage());
        } catch (Exception e) {
            SuperLogger.error(e.getMessage(), e);
            return LayuiResultUtil.error(e);
        }
        return LayuiResultUtil.success();
    }

    /**
     * 删除彩信模板
     *
     * @param entity
     * @return
     */
    @RequestMapping("business_deleteMmsTemplate")
    @ResponseBody
    public LayUiJsonObjectFmt deleteMmsTemplate(BaseParamEntity entity) {
        try {
            this.materialManage.deleteMmsTemplate(entity.getCkIds(), getAuthorityEnterpriseUserBean());
        } catch (ServiceException e) {
            return LayuiResultUtil.fail(e.getMessage());
        } catch (Exception e) {
            SuperLogger.error(e.getMessage(), e);
            return LayuiResultUtil.error(e);
        }
        return LayuiResultUtil.success();
    }

    @RequestMapping("business_operatorListByCountry")
    @ResponseBody
    public String operatorListByCountry(String countryNumber) {
        List<SmsRouteExt> smsRouteList = businessManage.queryOperatorListByCountry(countryNumber);
        return new LayUiObjectMapper().asSuccessString(smsRouteList);
    }
}
