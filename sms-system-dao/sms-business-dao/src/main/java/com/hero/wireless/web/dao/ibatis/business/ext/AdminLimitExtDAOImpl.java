package com.hero.wireless.web.dao.ibatis.business.ext;

import com.hero.wireless.web.dao.business.ext.IAdminLimitExtDAO;
import com.hero.wireless.web.dao.ibatis.MybatisBaseBusinessExtDao;
import com.hero.wireless.web.entity.business.AdminLimit;
import com.hero.wireless.web.entity.business.AdminLimitExample;
import com.hero.wireless.web.entity.business.ext.AdminLimitExt;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("adminLimitExtDAO")
public class AdminLimitExtDAOImpl extends MybatisBaseBusinessExtDao<AdminLimitExt, AdminLimitExample, AdminLimit>
		implements IAdminLimitExtDAO {

	@Override
	public List<AdminLimitExt> selectBindRoleLimitByExample(Integer rId) {
		List<AdminLimitExt> list = getSqlBusinessSessionTemplate()
				.selectList("selectBindAdminRoleLimitByExample", rId);
		return list;
	}

}
