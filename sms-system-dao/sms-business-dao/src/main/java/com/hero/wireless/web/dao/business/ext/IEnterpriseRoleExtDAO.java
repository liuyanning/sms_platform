package com.hero.wireless.web.dao.business.ext;

import com.hero.wireless.web.dao.business.IEnterpriseRoleDAO;
import com.hero.wireless.web.entity.business.ext.EnterpriseRoleExt;

import java.util.List;

public interface IEnterpriseRoleExtDAO extends IEnterpriseRoleDAO<EnterpriseRoleExt> {
	List<EnterpriseRoleExt> selectBindUserRoleByExample(Integer uId);
}
