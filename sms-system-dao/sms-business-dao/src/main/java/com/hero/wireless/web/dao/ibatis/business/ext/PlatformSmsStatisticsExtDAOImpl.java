package com.hero.wireless.web.dao.ibatis.business.ext;

import com.hero.wireless.web.dao.business.ext.IPlatformSmsStatisticsExtDAO;
import com.hero.wireless.web.dao.ibatis.MybatisBaseBusinessExtDao;
import com.hero.wireless.web.entity.business.PlatformSmsStatistics;
import com.hero.wireless.web.entity.business.PlatformSmsStatisticsExample;
import com.hero.wireless.web.entity.business.ext.PlatformSmsStatisticsExt;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository("platformSmsStatisticsExtDAO")
public class PlatformSmsStatisticsExtDAOImpl extends
	MybatisBaseBusinessExtDao<PlatformSmsStatisticsExt, PlatformSmsStatisticsExample, PlatformSmsStatistics> implements IPlatformSmsStatisticsExtDAO {

	@Override
	public List<Map<String, Object>> getPlatformStatisticListForExport(PlatformSmsStatisticsExt platformSmsStatisticsExt) {
		return getSqlBusinessSessionTemplate().selectList("getPlatformStatisticListForExport", platformSmsStatisticsExt);
	}
}
