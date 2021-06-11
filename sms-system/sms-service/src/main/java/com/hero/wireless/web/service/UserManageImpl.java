package com.hero.wireless.web.service;

import com.drondea.wireless.config.ResultStatus;
import com.drondea.wireless.util.*;
import com.hero.wireless.enums.AccountStatus;
import com.hero.wireless.web.config.DatabaseCache;
import com.hero.wireless.web.config.ExceptionKey;
import com.hero.wireless.web.config.SystemKey;
import com.hero.wireless.web.dao.business.IAdminLimitDAO;
import com.hero.wireless.web.dao.business.IAdminRoleDAO;
import com.hero.wireless.web.dao.business.IAdminRoleLimitDAO;
import com.hero.wireless.web.dao.business.IAdminUserDAO;
import com.hero.wireless.web.dao.business.IAdminUserRolesDAO;
import com.hero.wireless.web.dao.business.ICodeDAO;
import com.hero.wireless.web.dao.business.ext.IAdminLimitExtDAO;
import com.hero.wireless.web.dao.business.ext.IAdminRoleExtDAO;
import com.hero.wireless.web.dao.business.ext.IAdminUserExtDAO;
import com.hero.wireless.web.entity.business.AdminLimit;
import com.hero.wireless.web.entity.business.AdminLimitExample;
import com.hero.wireless.web.entity.business.AdminRole;
import com.hero.wireless.web.entity.business.AdminRoleExample;
import com.hero.wireless.web.entity.business.AdminRoleLimit;
import com.hero.wireless.web.entity.business.AdminRoleLimitExample;
import com.hero.wireless.web.entity.business.AdminUser;
import com.hero.wireless.web.entity.business.AdminUserExample;
import com.hero.wireless.web.entity.business.AdminUserRoles;
import com.hero.wireless.web.entity.business.AdminUserRolesExample;
import com.hero.wireless.web.entity.business.Code;
import com.hero.wireless.web.entity.business.ext.AdminLimitExt;
import com.hero.wireless.web.entity.business.ext.AdminRoleExt;
import com.hero.wireless.web.entity.business.ext.AdminUserExt;
import com.hero.wireless.web.exception.BaseException;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import static com.hero.wireless.web.config.MessagesManger.getSystemMessages;

/**
 * 真正的贫穷不是没有钱，而是没有思想 UserManageImpl
 *
 * @author 张丽阳
 * @version 1.0.0
 * @createTime 2015年6月2日 下午3:58:20
 */
@Service("userManage")
public class UserManageImpl implements IUserManage {
    @Resource(name = "IAdminUserDAO")
    private IAdminUserDAO<AdminUser> adminUserDAO;
    @Resource(name = "IAdminLimitDAO")
    private IAdminLimitDAO<AdminLimit> adminLimitDAO;
    @Resource(name = "IAdminRoleDAO")
    private IAdminRoleDAO<AdminRole> adminRoleDAO;
    @Resource(name = "IAdminRoleLimitDAO")
    private IAdminRoleLimitDAO<AdminRoleLimit> adminRoleLimitDAO;
    @Resource(name = "IAdminUserRolesDAO")
    private IAdminUserRolesDAO<AdminUserRoles> adminUserRolesDAO;
    @Resource(name = "adminUserExtDAO")
    private IAdminUserExtDAO adminUserExtDAO;
    @Resource(name = "adminRoleExtDAO")
    private IAdminRoleExtDAO adminRoleExtDAO;
    @Resource(name = "adminLimitExtDAO")
    private IAdminLimitExtDAO adminLimitExtDAO;
    @Resource(name = "ICodeDAO")
    private ICodeDAO<Code> codeDAO;

