package com.hero.wireless.web.entity.base;

public class BaseExample {
	private BaseEntity baseEntity;
	public BaseEntity getBaseEntity() {
		return baseEntity;
	}
	public void setBaseEntity(BaseEntity baseEntity) {
		this.baseEntity = baseEntity;
	}
	private Pagination pagination;
	public Pagination getPagination() {
		return pagination;
	}
	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}
}
