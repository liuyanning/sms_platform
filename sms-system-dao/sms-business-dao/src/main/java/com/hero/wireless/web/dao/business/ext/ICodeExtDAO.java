package com.hero.wireless.web.dao.business.ext;

import com.hero.wireless.web.dao.business.ICodeDAO;
import com.hero.wireless.web.dao.ext.IExtDAO;
import com.hero.wireless.web.entity.business.CodeExample;
import com.hero.wireless.web.entity.business.ext.CodeExt;

public interface ICodeExtDAO extends ICodeDAO<CodeExt>, IExtDAO<CodeExt, CodeExample> {

}
