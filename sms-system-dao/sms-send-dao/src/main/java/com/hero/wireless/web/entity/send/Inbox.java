package com.hero.wireless.web.entity.send;

import com.hero.wireless.web.entity.base.BaseEntity;

import java.util.Date;

public class Inbox extends BaseEntity {
    private Integer id;

    private String SP_Number;

    private String sub_Code;

    private String group_Code;

    private String input_Sub_Code;

    private String input_Msg_No;

    private String input_Content;

    private Date input_Create_Date;

    private String phone_No;

    private String country_Code;

    private String content;

    private String channel_No;

    private String agent_No;

    private String enterprise_No;

    private Integer enterprise_User_Id;

    private String protocol_Type_Code;

    private Integer pull_Total;

    private Date pull_Date;

    private Integer notify_Total;

    private Date notify_Date;

    private String notify_Status_Code;

    private String sender_Local_IP;

    private String charset;

    private Date create_Date;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSP_Number() {
        return SP_Number;
    }

    public void setSP_Number(String SP_Number) {
        this.SP_Number = SP_Number;
    }

    public String getSub_Code() {
        return sub_Code;
    }

    public void setSub_Code(String sub_Code) {
        this.sub_Code = sub_Code;
    }

    public String getGroup_Code() {
        return group_Code;
    }

    public void setGroup_Code(String group_Code) {
        this.group_Code = group_Code;
    }

    public String getInput_Sub_Code() {
        return input_Sub_Code;
    }

    public void setInput_Sub_Code(String input_Sub_Code) {
        this.input_Sub_Code = input_Sub_Code;
    }

    public String getInput_Msg_No() {
        return input_Msg_No;
    }

    public void setInput_Msg_No(String input_Msg_No) {
        this.input_Msg_No = input_Msg_No;
    }

    public String getInput_Content() {
        return input_Content;
    }

    public void setInput_Content(String input_Content) {
        this.input_Content = input_Content;
    }

    public Date getInput_Create_Date() {
        return input_Create_Date;
    }

    public void setInput_Create_Date(Date input_Create_Date) {
        this.input_Create_Date = input_Create_Date;
    }

    public String getPhone_No() {
        return phone_No;
    }

    public void setPhone_No(String phone_No) {
        this.phone_No = phone_No;
    }

    public String getCountry_Code() {
        return country_Code;
    }

    public void setCountry_Code(String country_Code) {
        this.country_Code = country_Code;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getChannel_No() {
        return channel_No;
    }

    public void setChannel_No(String channel_No) {
        this.channel_No = channel_No;
    }

    public String getAgent_No() {
        return agent_No;
    }

    public void setAgent_No(String agent_No) {
        this.agent_No = agent_No;
    }

    public String getEnterprise_No() {
        return enterprise_No;
    }

    public void setEnterprise_No(String enterprise_No) {
        this.enterprise_No = enterprise_No;
    }

    public Integer getEnterprise_User_Id() {
        return enterprise_User_Id;
    }

    public void setEnterprise_User_Id(Integer enterprise_User_Id) {
        this.enterprise_User_Id = enterprise_User_Id;
    }

    public String getProtocol_Type_Code() {
        return protocol_Type_Code;
    }

    public void setProtocol_Type_Code(String protocol_Type_Code) {
        this.protocol_Type_Code = protocol_Type_Code;
    }

    public Integer getPull_Total() {
        return pull_Total;
    }

    public void setPull_Total(Integer pull_Total) {
        this.pull_Total = pull_Total;
    }

    public Date getPull_Date() {
        return pull_Date;
    }

    public void setPull_Date(Date pull_Date) {
        this.pull_Date = pull_Date;
    }

    public Integer getNotify_Total() {
        return notify_Total;
    }

    public void setNotify_Total(Integer notify_Total) {
        this.notify_Total = notify_Total;
    }

    public Date getNotify_Date() {
        return notify_Date;
    }

    public void setNotify_Date(Date notify_Date) {
        this.notify_Date = notify_Date;
    }

    public String getNotify_Status_Code() {
        return notify_Status_Code;
    }

    public void setNotify_Status_Code(String notify_Status_Code) {
        this.notify_Status_Code = notify_Status_Code;
    }

    public String getSender_Local_IP() {
        return sender_Local_IP;
    }

    public void setSender_Local_IP(String sender_Local_IP) {
        this.sender_Local_IP = sender_Local_IP;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public Date getCreate_Date() {
        return create_Date;
    }

    public void setCreate_Date(Date create_Date) {
        this.create_Date = create_Date;
    }
}