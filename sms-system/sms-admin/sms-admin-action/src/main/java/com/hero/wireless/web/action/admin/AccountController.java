package com.hero.wireless.web.action.admin;

import com.drondea.wireless.util.ServiceException;
import com.drondea.wireless.util.SuperLogger;
import com.hero.wireless.json.LayUiJsonObjectFmt;
import com.hero.wireless.json.LayUiObjectMapper;
import com.hero.wireless.json.LayuiResultUtil;
import com.hero.wireless.web.action.AdminControllerBase;
import com.hero.wireless.web.action.entity.BaseParamEntity;
import com.hero.wireless.web.action.interceptor.AvoidRepeatableCommitAnnotation;
import com.hero.wireless.web.action.interceptor.OperateAnnotation;
import com.hero.wireless.web.config.DatabaseCache;
import com.hero.wireless.web.entity.business.AdminLimit;
import com.hero.wireless.web.entity.business.AdminRole;
import com.hero.wireless.web.entity.business.AdminRoleLimit;
import com.hero.wireless.web.entity.business.AdminUser;
import com.hero.wireless.web.entity.business.AdminUserRoles;
import com.hero.wireless.web.entity.business.Code;
import com.hero.wireless.web.entity.business.EnterpriseUser;
import com.hero.wireless.web.entity.business.ext.AdminLimitExt;
import com.hero.wireless.web.service.IUserManage;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Resource;


/**
 * AdminAction
 *
 * @author
 * @version 1.0.0
 * @createTime 2016年10月20日 上午10:42:08
 */
@Controller
@RequestMapping("/admin/")
public class AccountController extends AdminControllerBase {

