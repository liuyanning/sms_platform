/**
 * Copyright (C), 2018-2020,drondea FileName: DeductFilterHandler Author:   20180512 Date:     2020/3/29 0:18
 * Description: DeductFilterHandler History:
 */
package com.hero.wireless.sender.filter;

import com.drondea.wireless.config.Constant;
import com.drondea.wireless.util.CommonThreadPoolFactory;
import com.hero.wireless.enums.MessageType;
import com.hero.wireless.enums.ReportNativeStatus;
import com.hero.wireless.enums.ReportStatus;
import com.hero.wireless.enums.SubmitStatus;
import com.hero.wireless.web.config.DatabaseCache;
import com.hero.wireless.web.entity.business.Channel;
import com.hero.wireless.web.entity.business.EnterpriseUser;
import com.hero.wireless.web.entity.send.Report;
import com.hero.wireless.web.entity.send.Submit;
import com.hero.wireless.web.util.CodeUtil;
import com.hero.wireless.web.util.CopyUtil;
import com.hero.wireless.web.util.QueueUtil;
import com.hero.wireless.web.util.SMSUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class DeductFilterHandler extends Handler {

    @Override
    public Submit doFilter(Channel channel, Submit submit) {
        if (execute(channel, submit) != null) {
            return nextHandler.doFilter(channel, submit);
        }
        return null;
    }

    private Submit execute(Channel channel, Submit submit) {
        //查看开关
        if (DatabaseCache.getStringValueBySystemEnvAndCode("deduct_filter", "close").equals("close")) {
            return submit;
        }
        EnterpriseUser enterpriseUser = DatabaseCache.getEnterpriseUserCachedById(submit.getEnterprise_User_Id());
        if (enterpriseUser == null) {
            return submit;
        }
        Float rate = enterpriseUser.getDeduct_Rate() == null ? 0F : enterpriseUser.getDeduct_Rate();
        if (rate <= 0 || rate >= 1) {
            return submit;
        }
        String deductKeyWords = DatabaseCache.getStringValueBySystemEnvAndCode("deduct_keyword", "");
        if (!StringUtils.isEmpty(deductKeyWords)) {
            long count = Arrays.asList(deductKeyWords.split(",")).stream().filter(
                    k -> submit.getContent().contains(k)).count();
            if (count == 0) {
                return submit;
            }
        }
        List<String> phoneNos = new ArrayList<String>(
                Arrays.asList(submit.getPhone_No().split(Constant.MUTL_MOBILE_SPLIT)));
        Iterator<String> phoneNoIterator = phoneNos.iterator();
        while (phoneNoIterator.hasNext()) {
            String phoneNo = phoneNoIterator.next();
            if (isDeduct(phoneNo, enterpriseUser, rate)) {
                phoneNoIterator.remove();
                Submit deductSubmit = new Submit();
                CopyUtil.SUBMIT_COPIER.copy(submit, deductSubmit, null);
                deductSubmit.setPhone_No(phoneNo);
                saveSubmit(channel, deductSubmit);
            }
        }
        if (!phoneNos.isEmpty()) {
            submit.setPhone_No(StringUtils.join(phoneNos, ","));
            return submit;
        }
        return null;
    }


    /**
     * 是否命中扣除
     *
     * @return
     */
    private boolean isDeduct(String mobile, EnterpriseUser enterpriseUser, float rate) {
        // 白名单 不扣除
        if (DatabaseCache.hashWhiteListByNumber(mobile, enterpriseUser.getEnterprise_No(), null)) {
            return false;
        }
        return Math.random() < rate;
    }


    /**
     * 直接保存扣除的号码
     *
     * @param primitiveSubmit
     */
    private void saveSubmit(Channel channel, Submit primitiveSubmit) {
        String[] contents;
        if (MessageType.SMS.toString().equals(primitiveSubmit.getMessage_Type_Code())) {
            contents = SMSUtil.splitContent(primitiveSubmit.getContent());
        } else {
            contents = new String[1];
            contents[0] = primitiveSubmit.getContent();
        }
        String enterpriseMsgId = primitiveSubmit.getEnterprise_Msg_Id();
        String[] msgIds = null;
        if (enterpriseMsgId != null) {
            msgIds = enterpriseMsgId.split(",");
        }
        for (int i = 0; i < contents.length; i++) {
            String fragment = contents[i];
            Submit submit = new Submit();
            CopyUtil.SUBMIT_COPIER.copy(primitiveSubmit, submit, null);
            submit.setId(null);
            submit.setEnterprise_Msg_Id(null);
            // 特殊标记
            submit.setIs_Deduct(true);
            submit.setContent_Length(fragment.length());
            submit.setContent(fragment);
            submit.setSequence(i + 1);
            SMSUtil.buildSubmitAreaAndOperator(submit);
            submit.setSubmit_Status_Code(SubmitStatus.SUCCESS.toString());
            submit.setSubmit_Description("0");
            submit.setChannel_Msg_Id(CodeUtil.buildMsgNo());
            submit.setSubmit_Date(new Date());
            if (msgIds != null && i < msgIds.length) {
                submit.setEnterprise_Msg_Id(msgIds[i]);
            }

            int delaySubmitResponseTime = (new Random().nextInt(100)) + 10;
            Calendar submitResponseDate = Calendar.getInstance();
            submitResponseDate.add(Calendar.MILLISECOND, delaySubmitResponseTime);
            submit.setSubmit_Response_Date(submitResponseDate.getTime());
            submit.setCreate_Date(submitResponseDate.getTime());
            QueueUtil.saveSubmit(Arrays.asList(submit));

            // 扣量回执机制 delivrd:回执成功 unknow:未知不返回
            String deductReportAction = DatabaseCache.getStringValueBySystemEnvAndCode("deduct_report_action",
                    "delivrd");
            if (!deductReportAction.equalsIgnoreCase("delivrd")) {
                return;
            }

            Report report = SMSUtil.buildReport(submit, submit.getPhone_No());
            report.setSP_Number(submit.getSP_Number());
            report.setNative_Status(ReportNativeStatus.DELIVRD.toString());
            report.setStatus_Code(ReportStatus.SUCCESS.toString());

            // 不低于一秒，数据更真实
            int saveReportDelay = (new Random().nextInt(15)) * 1000 + 1000;
            final Report delaySaveReport = report;

            CommonThreadPoolFactory.getInstance().getScheduleExecutor().schedule(() -> {
                report.setStatus_Date(new Date());
                report.setCreate_Date(new Date());
                QueueUtil.saveReport(delaySaveReport);
            }, saveReportDelay, TimeUnit.MILLISECONDS);

        }
    }
}
