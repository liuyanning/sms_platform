package com.hero.wireless.web.entity.business.ext;

import com.hero.wireless.web.entity.business.AdminRole;
import com.hero.wireless.web.entity.business.AdminUser;

public class AdminRoleExt extends AdminRole {
    
    private AdminUser adminUser;

	public AdminUser getAdminUser() {
		return adminUser;
	}

	public void setAdminUser(AdminUser adminUser) {
		this.adminUser = adminUser;
	}
}