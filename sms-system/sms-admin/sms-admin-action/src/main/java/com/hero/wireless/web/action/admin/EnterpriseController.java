package com.hero.wireless.web.action.admin;

import com.alibaba.fastjson.JSONObject;
import com.drondea.wireless.config.Constant;
import com.drondea.wireless.util.ServiceException;
import com.drondea.wireless.util.SuperLogger;
import com.hero.wireless.enums.*;
import com.hero.wireless.json.*;
import com.hero.wireless.web.action.AdminControllerBase;
import com.hero.wireless.web.action.BaseAdminController;
import com.hero.wireless.web.action.entity.BaseParamEntity;
import com.hero.wireless.web.action.interceptor.AvoidRepeatableCommitAnnotation;
import com.hero.wireless.web.action.interceptor.OperateAnnotation;
import com.hero.wireless.web.config.DatabaseCache;
import com.hero.wireless.web.entity.business.Properties;
import com.hero.wireless.web.entity.business.*;
import com.hero.wireless.web.entity.business.ext.*;
import com.hero.wireless.web.entity.ext.SqlStatisticsEntity;
import com.hero.wireless.web.exception.BaseException;
import com.hero.wireless.web.service.IEnterpriseManage;
import com.hero.wireless.web.service.IPropertyManage;
import com.hero.wireless.web.service.ISendManage;
import com.hero.wireless.web.util.ChannelUtil;
import com.hero.wireless.web.util.SMSUtil;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 企业EnterpriseController
 *
 * @author Administrator
 */
@Controller
@RequestMapping("/admin/")
public class EnterpriseController extends BaseAdminController {

    @Resource(name = "enterpriseManage")
    private IEnterpriseManage enterpriseManage;
    @Resource(name = "sendManage")
    private ISendManage sendManage;
    @Resource(name = "propertyManage")
    private IPropertyManage propertyManage;

    @InitBinder
    public void initDateFormate(WebDataBinder dataBinder) {
        dataBinder.addCustomFormatter(new DateFormatter("yyyy-MM-dd HH:mm:ss"));
    }

    @RequestMapping("enterprise_add")
    @ResponseBody
    @OperateAnnotation(moduleName = "企业管理", option = "添加企业")
    @AvoidRepeatableCommitAnnotation(systemModuleName = ADMIN_PLATFORM + "enterprise_add")
    public LayUiJsonObjectFmt add(Enterprise enterprise) {
        try {
            enterprise.setData_Source(DataSourceType.PLATFORMADD.toString());// 企业数据来源
            enterprise.setAuthentication_State_Code(AuthCode.PASSED.toString());// 认证状态:00:未认证 01：认证通过 02：认证拒绝  要换成枚举
            this.enterpriseManage.addEnterprise(enterprise);
        } catch (Exception e) {
            SuperLogger.error(e);
            return LayuiResultUtil.fail(e.getMessage());
        }
        return LayuiResultUtil.success();
    }

    /**
     * 修改企业前置
     *
     * @return
     */
    @RequestMapping("enterprise_preEdit")
    public String preEdit(BaseParamEntity entity) {
        if (entity.getCkIds() == null || entity.getCkIds().size() > 1) {
            SuperLogger.error("enterprise_preEdit ckIds is null");
            throw new ServiceException("id is not null");
        }
        Enterprise enterprise = enterpriseManage.queryEnterpriseById(entity.getCkIds().get(0));
        request.setAttribute("eBean", enterprise);
        return "/enterprise/edit";
    }

    /**
     * 短信配置前置
     *
     * @return
     */
    @RequestMapping("enterprise_preEditSmsConfig")
    public String preEditSmsConfig(BaseParamEntity entity) {
        preEditUser(entity);
        return "/enterprise/edit_config";
    }

    /**
     * IP白名单配置前置
     *
     * @return
     */
    @RequestMapping("enterprise_preEditIpWhiteList")
    public String preEditIpWhiteList(BaseParamEntity entity) {
        preEditUser(entity);
        return "/enterprise/edit_ip_white";
    }

    @RequestMapping("enterprise_preEditUser")
    public String preEditUser(BaseParamEntity entity) {
        if (entity.getCkIds() == null || entity.getCkIds().isEmpty()) {
            return "/enterprise/eidt_user";
        }
        EnterpriseUserExt enterpriseUserExt = new EnterpriseUserExt();
        enterpriseUserExt.setId(entity.getCkIds().get(0));
        List<EnterpriseUser> enterpriseUserList = this.enterpriseManage.queryEnterpriseUserList(enterpriseUserExt);
        if (!enterpriseUserList.isEmpty()) {
            request.setAttribute("enterpriseUser", enterpriseUserList.get(0));
        }
        return "/enterprise/edit_user";
    }

    /**
     * 用户管理=》属性配置
     *
     * @return
     */
    @RequestMapping("enterprise_preEditPropertySwitch")
    public String preEditPropertySwitch(BaseParamEntity entity) {
        request.setAttribute("enterpriseUserId", entity.getCkIds().get(0));
        Properties properties = new Properties();
        properties.setType_Code(PropertiesType.ENTERPRISE_USER.toString());
        properties.setType_Code_Num(String.valueOf(entity.getCkIds().get(0)));
        List<Properties> propertiesList = propertyManage.queryPropertiesList(properties);
        setUserPropertiesList(propertiesList);
        return "/enterprise/enterprise_user_property";
    }

