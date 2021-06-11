package com.hero.wireless.web.entity.send;

import com.hero.wireless.web.entity.base.BaseEntity;
import java.math.BigDecimal;
import java.util.Date;

public class InputLog extends BaseEntity {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column input_log.Id
     *
     * @mbg.generated Wed May 12 10:51:03 CST 2021
     */
    private Long id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column input_log.Msg_Batch_No
     *
     * @mbg.generated Wed May 12 10:51:03 CST 2021
     */
    private String msg_Batch_No;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column input_log.Enterprise_Msg_Id
     *
     * @mbg.generated Wed May 12 10:51:03 CST 2021
     */
    private String enterprise_Msg_Id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column input_log.Enterprise_No
     *
     * @mbg.generated Wed May 12 10:51:03 CST 2021
     */
    private String enterprise_No;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column input_log.Agent_No
     *
     * @mbg.generated Wed May 12 10:51:03 CST 2021
     */
    private String agent_No;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column input_log.Business_User_Id
     *
     * @mbg.generated Wed May 12 10:51:03 CST 2021
     */
    private Integer business_User_Id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column input_log.Enterprise_User_Id
     *
     * @mbg.generated Wed May 12 10:51:03 CST 2021
     */
    private Integer enterprise_User_Id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column input_log.Product_No
     *
     * @mbg.generated Wed May 12 10:51:03 CST 2021
     */
    private String product_No;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column input_log.Is_LMS
     *
     * @mbg.generated Wed May 12 10:51:03 CST 2021
     */
    private Boolean is_LMS;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column input_log.Data_Status_Code
     *
     * @mbg.generated Wed May 12 10:51:03 CST 2021
     */
    private String data_Status_Code;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column input_log.Is_Show
     *
     * @mbg.generated Wed May 12 10:51:03 CST 2021
     */
    private Boolean is_Show;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column input_log.Message_Type_Code
     *
     * @mbg.generated Wed May 12 10:51:03 CST 2021
     */
    private String message_Type_Code;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column input_log.Content
     *
     * @mbg.generated Wed May 12 10:51:03 CST 2021
     */
    private String content;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column input_log.Charset
     *
     * @mbg.generated Wed May 12 10:51:03 CST 2021
     */
    private String charset;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column input_log.Phone_Nos
     *
     * @mbg.generated Wed May 12 10:51:03 CST 2021
     */
    private String phone_Nos;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column input_log.Protocol_Type_Code
     *
     * @mbg.generated Wed May 12 10:51:03 CST 2021
     */
    private String protocol_Type_Code;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column input_log.Country_Code
     *
     * @mbg.generated Wed May 12 10:51:03 CST 2021
     */
    private String country_Code;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column input_log.Source_IP
     *
     * @mbg.generated Wed May 12 10:51:03 CST 2021
     */
    private String source_IP;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column input_log.Priority_Number
     *
     * @mbg.generated Wed May 12 10:51:03 CST 2021
     */
    private Integer priority_Number;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column input_log.Audit_Status_Code
     *
     * @mbg.generated Wed May 12 10:51:03 CST 2021
     */
    private String audit_Status_Code;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column input_log.Audit_Admin_User_Id
     *
     * @mbg.generated Wed May 12 10:51:03 CST 2021
     */
    private Integer audit_Admin_User_Id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column input_log.Audit_Date
     *
     * @mbg.generated Wed May 12 10:51:03 CST 2021
     */
    private Date audit_Date;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column input_log.Sub_Code
     *
     * @mbg.generated Wed May 12 10:51:03 CST 2021
     */
    private String sub_Code;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column input_log.Input_Sub_Code
     *
     * @mbg.generated Wed May 12 10:51:03 CST 2021
     */
    private String input_Sub_Code;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column input_log.Input_Date
     *
     * @mbg.generated Wed May 12 10:51:03 CST 2021
     */
    private Date input_Date;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column input_log.Send_Time
     *
     * @mbg.generated Wed May 12 10:51:03 CST 2021
     */
    private Date send_Time;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column input_log.Gate_Ip
     *
     * @mbg.generated Wed May 12 10:51:03 CST 2021
     */
    private String gate_Ip;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column input_log.Content_Length
     *
     * @mbg.generated Wed May 12 10:51:03 CST 2021
     */
    private Integer content_Length;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column input_log.Phone_Nos_Count
     *
     * @mbg.generated Wed May 12 10:51:03 CST 2021
     */
    private Integer phone_Nos_Count;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column input_log.Fee_Count
     *
     * @mbg.generated Wed May 12 10:51:03 CST 2021
     */
    private Integer fee_Count;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column input_log.Faild_Count
     *
     * @mbg.generated Wed May 12 10:51:03 CST 2021
     */
    private Integer faild_Count;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column input_log.Sale_Amount
     *
     * @mbg.generated Wed May 12 10:51:03 CST 2021
     */
    private BigDecimal sale_Amount;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column input_log.Description
     *
     * @mbg.generated Wed May 12 10:51:03 CST 2021
     */
    private String description;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column input_log.Remark
     *
     * @mbg.generated Wed May 12 10:51:03 CST 2021
     */
    private String remark;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column input_log.Create_Date
     *
     * @mbg.generated Wed May 12 10:51:03 CST 2021
     */
    private Date create_Date;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column input_log.Id
     *
     * @return the value of input_log.Id
     *
     * @mbg.generated Wed May 12 10:51:03 CST 2021
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column input_log.Id
     *
     * @param id the value for input_log.Id
     *
     * @mbg.generated Wed May 12 10:51:03 CST 2021
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column input_log.Msg_Batch_No
     *
     * @return the value of input_log.Msg_Batch_No
     *
     * @mbg.generated Wed May 12 10:51:03 CST 2021
     */
    public String getMsg_Batch_No() {
        return msg_Batch_No;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column input_log.Msg_Batch_No
     *
     * @param msg_Batch_No the value for input_log.Msg_Batch_No
     *
     * @mbg.generated Wed May 12 10:51:03 CST 2021
     */
    public void setMsg_Batch_No(String msg_Batch_No) {
        this.msg_Batch_No = msg_Batch_No;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column input_log.Enterprise_Msg_Id
     *
     * @return the value of input_log.Enterprise_Msg_Id
     *
     * @mbg.generated Wed May 12 10:51:03 CST 2021
     */
    public String getEnterprise_Msg_Id() {
        return enterprise_Msg_Id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column input_log.Enterprise_Msg_Id
     *
     * @param enterprise_Msg_Id the value for input_log.Enterprise_Msg_Id
     *
     * @mbg.generated Wed May 12 10:51:03 CST 2021
     */
    public void setEnterprise_Msg_Id(String enterprise_Msg_Id) {
        this.enterprise_Msg_Id = enterprise_Msg_Id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column input_log.Enterprise_No
     *
     * @return the value of input_log.Enterprise_No
     *
     * @mbg.generated Wed May 12 10:51:03 CST 2021
     */
    public String getEnterprise_No() {
        return enterprise_No;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column input_log.Enterprise_No
     *
     * @param enterprise_No the value for input_log.Enterprise_No
     *
     * @mbg.generated Wed May 12 10:51:03 CST 2021
     */
    public void setEnterprise_No(String enterprise_No) {
        this.enterprise_No = enterprise_No;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column input_log.Agent_No
     *
     * @return the value of input_log.Agent_No
     *
     * @mbg.generated Wed May 12 10:51:03 CST 2021
     */
    public String getAgent_No() {
        return agent_No;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column input_log.Agent_No
     *
     * @param agent_No the value for input_log.Agent_No
     *
     * @mbg.generated Wed May 12 10:51:03 CST 2021
     */
    public void setAgent_No(String agent_No) {
        this.agent_No = agent_No;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column input_log.Business_User_Id
     *
     * @return the value of input_log.Business_User_Id
     *
     * @mbg.generated Wed May 12 10:51:03 CST 2021
     */
    public Integer getBusiness_User_Id() {
        return business_User_Id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column input_log.Business_User_Id
     *
     * @param business_User_Id the value for input_log.Business_User_Id
     *
     * @mbg.generated Wed May 12 10:51:03 CST 2021
     */
    public void setBusiness_User_Id(Integer business_User_Id) {
        this.business_User_Id = business_User_Id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column input_log.Enterprise_User_Id
     *
     * @return the value of input_log.Enterprise_User_Id
     *
     * @mbg.generated Wed May 12 10:51:03 CST 2021
     */
    public Integer getEnterprise_User_Id() {
        return enterprise_User_Id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column input_log.Enterprise_User_Id
     *
     * @param enterprise_User_Id the value for input_log.Enterprise_User_Id
     *
     * @mbg.generated Wed May 12 10:51:03 CST 2021
     */
    public void setEnterprise_User_Id(Integer enterprise_User_Id) {
        this.enterprise_User_Id = enterprise_User_Id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column input_log.Product_No
     *
     * @return the value of input_log.Product_No
     *
     * @mbg.generated Wed May 12 10:51:03 CST 2021
     */
    public String getProduct_No() {
        return product_No;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column input_log.Product_No
     *
     * @param product_No the value for input_log.Product_No
     *
     * @mbg.generated Wed May 12 10:51:03 CST 2021
     */
    public void setProduct_No(String product_No) {
        this.product_No = product_No;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column input_log.Is_LMS
     *
     * @return the value of input_log.Is_LMS
     *
     * @mbg.generated Wed May 12 10:51:03 CST 2021
     */
    public Boolean getIs_LMS() {
        return is_LMS;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column input_log.Is_LMS
     *
     * @param is_LMS the value for input_log.Is_LMS
     *
     * @mbg.generated Wed May 12 10:51:03 CST 2021
     */
    public void setIs_LMS(Boolean is_LMS) {
        this.is_LMS = is_LMS;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column input_log.Data_Status_Code
     *
     * @return the value of input_log.Data_Status_Code
     *
     * @mbg.generated Wed May 12 10:51:03 CST 2021
     */
    public String getData_Status_Code() {
        return data_Status_Code;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column input_log.Data_Status_Code
     *
     * @param data_Status_Code the value for input_log.Data_Status_Code
     *
     * @mbg.generated Wed May 12 10:51:03 CST 2021
     */
    public void setData_Status_Code(String data_Status_Code) {
        this.data_Status_Code = data_Status_Code;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column input_log.Is_Show
     *
     * @return the value of input_log.Is_Show
     *
     * @mbg.generated Wed May 12 10:51:03 CST 2021
     */
    public Boolean getIs_Show() {
        return is_Show;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column input_log.Is_Show
     *
     * @param is_Show the value for input_log.Is_Show
     *
     * @mbg.generated Wed May 12 10:51:03 CST 2021
     */
    public void setIs_Show(Boolean is_Show) {
        this.is_Show = is_Show;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column input_log.Message_Type_Code
     *
     * @return the value of input_log.Message_Type_Code
     *
     * @mbg.generated Wed May 12 10:51:03 CST 2021
     */
    public String getMessage_Type_Code() {
        return message_Type_Code;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column input_log.Message_Type_Code
     *
     * @param message_Type_Code the value for input_log.Message_Type_Code
     *
     * @mbg.generated Wed May 12 10:51:03 CST 2021
     */
    public void setMessage_Type_Code(String message_Type_Code) {
        this.message_Type_Code = message_Type_Code;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column input_log.Content
     *
     * @return the value of input_log.Content
     *
     * @mbg.generated Wed May 12 10:51:03 CST 2021
     */
    public String getContent() {
        return content;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column input_log.Content
     *
     * @param content the value for input_log.Content
     *
     * @mbg.generated Wed May 12 10:51:03 CST 2021
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column input_log.Charset
     *
     * @return the value of input_log.Charset
     *
     * @mbg.generated Wed May 12 10:51:03 CST 2021
     */
    public String getCharset() {
        return charset;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column input_log.Charset
     *
     * @param charset the value for input_log.Charset
     *
     * @mbg.generated Wed May 12 10:51:03 CST 2021
     */
    public void setCharset(String charset) {
        this.charset = charset;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column input_log.Phone_Nos
     *
     * @return the value of input_log.Phone_Nos
     *
     * @mbg.generated Wed May 12 10:51:03 CST 2021
     */
    public String getPhone_Nos() {
        return phone_Nos;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column input_log.Phone_Nos
     *
     * @param phone_Nos the value for input_log.Phone_Nos
     *
     * @mbg.generated Wed May 12 10:51:03 CST 2021
     */
    public void setPhone_Nos(String phone_Nos) {
        this.phone_Nos = phone_Nos;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column input_log.Protocol_Type_Code
     *
     * @return the value of input_log.Protocol_Type_Code
     *
     * @mbg.generated Wed May 12 10:51:03 CST 2021
     */
    public String getProtocol_Type_Code() {
        return protocol_Type_Code;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column input_log.Protocol_Type_Code
     *
     * @param protocol_Type_Code the value for input_log.Protocol_Type_Code
     *
     * @mbg.generated Wed May 12 10:51:03 CST 2021
     */
    public void setProtocol_Type_Code(String protocol_Type_Code) {
        this.protocol_Type_Code = protocol_Type_Code;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column input_log.Country_Code
     *
     * @return the value of input_log.Country_Code
     *
     * @mbg.generated Wed May 12 10:51:03 CST 2021
     */
    public String getCountry_Code() {
        return country_Code;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column input_log.Country_Code
     *
     * @param country_Code the value for input_log.Country_Code
     *
     * @mbg.generated Wed May 12 10:51:03 CST 2021
     */
    public void setCountry_Code(String country_Code) {
        this.country_Code = country_Code;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column input_log.Source_IP
     *
     * @return the value of input_log.Source_IP
     *
     * @mbg.generated Wed May 12 10:51:03 CST 2021
     */
    public String getSource_IP() {
        return source_IP;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column input_log.Source_IP
     *
     * @param source_IP the value for input_log.Source_IP
     *
     * @mbg.generated Wed May 12 10:51:03 CST 2021
     */
    public void setSource_IP(String source_IP) {
        this.source_IP = source_IP;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column input_log.Priority_Number
     *
     * @return the value of input_log.Priority_Number
     *
     * @mbg.generated Wed May 12 10:51:03 CST 2021
     */
    public Integer getPriority_Number() {
        return priority_Number;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column input_log.Priority_Number
     *
     * @param priority_Number the value for input_log.Priority_Number
     *
     * @mbg.generated Wed May 12 10:51:03 CST 2021
     */
    public void setPriority_Number(Integer priority_Number) {
        this.priority_Number = priority_Number;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column input_log.Audit_Status_Code
     *
     * @return the value of input_log.Audit_Status_Code
     *
     * @mbg.generated Wed May 12 10:51:03 CST 2021
     */
    public String getAudit_Status_Code() {
        return audit_Status_Code;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column input_log.Audit_Status_Code
     *
     * @param audit_Status_Code the value for input_log.Audit_Status_Code
     *
     * @mbg.generated Wed May 12 10:51:03 CST 2021
     */
    public void setAudit_Status_Code(String audit_Status_Code) {
        this.audit_Status_Code = audit_Status_Code;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column input_log.Audit_Admin_User_Id
     *
     * @return the value of input_log.Audit_Admin_User_Id
     *
     * @mbg.generated Wed May 12 10:51:03 CST 2021
     */
    public Integer getAudit_Admin_User_Id() {
        return audit_Admin_User_Id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column input_log.Audit_Admin_User_Id
     *
     * @param audit_Admin_User_Id the value for input_log.Audit_Admin_User_Id
     *
     * @mbg.generated Wed May 12 10:51:03 CST 2021
     */
    public void setAudit_Admin_User_Id(Integer audit_Admin_User_Id) {
        this.audit_Admin_User_Id = audit_Admin_User_Id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column input_log.Audit_Date
     *
     * @return the value of input_log.Audit_Date
     *
     * @mbg.generated Wed May 12 10:51:03 CST 2021
     */
    public Date getAudit_Date() {
        return audit_Date;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column input_log.Audit_Date
     *
     * @param audit_Date the value for input_log.Audit_Date
     *
     * @mbg.generated Wed May 12 10:51:03 CST 2021
     */
    public void setAudit_Date(Date audit_Date) {
        this.audit_Date = audit_Date;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column input_log.Sub_Code
     *
     * @return the value of input_log.Sub_Code
     *
     * @mbg.generated Wed May 12 10:51:03 CST 2021
     */
    public String getSub_Code() {
        return sub_Code;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column input_log.Sub_Code
     *
     * @param sub_Code the value for input_log.Sub_Code
     *
     * @mbg.generated Wed May 12 10:51:03 CST 2021
     */
    public void setSub_Code(String sub_Code) {
        this.sub_Code = sub_Code;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column input_log.Input_Sub_Code
     *
     * @return the value of input_log.Input_Sub_Code
     *
     * @mbg.generated Wed May 12 10:51:03 CST 2021
     */
    public String getInput_Sub_Code() {
        return input_Sub_Code;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column input_log.Input_Sub_Code
     *
     * @param input_Sub_Code the value for input_log.Input_Sub_Code
     *
     * @mbg.generated Wed May 12 10:51:03 CST 2021
     */
    public void setInput_Sub_Code(String input_Sub_Code) {
        this.input_Sub_Code = input_Sub_Code;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column input_log.Input_Date
     *
     * @return the value of input_log.Input_Date
     *
     * @mbg.generated Wed May 12 10:51:03 CST 2021
     */
    public Date getInput_Date() {
        return input_Date;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column input_log.Input_Date
     *
     * @param input_Date the value for input_log.Input_Date
     *
     * @mbg.generated Wed May 12 10:51:03 CST 2021
     */
    public void setInput_Date(Date input_Date) {
        this.input_Date = input_Date;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column input_log.Send_Time
     *
     * @return the value of input_log.Send_Time
     *
     * @mbg.generated Wed May 12 10:51:03 CST 2021
     */
    public Date getSend_Time() {
        return send_Time;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column input_log.Send_Time
     *
     * @param send_Time the value for input_log.Send_Time
     *
     * @mbg.generated Wed May 12 10:51:03 CST 2021
     */
    public void setSend_Time(Date send_Time) {
        this.send_Time = send_Time;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column input_log.Gate_Ip
     *
     * @return the value of input_log.Gate_Ip
     *
     * @mbg.generated Wed May 12 10:51:03 CST 2021
     */
    public String getGate_Ip() {
        return gate_Ip;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column input_log.Gate_Ip
     *
     * @param gate_Ip the value for input_log.Gate_Ip
     *
     * @mbg.generated Wed May 12 10:51:03 CST 2021
     */
    public void setGate_Ip(String gate_Ip) {
        this.gate_Ip = gate_Ip;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column input_log.Content_Length
     *
     * @return the value of input_log.Content_Length
     *
     * @mbg.generated Wed May 12 10:51:03 CST 2021
     */
    public Integer getContent_Length() {
        return content_Length;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column input_log.Content_Length
     *
     * @param content_Length the value for input_log.Content_Length
     *
     * @mbg.generated Wed May 12 10:51:03 CST 2021
     */
    public void setContent_Length(Integer content_Length) {
        this.content_Length = content_Length;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column input_log.Phone_Nos_Count
     *
     * @return the value of input_log.Phone_Nos_Count
     *
     * @mbg.generated Wed May 12 10:51:03 CST 2021
     */
    public Integer getPhone_Nos_Count() {
        return phone_Nos_Count;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column input_log.Phone_Nos_Count
     *
     * @param phone_Nos_Count the value for input_log.Phone_Nos_Count
     *
     * @mbg.generated Wed May 12 10:51:03 CST 2021
     */
    public void setPhone_Nos_Count(Integer phone_Nos_Count) {
        this.phone_Nos_Count = phone_Nos_Count;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column input_log.Fee_Count
     *
     * @return the value of input_log.Fee_Count
     *
     * @mbg.generated Wed May 12 10:51:03 CST 2021
     */
    public Integer getFee_Count() {
        return fee_Count;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column input_log.Fee_Count
     *
     * @param fee_Count the value for input_log.Fee_Count
     *
     * @mbg.generated Wed May 12 10:51:03 CST 2021
     */
    public void setFee_Count(Integer fee_Count) {
        this.fee_Count = fee_Count;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column input_log.Faild_Count
     *
     * @return the value of input_log.Faild_Count
     *
     * @mbg.generated Wed May 12 10:51:03 CST 2021
     */
    public Integer getFaild_Count() {
        return faild_Count;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column input_log.Faild_Count
     *
     * @param faild_Count the value for input_log.Faild_Count
     *
     * @mbg.generated Wed May 12 10:51:03 CST 2021
     */
    public void setFaild_Count(Integer faild_Count) {
        this.faild_Count = faild_Count;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column input_log.Sale_Amount
     *
     * @return the value of input_log.Sale_Amount
     *
     * @mbg.generated Wed May 12 10:51:03 CST 2021
     */
    public BigDecimal getSale_Amount() {
        return sale_Amount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column input_log.Sale_Amount
     *
     * @param sale_Amount the value for input_log.Sale_Amount
     *
     * @mbg.generated Wed May 12 10:51:03 CST 2021
     */
    public void setSale_Amount(BigDecimal sale_Amount) {
        this.sale_Amount = sale_Amount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column input_log.Description
     *
     * @return the value of input_log.Description
     *
     * @mbg.generated Wed May 12 10:51:03 CST 2021
     */
    public String getDescription() {
        return description;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column input_log.Description
     *
     * @param description the value for input_log.Description
     *
     * @mbg.generated Wed May 12 10:51:03 CST 2021
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column input_log.Remark
     *
     * @return the value of input_log.Remark
     *
     * @mbg.generated Wed May 12 10:51:03 CST 2021
     */
    public String getRemark() {
        return remark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column input_log.Remark
     *
     * @param remark the value for input_log.Remark
     *
     * @mbg.generated Wed May 12 10:51:03 CST 2021
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column input_log.Create_Date
     *
     * @return the value of input_log.Create_Date
     *
     * @mbg.generated Wed May 12 10:51:03 CST 2021
     */
    public Date getCreate_Date() {
        return create_Date;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column input_log.Create_Date
     *
     * @param create_Date the value for input_log.Create_Date
     *
     * @mbg.generated Wed May 12 10:51:03 CST 2021
     */
    public void setCreate_Date(Date create_Date) {
        this.create_Date = create_Date;
    }
}