package com.hero.wireless.web.dao.base;

import org.mybatis.spring.SqlSessionTemplate;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class MybatisBaseDao<T, E> implements IDao<T, E>  {

	@Resource(name="sqlBusinessSessionTemplate")
	protected SqlSessionTemplate sqlBusinessSessionTemplate;
	
	@Resource(name="sqlSendSessionTemplate")
	protected SqlSessionTemplate sqlSendSessionTemplate;

	@Resource(name="sqlSendNoShardingSessionTemplate")
	protected SqlSessionTemplate sqlSendNoShardingSessionTemplate;
	
	public SqlSessionTemplate getSqlBusinessSessionTemplate() {
		return sqlBusinessSessionTemplate;
	}

	public void setSqlBusinessSessionTemplate(SqlSessionTemplate sqlBusinessSessionTemplate) {
		this.sqlBusinessSessionTemplate = sqlBusinessSessionTemplate;
	}

	public SqlSessionTemplate getSqlSendSessionTemplate() {
		return sqlSendSessionTemplate;
	}

	public void setSqlSendSessionTemplate(SqlSessionTemplate sqlSendSessionTemplate) {
		this.sqlSendSessionTemplate = sqlSendSessionTemplate;
	}

	public SqlSessionTemplate getSqlSendNoShardingSessionTemplate() {
		return sqlSendNoShardingSessionTemplate;
	}

	public void setSqlSendNoShardingSessionTemplate(SqlSessionTemplate sqlSendNoShardingSessionTemplate) {
		this.sqlSendNoShardingSessionTemplate = sqlSendNoShardingSessionTemplate;
	}

	public MybatisBaseDao() {
		super();
	}
	
	protected abstract SqlSessionTemplate currentSqlSessionTemplate();
	/**
	 * 
	 * @return
	 */
	protected String sqlStatmentNamespace(String statmentId) {
		Class<?>[] interfaces = this.getClass().getInterfaces();
		Class<?>[] baseInterface = interfaces[0].getInterfaces();
		String np = baseInterface[0].getName();
		return np + "." + statmentId;
	}

	@Override
	public int countByExample(E example) {
		return currentSqlSessionTemplate().selectOne(this.sqlStatmentNamespace("countByExample"), example);
	}

	@Override
	public int deleteByExample(E example) {
		return currentSqlSessionTemplate().delete(this.sqlStatmentNamespace("deleteByExample"), example);
	}

	@Override
	public int deleteByPrimaryKey(Integer id) {
		return currentSqlSessionTemplate().delete(this.sqlStatmentNamespace("deleteByPrimaryKey"), id);
	}
	
	@Override
	public int deleteByPrimaryKey(Long id) {
		return currentSqlSessionTemplate().delete(this.sqlStatmentNamespace("deleteByPrimaryKey"), id);
	}

	@Override
	public int insert(T record) {
		return currentSqlSessionTemplate().insert(this.sqlStatmentNamespace("insert"), record);
	}

	@Override
	public int insertSelective(T record) {
		return currentSqlSessionTemplate().insert(this.sqlStatmentNamespace("insertSelective"), record);
	}

	@Override
	public List<T> selectByExample(E example) {
		return currentSqlSessionTemplate().selectList(this.sqlStatmentNamespace("selectByExample"), example);
	}

	@Override
	public List<T> selectByExamplePage(E example) {
		throw new UnsupportedOperationException();
	}

	@Override
	public T selectByPrimaryKey(Integer id) {
		return currentSqlSessionTemplate().selectOne(this.sqlStatmentNamespace("selectByPrimaryKey"), id);
	}
	
	@Override
	public T selectByPrimaryKey(Long id) {
		return currentSqlSessionTemplate().selectOne(this.sqlStatmentNamespace("selectByPrimaryKey"), id);
	}

	@Override
	public int updateByExampleSelective(E example) {
		return currentSqlSessionTemplate().update(this.sqlStatmentNamespace("updateByExampleSelective"), example);
	}

	@Override
	public int updateByExample(E example) {
		return currentSqlSessionTemplate().update(this.sqlStatmentNamespace("updateByExample"), example);
	}

	@Override
	public int updateByPrimaryKeySelective(T record) {
		return currentSqlSessionTemplate().update(this.sqlStatmentNamespace("updateByPrimaryKeySelective"), record);
	}

	@Override
	public int updateByPrimaryKey(T record) {
		return currentSqlSessionTemplate().update(this.sqlStatmentNamespace("updateByPrimaryKey"), record);
	}
	
	@Override
	public int insertList(List<T> list) {
		return currentSqlSessionTemplate().insert(this.sqlStatmentNamespace("insertList"), list);
	}

	@Override
	public int updateByExampleSelective(T record, E example) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("record", record);
		map.put("example", example);
		return currentSqlSessionTemplate().update(this.sqlStatmentNamespace("updateByExampleSelective"), map);
	}

	@Override
	public int updateByExample(T record, E example) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("record", record);
		map.put("example", example);
		return currentSqlSessionTemplate().update(this.sqlStatmentNamespace("updateByExample"), map);
	}
}
