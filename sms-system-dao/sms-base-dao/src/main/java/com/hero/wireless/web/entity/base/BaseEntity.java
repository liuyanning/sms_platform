/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hero.wireless.web.entity.base;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 
 * @author Volcano
 */
public class BaseEntity implements IEntity {

	private static final long serialVersionUID = 1L;
	
	private static final Log LOG = LogFactory.getLog(BaseEntity.class);
	private Pagination pagination;

	/**
	 * @return the pagination
	 */
	public Pagination getPagination() {
		return pagination;
	}

	/**
	 * @param pagination
	 *            the pagination to set
	 */
	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}

	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer(super.toString());
		sb.append("\n");
		Class<BaseEntity> baseEntityClass = (Class<BaseEntity>) this.getClass();
		Field[] fields = baseEntityClass.getDeclaredFields();
		if (fields == null || fields.length == 0) {
			return sb.toString();
		}
		for (int i = 0; i < fields.length; i++) {
			LOG.debug(fields[i].getType());
			LOG.debug(fields[i].getName());
			fields[i].setAccessible(true);
			Object value = null;
			try {
				value = fields[i].get(this);
			} catch (IllegalArgumentException e) {
				LOG.error(e.getMessage(), e);
			} catch (IllegalAccessException e) {
				LOG.error(e.getMessage(), e);
			}
			if (value == null) {
				sb.append(fields[i].getName()).append("=").append("null")
						.append(",");
				continue;
			}
			if (fields[i].getType() == Calendar.class) {
				value = ((Calendar) value).getTime();
				value = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
						.format((Date) value);
				sb.append(fields[i].getName()).append("=").append(value)
						.append(",");
				continue;
			}
			if (fields[i].getType() == Date.class) {
				value = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
						.format((Date) value);
				sb.append(fields[i].getName()).append("=").append(value)
						.append(",");
				continue;
			}
			sb.append(fields[i].getName()).append("=").append(value)
					.append(",");
		}
		return sb.toString();
	}
}
