package com.hero.wireless.web.dao.ibatis.business.ext;

import com.hero.wireless.web.dao.business.ext.IEnterpriseLimitExtDAO;
import com.hero.wireless.web.dao.ibatis.MybatisBaseBusinessExtDao;
import com.hero.wireless.web.entity.business.EnterpriseLimit;
import com.hero.wireless.web.entity.business.EnterpriseLimitExample;
import com.hero.wireless.web.entity.business.ext.EnterpriseLimitExt;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("enterpriseLimitExtDAO")
public class EnterpriseLimitExtDAOImpl
		extends MybatisBaseBusinessExtDao<EnterpriseLimitExt, EnterpriseLimitExample, EnterpriseLimit>
		implements IEnterpriseLimitExtDAO {

	@Override
	public List<EnterpriseLimitExt> selectBindRoleLimitByExample(Integer rId) {
		List<EnterpriseLimitExt> list = getSqlBusinessSessionTemplate()
				.selectList("selectBindRoleLimitByExample", rId);
		return list;
	}

}
