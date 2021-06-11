package com.hero.wireless.web.entity.business.ext;

import com.hero.wireless.web.entity.business.AdminLimit;
import com.hero.wireless.web.entity.business.AdminRole;
import com.hero.wireless.web.entity.business.AdminUser;

import java.util.List;

public class AdminUserExt extends AdminUser {
	private List<AdminRole> roles;

	private List<AdminLimit> limits;
	
	private List<AdminLimit> limitOrderIds;

	public List<AdminLimit> getLimitOrderIds() {
		return limitOrderIds;
	}

	public void setLimitOrderIds(List<AdminLimit> limitOrderIds) {
		this.limitOrderIds = limitOrderIds;
	}

	public List<AdminLimit> getLimits() {
		return limits;
	}

	public List<AdminRole> getRoles() {
		return roles;
	}

	public void setLimits(List<AdminLimit> limits) {
		this.limits = limits;
	}

	public void setRoles(List<AdminRole> roles) {
		this.roles = roles;
	}
}
