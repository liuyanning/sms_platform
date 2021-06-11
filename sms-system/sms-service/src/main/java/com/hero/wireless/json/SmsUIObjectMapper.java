package com.hero.wireless.json;

import com.hero.wireless.web.entity.base.BaseEntity;
import com.hero.wireless.web.entity.base.Pagination;
import com.hero.wireless.web.entity.business.ext.BlackListExt;
import com.hero.wireless.web.entity.send.Inbox;
import com.hero.wireless.web.entity.send.Input;
import com.hero.wireless.web.entity.send.ReportNotify;
import com.hero.wireless.web.entity.send.Submit;
import com.hero.wireless.web.entity.send.ext.InputLogExt;
import com.hero.wireless.web.entity.send.ext.ReportExt;
import com.hero.wireless.web.entity.send.ext.ReportNotifyExt;
import com.hero.wireless.web.entity.send.ext.SubmitExt;

import java.util.List;

public class SmsUIObjectMapper extends LayUiObjectMapper {

    private static final long serialVersionUID = 1L;

    public SmsUIObjectMapper(boolean isBlur){
        super();
        if(!isBlur){
            // 脱敏
            addBlurProperty(InputLogExt.class, new String[] { "phone_Nos:PHONE_NO" });
            addBlurProperty(SubmitExt.class, new String[] { "phone_No:PHONE_NO" });
            addBlurProperty(Submit.class, new String[] { "phone_No:PHONE_NO" });
            addBlurProperty(ReportExt.class, new String[] { "phone_No:PHONE_NO" });
            addBlurProperty(ReportNotify.class, new String[] { "phone_No:PHONE_NO" });
            addBlurProperty(ReportNotifyExt.class, new String[] { "phone_No:PHONE_NO" });
            addBlurProperty(Inbox.class, new String[] { "phone_No:PHONE_NO" });
            addBlurProperty(Input.class, new String[] { "phone_Nos:PHONE_NO" });
            addBlurProperty(BlackListExt.class, new String[] { "phone_No:PHONE_NO" });
        }
    }
    public SmsUIObjectMapper(){
        super();
    }

    @Override
    public <T> String asSuccessString(List<T> data, Pagination pagination) {
        return super.asSuccessString(data, pagination.getTotalCount());
    }

    public <T> String asSuccessString(List<T> data, BaseEntity baseEntity) {
        return super.asSuccessString(data, baseEntity.getPagination().getTotalCount(), baseEntity);
    }

    public <T> String asFaildString(String msg) {
        return super.asFaildString(msg, null);
    }

    public <T> String asErrorString() {
        return super.asFaildString("未知错误", null);
    }
}
