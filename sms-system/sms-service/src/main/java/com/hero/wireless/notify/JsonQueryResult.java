package com.hero.wireless.notify;

import com.drondea.wireless.util.DateTime;
import com.drondea.wireless.util.SuperLogger;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.hero.wireless.enums.MoSpType;
import com.hero.wireless.json.JsonUtil;
import com.hero.wireless.web.config.DatabaseCache;
import com.hero.wireless.web.entity.business.EnterpriseUser;
import com.hero.wireless.web.entity.send.Inbox;
import com.hero.wireless.web.entity.send.Report;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.*;

/**
 * 查询结果
 * 
 * @author zly
 *
 */
public class JsonQueryResult extends JsonResponse {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4542825084909297664L;
	private List<?> dataList;

	public JsonQueryResult(String result, EnterpriseUser user, List<?> dataList) {
		super(result, user);
		this.dataList = dataList;
	}

	public JsonQueryResult(String result, EnterpriseUser user) {
		super(result, user);
	}

	public List<?> getDataList() {
		return dataList;
	}

	public void setDataList(List<?> dataList) {
		this.dataList = dataList;
	}

	public String toReportJson() {
		Map<String, Object> dataMap = toQueryJson();
		List<Map<String, String>> reports = new ArrayList<Map<String, String>>();
		dataList.forEach(item -> {
			Report reportRecord = (Report) item;
            String phoneNo = reportRecord.getPhone_No();
            phoneNo = DatabaseCache.getUserCountryCodePhoneNo(reportRecord.getEnterprise_User_Id(), phoneNo);
            Map<String, String> reportMap = new HashMap<>();
			reportMap.put("phone", phoneNo);
			reportMap.put("seq", reportRecord.getSequence().toString());
			reportMap.put("msgid", reportRecord.getMsg_Batch_No());
			reportMap.put("result", reportRecord.getStatus_Code());
			reportMap.put("netway_code", StringUtils.defaultIfBlank(reportRecord.getSub_Code(), ""));
			reportMap.put("mcc", StringUtils.defaultIfBlank(reportRecord.getMCC(), ""));
			reportMap.put("mnc", StringUtils.defaultIfBlank(reportRecord.getMNC(), ""));
			reportMap.put("cost",String.valueOf(reportRecord.getEnterprise_User_Unit_Price()));//成本
			reports.add(reportMap);
		});
		dataMap.put("report", reports);
		try {
			return JsonUtil.NON_NULL.writeValueAsString(dataMap);
		} catch (JsonProcessingException e) {
			SuperLogger.error(e.getMessage(), e);
			return "";
		}
	}

	private Map<String, Object> toQueryJson() {
		Map<String, Object> dataMap = new LinkedHashMap<String, Object>();
		dataMap.put(Constants.RESULT.toString(), this.getResult());
		dataMap.put(Constants.DESC.toString(), this.getDesc());
		dataMap.put(Constants.SIGN.toString(), this.getSign());
		dataMap.put("timestamp", this.getTimestamp());
		return dataMap;
	}

	public String toMoJson() {
		Map<String, Object> dataMap = toQueryJson();
		List<Map<String, String>> reports = new ArrayList<Map<String, String>>();
		dataList.forEach(item -> {
			Inbox moRecord = (Inbox) item;
            String phoneNo = moRecord.getPhone_No();
            phoneNo = DatabaseCache.getUserCountryCodePhoneNo(moRecord.getEnterprise_User_Id(),phoneNo);
            Map<String, String> reportMap = new HashMap<>();
			reportMap.put("phone", phoneNo);
			EnterpriseUser enterpriseUser = DatabaseCache.getEnterpriseUserCachedById(moRecord.getEnterprise_User_Id());
			// 上行码号类型
			String moSpType = StringUtils.isEmpty(enterpriseUser.getMo_Sp_Type_Code()) ? MoSpType.VIRTUAL_CUSTOM.toString() : enterpriseUser.getMo_Sp_Type_Code();
			if (MoSpType.VIRTUAL_CUSTOM.equals(moSpType)) {
				reportMap.put("port", StringUtils.defaultIfBlank(moRecord.getInput_Sub_Code(), ""));
			} else {
				reportMap.put("port", StringUtils.defaultIfBlank(moRecord.getSub_Code(), ""));
			}
			reportMap.put("content", moRecord.getContent().toString());
			reports.add(reportMap);
		});
		dataMap.put("report", reports);
		try {
			return JsonUtil.NON_NULL.writeValueAsString(dataMap);
		} catch (JsonProcessingException e) {
			SuperLogger.error(e.getMessage(), e);
			return "";
		}
	}
	
	/**
	 * 仿迈远接口查询状态报告返回数据组装
	 */
	public String toMYReportXml() {
		Document doc = DocumentHelper.createDocument();
		Element returnsmsElement = doc.addElement("returnsms");
		if (dataList == null) {
			return doc.asXML();
		}
		dataList.forEach(item -> {
			Report report = (Report)item;
			Element statusboxElement = returnsmsElement.addElement("statusbox");
			statusboxElement.addElement("mobile").setText(report.getPhone_No());
			statusboxElement.addElement("taskid").setText(report.getMsg_Batch_No());
			statusboxElement.addElement("status").setText("success".equalsIgnoreCase(report.getStatus_Code())? "10": "20");
			statusboxElement.addElement("receivetime").setText(DateTime.getString(report.getStatus_Date(), DateTime.Y_M_D_H_M_S_1));
			statusboxElement.addElement("errorcode").setText(report.getNative_Status());
			statusboxElement.addElement("extno").setText(StringUtils.defaultIfEmpty(report.getSub_Code(), ""));
		});
		try {
			return doc.asXML();
		} catch (Exception e) {
			SuperLogger.error(e.getMessage(), e);
			return "";
		}
	}
	
	/**
	 * 仿迈远查询上行短信返回数据组装
	 * @return
	 */
	public String toMYMoXml() {
		Document doc = DocumentHelper.createDocument();
		Element returnsmsElement = doc.addElement("returnsms");
		dataList.forEach(item -> {
			Inbox inbox = (Inbox)item;
			Element statusboxElement = returnsmsElement.addElement("callbox");
			statusboxElement.addElement("mobile").setText(inbox.getPhone_No());
			statusboxElement.addElement("taskid").setText(inbox.getInput_Msg_No());
			statusboxElement.addElement("content").setText(inbox.getContent());
			statusboxElement.addElement("receivetime").setText(DateTime.getString(inbox.getCreate_Date(), DateTime.Y_M_D_H_M_S_1));
			statusboxElement.addElement("extno").setText(StringUtils.defaultIfEmpty(inbox.getInput_Sub_Code(), ""));
		});
		try {
			return doc.asXML();
		} catch (Exception e) {
			SuperLogger.error(e.getMessage(), e);
			return "";
		}
	}
	
}
