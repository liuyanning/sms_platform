package com.hero.wireless.web.dao.business;

import com.hero.wireless.web.dao.base.IDao;
import com.hero.wireless.web.entity.business.MmsMaterial;
import com.hero.wireless.web.entity.business.MmsMaterialExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IMmsMaterialDAO<T extends MmsMaterial> extends IDao<MmsMaterial, MmsMaterialExample> {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mms_material
     *
     * @mbg.generated Fri Dec 27 09:14:42 CST 2019
     */
    int countByExample(MmsMaterialExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mms_material
     *
     * @mbg.generated Fri Dec 27 09:14:42 CST 2019
     */
    int deleteByExample(MmsMaterialExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mms_material
     *
     * @mbg.generated Fri Dec 27 09:14:42 CST 2019
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mms_material
     *
     * @mbg.generated Fri Dec 27 09:14:42 CST 2019
     */
    int insert(MmsMaterial record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mms_material
     *
     * @mbg.generated Fri Dec 27 09:14:42 CST 2019
     */
    int insertList(List<MmsMaterial> list);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mms_material
     *
     * @mbg.generated Fri Dec 27 09:14:42 CST 2019
     */
    int insertSelective(MmsMaterial record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mms_material
     *
     * @mbg.generated Fri Dec 27 09:14:42 CST 2019
     */
    List<MmsMaterial> selectByExample(MmsMaterialExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mms_material
     *
     * @mbg.generated Fri Dec 27 09:14:42 CST 2019
     */
    MmsMaterial selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mms_material
     *
     * @mbg.generated Fri Dec 27 09:14:42 CST 2019
     */
    int updateByExampleSelective(@Param("record") MmsMaterial record, @Param("example") MmsMaterialExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mms_material
     *
     * @mbg.generated Fri Dec 27 09:14:42 CST 2019
     */
    int updateByExample(@Param("record") MmsMaterial record, @Param("example") MmsMaterialExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mms_material
     *
     * @mbg.generated Fri Dec 27 09:14:42 CST 2019
     */
    int updateByPrimaryKeySelective(MmsMaterial record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mms_material
     *
     * @mbg.generated Fri Dec 27 09:14:42 CST 2019
     */
    int updateByPrimaryKey(MmsMaterial record);
}