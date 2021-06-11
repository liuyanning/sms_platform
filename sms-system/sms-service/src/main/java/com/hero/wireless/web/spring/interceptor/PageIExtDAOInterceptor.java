package com.hero.wireless.web.spring.interceptor;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import com.drondea.wireless.util.SuperLogger;
import com.hero.wireless.web.dao.ext.IExtDAO;
import com.hero.wireless.web.entity.base.BaseEntity;
import com.hero.wireless.web.entity.base.BaseExample;
import com.hero.wireless.web.entity.base.Pagination;

public class PageIExtDAOInterceptor implements MethodInterceptor {
	
	@SuppressWarnings("unchecked")
	@Override
	public Object invoke(MethodInvocation mi) throws Throwable {
		SuperLogger.debug("PageIExtDAOInterceptor");
		IExtDAO<BaseEntity, BaseExample> dao = (IExtDAO<BaseEntity, BaseExample>) mi.getThis();
		BaseExample example = (BaseExample) mi.getArguments()[0];
		Pagination page = example.getPagination();
		if (page == null) {
			return dao.selectExtByExample(example);
		}
		page.setTotalCount(dao.countExtByExample(example));
		if (page.isEmpty()) {
			return null;
		}
		return dao.selectExtByExample(example);
	}
}
