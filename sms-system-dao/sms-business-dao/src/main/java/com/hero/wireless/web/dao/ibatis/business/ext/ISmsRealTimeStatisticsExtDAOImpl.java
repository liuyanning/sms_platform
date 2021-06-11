package com.hero.wireless.web.dao.ibatis.business.ext;

import com.hero.wireless.web.dao.business.ext.ISmsRealTimeStatisticsExtDAO;
import com.hero.wireless.web.dao.ibatis.MybatisBaseBusinessExtDao;
import com.hero.wireless.web.entity.business.SmsRealTimeStatistics;
import com.hero.wireless.web.entity.business.SmsRealTimeStatisticsExample;
import com.hero.wireless.web.entity.business.ext.SmsRealTimeStatisticsExt;

import java.util.List;
import java.util.Map;


public class ISmsRealTimeStatisticsExtDAOImpl extends
        MybatisBaseBusinessExtDao<SmsRealTimeStatisticsExt, SmsRealTimeStatisticsExample, SmsRealTimeStatistics> implements ISmsRealTimeStatisticsExtDAO {


    @Override
    public List<SmsRealTimeStatisticsExt> querySmsRealTimeStatisticsGroupDataList(SmsRealTimeStatisticsExt statisticsExt) {
        List<SmsRealTimeStatisticsExt> list = getSqlBusinessSessionTemplate()
                .selectList("querySmsRealTimeStatisticsGroupDataList", statisticsExt);
        return list;
    }

    @Override
    public Map<String,Object> countSmsRealTimeStatisticsGroupDataList(SmsRealTimeStatisticsExt statisticsExt) {
        return getSqlBusinessSessionTemplate().selectOne("countSmsRealTimeStatisticsGroupDataList", statisticsExt);
    }

    @Override
    public List<Map<String, Object>> querySmsDailySendedStatisticListForExportPage(SmsRealTimeStatisticsExt statisticsExt) {
        List<Map<String, Object>> list = getSqlBusinessSessionTemplate()
                .selectList("querySmsDailySendedStatisticListForExportPage", statisticsExt);
        return list;
    }
}
