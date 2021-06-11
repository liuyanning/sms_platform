package com.hero.wireless.web.entity.business.ext;

import com.hero.wireless.web.entity.business.EnterpriseLimit;
import com.hero.wireless.web.entity.business.EnterpriseRole;

public class EnterpriseLimitExt extends EnterpriseLimit {
	private EnterpriseRole enterpriseRole;

	public EnterpriseRole getEnterpriseRole() {
		return enterpriseRole;
	}

	public void setEnterpriseRole(EnterpriseRole enterpriseRole) {
		this.enterpriseRole = enterpriseRole;
	}
}
