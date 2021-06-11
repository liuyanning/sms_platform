package com.hero.wireless.web.entity.business.ext;

import com.hero.wireless.web.entity.business.BlackList;
import com.hero.wireless.web.entity.business.Enterprise;

public class BlackListExt extends BlackList {
	private Enterprise enterprise;
	private String enterprise_Name;

	public String getEnterprise_Name() {
		return enterprise_Name;
	}

	public void setEnterprise_Name(String enterprise_Name) {
		this.enterprise_Name = enterprise_Name;
	}

	public Enterprise getEnterprise() {
		return enterprise;
	}

	public void setEnterprise(Enterprise enterprise) {
		this.enterprise = enterprise;
	}

}
