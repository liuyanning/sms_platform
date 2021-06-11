package com.hero.wireless.sms.sender.service;

import com.drondea.sms.channel.ChannelSession;
import com.drondea.sms.common.SequenceNumber;
import com.drondea.sms.common.util.SgipSequenceNumber;
import com.drondea.sms.conf.sgip.SgipClientSocketConfig;
import com.drondea.sms.message.IMessage;
import com.drondea.sms.message.sgip12.SgipSubmitRequestMessage;
import com.drondea.sms.message.sgip12.SgipSubmitResponseMessage;
import com.drondea.sms.thirdparty.SmsDcs;
import com.hero.wireless.enums.ProtocolType;
import com.hero.wireless.enums.SubmitStatus;
import com.hero.wireless.web.entity.business.Channel;
import com.hero.wireless.web.entity.send.Submit;
import com.hero.wireless.web.util.ChannelUtil.OtherParameter;
import com.hero.wireless.web.util.SMSUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service("sgipService")
public class SgipServiceImpl extends AbstractTcpSenderService {

	@Override
	protected void setRequestInfo(Channel channel, Submit submitted, IMessage request) {
		SgipSubmitRequestMessage requestMessage = (SgipSubmitRequestMessage) request;
		submitted.setSequence((int) requestMessage.getPkNumber());
		submitted.setContent(requestMessage.getMsgContent());
		submitted.setContent_Length(submitted.getContent().length());
		submitted.setSP_Number(requestMessage.getSpNumber());
	}

	@Override
	public SubmitStatus parseSubmitResponse(IMessage response,Submit submit) {
		SgipSubmitResponseMessage msg = (SgipSubmitResponseMessage) response;
		submit.setSubmit_Description(String.valueOf(msg.getResult()));
		//这里保存整个sequenceNumber
		submit.setChannel_Msg_Id(msg.getHeader().getSequenceNumber().toString());
		if (0 == msg.getResult()) {
			return SubmitStatus.SUCCESS;
		}
		return SubmitStatus.FAILD;
	}

	@Override
	protected IMessage wrapSubmitRequest(ChannelSession channelSession, Channel channel, Map<String, OtherParameter> otherParameters,
										 Submit submit) {
		SgipSubmitRequestMessage message = new SgipSubmitRequestMessage();
		SequenceNumber sequenceNumber = channelSession.getSequenceNumber();
		SgipClientSocketConfig configuration = (SgipClientSocketConfig) channelSession.getConfiguration();
		SgipSequenceNumber sgipSequenceNumber = new SgipSequenceNumber(configuration.getNodeId(), sequenceNumber.next());
		String content = getSubmitContent(submit, channel);
		message.setSignature(submit.getSignature());
		message.getHeader().setSequenceNumber(sgipSequenceNumber);
		message.setUserNumber(new String[] { submit.getPhone_No() });
		String spNo = SMSUtil.getSpNumber(channel, submit.getSub_Code());
		message.setSpNumber(spNo);
		String protocolType = submit.getProtocol_Type_Code();
		if (protocolType.equals(ProtocolType.HTTP_XML.toString()) || protocolType.equals(ProtocolType.HTTP_JSON.toString())
				|| protocolType.equals(ProtocolType.WEB.toString()) || StringUtils.isEmpty(submit.getCharset())) {
			message.setMsgContent(content);
			//编码
			submit.setCharset(SMSUtil.getCharsetByByte(message.getMessageCoding().getValue()));
		} else {
			message.setMsgContent(content, new SmsDcs(SMSUtil.getGeneralDataCodingDcs(submit.getCharset())));
		}

		return message;
	}
}
