package com.hero.wireless.web.dao.business.ext;

import com.hero.wireless.web.dao.business.IAdminRoleDAO;
import com.hero.wireless.web.entity.business.ext.AdminRoleExt;

import java.util.List;

public interface IAdminRoleExtDAO extends IAdminRoleDAO<AdminRoleExt> {
	List<AdminRoleExt> selectBindUserRoleByExample(Integer uId);
}
