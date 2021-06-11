package com.hero.wireless.web.dao.ibatis.business.ext;

import com.hero.wireless.web.dao.business.ext.ICodeExtDAO;
import com.hero.wireless.web.dao.ibatis.MybatisBaseBusinessExtDao;
import com.hero.wireless.web.entity.business.Code;
import com.hero.wireless.web.entity.business.CodeExample;
import com.hero.wireless.web.entity.business.ext.CodeExt;
import org.springframework.stereotype.Repository;

@Repository("codeExtDAO")
public class CodeExtDAOImpl extends MybatisBaseBusinessExtDao<CodeExt, CodeExample, Code> implements ICodeExtDAO {

}
