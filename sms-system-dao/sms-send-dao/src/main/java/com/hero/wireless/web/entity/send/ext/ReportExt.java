package com.hero.wireless.web.entity.send.ext;

import com.hero.wireless.web.entity.business.Enterprise;
import com.hero.wireless.web.entity.business.EnterpriseUser;
import com.hero.wireless.web.entity.ext.SqlStatisticsEntity;
import com.hero.wireless.web.entity.send.Report;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class ReportExt extends Report {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private String channel_Name;
	private Date minSubmitDate;
	private Date maxSubmitDate;
	private Date minCreateDate;
	private Date maxCreateDate;
	private Enterprise enterprise;
	private EnterpriseUser enterpriseUser;
	private List<Integer> enterpriseIds;
	private List<String> enterpriseNoList;
	private Integer count_Total;
	private SqlStatisticsEntity statisticsEntity;
	private int send_Success_Total;
	private int send_Faild_Total;
	private String mcc;
	private String mnc;
	private String tableSuffix;
	private BigDecimal faild_Code_Rate;

	private String groupStr;

    public String getMcc() {
        return mcc;
    }

    public void setMcc(String mcc) {
        this.mcc = mcc;
    }

    public String getMnc() {
        return mnc;
    }

    public void setMnc(String mnc) {
        this.mnc = mnc;
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

	public String getChannel_Name() {
		return channel_Name;
	}

	public void setChannel_Name(String channel_Name) {
		this.channel_Name = channel_Name;
	}

	public SqlStatisticsEntity getStatisticsEntity() {
		return statisticsEntity;
	}

	public void setStatisticsEntity(SqlStatisticsEntity statisticsEntity) {
		this.statisticsEntity = statisticsEntity;
	}

	public List<Integer> getEnterpriseIds() {
		return enterpriseIds;
	}

	public void setEnterpriseIds(List<Integer> enterpriseIds) {
		this.enterpriseIds = enterpriseIds;
	}

	public Integer getCount_Total() {
		return count_Total;
	}

	public void setCount_Total(Integer count_Total) {
		this.count_Total = count_Total;
	}

	public List<String> getEnterpriseNoList() {
		return enterpriseNoList;
	}

	public void setEnterpriseNoList(List<String> enterpriseNoList) {
		this.enterpriseNoList = enterpriseNoList;
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

	public BigDecimal getFaild_Code_Rate() {
		return faild_Code_Rate;
	}

	public void setFaild_Code_Rate(BigDecimal faild_Code_Rate) {
		this.faild_Code_Rate = faild_Code_Rate;
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

	public String getGroupStr() {
		return groupStr;
	}

	public void setGroupStr(String groupStr) {
		this.groupStr = groupStr;
	}
}
