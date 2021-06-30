package com.hero.wireless.web.action.admin;

import com.drondea.wireless.config.Constant;
import com.drondea.wireless.util.BlurUtil;
import com.drondea.wireless.util.DateTime;
import com.drondea.wireless.util.ServiceException;
import com.drondea.wireless.util.SuperLogger;
import com.hero.wireless.enums.ContentAuditStatus;
import com.hero.wireless.enums.MessageType;
import com.hero.wireless.enums.ReportStatus;
import com.hero.wireless.json.LayUiJsonObjectFmt;
import com.hero.wireless.json.LayUiObjectMapper;
import com.hero.wireless.json.LayuiResultUtil;
import com.hero.wireless.json.SmsUIObjectMapper;
import com.hero.wireless.web.action.AdminControllerBase;
import com.hero.wireless.web.action.BaseAdminController;
import com.hero.wireless.web.action.entity.BaseParamEntity;
import com.hero.wireless.web.action.interceptor.AvoidRepeatableCommitAnnotation;
import com.hero.wireless.web.action.interceptor.OperateAnnotation;
import com.hero.wireless.web.config.DatabaseCache;
import com.hero.wireless.web.entity.base.Pagination;
import com.hero.wireless.web.entity.business.EnterpriseUser;
import com.hero.wireless.web.entity.business.ExportFile;
import com.hero.wireless.web.entity.business.SmsTemplate;
import com.hero.wireless.web.entity.ext.SqlStatisticsEntity;
import com.hero.wireless.web.entity.send.*;
import com.hero.wireless.web.entity.send.ext.*;
import com.hero.wireless.web.service.IBusinessManage;
import com.hero.wireless.web.service.IEnterpriseManage;
import com.hero.wireless.web.service.IReportNotifyService;
import com.hero.wireless.web.service.ISendManage;
import com.hero.wireless.web.service.facede.SmsDataTrackingDetailsFacede;
import com.hero.wireless.web.service.facede.SubmitDetailsFacede;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/")
public class SendedController extends BaseAdminController {

    @Resource(name = "sendManage")
    private ISendManage sendManage;
    @Resource(name = "businessManage")
    private IBusinessManage businessManage;
    @Resource(name = "reportNotifyService")
    private IReportNotifyService reportNotifyService;
    @Resource(name = "enterpriseManage")
    private IEnterpriseManage enterpriseManage;

    @InitBinder
    public void initDateFormate(WebDataBinder dataBinder) {
        dataBinder.addCustomFormatter(new DateFormatter("yyyy-MM-dd HH:mm:ss"));
    }

    // 等待分拣短信
    @RequestMapping("sended_inputList")
    @ResponseBody
    public String inputList(InputExt inputExt) throws ParseException {
        inputExt.setAudit_Status_Code(ContentAuditStatus.PASSED.toString());
        inputExt.setFlag("wait_Sort");
        List<InputExt> inputExtList = sendManage.queryInputList(inputExt);
        return new SmsUIObjectMapper().asSuccessString(inputExtList, inputExt.getPagination());
    }

    /**
     * 修改短信前置
     *
     * @return
     */
    @RequestMapping("sended_preInputEdit")
    @ResponseBody
    public ModelAndView preInputEdit(BaseParamEntity entity) {
        InputExt inputExt = new InputExt();
        inputExt.setId(entity.getCkLongs().get(0));
        ModelAndView mv = new ModelAndView("/sended/input_edit");
        inputExt.setAudit_Status_Code(ContentAuditStatus.AUDITING.toString());
        List<InputExt> inputExts = sendManage.queryInputList(inputExt);
        if (inputExts != null && inputExts.size() > 0) {
            inputExt = inputExts.get(0);
        }
        inputExt.setEnterpriseUser(DatabaseCache.getEnterpriseUserById(inputExt.getEnterprise_User_Id()));
        mv.addObject("inputExt", inputExt);
        return mv;
    }

    @RequestMapping("sended_editInput")
    @ResponseBody
    @OperateAnnotation(moduleName = "短信管理", option = "修改短信")
    public LayUiJsonObjectFmt editInput(InputExt inputExt) {
        sendManage.editInput(inputExt);
        return LayuiResultUtil.success();
    }


    /**
     * 短信审核修改
     *
     * @return
     */
    @RequestMapping("sended_editAgreeInput")
    @ResponseBody
    public LayUiJsonObjectFmt editAgreeInput(InputExt inputExt) {
        inputExt.setAudit_Status_Code(ContentAuditStatus.AUDITING.toString());
        sendManage.editAgreeInput(inputExt);
        return LayuiResultUtil.success();
    }


    /**
     * 审核短信列表
     *
     * @return
     */
    @RequestMapping("sended_approveInputList")
    @ResponseBody
    public String approveInputList(InputExt inputExt) {
        inputExt.setAudit_Status_Code(ContentAuditStatus.AUDITING.toString());
        List<InputExt> inputExtList = sendManage.queryAuditingInputList(inputExt);
        return new SmsUIObjectMapper().asSuccessString(inputExtList, inputExt.getPagination());
    }

