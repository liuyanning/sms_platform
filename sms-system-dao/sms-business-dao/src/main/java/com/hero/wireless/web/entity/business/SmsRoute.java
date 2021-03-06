package com.hero.wireless.web.entity.business;

import com.hero.wireless.web.entity.base.BaseEntity;

import java.util.Date;

public class SmsRoute extends BaseEntity {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sms_route.Id
     *
     * @mbg.generated Thu Aug 06 18:06:31 CST 2020
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sms_route.Prefix_Number
     *
     * @mbg.generated Thu Aug 06 18:06:31 CST 2020
     */
    private String prefix_Number;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sms_route.Route_Name_Code
     *
     * @mbg.generated Thu Aug 06 18:06:31 CST 2020
     */
    private String route_Name_Code;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sms_route.Country_Code
     *
     * @mbg.generated Thu Aug 06 18:06:31 CST 2020
     */
    private String country_Code;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sms_route.Country_Number
     *
     * @mbg.generated Thu Aug 06 18:06:31 CST 2020
     */
    private String country_Number;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sms_route.MCC
     *
     * @mbg.generated Thu Aug 06 18:06:31 CST 2020
     */
    private String MCC;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sms_route.MNC
     *
     * @mbg.generated Thu Aug 06 18:06:31 CST 2020
     */
    private String MNC;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sms_route.Description
     *
     * @mbg.generated Thu Aug 06 18:06:31 CST 2020
     */
    private String description;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sms_route.Remark
     *
     * @mbg.generated Thu Aug 06 18:06:31 CST 2020
     */
    private String remark;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sms_route.Create_Date
     *
     * @mbg.generated Thu Aug 06 18:06:31 CST 2020
     */
    private Date create_Date;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sms_route.Id
     *
     * @return the value of sms_route.Id
     *
     * @mbg.generated Thu Aug 06 18:06:31 CST 2020
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sms_route.Id
     *
     * @param id the value for sms_route.Id
     *
     * @mbg.generated Thu Aug 06 18:06:31 CST 2020
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sms_route.Prefix_Number
     *
     * @return the value of sms_route.Prefix_Number
     *
     * @mbg.generated Thu Aug 06 18:06:31 CST 2020
     */
    public String getPrefix_Number() {
        return prefix_Number;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sms_route.Prefix_Number
     *
     * @param prefix_Number the value for sms_route.Prefix_Number
     *
     * @mbg.generated Thu Aug 06 18:06:31 CST 2020
     */
    public void setPrefix_Number(String prefix_Number) {
        this.prefix_Number = prefix_Number;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sms_route.Route_Name_Code
     *
     * @return the value of sms_route.Route_Name_Code
     *
     * @mbg.generated Thu Aug 06 18:06:31 CST 2020
     */
    public String getRoute_Name_Code() {
        return route_Name_Code;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sms_route.Route_Name_Code
     *
     * @param route_Name_Code the value for sms_route.Route_Name_Code
     *
     * @mbg.generated Thu Aug 06 18:06:31 CST 2020
     */
    public void setRoute_Name_Code(String route_Name_Code) {
        this.route_Name_Code = route_Name_Code;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sms_route.Country_Code
     *
     * @return the value of sms_route.Country_Code
     *
     * @mbg.generated Thu Aug 06 18:06:31 CST 2020
     */
    public String getCountry_Code() {
        return country_Code;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sms_route.Country_Code
     *
     * @param country_Code the value for sms_route.Country_Code
     *
     * @mbg.generated Thu Aug 06 18:06:31 CST 2020
     */
    public void setCountry_Code(String country_Code) {
        this.country_Code = country_Code;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sms_route.Country_Number
     *
     * @return the value of sms_route.Country_Number
     *
     * @mbg.generated Thu Aug 06 18:06:31 CST 2020
     */
    public String getCountry_Number() {
        return country_Number;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sms_route.Country_Number
     *
     * @param country_Number the value for sms_route.Country_Number
     *
     * @mbg.generated Thu Aug 06 18:06:31 CST 2020
     */
    public void setCountry_Number(String country_Number) {
        this.country_Number = country_Number;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sms_route.MCC
     *
     * @return the value of sms_route.MCC
     *
     * @mbg.generated Thu Aug 06 18:06:31 CST 2020
     */
    public String getMCC() {
        return MCC;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sms_route.MCC
     *
     * @param MCC the value for sms_route.MCC
     *
     * @mbg.generated Thu Aug 06 18:06:31 CST 2020
     */
    public void setMCC(String MCC) {
        this.MCC = MCC;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sms_route.MNC
     *
     * @return the value of sms_route.MNC
     *
     * @mbg.generated Thu Aug 06 18:06:31 CST 2020
     */
    public String getMNC() {
        return MNC;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sms_route.MNC
     *
     * @param MNC the value for sms_route.MNC
     *
     * @mbg.generated Thu Aug 06 18:06:31 CST 2020
     */
    public void setMNC(String MNC) {
        this.MNC = MNC;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sms_route.Description
     *
     * @return the value of sms_route.Description
     *
     * @mbg.generated Thu Aug 06 18:06:31 CST 2020
     */
    public String getDescription() {
        return description;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sms_route.Description
     *
     * @param description the value for sms_route.Description
     *
     * @mbg.generated Thu Aug 06 18:06:31 CST 2020
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sms_route.Remark
     *
     * @return the value of sms_route.Remark
     *
     * @mbg.generated Thu Aug 06 18:06:31 CST 2020
     */
    public String getRemark() {
        return remark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sms_route.Remark
     *
     * @param remark the value for sms_route.Remark
     *
     * @mbg.generated Thu Aug 06 18:06:31 CST 2020
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sms_route.Create_Date
     *
     * @return the value of sms_route.Create_Date
     *
     * @mbg.generated Thu Aug 06 18:06:31 CST 2020
     */
    public Date getCreate_Date() {
        return create_Date;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sms_route.Create_Date
     *
     * @param create_Date the value for sms_route.Create_Date
     *
     * @mbg.generated Thu Aug 06 18:06:31 CST 2020
     */
    public void setCreate_Date(Date create_Date) {
        this.create_Date = create_Date;
    }
}