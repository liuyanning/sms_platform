package com.hero.wireless.web.dao.business.ext;

import com.hero.wireless.web.dao.business.IChargeOrderDAO;
import com.hero.wireless.web.dao.ext.IExtDAO;
import com.hero.wireless.web.entity.business.ChargeOrderExample;
import com.hero.wireless.web.entity.business.ext.ChargeOrderExt;

import java.util.List;
import java.util.Map;

public interface IChargeOrderExtDAO extends IChargeOrderDAO<ChargeOrderExt>, IExtDAO<ChargeOrderExt, ChargeOrderExample> {

    List<Map<String, Object>> getChargeOrderListForExport(ChargeOrderExample example);
}
