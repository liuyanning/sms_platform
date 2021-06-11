package com.hero.wireless.web.dao.business.ext;

import com.hero.wireless.web.dao.business.ISmsRealTimeStatisticsDAO;
import com.hero.wireless.web.dao.ext.IExtDAO;
import com.hero.wireless.web.entity.business.SmsRealTimeStatisticsExample;
import com.hero.wireless.web.entity.business.ext.SmsRealTimeStatisticsExt;

import java.util.List;
import java.util.Map;

public interface ISmsRealTimeStatisticsExtDAO extends ISmsRealTimeStatisticsDAO<SmsRealTimeStatisticsExt>, IExtDAO<SmsRealTimeStatisticsExt, SmsRealTimeStatisticsExample> {

    List<SmsRealTimeStatisticsExt> querySmsRealTimeStatisticsGroupDataList(SmsRealTimeStatisticsExt statisticsExt);

    Map<String,Object> countSmsRealTimeStatisticsGroupDataList(SmsRealTimeStatisticsExt statisticsExt);

    List<Map<String, Object>> querySmsDailySendedStatisticListForExportPage(SmsRealTimeStatisticsExt statisticsExt);
}
