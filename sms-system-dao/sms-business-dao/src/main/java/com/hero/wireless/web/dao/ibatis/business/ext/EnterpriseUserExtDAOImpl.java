package com.hero.wireless.web.dao.ibatis.business.ext;

import com.hero.wireless.web.dao.business.ext.IEnterpriseUserExtDAO;
import com.hero.wireless.web.dao.ibatis.MybatisBaseBusinessExtDao;
import com.hero.wireless.web.entity.business.EnterpriseUser;
import com.hero.wireless.web.entity.business.EnterpriseUserExample;
import com.hero.wireless.web.entity.business.ext.EnterpriseExt;
import com.hero.wireless.web.entity.business.ext.EnterpriseUserExt;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository("enterpriseUserExtDAO")
public class EnterpriseUserExtDAOImpl
		extends MybatisBaseBusinessExtDao<EnterpriseUserExt, EnterpriseUserExample, EnterpriseUser>
		implements IEnterpriseUserExtDAO {

	public List<EnterpriseUserExt> selectRolesAndLimitsByExample(EnterpriseUserExample example) {
		List<EnterpriseUserExt> list = getSqlBusinessSessionTemplate()
				.selectList("selectRolesAndLimitsByExample", example);
		return list;
	}

	@Override
	public List<EnterpriseUserExt> selectUserByEnterpriseAndRoleCode(EnterpriseExt enterpriseExt) {
		List<EnterpriseUserExt> list = getSqlBusinessSessionTemplate()
				.selectList("selectUserByEnterpriseAndRoleCode", enterpriseExt);
		return list;
	}

	@Override
	public double getEnterpriseUserAllBalance(EnterpriseUserExt enterpriseUserExt) {
		return getSqlBusinessSessionTemplate().selectOne("getEnterpriseUserAllBalance", enterpriseUserExt);
	}

    @Override
    public List<Map<String, Object>> queryEnterpriseUserListForExportPage(EnterpriseUserExample example) {
        return getSqlBusinessSessionTemplate().selectList("queryEnterpriseUserListForExportPage",example);
    }

}
