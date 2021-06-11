package com.hero.wireless.web.dao.ibatis.business.ext;

import com.hero.wireless.web.dao.business.ext.IChargeOrderExtDAO;
import com.hero.wireless.web.dao.ibatis.MybatisBaseBusinessExtDao;
import com.hero.wireless.web.entity.business.ChargeOrder;
import com.hero.wireless.web.entity.business.ChargeOrderExample;
import com.hero.wireless.web.entity.business.ext.ChargeOrderExt;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository("chargeOrderExtDAO")
public class ChargeOrderExtDAOImpl extends MybatisBaseBusinessExtDao<ChargeOrderExt, ChargeOrderExample, ChargeOrder> implements
		IChargeOrderExtDAO {

    @Override
    public List<Map<String, Object>> getChargeOrderListForExport(ChargeOrderExample example) {
        return getSqlBusinessSessionTemplate().selectList("getChargeOrderListForExport",example);
    }
}
