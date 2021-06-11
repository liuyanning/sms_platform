package com.hero.wireless.netway.controller.wrap;

import com.drondea.wireless.util.SecretUtil;
import com.drondea.wireless.util.ServiceException;
import com.hero.wireless.notify.Constants;
import com.hero.wireless.notify.JsonBase;
import com.hero.wireless.notify.JsonMessage;
import com.hero.wireless.notify.JsonSubmit;
import com.hero.wireless.web.config.DatabaseCache;
import com.hero.wireless.web.entity.business.EnterpriseUser;
import org.apache.commons.lang3.ObjectUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class DrondeaXmlRequestData extends AbstractAPIRequestData {

    private String reqeustStr;
    private String enterprise_no;
    private String account;
    private String password;

    public DrondeaXmlRequestData(String json) {
        this.reqeustStr = json;
    }

    public String getJsonSubmit() {
        return reqeustStr;
    }

    public void setJsonSubmit(String jsonSubmit) {
        this.reqeustStr = jsonSubmit;
    }

    public String getEnterprise_no() {
        return enterprise_no;
    }

    public String getAccount() {
        return account;
    }

    public String getPassword() {
        return password;
    }

    public void setEnterprise_no(String enterprise_no) {
        this.enterprise_no = enterprise_no;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean checkSign() throws Exception {
        return false;
    }

    public void checkPassword() throws Exception {
        // 获取密码
        String password = this.password;
        EnterpriseUser userInfo = DatabaseCache.getEnterpriseUserByNoAndUserName(null, this.account);
        if (ObjectUtils.isEmpty(userInfo)) {
            throw new ServiceException("-1", "企业编码或用户名错误或userid错误");
        }
        if (!SecretUtil.MD5(password).equals(userInfo.getWeb_Password())) {
            throw new ServiceException("-1", "用户名或密码错误");
        }
        this.enterprise_no = userInfo.getEnterprise_No();
    }

    @Override
    public JsonBase assembleBalaceRequestData() throws Exception {
        JsonBase jsonBase = new JsonBase();
        Document doc = DocumentHelper.parseText(reqeustStr);
        Element rootElement = doc.getRootElement();
        String account = rootElement.elementText(Constants.ACCOUNT.toString());
        String password = rootElement.elementText(Constants.PASSWORD.toString());
        this.password = password;
        this.account = account;
        jsonBase.setTimestamp(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()));
        jsonBase.setAccount(account);
        return jsonBase;
    }

    @Override
    public JsonBase assembleReportRequestData() throws Exception {
        JsonBase jsonBase = new JsonBase();
        Document doc = DocumentHelper.parseText(reqeustStr);
        Element rootElement = doc.getRootElement();
        String account = rootElement.elementText(Constants.ACCOUNT.toString());
        String password = rootElement.elementText(Constants.PASSWORD.toString());
        this.password = password;
        this.account = account;
        jsonBase.setTimestamp(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()));
        jsonBase.setAccount(account);
        return jsonBase;
    }

    @Override
    public JsonBase assembleMoRequestData() throws Exception {
        JsonBase jsonBase = new JsonBase();
        Document doc = DocumentHelper.parseText(reqeustStr);
        Element rootElement = doc.getRootElement();
        String account = rootElement.elementText(Constants.ACCOUNT.toString());
        String password = rootElement.elementText(Constants.PASSWORD.toString());
        this.password = password;
        this.account = account;
        jsonBase.setTimestamp(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()));
        jsonBase.setAccount(account);
        return jsonBase;
    }

    @Override
    public JsonSubmit assembleSubmitRequestData() throws Exception {
        JsonSubmit json = new JsonSubmit();
        json.setTimestamp(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()));
        Document doc = DocumentHelper.parseText(reqeustStr);
        Element rootElement = doc.getRootElement();
        String account = rootElement.elementText(Constants.ACCOUNT.toString());
        String password = rootElement.elementText(Constants.PASSWORD.toString());
        List<?> messageList = rootElement.elements(Constants.MESSAGE.toString());
        messageList.forEach(item -> {
            JsonMessage message = new JsonMessage();
            Element messageElemnet = (Element) item;
            message.setPhones(messageElemnet.elementText(Constants.PHONES.toString()));
            message.setContent(messageElemnet.elementText(Constants.CONTENT.toString()));
            message.setSubcode(messageElemnet.elementText(Constants.SUBCODE.toString()));
            message.setSendtime(messageElemnet.elementText(Constants.SENDTIME.toString()));
            message.setCountrycode(messageElemnet.elementText(Constants.COUNTRYCODE.toString()));
            message.setCharset(messageElemnet.elementText(Constants.CHARSET.toString()));
            json.getMessageList().add(message);
        });
        this.password = password;
        this.account = account;
        json.setAccount(account);
        json.setPassword(password);
        return json;
    }

}