    private void setUserPropertiesList(List<Properties> propertiesList) {
        propertiesList.forEach(bean ->{
            if(PropertiesType.Property_Name.BLACKLIST_SWITCH.toString().equals(bean.getProperty_Name())){
                request.setAttribute("blacklistSwitch",bean.getProperty_Value());
            } else if(PropertiesType.Property_Name.SIGNATURE_LOCATION.toString().equals(bean.getProperty_Name())){
                request.setAttribute("signatureLocation",bean.getProperty_Value());
            }else if(PropertiesType.Property_Name.SGIP_SP_IP.toString().equals(bean.getProperty_Name())){
                request.setAttribute("sgipSpIp",bean.getProperty_Value());
            }else if(PropertiesType.Property_Name.SGIP_SP_PORT.toString().equals(bean.getProperty_Name())){
                request.setAttribute("sgipSpPort",bean.getProperty_Value());
            }else if(PropertiesType.Property_Name.COUNTRY_CODE_VALUE.toString().equals(bean.getProperty_Name())){
                request.setAttribute("countryCodeValue",bean.getProperty_Value());
            }else if(PropertiesType.Property_Name.WINDOW_SIZE.toString().equals(bean.getProperty_Name())){
                request.setAttribute("windowSize",bean.getProperty_Value());
            }
        });
    }

    /**
     * 企业用户属性配置保存
     *
     * @return
     */
    @RequestMapping("enterprise_editPropertySwitch")
    @ResponseBody
    @OperateAnnotation(moduleName = "企业管理", option = "企业用户属性配置保存")
    @AvoidRepeatableCommitAnnotation(systemModuleName = ADMIN_PLATFORM + "editPropertySwitch")
    public LayUiJsonObjectFmt editPropertySwitch(EnterpriseUserExt enterpriseUser) {
        try {
            propertyManage.addEnterpriseUserProperties(enterpriseUser);

        } catch (Exception e) {
            SuperLogger.error(e.getMessage(),e);
            return LayuiResultUtil.fail(e.getMessage());
        }
        return LayuiResultUtil.success();
    }

    /**
     * 编辑保存企业用户
     *
     * @return
     */
    @RequestMapping("enterprise_editUser")
    @ResponseBody
    @OperateAnnotation(moduleName = "企业管理", option = "修改企业用户")
    public LayUiJsonObjectFmt editUser(EnterpriseUserExt enterpriseUser) {
        try {
            if (enterpriseUser.getPassword() != null
                    && !enterpriseUser.getPassword().equals(enterpriseUser.getConfirmPassword())) {
                return LayuiResultUtil.fail("两次密码输入不一致");
            }
            this.enterpriseManage.editUser(enterpriseUser);

            if (enterpriseUser.getSourceFlag() != null && "1".equals(enterpriseUser.getSourceFlag())) {
                insertOrUpdateAllowedSendTimeProperties(enterpriseUser);
            }
//            //修改提交速度
//            EnterpriseUser oldUser = enterpriseManage.queryEnterpriseUserById(enterpriseUser.getId());
//            if (enterpriseUser.getTcp_Submit_Speed() != null && oldUser
//                    .getTcp_Submit_Speed().intValue() != enterpriseUser
//                    .getTcp_Submit_Speed().intValue()) {
//                enterpriseUser.setTcp_User_Name(oldUser.getTcp_User_Name());
//                MQUtil.notifyCloseSocketClient(enterpriseUser);
//            }
        } catch (Exception e) {
            e.printStackTrace();
            return LayuiResultUtil.fail(e.getMessage());
        }
        return LayuiResultUtil.success();
    }

    protected void insertOrUpdateAllowedSendTimeProperties(EnterpriseUserExt enterpriseUser) {
        List<Properties> propertiesList = DatabaseCache.queryPropertiesListByFields("EnterpriseUser", String.valueOf(enterpriseUser.getId()), "", "Allowed_Send_Time");
        if (propertiesList.isEmpty()) {
            Properties properties = new Properties();
            properties.setType_Code("enterpriseUser");
            properties.setType_Code_Num(String.valueOf(enterpriseUser.getId()));
            properties.setProperty_Value(enterpriseUser.getFirstGroupBeginTime());
            properties.setExtended_Field("Allowed_Send_Time");
            properties.setProperty_Name("First_Group_Begin_Time");
            properties.setCreate_Date(new Date());
            propertyManage.save(properties);
            properties.setId(null);
            properties.setProperty_Name("First_Group_End_Time");
            properties.setProperty_Value(enterpriseUser.getFirstGroupEndTime());
            propertyManage.save(properties);
            properties.setId(null);
            properties.setProperty_Name("Second_Group_Begin_Time");
            properties.setProperty_Value(enterpriseUser.getSecondGroupBeginTime());
            propertyManage.save(properties);
            properties.setId(null);
            properties.setProperty_Name("Second_Group_End_Time");
            properties.setProperty_Value(enterpriseUser.getSecondGroupEndTime());
            propertyManage.save(properties);
        } else {
            for (Properties properties : propertiesList) {
                if ("First_Group_Begin_Time".equals(properties.getProperty_Name())) {
                    properties.setProperty_Value(enterpriseUser.getFirstGroupBeginTime());
                    propertyManage.update(properties);
                }
                if ("First_Group_End_Time".equals(properties.getProperty_Name())) {
                    properties.setProperty_Value(enterpriseUser.getFirstGroupEndTime());
                    propertyManage.update(properties);
                }
                if ("Second_Group_Begin_Time".equals(properties.getProperty_Name())) {
                    properties.setProperty_Value(enterpriseUser.getSecondGroupBeginTime());
                    propertyManage.update(properties);
                }
                if ("Second_Group_End_Time".equals(properties.getProperty_Name())) {
                    properties.setProperty_Value(enterpriseUser.getSecondGroupEndTime());
                    propertyManage.update(properties);
                }
            }
        }
    }

    /**
     * 编辑保存
     *
     * @return
     */
    @RequestMapping("enterprise_edit")
    @ResponseBody
    @OperateAnnotation(moduleName = "企业管理", option = "修改企业信息")
    public LayUiJsonObjectFmt edit(EnterpriseExt enterpriseExt) throws Exception {
        this.enterpriseManage.editEnterprise(enterpriseExt);
        return LayuiResultUtil.success();
    }

