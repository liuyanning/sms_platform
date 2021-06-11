package com.hero.wireless.web.service;

import com.drondea.wireless.config.Constant;
import com.drondea.wireless.util.DateTime;
import com.drondea.wireless.util.SecretUtil;
import com.drondea.wireless.util.ServiceException;
import com.hero.wireless.json.JsonUtil;
import com.hero.wireless.web.config.DatabaseCache;
import com.hero.wireless.web.dao.business.IExportFileDAO;
import com.hero.wireless.web.dao.business.IPlatformDAO;
import com.hero.wireless.web.dao.business.IPlatformSmsStatisticsDAO;
import com.hero.wireless.web.dao.business.ISmsRealTimeStatisticsDAO;
import com.hero.wireless.web.dao.business.ext.IPlatformSmsStatisticsExtDAO;
import com.hero.wireless.web.dao.business.ext.ISmsRealTimeStatisticsExtDAO;
import com.hero.wireless.web.dao.business.ext.ISmsStatisticsExtDAO;
import com.hero.wireless.web.dao.send.ext.IReportExtDAO;
import com.hero.wireless.web.dao.send.ext.ISubmitExtDAO;
import com.hero.wireless.web.entity.base.Pagination;
import com.hero.wireless.web.entity.business.*;
import com.hero.wireless.web.entity.business.ext.ExportFileExt;
import com.hero.wireless.web.entity.business.ext.PlatformSmsStatisticsExt;
import com.hero.wireless.web.entity.business.ext.SmsRealTimeStatisticsExt;
import com.hero.wireless.web.entity.business.ext.SmsStatisticsExt;
import com.hero.wireless.web.entity.ext.SqlStatisticsEntity;
import com.hero.wireless.web.entity.send.ext.ReportExt;
import com.hero.wireless.web.entity.send.ext.SubmitExt;
import com.hero.wireless.web.service.base.BaseService;
import com.hero.wireless.web.util.CodeUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service("statisticsManage")
public class StatisticsManageImpl extends BaseService implements IStatisticsManage {

	@Resource(name = "submitExtDAO")
	private ISubmitExtDAO submitExtDAO;

	@Resource(name = "smsStatisticsExtDAO")
	private ISmsStatisticsExtDAO smsStatisticsExtDAO;

	@Resource(name = "IExportFileDAO")
	private IExportFileDAO<ExportFile> exportFileDAO;

	@Resource(name = "reportExtDAO")
	protected IReportExtDAO reportExtDAO;

	@Resource(name = "ISmsRealTimeStatisticsDAO")
	protected ISmsRealTimeStatisticsDAO smsRealTimeStatisticsDAO;
	@Resource(name = "IPlatformSmsStatisticsDAO")
	protected IPlatformSmsStatisticsDAO platformSmsStatisticsDAO;
	@Resource(name = "platformSmsStatisticsExtDAO")
	protected IPlatformSmsStatisticsExtDAO platformSmsStatisticsExtDAO;
	@Resource(name = "ISmsRealTimeStatisticsExtDAO")
	protected ISmsRealTimeStatisticsExtDAO smsRealTimeStatisticsExtDAO;
	@Resource(name = "IPlatformDAO")
	private IPlatformDAO platformDAO;

	@Override
	public List<SmsStatisticsExt> querySmsSendFailedByExt(SmsStatisticsExt smsStatisticsExt) {
		return this.smsStatisticsExtDAO.querySmsSendFailedByExt(smsStatisticsExt);
	}

	@Override
	public List<Map<String, Object>> getSmsStatisticListForExport(SmsStatisticsExt smsStatisticsExt) {
		return this.smsStatisticsExtDAO.getSmsStatisticListForExport(smsStatisticsExt);
	}

	@Override
	public List<Map<String, Object>> getPlatformStatisticListForExport(PlatformSmsStatisticsExt platformSmsStatisticsExt) {
		return this.platformSmsStatisticsExtDAO.getPlatformStatisticListForExport(platformSmsStatisticsExt);
	}


