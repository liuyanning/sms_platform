package com.hero.wireless.web.dao.business.ext;

import com.hero.wireless.web.dao.business.IBlackListDAO;
import com.hero.wireless.web.dao.ext.IExtDAO;
import com.hero.wireless.web.entity.business.BlackListExample;
import com.hero.wireless.web.entity.business.ext.BlackListExt;

public interface IBlackListExtDAO extends IBlackListDAO<BlackListExt>, IExtDAO<BlackListExt, BlackListExample> {

}