    /**
     * 审核同意
     *
     * @return
     */
    @RequestMapping("sended_agreeInput")
    @ResponseBody
    @OperateAnnotation(moduleName = "等待审核", option = "审核同意")
    public LayUiJsonObjectFmt agreeInput(BaseParamEntity entity) {
        return doMapToInput(entity, ContentAuditStatus.PASSED.toString());
    }

    /**
     * 审核拒绝
     *
     * @return
     */
    @RequestMapping("sended_objectInput")
    @ResponseBody
    @OperateAnnotation(moduleName = "等待审核", option = "审核拒绝")
    public LayUiJsonObjectFmt objectInput(BaseParamEntity entity) {
        return doMapToInput(entity, ContentAuditStatus.REJECT.toString());
    }

    //审核短信
    private LayUiJsonObjectFmt doMapToInput(BaseParamEntity entity, String status) {
        try {
            List<String> ckStrings = entity.getCkStrings();
            List<InputExt> inputExtList = new ArrayList<>();
            ckStrings.forEach(key -> {
                InputExt inputExt = new InputExt();
                inputExt.setAuditFlag("1");
                inputExt.setAssist_Audit_Key(key);
                inputExt.setAudit_Admin_User_Id(getUserId());
                inputExt.setAudit_Date(new Date());
                inputExtList.add(inputExt);
            });
            this.sendManage.approveSMS(status, inputExtList);
            return LayuiResultUtil.success();
        } catch (ServiceException se) {
            SuperLogger.error(se.getMessage(), se);
            return LayuiResultUtil.fail(se.getMessage());
        } catch (Exception e) {
            SuperLogger.error(e.getMessage(), e);
            return LayuiResultUtil.error(e);
        }
    }

    /**
     * 查询待审核短信条数
     *
     * @return
     */
    @RequestMapping("sended_auditingInputCount")
    @ResponseBody
    public int auditingInputCount(InputExt inputExt) {
        inputExt.setAudit_Status_Code(ContentAuditStatus.AUDITING.toString());
        return sendManage.queryAuditingInputCount(inputExt);
    }

    /**
     *
     * 批量审核短信
     *
     * @return
     */
    @RequestMapping("sended_batchApproveInput")
    @ResponseBody
    @OperateAnnotation(moduleName = "等待审核", option = "批量审核")
    @AvoidRepeatableCommitAnnotation(systemModuleName = ADMIN_PLATFORM+"product:sended_batchApproveInput")
    public LayUiJsonObjectFmt batchApproveInput(InputExt inputExt) {
        try {
            List<InputExt> inputExtList = new ArrayList<>();
            inputExt.setAuditFlag("2");//1普通审核  2批量审核
            inputExt.setDescription(getLoginUser().getReal_Name());
            inputExtList.add(inputExt);
            this.sendManage.approveSMS(inputExt.getAudit_Status_Code(), inputExtList);
            return LayuiResultUtil.success();
        } catch (ServiceException e) {
            return LayuiResultUtil.fail(e.getMessage());
        } catch (Exception e) {
            SuperLogger.error(e.getMessage(), e);
            return LayuiResultUtil.error(e);
        }
    }

