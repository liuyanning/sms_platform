package com.hero.wireless.web.dao.ext;

import com.hero.wireless.web.entity.ext.SqlStatisticsEntity;

import java.util.List;

/**
 * 扩展DAO
 * @author Administrator
 *
 * @param <T>
 * @param <E>
 */
public interface IExtDAO<T,E> {
	int countExtByExample(E example);
	List<T> selectExtByExample(E example);
	List<T> selectExtByExamplePage(E example);
	
	<M extends SqlStatisticsEntity> M statisticsExtByExample(E example);
}
