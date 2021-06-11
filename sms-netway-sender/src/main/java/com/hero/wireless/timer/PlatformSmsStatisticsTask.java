package com.hero.wireless.timer;

import com.drondea.wireless.util.DateTime;
import com.drondea.wireless.util.SecretUtil;
import com.drondea.wireless.util.SuperLogger;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hero.wireless.enums.DateType;
import com.hero.wireless.json.JsonUtil;
import com.hero.wireless.okhttp.CharsetResponseBody;
import com.hero.wireless.okhttp.HttpClient;
import com.hero.wireless.web.config.DatabaseCache;
import com.hero.wireless.web.config.MessagesManger;
import com.hero.wireless.web.entity.business.PlatformSmsStatistics;
import com.hero.wireless.web.entity.business.ext.SmsStatisticsExt;
import com.hero.wireless.web.service.IStatisticsManage;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 各平台短信数据每日统计
 */
@Component
public class PlatformSmsStatisticsTask extends OperatDataBase {

	@Resource(name = "statisticsManage")
	private IStatisticsManage statisticsManage;

	private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	//每天4:05执行
	@Scheduled(cron = "0 5 4 * * ?")
	public void execute() {
		try {
			if(MessagesManger.getSystemMessages("platform_no")=="platform_no"){
				return;
			}
			platformSmsStatisticsData();
		}catch (Exception e){
			e.printStackTrace();
			SuperLogger.error(e);
		}
	}

	private void platformSmsStatisticsData() throws Exception {
		long startTime = System.currentTimeMillis();
		int dayData = -DatabaseCache.getIntValueBySortCodeAndCode("sys_performance_setup", "report_acquisition_interval",
				4);
		String statisticsDate = formatter.format(DateTime.getTheDayBeforeMinDate(dayData));
		SmsStatisticsExt smsStatisticsExt =new SmsStatisticsExt();
		smsStatisticsExt.setMin_Statistics_Date_Str(statisticsDate);
		smsStatisticsExt.setMax_Statistics_Date_Str(statisticsDate);
		List<SmsStatisticsExt> pageList =  this.statisticsManage.getSmsStatisticExtListByExtPage(smsStatisticsExt);

		PlatformSmsStatistics platformSmsStatistics = new PlatformSmsStatistics();
		if(pageList.size() > 0){
			smsStatisticsExt = pageList.get(0);
			platformSmsStatistics.setSubmit_Total(smsStatisticsExt.getSubmit_Total()==null?0:smsStatisticsExt.getSubmit_Total());
			platformSmsStatistics.setSort_Faild_Total(smsStatisticsExt.getSort_Faild_Total()==null?0:smsStatisticsExt.getSort_Faild_Total());
			platformSmsStatistics.setSubmit_Success_Total(smsStatisticsExt.getSubmit_Success_Total()==null?0:smsStatisticsExt.getSubmit_Success_Total());
			platformSmsStatistics.setSubmit_Faild_Total(smsStatisticsExt.getSubmit_Faild_Total()==null?0:smsStatisticsExt.getSubmit_Faild_Total());
			platformSmsStatistics.setSend_Success_Total(smsStatisticsExt.getSend_Success_Total()==null?0:smsStatisticsExt.getSend_Success_Total());
			platformSmsStatistics.setSend_Faild_Total(smsStatisticsExt.getSend_Faild_Total()==null?0:smsStatisticsExt.getSend_Faild_Total());
			int unknownTotal = platformSmsStatistics.getSubmit_Success_Total()
					- platformSmsStatistics.getSend_Success_Total()
					- platformSmsStatistics.getSend_Faild_Total();
			platformSmsStatistics.setSend_Unknown_Total(unknownTotal < 0 ? 0 : unknownTotal);
		}

		platformSmsStatistics.setStatistics_Date(formatter.parse(statisticsDate));
		platformSmsStatistics.setPlatform_No(MessagesManger.getSystemMessages("platform_no"));
		platformSmsStatistics.setStatistics_Type_Code(DateType.DAY.toString());
		platformSmsStatistics.setCreate_Date(new Date());
		ObjectMapper mapper = new ObjectMapper();
		String objToJson = mapper.writeValueAsString(platformSmsStatistics);

		//生成sign
		String string = platformSmsStatistics.getPlatform_No()+platformSmsStatistics.getStatistics_Date()+MessagesManger.getSystemMessages("secret");
		String sign = SecretUtil.MD5(string).toLowerCase();
		Map<String, Object> params = new HashMap<>();
		params.put("platformSmsStatistics", objToJson);
		params.put("sign", sign);//签名

		//发送统计结果
		String result = sendSmsStatistics(MessagesManger.getSystemMessages("platform_sms_statistics_url"),params,"utf-8");

		long endTime = System.currentTimeMillis();
		saveSystemLog("平台每日发送统计", startTime, endTime, result);
	}

	/**
	 * 发送统计结果
	 * @param url
	 * @param params
	 * @param charSet
	 * @return
	 * @throws Exception
	 */
	private String sendSmsStatistics(String url, Map<String, Object> params, String charSet) throws Exception {
		HttpClient httpClient = new HttpClient();
		httpClient.setCharset(charSet);
		String httpContent = JsonUtil.STANDARD.writeValueAsString(params);
		CharsetResponseBody response = httpClient.post(url, httpContent,HttpClient.MEDIA_TYPE_JSON);
		if(response != null){
			Map<String, String> resultMap = JsonUtil.STANDARD.readValue(response.string(), new TypeReference<Map<String, String>>() {});
			String httpResult = resultMap.get("result");
			if("0".equals(httpResult)){//0:成功，-1：失败
				return "";
			}else{
				return resultMap.get("desc");
			}
		}
		return "";
	}
}
