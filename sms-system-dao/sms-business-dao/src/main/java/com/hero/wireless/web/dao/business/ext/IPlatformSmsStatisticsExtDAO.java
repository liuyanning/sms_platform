package com.hero.wireless.web.dao.business.ext;

import com.hero.wireless.web.dao.business.IPlatformSmsStatisticsDAO;
import com.hero.wireless.web.dao.ext.IExtDAO;
import com.hero.wireless.web.entity.business.PlatformSmsStatisticsExample;
import com.hero.wireless.web.entity.business.ext.PlatformSmsStatisticsExt;

import java.util.List;
import java.util.Map;

public interface IPlatformSmsStatisticsExtDAO extends IPlatformSmsStatisticsDAO<PlatformSmsStatisticsExt>, IExtDAO<PlatformSmsStatisticsExt, PlatformSmsStatisticsExample> {

    List<Map<String, Object>> getPlatformStatisticListForExport(PlatformSmsStatisticsExt platformSmsStatisticsExt);

}
