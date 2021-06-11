package com.hero.wireless.web.entity.business.ext;

import com.hero.wireless.web.entity.business.Enterprise;
import com.hero.wireless.web.entity.ext.SqlStatisticsEntity;

import java.math.BigDecimal;

public class EnterpriseExt extends Enterprise {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private Enterprise enterprise;

	private Integer upEnterpriseId;//上级企业id

	private String upEnterpriseName;//上级企业name

	private BigDecimal min_Available_Money;//最小可用金额

	private BigDecimal max_Available_Money;//最大可用金额

	private String beginTime;

	private String endTime;

	private String roleCode;
	private String status_Name;
	private String authentication_State_Code_Name;

	private SqlStatisticsEntity statisticsEntity;
	public SqlStatisticsEntity getStatisticsEntity() {
		return statisticsEntity;
	}
	public void setStatisticsEntity(SqlStatisticsEntity statisticsEntity) {
		this.statisticsEntity = statisticsEntity;
	}

	public BigDecimal getMin_Available_Money() {
		return min_Available_Money;
	}

	public void setMin_Available_Money(BigDecimal min_Available_Money) {
		this.min_Available_Money = min_Available_Money;
	}

	public BigDecimal getMax_Available_Money() {
		return max_Available_Money;
	}

	public void setMax_Available_Money(BigDecimal max_Available_Money) {
		this.max_Available_Money = max_Available_Money;
	}

	public Integer getUpEnterpriseId() {
		return upEnterpriseId;
	}

	public void setUpEnterpriseId(Integer upEnterpriseId) {
		this.upEnterpriseId = upEnterpriseId;
	}

	public String getUpEnterpriseName() {
		return upEnterpriseName;
	}

	public void setUpEnterpriseName(String upEnterpriseName) {
		this.upEnterpriseName = upEnterpriseName;
	}

	public Enterprise getEnterprise() {
		return enterprise;
	}

	public void setEnterprise(Enterprise enterprise) {
		this.enterprise = enterprise;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getStatus_Name() {
		return status_Name;
	}

	public void setStatus_Name(String status_Name) {
		this.status_Name = status_Name;
	}

	public String getAuthentication_State_Code_Name() {
		return authentication_State_Code_Name;
	}

	public void setAuthentication_State_Code_Name(String authentication_State_Code_Name) {
		this.authentication_State_Code_Name = authentication_State_Code_Name;
	}
}
