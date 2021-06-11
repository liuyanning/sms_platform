package com.hero.wireless.web.action.admin;

import com.drondea.wireless.util.ServiceException;
import com.drondea.wireless.util.SuperLogger;
import com.hero.wireless.json.LayUiJsonObjectFmt;
import com.hero.wireless.json.LayUiObjectMapper;
import com.hero.wireless.json.LayuiResultUtil;
import com.hero.wireless.web.action.BaseAdminController;
import com.hero.wireless.web.action.entity.BaseParamEntity;
import com.hero.wireless.web.action.interceptor.AvoidRepeatableCommitAnnotation;
import com.hero.wireless.web.action.interceptor.OperateAnnotation;
import com.hero.wireless.web.entity.business.Platform;
import com.hero.wireless.web.service.IPlatformManage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 *
 * @author lyh
 * @version 1.0.0
 * @createTime 2020年06月09日
 */
@Controller
@RequestMapping("/admin/")
public class PlatformController extends BaseAdminController {

    @Resource(name = "platformManage")
    private IPlatformManage platformManage;

    /**
     * 平台列表
     *
     */
    @RequestMapping("platform_list")
    @ResponseBody
    public String list(Platform platform) {
        platform = platform == null ? new Platform() : platform;
        List<Platform> platformList =  platformManage.queryPlatformList(platform);
        return new LayUiObjectMapper().asSuccessString(platformList, platform.getPagination().getTotalCount());
    }


    @RequestMapping("platform_add")
    @ResponseBody
    @OperateAnnotation(moduleName = "平台管理", option = "添加平台")
    @AvoidRepeatableCommitAnnotation(systemModuleName = ADMIN_PLATFORM + "platform_add")
    public LayUiJsonObjectFmt add(Platform platform) {
        try {
            this.platformManage.addPlatform(platform);
        } catch (Exception e) {
            SuperLogger.error(e);
            return LayuiResultUtil.fail(e.getMessage());
        }
        return LayuiResultUtil.success();
    }
    /**
     * 修改平台前置
     *
     * @return
     */
    @RequestMapping("platform_preEdit")
    public String preEdit(BaseParamEntity entity) {
        if (entity.getCkIds() == null || entity.getCkIds().size() > 1) {
            SuperLogger.error("enterprise_preEdit ckIds is null");
            throw new ServiceException("id is not null");
        }
        Platform platform = platformManage.queryPlatformById(entity.getCkIds().get(0));
        request.setAttribute("platform", platform);
        return "/platform/edit";
    }
    /**
     * 编辑保存
     *
     * @return
     */
    @RequestMapping("platform_edit")
    @ResponseBody
    @OperateAnnotation(moduleName = "平台管理", option = "修改平台信息")
    public LayUiJsonObjectFmt edit(Platform platform) {
        try {
            platformManage.updateByPrimaryKeySelective(platform);
        } catch (Exception e) {
            SuperLogger.error(e);
            return LayuiResultUtil.fail(e.getMessage());
        }
        return LayuiResultUtil.success();
    }
}