	@Override
	public void exportSmsStatistic(final String baseExportBasePath, final SmsStatisticsExt bean,
								   final ExportFileExt exportFile, final String exportType) {
		String redisKey = newThreadBefore(Constant.THREAD_TOTAL_EXPORT); // 校验
		new Thread() {
			public void run() {
				int pageSize = DatabaseCache.getIntValueBySortCodeAndCode("sys_performance_setup", "export_file_size",5000);
				Pagination firstPage = new Pagination(1, pageSize);
				List<Map<String, Object>> beanList = null;
				exportFile.setBatch_Id(CodeUtil.buildMsgNo());
				while (true) {
					bean.setPagination(firstPage);
					beanList = getSmsStatisticListForExport(bean);
					if (beanList == null || beanList.isEmpty()) {
						break;
					}
					exportSmsStatisticExcel(baseExportBasePath, beanList, exportFile, exportType,bean);
					if (firstPage.getPageIndex() == firstPage.getPageCount()) {
						break;
					}
					firstPage = new Pagination(firstPage.getPageIndex() + 1, pageSize);
				}
				newThreadAfter(redisKey);
			}
		}.start();
	}
	@Override
	public void exportPlatformStatistic(final String baseExportBasePath,final PlatformSmsStatisticsExt bean,
										final ExportFileExt exportFile,final String exportType){
		String redisKey = newThreadBefore(Constant.THREAD_TOTAL_EXPORT); // 校验
		new Thread() {
			public void run() {
				int pageSize = DatabaseCache.getIntValueBySortCodeAndCode("sys_performance_setup", "export_file_size",5000);
				Pagination firstPage = new Pagination(1, pageSize);
				List<Map<String, Object>> beanList = null;
				exportFile.setBatch_Id(CodeUtil.buildMsgNo());
				while (true) {
					bean.setPagination(firstPage);
					beanList = getPlatformStatisticListForExport(bean);
					if (beanList == null || beanList.isEmpty()) {
						break;
					}
					exportPlatformSmsStatisticsListExcel(baseExportBasePath, beanList, exportFile);
					if (firstPage.getPageIndex() == firstPage.getPageCount()) {
						break;
					}
					firstPage = new Pagination(firstPage.getPageIndex() + 1, pageSize);
				}
				newThreadAfter(redisKey);
			}
		}.start();
	}
	@Override
	public Map<String, Object> countSmsStatisticExtListByExt(SmsStatisticsExt smsStatisticsExt) {
		return this.smsStatisticsExtDAO.countSmsStatisticExtListByExt(smsStatisticsExt);
	}

	@Override
	public List<SmsStatisticsExt> getSmsStatisticExtListByExtPage(SmsStatisticsExt smsStatisticsExt) {
		List<SmsStatisticsExt> resultList = this.smsStatisticsExtDAO.getSmsStatisticExtListByExtPage(smsStatisticsExt);
		resultList.removeAll(Collections.singleton(null));
		return resultList;
	}

	// 导出通道报表
	@Override
	public void exportChannelStatisticList(final String baseExportBasePath, final SmsStatisticsExt bean,
										   final ExportFileExt exportFile) {
		exportSmsStatistic(baseExportBasePath, bean, exportFile, "ChannelStatisticList");
	}

	// 导出企业报表
	@Override
	public void exportEnterpriseStatisticList(final String baseExportBasePath, final SmsStatisticsExt bean,
											  final ExportFileExt exportFile) {
		exportSmsStatistic(baseExportBasePath, bean, exportFile, "EnterpriseStatisticList");
	}


	// 导出商务报表
	@Override
	public void exportBusinessStatisticList(final String baseExportBasePath, final SmsStatisticsExt bean,
											final ExportFileExt exportFile) {
		exportSmsStatistic(baseExportBasePath, bean, exportFile, "BusinessStatisticList");
	}

	// 导出短信报表(客户平台)
	@Override
	public void exportSmsStatisticByEnterprise(final String baseExportBasePath, final SmsStatisticsExt bean,
											   final ExportFileExt exportFile) {
		exportSmsStatistic(baseExportBasePath, bean, exportFile, "SmsStatisticByEnterprise");
	}

	@Override
	public void exportSignatureStatisticList(String baseExportBasePath, SmsStatisticsExt bean, ExportFileExt exportFile) {
		exportSmsStatistic(baseExportBasePath, bean, exportFile, "SignatureStatisticList");
	}

