package com.hero.wireless.web.action.admin;

import com.drondea.wireless.util.DateTime;
import com.drondea.wireless.util.SuperLogger;
import com.hero.wireless.json.LayUiObjectMapper;
import com.hero.wireless.web.action.AdminControllerBase;
import com.hero.wireless.web.config.DatabaseCache;
import com.hero.wireless.web.entity.business.Code;
import com.hero.wireless.web.entity.business.Enterprise;
import com.hero.wireless.web.entity.business.ext.SmsRealTimeStatisticsExt;
import com.hero.wireless.web.entity.send.ext.ReportExt;
import com.hero.wireless.web.entity.send.ext.SubmitExt;
import com.hero.wireless.web.service.IEnterpriseManage;
import com.hero.wireless.web.service.ISendManage;
import com.hero.wireless.web.service.IStatisticsManage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class IndexController extends AdminControllerBase {

	@Resource(name = "statisticsManage")
	private IStatisticsManage statisticsManage;
	@Resource(name = "sendManage")
	private ISendManage sendManage;
	@Resource(name = "enterpriseManage")
	private IEnterpriseManage enterpriseManage;
	/**
	 * 登录成功跳转至index页面
	 *
	 * @return
	 */
	@RequestMapping("/index")
	public ModelAndView index() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/home/console");
		if (currentBusinessUserId() != null) {
			mv.setViewName("/home/business_console");
		}
		return mv;
	}

	@RequestMapping("/index_currentDataMap")
	@ResponseBody
	public Map currentDataMap() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		ReportExt reportExt = new ReportExt();
		reportExt.setMinSubmitDate(DateTime.getDate(DateTime.getCurrentDayMinDate()));
		reportExt.setMaxSubmitDate(DateTime.getDate(DateTime.getCurrentDayMaxDate()));
		reportExt.setGroupStr("Submit_Date");
		List<ReportExt> listReport = sendManage.querySendCountDetailFromReport(reportExt);

		SubmitExt submitExt = new SubmitExt();
		submitExt.setMinSubmitDate(DateTime.getDate(DateTime.getCurrentDayMinDate()));
		submitExt.setMaxSubmitDate(DateTime.getDate(DateTime.getCurrentDayMaxDate()));
		List<SubmitExt> listSubmit = sendManage.querySendCountDetailFromSubmit(submitExt);

		List<Enterprise> listEnterprise = enterpriseManage.queryAddEnterpriseCount();
		int submitTotal = 0;
		if(listSubmit.size() > 0){
			submitTotal = (listSubmit.get(0).getSubmit_Success_Total())==null?0:listSubmit.get(0).getSubmit_Success_Total();
		}
		int sendSuccessTotal = 0;
		if (listReport.size()>0){
			ReportExt source = listReport.get(0);
			sendSuccessTotal = source.getSend_Success_Total();
		}
		map.put("submitTotal", submitTotal);
		map.put("sendSuccessCount", sendSuccessTotal);
		map.put("addEnterpriseCount", listEnterprise.size());
		return map;
	}

	@RequestMapping("/index_enterpriseSendMap")
	@ResponseBody
	public String enterpriseSendMap() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			//获取一周的日期
			ArrayList<String> realTimes = DateTime.getDays(7);
			//获取提交成功的数据
			SubmitExt submitExt = new SubmitExt();
			submitExt.setMinSubmitDate(DateTime.getDate(realTimes.get(0)+" 00:00:00"));
			submitExt.setMaxSubmitDate(DateTime.getDate(realTimes.get(6)+" 23:59:59:999"));
			submitExt.setGroupStr("Submit_Date");
			List<SubmitExt> listSubmit = sendManage.querySendCountDetailFromSubmit(submitExt);
			//获取发送成功的数据
			ReportExt reportExt = new ReportExt();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			reportExt.setMinSubmitDate(DateTime.getDate(realTimes.get(0)+" 00:00:00"));
			reportExt.setMaxSubmitDate(DateTime.getDate(realTimes.get(6)+" 23:59:59:999"));
			reportExt.setGroupStr("Submit_Date");
			List<ReportExt> listReport = sendManage.querySendCountDetailFromReport(reportExt);

			for(int i = 0;i<realTimes.size();i++){
				int countTotal = 0;
				int sendSuccessCountTotal = 0;
				Map<String, Object> map = new HashMap<>();
				map.put("submitDate", realTimes.get(i));
				map.put("countTotal", countTotal);
				map.put("sendSuccessCountTotal", sendSuccessCountTotal);
				for(SubmitExt ext : listSubmit) {
					if (ext.getCount_Total() > 0) {
						String submitDate = DateTime.getString(ext.getSubmit_Date(),DateTime.Y_M_D_1);
						if(realTimes.get(i).equals(submitDate)){
							map.put("countTotal", (ext.getSubmit_Success_Total()==null?0:ext.getSubmit_Success_Total()));
							break;
						}
					}
				}
				for(ReportExt entity: listReport){
					if(entity.getCount_Total()>0){
						String subDate = DateTime.getString(entity.getSubmit_Date(),DateTime.Y_M_D_1);
						if(realTimes.get(i).equals(subDate)){
							map.put("sendSuccessCountTotal", (entity.getSend_Success_Total()));
							break;
						}
					}
				}
				list.add(map);
			}
			return new LayUiObjectMapper().asSuccessString(list);
		} catch (Exception e) {
			SuperLogger.error(e.getMessage(), e);
			return new LayUiObjectMapper().asSuccessString(list);
		}
	}

	@RequestMapping("/index_currentOperatorSendMapData")
	@ResponseBody
	public String index_currentOperatorSendMapData() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			//获取提交成功的数据
			SubmitExt submitExt = new SubmitExt();
			submitExt.setMinSubmitDate(DateTime.getDate(DateTime.getCurrentDayMinDate()));
			submitExt.setMaxSubmitDate(DateTime.getDate(DateTime.getCurrentDayMaxDate()));
			submitExt.setGroupStr("Operator");
			List<SubmitExt> listSubmit = sendManage.querySendCountDetailFromSubmit(submitExt);

			ReportExt reportExt = new ReportExt();
			reportExt.setMinSubmitDate(DateTime.getDate(DateTime.getCurrentDayMinDate()));
			reportExt.setMaxSubmitDate(DateTime.getDate(DateTime.getCurrentDayMaxDate()));
			reportExt.setGroupStr("Operator");
			List<ReportExt> listReport = sendManage.querySendCountDetailFromReport(reportExt);

			for(int i =0 ;i<listSubmit.size();i++){
				Map<String, Object> map = new HashMap<>();
				map.put("count", listSubmit.get(i).getCount_Total());
				map.put("submitSuccessTotal", listSubmit.get(i).getSubmit_Success_Total());
				for(int t = 0;t<listReport.size();t++){
					if(listSubmit.get(i).getOperator().equals(listReport.get(t).getOperator())){
						map.put("successCount", listReport.get(t).getSend_Success_Total());
					}
				}
				map.put("operator", listSubmit.get(i).getOperator());
				list.add(map);
			}
			return new LayUiObjectMapper().asSuccessString(list);
		} catch (Exception e) {
			SuperLogger.error(e.getMessage(), e);
			return new LayUiObjectMapper().asSuccessString(list);
		}
	}

	@RequestMapping("/index_currentEchartsMapData")
	@ResponseBody
	public String currentEchartsMapData() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			SmsRealTimeStatisticsExt statisticsExt = new SmsRealTimeStatisticsExt();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			statisticsExt.setMin_Submit_Date(sdf.parse(DateTime.getString()));
			statisticsExt.setGroupStr("Province_Code");
			List<SmsRealTimeStatisticsExt> smsRealTimeStatisticsExts = statisticsManage.querySmsRealTimeStatisticsGroupDataList(statisticsExt);
			for (SmsRealTimeStatisticsExt source: smsRealTimeStatisticsExts){
				String province = source.getProvince_Code();
				Map<String, Object> map = new HashMap<>();
				map.put("value", source.getSubmit_Success_Total());
				if (!"000".equals(province)) {
					Code code = DatabaseCache.getCodeBySortCodeAndCode("location", province);
					map.put("name", code == null?"":code.getName());
				}
				list.add(map);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}finally {
			return new LayUiObjectMapper().asSuccessString(list);
		}
	}

}