    @Override
    public AdminUserExt adminUserLogin(AdminUser condition, String googleValidateCode) {
        //rsa解密
        condition.setPassword(getPlainPassword(condition.getPassword()));

        // 用户名查询用户信息
        AdminUser userNameEntity = new AdminUser();
        userNameEntity.setUser_Name(condition.getUser_Name());
        List<AdminUser> userNameEntityList = this.queryAdminUserList(userNameEntity);
        if (userNameEntityList == null || userNameEntityList.isEmpty()) {
            throw new ServiceException(ResultStatus.USER_PASSWORD_ERROR);
        }
        userNameEntity = userNameEntityList.get(0);

        if (!StringUtils.isEmpty(userNameEntity.getStatus())
                && !userNameEntity.getStatus().equals(getSystemMessages(SystemKey.USER_STATUS_NORMAL))) {
            throw new BaseException("该用户已被锁定!");
        }
        // 用户登录
        AdminUserExample auie = new AdminUserExample();
        AdminUserExample.Criteria cri = auie.createCriteria();
        cri.andUser_NameEqualTo(condition.getUser_Name());
        cri.andPasswordEqualTo(SecretUtil.MD5(condition.getPassword()));
        List<AdminUserExt> auiList = adminUserExtDAO.selectRolesAndLimitsByExample(auie);

        // 更新登录信息
        AdminUser updateUserLoginInfo = new AdminUser();
        updateUserLoginInfo.setCurrent_Login_Time(new Date());
        updateUserLoginInfo.setCurrent_Login_IP(condition.getCurrent_Login_IP());
        updateUserLoginInfo.setLast_Login_Time(userNameEntity.getCurrent_Login_Time());
        updateUserLoginInfo.setLast_Login_IP(userNameEntity.getCurrent_Login_IP());
        AdminUserExample updateUserLoginInfoExample = new AdminUserExample();
        AdminUserExample.Criteria updateUserLoginInfoCri = updateUserLoginInfoExample.createCriteria();
        updateUserLoginInfoCri.andUser_NameEqualTo(condition.getUser_Name());
        if (auiList == null || auiList.isEmpty()) {
            int loginFaildCount = userNameEntity.getLogin_Faild_Count() == null ? 0 : userNameEntity.getLogin_Faild_Count();
            loginFaildCount++;
            updateUserLoginInfo.setLogin_Faild_Count(loginFaildCount);
            int loginFaildLockCount = userNameEntity.getLogin_Faild_Lock_Count() == null ? 0 : userNameEntity.getLogin_Faild_Lock_Count();
            SuperLogger.debug(loginFaildLockCount);
            if (loginFaildCount > loginFaildLockCount) {
                updateUserLoginInfo.setStatus("Locked");
            }
            adminUserDAO.updateByExampleSelective(updateUserLoginInfo, updateUserLoginInfoExample);// 更新数据库
            throw new ServiceException(ResultStatus.USER_PASSWORD_ERROR);
        }
        AdminUserExt loginResult = auiList.get(0);
        updateUserLoginInfo.setLogin_Faild_Count(0);
        adminUserDAO.updateByExampleSelective(updateUserLoginInfo, updateUserLoginInfoExample);// 更新数据库
        loginResult.setCurrent_Login_IP(updateUserLoginInfo.getCurrent_Login_IP());
        loginResult.setCurrent_Login_Time(updateUserLoginInfo.getCurrent_Login_Time());
        loginResult.setLast_Login_IP(updateUserLoginInfo.getLast_Login_IP());
        loginResult.setLast_Login_Time(updateUserLoginInfo.getLast_Login_Time());

        return loginResult;
    }

    @Override
    public List<AdminUser> queryAdminUserList(AdminUser condition) {
        AdminUserExample userInfoExample = new AdminUserExample();
        SuperLogger.debug("设置分页信息.....");
        userInfoExample.setPagination(condition.getPagination());
        AdminUserExample.Criteria cri = userInfoExample.createCriteria();
        if (!StringUtils.isEmpty(condition.getUser_Name())) {
            cri.andUser_NameEqualTo(condition.getUser_Name());
        }
        if (condition.getId() != null) {
            cri.andIdEqualTo(condition.getId());
        }
        if (!StringUtils.isEmpty(condition.getReal_Name())) {
            cri.andReal_NameLike("%" + condition.getReal_Name() + "%");
        }
        if (!StringUtils.isEmpty(condition.getStatus())) {
            cri.andStatusEqualTo(condition.getStatus());
        }
        return adminUserDAO.selectByExamplePage(userInfoExample);
    }

    @Override
    public AdminUser addAdminUser(AdminUser data) {

        // 查询用户名是否存在
        AdminUserExample userInfoExample = new AdminUserExample();
        userInfoExample.createCriteria().andUser_NameEqualTo(data.getUser_Name());
        List<AdminUser> auiList = adminUserDAO.selectByExample(userInfoExample);
        if (auiList != null && auiList.size() > 0) {
           throw new ServiceException(ResultStatus.USER_NAME_EXSITS);
        }

        // MD5加密
        data.setPassword(SecretUtil.MD5(data.getPassword()));
        data.setGoogle_ID_Key(GoogleAuthenticatorUtil.generateSecretKey());//谷歌key
        data.setStatus(AccountStatus.NORMAL.toString());
        data.setLogin_Faild_Count(0);
        data.setLogin_Faild_Lock_Count(5);
        data.setCreate_Date(new Date());
        adminUserDAO.insert(data);
        return data;
    }

    @Override
    public boolean lockAdminUser(List<Integer> userIdList) {
        if (userIdList == null || userIdList.size() == 0) {
            return true;
        }
        AdminUserExample auie = new AdminUserExample();
        AdminUserExample.Criteria cri = auie.createCriteria();
        cri.andIdIn(userIdList);

        AdminUser userInfoUpdateData = new AdminUser();
        userInfoUpdateData.setStatus("Locked");
        adminUserDAO.updateByExampleSelective(userInfoUpdateData, auie);
        return true;
    }

