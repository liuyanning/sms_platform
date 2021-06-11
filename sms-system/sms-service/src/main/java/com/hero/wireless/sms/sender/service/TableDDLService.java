package com.hero.wireless.sms.sender.service;

import com.hero.wireless.web.dao.send.ext.IInputLogExtDAO;
import com.hero.wireless.web.dao.send.ext.IReportExtDAO;
import com.hero.wireless.web.dao.send.ext.IReportNotifyExtDAO;
import com.hero.wireless.web.dao.send.ext.ISubmitExtDAO;
import com.hero.wireless.web.entity.send.ext.InputLogExt;
import com.hero.wireless.web.entity.send.ext.ReportExt;
import com.hero.wireless.web.entity.send.ext.ReportNotifyExt;
import com.hero.wireless.web.entity.send.ext.SubmitExt;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @version V3.0.0
 * @description: send库表的创建删除
 * @author: 刘彦宁
 * @date: 2021年02月01日15:43
 **/
@Service
public class TableDDLService {

    @Resource
    private IReportNotifyExtDAO reportNotifyExtDAO;
    @Resource
    private ISubmitExtDAO submitExtDAO;
    @Resource
    private IInputLogExtDAO inputLogExtDAO;
    @Resource
    private IReportExtDAO reportExtDAO;

    public int dropReportNotifyTable(String tableSuffix) {
        ReportNotifyExt reportNotifyExt = new ReportNotifyExt();
        reportNotifyExt.setTableSuffix(tableSuffix);
        return reportNotifyExtDAO.dropTable(reportNotifyExt);
    }

    public int createReportNotifyTable(String tableSuffix) {
        ReportNotifyExt reportNotifyExt = new ReportNotifyExt();
        reportNotifyExt.setTableSuffix(tableSuffix);
        return reportNotifyExtDAO.createReportNotifyTable(reportNotifyExt);
    }

    public int createSubmitTable(String tableSuffix) {
        SubmitExt submitExt = new SubmitExt();
        submitExt.setTableSuffix(tableSuffix);
        return submitExtDAO.createSubmitTable(submitExt);
    }

    public int dropSubmitTable(String tableSuffix) {
        SubmitExt submitExt = new SubmitExt();
        submitExt.setTableSuffix(tableSuffix);
        return submitExtDAO.dropTable(submitExt);
    }

    public int createInputLogTable(String tableSuffix) {
        InputLogExt inputLogExt = new InputLogExt();
        inputLogExt.setTableSuffix(tableSuffix);
        return inputLogExtDAO.createInputLogTable(inputLogExt);
    }

    public int dropInputLogTable(String tableSuffix) {
        InputLogExt inputLogExt = new InputLogExt();
        inputLogExt.setTableSuffix(tableSuffix);
        return inputLogExtDAO.dropTable(inputLogExt);
    }

    public int createReportTable(String tableSuffix) {
        ReportExt reportExt = new ReportExt();
        reportExt.setTableSuffix(tableSuffix);
        return reportExtDAO.createReportTable(reportExt);
    }

    public int dropReportTable(String tableSuffix) {
        ReportExt reportExt = new ReportExt();
        reportExt.setTableSuffix(tableSuffix);
        return reportExtDAO.dropTable(reportExt);
    }
}
