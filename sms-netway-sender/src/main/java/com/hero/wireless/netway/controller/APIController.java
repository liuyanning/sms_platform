package com.hero.wireless.netway.controller;

import com.drondea.wireless.util.IpUtil;
import com.drondea.wireless.util.ServiceException;
import com.drondea.wireless.util.SuperLogger;
import com.hero.wireless.enums.MessageType;
import com.hero.wireless.enums.ProtocolType;
import com.hero.wireless.json.JsonUtil;
import com.hero.wireless.netway.controller.wrap.DrondeaJsonRequestData;
import com.hero.wireless.netway.controller.wrap.DrondeaXmlRequestData;
import com.hero.wireless.netway.service.IHttpService;
import com.hero.wireless.netway.service.impl.BaseService;
import com.hero.wireless.notify.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author volcano
 * @version V1.0
 * @date 2019年9月19日上午7:26:33
 */
@SuppressWarnings("deprecation")
@Controller
public class APIController extends BaseService {
    @Autowired
    private IHttpService apiService;
    @Autowired
    public HttpServletRequest request;

    @RequestMapping({"/xml/submit", "/submitSms/xml"})
    @ResponseBody
    public String xmlSubmit(@RequestBody String xml) {
        try {
            SuperLogger.debug(xml);
            DrondeaXmlRequestData data = new DrondeaXmlRequestData(xml);
            JsonSubmit submit = data.assembleSubmitRequestData();
            data.checkPassword();
            submit.setEnterprise_no(data.getEnterprise_no());
            submit.setClientIp(IpUtil.getRemoteIpAddr(request));
            submit.setProtocolType(ProtocolType.HTTP_XML.toString());
            submit.setMessageType(MessageType.SMS.toString());
            String returnXmlString = apiService.submit(submit);
            SuperLogger.debug("给下游返回结果：" + returnXmlString.toString());
            JsonSubmitResponse response = JsonUtil.NON_NULL.readValue(returnXmlString, JsonSubmitResponse.class);
            return new XmlResponse("0", "成功", response.getMsgid()).toXml();
        } catch (Exception e) {
            SuperLogger.error(e.getMessage(), e);
            return (new XmlResponse("-1000", e.getMessage()).toXml());
        }
    }

    @RequestMapping({"/xml/balance", "/submitSms/balance"})
    @ResponseBody
    public String xmlBalance(@RequestBody String xml) {
        try {
            SuperLogger.debug(xml);
            DrondeaXmlRequestData data = new DrondeaXmlRequestData(xml);
            JsonBase jsonBase = data.assembleBalaceRequestData();
            data.checkPassword();
            jsonBase.setClientIp(IpUtil.getRemoteIpAddr(request));
            jsonBase.setEnterprise_no(data.getEnterprise_no());
            JsonBalanceResponse info = apiService.balance(jsonBase);
            return ((new XmlResponse("0", String.format("余额:%1$s元,已发送:%2$s条",
                    info.getBalance(), info.getSended()))).toXml());
        } catch (Exception e) {
            SuperLogger.error(e.getMessage(), e);
            return (new XmlResponse("-1000", e.getMessage()).toXml());
        }
    }

    @RequestMapping("/xml/mo")
    @ResponseBody
    public String xmlMo(@RequestBody String xml) {
        try {
            SuperLogger.debug(xml);
            DrondeaXmlRequestData data = new DrondeaXmlRequestData(xml);
            JsonBase jsonBase = data.assembleMoRequestData();
            data.checkPassword();
            jsonBase.setClientIp(IpUtil.getRemoteIpAddr(request));
            jsonBase.setEnterprise_no(data.getEnterprise_no());
            JsonQueryResult info = apiService.queryMo(jsonBase);
            return (new XmlQueryResult("0", "成功", info.getDataList()).toMoXml());
        } catch (Exception e) {
            SuperLogger.error(e.getMessage(), e);
            return (new XmlQueryResult("-1000", e.getMessage()).toMoXml());
        }
    }

    @RequestMapping("/xml/report")
    @ResponseBody
    public String xmlReport(@RequestBody String xml) {
        try {
            SuperLogger.debug(xml);
            DrondeaXmlRequestData data = new DrondeaXmlRequestData(xml);
            JsonBase jsonBase = data.assembleReportRequestData();
            data.checkPassword();
            jsonBase.setClientIp(IpUtil.getRemoteIpAddr(request));
            jsonBase.setEnterprise_no(data.getEnterprise_no());
            JsonQueryResult info = apiService.queryReport(jsonBase);
            return ((new XmlQueryResult("0", "成功", info.getDataList())).toReportXml());
        } catch (Exception e) {
            SuperLogger.error(e.getMessage(), e);
            return (new XmlQueryResult("-1000", e.getMessage()).toReportXml());
        }
    }

