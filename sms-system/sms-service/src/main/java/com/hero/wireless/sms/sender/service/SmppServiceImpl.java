package com.hero.wireless.sms.sender.service;

import com.drondea.sms.channel.ChannelSession;
import com.drondea.sms.common.SequenceNumber;
import com.drondea.sms.message.IMessage;
import com.drondea.sms.message.smpp34.SmppSubmitSmRequestMessage;
import com.drondea.sms.message.smpp34.SmppSubmitSmResponseMessage;
import com.hero.wireless.enums.ProtocolType;
import com.hero.wireless.enums.SubmitStatus;
import com.hero.wireless.web.entity.business.Channel;
import com.hero.wireless.web.entity.send.Submit;
import com.hero.wireless.web.util.ChannelUtil.OtherParameter;
import com.hero.wireless.web.util.SMSUtil;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 
 * 
 * @author volcano
 * @date 2019年10月27日下午7:24:43
 * @version V1.0
 */
@Service
public class SmppServiceImpl extends AbstractTcpSenderService {

	@Override
	protected void setRequestInfo(Channel channel, Submit submitted, IMessage request) {
		SmppSubmitSmRequestMessage requestMessage = (SmppSubmitSmRequestMessage) request;
		submitted.setSequence((int) requestMessage.getPkNumber());
		submitted.setContent(requestMessage.getMsgContent());
		submitted.setContent_Length(submitted.getContent().length());
		submitted.setSP_Number(requestMessage.getSourceAddr());
	}

	@Override
	public SubmitStatus parseSubmitResponse(IMessage response, Submit submit) {
		SmppSubmitSmResponseMessage srp = (SmppSubmitSmResponseMessage) response;
		submit.setSubmit_Description(srp.getHeader().getResultMessage());

		String messageId = srp.getMessageId();
		submit.setChannel_Msg_Id(messageId);
		if (srp.getHeader().getCommandStatus() == 0) {
			return SubmitStatus.SUCCESS;
		}
		return SubmitStatus.FAILD;
	}

	@Override
	protected IMessage wrapSubmitRequest(ChannelSession channelSession, Channel channel, Map<String, OtherParameter> otherParameters,
										 Submit submit) {
		SmppSubmitSmRequestMessage message = new SmppSubmitSmRequestMessage();
		SequenceNumber sequenceNumber = channelSession.getSequenceNumber();
		message.getHeader().setSequenceNumber(sequenceNumber.next());

		String spNo = SMSUtil.getSpNumber(channel, submit.getSub_Code());
		message.setSourceAddrNpi((byte) 1);
		message.setSourceAddrTon((byte) 1);
		message.setSourceAddr(spNo);
		String countryCode = ObjectUtils.isEmpty(submit.getCountry_Code()) ? "" : submit.getCountry_Code();
		String content = getSubmitContent(submit, channel);
		message.setSignature(submit.getSignature());
		message.setDestAddrTon((byte) 1);
		message.setDestAddrNpi((byte) 1);
		message.setDestinationAddr(countryCode + submit.getPhone_No());
		// 设置编码 以客户提交的编码为准
		byte charSet = SMSUtil.getSmppGeneralDataCodingDcs(submit.getCharset());
		String protocolType = submit.getProtocol_Type_Code();
		if (protocolType.equals(ProtocolType.HTTP_XML.toString()) || protocolType.equals(ProtocolType.HTTP_JSON.toString())
				|| protocolType.equals(ProtocolType.WEB.toString()) || StringUtils.isEmpty(submit.getCharset())) {
			message.setMsgContent(content);
			submit.setCharset(SMSUtil.getSmppCharsetByByte((byte) message.getDataCoding()));
		} else {
			message.setMsgContent(content, charSet);
		}

		return message;
	}

}