    // 短信发件箱前置
    @RequestMapping("sended_preInputLogList")
    public ModelAndView preInputLogList() {
        ModelAndView mv = new ModelAndView("/sended/input_log_list");
        String  interval = DatabaseCache.getStringValueBySortCodeAndCode("page_configuration", "page_search_interval", "");
        if (StringUtils.isNotBlank(interval)) {
            mv.addObject("minSubmitDate", DateTime.getCurentTimeBeforeMinutes(Integer.valueOf(interval)));
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



    // 短信发件箱
    @RequestMapping("sended_inputLogList")
    @ResponseBody
    public String inputLogList(InputLogExt inputLogExt) {
      try {
        inputLogExt.setBusiness_User_Id(AdminControllerBase.currentBusinessUserId());
        List<InputLogExt> inputLogExtList = sendManage.queryInputLogList(inputLogExt);
        return new SmsUIObjectMapper(AdminControllerBase.isBlurPhoneNo()).asSuccessString(inputLogExtList,
            inputLogExt.getPagination());
    } catch (Exception e) {
            String s = e.getMessage();
            String substring = s.substring(s.length()-13);
           if(("doesn't exist").equals(substring)) {
               return new SmsUIObjectMapper().asFaildString("超出可查询时间");
           }
            return new SmsUIObjectMapper().asFaildString(e.getMessage());
    }
    }

    // 发送记录列表统计
    @RequestMapping("sended_querySubmitedListTotalData")
    @ResponseBody
    public String querySubmitedListTotalData(SubmitExt submitedExt) {
        submitedExt.setBusiness_User_Id(AdminControllerBase.currentBusinessUserId());
        SqlStatisticsEntity sqlStatisticsEntity = sendManage.querySubmitedListTotalData(submitedExt);
        return new SmsUIObjectMapper().asSuccessString(sqlStatisticsEntity);
    }

    // 发件箱数据统计
    @RequestMapping("sended_queryInputLogListTotalData")
    @ResponseBody
    public String queryInputLogListTotalData(InputLogExt inputLogExt) {
        try {
            inputLogExt.setBusiness_User_Id(AdminControllerBase.currentBusinessUserId());
            SqlStatisticsEntity entity = sendManage.queryInputLogListTotalData(inputLogExt);
            return new SmsUIObjectMapper().asSuccessString(entity);
        } catch (Exception e) {
            e.printStackTrace();
            return new SmsUIObjectMapper().asFaildString(e.getMessage());
        }
    }

    // 发件箱:查询重发条数
    @RequestMapping("sended_queryInputLogList")
    @ResponseBody
    public String sended_queryInputLogList(InputLogExt inputLogExt) {
        SqlStatisticsEntity entity = sendManage.queryInputLogListTotalData(inputLogExt);
        int maxResend = DatabaseCache.getIntValueBySortCodeAndCode("sys_performance_setup", "max_resend_total", 20000);
        if (entity != null && entity.getCount() > maxResend) {
            return new LayUiObjectMapper().asFaildString("dataTooLarge", entity.getCount());
        }
        return new LayUiObjectMapper().asSuccessString(entity == null ? 0 : entity.getCount());
    }

    // 发件箱:批量重发
    @RequestMapping("sended_resendInputLog")
    @ResponseBody
    @OperateAnnotation(moduleName = "发件箱", option = "批量重发")
    @AvoidRepeatableCommitAnnotation(systemModuleName = ADMIN_PLATFORM + "sended_resendInputLog")
    public LayUiJsonObjectFmt resendInputLog(InputLogExt inputLogExt, String subCode, String enterpriseNo, Integer enterpriseUserId) {
        try {
            inputLogExt.setMessage_Type_Code(MessageType.SMS.toString());
            sendManage.resendInputLog(inputLogExt, subCode, enterpriseNo, enterpriseUserId);//bean的sub_Code查询用 填写subCode
        } catch (Exception e) {
            SuperLogger.error(e.getMessage(), e);
            return LayuiResultUtil.fail("补发失败：" + e.getMessage());
        }
        return LayuiResultUtil.success();
    }

    @RequestMapping("sended_inputLogDesc")
    public String inputLogDesc(InputLogExt inputLogExt) {
        inputLogList(inputLogExt);
        return "/sended/input_log_desc";
    }

    /**
     * 导出发件箱
     *
     * @return
     */
    @RequestMapping("sended_exportInputLogList")
    @ResponseBody
    public LayUiJsonObjectFmt exportInputLogList(InputLogExt inputLogExt) throws Exception {
        try {
            inputLogExt.setBusiness_User_Id(AdminControllerBase.currentBusinessUserId());
            sendManage.exportInputLog(DatabaseCache.getCodeBySortCodeAndCode("system_env", "export_dir").getValue(),
                    inputLogExt, getAdminDefaultExportFile());
            return new LayUiJsonObjectFmt(SmsUIObjectMapper.CODE_SUCCESS, "已提交后台导出任务!");
        } catch (ServiceException e) {
            return LayuiResultUtil.fail(e.getMessage());
        } catch (Exception e) {
            SuperLogger.error(e.getMessage(), e);
            return LayuiResultUtil.error(e);
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
        List<Inbox> inboxList = sendManage.queryInboxList(inbox);
        return new SmsUIObjectMapper(AdminControllerBase.isBlurPhoneNo()).asSuccessString(inboxList, inbox.getPagination());
    }

    /**
     * 导出收件箱
     */
    @RequestMapping("sended_exportInboxList")
    @ResponseBody
    public LayUiJsonObjectFmt exportInboxList(InboxExt inboxExt) throws Exception {
        try {
            sendManage.exportInboxList(DatabaseCache.getCodeBySortCodeAndCode("system_env", "export_dir").getValue(),
                    inboxExt, getAdminDefaultExportFile(), Constant.PLATFORM_FLAG_ADMIN);
            return new LayUiJsonObjectFmt(SmsUIObjectMapper.CODE_SUCCESS, "已提交后台导出任务!");
        } catch (ServiceException e) {
            return LayuiResultUtil.fail(e.getMessage());
        } catch (Exception e) {
            SuperLogger.error(e.getMessage(), e);
            return LayuiResultUtil.error(e);
        }
    }

    @RequestMapping("sended_exportFileList")
    @ResponseBody
    public String exportFileList(ExportFile exportFile) {
        exportFile.setEnterprise_No("0");
        exportFile.setAgent_No("0");
        List<ExportFile> exportFileList = sendManage.queryExportFile(exportFile);
        return new SmsUIObjectMapper().asSuccessString(exportFileList, exportFile.getPagination());
    }

    // 等待分拣短信 导出
    @RequestMapping("sended_exportInputList")
    @ResponseBody
    public LayUiJsonObjectFmt exportInputList(InputExt inputExt) {
        try {
            inputExt.setAudit_Status_Code(ContentAuditStatus.PASSED.toString());
            inputExt.setFlag("wait_Sort");
            sendManage.exportInput(DatabaseCache.getCodeBySortCodeAndCode("system_env", "export_dir").getValue(), inputExt,
                    getAdminDefaultExportFile());
            return new LayUiJsonObjectFmt(SmsUIObjectMapper.CODE_SUCCESS, "已提交后台导出任务!");
        } catch (ServiceException e) {
            return LayuiResultUtil.fail(e.getMessage());
        } catch (Exception e) {
            SuperLogger.error(e.getMessage(), e);
            return LayuiResultUtil.error(e);
        }
    }

    // 统一发送记录
    @RequestMapping("sended_submitList")
    @ResponseBody
    public String submitList(SubmitExt submitExt) {
        try {
            submitExt.setMaxSubmitDate(DateTime.getTimeForMaxMillisecond(submitExt.getMaxSubmitDate()));
            submitExt.setBusiness_User_Id(AdminControllerBase.currentBusinessUserId());
            List<SubmitExt> submitExtList = sendManage.querySubmitListSharding(submitExt);
            return new SmsUIObjectMapper(AdminControllerBase.isBlurPhoneNo()).asSuccessString(submitExtList, submitExt);
        } catch (Exception e) {
            e.printStackTrace();
            String s = e.getMessage();
            String substring = s.substring(s.length()-13);
            if(("doesn't exist").equals(substring)) {
                return new SmsUIObjectMapper().asFaildString("超出可查询时间");
            }
            return new SmsUIObjectMapper().asFaildString(e.getMessage());
        }
    }


    @RequestMapping("sended_submitAwaitList")
    @ResponseBody
    public String submitAwaitList(SubmitAwaitExt submitAwaitExt) {
        try {
            submitAwaitExt.setMaxCreateDate(DateTime.getTimeForMaxMillisecond(submitAwaitExt.getMaxCreateDate()));
            submitAwaitExt.setBusiness_User_Id(AdminControllerBase.currentBusinessUserId());
            List<SubmitAwait> submitExtList = sendManage.querySubmitAwaitList(submitAwaitExt);
            return new SmsUIObjectMapper(AdminControllerBase.isBlurPhoneNo()).asSuccessString(submitExtList, submitAwaitExt);
        } catch (Exception e) {
            e.printStackTrace();
            String s = e.getMessage();
            String substring = s.substring(s.length()-13);
            if(("doesn't exist").equals(substring)) {
                return new SmsUIObjectMapper().asFaildString("超出可查询时间");
            }
            return new SmsUIObjectMapper().asFaildString(e.getMessage());
        }
    }

    // 发送记录列表统计
    @RequestMapping("sended_querySubmitListTotalData")
    @ResponseBody
    public String querySubmitListTotalData(SubmitExt submitExt) {
        try {
            submitExt.setBusiness_User_Id(AdminControllerBase.currentBusinessUserId());
            SqlStatisticsEntity sqlStatisticsEntity = sendManage.querySubmitListTotalData(submitExt);
            return new SmsUIObjectMapper().asSuccessString(sqlStatisticsEntity);
        } catch (Exception e) {
            e.printStackTrace();
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
        request.setAttribute("channel_Master_Msg_No", "undefined".equals(channel_Master_Msg_No) ? "" : channel_Master_Msg_No);
        request.setAttribute("msg_Batch_No", "undefined".equals(msg_Batch_No) ? "" : msg_Batch_No);
        request.setAttribute("limitCode", limitCode);
        request.setAttribute("flag", flag);
        if (!StringUtils.isEmpty(minCreateDate) && minCreateDate.length() > 10) {
            minCreateDate = minCreateDate.substring(0, 10) + " 00:00:00";
            String maxCreateDate = minCreateDate.substring(0, 10) + " 23:59:59";
            request.setAttribute("minSubmitDate", minCreateDate);
            request.setAttribute("maxSubmitDate", maxCreateDate);
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
        try{
        reportExt.setBusiness_User_Id(AdminControllerBase.currentBusinessUserId());
        reportExt.setMaxSubmitDate(DateTime.getTimeForMaxMillisecond(reportExt.getMaxSubmitDate()));
        //发送记录详情
        if ("Submit_Flag".equals(flag) && StringUtils.isEmpty(reportExt.getChannel_Msg_Id())) {
            return new SmsUIObjectMapper().asSuccessString(null, reportExt.getPagination());
        }
        //提交记录详情
        if ("Input_Log_Flag".equals(flag) && StringUtils.isEmpty(reportExt.getMsg_Batch_No())) {
            return new SmsUIObjectMapper().asSuccessString(null, reportExt.getPagination());
        }
        reportExt.setMaxSubmitDate(DateTime.getTimeForMaxMillisecond(reportExt.getMaxSubmitDate()));
        List<ReportExt> reportExtList = sendManage.queryReportListSharding(reportExt);
        return new SmsUIObjectMapper(AdminControllerBase.isBlurPhoneNo()).asSuccessString(reportExtList, reportExt);
        } catch (Exception e) {
            String s = e.getMessage();
            String substring = s.substring(s.length()-13);
            if(("doesn't exist").equals(substring)) {
                return new SmsUIObjectMapper().asFaildString("超出可查询时间");
            }
            return new SmsUIObjectMapper().asFaildString(e.getMessage());
        }
    }

    @RequestMapping("sended_reportNotifyAwaitList")
    @ResponseBody
    public String reportNotifyAwaitList(ReportNotifyAwaitExt reportNotifyAwaitExt) {
        try{
            reportNotifyAwaitExt.setBusiness_User_Id(AdminControllerBase.currentBusinessUserId());
            reportNotifyAwaitExt.setMaxSubmitDate(DateTime.getTimeForMaxMillisecond(reportNotifyAwaitExt.getMaxSubmitDate()));
            reportNotifyAwaitExt.setMaxSubmitDate(DateTime.getTimeForMaxMillisecond(reportNotifyAwaitExt.getMaxSubmitDate()));
            List<ReportNotifyAwait> reportExtList = sendManage.queryReportNotifyAwaitList(reportNotifyAwaitExt);
            return new SmsUIObjectMapper(AdminControllerBase.isBlurPhoneNo()).asSuccessString(reportExtList, reportNotifyAwaitExt);
        } catch (Exception e) {
            String s = e.getMessage();
            String substring = s.substring(s.length()-13);
            if(("doesn't exist").equals(substring)) {
                return new SmsUIObjectMapper().asFaildString("超出可查询时间");
            }
            return new SmsUIObjectMapper().asFaildString(e.getMessage());
        }
    }

    //发送回执数据统计
    @RequestMapping("sended_queryReportListTotalData")
    @ResponseBody
    public String queryReportListTotalData(ReportExt reportExt) {
        reportExt.setBusiness_User_Id(AdminControllerBase.currentBusinessUserId());
        SqlStatisticsEntity sqlStatisticsEntity = sendManage.queryReportListTotalData(reportExt);
        return new SmsUIObjectMapper().asSuccessString(sqlStatisticsEntity);
    }

    // 发送回执:查询重推条数
    @RequestMapping("sended_queryRepushReportList")
    @ResponseBody
    public String queryRepushReportList(ReportExt reportExt) {
        reportExt.setBusiness_User_Id(AdminControllerBase.currentBusinessUserId());
        SqlStatisticsEntity entity = sendManage.queryReportListTotalData(reportExt);
        return new LayUiObjectMapper().asSuccessString(entity == null ? 0 : entity.getCount());
    }

    // 短信重推状态报告
    @RequestMapping("sended_repushSmsReport")
    @ResponseBody
    @OperateAnnotation(moduleName = "发送回执", option = "批量补推")
    @AvoidRepeatableCommitAnnotation(systemModuleName = ADMIN_PLATFORM + "sended_repushSmsReport")
    public LayUiJsonObjectFmt repushSmsReport(ReportExt reportExt) {
        try {
            reportExt.setBusiness_User_Id(AdminControllerBase.currentBusinessUserId());
            sendManage.repushSmsReport(reportExt);
        } catch (Exception e) {
            SuperLogger.error(e.getMessage(), e);
            return LayuiResultUtil.fail("发送失败：" + e.getMessage());
        }
        return LayuiResultUtil.success();
    }

    /**
     * 生成上行
     * @param entity
     * @return
     */
    @RequestMapping("sended_manualSendMO")
    @ResponseBody
    @OperateAnnotation(moduleName = "发送回执", option = "生成上行")
    @AvoidRepeatableCommitAnnotation(systemModuleName = ADMIN_PLATFORM + "sended_manualSendMO")
    public LayUiJsonObjectFmt manualSendMO(BaseParamEntity entity, ReportExt reportExt) {
        try {
            reportExt.setStatus_Code(ReportStatus.SUCCESS.toString());
            sendManage.manualSendMO(reportExt, entity.getCkLongs(), entity.getCkStrings().get(0));
        } catch (Exception e) {
            SuperLogger.error(e.getMessage(), e);
            return LayuiResultUtil.fail("生成上行失败：" + e.getMessage());
        }
        return LayuiResultUtil.success();
    }

    /**
     * 重推MO通知
     *
     * @param entity
     * @return
     */
    @RequestMapping("sended_repushSmsMo")
    @ResponseBody
    @OperateAnnotation(moduleName = "收件箱", option = "收件箱批量补推")
    public LayUiJsonObjectFmt repushSmsMo(BaseParamEntity entity) {
        try {
            List<Integer> ckIds = entity.getCkIds();
            sendManage.repushSmsMo(ckIds);
        } catch (Exception e) {
            return LayuiResultUtil.fail("发送失败：" + e.getMessage());
        }
        return LayuiResultUtil.success();
    }

    /**
     * 重发信条数查询
     *
     * @param submitExt
     * @return
     */
    @RequestMapping("sended_queryResendSmsCount")
    @ResponseBody
    public String queryResendSmsCount(SubmitExt submitExt) {
        SqlStatisticsEntity entity = sendManage.querySubmitListTotalData(submitExt);
        int maxResend = DatabaseCache.getIntValueBySortCodeAndCode("sys_performance_setup", "max_resend_total", 20000);
        if (entity != null && entity.getCount() > maxResend) {
            return new LayUiObjectMapper().asFaildString("dataTooLarge", entity.getCount());
        }
        return new LayUiObjectMapper().asSuccessString(entity == null ? 0 : entity.getCount());
    }

    /**
     * 短信重发
     *
     * @param submitExt
     * @return
     */
    @RequestMapping("sended_resendSms")
    @ResponseBody
    @OperateAnnotation(moduleName = "发送记录", option = "重发短信")
    @AvoidRepeatableCommitAnnotation(systemModuleName = ADMIN_PLATFORM + "sended_resendSms")
    public LayUiJsonObjectFmt resendSms(SubmitExt submitExt, String subCode, String enterpriseNo, Integer enterpriseUserId) {
        try {
            sendManage.resendSms(submitExt, subCode, enterpriseNo, enterpriseUserId);//bean的sub_Code查询用 填写subCode
        } catch (Exception e) {
            SuperLogger.error(e.getMessage(), e);
            return LayuiResultUtil.fail("补发失败：" + e.getMessage());
        }
        return LayuiResultUtil.success();
    }

    /**
     * 导出发送记录
     *
     * @param submitExt
     * @return
     */
    @RequestMapping("sended_exportSubmitList")
    @ResponseBody
    public LayUiJsonObjectFmt exportSubmitList(SubmitExt submitExt) {
        submitExt.setBusiness_User_Id(AdminControllerBase.currentBusinessUserId());
        try {
            sendManage.exportSubmit(DatabaseCache.getCodeBySortCodeAndCode("system_env", "export_dir").getValue(),
                    submitExt, getAdminDefaultExportFile(), Constant.PLATFORM_FLAG_ADMIN);
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
     *
     * @param reportExt
     * @return
     */
    @RequestMapping("sended_exportReportList")
    @ResponseBody
    public LayUiJsonObjectFmt exportReportList(ReportExt reportExt) {
        try {
            reportExt.setBusiness_User_Id(AdminControllerBase.currentBusinessUserId());
            if ("null".equals(reportExt.getChannel_Msg_Id()) || "".equals(reportExt.getChannel_Msg_Id())) {
                reportExt.setChannel_Msg_Id(null);
            }
            sendManage.exportReport(DatabaseCache.getCodeBySortCodeAndCode("system_env", "export_dir").getValue(),
                    reportExt, getAdminDefaultExportFile(), Constant.PLATFORM_FLAG_ADMIN);
            return new LayUiJsonObjectFmt(LayUiObjectMapper.CODE_SUCCESS, "已提交后台导出任务!");
        } catch (ServiceException e) {
            return LayuiResultUtil.fail(e.getMessage());
        } catch (Exception e) {
            SuperLogger.error(e.getMessage(), e);
            return LayuiResultUtil.error(e);
        }
    }

    @RequestMapping("sended_deleteInputList")
    @ResponseBody
    @OperateAnnotation(moduleName = "等待分拣", option = "取消分拣")
    public LayUiJsonObjectFmt deleteInputList(@RequestParam(name = "ckIds") List<Long> ckIds) {
        sendManage.deleteInputById(ckIds, new EnterpriseUser());
        return LayuiResultUtil.success();
    }

    /**
     * 文件下载
     **/
    @RequestMapping("sended_downloadFile")
    @ResponseBody
    public void downloadFile(Integer id) {
        try {
            if (id == null) {
                return;
            }
            ExportFile exportFile = getAdminDefaultExportFile();
            exportFile.setUser_Id(null);//不再限制用户id
            exportFile.setId(id);
            sendManage.downloadFile(response, exportFile);
            return;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 定时短信
     */
    @RequestMapping("sms_sentTime")
    @ResponseBody
    public String queryInputList(InputExt inputExt) {
        List<Input> list = sendManage.queryTimerInputList(inputExt);
        return new SmsUIObjectMapper(AdminControllerBase.isBlurPhoneNo()).asSuccessString(list, inputExt.getPagination());
    }

    /**
     * 取消定时
     *
     * @param
     * @return
     */
    @RequestMapping("delete_InputTimeList")
    @ResponseBody
    @OperateAnnotation(moduleName = "定时短信", option = "取消定时")
    public LayUiJsonObjectFmt deleteInputTimeList(@RequestParam(name = "ckIds") List<Long> ckIds) {
        sendManage.deleteInputById(ckIds, new EnterpriseUser());
        return LayuiResultUtil.success();
    }

    /**
     * 短信管理 ==》推送记录列表
     */
    @RequestMapping("sended_reportNotifyList")
    @ResponseBody
    public String reportNotifyList(ReportNotifyExt reportNotifyExt) {
        try {
        reportNotifyExt.setBusiness_User_Id(AdminControllerBase.currentBusinessUserId());
        reportNotifyExt.setMaxSubmitDate(DateTime.getTimeForMaxMillisecond(reportNotifyExt.getMaxSubmitDate()));
        List<ReportNotify> reportNotifyList = sendManage.queryReportNotifyListSharding(reportNotifyExt);
        if(ObjectUtils.isEmpty(reportNotifyList)){
            return new SmsUIObjectMapper().asSuccessString(reportNotifyList, reportNotifyExt.getPagination());
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        List<ReportNotifyExt> list = reportNotifyList.stream().map(item->{
            ReportNotifyExt reportNotify = new ReportNotifyExt();
            BeanUtils.copyProperties(item, reportNotify);
            if(item.getNotify_Submit_Date()!=null){
                reportNotify.setNotifySubmitDate(sdf.format(item.getNotify_Submit_Date()));
            }
            if(item.getNotify_Response_Date()!=null){
                reportNotify.setNotifyResponseDate(sdf.format(item.getNotify_Response_Date()));
            }
            return reportNotify;
        }).collect(Collectors.toList());
        return new SmsUIObjectMapper(AdminControllerBase.isBlurPhoneNo()).asSuccessString(list, reportNotifyExt.getPagination());
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
     * 短信管理 ==》未知记录列表
     */
    @RequestMapping("sended_reportUnknownList")
    @ResponseBody
    public String reportUnknownList(SubmitExt submitExt) {
        try {
            List<SubmitExt> submitExtList = sendManage.querySubmitUnknownList(submitExt);
            return new SmsUIObjectMapper(AdminControllerBase.isBlurPhoneNo()).asSuccessString(submitExtList, submitExt);
        } catch (ServiceException e) {
            return new SmsUIObjectMapper().asFaildString(e.getMessage());
        } catch (Exception e) {
            String s = e.getMessage();
            String substring = s.substring(s.length()-13);
            SuperLogger.error(e.getMessage(), e);
            if(("doesn't exist").equals(substring)|(" table config").equals(substring)) {
                return new SmsUIObjectMapper().asFaildString("超出可查询时间");
            }
            return new SmsUIObjectMapper().asErrorString();
        }
    }

    /**
     * 短信管理 ==》未知记录导出
     */
    @RequestMapping("sended_exportSubmitReportUnknownList")
    @ResponseBody
    public LayUiJsonObjectFmt exportSubmitReportUnknownList(SubmitExt submitExt) {
        try {
            sendManage.exportSubmitReportUnknownList(
                    DatabaseCache.getCodeBySortCodeAndCode("system_env", "export_dir").getValue()
                    , submitExt, getAdminDefaultExportFile(), Constant.PLATFORM_FLAG_ADMIN);
            return LayuiResultUtil.success("已提交后台导出任务!");
        } catch (ServiceException e) {
            return LayuiResultUtil.fail(e.getMessage());
        } catch (Exception e) {
            SuperLogger.error(e.getMessage(), e);
            return LayuiResultUtil.error(e);
        }
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
        Date date = DateTime.getDate(submitExt.getSubmit_Date_Str());
        submitExt.setMinSubmitDate(DateTime.getStartOfDay(date));
        submitExt.setMaxSubmitDate(DateTime.getEndOfDay(date));
        List<Submit> submitList = this.sendManage.querySubmitByPrimaryKey(submitExt);
        if (submitList.size() > 0) {
            Submit submit = submitList.get(0);
            submitExt.setPhone_No(submit.getPhone_No());
            submitExt.setChannel_Msg_Id(submit.getChannel_Msg_Id());
            submitExt.setMsg_Batch_No(submit.getMsg_Batch_No());
            List<Report> reportList = this.sendManage.queryReportListBySubmit(submitExt);
            List<ReportNotify> reportNotifyList = reportNotifyService.queryReportNotifyListBySubmit(submitExt);
            if(!AdminControllerBase.isBlurPhoneNo()){
                submit.setPhone_No(BlurUtil.bathPhoneNoBlur(submit.getPhone_No()));
            }
            SubmitDetailsFacede submitDetailsFacede = new SubmitDetailsFacede(submit, reportList, reportNotifyList);
            mv.addObject("details", submitDetailsFacede);
        }

        return mv;
    }
    /**
     * 数据跟踪》》》查看详情
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("sms_data_tracking")
    public ModelAndView smsDataTracking(String phone_No,Date minCreateDate,Date maxCreateDate) throws Exception {
        try {
        ModelAndView mv = new ModelAndView("/sended/data_tracking_details");
        InputLogExt inputLogExt = new InputLogExt();
        inputLogExt.setPhone_Nos(phone_No);
        inputLogExt.setMinCreateDate(minCreateDate);
        inputLogExt.setMaxCreateDate(DateTime.getTimeForMaxMillisecond(maxCreateDate));
        inputLogExt.setPagination(new Pagination());
        List<InputLogExt> inputLogList = sendManage.queryInputLogListSharding(inputLogExt);

        SubmitExt submitExt = new SubmitExt();
        submitExt.setMinSubmitDate(minCreateDate);
        submitExt.setMaxSubmitDate(DateTime.getTimeForMaxMillisecond(maxCreateDate));
        submitExt.setPhone_No(phone_No);
        submitExt.setPagination(new Pagination());
        List<SubmitExt> submitList = this.sendManage.querySubmitListSharding(submitExt);

        ReportExt reportExt = new ReportExt();
        reportExt.setPhone_No(phone_No);
        reportExt.setMinSubmitDate(minCreateDate);
        reportExt.setMaxSubmitDate(DateTime.getTimeForMaxMillisecond(maxCreateDate));
        reportExt.setPagination(new Pagination());
        List<ReportExt> reportList = this.sendManage.queryReportListSharding(reportExt);

        ReportNotifyExt reportNotifyExt = new ReportNotifyExt();
        reportNotifyExt.setPhone_No(phone_No);
        reportNotifyExt.setMinSubmitDate(minCreateDate);
        reportNotifyExt.setMaxSubmitDate(DateTime.getTimeForMaxMillisecond(maxCreateDate));
        reportNotifyExt.setPagination(new Pagination());
        List<ReportNotify> reportNotifyList = this.sendManage.queryReportNotifyListSharding(reportNotifyExt);
        mv.addObject("details", new SmsDataTrackingDetailsFacede(inputLogList,submitList,reportList,reportNotifyList));
            return mv;
    } catch (Exception e) {
        String s = e.getMessage();
        String substring = s.substring(s.length()-13);
            System.out.println("<<<<<<<<<<"+e.getMessage()+">>>>>>>>>>>>>");
        if(("doesn't exist").equals(substring)) {
            return new ModelAndView("/sended/error");
        }
        return null;
    }
    }

    /**
    * 等待审核创建模板前置
    *
    * @return
    */
    @RequestMapping("sended_preAddTemplateByInput")
    public ModelAndView preAddTemplateByInput(BaseParamEntity entity) {
        ModelAndView mv = new ModelAndView("/sended/sms_input_add_template");
        InputExt inputExt = new InputExt();
        inputExt.setAssist_Audit_Key(entity.getCkStrings().get(0));
        List<InputExt> inputExts = sendManage.queryInputList(inputExt);
        if (inputExts != null && inputExts.size() > 0) {
            inputExt = inputExts.get(0);
        }
        mv.addObject("inputExt", inputExt);
        return mv;
    }

    @RequestMapping("enterprise_addSmsTemplateByInput")
    @ResponseBody
    @OperateAnnotation(moduleName = "短信审核", option = "生成模板")
    @AvoidRepeatableCommitAnnotation(systemModuleName = ADMIN_PLATFORM + "addSmsTemplateByInput")
    public LayUiJsonObjectFmt addSmsTemplateByInput(SmsTemplate smsTemplate) {
        try {
            smsTemplate.setApprove_Status(Constant.SMS_TEMPLAT_CHECK_STATUS_PASS);
            this.enterpriseManage.addSmsTemplate(smsTemplate);
        } catch (ServiceException e) {
            return LayuiResultUtil.fail(e.getMessage());
        } catch (Exception e) {
            return LayuiResultUtil.fail(e.getMessage());
        }
        return LayuiResultUtil.success();
    }

    /**
     * 告警记录 ==》告警详情列表
     */
    @RequestMapping("sended_alarmLogDetailList")
    @ResponseBody
    public String alarmLogDetailList(String bingValveType, String bindValue
            , String beginDate, String endDate, String alarmType, Pagination pagination) {
        try {
            return sendManage.queryAlarmLogDetailList(bingValveType,bindValue,beginDate,endDate,alarmType,pagination);
        } catch (ServiceException e) {
            return new SmsUIObjectMapper().asFaildString(e.getMessage());
        } catch (Exception e) {
            SuperLogger.error(e.getMessage(), e);
            return new SmsUIObjectMapper().asErrorString();
        }
    }
}
