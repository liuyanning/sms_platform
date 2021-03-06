package com.hero.wireless.web.entity.business;

import com.hero.wireless.web.entity.base.BaseEntity;

import java.util.Date;

public class ContactGroup extends BaseEntity {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column contact_group.Id
     *
     * @mbg.generated Thu May 14 09:56:20 CST 2020
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column contact_group.Group_Name
     *
     * @mbg.generated Thu May 14 09:56:20 CST 2020
     */
    private String group_Name;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column contact_group.Enterprise_No
     *
     * @mbg.generated Thu May 14 09:56:20 CST 2020
     */
    private String enterprise_No;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column contact_group.Create_Enterprise_User_Id
     *
     * @mbg.generated Thu May 14 09:56:20 CST 2020
     */
    private Integer create_Enterprise_User_Id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column contact_group.Create_Enterprise_User_Name
     *
     * @mbg.generated Thu May 14 09:56:20 CST 2020
     */
    private String create_Enterprise_User_Name;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column contact_group.Create_Date
     *
     * @mbg.generated Thu May 14 09:56:20 CST 2020
     */
    private Date create_Date;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column contact_group.Id
     *
     * @return the value of contact_group.Id
     *
     * @mbg.generated Thu May 14 09:56:20 CST 2020
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column contact_group.Id
     *
     * @param id the value for contact_group.Id
     *
     * @mbg.generated Thu May 14 09:56:20 CST 2020
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column contact_group.Group_Name
     *
     * @return the value of contact_group.Group_Name
     *
     * @mbg.generated Thu May 14 09:56:20 CST 2020
     */
    public String getGroup_Name() {
        return group_Name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column contact_group.Group_Name
     *
     * @param group_Name the value for contact_group.Group_Name
     *
     * @mbg.generated Thu May 14 09:56:20 CST 2020
     */
    public void setGroup_Name(String group_Name) {
        this.group_Name = group_Name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column contact_group.Enterprise_No
     *
     * @return the value of contact_group.Enterprise_No
     *
     * @mbg.generated Thu May 14 09:56:20 CST 2020
     */
    public String getEnterprise_No() {
        return enterprise_No;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column contact_group.Enterprise_No
     *
     * @param enterprise_No the value for contact_group.Enterprise_No
     *
     * @mbg.generated Thu May 14 09:56:20 CST 2020
     */
    public void setEnterprise_No(String enterprise_No) {
        this.enterprise_No = enterprise_No;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column contact_group.Create_Enterprise_User_Id
     *
     * @return the value of contact_group.Create_Enterprise_User_Id
     *
     * @mbg.generated Thu May 14 09:56:20 CST 2020
     */
    public Integer getCreate_Enterprise_User_Id() {
        return create_Enterprise_User_Id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column contact_group.Create_Enterprise_User_Id
     *
     * @param create_Enterprise_User_Id the value for contact_group.Create_Enterprise_User_Id
     *
     * @mbg.generated Thu May 14 09:56:20 CST 2020
     */
    public void setCreate_Enterprise_User_Id(Integer create_Enterprise_User_Id) {
        this.create_Enterprise_User_Id = create_Enterprise_User_Id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column contact_group.Create_Enterprise_User_Name
     *
     * @return the value of contact_group.Create_Enterprise_User_Name
     *
     * @mbg.generated Thu May 14 09:56:20 CST 2020
     */
    public String getCreate_Enterprise_User_Name() {
        return create_Enterprise_User_Name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column contact_group.Create_Enterprise_User_Name
     *
     * @param create_Enterprise_User_Name the value for contact_group.Create_Enterprise_User_Name
     *
     * @mbg.generated Thu May 14 09:56:20 CST 2020
     */
    public void setCreate_Enterprise_User_Name(String create_Enterprise_User_Name) {
        this.create_Enterprise_User_Name = create_Enterprise_User_Name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column contact_group.Create_Date
     *
     * @return the value of contact_group.Create_Date
     *
     * @mbg.generated Thu May 14 09:56:20 CST 2020
     */
    public Date getCreate_Date() {
        return create_Date;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column contact_group.Create_Date
     *
     * @param create_Date the value for contact_group.Create_Date
     *
     * @mbg.generated Thu May 14 09:56:20 CST 2020
     */
    public void setCreate_Date(Date create_Date) {
        this.create_Date = create_Date;
    }
}