package com.hero.wireless.web.dao.business;

import com.hero.wireless.web.dao.base.IDao;
import com.hero.wireless.web.entity.business.MobileArea;
import com.hero.wireless.web.entity.business.MobileAreaExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IMobileAreaDAO<T extends MobileArea> extends IDao<MobileArea, MobileAreaExample> {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mobile_area
     *
     * @mbg.generated Sat Sep 14 11:02:15 CST 2019
     */
    int countByExample(MobileAreaExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mobile_area
     *
     * @mbg.generated Sat Sep 14 11:02:15 CST 2019
     */
    int deleteByExample(MobileAreaExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mobile_area
     *
     * @mbg.generated Sat Sep 14 11:02:15 CST 2019
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mobile_area
     *
     * @mbg.generated Sat Sep 14 11:02:15 CST 2019
     */
    int insert(MobileArea record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mobile_area
     *
     * @mbg.generated Sat Sep 14 11:02:15 CST 2019
     */
    int insertList(List<MobileArea> list);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mobile_area
     *
     * @mbg.generated Sat Sep 14 11:02:15 CST 2019
     */
    int insertSelective(MobileArea record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mobile_area
     *
     * @mbg.generated Sat Sep 14 11:02:15 CST 2019
     */
    List<MobileArea> selectByExample(MobileAreaExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mobile_area
     *
     * @mbg.generated Sat Sep 14 11:02:15 CST 2019
     */
    MobileArea selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mobile_area
     *
     * @mbg.generated Sat Sep 14 11:02:15 CST 2019
     */
    int updateByExampleSelective(@Param("record") MobileArea record, @Param("example") MobileAreaExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mobile_area
     *
     * @mbg.generated Sat Sep 14 11:02:15 CST 2019
     */
    int updateByExample(@Param("record") MobileArea record, @Param("example") MobileAreaExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mobile_area
     *
     * @mbg.generated Sat Sep 14 11:02:15 CST 2019
     */
    int updateByPrimaryKeySelective(MobileArea record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mobile_area
     *
     * @mbg.generated Sat Sep 14 11:02:15 CST 2019
     */
    int updateByPrimaryKey(MobileArea record);
}