package com.hero.wireless.web.dao.ibatis.business.ext;

import com.hero.wireless.web.dao.business.ext.IEnterpriseExtDAO;
import com.hero.wireless.web.dao.ibatis.MybatisBaseBusinessExtDao;
import com.hero.wireless.web.entity.business.Enterprise;
import com.hero.wireless.web.entity.business.EnterpriseExample;
import com.hero.wireless.web.entity.business.ext.EnterpriseExt;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository("enterpriseExtDAO")
public class EnterpriseExtDAOImpl extends
	MybatisBaseBusinessExtDao<EnterpriseExt, EnterpriseExample, Enterprise> implements IEnterpriseExtDAO {

    @Override
    public List<Map<String, Object>> queryEnterpriseListForExportPage(EnterpriseExample example) {
        return getSqlBusinessSessionTemplate().selectList("queryEnterpriseListForExportPage",example);
    }
}
