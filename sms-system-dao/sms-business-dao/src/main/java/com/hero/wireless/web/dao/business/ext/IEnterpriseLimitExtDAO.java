package com.hero.wireless.web.dao.business.ext;

import com.hero.wireless.web.dao.business.IEnterpriseLimitDAO;
import com.hero.wireless.web.entity.business.ext.EnterpriseLimitExt;

import java.util.List;

public interface IEnterpriseLimitExtDAO extends IEnterpriseLimitDAO<EnterpriseLimitExt> {
	List<EnterpriseLimitExt> selectBindRoleLimitByExample(Integer rId);
}
