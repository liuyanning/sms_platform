package com.hero.wireless.web.entity.send.ext;

import com.hero.wireless.web.entity.business.Enterprise;
import com.hero.wireless.web.entity.business.EnterpriseUser;
import com.hero.wireless.web.entity.ext.SqlStatisticsEntity;
import com.hero.wireless.web.entity.send.SubmitAwait;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class SubmitAwaitExt extends SubmitAwait {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer count_Total;
	private Integer submit_Success_Total;
	private String submit_Date_Str;
	private String channel_Name;
	private String type_Code;
	private Date minSubmitDate;
	private Date maxSubmitDate;
	private Enterprise enterprise;
	private EnterpriseUser enterpriseUser;
	private SqlStatisticsEntity statisticsEntity;
    private List<String> enterpriseNoList;
    private Long submitResponseTime;//提交响应时间差（毫秒）
    private int send_Success_Total;
    private int send_Faild_Total;
    private String tableSuffix;
    private BigDecimal sendSpeed;//发送速度
    private Date minCreateDate;
    private Date maxCreateDate;

    private String groupStr;

    public List<String> getEnterpriseNoList() {
        return enterpriseNoList;
    }

    public void setEnterpriseNoList(List<String> enterpriseNoList) {
        this.enterpriseNoList = enterpriseNoList;
    }

    public Integer getCount_Total() {
        return count_Total;
    }

    public void setCount_Total(Integer count_Total) {
        this.count_Total = count_Total;
    }

    public Integer getSubmit_Success_Total() {
        return submit_Success_Total;
    }

    public void setSubmit_Success_Total(Integer submit_Success_Total) {
        this.submit_Success_Total = submit_Success_Total;
    }

    public String getSubmit_Date_Str() {
        return submit_Date_Str;
    }

    public void setSubmit_Date_Str(String submite_Date_Str) {
        this.submit_Date_Str = submite_Date_Str;
    }

    public String getChannel_Name() {
        return channel_Name;
    }

    public void setChannel_Name(String channel_Name) {
        this.channel_Name = channel_Name;
    }

    public String getType_Code() {
        return type_Code;
    }

    public void setType_Code(String type_Code) {
        this.type_Code = type_Code;
    }

    public Date getMinSubmitDate() {
        return minSubmitDate;
    }

    public void setMinSubmitDate(Date minSubmitDate) {
        this.minSubmitDate = minSubmitDate;
    }

    public Date getMaxSubmitDate() {
        return maxSubmitDate;
    }

    public void setMaxSubmitDate(Date maxSubmitDate) {
        this.maxSubmitDate = maxSubmitDate;
    }

    public Enterprise getEnterprise() {
        return enterprise;
    }

    public void setEnterprise(Enterprise enterprise) {
        this.enterprise = enterprise;
    }

    public EnterpriseUser getEnterpriseUser() {
        return enterpriseUser;
    }

    public void setEnterpriseUser(EnterpriseUser enterpriseUser) {
        this.enterpriseUser = enterpriseUser;
    }

    public SqlStatisticsEntity getStatisticsEntity() {
        return statisticsEntity;
    }

    public void setStatisticsEntity(SqlStatisticsEntity statisticsEntity) {
        this.statisticsEntity = statisticsEntity;
    }

    public Long getSubmitResponseTime() {
        if(getSubmit_Date() == null || getSubmit_Response_Date() == null) {
            return null;
        }
        return getSubmit_Response_Date().getTime() - getSubmit_Date().getTime();
    }

    public void setSubmitResponseTime(Long submitResponseTime) {
        this.submitResponseTime = submitResponseTime;
    }

    public int getSend_Success_Total() {
        return send_Success_Total;
    }

    public void setSend_Success_Total(int send_Success_Total) {
        this.send_Success_Total = send_Success_Total;
    }

    public int getSend_Faild_Total() {
        return send_Faild_Total;
    }

    public void setSend_Faild_Total(int send_Faild_Total) {
        this.send_Faild_Total = send_Faild_Total;
    }

    public String getTableSuffix() {
        return tableSuffix;
    }

    public void setTableSuffix(String tableSuffix) {
        this.tableSuffix = tableSuffix;
    }

    public BigDecimal getSendSpeed() {
        return sendSpeed;
    }

    public void setSendSpeed(BigDecimal sendSpeed) {
        this.sendSpeed = sendSpeed;
    }

    public String getGroupStr() {
        return groupStr;
    }

    public void setGroupStr(String groupStr) {
        this.groupStr = groupStr;
    }

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
}
