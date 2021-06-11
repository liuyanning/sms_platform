package com.hero.wireless.web.action.admin;

import com.drondea.wireless.util.DateTime;
import com.drondea.wireless.util.ServiceException;
import com.drondea.wireless.util.SuperLogger;
import com.hero.wireless.json.LayUiJsonObjectFmt;
import com.hero.wireless.json.LayUiObjectMapper;
import com.hero.wireless.json.LayuiResultUtil;
import com.hero.wireless.web.action.BaseEnterpriseController;
import com.hero.wireless.web.config.DatabaseCache;
import com.hero.wireless.web.entity.business.ext.SmsRealTimeStatisticsExt;
import com.hero.wireless.web.entity.business.ext.SmsStatisticsExt;
import com.hero.wireless.web.service.IStatisticsManage;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

@Controller
@RequestMapping("/admin")
public class StatisticController extends BaseEnterpriseController {

	@Resource(name = "statisticsManage")
	private IStatisticsManage statisticsManage;

	// 短信日报表
	@RequestMapping("/statistic_smsStatisticList")
	@ResponseBody
	public String smsStatisticDailyList(SmsStatisticsExt smsStatisticsExt) {
		smsStatisticsExt.setEnterprise_No(getLoginEnterprise().getNo());
        smsStatisticsExt.setEnterprise_User_Id(getLoginEnterpriseUser().getId());
		List<SmsStatisticsExt> pageList = this.statisticsManage.getSmsStatisticExtListByExtPage(smsStatisticsExt);
		Map<String, Object> statisticMap = this.statisticsManage.countSmsStatisticExtListByExt(smsStatisticsExt);
		return new LayUiObjectMapper().asSuccessString(pageList, Integer.valueOf(statisticMap.get("total_Record").toString()));
	}

	/**
	 * 导出短信日报表
	 */
	@RequestMapping("/statistic_exportSmsStatisticList")
	@ResponseBody
	public LayUiJsonObjectFmt exportLSmsStatisticList(SmsStatisticsExt smsStatisticsExt) {
		try {
            smsStatisticsExt.setEnterprise_No(getLoginEnterprise().getNo());
            smsStatisticsExt.setEnterprise_User_Id(getUserId());
            statisticsManage.exportSmsStatisticByEnterprise(
                    DatabaseCache.getCodeBySortCodeAndCode("system_env", "export_dir").getValue(), smsStatisticsExt,
                    getEnterpriseDefaultExportFile());
            return new LayUiJsonObjectFmt(LayUiObjectMapper.CODE_SUCCESS, "已提交后台导出任务!");
        }catch (ServiceException e){
            return LayuiResultUtil.fail(e.getMessage());
        }catch (Exception e){
            SuperLogger.error(e.getMessage(),e);
            return LayuiResultUtil.error(e);
        }
	}

	// 实时统计
	@RequestMapping("statistic_smsNowStatisticList")
	@ResponseBody
	public String smsNowStatisticList(SmsRealTimeStatisticsExt statisticsExt) throws ParseException {
		statisticsExt.setGroupStr("Submit_Date");
		statisticsExt.setEnterprise_No(getLoginEnterprise().getNo());
        statisticsExt.setEnterprise_User_Id(getUserId());
		List<SmsRealTimeStatisticsExt> list = this.statisticsManage.querySmsRealTimeStatisticsGroupDataList(statisticsExt);
		return new LayUiObjectMapper().asSuccessString(list,list.size());
	}

	// 短信管理 ==》实时统计前置
	@RequestMapping("statistic_preRealTimeStatistics")
	public ModelAndView preRealTimeStatistics() {
		ModelAndView mv = new ModelAndView("/charge/sms_real_time_statistics");
		String  interval = DatabaseCache.getStringValueBySortCodeAndCode("page_configuration", "page_search_interval", "");
		if (StringUtils.isNotBlank(interval)) {
			mv.addObject("minCreateDate", DateTime.getCurentTimeBeforeMinutes(Integer.valueOf(interval)));
		}
		return mv;
	}

}
