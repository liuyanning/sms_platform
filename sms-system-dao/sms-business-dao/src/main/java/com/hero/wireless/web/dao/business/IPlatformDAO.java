package com.hero.wireless.web.dao.business;

import com.hero.wireless.web.dao.base.IDao;
import com.hero.wireless.web.entity.business.Platform;
import com.hero.wireless.web.entity.business.PlatformExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IPlatformDAO<T extends Platform> extends IDao<Platform, PlatformExample> {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table platform
     *
     * @mbg.generated Wed Jun 10 18:52:20 CST 2020
     */
    int countByExample(PlatformExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table platform
     *
     * @mbg.generated Wed Jun 10 18:52:20 CST 2020
     */
    int deleteByExample(PlatformExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table platform
     *
     * @mbg.generated Wed Jun 10 18:52:20 CST 2020
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table platform
     *
     * @mbg.generated Wed Jun 10 18:52:20 CST 2020
     */
    int insert(Platform record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table platform
     *
     * @mbg.generated Wed Jun 10 18:52:20 CST 2020
     */
    int insertList(List<Platform> list);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table platform
     *
     * @mbg.generated Wed Jun 10 18:52:20 CST 2020
     */
    int insertSelective(Platform record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table platform
     *
     * @mbg.generated Wed Jun 10 18:52:20 CST 2020
     */
    List<Platform> selectByExample(PlatformExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table platform
     *
     * @mbg.generated Wed Jun 10 18:52:20 CST 2020
     */
    Platform selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table platform
     *
     * @mbg.generated Wed Jun 10 18:52:20 CST 2020
     */
    int updateByExampleSelective(@Param("record") Platform record, @Param("example") PlatformExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table platform
     *
     * @mbg.generated Wed Jun 10 18:52:20 CST 2020
     */
    int updateByExample(@Param("record") Platform record, @Param("example") PlatformExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table platform
     *
     * @mbg.generated Wed Jun 10 18:52:20 CST 2020
     */
    int updateByPrimaryKeySelective(Platform record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table platform
     *
     * @mbg.generated Wed Jun 10 18:52:20 CST 2020
     */
    int updateByPrimaryKey(Platform record);
}