package com.hero.wireless.web.dao.business.ext;

import com.hero.wireless.web.dao.business.IAdminUserDAO;
import com.hero.wireless.web.entity.business.AdminUser;
import com.hero.wireless.web.entity.business.AdminUserExample;
import com.hero.wireless.web.entity.business.ext.AdminUserExt;

import java.util.List;


public interface IAdminUserExtDAO extends IAdminUserDAO<AdminUserExt> {
	List<AdminUserExt> selectRolesAndLimitsByExample(AdminUserExample example);

    List<AdminUser> getAdminUsersByRoleCode(String roleCode);
}
