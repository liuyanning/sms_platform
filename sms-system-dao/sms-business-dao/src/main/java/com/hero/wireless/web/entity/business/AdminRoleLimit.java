package com.hero.wireless.web.entity.business;

import com.hero.wireless.web.entity.base.BaseEntity;

import java.util.Date;

public class AdminRoleLimit extends BaseEntity {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column admin_role_limit.Id
     *
     * @mbg.generated Thu May 14 09:56:20 CST 2020
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column admin_role_limit.Role_Id
     *
     * @mbg.generated Thu May 14 09:56:20 CST 2020
     */
    private Integer role_Id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column admin_role_limit.Limit_Id
     *
     * @mbg.generated Thu May 14 09:56:20 CST 2020
     */
    private Integer limit_Id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column admin_role_limit.Create_User
     *
     * @mbg.generated Thu May 14 09:56:20 CST 2020
     */
    private String create_User;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column admin_role_limit.Create_Date
     *
     * @mbg.generated Thu May 14 09:56:20 CST 2020
     */
    private Date create_Date;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column admin_role_limit.Id
     *
     * @return the value of admin_role_limit.Id
     *
     * @mbg.generated Thu May 14 09:56:20 CST 2020
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column admin_role_limit.Id
     *
     * @param id the value for admin_role_limit.Id
     *
     * @mbg.generated Thu May 14 09:56:20 CST 2020
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column admin_role_limit.Role_Id
     *
     * @return the value of admin_role_limit.Role_Id
     *
     * @mbg.generated Thu May 14 09:56:20 CST 2020
     */
    public Integer getRole_Id() {
        return role_Id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column admin_role_limit.Role_Id
     *
     * @param role_Id the value for admin_role_limit.Role_Id
     *
     * @mbg.generated Thu May 14 09:56:20 CST 2020
     */
    public void setRole_Id(Integer role_Id) {
        this.role_Id = role_Id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column admin_role_limit.Limit_Id
     *
     * @return the value of admin_role_limit.Limit_Id
     *
     * @mbg.generated Thu May 14 09:56:20 CST 2020
     */
    public Integer getLimit_Id() {
        return limit_Id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column admin_role_limit.Limit_Id
     *
     * @param limit_Id the value for admin_role_limit.Limit_Id
     *
     * @mbg.generated Thu May 14 09:56:20 CST 2020
     */
    public void setLimit_Id(Integer limit_Id) {
        this.limit_Id = limit_Id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column admin_role_limit.Create_User
     *
     * @return the value of admin_role_limit.Create_User
     *
     * @mbg.generated Thu May 14 09:56:20 CST 2020
     */
    public String getCreate_User() {
        return create_User;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column admin_role_limit.Create_User
     *
     * @param create_User the value for admin_role_limit.Create_User
     *
     * @mbg.generated Thu May 14 09:56:20 CST 2020
     */
    public void setCreate_User(String create_User) {
        this.create_User = create_User;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column admin_role_limit.Create_Date
     *
     * @return the value of admin_role_limit.Create_Date
     *
     * @mbg.generated Thu May 14 09:56:20 CST 2020
     */
    public Date getCreate_Date() {
        return create_Date;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column admin_role_limit.Create_Date
     *
     * @param create_Date the value for admin_role_limit.Create_Date
     *
     * @mbg.generated Thu May 14 09:56:20 CST 2020
     */
    public void setCreate_Date(Date create_Date) {
        this.create_Date = create_Date;
    }
}