    /**
     * 增加企业用户
     *
     * @return
     */
    @RequestMapping("enterprise_addUser")
    @ResponseBody
    @OperateAnnotation(moduleName = "企业管理", option = "增加企业用户")
    @AvoidRepeatableCommitAnnotation(systemModuleName = ADMIN_PLATFORM + "enterprise_addUser")
    public LayUiJsonObjectFmt addUser(EnterpriseUserExt enterpriseUser) {
        try {
            if (enterpriseUser.getPassword() == null
                    || !enterpriseUser.getPassword().equals(enterpriseUser.getConfirmPassword())) {
                return LayuiResultUtil.fail("两次密码输入不一致");
            }
            if (StringUtils.isEmpty(enterpriseUser.getEnterprise_No())) {
                return LayuiResultUtil.fail("请选择企业！");
            }
            this.enterpriseManage.addEnterpriseUser(enterpriseUser);
        } catch (Exception e) {
            return LayuiResultUtil.fail(e.getMessage());
        }
        return LayuiResultUtil.success();
    }

    /**
     * 查看企业信息
     *
     * @return
     * @RequestMapping("enterprise_info")
     * @OperateAnnotation(moduleName = "企业管理", option = "查看企业信息")
     * public String info(@RequestParam(value = "eidArray") List<Integer> eidArray) {
     * if (eidArray == null || eidArray.isEmpty()) {
     * SuperLogger.error("enterprise_preEdit ckids is null");
     * throw new ServiceException("id is not null");
     * }
     * Enterprise enterprise = enterpriseManage.queryEnterpriseById(eidArray.get(0));
     * request.setAttribute("eBean", enterprise);
     * return "/enterprise/info";
     * }
     */

    @RequestMapping("enterprise_list")
    @ResponseBody
    public String list(EnterpriseExt enterpriseExt) {
        enterpriseExt.setBusiness_User_Id(AdminControllerBase.currentBusinessUserId());
        List<Enterprise> enterpriseList = enterpriseManage.queryEnterpriseList(enterpriseExt);
        return new SmsUIObjectMapper().asSuccessString(enterpriseList, enterpriseExt.getPagination());
    }

    /**
     * 企业管理 =>导出
     */
    @RequestMapping("enterprise_exportEnterpriseList")
    @ResponseBody
    public LayUiJsonObjectFmt exportEnterpriseList(EnterpriseExt enterpriseExt) {
        try {
            enterpriseExt.setBusiness_User_Id(AdminControllerBase.currentBusinessUserId());
            enterpriseManage.exportEnterpriseList(enterpriseExt, getAdminDefaultExportFile());
            return new LayUiJsonObjectFmt(LayUiObjectMapper.CODE_SUCCESS, "已提交后台导出任务!");
        }catch (ServiceException e){
            return LayuiResultUtil.fail(e.getMessage());
        }catch (Exception e){
            SuperLogger.error(e.getMessage(),e);
            return LayuiResultUtil.error(e);
        }
    }

    // 异步数据统计
    @RequestMapping("enterprise_queryEnterpriseListTotalData")
    @ResponseBody
    public String queryEnterpriseListTotalData(EnterpriseExt enterpriseExt) {
        enterpriseExt.setBusiness_User_Id(AdminControllerBase.currentBusinessUserId());
        SqlStatisticsEntity entity = enterpriseManage.queryEnterpriseListTotalData(enterpriseExt);
        return new LayUiObjectMapper().asSuccessString(entity);
    }

    @RequestMapping("enterprise_userIndex")
    public String userIndex(Integer ckIds, String limitCode) {
        Enterprise enterprise = enterpriseManage.queryEnterpriseById(ckIds);
        if (enterprise == null) {
            throw new NullPointerException("enterprise is null");
        }
        request.setAttribute("enterprise_No", enterprise.getNo());
        request.setAttribute("limitCode", limitCode);
        setParam(request);
        return "/enterprise/user_list";
    }

    @RequestMapping("enterprise_perUserlist")
    public String perUserlist() {
        setParam(request);
        return "/enterprise/user_list";
    }

    private void setParam(HttpServletRequest request) {
        String apiDocUrl = DatabaseCache.getStringValueBySystemEnvAndCode("api_doc_url", "https://www.showdoc.com.cn/872195016401251?page_id=4702298269132771");
        Code netwayHttpIp = DatabaseCache.getCodeBySortCodeAndCode("netway_http", "netway_http_ip");
        Code netwayHttpPort = DatabaseCache.getCodeBySortCodeAndCode("netway_http", "netway_http_port");
        Code netwayCmppIp = DatabaseCache.getCodeBySortCodeAndCode("netway_cmpp", "netway_cmpp_ip");
        Code netwayCmppPort = DatabaseCache.getCodeBySortCodeAndCode("netway_cmpp", "netway_cmpp_port");
        Code netwaySmppPort = DatabaseCache.getCodeBySortCodeAndCode("netway_smpp", "netway_smpp_port");
        Code netwaySgipPort = DatabaseCache.getCodeBySortCodeAndCode("netway_sgip", "netway_sgip_port");
        Code netwaySmgpPort = DatabaseCache.getCodeBySortCodeAndCode("netway_smgp", "netway_smgp_port");
        request.setAttribute("apiDocUrl", apiDocUrl);
        request.setAttribute("netwayHttpIp", netwayHttpIp);
        request.setAttribute("netwayHttpPort", netwayHttpPort);
        request.setAttribute("netwayCmppIp", netwayCmppIp);
        request.setAttribute("netwayCmppPort", netwayCmppPort);
        request.setAttribute("netwaySmppPort", netwaySmppPort);
        request.setAttribute("netwaySgipPort", netwaySgipPort);
        request.setAttribute("netwaySmgpPort", netwaySmgpPort);
    }

