package com.hero.wireless.web.entity.business;

import com.hero.wireless.web.entity.base.BaseEntity;

import java.util.Date;

public class Contact extends BaseEntity {
    private Integer id;

    private Integer group_Id;

    private String phone_No;

    private String real_Name;

    private String gender;

    private Date birthday;

    private String address;

    private String company;

    private String position;

    private String enterprise_No;

    private Integer create_Enterprise_User_Id;

    private String create_Enterprise_User_Name;

    private Date create_Date;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGroup_Id() {
        return group_Id;
    }

    public void setGroup_Id(Integer group_Id) {
        this.group_Id = group_Id;
    }

    public String getPhone_No() {
        return phone_No;
    }

    public void setPhone_No(String phone_No) {
        this.phone_No = phone_No;
    }

    public String getReal_Name() {
        return real_Name;
    }

    public void setReal_Name(String real_Name) {
        this.real_Name = real_Name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getEnterprise_No() {
        return enterprise_No;
    }

    public void setEnterprise_No(String enterprise_No) {
        this.enterprise_No = enterprise_No;
    }

    public Integer getCreate_Enterprise_User_Id() {
        return create_Enterprise_User_Id;
    }

    public void setCreate_Enterprise_User_Id(Integer create_Enterprise_User_Id) {
        this.create_Enterprise_User_Id = create_Enterprise_User_Id;
    }

    public String getCreate_Enterprise_User_Name() {
        return create_Enterprise_User_Name;
    }

    public void setCreate_Enterprise_User_Name(String create_Enterprise_User_Name) {
        this.create_Enterprise_User_Name = create_Enterprise_User_Name;
    }

    public Date getCreate_Date() {
        return create_Date;
    }

    public void setCreate_Date(Date create_Date) {
        this.create_Date = create_Date;
    }
}