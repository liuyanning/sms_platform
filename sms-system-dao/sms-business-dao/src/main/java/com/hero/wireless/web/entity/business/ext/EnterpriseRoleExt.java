package com.hero.wireless.web.entity.business.ext;

import com.hero.wireless.web.entity.business.EnterpriseRole;
import com.hero.wireless.web.entity.business.EnterpriseUser;

public class EnterpriseRoleExt extends EnterpriseRole {
	private EnterpriseUser enterpriseUser;

	public EnterpriseUser getEnterpriseUserInfo() {
		return enterpriseUser;
	}

	public void setEnterpriseUserInfo(EnterpriseUser enterpriseUserInfo) {
		this.enterpriseUser = enterpriseUser;
	}
}
