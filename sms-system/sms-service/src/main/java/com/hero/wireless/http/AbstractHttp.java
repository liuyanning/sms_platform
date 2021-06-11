package com.hero.wireless.http;

import com.drondea.wireless.util.CommonThreadPoolFactory;
import com.drondea.wireless.util.DateTime;
import com.hero.wireless.enums.SignaturePosition;
import com.hero.wireless.enums.SubmitStatus;
import com.hero.wireless.sms.sender.service.SubmitCacheService;
import com.hero.wireless.web.config.DatabaseCache;
import com.hero.wireless.web.entity.business.Channel;
import com.hero.wireless.web.entity.send.Inbox;
import com.hero.wireless.web.entity.send.Report;
import com.hero.wireless.web.entity.send.Submit;
import com.hero.wireless.web.entity.send.ext.SubmitExt;
import com.hero.wireless.web.service.ISendManage;
import com.hero.wireless.web.util.*;
import org.apache.commons.lang3.ObjectUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public abstract class AbstractHttp implements IHttp {

	protected void saveMo(Inbox mo) {
		QueueUtil.saveMo(mo);
	}

	protected void saveReport(Report recive) {
		saveReport(recive, 1);
	}

	private void saveReport(Report recive, int retryTimes) {
		List<Submit> submits = new ArrayList<>();
		String key = CacheKeyUtil.genNewCacheSubmitedKey(recive.getChannel_Msg_Id(), recive.getPhone_No());
		Submit submit = SubmitCacheService.SUBMIT_LOCAL_CACHE.get(key);

		if (submit != null) {
			submits.add(submit);
			//保存长短信的除最后一个片段外的所有submit信息
			for(int i= 0; i < (submit.getSequence() - 1); i++){
				int sequence = i + 1;
				String localKey = CacheKeyUtil.genNewCacheSubmitedKey(
						recive.getChannel_Msg_Id() + "_" + sequence, recive.getPhone_No());
				Submit entity = SubmitCacheService.SUBMIT_LOCAL_CACHE.get(localKey);
				if(ObjectUtils.isNotEmpty(entity)){
					submits.add(entity);
				}
			}
		} else {
			// 本地缓存找不到记录，去数据库查询
			ISendManage sendManage = ApplicationContextUtil.getBean("sendManage");
			SubmitExt condition = new SubmitExt();
			condition.setMinSubmitDate(DateTime.addDay(-4));
			condition.setMaxSubmitDate(new Date());
			condition.setChannel_Msg_Id(recive.getChannel_Msg_Id());
			condition.setPhone_No(recive.getPhone_No());
			List<SubmitExt> submitExts = sendManage.querySubmitListSharding(condition);
			if(ObjectUtils.isNotEmpty(submitExts)) {
				submits.addAll(submitExts);
			}
		}

		if (ObjectUtils.isEmpty(submits)) {
			if(retryTimes <= 0) {
				// 没有了重试机会
				return;
			}
			CommonThreadPoolFactory.getInstance().getScheduleExecutor().schedule(() -> {
				saveReport(recive, retryTimes - 1);
			}, 1, TimeUnit.MINUTES);
			return;
		}

		submits.forEach(entity ->{
			//长短信时  一条状态成功 就认为所有的片段的状态报告都是成功的
			Report report = SMSUtil.buildReport(entity);
			report.setStatus_Code(recive.getStatus_Code());
			report.setNative_Status(recive.getNative_Status());
			report.setRemark(recive.getRemark());
			report.setStatus_Date(new Date());
			QueueUtil.saveReport(report);
		});
	}

	/**
	 * 设置签名前置
	 * 
	 * @param sms
	 * @return
	 */
	protected static String setSignaturePre(String sms) {
		return SMSUtil.setSignaturePosition(sms, SignaturePosition.PREFIX);
	}

	/**
	 * 去除签名
	 * 
	 * @param sms
	 * @return String
	 * @exception @since 1.0.0
	 */
	protected String clearSignature(String sms) {
		return SMSUtil.clearSignature(sms);
	}

	/**
	 * 
	 * @param channel
	 * @param code
	 * @param defaultValue
	 * @return
	 * @author volcano
	 * @date 2019年10月2日上午10:51:02
	 * @version V1.0
	 */
	protected String parameter(Channel channel, String code, String defaultValue) {
		return ChannelUtil.getParameter(channel, code, defaultValue);
	}

	/**
	 * 
	 * @param channel
	 * @param code
	 * @return
	 * @author volcano
	 * @date 2019年10月2日上午10:52:07
	 * @version V1.0
	 */
	protected String parameter(Channel channel, String code) {
		return ChannelUtil.getParameter(channel, code, "");
	}

	/**
	 * 
	 * @param channel
	 * @return
	 * @author volcano
	 * @date 2019年10月2日上午10:54:23
	 * @version V1.0
	 */
	protected String moUrl(Channel channel) {
		return parameter(channel, "mo_url");
	}

	/**
	 * 
	 * @param channel
	 * @return
	 * @author volcano
	 * @date 2019年10月2日上午10:55:15
	 * @version V1.0
	 */
	protected String reportUrl(Channel channel) {
		return parameter(channel, "report_url");
	}

	/**
	 * 
	 * @param channel
	 * @return
	 * @author volcano
	 * @date 2019年10月2日上午10:55:42
	 * @version V1.0
	 */
	protected String submitUrl(Channel channel) {
		return parameter(channel, "interface_url");
	}

	/**
	 * 
	 * @param submit
	 * @return
	 * @author volcano
	 * @date 2019年10月2日上午11:11:22
	 * @version V1.0
	 */
	protected String submitUrl(Submit submit) {
		Channel channel = DatabaseCache.getChannelByNo(submit.getChannel_No());
		return parameter(channel, "interface_url");
	}

	/**
	 * 
	 * @param channel
	 * @return
	 * @author volcano
	 * @date 2019年10月2日上午10:57:44
	 * @version V1.0
	 */
	protected String balanceUrl(Channel channel) {
		return parameter(channel, "balance_url");
	}

	/**
	 * 
	 * @param channel
	 * @return
	 * @author volcano
	 * @date 2019年10月2日上午11:14:13
	 * @version V1.0
	 */
	protected String extInfo2(Channel channel) {
		return parameter(channel, "extInfo2");
	}
	
	protected String extInfo1(Channel channel) {
		return parameter(channel, "extInfo1");
	}
	
	protected String extInfo3(Channel channel) {
		return parameter(channel, "extInfo3");
	}


	@Override
	public void mo(Channel channel) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void report(Channel channel) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public SubmitStatus submit(Submit submited) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String balance(Channel channel) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Integer> queryTransferPhone(String token, String mobiles, String url){
		return null;
	}

}
