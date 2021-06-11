package com.hero.wireless.web.dao.business.ext;

import com.hero.wireless.web.dao.business.IAdminLimitDAO;
import com.hero.wireless.web.entity.business.ext.AdminLimitExt;

import java.util.List;

public interface IAdminLimitExtDAO extends IAdminLimitDAO<AdminLimitExt> {
	List<AdminLimitExt> selectBindRoleLimitByExample(Integer rId);
}
