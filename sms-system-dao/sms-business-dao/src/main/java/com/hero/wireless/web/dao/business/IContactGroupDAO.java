package com.hero.wireless.web.dao.business;

import com.hero.wireless.web.dao.base.IDao;
import com.hero.wireless.web.entity.business.ContactGroup;
import com.hero.wireless.web.entity.business.ContactGroupExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IContactGroupDAO<T extends ContactGroup> extends IDao<ContactGroup, ContactGroupExample> {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table contact_group
     *
     * @mbg.generated Sat Sep 14 11:02:15 CST 2019
     */
    int countByExample(ContactGroupExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table contact_group
     *
     * @mbg.generated Sat Sep 14 11:02:15 CST 2019
     */
    int deleteByExample(ContactGroupExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table contact_group
     *
     * @mbg.generated Sat Sep 14 11:02:15 CST 2019
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table contact_group
     *
     * @mbg.generated Sat Sep 14 11:02:15 CST 2019
     */
    int insert(ContactGroup record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table contact_group
     *
     * @mbg.generated Sat Sep 14 11:02:15 CST 2019
     */
    int insertList(List<ContactGroup> list);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table contact_group
     *
     * @mbg.generated Sat Sep 14 11:02:15 CST 2019
     */
    int insertSelective(ContactGroup record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table contact_group
     *
     * @mbg.generated Sat Sep 14 11:02:15 CST 2019
     */
    List<ContactGroup> selectByExample(ContactGroupExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table contact_group
     *
     * @mbg.generated Sat Sep 14 11:02:15 CST 2019
     */
    ContactGroup selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table contact_group
     *
     * @mbg.generated Sat Sep 14 11:02:15 CST 2019
     */
    int updateByExampleSelective(@Param("record") ContactGroup record, @Param("example") ContactGroupExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table contact_group
     *
     * @mbg.generated Sat Sep 14 11:02:15 CST 2019
     */
    int updateByExample(@Param("record") ContactGroup record, @Param("example") ContactGroupExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table contact_group
     *
     * @mbg.generated Sat Sep 14 11:02:15 CST 2019
     */
    int updateByPrimaryKeySelective(ContactGroup record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table contact_group
     *
     * @mbg.generated Sat Sep 14 11:02:15 CST 2019
     */
    int updateByPrimaryKey(ContactGroup record);
}