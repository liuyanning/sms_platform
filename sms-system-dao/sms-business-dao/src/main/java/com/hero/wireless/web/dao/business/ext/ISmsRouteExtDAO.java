package com.hero.wireless.web.dao.business.ext;

import com.hero.wireless.web.dao.business.ISmsRouteDAO;
import com.hero.wireless.web.dao.ext.IExtDAO;
import com.hero.wireless.web.entity.business.SmsRouteExample;
import com.hero.wireless.web.entity.business.ext.SmsRouteExt;

import java.util.List;

public interface ISmsRouteExtDAO extends ISmsRouteDAO<SmsRouteExt>, IExtDAO<SmsRouteExt, SmsRouteExample> {

    List<SmsRouteExt> queryOperatorListByCountry(String countryNumber);

    List<SmsRouteExt> query4LocalCache(SmsRouteExample smsRouteExample);
}