    @Resource(name = "userManage")
    private IUserManage userManage;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setAutoGrowNestedPaths(true);
        binder.setAutoGrowCollectionLimit(1024);
    }

    /**
     * 账号管理页面
     */
    @RequestMapping("account_preAccountList")
    public ModelAndView preAccountlist() {
        ModelAndView mv = new ModelAndView("/account/account_list");
        Code code = DatabaseCache.getSystemEnvByCode("platform_admin_tags");
        if (code != null){
            mv.addObject("platformAdminTags",code.getName());
        }
        return mv;
    }

    /**
     * 账号管理列表
     */
    @RequestMapping("account_list")
    @ResponseBody
    public String list(AdminUser userInfo) {
        List<AdminUser> list = userManage.queryAdminUserList(userInfo);
        return new LayUiObjectMapper().asSuccessString(list, userInfo.getPagination().getTotalCount());
    }

    /**
     * 增加账户
     */
    @RequestMapping("account_save")
    @ResponseBody
    @OperateAnnotation(moduleName = "账户管理", option = "增加账户")
    @AvoidRepeatableCommitAnnotation(systemModuleName = ADMIN_PLATFORM+"account_save")
    public LayUiJsonObjectFmt save(AdminUser userInfo) {
        userInfo.setRemark(currentAdmin().getReal_Name() + "添加");
        try {
            userManage.addAdminUser(userInfo);
        } catch (Exception e) {
            SuperLogger.error(e);
            return LayuiResultUtil.fail(e.getMessage());
        }
        return LayuiResultUtil.success();
    }

    /**
     * 锁定用户
     */
    @RequestMapping("account_lock")
    @ResponseBody
    @OperateAnnotation(moduleName = "账户管理", option = "锁定账户")
    public LayUiJsonObjectFmt lock(BaseParamEntity entity) {
        userManage.lockAdminUser(entity.getCkIds());
        return LayuiResultUtil.success();
    }

    /**
     * 解锁用户
     */
    @RequestMapping("account_unLock")
    @ResponseBody
    @OperateAnnotation(moduleName = "账户管理", option = "解锁账户")
    public LayUiJsonObjectFmt unLock(BaseParamEntity entity) {
        userManage.unLockAdminUser(entity.getCkIds());
        return LayuiResultUtil.success();
    }

    /**
     * 角色管理列表
     */
    @RequestMapping("account_roleList")
    @ResponseBody
    public String roleList(AdminRole adminRole) {
        List<AdminRole> list = userManage.queryAdminRoleList(adminRole);
        return new LayUiObjectMapper().asSuccessString(list, adminRole.getPagination().getTotalCount());
    }

    /**
     * 跳转至角色修改页面
     */
    @RequestMapping("account_preRoleEdit")
    public String preRoleEdit(BaseParamEntity entity) {
        request.setAttribute("adminRole", userManage.queryRoleById(entity.getCkIds().get(0)));
        return "/account/role_edit";
    }

    /**
     * 新增角色
     */
    @RequestMapping("account_saveRole")
    @OperateAnnotation(moduleName = "账户管理", option = "新增角色")
    @AvoidRepeatableCommitAnnotation(systemModuleName = ADMIN_PLATFORM+"account_saveRole")
    public @ResponseBody
    LayUiJsonObjectFmt saveRole(AdminRole adminRole) {
        adminRole.setCreate_User(currentAdmin().getReal_Name() + "添加");
        try {
            userManage.addAdminRole(adminRole);
        } catch (Exception e) {
            SuperLogger.error(e);
            return LayuiResultUtil.fail(e.getMessage());
        }
        return LayuiResultUtil.success();
    }

    /**
     * 修改角色
     */
    @RequestMapping("account_roleEdit")
    @OperateAnnotation(moduleName = "账户管理", option = "修改角色")
    public @ResponseBody
    LayUiJsonObjectFmt editRoleSave(AdminRole adminRole) {
        adminRole.setCreate_User(currentAdmin().getReal_Name() + "修改");
        userManage.editRoleByPK(adminRole);
        return LayuiResultUtil.success();
    }

    /**
     * 分配角色列表
     */
    @RequestMapping("account_bindUserRoleList")
    public String bindUserRoleList(BaseParamEntity entity) {
        request.setAttribute("userId", entity.getCkIds().get(0));
        request.setAttribute("adminRoleExtList", userManage.queryBindUserRoleList(entity.getCkIds()));
        return "/account/role_bind_list";
    }

    /**
     * 分配用户角色
     */
    @RequestMapping("account_bindUserRole")
    @ResponseBody
    @OperateAnnotation(moduleName = "账户管理", option = "分配用户角色")
    public LayUiJsonObjectFmt bindUserRole(BaseParamEntity entity) {
        List<AdminUserRoles> userRoleList = new ArrayList<>();
        if (entity.getCkRoleId() == null || entity.getCkRoleId().size() == 0) {
            return LayuiResultUtil.fail("操作失败，请至少选择一个角色!");
        }
        List<Integer> ckRoleIds = entity.getCkRoleId();
        for (Integer roleIds :ckRoleIds){
            if(roleIds == null) continue;
            AdminUserRoles role = new AdminUserRoles();
            role.setRole_Id(roleIds);
            role.setUser_Id(entity.getCkIds().get(0));
            role.setCreate_User(currentAdmin().getReal_Name());
            userRoleList.add(role);
        }
        userManage.bindAdminUserRole(userRoleList);
        return LayuiResultUtil.success();
    }

    /**
     * 分配角色权限列表
     */
    @RequestMapping("account_bindRoleLimitList")
    @ResponseBody
    public LayUiJsonObjectFmt bindRoleLimitList(BaseParamEntity entity) {
        List<AdminLimitExt> adminLimitExtList = userManage.queryBindRoleLimitList(entity.getCkIds().get(0));
        List<AdminLimitExt> ckLimits = adminLimitExtList.stream().filter(item -> null != item.getAdminRole()).collect(Collectors.toList());
        Set<Integer> ckLimitsId = ckLimits.stream().map(AdminLimitExt::getId).collect(Collectors.toSet());
        Map<String, Object> data = new HashMap<>();
        data.put("list", allLimit(adminLimitExtList));// 全部权限
        data.put("checkedId", ckLimitsId);// 该角色已拥有权限
        return LayuiResultUtil.success(data);
    }

    private List<Map> allLimit(List<AdminLimitExt> adminLimitExtList) {
        List<Map> list = new ArrayList<>();
        adminLimitExtList.stream().forEach(item -> {
            int pId = getPId(item.getUp_Code(), adminLimitExtList);
            Map map = new HashMap();
            map.put("id", item.getId());
            map.put("upId", pId);
            map.put("name", item.getName());
            list.add(map);
        });
        return list;
    }

    private int getPId(String upCode, List<AdminLimitExt> adminLimitExtList) {
        for (int i = 0, len = adminLimitExtList.size(); i < len; i++) {
            if (adminLimitExtList.get(i).getCode().equals(upCode)) {
                return adminLimitExtList.get(i).getId();
            }
        }
        return 0;
    }

    /**
     * 分配角色权限
     */
    @RequestMapping("account_bindRoleLimit")
    @OperateAnnotation(moduleName = "账户管理", option = "分配角色权限")
    public @ResponseBody
    LayUiJsonObjectFmt bindRoleLimit(BaseParamEntity entity) {
        List<AdminRoleLimit> roleLimitList = new ArrayList<AdminRoleLimit>();
        if (entity.getCkLimitId() == null) {
            return LayuiResultUtil.fail("操作失败，请至少选择一个权限!");
        }
        AdminRoleLimit saveRoleLimit;
        for (int j = 0; j < entity.getCkLimitId().size(); j++) {
            saveRoleLimit = new AdminRoleLimit();
            saveRoleLimit.setRole_Id(entity.getCkRoleId().get(0));
            saveRoleLimit.setLimit_Id(entity.getCkLimitId().get(j));
            saveRoleLimit.setCreate_User(currentAdmin().getReal_Name());
            roleLimitList.add(saveRoleLimit);
        }
        userManage.bindAdminRoleLimit(roleLimitList);
        return LayuiResultUtil.success("");
    }

    /**
     * 权限管理列表
     */
    @RequestMapping("account_limitList")
    @ResponseBody
    public String limitList(AdminLimit adminLimit) {
        List<AdminLimit> list = userManage.queryAdminLimitList(adminLimit);
        return new LayUiObjectMapper().asSuccessString(list, adminLimit.getPagination().getTotalCount());
    }

    /**
     * 跳转至权限修改页
     */
    @RequestMapping("account_preLimitEdit")
    public String preLimitEdit(BaseParamEntity entity) {
        request.setAttribute("adminLimit", userManage.queryAdminLimitById(entity.getCkIds().get(0)));
        return "/account/limit_edit";
    }

    /**
     * 保存权限
     */
    @RequestMapping("account_saveLimit")
    @OperateAnnotation(moduleName = "账户管理", option = "添加权限")
    @AvoidRepeatableCommitAnnotation(systemModuleName = ADMIN_PLATFORM+"account_saveLimit")
    public @ResponseBody
    LayUiJsonObjectFmt saveLimit(AdminLimit adminLimit) {
        adminLimit.setCreate_User(currentAdmin().getReal_Name() + "添加");
        try {
            userManage.addAdminLimit(adminLimit);
        } catch (Exception e) {
            SuperLogger.error(e);
            return LayuiResultUtil.fail(e.getMessage());
        }
        return LayuiResultUtil.success();
    }

    /**
     * 保存权限
     */
    @RequestMapping("account_limitEdit")
    @OperateAnnotation(moduleName = "账户管理", option = "修改权限")
    public @ResponseBody
    LayUiJsonObjectFmt editLimitSave(AdminLimit adminLimit) {
        adminLimit.setCreate_User(currentAdmin().getReal_Name() + "修改");
        userManage.editLimitByPK(adminLimit);
        return LayuiResultUtil.success();
    }

    /**
     * 密码修改保存
     */
    @RequestMapping("account_editPassword")
    @OperateAnnotation(moduleName = "账户管理", option = "修改密码")
    public @ResponseBody
    LayUiJsonObjectFmt editPassword(AdminUser userInfo, String oldPassword) {

        try {
        	userInfo.setId(currentAdmin().getId());
            userInfo.setUser_Name(currentAdmin().getUser_Name());
            userManage.editPassword(userInfo, oldPassword,0);
		} catch (ServiceException be) {
			return LayuiResultUtil.fail(be.getMessage());
		} catch (Exception be) {
			return LayuiResultUtil.fail(be.getMessage());
		}
        
        return LayuiResultUtil.success();
    }

    /**
     * 跳转至密码修改页
     */
    @RequestMapping("account_prePasswordEdit")
    public String prePasswordEdit(BaseParamEntity entity) {
        return "account/edit_password";
    }
    
    /**
     * 跳转至密码重置页
     */
    @RequestMapping("account_prePasswordReset")
    public String prePasswordReset(BaseParamEntity entity) {
    	
    	request.setAttribute("userId", entity.getCkIds().get(0));
    	return "/account/reset_password";
    }
    
    /**
     * 密码重置
     */
    @RequestMapping("account_resetPassword")
    @OperateAnnotation(moduleName = "账户管理", option = "修改密码")
    public @ResponseBody
    LayUiJsonObjectFmt ResetPassword(AdminUser userInfo) {
        
        userManage.editPassword(userInfo, "",1);
        return LayuiResultUtil.success();
    }


    @RequestMapping("account_deleteLimit")
    @ResponseBody
    public LayUiJsonObjectFmt deleteLimit(@RequestParam(value = "ckIds") List<Integer> ckIds) {
        try {
            userManage.deleteAdminLimit(ckIds);
        }catch (DataIntegrityViolationException de){
            return LayuiResultUtil.fail("删除失败，请先解绑相关权限！");
        }catch (Exception e){
            return LayuiResultUtil.fail(e.getMessage());
        }
        return LayuiResultUtil.success();
    }
}
