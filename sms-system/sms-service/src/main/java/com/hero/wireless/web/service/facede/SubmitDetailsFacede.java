package com.hero.wireless.web.service.facede;

import com.drondea.wireless.util.DateTime;
import com.hero.wireless.web.config.DatabaseCache;
import com.hero.wireless.web.entity.business.Channel;
import com.hero.wireless.web.entity.business.Code;
import com.hero.wireless.web.entity.send.Report;
import com.hero.wireless.web.entity.send.ReportNotify;
import com.hero.wireless.web.entity.send.Submit;
import org.apache.commons.lang3.ObjectUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SubmitDetailsFacede {

    private Submit submit;
    private List<Report> reportList;
    private List<ReportNotify> notifyList;

    private String enterpriseName;
    private String enterpriseUserName;
    private String countryName;
    private String messageTypeName;
    private String isLMSName;
    private Long submitResponseTime;
    private String submiteStatusName;

    private List<Map<String, String>> reportStatusInfo;
    private List<Map<String, String>> reportNotifyStatusInfo;


    public SubmitDetailsFacede(Submit submit, List<Report> reportList, List<ReportNotify> notifyList){
        this.submit= submit;
        this.reportList= reportList;
        this.notifyList= notifyList;
    }

    public Submit getSubmit() {
        return submit;
    }

    public void setSubmit(Submit submit) {
        this.submit = submit;
    }

    public List<Report> getReportList() {
        return reportList;
    }

    public void setReportList(List<Report> reportList) {
        this.reportList = reportList;
    }

    public List<ReportNotify> getNotifyList() {
        return notifyList;
    }

    public void setNotifyList(List<ReportNotify> notifyList) {
        this.notifyList = notifyList;
    }

    public String getEnterpriseName() {
        return DatabaseCache.getEnterpriseTranslateByNo(this.submit.getEnterprise_No()).getName();
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    public String getEnterpriseUserName() {
        return DatabaseCache.getEnterpriseUserTranslateById(this.submit.getEnterprise_User_Id()).getUser_Name();
    }

    public void setEnterpriseUserName(String enterpriseUserName) {
        this.enterpriseUserName = enterpriseUserName;
    }

    public String getCountryName() {
        return DatabaseCache.getCodeNameBySortCodeAndCodeNotNull("country", this.submit.getCountry_Number());
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getMessageTypeName() {
        return DatabaseCache.getCodeNameBySortCodeAndCodeNotNull("message_type_code", this.submit.getMessage_Type_Code());
    }

    public void setMessageTypeName(String messageTypeName) {
        this.messageTypeName = messageTypeName;
    }

    public String getIsLMSName() {
        return DatabaseCache.getCodeNameBySortCodeAndCodeNotNull("006", this.submit.getIs_LMS()+"");
    }

    public void setIsLMSName(String isLMSName) {
        this.isLMSName = isLMSName;
    }

    public Long getSubmitResponseTime() {
        if(submit.getSubmit_Date() == null || submit.getSubmit_Response_Date() == null)
            return null;
        return submit.getSubmit_Response_Date().getTime() - submit.getSubmit_Date().getTime();
    }

    public void setSubmitResponseTime(Long submitResponseTime) {
        this.submitResponseTime = submitResponseTime;
    }

    public String getSubmiteStatusName() {
        return DatabaseCache.getCodeNameBySortCodeAndCodeNotNull("034", this.submit.getSubmit_Status_Code());
    }

    public void setSubmiteStatusName(String submiteStatusName) {
        this.submiteStatusName = submiteStatusName;
    }

    public List<Map<String, String>> getReportStatusInfo() {
        List<Map<String, String>> mapList = new ArrayList<Map<String, String>>();
        if (ObjectUtils.isNotEmpty(reportList)){
            for (Report report: reportList){
                Map<String, String> map = new HashMap<String,String>();
                map.put("statusDate", DateTime.getString(report.getStatus_Date()));
                Code codeObject = DatabaseCache.getCodeBySortCodeAndCode("027", report.getNative_Status());
                String codeName = codeObject == null ? report.getNative_Status() : codeObject.getName();
                map.put("reportStatusName", DatabaseCache.getCodeNameBySortCodeAndCodeNotNull("033", report.getStatus_Code())
                        +"/"+codeName);
                map.put("costTime", DateTime.getFormatDurationByMS(DateTime.between(report.getSubmit_Date(), report.getStatus_Date())));
                mapList.add(map);
            }
        }
        return mapList;
    }

    public void setReportStatusInfo(List<Map<String, String>> reportStatusName) {
        this.reportStatusInfo = reportStatusInfo;
    }

    public List<Map<String, String>> getReportNotifyStatusInfo() {
        List<Map<String, String>> mapList = new ArrayList<Map<String, String>>();
        if (ObjectUtils.isNotEmpty(notifyList)){
            for (ReportNotify notify: notifyList){
                Map<String, String> map = new HashMap<String,String>();
                map.put("create_Date", DateTime.getString(notify.getCreate_Date()));
                map.put("notifyStatusName", DatabaseCache.getCodeNameBySortCodeAndCodeNotNull("032", notify.getNotify_Status_Code()));
                map.put("protocolTypeCode", notify.getProtocol_Type_Code());
                mapList.add(map);
            }
        }
        return mapList;
    }

    public void setReportNotifyStatusInfo(List<Map<String, String>> reportNotifyStatusInfo) {
        this.reportNotifyStatusInfo = reportNotifyStatusInfo;
    }
}
