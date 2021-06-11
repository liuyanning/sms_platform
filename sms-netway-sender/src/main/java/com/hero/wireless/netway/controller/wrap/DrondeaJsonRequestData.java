package com.hero.wireless.netway.controller.wrap;

import com.drondea.wireless.util.SecretUtil;
import com.drondea.wireless.util.ServiceException;
import com.hero.wireless.notify.JsonBase;
import com.hero.wireless.notify.JsonMessage;
import com.hero.wireless.notify.JsonSubmit;
import com.hero.wireless.web.entity.business.EnterpriseUser;
import org.apache.commons.lang3.ObjectUtils;

public class DrondeaJsonRequestData extends AbstractAPIRequestData {

    private String reqeustStr;

    public DrondeaJsonRequestData(String json) {
        this.reqeustStr = json;
    }

    @Override
    public boolean checkSign() throws Exception {
        JsonSubmit submit = JsonSubmit.parseJson(reqeustStr);
        EnterpriseUser user = getEnterpriseUser(submit.getEnterprise_no(), null, submit.getAccount());
        if (ObjectUtils.isEmpty(user)) {
            throw new ServiceException("-1", "企业编码或用户名错误或userid错误");
        }
        String sign = submit.getSign();
        String createSign = SecretUtil.MD5(user.getEnterprise_No() + user.getUser_Name() + submit.getTimestamp() + user.getHttp_Sign_Key());
        if (!sign.equalsIgnoreCase(createSign)) {
            throw new ServiceException("-1", "签名错误");
        }
        return true;
    }

    @Override
    public JsonBase assembleBalaceRequestData() throws Exception {
        JsonBase jsonBase = JsonBase.parseJson(reqeustStr);
        return jsonBase;
    }

    @Override
    public JsonBase assembleReportRequestData() throws Exception {
        JsonBase base = JsonBase.parseJson(reqeustStr);
        return base;
    }

    @Override
    public JsonSubmit assembleSubmitRequestData() throws Exception {
        JsonSubmit submit = JsonSubmit.parseJson(reqeustStr);
        JsonMessage message = new JsonMessage();
        message.setPhones(submit.getPhones());
        message.setContent(submit.getContent());
        message.setSubcode(submit.getSubcode());
        message.setSendtime(submit.getSendtime());
        message.setCountrycode(submit.getCountrycode());
        message.setCharset(submit.getCharset());
        submit.getMessageList().add(message);
        return submit;
    }

    @Override
    public JsonBase assembleMoRequestData() throws Exception {
        JsonBase base = JsonBase.parseJson(reqeustStr);
        return base;
    }

}
