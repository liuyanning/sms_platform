package com.hero.wireless.web.service;

import com.hero.wireless.web.entity.business.ext.AdminUserExt;
import java.util.List;

import com.hero.wireless.web.entity.business.AdminLimit;
import com.hero.wireless.web.entity.business.AdminRole;
import com.hero.wireless.web.entity.business.AdminRoleLimit;
import com.hero.wireless.web.entity.business.AdminUser;
import com.hero.wireless.web.entity.business.AdminUserRoles;
import com.hero.wireless.web.entity.business.ext.AdminLimitExt;
import com.hero.wireless.web.entity.business.ext.AdminRoleExt;

/**
 * 用户管理
 * 
 * @author Administrator
 *
 */
public interface IUserManage {
	/**
	 * 管理平台用户登录
	 * 
	 * @param condition
	 * @return
	 */
	AdminUserExt adminUserLogin(AdminUser condition, String googleValidateCode);

	/**
	 * 查询管理平台用户
	 * 
	 * @param condition
	 * @return
	 */
	List<AdminUser> queryAdminUserList(AdminUser condition);

	/**
	 * 增加管理平台用户
	 * 
	 * @param data
	 * @return
	 */
	AdminUser addAdminUser(AdminUser data);

	/**
	 * 锁定管理平台用户
	 * 
	 * @param userIdList
	 * @return
	 */
	boolean lockAdminUser(List<Integer> userIdList);

	/**
	 * 解锁管理平台用户
	 * 
	 * @param userIdList
	 * @return
	 */
	boolean unLockAdminUser(List<Integer> userIdList);

	/**
	 * 查询管理平台角色
	 * 
	 * @param condition
	 * @return
	 */
	List<AdminRole> queryAdminRoleList(AdminRole condition);

	/**
	 * 增加管理平台角色
	 * 
	 * @param data
	 * @return
	 */
	AdminRole addAdminRole(AdminRole data);

	/**
	 * 绑定管理平台用户角色
	 * 
	 * @param userRoleList
	 * @return
	 */
	boolean bindAdminUserRole(List<AdminUserRoles> userRoleList);

	/**
	 * 增加管理平台权限
	 * 
	 * @param data
	 * @return
	 */
	AdminLimit addAdminLimit(AdminLimit data);

	/**
	 * 删除管理平台权限
	 *
	 * @param ids
	 * @return
	 */
	int deleteAdminLimit(List<Integer> ids);

	/**
	 * 绑定管理平台用户角色权限
	 * 
	 * @param roleLimitList
	 * @return
	 */
	boolean bindAdminRoleLimit(List<AdminRoleLimit> roleLimitList);

	/**
	 * 查询管理平台权限
	 * 
	 * @param condition
	 * @return
	 */
	List<AdminLimit> queryAdminLimitList(AdminLimit condition);

	/**
	 * 分配用户角色列表
	 * 
	 * @param userIdList
	 * @return
	 */
	List<AdminRoleExt> queryBindUserRoleList(List<Integer> userIdList);

	/**
	 * 分配角色权限列表
	 * 
	 * @return
	 */
	List<AdminLimitExt> queryBindRoleLimitList(Integer roleId);

	/**
	 * 根据主键修改角色
	 * 
	 * @param data
	 * @return
	 */
	boolean editRoleByPK(AdminRole data);

	/**
	 * 根据主键修改权限
	 * 
	 * @param data
	 * @return
	 */
	boolean editLimitByPK(AdminLimit data);

	/**
	 * 根据ID查询权限
	 *
	 * @param ckLimitId
	 * @return
	 */
	AdminLimit queryAdminLimitById(Integer ckLimitId);

	/**
	 * 根据id查询角色
	 *
	 * @param ckRoleId
	 * @return
	 */
	AdminRole queryRoleById(Integer ckRoleId);

	/**
	 * 修改密码
	 * 
	 * @param userInfo
	 * @param oldPassword
	 * @param type 0:：有oldPassword 修改， 1：无oldPassword 重置
	 * @return
	 */
	AdminUser editPassword(AdminUser userInfo, String oldPassword,int type);

	/**
	 * 添加code
	 * 
	 * @param adminUser
	 */
	void addCode(AdminUser adminUser);

	/**
	 * 查询用户角色
	 * 
	 * @param adminUserRoles
	 * @return
	 */
	List<AdminUserRoles> queryAdminUserRolesList(AdminUserRoles adminUserRoles);

	/**
	 * 获得用户通过角色code
	 * 
	 * @param roleCode
	 * @return
	 */
	List<AdminUser> getAdminUsersByRoleCode(String roleCode);
}
