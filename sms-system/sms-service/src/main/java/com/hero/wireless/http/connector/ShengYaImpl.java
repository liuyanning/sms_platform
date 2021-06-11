package com.hero.wireless.http.connector;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

import org.apache.commons.lang3.StringUtils;

import com.drondea.wireless.util.SuperLogger;
import com.hero.wireless.enums.ReportStatus;
import com.hero.wireless.enums.SubmitStatus;
import com.hero.wireless.http.AbstractHttp;
import com.hero.wireless.okhttp.AbstractCallback;
import com.hero.wireless.okhttp.CharsetResponseBody;
import com.hero.wireless.okhttp.HttpClient;
import com.hero.wireless.web.config.DatabaseCache;
import com.hero.wireless.web.entity.business.Channel;
import com.hero.wireless.web.entity.send.Inbox;
import com.hero.wireless.web.entity.send.Report;
import com.hero.wireless.web.entity.send.Submit;

/**
 *
 *
 * ShengYaSubmitThread
 *
 * @author 张丽阳
 * @createTime 2016年1月27日 下午11:12:51
 * @version 1.0.0
 *
 */
public class ShengYaImpl extends AbstractHttp {

	@Override
	public SubmitStatus submit(Submit submit) throws Exception {
		Channel channel = DatabaseCache.getChannelByNo(submit.getChannel_No());
		String postURL = submitUrl(channel);
		Map<String, String> params = new HashMap<String, String>();
		params.put("act", "sendmsg");
		params.put("user", channel.getAccount());
		params.put("passwd", channel.getPassword());
		params.put("phone", submit.getPhone_No());
		params.put("msg", submit.getContent());
		HttpClient httpClient = new HttpClient();
		httpClient.setCharset("UTF-8");

		String submitResult = httpClient.post(postURL, params).string();
		if (SuperLogger.isDebugEnabled()) {
			SuperLogger.debug(submitResult);
		}
		String ststuStr = submitResult.startsWith("-") ? submitResult : "0";
		submit.setChannel_Msg_Id(submitResult);
		submit.setSubmit_Description(ststuStr);
		if ("0".equals(ststuStr)) {
			return SubmitStatus.SUCCESS;
		} else {
			return SubmitStatus.FAILD;
		}
	}

	@Override
	public void mo(Channel channel) {
		String postURL = moUrl(channel);
		Map<String, String> params = new HashMap<String, String>();
		params.put("user", channel.getAccount());
		params.put("passwd", channel.getPassword());
		HttpClient httpClient = new HttpClient();
		httpClient.postAsync(postURL, params, new AbstractCallback(httpClient.getCharset()) {
			@Override
			public void ok(Call call, CharsetResponseBody response) throws Exception {
				String resultString = response.string();
				if (StringUtils.isEmpty(resultString)) {
					return;
				}
				if (SuperLogger.isDebugEnabled()) {
					SuperLogger.debug(resultString);
				}
				if (StringUtils.isEmpty(resultString.trim()) || resultString.startsWith("-")) {
					return;
				}
				String[] moElementList = resultString.split(";");
				if (moElementList == null || moElementList.length == 0) {
					return;
				}
				List<String> resultList = Arrays.asList(moElementList);
				resultList.forEach(entity -> {
					Inbox inbox = new Inbox();
					inbox.setChannel_No(channel.getNo());
					String[] moElement = entity.split("\\|\\^\\|");
					inbox.setSP_Number(channel.getSp_Number());
					inbox.setPhone_No(moElement[0]);
					inbox.setContent(moElement[1]);
					inbox.setCreate_Date(new Date());
					// MO里面放数据
					saveMo(inbox);
				});
			}
		});
	}

	@Override
	public void report(Channel channel) {
		String postURL = reportUrl(channel);
		Map<String, String> params = new HashMap<String, String>();
		params.put("user", channel.getAccount());
		params.put("passwd", channel.getPassword());
		HttpClient httpClient = new HttpClient();
		httpClient.setCharset("GBK");
		httpClient.postAsync(postURL, params, new AbstractCallback(httpClient.getCharset()) {
			@Override
			public void ok(Call call, CharsetResponseBody response) throws Exception {
				String resultString = response.string();
				if (StringUtils.isEmpty(resultString)) {
					return;
				}
				String[] reportElementList = resultString.split("\\|;\\|");
				if (reportElementList == null || reportElementList.length == 0) {
					return;
				}
				List<String> reportlist = Arrays.asList(reportElementList);
				reportlist.forEach(entity -> {
					String[] report = entity.split("\\|\\^\\|");
					Report reportEntity = new Report();
					reportEntity.setChannel_No(channel.getNo());
					reportEntity.setChannel_Msg_Id(channel.getId() + "_" + report[0]);
					reportEntity.setPhone_No(report[1]);
					reportEntity.setNative_Status(report[2]);
					if ("0".equals(report[2])) {
						reportEntity.setStatus_Code(ReportStatus.SUCCESS.toString());
					} else {
						reportEntity.setStatus_Code(ReportStatus.FAILD.toString());
					}
					saveReport(reportEntity);
				});
			}
		});
	}

	@Override
	public String balance(Channel channel) {
		String url = balanceUrl(channel);
		Map<String, String> params = new HashMap<String, String>();
		params.put("user", channel.getAccount());
		params.put("passwd", channel.getPassword());
		HttpClient httpClient = new HttpClient();
		httpClient.setCharset("GBK");
		String result = "";
		try {
			CharsetResponseBody response = httpClient.post(url, params);
			if (response == null)
				return "";
			result = response.string();
			result = "余额：" + result + "条;";
		} catch (Exception ex) {
			SuperLogger.error(ex.getMessage(), ex);
			result = ex.getMessage();
		}
		return result;
	}
}
