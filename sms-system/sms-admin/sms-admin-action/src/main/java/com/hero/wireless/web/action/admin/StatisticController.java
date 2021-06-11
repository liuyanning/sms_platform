package com.hero.wireless.web.action.admin;

import com.drondea.wireless.config.Constant;
import com.drondea.wireless.util.DateTime;
import com.drondea.wireless.util.ServiceException;
import com.drondea.wireless.util.SuperLogger;
import com.hero.wireless.json.LayUiJsonObjectFmt;
import com.hero.wireless.json.LayUiObjectMapper;
import com.hero.wireless.json.LayuiResultUtil;
import com.hero.wireless.json.SmsUIObjectMapper;
import com.hero.wireless.web.action.AdminControllerBase;
import com.hero.wireless.web.action.BaseAdminController;
import com.hero.wireless.web.config.DatabaseCache;
import com.hero.wireless.web.entity.business.EnterpriseUser;
import com.hero.wireless.web.entity.business.PlatformSmsStatistics;
import com.hero.wireless.web.entity.business.ext.PlatformSmsStatisticsExt;
import com.hero.wireless.web.entity.business.ext.SmsRealTimeStatisticsExt;
import com.hero.wireless.web.entity.business.ext.SmsStatisticsExt;
import com.hero.wireless.web.entity.send.ext.SubmitExt;
import com.hero.wireless.web.service.IEnterpriseManage;
import com.hero.wireless.web.service.ISendManage;
import com.hero.wireless.web.service.IStatisticsManage;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/")
public class StatisticController extends BaseAdminController {

    @Resource(name = "statisticsManage")
    private IStatisticsManage statisticsManage;
    @Resource(name = "sendManage")
    private ISendManage sendManage;

    @Resource(name = "enterpriseManage")
    private IEnterpriseManage enterpriseManage;

    @InitBinder
    public void initDateFormate(WebDataBinder dataBinder) {
        dataBinder.addCustomFormatter(new DateFormatter("yyyy-MM-dd HH:mm:ss"));
    }

