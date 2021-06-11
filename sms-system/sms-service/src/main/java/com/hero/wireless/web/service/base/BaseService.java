package com.hero.wireless.web.service.base;

import com.drondea.wireless.config.Constant;
import com.drondea.wireless.util.ExcelUtil;
import com.drondea.wireless.util.ServiceException;
import com.hero.wireless.web.config.DatabaseCache;
import com.hero.wireless.web.dao.business.IExportFileDAO;
import com.hero.wireless.web.dao.ext.IExtDAO;
import com.hero.wireless.web.entity.base.BaseEntity;
import com.hero.wireless.web.entity.base.BaseExample;
import com.hero.wireless.web.entity.base.ShardingPagination;
import com.hero.wireless.web.entity.business.ExportFile;
import com.hero.wireless.web.entity.business.ext.ExportFileExt;
import com.hero.wireless.web.entity.ext.SqlStatisticsEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class BaseService {
//    @Resource
//	protected StringRedisTemplate stringRedisTemplate;
    @Resource(name = "IExportFileDAO")
    protected IExportFileDAO<ExportFile> exportFileDAO;

    /**
     * 导入导出 new Thread 前调用，校验当前登录用户已开启线程数
     * @param threadType 操作类型 import：导入 export：导出
     * @return redisKey  返回当前操作的redisKey
     */
    public String newThreadBefore(String threadType) throws ServiceException{
        if (StringUtils.isEmpty(threadType)) {
			return null;
		}
        int threadTotal = 0;
        int THREAD_TOTAL_MAX = DatabaseCache.getIntValueBySortCodeAndCode("sys_performance_setup",
                "new_thread_total_max", 3);
        String redisKey = getThreadRedisKey(threadType);
//        synchronized (redisKey.intern()){
//            String value = stringRedisTemplate.opsForValue().get(redisKey);
//            if (StringUtils.isNotEmpty(value)) {
//                threadTotal = Integer.valueOf(value);
//            }
//            if(THREAD_TOTAL_MAX <= threadTotal){
//                String msg = Constant.THREAD_TOTAL_IMPORT.equals(threadType)?"导入":"导出";
//                throw new ServiceException(msg+"线程最大开启"+THREAD_TOTAL_MAX+"个，请稍后重试");
//            }
//            stringRedisTemplate.opsForValue().set(redisKey, ++threadTotal+"", 30, TimeUnit.MINUTES);
//        }
        return redisKey;
    }

    /**
     * 导入导出 new Thread 后调用，维护redis中线程数
     * @param redisKey
     */
    public void newThreadAfter(String redisKey){
        if(StringUtils.isEmpty(redisKey)) {
			return;
		}
//        synchronized (redisKey.intern()){
//            String value = stringRedisTemplate.opsForValue().get(redisKey);
//            if (StringUtils.isNotEmpty(value)) {
//                int threadTotal = Integer.valueOf(value);
//                stringRedisTemplate.opsForValue().set(redisKey, --threadTotal+"", 30, TimeUnit.MINUTES);
//            }
//        }
    }

    /**
     *  RedisKey
     */
    private String getThreadRedisKey(final String threadType) {
        String sessionId = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestedSessionId();
        return Constant.THREAD_TOTAL+threadType+"_"+sessionId;
    }

	/**
	 * 获取发送记录汇总数据实例
	 * 
	 * @param reusltList
	 * @return
	 */
	public SqlStatisticsEntity getSubmitSqlStatisticsEntity(
			List<SqlStatisticsEntity> reusltList) {
		SqlStatisticsEntity statisticsEntity = new SqlStatisticsEntity();
		for (SqlStatisticsEntity entity : reusltList) {
			statisticsEntity.setCount(statisticsEntity.getCount() + entity.getCount());
			statisticsEntity.setSubmit_Success_Total(statisticsEntity.getSubmit_Success_Total()
							+ entity.getSubmit_Success_Total());
			statisticsEntity.setSort_Faild_Total(statisticsEntity
					.getSort_Faild_Total()
					+ entity.getSort_Faild_Total());
			statisticsEntity.setSubmit_Faild_Total(statisticsEntity
					.getSubmit_Faild_Total()
					+ entity.getSubmit_Faild_Total());
			statisticsEntity.setProfits_Total(statisticsEntity
					.getProfits_Total() + entity.getProfits_Total());
			statisticsEntity.setChannel_Unit_Price_Total(statisticsEntity
							.getChannel_Unit_Price_Total().add(entity
							.getChannel_Unit_Price_Total()));
			statisticsEntity.setChannel_Taxes_Total(statisticsEntity.getChannel_Taxes_Total().add(entity
					.getChannel_Taxes_Total()));
			statisticsEntity.setEnterprise_User_Taxes_Total(statisticsEntity
							.getEnterprise_User_Taxes_Total().add(entity
							.getEnterprise_User_Taxes_Total()));
		}
		return statisticsEntity;
	}
	
	/**
	 * 获取回执汇总数据实例
	 * 
	 * @param resultList
	 * @return
	 */
	public SqlStatisticsEntity getReportSqlStatisticsEntity(
			List<SqlStatisticsEntity> resultList) {
		SqlStatisticsEntity statisticsEntity = new SqlStatisticsEntity();
		for (SqlStatisticsEntity entity : resultList) {
			statisticsEntity.setCreate_Date(entity.getCreate_Date());
			statisticsEntity.setCount(statisticsEntity.getCount() == null ? 0 + entity.getCount()
					: statisticsEntity.getCount() + entity.getCount());
			statisticsEntity.setSubmit_Success_Total(statisticsEntity.getSubmit_Success_Total()
							+ entity.getSubmit_Success_Total());
			statisticsEntity.setSort_Faild_Total(statisticsEntity
					.getSort_Faild_Total()
					+ entity.getSort_Faild_Total());
			statisticsEntity.setSubmit_Faild_Total(statisticsEntity
					.getSubmit_Faild_Total()
					+ entity.getSubmit_Faild_Total());
			statisticsEntity.setSend_Success_Total(statisticsEntity
					.getSend_Success_Total()
					+ entity.getSend_Success_Total());
			statisticsEntity.setSend_Faild_Total(statisticsEntity
					.getSend_Faild_Total()
					+ entity.getSend_Faild_Total());
		}
		return statisticsEntity;
	}

    // 导出并插入
    protected void exportAndInsert(ExportFileExt exportFile, String path, String fileName, Object[][] titles,
								   List<Map<String, Object>> beanList) {
        fileName = ExcelUtil.exportExcel(path, fileName, titles, beanList,exportFile.isBlur());
        exportFile.setFIle_Name(fileName);
        exportFile.setFile_Url(path + File.separator + fileName);
        exportFile.setCreate_Date(new Date());
        exportFile.setId(null);
        exportFileDAO.insert(exportFile);
    }
	protected <T extends BaseEntity> List<T> queryShardingPageList(IExtDAO idao, List<? extends BaseExample> examples, T condition) {
		int pageSize = condition.getPagination().getPageSize();
		int pageBegin = condition.getPagination().getFirstResult();
		int pageEnd = pageBegin + pageSize;

		int totalCount = 0;

		//根据limit和count判断执行哪个条件获取要查询的数据
		List<BaseExample> executeExamples = new ArrayList<>();

		//遍历examples进行count，并记录每个count值
		for (int i = examples.size() - 1; i >= 0; i--) {
			BaseExample example = examples.get(i);
			int count = idao.countExtByExample(example);
			if (count == 0) {
				continue;
			}

			int preTotal = totalCount;
			totalCount = totalCount + count;

			if (pageBegin >= pageEnd || totalCount <= pageBegin) {
				continue;
			}

			//开始位置
			int newPageStart = pageBegin - preTotal;

			if (totalCount >= pageEnd) {
				pageSize = pageEnd - pageBegin;
				pageBegin = pageEnd;
			} else {
				pageSize = totalCount - pageBegin;
				pageBegin = totalCount;
			}

			ShardingPagination pagination = new ShardingPagination(newPageStart, pageSize);
			example.setPagination(pagination);
			executeExamples.add(example);
		}

		//总数
		condition.getPagination().setTotalCount(totalCount);

		List<T> queryResult = new ArrayList<>();
		//没有要执行的
		if (executeExamples.size() == 0) {
			return queryResult;
		}

		//执行每个查询，拼接结果
		executeExamples.stream().forEach(example -> queryResult.addAll(idao.selectExtByExample(example)));

		return queryResult;
	}
}
