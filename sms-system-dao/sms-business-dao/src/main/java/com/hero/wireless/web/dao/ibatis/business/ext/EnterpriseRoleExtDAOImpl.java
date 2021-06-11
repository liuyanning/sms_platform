package com.hero.wireless.web.dao.ibatis.business.ext;

import com.hero.wireless.web.dao.business.ext.IEnterpriseRoleExtDAO;
import com.hero.wireless.web.dao.ibatis.MybatisBaseBusinessExtDao;
import com.hero.wireless.web.entity.business.EnterpriseRole;
import com.hero.wireless.web.entity.business.EnterpriseRoleExample;
import com.hero.wireless.web.entity.business.ext.EnterpriseRoleExt;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("enterpriseRoleExtDAO")
public class EnterpriseRoleExtDAOImpl extends
	MybatisBaseBusinessExtDao<EnterpriseRoleExt, EnterpriseRoleExample, EnterpriseRole> implements IEnterpriseRoleExtDAO {

	@Override
	public List<EnterpriseRoleExt> selectBindUserRoleByExample(Integer uId) {
		List<EnterpriseRoleExt> list = getSqlBusinessSessionTemplate()
				.selectList("selectBindUserRoleByExample", uId);
		return list;
	}

}
