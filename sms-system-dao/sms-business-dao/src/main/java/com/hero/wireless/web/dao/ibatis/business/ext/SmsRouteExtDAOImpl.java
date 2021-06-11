package com.hero.wireless.web.dao.ibatis.business.ext;

import com.hero.wireless.web.dao.business.ext.ISmsRouteExtDAO;
import com.hero.wireless.web.dao.ibatis.MybatisBaseBusinessExtDao;
import com.hero.wireless.web.entity.business.SmsRoute;
import com.hero.wireless.web.entity.business.SmsRouteExample;
import com.hero.wireless.web.entity.business.ext.SmsRouteExt;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("smsRouteExtDAO")
public class SmsRouteExtDAOImpl extends MybatisBaseBusinessExtDao<SmsRouteExt, SmsRouteExample, SmsRoute> implements
        ISmsRouteExtDAO {

    @Override
    public List<SmsRouteExt> queryOperatorListByCountry(String countryNumber) {
        return getSqlBusinessSessionTemplate().selectList("queryOperatorListByCountry", countryNumber);
    }

    @Override
    public List<SmsRouteExt> query4LocalCache(SmsRouteExample smsRouteExample) {
        return getSqlBusinessSessionTemplate().selectList("query4LocalCache", smsRouteExample);
    }
}
