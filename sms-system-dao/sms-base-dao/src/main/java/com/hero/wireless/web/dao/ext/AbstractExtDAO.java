package com.hero.wireless.web.dao.ext;

import com.hero.wireless.web.entity.base.BaseExample;
import com.hero.wireless.web.entity.base.Pagination;

import java.util.List;

public abstract class AbstractExtDAO<T, E> implements IExtDAO<T, E> {

	@Override
	public List<T> selectExtByExamplePage(E example) {
		BaseExample baseExample = (BaseExample) example;
		Pagination page = baseExample.getPagination();
		if (page != null) {
			page.setTotalCount(countExtByExample(example));
			if (page.isEmpty()) {
				return null;
			}
		}
		return selectExtByExample(example);
	}

	@Override
	public int countExtByExample(E example) {
		return 0;
	}

	@Override
	public List<T> selectExtByExample(E example) {
		return null;
	}

}