	/**
	 * ***注意这里startDate和endDate不能有跨天的数据，实时统计是按小时统计所以不会有跨天数据
	 * @param startDate
	 * @param endDate
	 */
	@Override
	public void insertSmsRealTimeStatisticsData(Date startDate, Date endDate) {
		SubmitExt submitExt = new SubmitExt();
		submitExt.setMinSubmitDate(startDate);
		submitExt.setMaxSubmitDate(endDate);
		List<SmsRealTimeStatisticsExt> submitData = this.submitExtDAO.querySmsRealTimeStatisticsSubmitData(submitExt);
		List<SmsRealTimeStatisticsExt> reportData = getLatestReportData(startDate, endDate);
		for (SmsRealTimeStatisticsExt submitStatistics: submitData){
			//查询的时间没有跨天所以提交时间是开始时间
			submitStatistics.setSubmit_Date(startDate);
			submitStatistics.setCreate_Date(new Date());
			for (SmsRealTimeStatisticsExt reportStatistics: reportData){
				if (submitStatistics.getJudgeStrExt().equals(reportStatistics.getJudgeStrExt())){
					submitStatistics.setSend_Success_Total(reportStatistics.getSend_Success_Total());
					submitStatistics.setSend_Faild_Total(reportStatistics.getSend_Faild_Total());
					reportStatistics.setFlag(true);
					break;
				}
			}
		}
		//某段时间内无发送有回执的情况
		for (SmsRealTimeStatisticsExt reportStatisticsBean: reportData){
			if (!reportStatisticsBean.getFlag()){
				reportStatisticsBean.setCreate_Date(new Date());
				submitData.add(reportStatisticsBean);
			}
		}
		if (submitData.size()>0){
			this.smsRealTimeStatisticsDAO.insertList(submitData);
		}
	}