    @RequestMapping("enterprise_userList")
    @ResponseBody
    public String userList(EnterpriseUserExt enterpriseUserExt) {
        List<EnterpriseUser> enterpriseUserList = enterpriseManage.queryEnterpriseUserList(enterpriseUserExt);
        return new SmsUIObjectMapper().asSuccessString(enterpriseUserList, enterpriseUserExt.getPagination());
    }

    /**
     * 企业用户=》导出
     */
    @RequestMapping("enterprise_exportEnterpriseUserList")
    @ResponseBody
    public LayUiJsonObjectFmt exportEnterpriseUserList(EnterpriseUserExt enterpriseUserExt) {
        try {
            enterpriseManage.exportEnterpriseUserList(enterpriseUserExt, getAdminDefaultExportFile());
            return new LayUiJsonObjectFmt(LayUiObjectMapper.CODE_SUCCESS, "已提交后台导出任务!");
        }catch (ServiceException e){
            return LayuiResultUtil.fail(e.getMessage());
        }catch (Exception e){
            SuperLogger.error(e.getMessage(),e);
            return LayuiResultUtil.error(e);
        }
    }

    @RequestMapping("enterprise_limitList")
    @ResponseBody
    public String limitList(EnterpriseLimit enterpriseLimit) {
        if (enterpriseLimit == null) {
            enterpriseLimit = new EnterpriseLimit();
        }
        List<EnterpriseLimit> enterpriseLimitList = enterpriseManage.queryEnterpriseLimitList(enterpriseLimit);
        return new SmsUIObjectMapper().asSuccessString(enterpriseLimitList, enterpriseLimit.getPagination());
    }

    @RequestMapping("enterprise_addLimit")
    @ResponseBody
    @OperateAnnotation(moduleName = "企业管理", option = "添加企业权限")
    @AvoidRepeatableCommitAnnotation(systemModuleName = ADMIN_PLATFORM + "enterprise_addLimit")
    public LayUiJsonObjectFmt addLimit(EnterpriseLimit enterpriseLimit) {
        enterpriseLimit.setCreate_User(getLoginRealName());
        try {
            this.enterpriseManage.addEnterpriseLimit(enterpriseLimit);
        } catch (Exception e) {
            SuperLogger.error(e);
            return LayuiResultUtil.fail(e.getMessage());
        }
        return LayuiResultUtil.success();
    }

    @RequestMapping("enterprise_roleList")
    @ResponseBody
    public String roleList(EnterpriseRole enterpriseRole) {
        if (enterpriseRole == null) {
            enterpriseRole = new EnterpriseRole();
        }
        List<EnterpriseRole> enterpriseRoleList = enterpriseManage.queryEnterpriseRoleList(enterpriseRole);
        return new SmsUIObjectMapper().asSuccessString(enterpriseRoleList, enterpriseRole.getPagination());
    }

    @RequestMapping("enterprise_addRole")
    @ResponseBody
    @OperateAnnotation(moduleName = "企业管理", option = "添加角色")
    @AvoidRepeatableCommitAnnotation(systemModuleName = ADMIN_PLATFORM + "enterprise_addRole")
    public LayUiJsonObjectFmt addRole(EnterpriseRole enterpriseRole) {
        enterpriseRole.setCreate_User(getLoginRealName());
        try {
            this.enterpriseManage.addEnterpriseRole(enterpriseRole);
        } catch (Exception e) {
            SuperLogger.error(e);
            return LayuiResultUtil.fail(e.getMessage());
        }
        return LayuiResultUtil.success();
    }

    /**
     * 分配角色权限列表
     */
    @RequestMapping("enterprise_bindRoleLimitList")
    @ResponseBody
    public LayUiJsonObjectFmt bindRoleLimitList(BaseParamEntity entity) {
        List<EnterpriseLimitExt> enterpriseLimitExtList = enterpriseManage.queryBindRoleLimitList(entity.getCkIds());
        List<EnterpriseLimitExt> ckLimits = enterpriseLimitExtList.stream()
                .filter(item -> null != item.getEnterpriseRole()).collect(Collectors.toList());
        Set<Integer> ckLimitsId = ckLimits.stream().map(EnterpriseLimitExt::getId).collect(Collectors.toSet());
        Map<String, Object> data = new HashMap<>();
        data.put("list", allLimit(enterpriseLimitExtList));// 全部权限
        data.put("checkedId", ckLimitsId);// 该角色已拥有权限
        return LayuiResultUtil.success(data);
    }

    /**
     * 分配角色列表
     *
     * @return
     */
    @RequestMapping("enterprise_bindUserRoleList")
    public String bindUserRoleList(BaseParamEntity entity) {
        try {
            List<EnterpriseRoleExt> enterpriseRoleExtList = enterpriseManage.queryBindUserRoleList(entity.getCkIds());
            request.setAttribute("userId", entity.getCkIds().get(0));
            request.setAttribute("enterpriseRoleExtList", enterpriseRoleExtList);
        } catch (BaseException be) {
            this.setServiceMessage(be.getMessage());
        }
        return "/enterprise/role_bind_list";
    }

