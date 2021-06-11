package com.hero.wireless.netway.controller;

import com.drondea.wireless.util.IpUtil;
import com.drondea.wireless.util.ServiceException;
import com.drondea.wireless.util.SuperLogger;
import com.hero.wireless.enums.MessageType;
import com.hero.wireless.enums.ProtocolType;
import com.hero.wireless.json.JsonUtil;
import com.hero.wireless.netway.controller.wrap.MaiYuanSmsRequestData;
import com.hero.wireless.netway.service.IHttpService;
import com.hero.wireless.netway.service.impl.BaseService;
import com.hero.wireless.notify.JsonQueryResult;
import com.hero.wireless.notify.JsonSubmitResponse;
import com.hero.wireless.notify.MaiYuanJsonBase;
import com.hero.wireless.notify.MaiYuanJsonSubmit;
import com.hero.wireless.web.entity.business.EnterpriseUser;
import com.hero.wireless.web.util.CodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Title: MaiYuanController
 * Description:迈远接口控制类
 *
 * @author yjb
 * @date 2020-04-07
 */
@Controller
@RequestMapping("/maiyuanv1")
public class MaiYuanV1Controller extends BaseService {
    @Autowired
    private IHttpService apiService;
    @Autowired
    public HttpServletRequest request;

    /**
     * 仿迈远短信提交
     *
     * @param submit
     * @return
     */
    @RequestMapping("sms.aspx")
    @ResponseBody
    public String submit(MaiYuanJsonSubmit submit) {
        try {
            MaiYuanSmsRequestData data = new MaiYuanSmsRequestData(submit);
            data.checkSignV1();
            submit = (MaiYuanJsonSubmit) data.assembleSubmitRequestData();
            submit.setClientIp(IpUtil.getRemoteIpAddr(request));
            submit.setProtocolType(ProtocolType.HTTP_JSON.toString());
            submit.setMessageType(MessageType.SMS.toString());
            String returnString = apiService.submit(submit);
            JsonSubmitResponse response = JsonUtil.readValue(returnString, JsonSubmitResponse.class);
            returnString = toMYResultXml("ok", "Success", data.getUser(), response.getMsgid());
            SuperLogger.debug("给下游返回结果：" + returnString);
            return (returnString);
        } catch (ServiceException se) {
            SuperLogger.error(se.getMessage(), se);
            return getResultResponse("提交失败", "Faild", new EnterpriseUser(), CodeUtil.buildMsgNo());
        } catch (Exception e) {
            SuperLogger.error(e.getMessage(), e);
            return getResultResponse("接口异常", "Faild", new EnterpriseUser(), CodeUtil.buildMsgNo());
        }
    }

    /**
     * 仿迈远状态报告获取
     *
     * @param base
     * @return
     */
    @RequestMapping("statusApi.aspx")
    @ResponseBody
    public String report(MaiYuanJsonBase base) {
        try {
            MaiYuanSmsRequestData data = new MaiYuanSmsRequestData(base);
            data.checkSignV1();
            base = (MaiYuanJsonBase) data.assembleReportRequestData();
            base.setClientIp(IpUtil.getRemoteIpAddr(request));
            JsonQueryResult info = apiService.queryReport(base);
            return info.toMYReportXml();
        } catch (ServiceException e) {
            SuperLogger.error(e.getMessage(), e);
            return getResultErrorResponse(e.getMessage(), "-1000");
        } catch (Exception e) {
            SuperLogger.error(e.getMessage(), e);
            return getResultErrorResponse("上游接口异常", "-1000");
        }
    }

    /**
     * 仿迈远上行短信查询
     *
     * @param base
     * @return
     */
    @RequestMapping("callApi.aspx")
    @ResponseBody
    public String mo(MaiYuanJsonBase base) {
        try {
            MaiYuanSmsRequestData data = new MaiYuanSmsRequestData(base);
            data.checkSignV1();
            base = (MaiYuanJsonBase) data.assembleMoRequestData();
            base.setClientIp(IpUtil.getRemoteIpAddr(request));
            JsonQueryResult info = apiService.queryMo(base);
            return info.toMYMoXml();
        } catch (ServiceException e) {
            SuperLogger.error(e.getMessage(), e);
            return getResultErrorResponse(e.getMessage(), "-1000");
        } catch (Exception e) {
            SuperLogger.error(e.getMessage(), e);
            return getResultErrorResponse("上游接口异常", "-1000");
        }
    }
}