	/**
	 * 从近4天的report表中统计回执情况
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	private List<SmsRealTimeStatisticsExt> getLatestReportData(Date startDate, Date endDate) {
		Date minDate = DateTime.getTheDayBeforeMinDate(startDate, -3);
		Date maxDate = DateTime.getTheDayBeforeMaxDate(startDate, 0);
		List<DateTime.DateInterVal> dayIntervalDates = DateTime.getDayIntervalDate(minDate, maxDate);
		List<SmsRealTimeStatisticsExt> allReportData = new ArrayList<>();
		for (DateTime.DateInterVal dateInterVal : dayIntervalDates) {
			Date begin = dateInterVal.getBegin();
			Date end = dateInterVal.getEnd();
			ReportExt reportExt = new ReportExt();
			//用submitDate来限定表
			reportExt.setMinSubmitDate(begin);
			reportExt.setMaxSubmitDate(end);
			reportExt.setMinCreateDate(startDate);
			reportExt.setMaxCreateDate(endDate);
			List<SmsRealTimeStatisticsExt> reportData = this.reportExtDAO.querySmsRealTimeStatisticsReportData(reportExt);
			//设置发送日期
			reportData.stream().forEach(statisticsExt -> {
				statisticsExt.setSubmit_Date(begin);
				allReportData.add(statisticsExt);
			});
		}
		return allReportData;
	}

	@Override
	public int insertPlatformSmsStatisticsData(Map<String, String> map) throws IOException {
		PlatformSmsStatistics platformSmsStatistics = JsonUtil.NON_NULL.readValue(map.get("platformSmsStatistics"), PlatformSmsStatistics.class);
		String sign = map.get("sign");
		//检查参数是否合法
		checkParam(platformSmsStatistics,sign);
		PlatformSmsStatisticsExample example = new PlatformSmsStatisticsExample();
		PlatformSmsStatisticsExample.Criteria cri = example.createCriteria();
		cri.andPlatform_NoEqualTo(platformSmsStatistics.getPlatform_No());
		cri.andStatistics_Type_CodeEqualTo(platformSmsStatistics.getStatistics_Type_Code());
		cri.andStatistics_DateEqualTo(platformSmsStatistics.getStatistics_Date());

		List<PlatformSmsStatistics> pssList = platformSmsStatisticsDAO.selectByExample(example);
		if(pssList.size()>0){
			throw new ServiceException("-1", "重复插入");
		}
		return this.platformSmsStatisticsDAO.insert(platformSmsStatistics);
	}

	private void checkParam(PlatformSmsStatistics platformSmsStatistics, String sign) {
		if (StringUtils.isEmpty(platformSmsStatistics.getPlatform_No())) {
			throw new ServiceException("-1", "平台编码错误");
		}
		if (platformSmsStatistics.getStatistics_Date() == null) {
			throw new ServiceException("-1", "统计日期错误");
		}
		PlatformExample example = new PlatformExample();
		PlatformExample.Criteria cri = example.createCriteria();
		cri.andPlatform_NoEqualTo(platformSmsStatistics.getPlatform_No());
		List<Platform> platformList = platformDAO.selectByExample(example);
		if (platformList.size() == 0) {
			throw new ServiceException("-1", "平台编码不存在" + platformSmsStatistics.getPlatform_No());
		}
		Platform platform = platformList.get(0);
		if (!platform.getStatistics_Status()) {
			throw new ServiceException("-1", "平台不纳入统计" + platformSmsStatistics.getPlatform_No());
		}
		String createSign = SecretUtil.MD5(platformSmsStatistics.getPlatform_No() + platformSmsStatistics.getStatistics_Date() + platform.getSign_Key()).toLowerCase();
		if (!sign.equalsIgnoreCase(createSign)) {
			throw new ServiceException("-1", "签名错误" + platformSmsStatistics.getPlatform_No());
		}
		platformSmsStatistics.setPlatform_Name(platform.getPlatform_Name());

	}
	@Override
	public int countPlatformSmsStatisticsList(PlatformSmsStatisticsExt platformSmsStatisticsExt) throws ParseException {
		PlatformSmsStatisticsExample example = new PlatformSmsStatisticsExample();
		PlatformSmsStatisticsExample.Criteria cri = example.createCriteria();
		if(StringUtils.isNotBlank(platformSmsStatisticsExt.getPlatform_No())){
			cri.andPlatform_NoEqualTo(platformSmsStatisticsExt.getPlatform_No());
		}
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		if(StringUtils.isNotBlank(platformSmsStatisticsExt.getMin_Statistics_Date_Str())){
			Date min_Statistics_Date = format.parse(platformSmsStatisticsExt.getMin_Statistics_Date_Str());
			cri.andStatistics_DateGreaterThanOrEqualTo(min_Statistics_Date);
		}
		if(StringUtils.isNotBlank(platformSmsStatisticsExt.getMax_Statistics_Date_Str())){
			Date max_Statistics_Date = format.parse(platformSmsStatisticsExt.getMax_Statistics_Date_Str());
			cri.andStatistics_DateLessThanOrEqualTo(max_Statistics_Date);
		}
		return platformSmsStatisticsDAO.countByExample(example);
	}
	@Override
	public List<PlatformSmsStatistics> queryPlatformSmsStatisticsList(PlatformSmsStatisticsExt platformSmsStatisticsExt) throws ParseException {
		PlatformSmsStatisticsExample example = new PlatformSmsStatisticsExample();
		PlatformSmsStatisticsExample.Criteria cri = example.createCriteria();
		example.setOrderByClause(" id desc");
		example.setPagination(platformSmsStatisticsExt.getPagination());
		if(StringUtils.isNotBlank(platformSmsStatisticsExt.getPlatform_No())){
			cri.andPlatform_NoEqualTo(platformSmsStatisticsExt.getPlatform_No());
		}
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		if(StringUtils.isNotBlank(platformSmsStatisticsExt.getMin_Statistics_Date_Str())){
			Date min_Statistics_Date = format.parse(platformSmsStatisticsExt.getMin_Statistics_Date_Str());
			cri.andStatistics_DateGreaterThanOrEqualTo(min_Statistics_Date);
		}
		if(StringUtils.isNotBlank(platformSmsStatisticsExt.getMax_Statistics_Date_Str())){
			Date max_Statistics_Date = format.parse(platformSmsStatisticsExt.getMax_Statistics_Date_Str());
			cri.andStatistics_DateLessThanOrEqualTo(max_Statistics_Date);
		}
		return platformSmsStatisticsDAO.selectByExamplePage(example);
	}

	@Override
	public List<SmsRealTimeStatisticsExt> querySmsRealTimeStatisticsGroupDataList(SmsRealTimeStatisticsExt statisticsExt) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if (StringUtils.isNotEmpty(statisticsExt.getMin_Submit_Date_Str())){
			statisticsExt.setMin_Submit_Date(sdf.parse(statisticsExt.getMin_Submit_Date_Str()));
		}
		if (StringUtils.isNotEmpty(statisticsExt.getMax_Submit_Date_Str())){
			statisticsExt.setMax_Submit_Date(sdf.parse(statisticsExt.getMax_Submit_Date_Str()));
		}
		return this.smsRealTimeStatisticsExtDAO.querySmsRealTimeStatisticsGroupDataList(statisticsExt);
	}

	@Override
	public Map<String,Object> countSmsRealTimeStatisticsGroupDataList(SmsRealTimeStatisticsExt statisticsExt) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if (StringUtils.isNotEmpty(statisticsExt.getMin_Submit_Date_Str())){
			statisticsExt.setMin_Submit_Date(sdf.parse(statisticsExt.getMin_Submit_Date_Str()));
		}
		if (StringUtils.isNotEmpty(statisticsExt.getMax_Submit_Date_Str())){
			statisticsExt.setMax_Submit_Date(sdf.parse(statisticsExt.getMax_Submit_Date_Str()));
		}
		return this.smsRealTimeStatisticsExtDAO.countSmsRealTimeStatisticsGroupDataList(statisticsExt);
	}

	// 数据过滤、排序
	private List<SqlStatisticsEntity> sortList(List<SqlStatisticsEntity> resultList) {
		if (ObjectUtils.isEmpty(resultList)) {
			return resultList;
		}
		Date date = DateTime.add(DateTime.getDate(DateTime.getCurrentDayMinDate()), Calendar.DATE, -3);
		resultList = resultList.stream() // 数据过滤、排序
				.filter(item -> item != null && StringUtils.isNotEmpty(item.getCreate_Date()) && DateTime
						.getDate(item.getCreate_Date() + " 23:59:59").after(date))
				.sorted(Comparator.comparing(SqlStatisticsEntity::getCreate_Date).reversed()).collect(Collectors.toList());
		return resultList;
	}

	/**
	 * 获取当前时间，同时分钟整10数，最后格式：YYYYMMDDHHm000 例如：20200303134000
	 *
	 * @return 时间字符串YYYYMMDDHHm000
	 */
	private String getNowDateStr() {
		return getYYYYMMDDHHm000(0);
	}

