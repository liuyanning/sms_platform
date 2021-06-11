package com.hero.wireless.web.dao.ibatis.business.ext;

import com.hero.wireless.web.dao.business.ext.IAdminUserExtDAO;
import com.hero.wireless.web.dao.ibatis.MybatisBaseBusinessExtDao;
import com.hero.wireless.web.entity.business.AdminUser;
import com.hero.wireless.web.entity.business.AdminUserExample;
import com.hero.wireless.web.entity.business.ext.AdminUserExt;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("adminUserExtDAO")
public class AdminUserExtDAOImpl extends MybatisBaseBusinessExtDao<AdminUserExt, AdminUserExample, AdminUser>
		implements IAdminUserExtDAO {

	public List<AdminUserExt> selectRolesAndLimitsByExample(AdminUserExample example) {
		List<AdminUserExt> list = getSqlBusinessSessionTemplate()
				.selectList("selectAdminRolesAndLimitsByExample", example);
		return list;
	}

	@Override
	public List<AdminUser> getAdminUsersByRoleCode(String roleCode) {
		List<AdminUser> list = getSqlBusinessSessionTemplate()
				.selectList("getAdminUsersByRoleCode", roleCode);
		return list;
	}

}
