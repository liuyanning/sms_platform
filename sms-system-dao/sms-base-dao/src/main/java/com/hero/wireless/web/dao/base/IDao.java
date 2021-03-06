/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hero.wireless.web.dao.base;

import java.util.List;

/**
 * 
 * @author Volcano
 */
public interface IDao<T,E> {

	/**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table test.student
     *
     * @ibatorgenerated  Fri Jun 25 00:32:33 CST 2010
     */
    int countByExample(E example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table test.student
     *
     * @ibatorgenerated Fri Jun 25 00:32:33 CST 2010
     */
    int deleteByExample(E example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table test.student
     *
     * @ibatorgenerated Fri Jun 25 00:32:33 CST 2010
     */
    int deleteByPrimaryKey(Integer id);
    
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table test.student
     *
     * @ibatorgenerated Fri Jun 25 00:32:33 CST 2010
     */
    int insert(T record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table test.student
     *
     * @ibatorgenerated Fri Jun 25 00:32:33 CST 2010
     */
    int insertSelective(T record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table test.student
     *
     * @ibatorgenerated Fri Jun 25 00:32:33 CST 2010
     */
    List<T> selectByExample(E example);
    
    
    List<T> selectByExamplePage(E example);
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table test.student
     *
     * @ibatorgenerated Fri Jun 25 00:32:33 CST 2010
     */
    T selectByPrimaryKey(Integer id);
    
    T selectByPrimaryKey(Long id);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table test.student
     *
     * @ibatorgenerated Fri Jun 25 00:32:33 CST 2010
     */
    int updateByExampleSelective(E example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table test.student
     *
     * @ibatorgenerated Fri Jun 25 00:32:33 CST 2010
     */
    int updateByExample(E example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table test.student
     *
     * @ibatorgenerated Fri Jun 25 00:32:33 CST 2010
     */
    int updateByPrimaryKeySelective(T record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table test.student
     *
     * @ibatorgenerated Fri Jun 25 00:32:33 CST 2010
     */
    int updateByPrimaryKey(T record);
    
    /**
     * 
     * TODO
     * 
     * @param list
     * @return
     */
	int insertList(List<T> list);

	/**
	 * 
	 * TODO
	 * 
	 * @param record
	 * @param example
	 * @return
	 */
	int updateByExampleSelective(T record, E example);

	/**
	 * 
	 * TODO
	 * 
	 * @param record
	 * @param example
	 * @return
	 */
	int updateByExample(T record, E example);
}
