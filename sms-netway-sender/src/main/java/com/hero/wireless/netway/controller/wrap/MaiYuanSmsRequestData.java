package com.hero.wireless.netway.controller.wrap;

import com.drondea.wireless.util.SecretUtil;
import com.drondea.wireless.util.ServiceException;
import com.hero.wireless.notify.*;
import com.hero.wireless.web.entity.business.EnterpriseUser;
import org.apache.commons.lang3.ObjectUtils;

public class MaiYuanSmsRequestData extends AbstractAPIRequestData {

    private MaiYuanJsonSubmit jsonSubmit;
    private MaiYuanJsonBase jsonBase;
    private String account;
    private String enterpriseNo;
    private EnterpriseUser user;
    private String userid;
    private String sign;
    private String timestamp;
    private String password;

    public MaiYuanSmsRequestData(MaiYuanJsonSubmit jsonSubmit) {
        this.sign = jsonSubmit.getSign();
        this.password = jsonSubmit.getPassword();
        this.account = jsonSubmit.getAccount();
        this.userid = jsonSubmit.getUserid();
        this.timestamp = jsonSubmit.getTimestamp();
        this.jsonSubmit = jsonSubmit;
    }

    public MaiYuanSmsRequestData(MaiYuanJsonBase jsonBase) {
        this.sign = jsonBase.getSign();
        this.password = jsonBase.getPassword();
        this.account = jsonBase.getAccount();
        this.userid = jsonBase.getUserid();
        this.timestamp = jsonBase.getTimestamp();
        this.jsonBase = jsonBase;
    }

    public MaiYuanJsonSubmit getJsonSubmit() {
        return jsonSubmit;
    }

    public void setJsonSubmit(MaiYuanJsonSubmit jsonSubmit) {
        this.jsonSubmit = jsonSubmit;
    }

    public MaiYuanJsonBase getJsonBase() {
        return jsonBase;
    }

    public void setJsonBase(MaiYuanJsonBase jsonBase) {
        this.jsonBase = jsonBase;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getEnterpriseNo() {
        return enterpriseNo;
    }

    public void setEnterpriseNo(String enterpriseNo) {
        this.enterpriseNo = enterpriseNo;
    }

    public EnterpriseUser getUser() {
        return user;
    }

    public void setUser(EnterpriseUser user) {
        this.user = user;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public boolean checkSign() {
        EnterpriseUser user = getEnterpriseUser(null, userid, account);
        if (ObjectUtils.isEmpty(user)) {
            throw new ServiceException("-1", "用户名错误或userid错误");
        }
        String sign = this.sign;
        String createSign = SecretUtil.MD5(user.getUser_Name() + user.getHttp_Sign_Key() + this.timestamp);
        if (!sign.equalsIgnoreCase(createSign)) {
            throw new ServiceException("-1", "签名错误");
        }
        this.account = user.getUser_Name();
        this.enterpriseNo = user.getEnterprise_No();
        this.user = user;
        return true;
    }

    public boolean checkSignV1() {
        EnterpriseUser user = getEnterpriseUser(null, userid, account);
        if(ObjectUtils.isEmpty(user)){
            throw new ServiceException("-1", "用户名错误或userid错误");
        }
        if (!user.getHttp_Sign_Key().equalsIgnoreCase(password)) {
            throw new ServiceException("-1", "签名错误");
        }
        this.account = user.getUser_Name();
        this.enterpriseNo = user.getEnterprise_No();
        this.user = user;
        return true;
    }

    @Override
    public JsonSubmit assembleSubmitRequestData() {
        jsonSubmit.setAccount(account);
        jsonSubmit.setEnterprise_no(enterpriseNo);
        jsonSubmit.setContent(jsonSubmit.getContent());
        jsonSubmit.setSubcode(jsonSubmit.getExtno());
        jsonSubmit.setPhones(jsonSubmit.getMobile());
        jsonSubmit.setSendtime(jsonSubmit.getSendTime());
        JsonMessage message = new JsonMessage();
        message.setPhones(jsonSubmit.getMobile());
        message.setContent(jsonSubmit.getContent());
        message.setSubcode(jsonSubmit.getExtno());
        message.setSendtime(jsonSubmit.getSendTime());
        jsonSubmit.getMessageList().add(message);
        return jsonSubmit;
    }

    @Override
    public JsonBase assembleBalaceRequestData() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public JsonBase assembleReportRequestData() {
        jsonBase.setAccount(account);
        jsonBase.setEnterprise_no(enterpriseNo);
        return jsonBase;
    }

    @Override
    public JsonBase assembleMoRequestData() throws Exception {
        jsonBase.setAccount(account);
        jsonBase.setEnterprise_no(enterpriseNo);
        return jsonBase;
    }

}
