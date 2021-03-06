package com.hero.wireless.web.dao.send;

import com.hero.wireless.web.dao.base.IDao;
import com.hero.wireless.web.entity.send.Submit;
import com.hero.wireless.web.entity.send.SubmitExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ISubmitDAO<T extends Submit> extends IDao<Submit, SubmitExample> {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table submit
     *
     * @mbg.generated Mon Jan 04 16:45:21 CST 2021
     */
    int countByExample(SubmitExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table submit
     *
     * @mbg.generated Mon Jan 04 16:45:21 CST 2021
     */
    int deleteByExample(SubmitExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table submit
     *
     * @mbg.generated Mon Jan 04 16:45:21 CST 2021
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table submit
     *
     * @mbg.generated Mon Jan 04 16:45:21 CST 2021
     */
    int insert(Submit record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table submit
     *
     * @mbg.generated Mon Jan 04 16:45:21 CST 2021
     */
    int insertList(List<Submit> list);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table submit
     *
     * @mbg.generated Mon Jan 04 16:45:21 CST 2021
     */
    int insertSelective(Submit record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table submit
     *
     * @mbg.generated Mon Jan 04 16:45:21 CST 2021
     */
    List<Submit> selectByExample(SubmitExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table submit
     *
     * @mbg.generated Mon Jan 04 16:45:21 CST 2021
     */
    Submit selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table submit
     *
     * @mbg.generated Mon Jan 04 16:45:21 CST 2021
     */
    int updateByExampleSelective(@Param("record") Submit record, @Param("example") SubmitExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table submit
     *
     * @mbg.generated Mon Jan 04 16:45:21 CST 2021
     */
    int updateByExample(@Param("record") Submit record, @Param("example") SubmitExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table submit
     *
     * @mbg.generated Mon Jan 04 16:45:21 CST 2021
     */
    int updateByPrimaryKeySelective(Submit record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table submit
     *
     * @mbg.generated Mon Jan 04 16:45:21 CST 2021
     */
    int updateByPrimaryKey(Submit record);
}