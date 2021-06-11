package com.hero.wireless.web.spring.interceptor;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import com.drondea.wireless.util.SuperLogger;
import com.hero.wireless.web.dao.base.IDao;
import com.hero.wireless.web.entity.base.BaseEntity;
import com.hero.wireless.web.entity.base.BaseExample;
import com.hero.wireless.web.entity.base.Pagination;

public class PageIDAOInterceptor implements MethodInterceptor {
	
	@SuppressWarnings("unchecked")
	@Override
	public Object invoke(MethodInvocation mi) throws Throwable {
		SuperLogger.debug("PageIDAOInterceptor");
		IDao<BaseEntity, BaseExample> dao = (IDao<BaseEntity, BaseExample>) mi.getThis();
		BaseExample example = (BaseExample) mi.getArguments()[0];
		Pagination page = example.getPagination();
		if (page == null) {
			return dao.selectByExample(example);
		}
		page.setTotalCount(dao.countByExample(example));
		if (page.isEmpty()) {
			return null;
		}
		return dao.selectByExample(example);
	}
}
