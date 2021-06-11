package com.hero.wireless.web.dao.business.ext;

import com.hero.wireless.web.dao.business.IContactDAO;
import com.hero.wireless.web.dao.ext.IExtDAO;
import com.hero.wireless.web.entity.business.ContactExample;
import com.hero.wireless.web.entity.business.ext.ContactExt;

import java.util.List;
import java.util.Map;

public interface IContactExtDAO extends IContactDAO<ContactExt>,
		IExtDAO<ContactExt, ContactExample> {

    List<Map<String, Object>> queryContactListForExportPage(ContactExample example);
}
