package com.hero.wireless.web.dao.ibatis.business.ext;

import com.hero.wireless.web.dao.business.ext.IAdminRoleExtDAO;
import com.hero.wireless.web.dao.ibatis.MybatisBaseBusinessExtDao;
import com.hero.wireless.web.entity.business.AdminRole;
import com.hero.wireless.web.entity.business.AdminRoleExample;
import com.hero.wireless.web.entity.business.ext.AdminRoleExt;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("adminRoleExtDAO")
public class AdminRoleExtDAOImpl extends MybatisBaseBusinessExtDao<AdminRoleExt, AdminRoleExample, AdminRole>
		implements IAdminRoleExtDAO {

	@Override
	public List<AdminRoleExt> selectBindUserRoleByExample(Integer uId) {
		List<AdminRoleExt> list = getSqlBusinessSessionTemplate()
				.selectList("selectBindAdminUserRoleByExample", uId);
		return list;
	}

}
