package com.hero.wireless.web.entity.business;

import com.hero.wireless.web.entity.base.BaseEntity;

import java.util.Date;

public class Channel extends BaseEntity {
    private Integer id;

    private String name;

    private String no;

    private String status_Code;

    private String protocol_Type_Code;

    private String session_Status;

    private Date session_Status_Date;

    private String ip;

    private Integer port;

    private String account;

    private String password;

    private String trade_Type_Code;

    private String group_Code;

    private String area_Code;

    private String version;

    private Integer max_Concurrent_Total;

    private Integer submit_Speed;

    private String sp_Number;

    private String signature_Direction_Code;

    private String signature_Position;

    private String channel_Source;

    private String other_Parameter;

    private String sender_Local_IP;

    private Date create_Date;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getStatus_Code() {
        return status_Code;
    }

    public void setStatus_Code(String status_Code) {
        this.status_Code = status_Code;
    }

    public String getProtocol_Type_Code() {
        return protocol_Type_Code;
    }

    public void setProtocol_Type_Code(String protocol_Type_Code) {
        this.protocol_Type_Code = protocol_Type_Code;
    }

    public String getSession_Status() {
        return session_Status;
    }

    public void setSession_Status(String session_Status) {
        this.session_Status = session_Status;
    }

    public Date getSession_Status_Date() {
        return session_Status_Date;
    }

    public void setSession_Status_Date(Date session_Status_Date) {
        this.session_Status_Date = session_Status_Date;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTrade_Type_Code() {
        return trade_Type_Code;
    }

    public void setTrade_Type_Code(String trade_Type_Code) {
        this.trade_Type_Code = trade_Type_Code;
    }

    public String getGroup_Code() {
        return group_Code;
    }

    public void setGroup_Code(String group_Code) {
        this.group_Code = group_Code;
    }

    public String getArea_Code() {
        return area_Code;
    }

    public void setArea_Code(String area_Code) {
        this.area_Code = area_Code;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Integer getMax_Concurrent_Total() {
        return max_Concurrent_Total;
    }

    public void setMax_Concurrent_Total(Integer max_Concurrent_Total) {
        this.max_Concurrent_Total = max_Concurrent_Total;
    }

    public Integer getSubmit_Speed() {
        return submit_Speed;
    }

    public void setSubmit_Speed(Integer submit_Speed) {
        this.submit_Speed = submit_Speed;
    }

    public String getSp_Number() {
        return sp_Number;
    }

    public void setSp_Number(String sp_Number) {
        this.sp_Number = sp_Number;
    }

    public String getSignature_Direction_Code() {
        return signature_Direction_Code;
    }

    public void setSignature_Direction_Code(String signature_Direction_Code) {
        this.signature_Direction_Code = signature_Direction_Code;
    }

    public String getSignature_Position() {
        return signature_Position;
    }

    public void setSignature_Position(String signature_Position) {
        this.signature_Position = signature_Position;
    }

    public String getChannel_Source() {
        return channel_Source;
    }

    public void setChannel_Source(String channel_Source) {
        this.channel_Source = channel_Source;
    }

    public String getOther_Parameter() {
        return other_Parameter;
    }

    public void setOther_Parameter(String other_Parameter) {
        this.other_Parameter = other_Parameter;
    }

    public String getSender_Local_IP() {
        return sender_Local_IP;
    }

    public void setSender_Local_IP(String sender_Local_IP) {
        this.sender_Local_IP = sender_Local_IP;
    }

    public Date getCreate_Date() {
        return create_Date;
    }

    public void setCreate_Date(Date create_Date) {
        this.create_Date = create_Date;
    }
}