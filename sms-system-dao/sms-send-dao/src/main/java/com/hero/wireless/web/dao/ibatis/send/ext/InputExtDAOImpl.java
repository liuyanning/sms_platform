package com.hero.wireless.web.dao.ibatis.send.ext;

import com.hero.wireless.web.dao.ibatis.MybatisBaseSendExtDao;
import com.hero.wireless.web.dao.send.ext.IInputExtDAO;
import com.hero.wireless.web.entity.send.Input;
import com.hero.wireless.web.entity.send.InputExample;
import com.hero.wireless.web.entity.send.ext.InputExt;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository("inputExtDAO")
public class InputExtDAOImpl extends MybatisBaseSendExtDao<InputExt, InputExample, Input> implements IInputExtDAO {

	@Override
	public Integer insertExt(List<Input> list) {
		return (Integer) getSqlSendSessionTemplate().insert(this.sqlStatmentNamespace("ibatorgenerated_insertExt"), list);
	}

	@Override
	public List<Map<String,Object>> queryInputListForExportPage(InputExample example) {
		return getSqlSendSessionTemplate().selectList("queryInputListForExportPage", example);
	}

	@Override
	public List<InputExt> selectAuditingExtByExample(InputExample example) {
		return getSqlSendSessionTemplate().selectList("selectAuditingExtByExample", example);
	}

	@Override
	public int selectAuditingExtByExampleCount(InputExample example) {
		return getSqlSendSessionTemplate().selectOne("selectAuditingExtByExampleCount", example);
	}

}
