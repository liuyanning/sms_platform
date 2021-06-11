package com.hero.wireless.web.entity.business.ext;

import com.hero.wireless.web.entity.business.AdminLimit;
import com.hero.wireless.web.entity.business.AdminRole;

public class AdminLimitExt extends AdminLimit {
	private AdminRole adminRole;

	public AdminRole getAdminRole() {
		return adminRole;
	}

	public void setAdminRole(AdminRole adminRole) {
		this.adminRole = adminRole;
	}
}