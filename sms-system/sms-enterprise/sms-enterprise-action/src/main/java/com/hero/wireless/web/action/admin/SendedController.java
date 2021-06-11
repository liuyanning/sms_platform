package com.hero.wireless.web.action.admin;

import com.drondea.wireless.config.Constant;
import com.drondea.wireless.util.*;
import com.hero.wireless.enums.MessageType;
import com.hero.wireless.enums.ProtocolType;
import com.hero.wireless.json.LayUiJsonObjectFmt;
import com.hero.wireless.json.LayUiObjectMapper;
import com.hero.wireless.json.LayuiResultUtil;
import com.hero.wireless.json.SmsUIObjectMapper;
import com.hero.wireless.web.action.BaseEnterpriseController;
import com.hero.wireless.web.action.admin.config.InitSystemEnv;
import com.hero.wireless.web.action.entity.BaseParamEntity;
import com.hero.wireless.web.action.interceptor.AvoidRepeatableCommitAnnotation;
import com.hero.wireless.web.config.DatabaseCache;
import com.hero.wireless.web.entity.business.ContactGroup;
import com.hero.wireless.web.entity.business.ExportFile;
import com.hero.wireless.web.entity.business.ext.SmsTemplateExt;
import com.hero.wireless.web.entity.ext.SqlStatisticsEntity;
import com.hero.wireless.web.entity.send.*;
import com.hero.wireless.web.entity.send.ext.*;
import com.hero.wireless.web.service.IReportNotifyService;
import com.hero.wireless.web.service.ISendManage;
import com.hero.wireless.web.service.facede.SubmitDetailsFacede;
import com.hero.wireless.web.util.CodeUtil;
import com.hero.wireless.web.util.UploadUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/")
public class SendedController extends BaseEnterpriseController {

    @Resource(name = "sendManage")
    private ISendManage sendManage;
    @Resource(name = "reportNotifyService")
    private IReportNotifyService reportNotifyService;

    @InitBinder
    public void initDateFormate(WebDataBinder dataBinder) {
        dataBinder.addCustomFormatter(new DateFormatter("yyyy-MM-dd HH:mm:ss"));
    }
    /**
     * 个性提交短信
     *
     * @return
     */
    @RequestMapping("sended_formatSmsSend")
    @ResponseBody
    @AvoidRepeatableCommitAnnotation(systemModuleName = ENTERPRISE_PLATFORM + "sended_formatSmsSend")
    public LayUiJsonObjectFmt sendedFormatSmsSend(InputExt input,
                                                  @RequestParam(value = "moblieFile") MultipartFile moblieFile) {
        input = assemblyInput(input);
        input.setMessage_Type_Code(MessageType.SMS.toString());
        try {
            int count = this.sendManage.formatSmsSend(moblieFile, input);
            return LayuiResultUtil.success(String.format("成功提交%1$s个号码", count));
        } catch (ServiceException e) {
            return LayuiResultUtil.fail(e.getMessage());
        } catch (Exception e) {
            return LayuiResultUtil.fail(e.getMessage());
        }
    }

    protected InputExt assemblyInput(InputExt input) {
        input.setEnterprise_No(getLoginEnterprise().getNo());
        input.setEnterprise_User_Id(getUserId());
        input.setSource_IP(IpUtil.getRemoteIpAddr(request));
        input.setProtocol_Type_Code(ProtocolType.WEB.toString());
        //设置为本机IP
        input.setGate_Ip(InitSystemEnv.LOCAL_IP);
        input.setMsg_Batch_No(CodeUtil.buildMsgNo());
        input.setInput_Date(new Date());
        if (input.getCountry_Code() == null || StringUtils.isEmpty(input.getCountry_Code().trim())) {
            String defaultCountryCode = DatabaseCache.getStringValueBySortCodeAndCode("custom_switch", "default_country_code", "86");
            input.setCountry_Code(defaultCountryCode);
        } else {
            input.setCountry_Code(input.getCountry_Code().trim());
        }
        return input;
    }

