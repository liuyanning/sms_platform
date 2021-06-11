package com.hero.wireless.netway.service.impl;

import com.drondea.wireless.config.Constant;
import com.drondea.wireless.util.ServiceException;
import com.drondea.wireless.util.SuperLogger;
import com.hero.wireless.enums.Charset;
import com.hero.wireless.enums.ProtocolType;
import com.hero.wireless.netway.config.InitSystemEnv;
import com.hero.wireless.notify.*;
import com.hero.wireless.okhttp.HttpClient;
import com.hero.wireless.web.config.DatabaseCache;
import com.hero.wireless.web.entity.business.EnterpriseUser;
import com.hero.wireless.web.entity.business.ext.EnterpriseUserExt;
import com.hero.wireless.web.entity.send.Inbox;
import com.hero.wireless.web.entity.send.Input;
import com.hero.wireless.web.entity.send.Report;
import com.hero.wireless.web.exception.BaseException;
import com.hero.wireless.web.util.CodeUtil;
import com.hero.wireless.web.util.QueueUtil.HttpReport;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Component
public class HttpAPIServiceImpl extends AbstractHttpService {

    @Override
    public JsonBalanceResponse balance(JsonBase data) throws Exception {
        EnterpriseUser loginUser = validateUser(data);
        // TODO 这么处理不好 后期修改 ;之前缓存用的userName 现在更新金额的时候 缓存用id作为key的 所以再用id去取一下缓存的数据
        loginUser = DatabaseCache.getEnterpriseUserById(loginUser.getId());
        return new JsonBalanceResponse("0", loginUser);
    }

    @Override
    public String submit(JsonSubmit data) throws Exception {
        JsonBase base = new JsonBase(data.getEnterprise_no(), data.getAccount(), data.getTimestamp());
        base.setClientIp(data.getClientIp());
        EnterpriseUser loginUser = validateUser(base);
        Input input = new Input();
        input.setInput_Date(new Date());
        input.setEnterprise_No(loginUser.getEnterprise_No());
        input.setEnterprise_User_Id(loginUser.getId());
        if (StringUtils.isEmpty(data.getCustomId())) {
            input.setMsg_Batch_No(CodeUtil.buildMsgNo());
        } else {
            //客户端提供批次号，账号+用户提供的id
            input.setMsg_Batch_No(data.getAccount() + "_" + data.getCustomId());
        }

        input.setSource_IP(data.getClientIp());
        input.setProtocol_Type_Code(data.getProtocolType());
        input.setMessage_Type_Code(data.getMessageType());
        List<JsonMessage> messages = data.getMessageList();
        messages.forEach(item -> {
            String phones = item.getPhones();
            APIAssert.phones(phones);
            input.setPhone_Nos(phones);
            String content = item.getContent();
            APIAssert.content(content);
            input.setContent(content);
            String subCode = item.getSubcode();
            APIAssert.subcode(subCode);
            input.setSub_Code(subCode);
            String sendTime = item.getSendtime();
            input.setSend_Time(APIAssert.sendtime(sendTime));
            String countryCode = item.getCountrycode();
            input.setCountry_Code(StringUtils.isEmpty(countryCode) ? "86" : countryCode);
            String charset = item.getCharset();
            input.setCharset(StringUtils.isEmpty(charset) ? Charset.UTF8.toString() : charset);
            //记录网关IP
            input.setGate_Ip(InitSystemEnv.LOCAL_IP);
            try {
                sendManage.batchInputSms(input);
            } catch (BaseException e) {
                SuperLogger.error(e.getMessage(), e);
                throw new ServiceException(e.getExceptionKey().toString(), e.getMessage());
            } catch (ServiceException e) {
                SuperLogger.error(e.getMessage(), e);
                throw new ServiceException("-1000", e.getMessage());
            } catch (Exception e) {
                SuperLogger.error(e.getMessage(), e);
                throw new ServiceException("-1000", "未知异常");
            }
        });
        return new JsonSubmitResponse("0", loginUser, input.getMsg_Batch_No()).toJson();
    }

    @Override
    public JsonQueryResult queryMo(JsonBase data) throws Exception {
        EnterpriseUser loginUser = validateUser(data);
        List<Inbox> inboxList = queryInboxList(loginUser, null);
        return new JsonQueryResult("0", loginUser, inboxList);
    }

    @Override
    public JsonQueryResult queryMo(JsonBase data, int pageSize) throws Exception {
        EnterpriseUser loginUser = validateUser(data);
        List<Inbox> inboxList = queryInboxList(loginUser, pageSize);
        return new JsonQueryResult("0", loginUser, inboxList);
    }

