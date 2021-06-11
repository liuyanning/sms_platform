package com.hero.wireless.sms.sender.service;

import com.drondea.sms.channel.ChannelSession;
import com.drondea.sms.message.IMessage;
import com.drondea.sms.type.IMessageResponseHandler;
import com.drondea.wireless.util.CommonThreadPoolFactory;
import com.drondea.wireless.util.SuperLogger;
import com.hero.wireless.enums.*;
import com.hero.wireless.json.JsonUtil;
import com.hero.wireless.sms.sender.statistics.SenderSpeedStatistics;
import com.hero.wireless.web.entity.business.Channel;
import com.hero.wireless.web.entity.send.Submit;
import com.hero.wireless.web.util.ChannelUtil.OtherParameter;
import com.hero.wireless.web.util.*;
import org.apache.commons.lang3.StringUtils;

import java.net.InetSocketAddress;
import java.util.Collections;
import java.util.Date;
import java.util.Map;

/**
 * 
 * 多少个日日夜夜熬过来，才总结出来的经验
 * 
 * @author volcano
 * @date 2019年11月13日下午4:59:49
 * @version V1.0
 */
public abstract class AbstractTcpSenderService extends AbstractSenderService {

	protected String getRequestContent(Channel channel, String content, String signature, int pkNumber, int pkTotal){
		if (StringUtils.isEmpty(signature)) {
			return content;
		}
		//客户端签名不用处理
		if (StringUtils.defaultIfEmpty(channel.getSignature_Direction_Code(), SignatureType.CUSTOMIZE.toString())
				.equalsIgnoreCase(SignatureType.CUSTOMIZE.toString())) {
			return content;
		}

		//前置签名第一个内容要拼接上签名
		if (pkNumber == 1 && StringUtils.defaultIfEmpty(channel.getSignature_Position(), SignaturePosition.PREFIX.toString())
				.equalsIgnoreCase(SignaturePosition.PREFIX.toString()) && !content.startsWith(signature)) {
			content = signature + content;
		}

		//后置签名最后一个内容要拼接上签名
		if (pkNumber == pkTotal && StringUtils.defaultIfEmpty(channel.getSignature_Position(), SignaturePosition.PREFIX.toString())
				.equalsIgnoreCase(SignaturePosition.SUFFIX.toString())) {
			content = content + signature;
		}
		return content;
	}

	public String getSubmitContent(Submit submit,  Channel channel) {
		String content = submit.getContent();
		//签名位置根据通道配置置换
		if (StringUtils.defaultIfEmpty(channel.getSignature_Position(), SignaturePosition.PREFIX.toString())
				.equalsIgnoreCase(SignaturePosition.PREFIX.toString())) {
			content = SMSUtil.resetSignaturePosition(content, submit.getSignature(), SignaturePosition.PREFIX);
		} else {
			content = SMSUtil.resetSignaturePosition(content, submit.getSignature(), SignaturePosition.SUFFIX);
		}
		return content;
	}


	/**
	 *
	 * @param submit
	 *  提交类
	 * @param msgIds
	 *  下游的msgId
	 * @param responseMsg
	 */
	public void doSubmitResponse(Channel channel, Submit submit, String[] msgIds, IMessage responseMsg) {
		Submit cloneSubmit = new Submit();
		CopyUtil.SUBMIT_COPIER.copy(submit, cloneSubmit, null);
		IMessage requestMsg = responseMsg.getRequestMessage();
		setRequestInfo(channel, cloneSubmit, requestMsg);
		SubmitStatus submitStatus = parseSubmitResponse(responseMsg, cloneSubmit);

		// 真实的提交时间
		cloneSubmit.setSubmit_Date(new Date(requestMsg.getSendTimeStamp()));
		Date now = new Date();
		// 响应时间
		cloneSubmit.setSubmit_Response_Date(now);
		int index = Integer.valueOf(cloneSubmit.getSequence()) - 1;
		if (msgIds != null && index < msgIds.length) {
			//下游相关的msgId
			cloneSubmit.setEnterprise_Msg_Id(msgIds[index]);
		}

		if (SubmitStatus.SUCCESS.equals(submitStatus)) {
			cloneSubmit.setSubmit_Status_Code(SubmitStatus.SUCCESS.toString());
		} else {
			//耗时操作
			CommonThreadPoolFactory.getInstance().getBizPoolExecutor().submit(() -> {
				//保存
				SMSUtil.saveFaildReportNotClone(cloneSubmit, true);
			});
			return;
		}

		submit.setCreate_Date(now);
		String key = CacheKeyUtil.genNewCacheSubmitedKey(cloneSubmit.getChannel_Msg_Id(), cloneSubmit.getGroup_Code());
		//保存到本地缓存
		SubmitCacheService.saveSubmit2Local(key, cloneSubmit);

		//线程池批量保存MQ
		QueueUtil.saveSubmit(Collections.singletonList(cloneSubmit));

		//统计发送成功
		SenderSpeedStatistics.channelSubmitSuccessCount(channel, cloneSubmit);
	}