    @RequestMapping("/json/submit")
    @ResponseBody
    public String submit(@RequestBody String json) {
        try {
            SuperLogger.debug(json);
            DrondeaJsonRequestData data = new DrondeaJsonRequestData(json);
            data.checkSign();
            JsonSubmit submit = data.assembleSubmitRequestData();
            submit.setClientIp(IpUtil.getRemoteIpAddr(request));
            submit.setProtocolType(ProtocolType.HTTP_JSON.toString());
            submit.setMessageType(MessageType.SMS.toString());
            String returnString = apiService.submit(submit);
            SuperLogger.debug("给下游返回结果：" + returnString.toString());
            return (returnString);
        } catch (Exception e) {
            SuperLogger.error(e.getMessage(), e);
            return (new JsonResponse("-1000", e.getMessage()).toJson());
        }
    }

    @RequestMapping("/json/balance")
    @ResponseBody
    public String balance(@RequestBody String json) {
        try {
            SuperLogger.debug(json);
            DrondeaJsonRequestData requestData = new DrondeaJsonRequestData(json);
            requestData.checkSign();
            JsonBase balance = requestData.assembleBalaceRequestData();
            balance.setClientIp(IpUtil.getRemoteIpAddr(request));
            JsonResponse info = apiService.balance(balance);
            return (info.toJson());
        } catch (Exception e) {
            SuperLogger.error(e.getMessage(), e);
            return (new JsonResponse("-1000", e.getMessage()).toJson());
        }
    }

    @RequestMapping("/json/report")
    @ResponseBody
    public String report(@RequestBody String json) {
        try {
            SuperLogger.debug(json);
            DrondeaJsonRequestData requestData = new DrondeaJsonRequestData(json);
            requestData.checkSign();
            JsonBase base = requestData.assembleReportRequestData();
            base.setClientIp(IpUtil.getRemoteIpAddr(request));
            JsonQueryResult info = apiService.queryReport(base);
            return (info.toReportJson());
        } catch (ServiceException e) {
            SuperLogger.error(e.getMessage(), e);
            return (new JsonResponse("-1000", e.getMessage()).toJson());
        } catch (Exception e) {
            SuperLogger.error(e.getMessage(), e);
            return (new JsonResponse("-1000", e.getMessage()).toJson());
        }
    }

    @RequestMapping("/json/mo")
    @ResponseBody
    public String mo(@RequestBody String json) {
        try {
            SuperLogger.debug(json);
            DrondeaJsonRequestData requestData = new DrondeaJsonRequestData(json);
            requestData.checkSign();
            JsonBase base = requestData.assembleMoRequestData();
            base.setClientIp(IpUtil.getRemoteIpAddr(request));
            JsonQueryResult info = apiService.queryMo(base);
            return (info.toMoJson());
        } catch (ServiceException e) {
            SuperLogger.error(e.getMessage(), e);
            return (new JsonResponse("-1000", e.getMessage()).toJson());
        } catch (Exception e) {
            SuperLogger.error(e.getMessage(), e);
            return (new JsonResponse("-1000", e.getMessage()).toJson());
        }
    }

    @RequestMapping("/mms/submit")
    @ResponseBody
    public String mmsSubmit(@RequestBody String json) {
        SuperLogger.debug(json);
        try {
            SuperLogger.debug(json);
            DrondeaJsonRequestData data = new DrondeaJsonRequestData(json);
            data.checkSign();
            JsonSubmit submit = data.assembleSubmitRequestData();
            submit.setProtocolType(ProtocolType.HTTP_JSON.toString());
            submit.setMessageType(MessageType.MMS.toString());
            String returnString = apiService.submit(submit);
            SuperLogger.debug("给下游返回结果：" + returnString.toString());
            return (returnString);
        } catch (Exception e) {
            SuperLogger.error(e.getMessage(), e);
            return (new JsonResponse("-1000", e.getMessage()).toJson());
        }
    }


    @RequestMapping("/video/submit")
    @ResponseBody
    public String videoSubmit(@RequestBody String json) {
        SuperLogger.debug(json);
        try {
            SuperLogger.debug(json);
            DrondeaJsonRequestData data = new DrondeaJsonRequestData(json);
            data.checkSign();
            JsonSubmit submit = data.assembleSubmitRequestData();
            submit.setProtocolType(ProtocolType.HTTP_JSON.toString());
            submit.setMessageType(MessageType.VIDEO.toString());
            String returnString = apiService.submit(submit);
            SuperLogger.debug("给下游返回结果：" + returnString.toString());
            return (returnString);
        } catch (Exception e) {
            SuperLogger.error(e.getMessage(), e);
            return (new JsonResponse("-1000", e.getMessage()).toJson());
        }
    }

}
