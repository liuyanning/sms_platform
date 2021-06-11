package com.hero.wireless.web.dao.business.ext;

import com.hero.wireless.web.dao.business.IEnterpriseDAO;
import com.hero.wireless.web.dao.ext.IExtDAO;
import com.hero.wireless.web.entity.business.EnterpriseExample;
import com.hero.wireless.web.entity.business.ext.EnterpriseExt;

import java.util.List;
import java.util.Map;

public interface IEnterpriseExtDAO extends IEnterpriseDAO<EnterpriseExt>,IExtDAO<EnterpriseExt, EnterpriseExample> {
    List<Map<String, Object>> queryEnterpriseListForExportPage(EnterpriseExample example);
}