    /**
     * 发送短信
     *
     * @return
     */
    @RequestMapping("sended_fileInputSms")
    @ResponseBody
    @AvoidRepeatableCommitAnnotation(systemModuleName = ENTERPRISE_PLATFORM + "sended_fileInputSms")
    public LayUiJsonObjectFmt fileInputSms(InputExt input) {
        input = assemblyInput(input);
        if (StringUtils.isEmpty(input.getMessage_Type_Code())) {
            input.setMessage_Type_Code(MessageType.SMS.toString());
        }
        try {
            //无论短信、彩信、视频、统一调用此方法
            int count = this.sendManage.batchInputSms(input);
            return LayuiResultUtil.success(String.format("成功提交%1$s个号码", count));
        } catch (ServiceException e) {
            return LayuiResultUtil.error(e);
        } catch (Exception e) {
            e.printStackTrace();
            return LayuiResultUtil.error(e);
        }
    }

    // 短信发件箱前置
    @RequestMapping("sended_preInputLogList")
    public ModelAndView preInputLogList() {
        ModelAndView mv = new ModelAndView("/sended/input_log_list");
        String  interval = DatabaseCache.getStringValueBySortCodeAndCode("page_configuration", "page_search_interval", "");
        if (StringUtils.isNotBlank(interval)) {
            mv.addObject("minCreateDate", DateTime.getCurentTimeBeforeMinutes(Integer.valueOf(interval)));
        }
        return mv;
    }

    // 发送记录前置
    @RequestMapping("sended_preSubmitList")
    public ModelAndView preSubmitList() {
        ModelAndView mv = new ModelAndView("/sended/submit_list");
        String  interval = DatabaseCache.getStringValueBySortCodeAndCode("page_configuration", "page_search_interval", "");
        if (StringUtils.isNotBlank(interval)) {
            mv.addObject("minCreateDate", DateTime.getCurentTimeBeforeMinutes(Integer.valueOf(interval)));
        }
        return mv;
    }

    // 短信管理 ==》发送回执前置
    @RequestMapping("sended_preReportList")
    public ModelAndView preReportList() {
        ModelAndView mv = new ModelAndView("/sended/report_list");
        String  interval = DatabaseCache.getStringValueBySortCodeAndCode("page_configuration", "page_search_interval", "");
        if (StringUtils.isNotBlank(interval)) {
            mv.addObject("minCreateDate", DateTime.getCurentTimeBeforeMinutes(Integer.valueOf(interval)));
        }
        return mv;
    }

    /**
     * 发件箱
     */
    @RequestMapping("sended_inputLogList")
    @ResponseBody
    public String inputLogList(InputLogExt inputLogExt) {
     try {
        inputLogExt.setEnterprise_No(getLoginEnterprise().getNo());
        inputLogExt.setEnterprise_User_Id(getLoginEnterpriseUser().getId());
        List<InputLogExt> inputLogExtList = sendManage.queryInputLogListSharding(inputLogExt);
        return new SmsUIObjectMapper(isBlurPhoneNo()).asSuccessString(inputLogExtList, inputLogExt.getPagination());
     } catch (Exception e) {
        String s = e.getMessage();
        String substring = s.substring(s.length()-13);
        if(("doesn't exist").equals(substring)) {
            return new SmsUIObjectMapper().asFaildString("超出可查询时间");
        }
        return new SmsUIObjectMapper().asFaildString(e.getMessage());
     }
    }

    /**
     * 收件箱
     *
     * @return
     */
    @RequestMapping("sended_inboxList")
    @ResponseBody
    public String inboxList(InboxExt inbox) {
        inbox.setEnterprise_No(this.getLoginEnterprise().getNo());
        inbox.setEnterprise_User_Id(getLoginEnterpriseUser().getId());
        List<Inbox> inboxList = sendManage.queryInboxList(inbox);
        return new SmsUIObjectMapper(isBlurPhoneNo()).asSuccessString(inboxList, inbox.getPagination());
    }

    /**
     * 导出收件箱
     */
    @RequestMapping("sended_exportInboxList")
    @ResponseBody
    public LayUiJsonObjectFmt exportInboxList(InboxExt inboxExt) {
        try {
            inboxExt.setEnterprise_No(this.getLoginEnterprise().getNo());
            inboxExt.setEnterprise_User_Id(getLoginEnterpriseUser().getId());
            sendManage.exportInboxList(DatabaseCache.getCodeBySortCodeAndCode("system_env", "export_dir").getValue(),
                    inboxExt, getEnterpriseDefaultExportFile(), Constant.PLATFORM_FLAG_ENTERPRISE);
            return new LayUiJsonObjectFmt(SmsUIObjectMapper.CODE_SUCCESS, "已提交后台导出任务!");
        } catch (ServiceException e) {
            return LayuiResultUtil.fail(e.getMessage());
        } catch (Exception e) {
            SuperLogger.error(e.getMessage(), e);
            return LayuiResultUtil.error(e);
        }
    }

