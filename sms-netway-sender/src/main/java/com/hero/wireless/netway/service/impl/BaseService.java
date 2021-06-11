package com.hero.wireless.netway.service.impl;

import com.drondea.wireless.util.SuperLogger;
import com.hero.wireless.json.JsonUtil;
import com.hero.wireless.notify.XmlResponse;
import com.hero.wireless.web.entity.business.EnterpriseUser;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.HashMap;


public class BaseService {

    /**
     * @param message
     * @param returnStatus
     * @param loginUser
     * @param taskId
     * @return
     * @throws
     * @Title: getResultResponse
     * @Description: 仿迈远接口短信提交返回数据包装类
     * @author yjb
     * @date 2020-04-17
     */
    @SuppressWarnings("static-access")
    protected String getResultResponse(String message, String returnStatus, EnterpriseUser loginUser,
                                       String taskId) {
        XmlResponse xml = new XmlResponse();
        xml.setMessage(message);
        xml.setMsgid(taskId);
        xml.setRemainpoint(String.valueOf(loginUser.getAvailable_Amount()));
        xml.setReturnstatus(returnStatus);
        xml.setSuccessCounts(returnStatus.equalsIgnoreCase("Success") ? "1" : "0");
        return xml.toMYXml(xml);
    }

    protected String getResultErrorResponse(String message, String errorCode) {
        try {
            Document doc = DocumentHelper.createDocument();
            Element returnsmsElement = doc.addElement("returnsms");
            Element errorstatusElement = returnsmsElement.addElement("errorstatus");
            errorstatusElement.addElement("error").setText(errorCode);
            errorstatusElement.addElement("remark").setText(message);
            return doc.asXML();
        } catch (Exception e) {
            SuperLogger.error(e.getMessage(), e);
            return "";
        }
    }

    /**
     * @param message
     * @param returnStatus
     * @param taskId
     * @return
     * @Title: getMASResultResponse
     * @Description: 仿MAS接口结果返回组长数据
     * @author yjb
     * @date 2020-04-22
     */
    protected String wrapMASResultResponse(String message, String returnStatus, String taskId) {
        try {
            HashMap<String, Object> retsMap = new HashMap<String, Object>();
            retsMap.put("taskId", taskId);
            HashMap<String, Object> resultMap = new HashMap<String, Object>();
            resultMap.put("code", returnStatus);
            resultMap.put("msg", message);
            resultMap.put("rets", retsMap);
            return JsonUtil.STANDARD.writeValueAsString(resultMap);
        } catch (Exception e) {
            SuperLogger.error("MAS接口返回组装数据异常：" + e.getMessage());
            return null;
        }
    }

    /**
     * 仿迈远接口查询状态报告返回数据组装
     */
    public String toMYResultXml(String message, String returnStatus, EnterpriseUser loginUser,
                                String taskId) {
        Document doc = DocumentHelper.createDocument();
        Element returnsmsElement = doc.addElement("returnsms");
        returnsmsElement.addElement("returnstatus").setText(returnStatus);
        returnsmsElement.addElement("message").setText(message);
        //这里迈远系统不能是负数，先写死
        returnsmsElement.addElement("remainpoint").setText("0");
        returnsmsElement.addElement("taskID").setText(taskId);
        returnsmsElement.addElement("successCounts").setText("1");
        try {
            return doc.asXML();
        } catch (Exception e) {
            SuperLogger.error(e.getMessage(), e);
            return "";
        }
    }
}