    /**
     * 分配角色权限
     *
     * @return
     */
    @RequestMapping("enterprise_bindRoleLimit")
    @ResponseBody
    @OperateAnnotation(moduleName = "企业管理", option = "绑定角色权限")
    public LayUiJsonObjectFmt bindRoleLimit(BaseParamEntity entity) {
        List<EnterpriseRoleLimit> roleLimitList = new ArrayList<EnterpriseRoleLimit>();
        if (entity.getCkLimitId() == null) {
            return LayuiResultUtil.fail("操作失败，请至少选择一个权限!");
        }
        EnterpriseRoleLimit saveRoleLimit = null;
        List<Integer> ckLimitId = entity.getCkLimitId();
        for (int j = 0; j < ckLimitId.size(); j++) {
            saveRoleLimit = new EnterpriseRoleLimit();
            saveRoleLimit.setRole_Id(entity.getCkRoleId().get(0));
            saveRoleLimit.setLimit_Id(entity.getCkLimitId().get(j));
            saveRoleLimit.setCreate_User(this.getLoginRealName());
            roleLimitList.add(saveRoleLimit);
        }
        enterpriseManage.bindRoleLimit(roleLimitList);
        return LayuiResultUtil.success();
    }

    /**
     * 分配用户角色
     *
     * @return
     */
    @RequestMapping("enterprise_bindUserRole")
    @ResponseBody
    @OperateAnnotation(moduleName = "企业管理", option = "绑定企业角色")
    public LayUiJsonObjectFmt bindUserRole(BaseParamEntity entity) {
        List<EnterpriseUserRoles> userRoleList = new ArrayList<EnterpriseUserRoles>();
        if (SuperLogger.isDebugEnabled()) {
            printRequest();
        }
        if (entity.getCkRoleId() == null || entity.getCkRoleId().isEmpty()) {
            return LayuiResultUtil.fail("请选择角色");
        }
        List<Integer> ckRoleIds = entity.getCkRoleId();
        for (Integer ckRoleId : ckRoleIds) {
            if (ckRoleId == null) {
                continue;
            }
            EnterpriseUserRoles saveUserRoles = new EnterpriseUserRoles();
            saveUserRoles.setRole_Id(ckRoleIds.get(0));
            saveUserRoles.setEnterprise_User_Id(entity.getCkIds().get(0));
            saveUserRoles.setCreate_User(this.getLoginRealName());
            userRoleList.add(saveUserRoles);
            enterpriseManage.bindUserRole(userRoleList);
            break;
        }
        return LayuiResultUtil.success();
    }

    @RequestMapping("enterprise_lockEnterprise")
    @ResponseBody
    @OperateAnnotation(moduleName = "企业管理", option = "锁定企业")
    public LayUiJsonObjectFmt lockEnterprise(@RequestParam(value = "ckIds") List<Integer> ckIds) {
        List<Integer> userIds = this.enterpriseManage.updateEnterpriseStatus(ckIds, "Locked");
        return LayuiResultUtil.success();
    }

    @RequestMapping("enterprise_unLockEnterprise")
    @ResponseBody
    @OperateAnnotation(moduleName = "企业管理", option = "解锁企业")
    public LayUiJsonObjectFmt unLockEnterprise(@RequestParam(value = "ckIds") List<Integer> ckIds) {
        this.enterpriseManage.updateEnterpriseStatus(ckIds, "Normal");
        return LayuiResultUtil.success();
    }

    @RequestMapping("enterprise_lockUser")
    @ResponseBody
    @OperateAnnotation(moduleName = "企业管理", option = "锁定用户")
    public LayUiJsonObjectFmt lockUser(@RequestParam(value = "ckIds") List<Integer> ckIds) {
        this.enterpriseManage.updateEnterpriseUserStatus(ckIds, "Locked");
        return LayuiResultUtil.success();
    }

    @RequestMapping("enterprise_unLockUser")
    @ResponseBody
    @OperateAnnotation(moduleName = "企业管理", option = "解锁用户")
    public LayUiJsonObjectFmt unLockUser(@RequestParam(value = "ckIds") List<Integer> ckIds) {
        this.enterpriseManage.updateEnterpriseUserStatus(ckIds, "Normal");
        return LayuiResultUtil.success();
    }

    @RequestMapping("enterprise_deleteEnterprise")
    @ResponseBody
    @OperateAnnotation(moduleName = "企业管理", option = "删除企业")
    public LayUiJsonObjectFmt deleteEnterprise(@RequestParam(value = "ckIds") List<Integer> ckIds) {
        List<Integer> userIds = this.enterpriseManage.updateEnterpriseStatus(ckIds, AccountStatus.DELETE.toString());
        return LayuiResultUtil.success();
    }

    @RequestMapping("enterprise_deleteUser")
    @ResponseBody
    @OperateAnnotation(moduleName = "企业管理", option = "删除用户")
    public LayUiJsonObjectFmt deleteUser(@RequestParam(value = "ckIds") List<Integer> ckIds) {
        this.enterpriseManage.updateEnterpriseUserStatus(ckIds, AccountStatus.DELETE.toString());
        return LayuiResultUtil.success();
    }

    @RequestMapping("enterprise_preEditLimit")
    public String preEditLimit(@RequestParam(value = "ckIds") List<Integer> ckIds) {
        if (ckIds == null || ckIds.isEmpty()) {
            return "/enterprise/edit_limit";
        }
        EnterpriseLimit enterpriseLimit = new EnterpriseLimit();
        enterpriseLimit.setId(ckIds.get(0));
        List<EnterpriseLimit> enterpriseLimitList = this.enterpriseManage.queryEnterpriseLimitList(enterpriseLimit);
        if (enterpriseLimitList == null || enterpriseLimitList.isEmpty()) {
            return "/enterprise/edit_limit";
        }
        enterpriseLimit = enterpriseLimitList.get(0);
        request.setAttribute("enterpriseLimit", enterpriseLimit);
        return "/enterprise/edit_limit";
    }

    @RequestMapping("enterprise_editLimit")
    @ResponseBody
    @OperateAnnotation(moduleName = "企业管理", option = "修改权限")
    public LayUiJsonObjectFmt editLimit(EnterpriseLimit enterpriseLimit) {
        this.enterpriseManage.editLimit(enterpriseLimit);
        return LayuiResultUtil.success();
    }

