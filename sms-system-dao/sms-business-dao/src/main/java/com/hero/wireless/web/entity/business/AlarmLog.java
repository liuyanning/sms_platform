package com.hero.wireless.web.entity.business;

import com.hero.wireless.web.entity.base.BaseEntity;

import java.util.Date;

public class AlarmLog extends BaseEntity {
    private Integer id;

    private Integer alarm_Id;

    private String type_Code;//预警类型

    private String bind_Value;//绑定对象

    private String threshold_Value;//阈值

    private Date begin_Date;

    private Date end_Date;

    private String probe_Value;//探测值

    private String probe_Result;//探测结果

    private String phone_Nos;

    private String emails;

    private String description;

    private String remark;

    private Date create_Date;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAlarm_Id() {
        return alarm_Id;
    }

    public void setAlarm_Id(Integer alarm_Id) {
        this.alarm_Id = alarm_Id;
    }

    public String getType_Code() {
        return type_Code;
    }

    public void setType_Code(String type_Code) {
        this.type_Code = type_Code;
    }

    public String getBind_Value() {
        return bind_Value;
    }

    public void setBind_Value(String bind_Value) {
        this.bind_Value = bind_Value;
    }

    public String getThreshold_Value() {
        return threshold_Value;
    }

    public void setThreshold_Value(String threshold_Value) {
        this.threshold_Value = threshold_Value;
    }

    public Date getBegin_Date() {
        return begin_Date;
    }

    public void setBegin_Date(Date begin_Date) {
        this.begin_Date = begin_Date;
    }

    public Date getEnd_Date() {
        return end_Date;
    }

    public void setEnd_Date(Date end_Date) {
        this.end_Date = end_Date;
    }

    public String getProbe_Value() {
        return probe_Value;
    }

    public void setProbe_Value(String probe_Value) {
        this.probe_Value = probe_Value;
    }

    public String getProbe_Result() {
        return probe_Result;
    }

    public void setProbe_Result(String probe_Result) {
        this.probe_Result = probe_Result;
    }

    public String getPhone_Nos() {
        return phone_Nos;
    }

    public void setPhone_Nos(String phone_Nos) {
        this.phone_Nos = phone_Nos;
    }

    public String getEmails() {
        return emails;
    }

    public void setEmails(String emails) {
        this.emails = emails;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreate_Date() {
        return create_Date;
    }

    public void setCreate_Date(Date create_Date) {
        this.create_Date = create_Date;
    }
}