    @Override
    public JsonQueryResult queryReport(JsonBase data) throws Exception {
        EnterpriseUser loginUser = validateUser(data);
        List<Report> reportList = queryReportList(loginUser, null);
        return new JsonQueryResult("0", loginUser, reportList);
    }

    @Override
    public JsonQueryResult queryReport(JsonBase data, int maxcount) throws Exception {
        EnterpriseUser loginUser = validateUser(data);
        List<Report> reportList = queryReportList(loginUser, maxcount);
        return new JsonQueryResult("0", loginUser, reportList);
    }

    private EnterpriseUser validateUser(JsonBase data) {
        // 获取企业编号
        String enterpriseNo = data.getEnterprise_no();
        APIAssert.enterpriseNo(enterpriseNo);
        // 获取用户名
        String account = data.getAccount();
        APIAssert.account(account);
        EnterpriseUserExt enterpriseUserExt = new EnterpriseUserExt();
        enterpriseUserExt.setEnterprise_No(data.getEnterprise_no());
        enterpriseUserExt.setUser_Name(data.getAccount());
        List<EnterpriseUser> userList = DatabaseCache.getEnterpriseUserList(enterpriseUserExt);
        EnterpriseUser user = userList.size() == 0 ? null : userList.get(0);
        APIAssert.enterpriseUser(user);
        // 校验ip
        APIAssert.ip(data.getClientIp(), user.getApi_Ip());
        return user;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void notifyReport(HttpReport entity) throws Exception {
        ProtocolType protocolType = ProtocolType.valueOf(entity.getProtocolTypeCode().toUpperCase());
        EnterpriseUser user = DatabaseCache.getEnterpriseUserCachedById(entity.getEnterpriseUserId());
        HttpClient httpClient = new HttpClient();
        String content = "";
        String mediaType = "";
        if (protocolType.equals(ProtocolType.HTTP_XML)) {
            content = new XmlQueryResult("0", "成功", entity.getReports()).toReportXml();
            mediaType = HttpClient.MEDIA_TYPE_XML;
        }
        if (protocolType.equals(ProtocolType.HTTP_JSON)) {
            content = new JsonQueryResult("0", user, entity.getReports()).toReportJson();
            mediaType = HttpClient.MEDIA_TYPE_JSON;
        }
        httpClient.postAsync(user.getNotify_Report_Url(), content, mediaType, null);
    }

}

/**
 * @author volcano
 * @version V1.0
 * @date 2019年9月21日上午4:17:07
 */
class APIAssert extends Assert {

    public static void notEmpty(String data, String code, String message) {
        if (StringUtils.isEmpty(data)) {
            throw new ServiceException(code, message);
        }
    }

    public static void enterpriseNo(String no) {
        notEmpty(no, "-1", "缺失enterprise_no元素");
    }

    public static void account(String account) {
        notEmpty(account, "-1", "缺失account元素");
    }

    public static void password(String password) {
        notEmpty(password, "-1", "缺失password元素");
    }

    public static void sign(String sign) {
        notEmpty(sign, "-1", "缺失sign元素");
    }

    public static void timestamp(String timestamp) {
        notEmpty(timestamp, "-1", "缺失timestamp元素");
    }

    public static void phones(String phones) {
        notEmpty(phones, "-1", "缺失phones元素");
    }

    public static void content(String content) {
        notEmpty(content, "-1", "缺失content元素");
    }

    public static void ip(String sourceIp, String bindIp) {
        if (StringUtils.isEmpty(bindIp)) {
            return;
        }
        boolean validateIp = Arrays.asList(bindIp.split(Constant.MUTL_MOBILE_SPLIT)).stream()
                .anyMatch(item -> item.equals(sourceIp));
        if (!validateIp) {
            throw new ServiceException("-1", sourceIp + "IP未绑定");
        }
    }

    public static void subcode(String subcode) {
        if (StringUtils.isEmpty(subcode)) {
            return;
        }
        if (subcode.length() > 21) {
            throw new ServiceException("-1", "扩展码只能小于等于21位");
        }
    }

    public static void enterpriseUser(EnterpriseUser user) {
        if (user == null) {
            throw new ServiceException("-1", "企业编码或用户名错误或userid错误");
        }
    }

    public static Date sendtime(String sendtime) {
        if (StringUtils.isEmpty(sendtime)) {
            return null;
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmm");
        try {
            return df.parse(sendtime);
        } catch (ParseException e) {
            SuperLogger.error(e.getMessage(), e);
            throw new ServiceException("-1", "定时日期格式错误,yyyyMMddHHmm");
        }
    }

}