    @RequestMapping("enterprise_preEditRole")
    public String preEditRole(@RequestParam(value = "ckIds") List<Integer> ckIds) {
        if (ckIds == null || ckIds.isEmpty()) {
            return "/enterprise/edit_role";
        }
        EnterpriseRole enterpriseRole = new EnterpriseRole();
        enterpriseRole.setId(ckIds.get(0));
        List<EnterpriseRole> enterpriseRoleList = this.enterpriseManage.queryEnterpriseRoleList(enterpriseRole);
        if (enterpriseRoleList == null || enterpriseRoleList.isEmpty()) {
            return "/enterprise/edit_role";
        }
        enterpriseRole = enterpriseRoleList.get(0);
        request.setAttribute("enterpriseRole", enterpriseRole);
        return "/enterprise/edit_role";
    }

    @RequestMapping("enterprise_editRole")
    @ResponseBody
    @OperateAnnotation(moduleName = "企业管理", option = "修改角色")
    public LayUiJsonObjectFmt editRole(EnterpriseRole enterpriseRole) {
        this.enterpriseManage.editRole(enterpriseRole);
        return LayuiResultUtil.success();
    }

    /**
     * 限制配置 前置
     *
     * @param entity
     * @return
     */
    @RequestMapping("enterprise_preEditAsrict")
    public String preEditAsrict(BaseParamEntity entity) {
        // preEditUser(entity);
        try {
            request.setAttribute("limitCode", DatabaseCache.getCodeListBySortCode("limit_repeat"));
            EnterpriseUserExt enterpriseUserExt = new EnterpriseUserExt();
            enterpriseUserExt.setId(entity.getCkIds().get(0));
            List<EnterpriseUser> enterpriseUserList = this.enterpriseManage.queryEnterpriseUserList(enterpriseUserExt);
            if (!enterpriseUserList.isEmpty()) {
                EnterpriseUser enterpriseUser = enterpriseUserList.get(0);
                request.setAttribute("enterpriseUser", enterpriseUser);
                if (enterpriseUser.getLimit_Repeat() != null) {
                    ChannelUtil.LimitRepeat limitRepeat = JsonUtil.NON_NULL.readValue(enterpriseUser.getLimit_Repeat(),
                            ChannelUtil.LimitRepeat.class);
                    if (limitRepeat != null) {
                        request.setAttribute("phoneNos", limitRepeat.getPhoneNos());
                        request.setAttribute("content", limitRepeat.getContent());
                    }
                }
                List<Properties> propertiesList = DatabaseCache.queryPropertiesListByFields("EnterpriseUser", String.valueOf(enterpriseUser.getId()), "", "Allowed_Send_Time");
                if (!propertiesList.isEmpty()) {
                    for (Properties properties : propertiesList) {
                        request.setAttribute(properties.getProperty_Name(), properties.getProperty_Value());
                    }
                }
            }
        } catch (Exception e) {
            SuperLogger.error(e.getMessage(), e);
        }
        return "/enterprise/edit_asrict_rule";
    }

    /**
     * 回调配置 前置
     *
     * @param entity
     * @return
     */
    @RequestMapping("enterprise_preEditCallback")
    public String preEditCallback(BaseParamEntity entity) {
        preEditUser(entity);
        return "/enterprise/edit_callback_rule";
    }

    /**
     * 认证企业前置
     *
     * @return
     */
    @RequestMapping("enterprise_preAuthenticate")
    public String preAuthenticate(BaseParamEntity entity) {
        preEdit(entity);
        return "/enterprise/authenticate";
    }

    /**
     * 认证保存
     *
     * @return
     */
    @RequestMapping("enterprise_authenticate")
    @ResponseBody
    @OperateAnnotation(moduleName = "企业管理", option = "认证企业")
    public LayUiJsonObjectFmt authenticate(EnterpriseExt enterpriseExt) throws Exception {
        enterpriseManage.authenticate(enterpriseExt);
        try {
            //2019.12.20 新增需求 审核结果短信通知申请人。
            String platformName = DatabaseCache.getStringValueBySystemEnvAndCode("platform_name", "信相通") + "客户平台";
            String msg = "您在" + platformName + "提交的认证申请未通过，请及时查看拒绝原因。";
            if (Constant.ENTERPRISE_CHECK_STATUS_PASS.equals(enterpriseExt.getAuthentication_State_Code())) {
                msg = "您在" + platformName + "提交的认证申请已通过，欢迎使用。";
            }
            Enterprise enterprise = enterpriseManage.queryEnterpriseById(enterpriseExt.getId());
           //添加国家码，
            String countryCode="86";
            this.sendManage.sendSMS(enterprise.getPhone_No(), msg,countryCode);
        } catch (Exception e) {
            SuperLogger.error(e.getMessage(), e);
        }
        return LayuiResultUtil.success();
    }

    /**
     * 模板列表查询
     *
     * @param smsTemplateExt
     * @return
     */
    @RequestMapping("enterprise_smsTemplateList")
    @ResponseBody
    public String smsTemplateList(SmsTemplateExt smsTemplateExt) {
        List<SmsTemplate> smsTemplateLists = enterpriseManage.querySmsTemplateList(smsTemplateExt);
        return new SmsUIObjectMapper().asSuccessString(smsTemplateLists, smsTemplateExt.getPagination());
    }

    /**
     * 模板审核前置
     *
     * @return
     */
    @RequestMapping("enterprise_preCheckTemplate")
    public String preCheckTemplate(@RequestParam(value = "ckIds") List<Integer> ckIds) {
        SmsTemplateExt smsTemplateExt = new SmsTemplateExt();
        smsTemplateExt.setId(ckIds.get(0));
        List<SmsTemplate> smsTemplateLists = enterpriseManage.querySmsTemplateList(smsTemplateExt);
        if (smsTemplateLists == null || smsTemplateLists.isEmpty()) {
            request.setAttribute("smsTemplateLists", smsTemplateLists);
            return "/enterprise/sms_template_check";
        }
        SmsTemplate smsTemplate = smsTemplateLists.get(0);
        request.setAttribute("smsTemplate", smsTemplate);
        return "/enterprise/sms_template_check";
    }

