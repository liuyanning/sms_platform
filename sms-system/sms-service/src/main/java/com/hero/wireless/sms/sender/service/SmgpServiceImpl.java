package com.hero.wireless.sms.sender.service;

import com.drondea.sms.channel.ChannelSession;
import com.drondea.sms.common.SequenceNumber;
import com.drondea.sms.message.IMessage;
import com.drondea.sms.message.smgp30.msg.SmgpSubmitRequestMessage;
import com.drondea.sms.message.smgp30.msg.SmgpSubmitResponseMessage;
import com.drondea.sms.thirdparty.SmsDcs;
import com.hero.wireless.enums.ProtocolType;
import com.hero.wireless.enums.SubmitStatus;
import com.hero.wireless.web.entity.business.Channel;
import com.hero.wireless.web.entity.send.Submit;
import com.hero.wireless.web.util.ChannelUtil;
import com.hero.wireless.web.util.ChannelUtil.OtherParameter;
import com.hero.wireless.web.util.SMSUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service("smgpService")
public class SmgpServiceImpl extends AbstractTcpSenderService {

	@Override
	protected void setRequestInfo(Channel channel, Submit submit, IMessage request) {
		SmgpSubmitRequestMessage requestMessage = (SmgpSubmitRequestMessage) request;
		short pknumer = requestMessage.getPkNumber();
		submit.setSequence((int) pknumer);
		String content = requestMessage.getMsgContent();
		//修正运营侧去掉内容签名的问题
		content = getRequestContent(channel, content, submit.getSignature(), requestMessage.getPkNumber(), requestMessage.getPkTotal());
		//这里的request是拿到的每个片段的内容
		submit.setContent(content);
		submit.setContent_Length(submit.getContent().length());
		submit.setSP_Number(requestMessage.getSrcTermId());
	}

	@Override
	public SubmitStatus parseSubmitResponse(IMessage response, Submit submit) {
		SmgpSubmitResponseMessage srp = (SmgpSubmitResponseMessage) response;
		submit.setSubmit_Description(String.valueOf(srp.getStatus()));
		submit.setChannel_Msg_Id(srp.getSmgpMsgId().toString());
		if (0 == srp.getStatus()) {
			return SubmitStatus.SUCCESS;
		}
		return SubmitStatus.FAILD;
	}

	@Override
	protected IMessage wrapSubmitRequest(ChannelSession channelSession, Channel channel, Map<String, OtherParameter> otherParameters,
										 Submit submit) {
		SmgpSubmitRequestMessage message = new SmgpSubmitRequestMessage();
		SequenceNumber sequenceNumber = channelSession.getSequenceNumber();
		message.getHeader().setSequenceId(sequenceNumber.next());
		String content = getSubmitContent(submit, channel);
		message.setSignature(submit.getSignature());

		String srcId = SMSUtil.getSpNumber(channel, submit.getSub_Code());
		message.setPriority(ChannelUtil.getParameterByteValue(otherParameters, "priority",(byte) 1));
		message.setServiceId(ChannelUtil.getParameterValue(otherParameters, "service_id", "0000000000"));
		message.setChargeTermId(ChannelUtil.getParameterValue(otherParameters, "charge_term_id", "000000000000000000000"));
		// 发送方号码
		message.setSrcTermId(srcId);

		String protocolType = submit.getProtocol_Type_Code();
		if (protocolType.equals(ProtocolType.HTTP_XML.toString()) || protocolType.equals(ProtocolType.HTTP_JSON.toString())
				|| protocolType.equals(ProtocolType.WEB.toString()) || StringUtils.isEmpty(submit.getCharset())) {
			message.setMsgContent(content);
			//编码
			submit.setCharset(SMSUtil.getCharsetByByte(message.getMsgFmt().getValue()));
		} else {
			message.setMsgContent(content, new SmsDcs(SMSUtil.getGeneralDataCodingDcs(submit.getCharset())));
		}

		// 接收信息的号码
		message.setDestTermIdArray(submit.getPhone_No());

		return message;
	}

}