    //三天之内列表
    @RequestMapping("statistic_threeDaysStatisticList")
    @ResponseBody
    public String threeDaysStatisticList(SmsRealTimeStatisticsExt statisticsExt) {
        Map<String,Object> statisticMap = null;
        List<SmsRealTimeStatisticsExt> list = null;
        try {
            statisticsExt.setBusiness_User_Id(AdminControllerBase.currentBusinessUserId());
            statisticMap = this.statisticsManage.countSmsRealTimeStatisticsGroupDataList(statisticsExt);
            list = this.statisticsManage.querySmsRealTimeStatisticsGroupDataList(statisticsExt);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return  new LayUiObjectMapper().asSuccessString(list, Integer.valueOf((ObjectUtils.isEmpty(statisticMap.get("totalRecord"))?0:statisticMap.get("totalRecord")).toString()));
    }

    //三天之前列表
    @RequestMapping("statistic_historyStatisticList")
    @ResponseBody
    public String smsStatisticDailyList(SmsStatisticsExt smsStatisticsExt) {
        smsStatisticsExt.setBusiness_User_Id(AdminControllerBase.currentBusinessUserId());
        List<SmsStatisticsExt> pageList =  this.statisticsManage.getSmsStatisticExtListByExtPage(smsStatisticsExt);
        Map<String,Object> statisticMap = this.statisticsManage.countSmsStatisticExtListByExt(smsStatisticsExt);
        return new LayUiObjectMapper().asSuccessString(pageList, Integer.valueOf(statisticMap.get("total_Record").toString()));
    }

    /**
     * 导出三天之内数据
     */
    @RequestMapping("statistic_exportThreeDaysStatisticList")
    @ResponseBody
    public LayUiJsonObjectFmt exportThreeDaysStatisticList(SmsRealTimeStatisticsExt statisticsExt) {
        try {
            statisticsManage.exportsmsDailySendedStatisticList(
                    DatabaseCache.getCodeBySortCodeAndCode("system_env", "export_dir").getValue()
                    , statisticsExt, getAdminDefaultExportFile(), Constant.PLATFORM_FLAG_ADMIN);
            return LayuiResultUtil.success("已提交后台导出任务!");
        }catch (ServiceException e){
            return LayuiResultUtil.fail(e.getMessage());
        }catch (Exception e){
            SuperLogger.error(e.getMessage(),e);
            return LayuiResultUtil.error(e);
        }
    }

    /**
     * 导出三天之前数据
     */
    @RequestMapping("statistic_exportHistoryStatisticList")
    @ResponseBody
    public LayUiJsonObjectFmt exportHistoryStatisticList(SmsStatisticsExt smsStatisticsExt) {
        try {
            statisticsManage.exportSmsStatistic(
                    DatabaseCache.getCodeBySortCodeAndCode("system_env", "export_dir").getValue(),
                    smsStatisticsExt, getAdminDefaultExportFile(),"SmsStatisticList");
            return new LayUiJsonObjectFmt(LayUiObjectMapper.CODE_SUCCESS, "已提交后台导出任务!");
        }catch (ServiceException e){
            return LayuiResultUtil.fail(e.getMessage());
        }catch (Exception e){
            SuperLogger.error(e.getMessage(),e);
            return LayuiResultUtil.error(e);
        }
    }
    //各平台短信统计列表
    @RequestMapping("statistic_platformSmsList")
    @ResponseBody
    public String platformSmsStatisticList(PlatformSmsStatisticsExt platformSmsStatisticsExt) {
        int count = 0;
        List<PlatformSmsStatistics> pageList = null;
        try {
            count = this.statisticsManage.countPlatformSmsStatisticsList(platformSmsStatisticsExt);
            pageList = this.statisticsManage.queryPlatformSmsStatisticsList(platformSmsStatisticsExt);
        } catch (ParseException e) {
            SuperLogger.error(e.getMessage(),e);
        }
        return new LayUiObjectMapper().asSuccessString(pageList, count);
    }
    /**
     * 导出各平台短信统计数据
     */
    @RequestMapping("statistic_exportPlatformStatisticList")
    @ResponseBody
    public LayUiJsonObjectFmt exportPlatformStatisticList(PlatformSmsStatisticsExt platformSmsStatisticsExt) {
        try {
            statisticsManage.exportPlatformStatistic(
                    DatabaseCache.getCodeBySortCodeAndCode("system_env", "export_dir").getValue(),
                    platformSmsStatisticsExt, getAdminDefaultExportFile(),"PlatformStatisticList");
            return new LayUiJsonObjectFmt(LayUiObjectMapper.CODE_SUCCESS, "已提交后台导出任务!");
        }catch (ServiceException e){
            return LayuiResultUtil.fail(e.getMessage());
        }catch (Exception e){
            SuperLogger.error(e.getMessage(),e);
            return LayuiResultUtil.error(e);
        }
    }

    // 报表管理》》未知统计
    @RequestMapping("statistic_unknownStatisticList")
    @ResponseBody
    public String unknownStatisticList(SmsRealTimeStatisticsExt statisticsExt) {
        List<SmsRealTimeStatisticsExt> list = null;
        try {
            list = this.statisticsManage.queryUnknownStatisticList(statisticsExt);
        } catch (ServiceException e) {
            return new SmsUIObjectMapper().asFaildString(e.getMessage());
        } catch (Exception e) {
            SuperLogger.error(e.getMessage(), e);
            return new SmsUIObjectMapper().asErrorString();
        }
        return  new LayUiObjectMapper().asSuccessString(list);
    }

    // 短信管理 ==》实时统计前置
    @RequestMapping("statistic_preRealTimeStatistics")
    public ModelAndView preRealTimeStatistics() {
        ModelAndView mv = new ModelAndView("/statistic/sms_real_time_statistics");
        String  interval = DatabaseCache.getStringValueBySortCodeAndCode("page_configuration", "page_search_interval", "");
        if (StringUtils.isNotBlank(interval)) {
            mv.addObject("minCreateDate", DateTime.getCurentTimeBeforeMinutes(Integer.valueOf(interval)));
        }
        return mv;
    }


    /**
     *
     * @Title: sendFaildNativeStatusDetails
     * @Description: 发送失败的错误码汇总
     * @author yjb
     * @param smsStatisticsExt
     * @return
     * @throws Exception
     * @throws
     * @date 2020-10-13
     */
    @RequestMapping("statistic_sendFaildNativeStatusDetails")
    public ModelAndView sendFaildNativeStatusDetails(SmsStatisticsExt smsStatisticsExt) throws Exception {
        smsStatisticsExt.setBusiness_User_Id(AdminControllerBase.currentBusinessUserId());
        ModelAndView mv = getSendData(smsStatisticsExt);
        return mv;
    }

    private ModelAndView getSendData(SmsStatisticsExt smsStatisticsExt) {
        ModelAndView mv = new ModelAndView("/statistic/send_faild_native_status_details");
        List<com.hero.wireless.web.entity.send.ext.ReportExt> list = this.statisticsManage.queryReportNativeStatus(smsStatisticsExt);
        int countTotal = list.stream().mapToInt(com.hero.wireless.web.entity.send.ext.ReportExt::getCount_Total).sum();

        List<com.hero.wireless.web.entity.send.ext.ReportExt> resultList = new ArrayList<com.hero.wireless.web.entity.send.ext.ReportExt>();
        for (int t = 0; t < list.size(); t++) {
            list.get(t).setFaild_Code_Rate((new BigDecimal(list.get(t).getCount_Total())
                    .divide(new BigDecimal(countTotal == 0 ? list.get(t).getCount_Total()
                            : countTotal), 4, BigDecimal.ROUND_HALF_UP)
                    .multiply(new BigDecimal(100)).setScale(4, BigDecimal.ROUND_HALF_UP)));
            resultList.add(list.get(t));
        }
        EnterpriseUser user = null;
        if(smsStatisticsExt.getGroup_Str().contains("Enterprise_User_Id")){
            user = enterpriseManage.queryEnterpriseUserById(smsStatisticsExt.getEnterprise_User_Id());
        }
        mv.addObject("details",resultList);
        mv.addObject("realName",user==null?null:user.getReal_Name());
        return mv;
    }


    /**
     *
     * @Title: preSendSpeedList
     * @Description: 通道发送速度汇总前置
     * @author yjb
     * @return
     * @throws
     * @date 2020-10-13
     */
    @RequestMapping("statistic_preSendSpeedList")
    public ModelAndView preSendSpeedList() {
        ModelAndView mv = new ModelAndView("/statistic/sms_sended_speed_statistic");
        mv.addObject("minSubmitDate", DateTime.getCurentTimeBeforeMinutes(Integer.valueOf(1)));
        mv.addObject("maxSubmitDate", DateTime.getCurentTimeBeforeMinutes(Integer.valueOf(0)));
        return mv;
    }

    /**
     *
     * @Title: submitedList
     * @Description: 通道发送速度汇总
     * @author yjb
     * @param submitedExt
     * @return
     * @throws
     * @date 2020-10-13
     */
    @RequestMapping("statistic_sended_speed")
    @ResponseBody
    public String sendSpeedList(SubmitExt submitedExt) {
        try {
            List<SubmitExt> submitedExtList = sendManage.querysendSpeed(submitedExt);
            return new SmsUIObjectMapper().asSuccessString(submitedExtList, submitedExt);
        }catch (ServiceException e){
            return new SmsUIObjectMapper().asFaildString(e.getMessage());
        }catch (Exception e){
            SuperLogger.error(e.getMessage(),e);
            return new SmsUIObjectMapper().asFaildString(e.getMessage());
        }
    }

}