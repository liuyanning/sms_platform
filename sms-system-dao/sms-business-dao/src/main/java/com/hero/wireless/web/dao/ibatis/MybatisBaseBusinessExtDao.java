package com.hero.wireless.web.dao.ibatis;

import com.hero.wireless.web.dao.base.MybatisBaseDao;
import com.hero.wireless.web.dao.ext.IExtDAO;
import com.hero.wireless.web.entity.ext.SqlStatisticsEntity;
import org.mybatis.spring.SqlSessionTemplate;

import java.util.List;

public  class MybatisBaseBusinessExtDao<T, E, BT> extends MybatisBaseDao<BT, E> implements IExtDAO<T, E> {
	@Override
	public int countExtByExample(E example) {
		return getSqlBusinessSessionTemplate().selectOne(this.sqlStatmentNamespace("countExtByExample"), example);
	}

	@Override
	public List<T> selectExtByExample(E example) {
		return getSqlBusinessSessionTemplate().selectList(this.sqlStatmentNamespace("selectExtByExample"), example);
	}

	@Override
	public List<T> selectExtByExamplePage(E example) {
		return getSqlBusinessSessionTemplate().selectList(this.sqlStatmentNamespace("selectExtByExample"), example);
	}
	public <M extends SqlStatisticsEntity> M statisticsExtByExample(E example) {
		return getSqlBusinessSessionTemplate().selectOne(this.sqlStatmentNamespace("statisticsExtByExample"), example);
	}

	@Override
	protected SqlSessionTemplate currentSqlSessionTemplate() {
		return getSqlBusinessSessionTemplate();
	}

}
