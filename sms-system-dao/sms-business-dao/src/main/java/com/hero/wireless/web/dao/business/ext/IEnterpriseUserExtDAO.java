package com.hero.wireless.web.dao.business.ext;


import com.hero.wireless.web.dao.business.IEnterpriseUserDAO;
import com.hero.wireless.web.entity.business.EnterpriseUserExample;
import com.hero.wireless.web.entity.business.ext.EnterpriseExt;
import com.hero.wireless.web.entity.business.ext.EnterpriseUserExt;

import java.util.List;
import java.util.Map;

public interface IEnterpriseUserExtDAO extends IEnterpriseUserDAO<EnterpriseUserExt> {
	List<EnterpriseUserExt> selectRolesAndLimitsByExample(EnterpriseUserExample example);

    List<EnterpriseUserExt> selectUserByEnterpriseAndRoleCode(EnterpriseExt enterpriseInfoExt);

    double getEnterpriseUserAllBalance(EnterpriseUserExt enterpriseUserExt);

    List<Map<String, Object>> queryEnterpriseUserListForExportPage(EnterpriseUserExample example);
}
