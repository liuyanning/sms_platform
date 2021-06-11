package com.hero.wireless.json;

import java.util.Map;

import com.hero.wireless.web.entity.base.IEntity;
import com.hero.wireless.web.entity.base.Pagination;

public class LayUIJsonListFmt extends LayUiJsonObjectFmt {
	@Deprecated
	private int count;
	private IEntity statistics;
	private Pagination pagination;
	private Map<String,Object> map;

	public Map<String, Object> getMap() {
		return map;
	}

	public void setMap(Map<String, Object> map) {
		this.map = map;
	}

	public Pagination getPagination() {
		return pagination;
	}

	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}

	public IEntity getStatistics() {
		return statistics;
	}

	public void setStatistics(IEntity statistics) {
		this.statistics = statistics;
	}

	@Deprecated
	public int getCount() {
		return count;
	}

	@Deprecated
	public void setCount(int count) {
		this.count = count;
	}

	@Deprecated
	public LayUIJsonListFmt(String code, String msg, Object data, int count) {
		super(code, msg, data);
		this.count = count;
	}

	@Deprecated
	public LayUIJsonListFmt(String code, String msg, Object data, int count, Map<String,Object> map) {
		super(code, msg, data,map);
		this.count = count;
		this.map = map;
	}

	public LayUIJsonListFmt(String code, String msg, Object data, Pagination pagination) {
		super(code, msg, data);
		this.pagination = pagination;
	}

	public LayUIJsonListFmt(String code, String msg, Object data, Pagination pagination, IEntity statistics) {
		this(code, msg, data, pagination);
		this.statistics = statistics;
	}

	@Deprecated
	public LayUIJsonListFmt(String code, String msg, Object data, int count, IEntity statistics) {
		this(code, msg, data, count);
		this.statistics = statistics;
	}
}
