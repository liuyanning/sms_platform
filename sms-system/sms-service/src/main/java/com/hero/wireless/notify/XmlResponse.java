package com.hero.wireless.notify;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class XmlResponse implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2112700629768616813L;
	private String result;
	private String desc;
	private String msgid;
	/**兼容MY的接口返回值字段*/
	private String returnsms;
	private String returnstatus;
	private String message;
	private String remainpoint;
	private String successCounts;

	public XmlResponse(){}
	
	public XmlResponse(String result, String desc) {
		super();
		this.result = result;
		this.desc = desc;
	}

	public XmlResponse(String result, String desc, String msgid) {
		super();
		this.result = result;
		this.desc = desc;
		this.msgid = msgid;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getMsgid() {
		return msgid;
	}

	public void setMsgid(String msgid) {
		this.msgid = msgid;
	}
	
	public String getReturnsms() {
		return returnsms;
	}

	public String getReturnstatus() {
		return returnstatus;
	}

	public String getMessage() {
		return message;
	}

	public String getRemainpoint() {
		return remainpoint;
	}


	public String getSuccessCounts() {
		return successCounts;
	}

	public void setReturnsms(String returnsms) {
		this.returnsms = returnsms;
	}

	public void setReturnstatus(String returnstatus) {
		this.returnstatus = returnstatus;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setRemainpoint(String remainpoint) {
		this.remainpoint = remainpoint;
	}

	public void setSuccessCounts(String successCounts) {
		this.successCounts = successCounts;
	}

	public String toXml() {
		//<message><result>0</result><desc>余额:91.9600元,已发送:134条</desc></message>
		//<message><result>0</result><desc>余额:91.9600元,已发送:134条</desc></message>
		Document doc = DocumentHelper.createDocument();
		Element rootElement = doc.addElement(Constants.MESSAGE.toString());
		Element resultElement = rootElement.addElement(Constants.RESULT.toString());
		Element descElement = rootElement.addElement(Constants.DESC.toString());
		resultElement.setText(result);
		descElement.setText(desc);
		if (StringUtils.isNotEmpty(msgid)) {
			rootElement.addElement(Constants.MSGID.toString()).setText(msgid);
		}
		return doc.asXML();
	}
	
	public static String toMYXml(XmlResponse xml) {
		Document doc = DocumentHelper.createDocument();
		Element returnsmsElement = doc.addElement("returnsms");
		Element returnstatusElement = returnsmsElement.addElement("returnstatus");
		Element messageElement = returnsmsElement.addElement(Constants.MESSAGE.toString());
		Element remainpointElement = returnsmsElement.addElement("remainpoint");
		Element taskIDElement = returnsmsElement.addElement("taskID");
		Element successCountsElement = returnsmsElement.addElement("successCounts");
		if(StringUtils.isNotBlank(xml.getReturnstatus())){
			returnstatusElement.setText(xml.getReturnstatus());
		}
		if(StringUtils.isNotBlank(xml.getMessage())){
			messageElement.setText(xml.getMessage());
		}
		if(StringUtils.isNotBlank(xml.getRemainpoint())){
			remainpointElement.setText(xml.getRemainpoint());
		}
		if(StringUtils.isNotBlank(xml.getMsgid())){
			taskIDElement.setText(xml.getMsgid());
		}
		if(StringUtils.isNotBlank(xml.getSuccessCounts())){
			successCountsElement.setText(xml.getSuccessCounts());
		}
		return doc.asXML();
	}
	
	public static void main(String[] args) {
		XmlResponse info=new XmlResponse("0", "余额:91.9600元,已发送:134条");
		System.out.println(info.toXml());
		XmlResponse info2=new XmlResponse();
		info2.setMessage("成功");
		info2.setRemainpoint("2");
		info2.setMsgid("asda134234zsdf");
		info2.setReturnstatus("success");
		System.out.println(toMYXml(info2));
	}
}
