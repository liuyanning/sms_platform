package com.hero.wireless.web.entity.send.ext;

import com.hero.wireless.web.entity.business.Enterprise;
import com.hero.wireless.web.entity.business.EnterpriseUser;
import com.hero.wireless.web.entity.ext.SqlStatisticsEntity;
import com.hero.wireless.web.entity.send.InputLog;

import java.util.Date;

public class InputLogExt extends InputLog {

	private static final long serialVersionUID = 1L;
    private Integer minId;
    private Integer maxId;
	private Enterprise enterprise;
	private EnterpriseUser enterpriseUser;
	private Date minCreateDate;
	private Date maxCreateDate;
    private String time_Cycle;
    private Float time_Cycle_Sale_Fee;
    private Float time_Cycle_Profits;
    private Integer time_Cycle_Fee_Count;
    private SqlStatisticsEntity statisticsEntity;
    private String tableSuffix;

    public Integer getMinId() {
        return minId;
    }

    public void setMinId(Integer minId) {
        this.minId = minId;
    }

    public Integer getMaxId() {
        return maxId;
    }

    public void setMaxId(Integer maxId) {
        this.maxId = maxId;
    }

    public SqlStatisticsEntity getStatisticsEntity() {
        return statisticsEntity;
    }
    public void setStatisticsEntity(SqlStatisticsEntity statisticsEntity) {
        this.statisticsEntity = statisticsEntity;
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

    public String getTime_Cycle() {
        return time_Cycle;
    }

    public void setTime_Cycle(String time_Cycle) {
        this.time_Cycle = time_Cycle;
    }

    public Float getTime_Cycle_Sale_Fee() {
        return time_Cycle_Sale_Fee;
    }

    public void setTime_Cycle_Sale_Fee(Float time_Cycle_Sale_Fee) {
        this.time_Cycle_Sale_Fee = time_Cycle_Sale_Fee;
    }

    public Float getTime_Cycle_Profits() {
        return time_Cycle_Profits;
    }

    public void setTime_Cycle_Profits(Float time_Cycle_Profits) {
        this.time_Cycle_Profits = time_Cycle_Profits;
    }

    public Integer getTime_Cycle_Fee_Count() {
        return time_Cycle_Fee_Count;
    }

    public void setTime_Cycle_Fee_Count(Integer time_Cycle_Fee_Count) {
        this.time_Cycle_Fee_Count = time_Cycle_Fee_Count;
    }

    public String getTableSuffix() {
        return tableSuffix;
    }

    public void setTableSuffix(String tableSuffix) {
        this.tableSuffix = tableSuffix;
    }
}
