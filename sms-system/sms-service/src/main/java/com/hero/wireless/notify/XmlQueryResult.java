package com.hero.wireless.notify;

import com.hero.wireless.enums.MoSpType;
import com.hero.wireless.web.config.DatabaseCache;
import com.hero.wireless.web.entity.business.EnterpriseUser;
import com.hero.wireless.web.entity.send.Inbox;
import com.hero.wireless.web.entity.send.Report;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 查询结果
 * 
 * @author zly
 *
 */
@Deprecated
public class XmlQueryResult implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4542825084909297664L;
	private String result;
	private String desc;
	private List<?> dataList;

	public XmlQueryResult(String result, String desc, List<?> dataList) {
		super();
		this.result = result;
		this.desc = desc;
		this.dataList = dataList;
	}

	public XmlQueryResult(String result, String desc) {
		super();
		this.result = result;
		this.desc = desc;
		this.dataList = new ArrayList<>();
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

	public List<?> getDataList() {
		return dataList;
	}

	public void setDataList(List<?> dataList) {
		this.dataList = dataList;
	}

	public String toReportXml() {
		Document doc = DocumentHelper.createDocument();
		Element rootElement = doc.addElement(Constants.REPORTS.toString());
		Element resultElement = rootElement.addElement(Constants.RESULT.toString());
		Element descElement = rootElement.addElement(Constants.DESC.toString());
		resultElement.setText(result);
		descElement.setText(desc);
		rootElement.addElement("type").setText("1");
		dataList.forEach(item -> {
			Report reportRecord = (Report) item;
            String phoneNo = reportRecord.getPhone_No();
            phoneNo = DatabaseCache.getUserCountryCodePhoneNo(reportRecord.getEnterprise_User_Id(),phoneNo);
            Element report = rootElement.addElement("report");
			report.addElement(" ").addCDATA("0");
			report.addElement("enterprise_no").addCDATA(reportRecord.getEnterprise_No());
			report.addElement("mobile").addCDATA(phoneNo);
			report.addElement("seq").addCDATA(reportRecord.getSequence().toString());
			report.addElement("msg_id").addCDATA(reportRecord.getMsg_Batch_No());
			report.addElement("result").addCDATA(reportRecord.getStatus_Code().toString());
			report.addElement("netway_code").addCDATA(StringUtils.defaultIfBlank(reportRecord.getSub_Code(), ""));
			report.addElement("mcc").addCDATA(StringUtils.defaultIfBlank(reportRecord.getMCC(), ""));
			report.addElement("mnc").addCDATA(StringUtils.defaultIfBlank(reportRecord.getMNC(), ""));
			report.addElement("cost").addCDATA(String.valueOf(reportRecord.getEnterprise_User_Unit_Price()));
		});
		return doc.asXML();
	}

	public String toMoXml() {
		Document doc = DocumentHelper.createDocument();
		Element rootElement = doc.addElement(Constants.REPORTS.toString());
		Element resultElement = rootElement.addElement(Constants.RESULT.toString());
		Element descElement = rootElement.addElement(Constants.DESC.toString());
		resultElement.setText(result);
		descElement.setText(desc);
		rootElement.addElement("type").setText("2");
		dataList.forEach(item -> {
			Inbox moRecord = (Inbox) item;
            String phoneNo = moRecord.getPhone_No();
            phoneNo = DatabaseCache.getUserCountryCodePhoneNo(moRecord.getEnterprise_User_Id(),phoneNo);
            Element report = rootElement.addElement("report");
			report.addElement("enterprise_id").addCDATA("0");
			report.addElement("enterprise_no").addCDATA(moRecord.getEnterprise_No());
			report.addElement("mobile").addCDATA(phoneNo);

			EnterpriseUser enterpriseUser = DatabaseCache.getEnterpriseUserCachedById(moRecord.getEnterprise_User_Id());
			// 上行码号类型
			String moSpType = StringUtils.isEmpty(enterpriseUser.getMo_Sp_Type_Code()) ? MoSpType.VIRTUAL_CUSTOM.toString() : enterpriseUser.getMo_Sp_Type_Code();
			if (MoSpType.VIRTUAL_CUSTOM.equals(moSpType)) {
				report.addElement("port").addCDATA(StringUtils.defaultIfBlank(moRecord.getInput_Sub_Code(), ""));
			} else {
				report.addElement("port").addCDATA(StringUtils.defaultIfBlank(moRecord.getSub_Code(), ""));
			}

			report.addElement("content").addCDATA(moRecord.getContent());
		});
		return doc.asXML();
	}
	
	public static void main(String[] args) {
		Document doc = DocumentHelper.createDocument();
		Element rootElement = doc.addElement(Constants.REPORTS.toString());
		rootElement.addElement("type").setText("1");
		System.out.println(doc.asXML());
	}
}