    @Override
    public boolean unLockAdminUser(List<Integer> userIdList) {
        if (userIdList == null || userIdList.size() == 0) {
            return true;
        }
        AdminUserExample auie = new AdminUserExample();
        AdminUserExample.Criteria cri = auie.createCriteria();
        cri.andIdIn(userIdList);

        AdminUser userInfoUpdateData = new AdminUser();
        userInfoUpdateData.setStatus("Normal");
        userInfoUpdateData.setLogin_Faild_Count(0);//登录失败次数清零
        adminUserDAO.updateByExampleSelective(userInfoUpdateData, auie);
        return true;
    }

    @Override
    public List<AdminRole> queryAdminRoleList(AdminRole condition) {
        AdminRoleExample example = new AdminRoleExample();
        AdminRoleExample.Criteria cri = example.createCriteria();
        if (!StringUtils.isEmpty(condition.getName())) {
            cri.andNameLike(condition.getName());
        }
        if (!StringUtils.isEmpty(condition.getCode())) {
            cri.andCodeEqualTo(condition.getCode());
        }
        if (condition.getId() != null) {
            cri.andIdEqualTo(condition.getId());
        }
        example.setPagination(condition.getPagination());
        return adminRoleDAO.selectByExamplePage(example);
    }

    @Override
    public AdminRole addAdminRole(AdminRole data) {
        AdminRole newBean = new AdminRole();
        newBean.setCode(data.getCode());
        List<AdminRole> list = this.queryAdminRoleList(newBean);
        if (list != null && !list.isEmpty()) {
            throw new ServiceException(ResultStatus.ROLE_CODE_EXSITS, data.getCode());
        }
        data.setCreate_Date(new Date());
        this.adminRoleDAO.insert(data);
        return data;
    }

    @Override
    public boolean bindAdminUserRole(List<AdminUserRoles> userRoleList) {
        for (AdminUserRoles adminUserRoles : userRoleList) {
            // 删除现有的角色
            AdminUserRolesExample example = new AdminUserRolesExample();
            example.createCriteria().andUser_IdEqualTo(adminUserRoles.getUser_Id());
            this.adminUserRolesDAO.deleteByExample(example);
        }
        for (AdminUserRoles adminUserRoles : userRoleList) {
            adminUserRoles.setCreate_Date(new Date());
            this.adminUserRolesDAO.insert(adminUserRoles);
        }
        return true;
    }
//添加权限
    @Override
    public AdminLimit addAdminLimit(AdminLimit data) {
        AdminLimit adminLimit =new AdminLimit();
        adminLimit.setCode(data.getCode());
        List<AdminLimit> adminLimits = queryAdminLimitList(adminLimit);
        if (adminLimits!=null && !adminLimits.isEmpty()){
          throw new ServiceException(ResultStatus.LIMIT_CODE_EXSITS,data.getCode());
        }
        data.setCreate_Date(new Date());
        this.adminLimitDAO.insert(data);
        return data;
    }

    @Override
    public boolean bindAdminRoleLimit(List<AdminRoleLimit> roleLimitList) {
        // 删除现有的权限
        List<Integer> roleIds = new ArrayList<>();
        for (AdminRoleLimit adminRoleLimit : roleLimitList) {
            roleIds.add(adminRoleLimit.getRole_Id());
        }
        if (!roleIds.isEmpty()) {
            AdminRoleLimitExample example = new AdminRoleLimitExample();
            example.createCriteria().andRole_IdIn(roleIds);
            this.adminRoleLimitDAO.deleteByExample(example);
        }
        for (AdminRoleLimit adminRoleLimit : roleLimitList) {
            adminRoleLimit.setCreate_Date(new Date());
        }
        if (!roleLimitList.isEmpty()) {
            this.adminRoleLimitDAO.insertList(roleLimitList);
        }
        return true;
    }

    @Override
    public List<AdminLimit> queryAdminLimitList(AdminLimit condition) {
        AdminLimitExample example = new AdminLimitExample();
        AdminLimitExample.Criteria cri = example.createCriteria();
        if (!StringUtils.isEmpty(condition.getUp_Code())) {
            cri.andUp_CodeEqualTo(condition.getUp_Code());
        }
        if (!StringUtils.isEmpty(condition.getCode())) {
            cri.andCodeEqualTo(condition.getCode());
        }
        if (!StringUtils.isEmpty(condition.getName())) {
            cri.andNameLike("%" + condition.getName() + "%");
        }
        if (condition.getId() != null) {
            cri.andIdEqualTo(condition.getId());
        }
        example.setOrderByClause(" code asc ");
        example.setPagination(condition.getPagination());
        return adminLimitDAO.selectByExamplePage(example);
    }