    /**
     * 模板审核
     *
     * @return
     */
    @RequestMapping("enterprise_checkTemplate")
    @ResponseBody
    @OperateAnnotation(moduleName = "短信模板", option = "模板审核")
    public LayUiJsonObjectFmt checkTemplate(SmsTemplateExt smsTemplateExt) {
        this.enterpriseManage.updateSmsTemplateExt(smsTemplateExt);
        return LayuiResultUtil.success();
    }

    @RequestMapping("enterprise_addSmsTemplate")
    @ResponseBody
    @OperateAnnotation(moduleName = "短信模板", option = "添加模板")
    @AvoidRepeatableCommitAnnotation(systemModuleName = ADMIN_PLATFORM + "admin_addSmsTemplate")
    public LayUiJsonObjectFmt addSmsTemplate(SmsTemplate smsTemplate) {
        try {
            smsTemplate.setApprove_Status(Constant.SMS_TEMPLAT_CHECK_STATUS_PENDING);
            this.enterpriseManage.addSmsTemplate(smsTemplate);
        } catch (ServiceException e) {
            return LayuiResultUtil.fail(e.getMessage());
        } catch (Exception e) {
            return LayuiResultUtil.fail(e.getMessage());
        }
        return LayuiResultUtil.success();
    }

    @RequestMapping("enterprise_preSmsTemplateEdit")
    public String preRoleEdit(BaseParamEntity entity) {
        request.setAttribute("smsTemplate", enterpriseManage.querySmsTemplateById(entity.getCkIds().get(0)));
        return "/enterprise/sms_template_edit";
    }

    @RequestMapping("enterprise_editSmsTemplate")
    @ResponseBody
    @OperateAnnotation(moduleName = "短信模板", option = "编辑模板")
    public LayUiJsonObjectFmt editSmsTemplate(SmsTemplate smsTemplate) {
        try {
            this.enterpriseManage.editSmsTemplate(smsTemplate);
        } catch (ServiceException e) {
            return LayuiResultUtil.fail(e.getMessage());
        }
        return LayuiResultUtil.success();
    }

    @RequestMapping("enterprise_delSmsTemplate")
    @ResponseBody
    @OperateAnnotation(moduleName = "短信模板", option = "删除模板")
    public LayUiJsonObjectFmt delSmsTemplate(BaseParamEntity entity) {
        try {
            this.enterpriseManage.deleteSmsTemplate(entity.getCkIds(), new EnterpriseUser());
        } catch (ServiceException e) {
            return LayuiResultUtil.fail(e.getMessage());
        }
        return LayuiResultUtil.success();
    }

    //短信模板测试前置
    @RequestMapping("preTemplateTry2Try")
    public String preTemplateTry2Try(BaseParamEntity entity) {
        if (entity != null && entity.getCkIds() != null) {
            request.setAttribute("smsTemplate", enterpriseManage.querySmsTemplateById(entity
                    .getCkIds().get(0)));
        } else {
            request.setAttribute("smsTemplate", null);
        }

        return "/netway/template_try_2_try";
    }

    //短信模板测试
    @RequestMapping("template_Try2Try")
    @ResponseBody
    public LayUiJsonObjectFmt templateTry2Try(@RequestParam String templateContent, @RequestParam String smsContent) {
        if (SMSUtil.comparisonOfTheContent(templateContent, smsContent)) {
            return LayuiResultUtil.success("测试通过");
        }
        return LayuiResultUtil.fail("短信内容非法");
    }


    @RequestMapping("enterprise_enterpriseUserFeeIndex")
    public String enterpriseUserFeeIndex(String limitCode, String ckIds) {
        request.setAttribute("limitCode", limitCode);
        request.setAttribute("enterpriseUserId", ckIds);
        return "/enterprise/enterprise_user_fee_list";
    }

    /**
     * 资费列表
     *
     * @param
     * @return
     */
    @RequestMapping("enterprise_enterpriseUserFeeList")
    @ResponseBody
    public String enterpriseUserFeeList(EnterpriseUserFee enterpriseUserFee) {
        List<EnterpriseUserFee> enterpriseUserFeeList = enterpriseManage.queryEnterpriseUserFeeList(enterpriseUserFee);
        return new LayUiObjectMapper().asSuccessString(enterpriseUserFeeList, enterpriseUserFee.getPagination().getTotalCount());
    }

    /**
     * 保存资费
     */
    @RequestMapping("enterprise_addEnterpriseUserFee")
    @ResponseBody
    @OperateAnnotation(moduleName = "用户管理", option = "添加资费")
    @AvoidRepeatableCommitAnnotation(systemModuleName = ADMIN_PLATFORM + "enterprise_addEnterpriseUserFee")
    public LayUiJsonObjectFmt addEnterpriseUserFee(EnterpriseUserFee enterpriseUserFee) {
        try {
            this.enterpriseManage.addEnterpriseUserFee(enterpriseUserFee);
        } catch (ServiceException se) {
            se.printStackTrace();
            return LayuiResultUtil.fail(se.getMessage());
        }
        return LayuiResultUtil.success();
    }

