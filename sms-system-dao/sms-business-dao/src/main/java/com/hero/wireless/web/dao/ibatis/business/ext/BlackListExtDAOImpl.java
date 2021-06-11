package com.hero.wireless.web.dao.ibatis.business.ext;

import com.hero.wireless.web.dao.business.ext.IBlackListExtDAO;
import com.hero.wireless.web.dao.ibatis.MybatisBaseBusinessExtDao;
import com.hero.wireless.web.entity.business.BlackList;
import com.hero.wireless.web.entity.business.BlackListExample;
import com.hero.wireless.web.entity.business.ext.BlackListExt;
import org.springframework.stereotype.Repository;

@Repository("blackListExtDAO")
public class BlackListExtDAOImpl extends MybatisBaseBusinessExtDao<BlackListExt, BlackListExample, BlackList> implements
		IBlackListExtDAO {
}
