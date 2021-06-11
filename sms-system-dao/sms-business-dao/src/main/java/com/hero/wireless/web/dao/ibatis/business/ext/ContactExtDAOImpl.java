package com.hero.wireless.web.dao.ibatis.business.ext;

import com.hero.wireless.web.dao.business.ext.IContactExtDAO;
import com.hero.wireless.web.dao.ibatis.MybatisBaseBusinessExtDao;
import com.hero.wireless.web.entity.business.Contact;
import com.hero.wireless.web.entity.business.ContactExample;
import com.hero.wireless.web.entity.business.ext.ContactExt;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository("contactExtDAO")
public class ContactExtDAOImpl extends MybatisBaseBusinessExtDao<ContactExt, ContactExample, Contact>
		implements IContactExtDAO {

    @Override
    public List<Map<String, Object>> queryContactListForExportPage(ContactExample example) {
        return getSqlBusinessSessionTemplate().selectList("queryContactListForExportPage",example);
    }
}
