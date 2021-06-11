package com.hero.wireless.http.connector;

import com.drondea.wireless.util.DateTime;
import com.drondea.wireless.util.SecretUtil;
import com.drondea.wireless.util.SuperLogger;
import com.fasterxml.jackson.core.type.TypeReference;
import com.hero.wireless.enums.ReportStatus;
import com.hero.wireless.enums.SubmitStatus;
import com.hero.wireless.http.AbstractHttp;
import com.hero.wireless.json.JsonUtil;
import com.hero.wireless.okhttp.HttpClient;
import com.hero.wireless.web.config.DatabaseCache;
import com.hero.wireless.web.entity.business.Channel;
import com.hero.wireless.web.entity.send.Report;
import com.hero.wireless.web.entity.send.Submit;
import com.hero.wireless.web.util.SMSUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 葩谱
 */
public class PaPuImpl extends AbstractHttp {

    @Override
    public void mo(Channel channel) throws Exception {

    }

    @Override
    public Object report(ChannelReport channel) throws Exception {
        DrondeaImpl.DrondeaReport report = JsonUtil.NON_NULL.readValue(channel.getData().toString(),
                DrondeaImpl.DrondeaReport.class);
        List<Map<String, String>> reports = report.getReport();
        reports.forEach(item -> {
            Report entity = new Report();
            entity.setChannel_No(channel.getChannelNo());
            String msgId = item.get("msgid");
            entity.setChannel_Msg_Id(msgId);
            entity.setPhone_No(item.get("mobile"));
            String status = item.get("state");
            entity.setNative_Status(status);

            if (status.trim().equalsIgnoreCase("0")) {
                entity.setStatus_Code(ReportStatus.SUCCESS.toString());
            } else {
                entity.setStatus_Code(ReportStatus.FAILD.toString());
            }
            entity.setStatus_Date(DateTime.getDate(item.get("datetime")));
            saveReport(entity);
        });
        return "1";
    }

    @Override
    public void report(Channel channel) throws Exception {

    }

    @Override
    public SubmitStatus submit(Submit submit) throws Exception {
        Channel channel = DatabaseCache.getChannelByNo(submit.getChannel_No());
        String url = reportUrl(channel);
        Map<String, String > dataMap = new HashMap<>();
        dataMap.put("username", channel.getAccount());
        dataMap.put("userpwd", SecretUtil.MD5(channel.getPassword()));
        dataMap.put("mobiles", submit.getPhone_No());
        dataMap.put("content", submit.getContent());
//        dataMap.put("sign", "");
        dataMap.put("extid", SMSUtil.getSpNumber(channel, submit.getSub_Code()));
//        dataMap.put("timing_time", "");
        HttpClient httpClient = new HttpClient();
        SuperLogger.debug("【葩谱】请求数据：" + dataMap);
        String result = httpClient.post(url, dataMap).string();
        SuperLogger.debug("【葩谱】请求返回：" + result);
        Map<String, String> resultMap = JsonUtil.STANDARD.readValue(result, new TypeReference<Map<String, String>>() {
        });
        String msgId = resultMap.get("msgid");
        String status = resultMap.get("code");
        submit.setChannel_Msg_Id(msgId);
        submit.setSubmit_Description(resultMap.get("message"));
        if (status.equals("0")) {
            return SubmitStatus.SUCCESS;
        }
        return SubmitStatus.FAILD;
    }

    @Override
    public String balance(Channel channel) throws Exception {
        String url = balanceUrl(channel);
        Map<String, String> dataMap = new HashMap<>();
        dataMap.put("username", channel.getAccount());
        dataMap.put("userpwd", SecretUtil.MD5(channel.getPassword()));
        HttpClient httpClient = new HttpClient();
        String result = httpClient.post(url, dataMap).string();
        Map<String, String> resultMap = JsonUtil.STANDARD.readValue(result, new TypeReference<Map<String, String>>() {
        });
        return String.format("余额：%1$s元", resultMap.get("balance"));
    }
}