    /**
     * 资费修改前置
     */
    @RequestMapping("enterprise_preEditEnterpriseUserFee")
    public ModelAndView preEditEnterpriseUserFee(BaseParamEntity entity) {
        ModelAndView mv = new ModelAndView();
        EnterpriseUserFee enterpriseUserFee = new EnterpriseUserFee();
        enterpriseUserFee.setId(entity.getCkIds().get(0));
        enterpriseUserFee = this.enterpriseManage.queryEnterpriseUserFeeList(enterpriseUserFee).get(0);
        mv.setViewName("/enterprise/enterprise_user_fee_edit");
        mv.addObject("enterpriseUserFee", enterpriseUserFee);
        return mv;
    }

    /**
     * 资费修改
     */
    @RequestMapping("enterprise_editEnterpriseUserFee")
    @ResponseBody
    @OperateAnnotation(moduleName = "用户管理", option = "资费修改")
    @AvoidRepeatableCommitAnnotation(systemModuleName = ADMIN_PLATFORM + "enterprise_editEnterpriseUserFee")
    public LayUiJsonObjectFmt editEnterpriseUserFee(EnterpriseUserFee enterpriseUserFee) {
        try {
            this.enterpriseManage.editEnterpriseUserFee(enterpriseUserFee);
        } catch (ServiceException se) {
            se.printStackTrace();
            return LayuiResultUtil.fail(se.getMessage());
        }
        return LayuiResultUtil.success();
    }

    /**
     * 删除资费
     */
    @RequestMapping("enterprise_delEnterpriseUserFee")
    @ResponseBody
    @OperateAnnotation(moduleName = "用户管理", option = "删除资费")
    @AvoidRepeatableCommitAnnotation(systemModuleName = ADMIN_PLATFORM + "enterprise_delEnterpriseUserFee")
    public LayUiJsonObjectFmt delEnterpriseUserFee(BaseParamEntity entity) {
        if (ObjectUtils.isEmpty(entity.getCkIds())) {
            return LayuiResultUtil.success();
        }
        this.enterpriseManage.delEnterpriseUserFee(entity.getCkIds(), new EnterpriseUserExt());
        return LayuiResultUtil.success();
    }

    @RequestMapping("enterprise_importSmsTemplate")
    @ResponseBody
    @OperateAnnotation(moduleName = "短信模板", option = "导入模板")
    @AvoidRepeatableCommitAnnotation(systemModuleName = ADMIN_PLATFORM + "enterprise_importSmsTemplate")
    public LayUiJsonObjectFmt importSmsTemplate(SmsTemplate smsTemplate, MultipartFile smsTemplateFile) {
        try {
            smsTemplate.setApprove_Status(AuditStatus.PASS.value());
            smsTemplate.setCreate_Date(new Date());
            this.enterpriseManage.importSmsTemplate(smsTemplate, smsTemplateFile);
        } catch (ServiceException e) {
            return LayuiResultUtil.fail(e.getMessage());
        } catch (Exception e) {
            return LayuiResultUtil.fail(e.getMessage());
        }
        return LayuiResultUtil.success();
    }

    private List<JSONObject> allLimit(List<EnterpriseLimitExt> enterpriseLimitExtList) {
        List<JSONObject> list = new ArrayList<>();
        enterpriseLimitExtList.stream().forEach(item -> {
            int pId = getPId(item.getUp_Code(), enterpriseLimitExtList);
            JSONObject json = new JSONObject();
            json.put("id", item.getId());
            json.put("upId", pId);
            json.put("name", item.getName());
            list.add(json);
        });
        return list;
    }

    private int getPId(String upCode, List<EnterpriseLimitExt> enterpriseLimitExtList) {
        for (int i = 0, len = enterpriseLimitExtList.size(); i < len; i++) {
            if (enterpriseLimitExtList.get(i).getCode().equals(upCode)) {
                return enterpriseLimitExtList.get(i).getId();
            }
        }
        return 0;
    }

    @RequestMapping("enterprise_deleteLimit")
    @ResponseBody
    public LayUiJsonObjectFmt deleteLimit(@RequestParam(value = "ckIds") List<Integer> ckIds) {
        this.enterpriseManage.deleteEnterpriseLimit(ckIds);
        return LayuiResultUtil.success();
    }

    /**
     * 获取企业用户信息
     *
     * @param condition
     * @return
     */
    @RequestMapping("enterprise_getUser")
    @ResponseBody
    public LayUiJsonObjectFmt getEnterpriseUser(EnterpriseUserExt condition) {
        //根据企业id查询企业用户列表
        List<EnterpriseUser> enterpriseUsers = enterpriseManage.queryEnterpriseUserList(condition);
        return LayuiResultUtil.success(enterpriseUsers);
    }

    /**
     * 发送测试前置
     * @return
     */
    @RequestMapping("enterprise_preTestProduct")
    public String preTestProduct(BaseParamEntity entity) {
        request.setAttribute("userId",entity.getCkIds().get(0));
        return "/enterprise/enterprise_user_product_test";
    }

    /**
     * 发送测试
     * @return
     */
    @RequestMapping("enterprise_testProduct")
    @ResponseBody
    public LayUiJsonObjectFmt testProduct(Integer userId,String phoneNos
            ,String content, @RequestParam(value = "importFile",required = false) MultipartFile importFile) {
        try {
            if(importFile != null){//文件发送（Excel）
                sendManage.testSendFile(userId,importFile);
            }else {//立即发生
                sendManage.testSend(userId,phoneNos,content);
            }
            return LayuiResultUtil.success();
        }catch (ServiceException e){
            return LayuiResultUtil.fail(e.getMessage());
        }catch (Exception e){
            SuperLogger.error(e.getMessage(),e);
            return LayuiResultUtil.error(e);
        }
    }

    /**
     * 用户提交速度前置
     * @return
     */
    @RequestMapping("enterprise_preUserSubmitSpeed")
    public String preUserSubmitSpeed(BaseParamEntity entity) {
        List<Integer> ckIds = entity.getCkIds();
        request.setAttribute("ckIds",ckIds);
        return "/enterprise/user_submit_speed";
    }

}
