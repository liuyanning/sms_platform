package com.hero.wireless.sms.sender.service;

import com.drondea.sms.channel.ChannelSession;
import com.drondea.sms.common.SequenceNumber;
import com.drondea.sms.message.IMessage;
import com.drondea.sms.message.cmpp.CmppSubmitRequestMessage;
import com.drondea.sms.message.cmpp.CmppSubmitResponseMessage;
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

@Service("cmppService")
public class CmppServiceImpl extends AbstractTcpSenderService {

	@Override
	protected void setRequestInfo(Channel channel, Submit submit, IMessage request) {
		CmppSubmitRequestMessage requestMessage = (CmppSubmitRequestMessage) request;
		submit.setSequence((int) requestMessage.getPkNumber());
		submit.setContent(requestMessage.getMsgContent());
		submit.setContent_Length(submit.getContent().length());

		String content = requestMessage.getMsgContent();
		//修正运营侧去掉内容签名的问题
		content = getRequestContent(channel, content, submit.getSignature(), requestMessage.getPkNumber(), requestMessage.getPkTotal());
		//这里的request是拿到的每个片段的内容
		submit.setContent(content);
		submit.setContent_Length(submit.getContent().length());
		submit.setSP_Number(requestMessage.getSrcId());
	}


	@Override
	public SubmitStatus parseSubmitResponse(IMessage response, Submit submit) {
		CmppSubmitResponseMessage srp = (CmppSubmitResponseMessage) response;
		submit.setSubmit_Description(String.valueOf(srp.getResult()));
		//上游返回的msgId,唯一标识一个消息
		submit.setChannel_Msg_Id(srp.getMsgId().toString());
		if (0 == srp.getResult()) {
			return SubmitStatus.SUCCESS;
		}
		return SubmitStatus.FAILD;
	}

	@Override
	protected IMessage wrapSubmitRequest(ChannelSession channelSession, Channel channel, Map<String, OtherParameter> otherParameters,
										 Submit submit) {
		//拼接通道的码号
		String srcId = SMSUtil.getSpNumber(channel, submit.getSub_Code());
		CmppSubmitRequestMessage requestMessage = new CmppSubmitRequestMessage();
		SequenceNumber sequenceNumber = channelSession.getSequenceNumber();
		requestMessage.getHeader().setSequenceId(sequenceNumber.next());
		String content = getSubmitContent(submit, channel);
		requestMessage.setSignature(submit.getSignature());

		String protocolType = submit.getProtocol_Type_Code();
		if (protocolType.equals(ProtocolType.HTTP_XML.toString()) || protocolType.equals(ProtocolType.HTTP_JSON.toString())
				|| protocolType.equals(ProtocolType.WEB.toString()) || StringUtils.isEmpty(submit.getCharset())) {
			requestMessage.setMsgContent(content);
			//设置编码
			submit.setCharset(SMSUtil.getCharsetByByte(requestMessage.getMsgFmt().getValue()));
		} else {
			requestMessage.setMsgContent(content, new SmsDcs(SMSUtil.getGeneralDataCodingDcs(submit.getCharset())));
		}

		String phoneNo = submit.getPhone_No();
		requestMessage.setDestUsrTl((short) 1);
		requestMessage.setDestTerminalId(new String[] { phoneNo });
		requestMessage.setSrcId(srcId);
		requestMessage.setServiceId(ChannelUtil.getParameterValue(otherParameters, "service_id", "0000000000"));
		requestMessage.setFeeUserType(ChannelUtil.getParameterShortValue(otherParameters, "fee_user_type", (short) 2));
		requestMessage.setFeeTerminalId(ChannelUtil.getParameterValue(otherParameters, "fee_terminal_id", ""));
		requestMessage.setMsgSrc(ChannelUtil.getParameterValue(otherParameters, "msg_src", ""));
		requestMessage.setRegisteredDelivery(
				ChannelUtil.getParameterShortValue(otherParameters, "registered_delivery", (short) 1));
		return requestMessage;
	}
}
