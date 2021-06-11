package com.hero.wireless.web.entity.business.ext;

import com.hero.wireless.web.entity.business.ChargeOrder;
import com.hero.wireless.web.entity.business.Enterprise;
import com.hero.wireless.web.entity.business.EnterpriseUser;

public class ChargeOrderExt extends ChargeOrder {
	private Enterprise enterprise;
	private EnterpriseUser enterpriseUser;
    private String searchStartDate;
    private String searchEndDate;
	private String min_Input_Date;
	private String max_Input_Date;

	public String getMin_Input_Date() {
		return min_Input_Date;
	}

	public void setMin_Input_Date(String min_Input_Date) {
		this.min_Input_Date = min_Input_Date;
	}

	public String getMax_Input_Date() {
		return max_Input_Date;
	}

	public void setMax_Input_Date(String max_Input_Date) {
		this.max_Input_Date = max_Input_Date;
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

    public String getSearchStartDate() {
		return searchStartDate;
	}

	public void setSearchStartDate(String searchStartDate) {
		this.searchStartDate = searchStartDate;
	}

	public String getSearchEndDate() {
		return searchEndDate;
	}

	public void setSearchEndDate(String searchEndDate) {
		this.searchEndDate = searchEndDate;
	}
	
}