	/**
	 * 获取上一个10分钟，同时分钟整10数，最后格式：YYYYMMDDHHm000 例如：20200303134000
	 *
	 * @return 时间字符串YYYYMMDDHHm000
	 */
	private String getLastTenMinuteStr() {
		return getYYYYMMDDHHm000(-10);
	}

	/**
	 * 获取上一个minute分钟，同时分钟整minute数，最后格式：YYYYMMDDHHm000 例如：20200303134000
	 *
	 * @return 时间字符串YYYYMMDDHHm000
	 */
	private String getYYYYMMDDHHm000(int minute) {
		Calendar now = Calendar.getInstance();
		now.add(Calendar.MINUTE, minute);
		int min = now.get(Calendar.MINUTE) / 10;
		return DateTime.getString(new Date(), DateTime.Y_M_D_H) + min + "000";
	}

	@SuppressWarnings("all")
	private void exportSmsStatisticExcel(String path, List<Map<String, Object>> beanList, ExportFileExt exportFile,
										 String exportType,SmsStatisticsExt smsStatisticsExt) {
		Object[][] titles = null;
		String fileName = null;
		switch (exportType) {
			case "SmsStatisticList": // 短信报表
				fileName = "三天之前报表";
                String groupString = smsStatisticsExt.getGroup_Str();
                String[] groupStr = groupString.split(",");
                if (groupString.contains("Channel_No")){
                    titles = new Object[groupStr.length+12][];
                    titles[titles.length-1] = new Object[]{"channel_Source_Name", "通道来源", 4000};
                }else{
                    titles = new Object[groupStr.length+11][];
                }
				for(int i = 0; i < groupStr.length ; i++){
					if ("Time_Cycle".equals(groupStr[i])){
						titles[i] = new Object[]{"Time_Cycle", "日期", 7000};
					}if ("Business_User_Id".equals(groupStr[i])){
						titles[i] = new Object[]{"Business_Name", "商务", 7000};
					}if ("Enterprise_No".equals(groupStr[i])){
						titles[i] = new Object[]{"Enterprise_Name", "企业", 7000};
					}if ("Enterprise_User_Id".equals(groupStr[i])){
						titles[i] = new Object[]{"Enterprise_User_Name", "提交人", 4000};
					}if ("Channel_No".equals(groupStr[i])){
						titles[i] = new Object[]{"Channel_Name", "通道", 4000};
					}if ("Message_Type_Code".equals(groupStr[i])){
						titles[i] = new Object[]{"Message_Type_Code", "消息类型", 4000};
					}if ("Province_Code".equals(groupStr[i])){
						titles[i] = new Object[]{"Province_Name", "区域", 4000};
					}if ("Country_Number".equals(groupStr[i])){
						titles[i] = new Object[]{"Country_Name", "国家", 4000};
					}if ("Operator".equals(groupStr[i])){
						titles[i] = new Object[]{"Operator", "运营商", 4000};
					}if ("Signature".equals(groupStr[i])){
						titles[i] = new Object[]{"Signature", "签名", 7000};
					}
				}
				titles[groupStr.length] = new Object[]{"Submit_Success_Total", "提交成功条数", 4000};
				titles[groupStr.length+1] = new Object[]{"Submit_Faild_Total", "提交失败条数", 4000};
				titles[groupStr.length+2] = new Object[]{"Send_Success_Total", "发送成功条数", 4000};
				titles[groupStr.length+3] = new Object[]{"Send_Faild_Total", "发送失败条数", 4000};
				titles[groupStr.length+4] = new Object[]{"Send_Unknown_Total", "发送未知条数", 4000};
				titles[groupStr.length+5] = new Object[]{"Success_Rate", "成功率", 4000};
				titles[groupStr.length+6] = new Object[]{"Failure_Rate", "失败率", 4000};
				titles[groupStr.length+7] = new Object[]{"Channel_Taxes", "发票抵扣", 4000};
				titles[groupStr.length+8] = new Object[]{"Enterprise_User_Taxes", "发票成本", 4000};
				titles[groupStr.length+9] = new Object[]{"Unit_Cost", "成本", 4000};
				titles[groupStr.length+10] = new Object[]{"Profits", "利润", 4000};
				break;
			case "SmsStatisticByEnterprise": // 短信报表(客户平台，展示字段不同)
				fileName = "短信报表";
				titles = new Object[][]{{"Submit_Total", "提交条数", 2000},
						{"Submit_Success_Total", "提交成功条数", 2000}, {"Submit_Faild_Total", "提交失败条数", 2000},
						{"Send_Success_Total", "发送成功条数", 2000}, {"Send_Faild_Total", "发送失败条数", 2000},
						{"Send_Unknown_Total", "发送未知条数", 2000}, {"Time_Cycle", "时间", 6000}};
				break;
			case "SmsStatisticByAgent": // 短信报表(代理平台，展示字段不同)
				fileName = "短信报表";
				titles = new Object[][]{{"Submit_Total", "提交条数", 2000},
						{"Submit_Success_Total", "提交成功条数", 2000}, {"Submit_Faild_Total", "提交失败条数", 2000},
						{"Send_Success_Total", "发送成功条数", 2000}, {"Send_Faild_Total", "发送失败条数", 2000},
						{"Send_Unknown_Total", "发送未知条数", 2000}, {"Agent_Unit_Cost", "成本", 4000},
						{"Agent_Profits", "利润", 4000}, {"Time_Cycle", "时间", 6000}};
				break;
		}
		exportAndInsert(exportFile, path, fileName, titles, beanList); // 导出数据
	}

