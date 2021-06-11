package com.hero.wireless.enums;

import com.hero.wireless.sms.sender.service.AbstractSenderService;
import com.hero.wireless.sms.sender.service.CmppServiceImpl;
import com.hero.wireless.sms.sender.service.HttpServiceImpl;
import com.hero.wireless.sms.sender.service.SgipServiceImpl;
import com.hero.wireless.sms.sender.service.SmgpServiceImpl;
import com.hero.wireless.sms.sender.service.SmppServiceImpl;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

/**
 * 通讯协议
 * 
 * @author volcano
 * @date 2019年9月12日下午11:16:40
 * @version V1.0
 */
public enum ProtocolType {
	HTTP(HttpServiceImpl.class),

	CMPP2(CmppServiceImpl.class),

	SGIP12(SgipServiceImpl.class),

//	SMGP3(SmgpServiceImpl.class),

	CMPP3(CmppServiceImpl.class),

	SMPP(SmppServiceImpl.class),

	HTTP_XML,

	HTTP_JSON,

	SYSTEM,

	CMPP(CmppServiceImpl.class),

	SGIP(SgipServiceImpl.class),

	SMGP(SmgpServiceImpl.class),

	WEB;
	private String value;
	private Class<? extends AbstractSenderService> senderServiceClasze;

	private ProtocolType() {
		this.value = this.name().toLowerCase();
	}

	private ProtocolType(Class<? extends AbstractSenderService> clasze) {
		this();
		this.senderServiceClasze = clasze;
	}

	public static Class<? extends AbstractSenderService> getSenderServiceClasze(String protocolTypeValue) {
		//聪明jdk如果有findFirst,在检查到第一条记录的时候,循环就会退出
		ProtocolType protocolType = Arrays.asList(ProtocolType.values()).stream().filter(item -> {
			return item.toString().equalsIgnoreCase(protocolTypeValue);
		}).findFirst().orElseGet(null);
		if (protocolType == null) {
			return null;
		}
		return protocolType.getSenderServiceClasze();
	}

	public Class<? extends AbstractSenderService> getSenderServiceClasze() {
		return this.senderServiceClasze;
	}

	@Override
	public String toString() {
		return value;
	}

	public boolean equals(String value) {
		if (StringUtils.isEmpty(value)) {
			return false;
		}
		return this.value.equalsIgnoreCase(value);
	}
}