	public void doSaveFailedReport(Channel channel, Submit submit, String[] msgIds, IMessage requestMsg, String desc, String requestKey) {
		Submit cloneSubmit = new Submit();
		CopyUtil.SUBMIT_COPIER.copy(submit, cloneSubmit, null);
		// 真实的提交时间
		if (requestMsg.getSendTimeStamp() > 0) {
			cloneSubmit.setSubmit_Date(new Date(requestMsg.getSendTimeStamp()));
		} else {
			cloneSubmit.setSubmit_Date(new Date());
		}
		setRequestInfo(channel, cloneSubmit, requestMsg);
		// 响应时间
		cloneSubmit.setSubmit_Response_Date(new Date());
		int index = Integer.valueOf(cloneSubmit.getSequence()) - 1;
		if (msgIds != null && index < msgIds.length) {
			//下游相关的msgId
			cloneSubmit.setEnterprise_Msg_Id(msgIds[index]);
		}
		cloneSubmit.setSubmit_Description(desc);

		if (StringUtils.isEmpty(cloneSubmit.getChannel_Msg_Id())) {
			cloneSubmit.setChannel_Msg_Id(CodeUtil.buildMsgNo());
		}
		//耗时操作
		CommonThreadPoolFactory.getInstance().getBizPoolExecutor().submit(() ->
				SMSUtil.saveFaildReportNotClone(cloneSubmit, true));
	}

	public IMessage getSubmitMessage(Channel channel, Submit submit, ChannelSession channelSession) {
		Map<String, OtherParameter> otherParameters = com.hero.wireless.web.util.ChannelUtil.getParameter(channel);
		submit.setChannel_Msg_Id(CodeUtil.buildMsgNo());
		// 这两个时间是初始时间，提交时间和创建时间会被重置
		submit.setSubmit_Date(new Date());
		submit.setCreate_Date(new Date());

		// 通道的group_code
		submit.setGroup_Code(channel.getGroup_Code());

		InetSocketAddress socketAddress = (InetSocketAddress) channelSession.getChannel().localAddress();
		submit.setSender_Local_Port(String.valueOf(socketAddress.getPort()));

		IMessage requestMessage = wrapSubmitRequest(channelSession, channel, otherParameters, submit);

		String enterpriseMsgId = submit.getEnterprise_Msg_Id();
		String[] msgIds = null;
		if (StringUtils.isNotEmpty(enterpriseMsgId)) {
			msgIds = enterpriseMsgId.split(",");
		}

		String[] finalMsgIds = msgIds;
		requestMessage.setMessageResponseHandler(new IMessageResponseHandler(){

			@Override
			public void messageComplete(IMessage msgRequest, IMessage msgResponse) {
				msgResponse.setRequestMessage(msgRequest);
				//这里不使用线程池
				doSubmitResponse(channel, submit, finalMsgIds, msgResponse);
			}

			@Override
			public void messageExpired(String key, IMessage msgRequest) {
				CommonThreadPoolFactory.getInstance().getBizPoolExecutor().submit(() -> {
					SuperLogger.error("asyncWrite response faild========>" + JsonUtil.writeValueAsString(submit));
					doSaveFailedReport(channel, submit, finalMsgIds, msgRequest, "sf002", key);
				});
			}

			@Override
			public void sendMessageFailed(IMessage msgRequest) {
				CommonThreadPoolFactory.getInstance().getBizPoolExecutor().submit(() -> {
					SuperLogger.error("asyncWrite submit faild========>" + JsonUtil.writeValueAsString(submit));
					doSaveFailedReport(channel, submit, finalMsgIds, msgRequest, "sf001", null);
				});
			}
		});
		return requestMessage;
	}

	/**
	 * 设置请求信息
	 * @param channel
	 * @param submitted
	 * @param request
	 */
	protected abstract void setRequestInfo(Channel channel, Submit submitted, IMessage request);


	/**
	 * 
	 * @param response
	 * @param submit
	 * @return
	 * @author volcano
	 * @date 2019年11月13日下午4:50:08
	 * @version V1.0
	 */
	public abstract SubmitStatus parseSubmitResponse(IMessage response, Submit submit);

	/**
	 * 把submit对象转换为各个协议对象
	 * @param channel
	 * @param otherParameters
	 * @param submit
	 * @return
	 * @author volcano
	 * @date 2019年11月13日下午6:01:38
	 * @version V1.0
	 */
	protected abstract IMessage wrapSubmitRequest(ChannelSession channelSession, Channel channel, Map<String, OtherParameter> otherParameters,
												  Submit submit);

}