    // 发送记录
    @RequestMapping("sended_submitList")
    @ResponseBody
    public String submitList(SubmitExt submitExt) {
     try{
        submitExt.setEnterprise_No(this.getLoginEnterprise().getNo());
        submitExt.setEnterprise_User_Id(getLoginEnterpriseUser().getId());
        submitExt.setMaxSubmitDate(DateTime.getTimeForMaxMillisecond(submitExt.getMaxSubmitDate()));
        List<SubmitExt> submitExtList = sendManage.querySubmitListSharding(submitExt);
        return new SmsUIObjectMapper(isBlurPhoneNo()).asSuccessString(submitExtList, submitExt.getPagination());
    } catch (Exception e) {
        String s = e.getMessage();
        String substring = s.substring(s.length()-13);
        if(("doesn't exist").equals(substring)) {
            return new SmsUIObjectMapper().asFaildString("超出可查询时间");
        }
        return new SmsUIObjectMapper().asFaildString(e.getMessage());
    }
    }

    /**
     * 短信管理 ==》发送记录==》点击详情
     *
     * @return
     */
    @RequestMapping("sended_reportIndex")
    public String reportIndex(String channel_Master_Msg_No, String limitCode, String flag, String minCreateDate,
                              String msg_Batch_No) {
        request.setAttribute("channel_Master_Msg_No", channel_Master_Msg_No);
        request.setAttribute("msg_Batch_No", msg_Batch_No);
        request.setAttribute("limitCode", limitCode);
        request.setAttribute("flag", flag);
        if (!StringUtils.isEmpty(minCreateDate) && minCreateDate.length() > 10) {
            minCreateDate = minCreateDate.substring(0, 10) + " 00:00:00";
            String maxCreateDate = minCreateDate.substring(0, 10) + " 23:59:59";
            request.setAttribute("minCreateDate", minCreateDate);
            request.setAttribute("maxCreateDate", maxCreateDate);
        }
        return "/sended/report_list";
    }

    /**
     * 短信管理 ==》发送回执
     *
     * @param reportExt flag: Submit_Flag 从发送记录点击详情 Input_Log_Flag 从发件箱点击详情 null
     *                  直接点击发送明细菜单
     * @return
     */
    @RequestMapping("sended_reportList")
    @ResponseBody
    public String reportList(ReportExt reportExt, String flag) {
      try {
        reportExt.setEnterprise_No(getLoginEnterprise().getNo());
        reportExt.setEnterprise_User_Id(getLoginEnterpriseUser().getId());
        reportExt.setMaxSubmitDate(DateTime.getTimeForMaxMillisecond(reportExt.getMaxSubmitDate()));
        //发送记录详情
        if ("Submit_Flag".equals(flag) && StringUtils.isEmpty(reportExt.getChannel_Msg_Id())) {
            return new SmsUIObjectMapper().asSuccessString(null, reportExt.getPagination());
        }
        //提交记录详情
        if ("Input_Log_Flag".equals(flag) && StringUtils.isEmpty(reportExt.getMsg_Batch_No())) {
            return new SmsUIObjectMapper().asSuccessString(null, reportExt.getPagination());
        }
        List<ReportExt> reportExtList = sendManage.queryReportListSharding(reportExt);
        return new SmsUIObjectMapper(isBlurPhoneNo()).asSuccessString(reportExtList, reportExt.getPagination());
      } catch (Exception e) {
        String s = e.getMessage();
        String substring = s.substring(s.length()-13);
        if(("doesn't exist").equals(substring)) {
            return new SmsUIObjectMapper().asFaildString("超出可查询时间");
        }
        return new SmsUIObjectMapper().asFaildString(e.getMessage());
      }
    }

    @RequestMapping("sended_exportFileList")
    @ResponseBody
    public String exportFileList(ExportFile exportFile) {
        exportFile.setAgent_No("0");
        exportFile.setEnterprise_No(this.getLoginEnterprise().getNo());
        exportFile.setUser_Id(getLoginEnterpriseUser().getId());
        List<ExportFile> exportFileList = sendManage.queryExportFile(exportFile);
        return new SmsUIObjectMapper().asSuccessString(exportFileList, exportFile.getPagination());
    }