	@Override
	public void exportsmsDailySendedStatisticList(String value, SmsRealTimeStatisticsExt statisticsExt,
												  ExportFileExt adminDefaultExportFile, String platformFlagAdmin) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if (StringUtils.isNotEmpty(statisticsExt.getMin_Submit_Date_Str())){
			statisticsExt.setMin_Submit_Date(sdf.parse(statisticsExt.getMin_Submit_Date_Str()));
		}
		if (StringUtils.isNotEmpty(statisticsExt.getMax_Submit_Date_Str())){
			statisticsExt.setMax_Submit_Date(sdf.parse(statisticsExt.getMax_Submit_Date_Str()));
		}
		new Thread(() -> {
			int pageSize = DatabaseCache.getIntValueBySortCodeAndCode("sys_performance_setup", "export_file_size",5000);
			Pagination firstPage = new Pagination(1, pageSize);
			List<Map<String, Object>> dataList;
			adminDefaultExportFile.setBatch_Id(CodeUtil.buildMsgNo());
			while (true) {
				statisticsExt.setPagination(firstPage);
				dataList = querySmsDailySendedStatisticListForExportPage(statisticsExt);
				if (dataList == null || dataList.isEmpty()) {
					break;
				}
				exportSmsDailySendedStatisticListExcel(value, dataList, adminDefaultExportFile,statisticsExt);
				if (firstPage.getPageIndex() == firstPage.getPageCount()) {
					break;
				}
				firstPage = new Pagination(firstPage.getPageIndex() + 1, pageSize);
			}
		}).start();
	}

	public List<Map<String, Object>> querySmsDailySendedStatisticListForExportPage(SmsRealTimeStatisticsExt condition) {
		return this.smsRealTimeStatisticsExtDAO.querySmsDailySendedStatisticListForExportPage(condition);
	}

	protected void exportSmsDailySendedStatisticListExcel(String path, List<Map<String, Object>> dataList,
														  ExportFileExt exportFile, SmsRealTimeStatisticsExt statisticsExt) {
	    String groupStr = statisticsExt.getGroupStr();
        String[] groupArray = groupStr.split(",");
        Object[][] titles;//标题
        if (groupStr.contains("Channel_No")){
            titles = new Object[groupArray.length+7][];
            titles[titles.length-1] = new Object[]{"channel_Source_Name", "通道来源", 4000};
        }else{
            titles = new Object[groupArray.length+6][];
        }
		for(int i= 0; i< groupArray.length; i++){
			if ("Submit_Date".equals(groupArray[i])){
				titles[i] = new Object[]{"submit_Date", "日期", 7000};
			}if ("Business_User_Id".equals(groupArray[i])){
				titles[i] = new Object[]{"business_User_Name", "商务", 7000};
			}if ("Enterprise_No".equals(groupArray[i])){
				titles[i] = new Object[]{"enterprise_Name", "企业", 7000};
			}if ("Enterprise_User_Id".equals(groupArray[i])){
				titles[i] = new Object[]{"enterprise_User_Name", "提交人", 2000};
			}if ("Channel_No".equals(groupArray[i])){
				titles[i] = new Object[]{"channel_Name", "通道", 2000};
			}if ("Message_Type_Code".equals(groupArray[i])){
				titles[i] = new Object[]{"message_Type_Name", "消息类型", 2000};
			}if ("Country_Number".equals(groupArray[i])){
				titles[i] = new Object[]{"country_Name", "国家", 2000};
			}if ("Operator".equals(groupArray[i])){
				titles[i] = new Object[]{"operator", "运营商", 2000};
			}if ("Signature".equals(groupArray[i])){
				titles[i] = new Object[]{"signature", "签名", 7000};
			}
		}
		titles[groupArray.length] = new Object[]{"submit_Total", "提交条数", 4000};
		titles[groupArray.length+1] = new Object[]{"submit_Success_Total", "提交成功", 4000};
		titles[groupArray.length+2] = new Object[]{"submit_Faild_Total", "提交失败", 4000};
		titles[groupArray.length+3] = new Object[]{"sort_Faild_Total", "分拣失败", 3000};
		titles[groupArray.length+4] = new Object[]{"send_Success_Total", "发送成功", 4000};
		titles[groupArray.length+5] = new Object[]{"send_Faild_Total", "发送失败", 4000};
		exportAndInsert(exportFile, path, "三天之内报表", titles, dataList);// 导出数据
	}

	protected void exportPlatformSmsStatisticsListExcel(String path, List<Map<String, Object>> dataList,
														  ExportFileExt exportFile) {
		Object[][] titles = new Object[][]{{"Platform_Name", "平台名称", 6000},
				{"Submit_Total", "提交条数", 4000},{"Sort_Faild_Total", "分拣失败条数", 4000},
				{"Submit_Success_Total", "提交成功条数", 4000}, {"Submit_Faild_Total", "提交失败条数", 4000},
				{"Send_Success_Total", "发送成功条数", 4000}, {"Send_Faild_Total", "发送失败条数", 4000},
				{"Send_Unknown_Total", "发送未知条数", 4000}, {"Statistics_Date", "统计时间", 5000}};

		exportAndInsert(exportFile, path, "各平台短信统计报表", titles, dataList);// 导出数据
	}

	@Override
	public List<SmsRealTimeStatisticsExt> queryUnknownStatisticList(SmsRealTimeStatisticsExt statisticsExt) {
		Date minDate = DateTime.getDate(statisticsExt.getMin_Submit_Date_Str());
		Date maxDate = DateTime.getDate(statisticsExt.getMax_Submit_Date_Str());
		statisticsExt.setMin_Submit_Date(minDate);
		statisticsExt.setMax_Submit_Date(maxDate);
		if (!DateUtils.isSameDay(minDate, maxDate)) {
			throw new ServiceException("不可以跨天查询！");
		}
		List<SmsRealTimeStatisticsExt> submitData = submitExtDAO.queryUnknownStatisticListSubmitData(statisticsExt);
		List<SmsRealTimeStatisticsExt> reportData = reportExtDAO.queryUnknownStatisticListReportData(statisticsExt);
		for (SmsRealTimeStatisticsExt submitStatistics: submitData){
			for (SmsRealTimeStatisticsExt reportStatistics: reportData) {
				if ("Channel_No".equals(statisticsExt.getGroupStr())) {
					if (submitStatistics.getChannel_No().equals(reportStatistics.getChannel_No())) {
						submitStatistics.setSend_Faild_Total(reportStatistics.getSend_Faild_Total());
						submitStatistics.setSend_Success_Total(reportStatistics.getSend_Success_Total());
					}
				} else if ("Enterprise_User_Id".equals(statisticsExt.getGroupStr())) {
					if (submitStatistics.getEnterprise_User_Id().equals(reportStatistics.getEnterprise_User_Id())) {
						submitStatistics.setSend_Faild_Total(reportStatistics.getSend_Faild_Total());
						submitStatistics.setSend_Success_Total(reportStatistics.getSend_Success_Total());
					}
				} else {
					if (submitStatistics.getChannel_No().equals(reportStatistics.getChannel_No())
							&& submitStatistics.getEnterprise_User_Id().equals(reportStatistics.getEnterprise_User_Id())) {
						submitStatistics.setSend_Faild_Total(reportStatistics.getSend_Faild_Total());
						submitStatistics.setSend_Success_Total(reportStatistics.getSend_Success_Total());
					}
				}
			}
		}
		return submitData;
	}

	@Override
	public List<ReportExt> queryReportNativeStatus(SmsStatisticsExt smsStatisticsExt) {
		String minSubmiteDate = smsStatisticsExt.getMin_Statistics_Date_Str() + " 00:00:00";
		String maxSubmiteDate = smsStatisticsExt.getMin_Statistics_Date_Str() + " 23:59:59";
		ReportExt reportExt = new ReportExt();
		if(smsStatisticsExt.getGroup_Str().contains("Enterprise_User_Id")){
			reportExt.setEnterprise_User_Id(Integer.valueOf(smsStatisticsExt.getEnterprise_User_Id_Ext()));
		}
		if(smsStatisticsExt.getGroup_Str().contains("Enterprise_No")){
			reportExt.setEnterprise_No(smsStatisticsExt.getEnterprise_No());
		}
		if(smsStatisticsExt.getGroup_Str().contains("Channel_No")){
			reportExt.setChannel_No(smsStatisticsExt.getChannel_No());
		}
		if(smsStatisticsExt.getGroup_Str().contains("Business_User_Id")){
			reportExt.setBusiness_User_Id(Integer.valueOf(smsStatisticsExt.getBusiness_User_Id_Ext()));
		}
		if(smsStatisticsExt.getGroup_Str().contains("Agent_No")){
			reportExt.setAgent_No(smsStatisticsExt.getAgent_No());
		}
		if(smsStatisticsExt.getGroup_Str().contains("Message_Type_Code")){
			reportExt.setMessage_Type_Code(smsStatisticsExt.getMessage_Type_Code());
		}
		if(smsStatisticsExt.getGroup_Str().contains("Country_Number")){
			reportExt.setCountry_Number(smsStatisticsExt.getCountry_Number());
		}
		if(smsStatisticsExt.getGroup_Str().contains("Operator")){
			reportExt.setOperator(smsStatisticsExt.getOperator());
		}
		if(smsStatisticsExt.getGroup_Str().contains("Province_Code")){
			reportExt.setProvince_Code(smsStatisticsExt.getProvince_Code());
		}
		if(smsStatisticsExt.getGroup_Str().contains("Signature")){
			reportExt.setSignature(smsStatisticsExt.getSignature());
		}

		reportExt.setMinSubmitDate(DateTime.getDate(minSubmiteDate));
		//上个正点时间
		LocalDateTime maxTime = LocalDateTime.now().withMinute(0).withSecond(0).withNano(0);
		if("send".equals(smsStatisticsExt.getDatabaseName())){
			reportExt.setMaxSubmitDate(Date.from(maxTime.atZone(ZoneId.systemDefault()).toInstant()));
		} else {
			reportExt.setMaxSubmitDate(DateTime.getDate(maxSubmiteDate));
		}

		List<ReportExt> list = reportExtDAO.queryReportNativeStatus(reportExt);
		return list;
	}
}
