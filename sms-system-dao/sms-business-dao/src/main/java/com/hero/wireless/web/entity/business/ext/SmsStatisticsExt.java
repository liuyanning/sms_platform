package com.hero.wireless.web.entity.business.ext;

import com.hero.wireless.web.entity.business.Enterprise;
import com.hero.wireless.web.entity.business.EnterpriseUser;
import com.hero.wireless.web.entity.business.SmsStatistics;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class SmsStatisticsExt extends SmsStatistics {
    private Date minCreateDate;
    private Date maxCreateDate;

    private String min_Statistics_Date_Str;
    private String max_Statistics_Date_Str;
    private String group_Str;
    private String time_Cycle;

    private Date min_Statistics_Date;
    private Date max_Statistics_Date;
    private String statisticsDateStr;

    private String interface_Name;

    private String enterprise_Name;
    private List<String> enterprise_No_List;
    private List<String> channel_No_List;
    private Integer unknownTotal;
    private Integer faildTotal;

    private String business_Name;
    private String channel_Name;
    private String province_Name;

    private Enterprise Enterprise;
    private EnterpriseUser enterpriseUser;

    private String cmp_Channel_No;
    private String sgi_Channel_No;
    private String smg_Channel_No;

    private BigDecimal min_Success_Rate;
    private BigDecimal max_Success_Rate;
    private BigDecimal min_Failure_Rate;
    private BigDecimal max_Failure_Rate;

    private double proportion;
    private String databaseName;

    private String business_User_Id_Ext;
    private String enterprise_User_Id_Ext;

    public Date getMinCreateDate() {
        return minCreateDate;
    }

    public void setMinCreateDate(Date minCreateDate) {
        this.minCreateDate = minCreateDate;
    }

    public Date getMaxCreateDate() {
        return maxCreateDate;
    }

    public void setMaxCreateDate(Date maxCreateDate) {
        this.maxCreateDate = maxCreateDate;
    }

    public String getMin_Statistics_Date_Str() {
        return min_Statistics_Date_Str;
    }

    public void setMin_Statistics_Date_Str(String min_Statistics_Date_Str) {
        this.min_Statistics_Date_Str = min_Statistics_Date_Str;
    }

    public String getMax_Statistics_Date_Str() {
        return max_Statistics_Date_Str;
    }

    public void setMax_Statistics_Date_Str(String max_Statistics_Date_Str) {
        this.max_Statistics_Date_Str = max_Statistics_Date_Str;
    }

    public String getGroup_Str() {
        return group_Str;
    }

    public void setGroup_Str(String group_Str) {
        this.group_Str = group_Str;
    }

    public String getTime_Cycle() {
        return time_Cycle;
    }

    public void setTime_Cycle(String time_Cycle) {
        this.time_Cycle = time_Cycle;
    }

    public Date getMin_Statistics_Date() {
        return min_Statistics_Date;
    }

    public void setMin_Statistics_Date(Date min_Statistics_Date) {
        this.min_Statistics_Date = min_Statistics_Date;
    }

    public Date getMax_Statistics_Date() {
        return max_Statistics_Date;
    }

    public void setMax_Statistics_Date(Date max_Statistics_Date) {
        this.max_Statistics_Date = max_Statistics_Date;
    }

    public String getStatisticsDateStr() {
        return statisticsDateStr;
    }

    public void setStatisticsDateStr(String statisticsDateStr) {
        this.statisticsDateStr = statisticsDateStr;
    }

    public String getInterface_Name() {
        return interface_Name;
    }

    public void setInterface_Name(String interface_Name) {
        this.interface_Name = interface_Name;
    }

    public String getEnterprise_Name() {
        return enterprise_Name;
    }

    public void setEnterprise_Name(String enterprise_Name) {
        this.enterprise_Name = enterprise_Name;
    }

    public String getProvince_Name() {
        return province_Name;
    }

    public void setProvince_Name(String province_Name) {
        this.province_Name = province_Name;
    }

    public List<String> getEnterprise_No_List() {
        return enterprise_No_List;
    }

    public void setEnterprise_No_List(List<String> enterprise_No_List) {
        this.enterprise_No_List = enterprise_No_List;
    }

    public List<String> getChannel_No_List() {
        return channel_No_List;
    }

    public void setChannel_No_List(List<String> channel_No_List) {
        this.channel_No_List = channel_No_List;
    }

    public Integer getUnknownTotal() {
        return unknownTotal;
    }

    public void setUnknownTotal(Integer unknownTotal) {
        this.unknownTotal = unknownTotal;
    }

    public Integer getFaildTotal() {
        return faildTotal;
    }

    public void setFaildTotal(Integer faildTotal) {
        this.faildTotal = faildTotal;
    }

    public String getBusiness_Name() {
        return business_Name;
    }

    public void setBusiness_Name(String business_Name) {
        this.business_Name = business_Name;
    }

    public String getChannel_Name() {
        return channel_Name;
    }

    public void setChannel_Name(String channel_Name) {
        this.channel_Name = channel_Name;
    }

    public com.hero.wireless.web.entity.business.Enterprise getEnterprise() {
        return Enterprise;
    }

    public void setEnterprise(com.hero.wireless.web.entity.business.Enterprise enterprise) {
        Enterprise = enterprise;
    }

    public EnterpriseUser getEnterpriseUser() {
        return enterpriseUser;
    }

    public void setEnterpriseUser(EnterpriseUser enterpriseUser) {
        this.enterpriseUser = enterpriseUser;
    }

    public String getCmp_Channel_No() {
        return cmp_Channel_No;
    }

    public void setCmp_Channel_No(String cmp_Channel_No) {
        this.cmp_Channel_No = cmp_Channel_No;
    }

    public String getSgi_Channel_No() {
        return sgi_Channel_No;
    }

    public void setSgi_Channel_No(String sgi_Channel_No) {
        this.sgi_Channel_No = sgi_Channel_No;
    }

    public String getSmg_Channel_No() {
        return smg_Channel_No;
    }

    public void setSmg_Channel_No(String smg_Channel_No) {
        this.smg_Channel_No = smg_Channel_No;
    }

    public BigDecimal getMin_Success_Rate() {
        return min_Success_Rate;
    }

    public void setMin_Success_Rate(BigDecimal min_Success_Rate) {
        this.min_Success_Rate = min_Success_Rate;
    }

    public BigDecimal getMax_Success_Rate() {
        return max_Success_Rate;
    }

    public void setMax_Success_Rate(BigDecimal max_Success_Rate) {
        this.max_Success_Rate = max_Success_Rate;
    }

    public BigDecimal getMin_Failure_Rate() {
        return min_Failure_Rate;
    }

    public void setMin_Failure_Rate(BigDecimal min_Failure_Rate) {
        this.min_Failure_Rate = min_Failure_Rate;
    }

    public BigDecimal getMax_Failure_Rate() {
        return max_Failure_Rate;
    }

    public void setMax_Failure_Rate(BigDecimal max_Failure_Rate) {
        this.max_Failure_Rate = max_Failure_Rate;
    }

    public double getProportion() {
        return proportion;
    }

    public void setProportion(double proportion) {
        this.proportion = proportion;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public String getBusiness_User_Id_Ext() {
        return business_User_Id_Ext;
    }

    public void setBusiness_User_Id_Ext(String business_User_Id_Ext) {
        this.business_User_Id_Ext = business_User_Id_Ext;
    }

    public String getEnterprise_User_Id_Ext() {
        return enterprise_User_Id_Ext;
    }

    public void setEnterprise_User_Id_Ext(String enterprise_User_Id_Ext) {
        this.enterprise_User_Id_Ext = enterprise_User_Id_Ext;
    }
}