    /**
     * 文件下载
     **/
    @RequestMapping("sended_downloadFile")
    @ResponseBody
    public void downloadFile(HttpServletResponse response, Integer id) {
        try {
            ExportFile exportFile = getEnterpriseDefaultExportFile();
            exportFile.setId(id);
            sendManage.downloadFile(response, exportFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("sended_addAutoReplySms")
    @ResponseBody
    @AvoidRepeatableCommitAnnotation(systemModuleName = ENTERPRISE_PLATFORM + "sended_addAutoReplySms")
    public LayUiJsonObjectFmt addAutoReplySms(AutoReplySms autoReplySms) {
        try {
            autoReplySms.setEnterprise_No(getLoginEnterprise().getNo());
            autoReplySms.setEnterprise_User_Id(getUserId());
            autoReplySms.setCreate_User_Id(getUserId());
            autoReplySms.setCreate_User(getLoginRealName());
            autoReplySms = this.sendManage.addAutoReplySms(autoReplySms);
            request.setAttribute("autoReplySms", autoReplySms);
        } catch (ServiceException e) {
            return LayuiResultUtil.error(e);
        }
        return LayuiResultUtil.success();
    }

    @RequestMapping("sended_autoReplySmsList")
    @ResponseBody
    public String autoReplySmsList(AutoReplySms autoReplySms) {
        autoReplySms.setEnterprise_No(getLoginEnterprise().getNo());
        autoReplySms.setEnterprise_User_Id(getLoginEnterpriseUser().getId());
        List<AutoReplySms> autoReplySmsList = sendManage.queryAutoReplySmsList(autoReplySms);
        return new SmsUIObjectMapper().asSuccessString(autoReplySmsList, autoReplySms.getPagination());
    }

    @RequestMapping("sended_preEditAutoReplySms")
    public ModelAndView preEditAutoReplySms(BaseParamEntity entity) {
        ModelAndView mv = new ModelAndView("/sended/auto_reply_sms_edit");
        if (entity.getCkIds() == null || entity.getCkIds().isEmpty()) {
            throw new ServiceException("请选择一条数据");
        }
        AutoReplySms condition = new AutoReplySms();
        condition.setId(entity.getCkIds().get(0));
        condition.setEnterprise_No(getLoginEnterprise().getNo());
        condition.setEnterprise_User_Id(getLoginEnterpriseUser().getId());
        AutoReplySms autoReplySms = this.sendManage.queryAutoReplySmsByIdSelective(condition);
        mv.addObject("autoReplySms", autoReplySms);
        return mv;
    }

    @RequestMapping("sended_editAutoReplySms")
    @ResponseBody
    public LayUiJsonObjectFmt editAutoReplySms(AutoReplySms autoReplySms) {
        try {
            autoReplySms.setEnterprise_No(getLoginEnterprise().getNo());
            autoReplySms.setEnterprise_User_Id(getLoginEnterpriseUser().getId());
            this.sendManage.editAutoReplySms(autoReplySms);
        } catch (ServiceException e) {
            return LayuiResultUtil.error(e);
        }
        return LayuiResultUtil.success();
    }

    @RequestMapping("sended_batchDeleteAutoReplySms")
    @ResponseBody
    public LayUiJsonObjectFmt batchDeleteAutoReplySms(BaseParamEntity entity) {
        this.sendManage.deleteAutoReplySmsByIds(entity.getCkIds(), getAuthorityEnterpriseUserBean());
        return LayuiResultUtil.success();
    }

    /**
     * 定时短信
     */
    @RequestMapping("sms_sentTime")
    @ResponseBody
    public String queryInputList(InputExt inputExt) {
        inputExt.setEnterprise_No(getLoginEnterprise().getNo());
        inputExt.setEnterprise_User_Id(getUserId());
        List<Input> list = sendManage.queryTimerInputList(inputExt);
        return new SmsUIObjectMapper(isBlurPhoneNo()).asSuccessString(list, inputExt.getPagination());
    }

    @RequestMapping("delete_InputTimeList")
    @ResponseBody
    public LayUiJsonObjectFmt deleteInputList(@RequestParam(name = "ckIds") List<Long> ckIds) {
        sendManage.deleteInputById(ckIds, getAuthorityEnterpriseUserBean());
        return LayuiResultUtil.success();
    }

    /**
     * 导出发件箱
     */
    @RequestMapping("sended_exportInputLogList")
    @ResponseBody
    public LayUiJsonObjectFmt exportInputLogList(InputLogExt inputLogExt) throws Exception {
        try {
            inputLogExt.setEnterprise_No(getLoginEnterprise().getNo());
            inputLogExt.setEnterprise_User_Id(getLoginEnterpriseUser().getId());
            sendManage.exportInputLog(DatabaseCache.getCodeBySortCodeAndCode("system_env", "export_dir").getValue(),
                    inputLogExt, getEnterpriseDefaultExportFile());
            return new LayUiJsonObjectFmt(LayUiObjectMapper.CODE_SUCCESS, "已提交后台导出任务!");
        } catch (ServiceException e) {
            return LayuiResultUtil.fail(e.getMessage());
        } catch (Exception e) {
            SuperLogger.error(e.getMessage(), e);
            return LayuiResultUtil.error(e);
        }
    }

    /**
     * 导出发送记录
     */
    @RequestMapping("sended_exportSubmitList")
    @ResponseBody
    public LayUiJsonObjectFmt exportSubmitList(SubmitExt submitExt) {
        try {
            submitExt.setEnterprise_No(getLoginEnterprise().getNo());
            submitExt.setEnterprise_User_Id(getLoginEnterpriseUser().getId());
            sendManage.exportSubmit(DatabaseCache.getCodeBySortCodeAndCode("system_env", "export_dir").getValue(),
                    submitExt, getEnterpriseDefaultExportFile(), Constant.PLATFORM_FLAG_ENTERPRISE);
            return new LayUiJsonObjectFmt(LayUiObjectMapper.CODE_SUCCESS, "已提交后台导出任务!");
        } catch (ServiceException e) {
            return LayuiResultUtil.fail(e.getMessage());
        } catch (Exception e) {
            SuperLogger.error(e.getMessage(), e);
            return LayuiResultUtil.error(e);
        }
    }

    /**
     * 导出发送回执
     */
    @RequestMapping("sended_exportReportList")
    @ResponseBody
    public LayUiJsonObjectFmt exportReportList(ReportExt reportExt) {
        try {
            reportExt.setEnterprise_No(getLoginEnterprise().getNo());
            reportExt.setEnterprise_User_Id(getLoginEnterpriseUser().getId());
            sendManage.exportReport(DatabaseCache.getCodeBySortCodeAndCode("system_env", "export_dir").getValue(),
                    reportExt, getEnterpriseDefaultExportFile(), Constant.PLATFORM_FLAG_ENTERPRISE);
            return new LayUiJsonObjectFmt(LayUiObjectMapper.CODE_SUCCESS, "已提交后台导出任务!");
        } catch (ServiceException e) {
            return LayuiResultUtil.fail(e.getMessage());
        } catch (Exception e) {
            SuperLogger.error(e.getMessage(), e);
            return LayuiResultUtil.error(e);
        }
    }

    /**
     * 发送记录表头统计  submit
     *
     * @param submitExt
     * @return
     */
    @RequestMapping("sended_querySubmitListTotalData")
    @ResponseBody
    public String querySubmitListTotalData(SubmitExt submitExt) {
        submitExt.setEnterprise_No(getLoginEnterprise().getNo());
        submitExt.setEnterprise_User_Id(getUserId());
        SqlStatisticsEntity statisticsEntity = sendManage.querySubmitListTotalData(submitExt);
        return new LayUiObjectMapper().asSuccessString(statisticsEntity);
    }

    //发送回执表头统计
    @SuppressWarnings("all")
    @RequestMapping("sended_queryReportListTotalData")
    @ResponseBody
    public String queryReportListTotalData(ReportExt reportExt, String flag) {
        reportExt.setEnterprise_No(getLoginEnterprise().getNo());
        reportExt.setEnterprise_User_Id(getUserId());
        //发送记录详情
        if ("Submit_Flag".equals(flag) && StringUtils.isEmpty(reportExt.getChannel_Msg_Id())) {
            return new LayUiObjectMapper().asSuccessString(new SqlStatisticsEntity());
        }
        //提交记录详情
        if ("Input_Log_Flag".equals(flag) && StringUtils.isEmpty(reportExt.getMsg_Batch_No())) {
            return new LayUiObjectMapper().asSuccessString(new SqlStatisticsEntity());
        }
        SqlStatisticsEntity entity = sendManage.queryReportListTotalData(reportExt);
        return new LayUiObjectMapper().asSuccessString(entity);
    }

    /**
     * 发件箱表头统计  s_history input_log
     *
     * @param inputLogExt
     * @return
     */
    @RequestMapping("sended_queryInputLogListTotalData")
    @ResponseBody
    public String queryInputLogListTotalData(InputLogExt inputLogExt) {
        inputLogExt.setEnterprise_No(getLoginEnterprise().getNo());
        inputLogExt.setEnterprise_User_Id(getUserId());
        SqlStatisticsEntity sqlStatisticsEntity = sendManage.queryInputLogListTotalData(inputLogExt);
        return new LayUiObjectMapper().asSuccessString(sqlStatisticsEntity);
    }

    /**
     * 发送彩信前置
     *
     * @return
     */
    @RequestMapping("sended_preSendMMS")
    public ModelAndView preSendMMS() {
        ModelAndView mv = new ModelAndView("/sended/mms_send");
        ContactGroup condition = new ContactGroup();
        condition.setEnterprise_No(getLoginEnterprise().getNo());
        List<ContactGroup> contactGroupList = sendManage.queryContactGroupList(condition);
        String maxSize = DatabaseCache.getStringValueBySystemEnvAndCode("mms_template_max_size", "1945");
        mv.addObject("maxSize", maxSize);
        mv.addObject("contactGroupList", contactGroupList);
        return mv;
    }

    /**
     * 文件上传
     *
     * @return
     */
    @RequestMapping(value = "sended_uploadFile", method = RequestMethod.POST)
    @ResponseBody
    public LayUiJsonObjectFmt uploadFile(MultipartFile uploadFile) {
        try {
            Map<String, String> map = new HashMap<>();
            if (uploadFile != null) {
                map = UploadUtil.uploadFile(uploadFile, "mmsUpload");
                if (!"true".equals(map.get("status"))) {
                    return LayuiResultUtil.fail("上传失败");
                }
            }
            return LayuiResultUtil.success(map);
        } catch (Exception e) {
            SuperLogger.error(e.getMessage(), e);
            return LayuiResultUtil.fail(e.getMessage());
        }
    }

    /**
     * 发送彩信
     *
     * @return
     */
    @RequestMapping("sended_sendMMS")
    @ResponseBody
    @AvoidRepeatableCommitAnnotation(systemModuleName = ENTERPRISE_PLATFORM + "sended_sendMMS")
    public LayUiJsonObjectFmt sendMMS(InputExt input) {
        try {
            input.setMessage_Type_Code(MessageType.MMS.toString());//彩信
            input = assemblyInput(input);
            int count = this.sendManage.batchInputSms(input);
            return LayuiResultUtil.success(String.format("成功提交%1$s个号码", count));
        } catch (ServiceException e) {
            return LayuiResultUtil.fail(e.getMessage());
        } catch (Exception e) {
            SuperLogger.error(e);
            return LayuiResultUtil.error(e);
        }
    }

    /**
     * 发送视频彩信
     *
     * @return
     */
    @RequestMapping("sended_sendVideoMMS")
    @ResponseBody
    @AvoidRepeatableCommitAnnotation(systemModuleName = ENTERPRISE_PLATFORM + "sended_sendVideoMMS")
    public LayUiJsonObjectFmt sendVideoMMS(InputExt input) {
        try {
            input.setMessage_Type_Code(MessageType.VIDEO.toString());//视频
            input = assemblyInput(input);
            int count = this.sendManage.batchInputSms(input);
            return LayuiResultUtil.success(String.format("成功提交%1$s个号码", count));
        } catch (ServiceException e) {
            return LayuiResultUtil.fail(e.getMessage());
        } catch (Exception e) {
            SuperLogger.error(e.getMessage(), e);
            return LayuiResultUtil.error(e);
        }
    }

    /**
     * 短信管理 ==》未知记录列表
     */
    @RequestMapping("sended_enterpriseReportUnknownList")
    @ResponseBody
    public String reportUnknownList(SubmitExt submitExt) {
        try {
            submitExt.setEnterprise_No(getLoginEnterprise().getNo());
            submitExt.setEnterprise_User_Id(getUserId());
            List<SubmitExt> submitExtList = sendManage.querySubmitUnknownList(submitExt);
            return new SmsUIObjectMapper(isBlurPhoneNo()).asSuccessString(submitExtList, submitExt.getPagination());
        } catch (ServiceException se) {
            return new SmsUIObjectMapper().asFaildString(se.getMessage());
        } catch (Exception e) {
            SuperLogger.error(e.getMessage(), e);
            String s = e.getMessage();
            String substring = s.substring(s.length()-13);
            if(("doesn't exist").equals(substring)|(" table config").equals(substring)) {
                return new SmsUIObjectMapper().asFaildString("超出可查询时间");
            }
            return new SmsUIObjectMapper().asFaildString("查询异常");
        }
    }

    /**
     * 短信管理 ==》未知记录导出
     */
    @RequestMapping("sended_enterpriseExportSubmitReportUnknownList")
    @ResponseBody
    public LayUiJsonObjectFmt exportSubmitReportUnknownList(SubmitExt submitExt) {
        submitExt.setEnterprise_No(getLoginEnterprise().getNo());
        submitExt.setEnterprise_User_Id(getUserId());
        try {
            sendManage.exportSubmitReportUnknownList(
                    DatabaseCache.getCodeBySortCodeAndCode("system_env", "export_dir").getValue()
                    , submitExt, getEnterpriseDefaultExportFile(), Constant.PLATFORM_FLAG_ENTERPRISE);
            return LayuiResultUtil.success("已提交后台导出任务!");
        } catch (ServiceException e) {
            return LayuiResultUtil.fail(e.getMessage());
        } catch (Exception e) {
            SuperLogger.error(e.getMessage(), e);
            return LayuiResultUtil.error(e);
        }
    }

    /**
     * 导出客户短信模板
     */
    @RequestMapping("sended_exportSmsTemplateList")
    @ResponseBody
    public LayUiJsonObjectFmt exportSmsTemplateList(SmsTemplateExt smsTemplateExt) {
        smsTemplateExt.setEnterprise_No(getLoginEnterprise().getNo());
        smsTemplateExt.setEnterprise_User_Id(getLoginEnterpriseUser().getId());
        sendManage.exportSmsTemplate(DatabaseCache.getCodeBySortCodeAndCode("system_env", "export_dir").getValue(),
                smsTemplateExt, getEnterpriseDefaultExportFile());
        return new LayUiJsonObjectFmt(LayUiObjectMapper.CODE_SUCCESS, "已提交后台导出任务!");
    }

    @PostMapping(value = "/sended_uploadSendFile")
    @ResponseBody
    public LayUiJsonObjectFmt uploadSendFile(HttpServletRequest request) throws IOException {
        Map<String, String> map;
        try {
            map = this.sendManage.uploadSendFile(request, getLoginEnterpriseUser().getId());
        } catch (ServiceException se) {
            return LayuiResultUtil.fail(se.getMessage());
        } catch (Exception e) {
            SuperLogger.error(e.getMessage(), e);
            return LayuiResultUtil.fail("上传错误");
        }
        return LayuiResultUtil.success(map);
    }

    /**
     * 发送记录 》》》查看详情
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("sended_submitDetails")
    public ModelAndView submitDetails(SubmitExt submitExt) throws Exception {
        ModelAndView mv = new ModelAndView("/sended/submit_details");
        submitExt.setEnterprise_No(this.getLoginEnterprise().getNo());
        submitExt.setEnterprise_User_Id(getLoginEnterpriseUser().getId());
        Date date = DateTime.getDate(submitExt.getSubmit_Date_Str());
        submitExt.setMinSubmitDate(DateTime.getStartOfDay(date));
        submitExt.setMaxSubmitDate(DateTime.getEndOfDay(date));
        List<Submit> submitList = this.sendManage.querySubmitByPrimaryKey(submitExt);
        if (submitList.size() > 0) {
            Submit submit = submitList.get(0);
            submitExt.setChannel_Msg_Id(submit.getChannel_Msg_Id());
            submitExt.setMsg_Batch_No(submit.getMsg_Batch_No());
            List<Report> reportList = this.sendManage.queryReportListBySubmit(submitExt);
            List<ReportNotify> reportNotifyList = reportNotifyService.queryReportNotifyListBySubmit(submitExt);
            if(!isBlurPhoneNo()){
                submit.setPhone_No(BlurUtil.bathPhoneNoBlur(submit.getPhone_No()));
            }
            SubmitDetailsFacede submitDetailsFacede = new SubmitDetailsFacede(submit, reportList, reportNotifyList);
            mv.addObject("details", submitDetailsFacede);
        }
        return mv;
    }

}