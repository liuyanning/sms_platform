package com.hero.wireless.netway.controller.wrap;

import com.drondea.wireless.util.DateTime;
import com.drondea.wireless.util.SecretUtil;
import com.drondea.wireless.util.ServiceException;
import com.hero.wireless.notify.JsonBase;
import com.hero.wireless.notify.JsonMessage;
import com.hero.wireless.notify.JsonSubmit;
import com.hero.wireless.notify.mms.MASMMSJsonSubmit;
import com.hero.wireless.web.entity.business.EnterpriseUser;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

public class MASMMSRequestData extends AbstractAPIRequestData {

    private String reqeustStr;
    private MASMMSJsonSubmit jsonSubmit;
    private String enterprise_no;
    private String account;

    public MASMMSRequestData(String json) {
        this.reqeustStr = json;
        this.jsonSubmit = MASMMSJsonSubmit.parseJson(reqeustStr);
    }

    public String getReqeustStr() {
        return reqeustStr;
    }

    public MASMMSJsonSubmit getJsonSubmit() {
        return jsonSubmit;
    }

    public void setReqeustStr(String reqeustStr) {
        this.reqeustStr = reqeustStr;
    }

    public void setJsonSubmit(MASMMSJsonSubmit jsonSubmit) {
        this.jsonSubmit = jsonSubmit;
    }

    public String getEnterprise_no() {
        return enterprise_no;
    }

    public String getAccount() {
        return account;
    }

    public void setEnterprise_no(String enterprise_no) {
        this.enterprise_no = enterprise_no;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    @Override
    public boolean checkSign() throws Exception {
        jsonSubmit = MASMMSJsonSubmit.parseJson(reqeustStr);
        //submit.getAppKey() = user_name
        EnterpriseUser user = getEnterpriseUser(jsonSubmit.getEnterprise_no(), null, jsonSubmit.getAppKey());
        if (ObjectUtils.isEmpty(user)) {
            throw new ServiceException("-1", "用户名错误");
        }
        String sign = jsonSubmit.getSign();
        // sign = modeId+time+appKey+secret;
        String createSign = SecretUtil.MD5(jsonSubmit.getModeId() + jsonSubmit.getTs() + jsonSubmit.getAppKey() + user.getHttp_Sign_Key()).toLowerCase();
        if (!sign.equalsIgnoreCase(createSign)) {
            throw new ServiceException("-1", "签名错误");
        }
        this.enterprise_no = user.getEnterprise_No();
        this.account = user.getUser_Name();
        return true;
    }

    @Override
    public JsonBase assembleBalaceRequestData() throws Exception {
        return null;
    }

    @Override
    public JsonBase assembleReportRequestData() throws Exception {
        return null;
    }

    @Override
    public JsonSubmit assembleSubmitRequestData() throws Exception {
        JsonMessage message = new JsonMessage();
        message.setPhones(StringUtils.join(jsonSubmit.getMobiles(), ","));
        message.setContent(jsonSubmit.getModeId());
        message.setSubcode(jsonSubmit.getSubcode());
        message.setSendtime(jsonSubmit.getSendTime());
        jsonSubmit.getMessageList().add(message);
        jsonSubmit.setEnterprise_no(enterprise_no);
        jsonSubmit.setAccount(account);
        jsonSubmit.setTimestamp(DateTime.getString(new Date(), DateTime.Y_M_D_H_M_S_S_2));
        return jsonSubmit;
    }

    @Override
    public JsonBase assembleMoRequestData() throws Exception {
        return null;
    }


    public static void main(String[] args) {
        long time = System.currentTimeMillis();
        System.out.println(time);
        String string = "259426+time+rpnbcs+112233";
        System.out.println(SecretUtil.MD5(string).toLowerCase());
    }
}
