package com.hero.wireless.web.entity.business.ext;

import com.hero.wireless.web.entity.business.Alarm;

import java.util.Date;
import java.util.List;

public class AlarmExt extends Alarm {

    private Integer bind_Value_User_Id;
    private String bind_Value_Agent_No;
    private String bind_Value_Channel_No;
    private String bind_Value_Product_No;
    private String bind_Value_Server_Ip;
    private String bind_Value_Input_Id;
    private boolean alarm_Result;
    private String alarm_Email_Title;
    private String alarm_Content;
    private String recovery_Email_Title;
    private String recovery_Content;
    private List<String> channel_No_List;
    private String min_Create_Date;
    private String max_Create_Date;
    private String monitorStartTime;
    private String monitorEndTime;

    private Date min_Submit_Date;
    private Date max_Submit_Date;
    private String channel_No;
    private String product_No;

    public String getBind_Value_Agent_No() {
        return bind_Value_Agent_No;
    }

    public void setBind_Value_Agent_No(String bind_Value_Agent_No) {
        this.bind_Value_Agent_No = bind_Value_Agent_No;
    }

    public Integer getBind_Value_User_Id() {
        return bind_Value_User_Id;
    }

    public void setBind_Value_User_Id(Integer bind_Value_User_Id) {
        this.bind_Value_User_Id = bind_Value_User_Id;
    }

    public String getBind_Value_Channel_No() {
        return bind_Value_Channel_No;
    }

    public void setBind_Value_Channel_No(String bind_Value_Channel_No) {
        this.bind_Value_Channel_No = bind_Value_Channel_No;
    }

    public String getBind_Value_Server_Ip() {
        return bind_Value_Server_Ip;
    }

    public void setBind_Value_Server_Ip(String bind_Value_Server_Ip) {
        this.bind_Value_Server_Ip = bind_Value_Server_Ip;
    }

    public String getBind_Value_Input_Id() {
        return bind_Value_Input_Id;
    }

    public void setBind_Value_Input_Id(String bind_Value_Input_Id) {
        this.bind_Value_Input_Id = bind_Value_Input_Id;
    }

    public boolean isAlarm_Result() {
        return alarm_Result;
    }

    public void setAlarm_Result(boolean alarm_Result) {
        this.alarm_Result = alarm_Result;
    }

    public String getAlarm_Email_Title() {
        return alarm_Email_Title;
    }

    public void setAlarm_Email_Title(String alarm_Email_Title) {
        this.alarm_Email_Title = alarm_Email_Title;
    }

    public String getAlarm_Content() {
        return alarm_Content;
    }

    public void setAlarm_Content(String alarm_Content) {
        this.alarm_Content = alarm_Content;
    }

    public String getRecovery_Email_Title() {
        return recovery_Email_Title;
    }

    public void setRecovery_Email_Title(String recovery_Email_Title) {
        this.recovery_Email_Title = recovery_Email_Title;
    }

    public String getRecovery_Content() {
        return recovery_Content;
    }

    public void setRecovery_Content(String recovery_Content) {
        this.recovery_Content = recovery_Content;
    }

    public String getBind_Value_Product_No() {
        return bind_Value_Product_No;
    }

    public void setBind_Value_Product_No(String bind_Value_Product_No) {
        this.bind_Value_Product_No = bind_Value_Product_No;
    }

    public List<String> getChannel_No_List() {
        return channel_No_List;
    }

    public void setChannel_No_List(List<String> channel_No_List) {
        this.channel_No_List = channel_No_List;
    }

    public String getMin_Create_Date() {
        return min_Create_Date;
    }

    public void setMin_Create_Date(String min_Create_Date) {
        this.min_Create_Date = min_Create_Date;
    }

    public String getMax_Create_Date() {
        return max_Create_Date;
    }

    public void setMax_Create_Date(String max_Create_Date) {
        this.max_Create_Date = max_Create_Date;
    }

    public String getMonitorStartTime() {
        return monitorStartTime;
    }

    public void setMonitorStartTime(String monitorStartTime) {
        this.monitorStartTime = monitorStartTime;
    }

    public String getMonitorEndTime() {
        return monitorEndTime;
    }

    public void setMonitorEndTime(String monitorEndTime) {
        this.monitorEndTime = monitorEndTime;
    }

    public Date getMin_Submit_Date() {
        return min_Submit_Date;
    }

    public void setMin_Submit_Date(Date min_Submit_Date) {
        this.min_Submit_Date = min_Submit_Date;
    }

    public Date getMax_Submit_Date() {
        return max_Submit_Date;
    }

    public void setMax_Submit_Date(Date max_Submit_Date) {
        this.max_Submit_Date = max_Submit_Date;
    }

    public String getChannel_No() {
        return channel_No;
    }

    public void setChannel_No(String channel_No) {
        this.channel_No = channel_No;
    }

    public String getProduct_No() {
        return product_No;
    }

    public void setProduct_No(String product_No) {
        this.product_No = product_No;
    }
}