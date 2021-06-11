package com.hero.wireless.http.connector.mms;

import com.drondea.wireless.config.Constant;
import com.drondea.wireless.util.SecretUtil;
import com.drondea.wireless.util.SuperLogger;
import com.fasterxml.jackson.core.type.TypeReference;
import com.hero.wireless.enums.Charset;
import com.hero.wireless.enums.ReportStatus;
import com.hero.wireless.enums.SubmitStatus;
import com.hero.wireless.http.AbstractHttp;
import com.hero.wireless.json.JsonUtil;
import com.hero.wireless.notify.MasMMSMo;
import com.hero.wireless.notify.MasMMSReport;
import com.hero.wireless.okhttp.CharsetResponseBody;
import com.hero.wireless.okhttp.HttpClient;
import com.hero.wireless.web.config.DatabaseCache;
import com.hero.wireless.web.entity.business.Channel;
import com.hero.wireless.web.entity.send.Inbox;
import com.hero.wireless.web.entity.send.Report;
import com.hero.wireless.web.entity.send.Submit;
import com.hero.wireless.web.util.QueueUtil;
import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * 
* Title: MASMMSImpl  
* Description: 移动云MAS视频短信
* @author yjb
* @date 2020-04-10
 */
public class MASMMSImpl extends AbstractHttp {

    /**
     * 上游主动推送 report
     * @param channelReport
     * @return
     * @throws Exception
     */
    @Override
    public Object report(ChannelReport channelReport) throws Exception {
        MasMMSReport base = (MasMMSReport)channelReport.getData();
        Report report = new Report();
        report.setChannel_No(channelReport.getChannelNo());
        report.setChannel_Msg_Id(base.getTaskId());
        report.setPhone_No(base.getMobile());
        if ("4".equals(base.getStatus())) {//发送结果 4 成功 5 失败
            report.setStatus_Code(ReportStatus.SUCCESS.toString());
        } else {
            report.setStatus_Code(ReportStatus.FAILD.toString());
        }
        report.setCreate_Date(new Date());
        report.setNative_Status(base.getStatus());
        report.setRemark(base.getErrorCode());
        report.setStatus_Date(new Date());
        saveReport(report);
        return "SUCCESS";
    }

    @Override
    public void report(Channel channel) throws Exception {

    }

	@SuppressWarnings("unchecked")
	@Override
	public SubmitStatus submit(Submit submit) throws Exception {
		Channel channel = DatabaseCache.getChannelByNo(submit.getChannel_No());
        String postURL = submitUrl(channel);
		HttpClient httpClient = new HttpClient();
		httpClient.setCharset(Charset.UTF8.toString());
		String timestamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		TreeMap<String, Object> params = new TreeMap<>();
        params.put("appKey", channel.getAccount());
        params.put("ts",timestamp);
        params.put("modeId", submit.getContent());
        params.put("mobiles", submit.getPhone_No().split(Constant.MUTL_MOBILE_SPLIT));
        params.put("notifyUrl", extInfo1(channel));
        params.put("sendTime","");
        StringBuffer buffer = new StringBuffer();
        String signString = buffer.append(submit.getContent()).append(timestamp).append(channel.getAccount()).append(channel.getPassword()).toString();
        params.put("sign",SecretUtil.MD5(signString).toLowerCase());
        String httpContent = JsonUtil.STANDARD.writeValueAsString(params);
        SuperLogger.debug("请求报文："+httpContent);
        CharsetResponseBody response = httpClient.post(postURL, httpContent,HttpClient.MEDIA_TYPE_JSON);
		if(response == null || StringUtils.isEmpty(response.string())){
            return SubmitStatus.FAILD;
        }
        SuperLogger.debug("响应报文："+response.string());
        Map<String, Object> resultMap = JsonUtil.STANDARD.readValue(response.string(), new TypeReference<Map<String, Object>>() {});
        String code = resultMap.get("code").toString();
        submit.setSubmit_Description(resultMap.get("msg").toString());
        if(!"0".equals(code)){//0:成功，-1：失败
            SuperLogger.error("视频短信提交失败：接口返回数据 ,"+resultMap.toString());
            return SubmitStatus.FAILD;
        }
        Map<String, String> rets = (LinkedHashMap<String, String>)resultMap.get("rets");
        submit.setChannel_Msg_Id(rets.get("taskId"));
        return SubmitStatus.SUCCESS;
	}

    /**
     * 上游主动推送 mo
     * @param channelReport
     * @return
     * @throws Exception
     */
	@Override
	public Object mo(ChannelReport channelReport) throws Exception {
        MasMMSMo base = (MasMMSMo)channelReport.getData();
        Inbox inbox = new Inbox();
        inbox.setChannel_No(channelReport.getChannelNo());
        inbox.setContent(base.getMoMsg());
        inbox.setSP_Number(base.getSrcId());
        inbox.setPhone_No(base.getMobile());
        inbox.setCreate_Date(new Date(base.getMoDate()));
        // 发送mq消息
        QueueUtil.saveMo(inbox);
        return "SUCCESS";
	}

	@Override
	public void mo(Channel channel) throws Exception {
		// TODO Auto-generated method stub
	}

	@Override
	public String balance(Channel channel) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