    @Override
    public List<AdminRoleExt> queryBindUserRoleList(List<Integer> userIdList) {
        if (userIdList == null || userIdList.isEmpty()) {
            throw new BaseException(ExceptionKey.ADMIN_USER_ROLE_1);
        }
        if (userIdList.size() > 1) {
            throw new BaseException(ExceptionKey.ADMIN_USER_ROLE_2);
        }
        return this.adminRoleExtDAO.selectBindUserRoleByExample(userIdList.get(0));
    }

    @Override
    public List<AdminLimitExt> queryBindRoleLimitList(Integer roleId) {
        return adminLimitExtDAO.selectBindRoleLimitByExample(roleId);
    }

    /**
     * 根据主键修改角色
     *
     * @param data 参数
     * @return Boolean 修改成功返回true
     */
    public boolean editRoleByPK(AdminRole data) {
        this.adminRoleDAO.updateByPrimaryKeySelective(data);
        return true;
    }

    /**
     * 根据主键修改权限
     *
     * @param data 参数
     * @return 修改权限成功返回true
     */
    public boolean editLimitByPK(AdminLimit data) {
        this.adminLimitDAO.updateByPrimaryKeySelective(data);
        return true;
    }

    @Override
    public AdminUser editPassword(AdminUser userInfo, String oldPassword,int type) {
        if(0 == type) {
        	AdminUserExample example = new AdminUserExample();
            AdminUserExample.Criteria cri = example.createCriteria();
            cri.andUser_NameEqualTo(userInfo.getUser_Name());
            // rsa解密
            oldPassword = getPlainPassword(oldPassword);
            cri.andPasswordEqualTo(SecretUtil.MD5(oldPassword));
            List<AdminUser> userList = this.adminUserDAO.selectByExample(example);
            if (userList == null || userList.isEmpty()) {
            	throw new ServiceException(ResultStatus.USER_PASSWORD_ERROR);
            }
        }
        //rsa解密
        userInfo.setPassword(getPlainPassword(userInfo.getPassword()));
        // MD5加密
        userInfo.setPassword(SecretUtil.MD5(userInfo.getPassword()));
        adminUserDAO.updateByPrimaryKeySelective(userInfo);
        return userInfo;
    }

    @Override
    public void addCode(AdminUser adminUser) {
        Code code = new Code();
        code.setSort_Code("BusinessMan");
        code.setCode(adminUser.getId() + "");
        code.setName(adminUser.getReal_Name());
        code.setRemark("用户的商务");
        code.setCreate_Date(new Date());
        codeDAO.insert(code);
    }

    @Override
    public List<AdminUserRoles> queryAdminUserRolesList(AdminUserRoles adminUserRoles) {
        AdminUserRolesExample example = new AdminUserRolesExample();
        AdminUserRolesExample.Criteria cri = example.createCriteria();
        if (adminUserRoles.getRole_Id() != null) {
            cri.andRole_IdEqualTo(adminUserRoles.getRole_Id());
        }
        return adminUserRolesDAO.selectByExample(example);
    }

    @Override
    public List<AdminUser> getAdminUsersByRoleCode(String roleCode) {
        return adminUserExtDAO.getAdminUsersByRoleCode(roleCode);
    }

    @Override
    public AdminLimit queryAdminLimitById(Integer ckLimitId) {

        return adminLimitDAO.selectByPrimaryKey(ckLimitId);
    }

    @Override
    public AdminRole queryRoleById(Integer ckRoleId) {

        return adminRoleDAO.selectByPrimaryKey(ckRoleId);
    }

    @Override
    public int deleteAdminLimit(List<Integer> ids) {
        if (ObjectUtils.isEmpty(ids)) {
            throw new BaseException("请选择权限");
        }
        return ids.stream().reduce(0, (row, id) -> {
			AdminLimit limit = this.adminLimitDAO.selectByPrimaryKey(id);
			AdminLimitExample deleteExample = new AdminLimitExample();
			deleteExample.createCriteria().andCodeLike(limit.getCode() + "%");
			return this.adminLimitDAO.deleteByExample(deleteExample);
		}, Integer::sum);
    }

    private String getPlainPassword(String encryptPassword) {
        //rsa解密
        String passwordPrivateKey = DatabaseCache.getStringValueBySystemEnvAndCode("password_private_key", "");
        String plainText = null;
        if (StringUtils.isNotBlank(passwordPrivateKey)) {
            plainText = CertificateUtil.decryptByPrivateKey(encryptPassword, passwordPrivateKey);
            if (StringUtils.isEmpty(plainText)) {
                SuperLogger.error("密码解密错误，请检查私钥");
                throw new ServiceException(ResultStatus.ERROR);
            }
        }
        return plainText;
    }
}
