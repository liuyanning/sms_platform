package com.hero.wireless.web.dao.ibatis.business.ext;

import com.hero.wireless.web.dao.business.ext.ISmsStatisticsExtDAO;
import com.hero.wireless.web.dao.ibatis.MybatisBaseBusinessExtDao;
import com.hero.wireless.web.entity.business.SmsStatistics;
import com.hero.wireless.web.entity.business.SmsStatisticsExample;
import com.hero.wireless.web.entity.business.ext.SmsStatisticsExt;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository("smsStatisticsExtDAO")
public class SmsStatisticsExtDAOImpl extends
	MybatisBaseBusinessExtDao<SmsStatisticsExt, SmsStatisticsExample, SmsStatistics> implements ISmsStatisticsExtDAO {

	@Override
	public List<SmsStatisticsExt> querySmsSendFailedByExt(SmsStatisticsExt smsStatisticsExt) {
		return getSqlBusinessSessionTemplate().selectList("querySmsSendFailedByExt", smsStatisticsExt);
	}

	@Override
	public List<Map<String, Object>> getSmsStatisticListForExport(SmsStatisticsExt smsStatisticsExt) {
		return getSqlBusinessSessionTemplate().selectList("getSmsStatisticListForExport", smsStatisticsExt);
	}

	@Override
	public Map<String, Object> countSmsStatisticExtListByExt(SmsStatisticsExt smsStatisticsExt) {
		return getSqlBusinessSessionTemplate()
			.selectOne("countSmsStatisticExtListByExt", smsStatisticsExt);
	}

	@Override
	public List<SmsStatisticsExt> getSmsStatisticExtListByExtPage(SmsStatisticsExt smsStatisticsExt) {
		return getSqlBusinessSessionTemplate().selectList("getSmsStatisticExtListByExtPage", smsStatisticsExt);
	}

//	@Override
//	public List<SmsStatistics> getSubmitSmsStatisticsByExt(
//			SmsStatisticsExt smsStatisticsExt) {
//		List<SmsStatistics> list = getSqlBusinessSessionTemplate().selectList("getSubmitSmsStatisticsByExt",smsStatisticsExt);
//		return list;
//	}

//	@Override
//	public List<SmsStatistics> getReportSmsStatisticsByExt(
//			SmsStatisticsExt smsStatisticsExt) {
//		List<SmsStatistics> list = getSqlBusinessSessionTemplate().selectList("getReportSmsStatisticsByExt",smsStatisticsExt);
//		return list;
//	}

}
