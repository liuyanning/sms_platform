package com.hero.wireless.web.service;

import com.drondea.wireless.config.Constant;
import com.drondea.wireless.util.DateTime;
import com.drondea.wireless.util.MailUtils;
import com.drondea.wireless.util.SecretUtil;
import com.drondea.wireless.util.ServiceException;
import com.drondea.wireless.util.SuperLogger;
import com.hero.wireless.web.config.DatabaseCache;
import com.hero.wireless.web.dao.business.IEnterpriseUserDAO;
import com.hero.wireless.web.entity.business.EnterpriseUser;
import com.hero.wireless.web.util.SMSUtil;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

//通知接口 （发短信、邮箱）
@Service("noticeManage")
public class NoticeManageImpl implements INoticeManage {

    @Resource(name = "IEnterpriseUserDAO")
    private IEnterpriseUserDAO<EnterpriseUser> enterpriseUserDAO;

    //平台发送短信接口（平台自用，默认发送角色为平台设置角色）
    @Override
    public boolean sendSms(String phones, String content) throws ServiceException {
        boolean result = false;
        try {
            checkParam(phones,"手机号码");
            checkParam(content,"短信内容");
            String url = DatabaseCache.getStringValueBySystemEnvAndCode("platform_default_submit_url",null);//http://ip/json/submit
            String enterprise_no = DatabaseCache.getStringValueBySystemEnvAndCode("platform_default_enterprise_no", null);
            String account = DatabaseCache.getStringValueBySystemEnvAndCode("platform_default_account", null);
            String http_sign_Key = DatabaseCache.getStringValueBySystemEnvAndCode("platform_default_http_sign_Key", null);
            String subCode = DatabaseCache.getStringValueBySystemEnvAndCode("platform_default_sub_code", "8888");
            //请求参数
            Map<String, String> params = assemblySubmitParam(phones,content,enterprise_no,account,http_sign_Key,subCode);
            //发送
            result = SMSUtil.sendSms(url,params,"utf-8");
        } catch (ServiceException e) {
            SuperLogger.error(e.getMessage());
        } catch (Exception e) {
            SuperLogger.error(e.getMessage(),e);
        }
        return result;
    }

    /**
     * 发送短信请求参数
     * @param phones
     * @param content
     * @param enterpriseNo
     * @param account
     * @param httpSignKey
     * @param subCode
     * @return
     */
    public Map<String, String> assemblySubmitParam(String phones, String content,String enterpriseNo
            ,String account,String httpSignKey,String subCode) {
        String timestamp = DateTime.getString(new Date(),DateTime.Y_M_D_H_M_S_S_2);
        String sign = SecretUtil.MD5(enterpriseNo+account+timestamp+httpSignKey);
        if(StringUtils.isEmpty(subCode)){
            subCode = DatabaseCache.getStringValueBySystemEnvAndCode("platform_default_sub_code", "8888");
        }
        Map<String, String> params = new HashMap<>();
        params.put("enterprise_no", enterpriseNo);
        params.put("account", account);
        params.put("phones", phones);
        params.put("content", content);
        params.put("timestamp", timestamp);//yyyyMMddHHmmssSSS
        params.put("sign", sign);//签名
        params.put("subcode",subCode);
//        params.put("sendtime", sendtime);//定时
        return params;
    }

    //发送邮件接口
    @Override
    public boolean sendEmail(String emailAddress, String title, String content) throws ServiceException {
        boolean result = false;
        checkParam(emailAddress,"收件人地址");
        checkParam(content,"发送内容");
        String[] split = emailAddress.split(Constant.MUTL_MOBILE_SPLIT);//多个地址分割
        for(int i=0; i<split.length; i++){
            if(StringUtils.isEmpty(split[i]))continue;
            try {
                MailUtils cn = new MailUtils();
                // 设置发件人地址、收件人地址和邮件标题 、内容
                cn.setAddress(DatabaseCache.getStringValueBySortCodeAndCode("sys_email_config","email_send_address",""),
                        split[i], StringUtils.isNotEmpty(title)?title:"通知", content);
                cn.send(DatabaseCache.getStringValueBySortCodeAndCode("sys_email_config","email_send_host",""),
                        DatabaseCache.getStringValueBySortCodeAndCode("sys_email_config","email_send_port",""),
                        DatabaseCache.getStringValueBySortCodeAndCode("sys_email_config","email_send_account",""),
                        DatabaseCache.getStringValueBySortCodeAndCode("sys_email_config","email_send_password","")
                );
                result = true;//只要发送成功一个地址 就为true
            } catch (Exception e) {
                SuperLogger.error(e.getMessage(),e);
            }
        }
        return result;
    }

    //重发短信使用，传参发送账号等信息
    @Override
    public boolean reSendSms(String phones, String content,String enterpriseNo,Integer enterpriseUserId,String subCode) throws ServiceException {
        boolean result = false;
        checkParam(phones,"手机号码");
        checkParam(content,"短信内容");
        checkParam(enterpriseNo,"发送企业信息");
        checkParam(enterpriseUserId+"","发送用户信息");
        try {
            EnterpriseUser user = enterpriseUserDAO.selectByPrimaryKey(enterpriseUserId);
            String url = DatabaseCache.getStringValueBySystemEnvAndCode("platform_default_submit_url",null);//http://ip/json/submit
            //请求参数
            Map<String, String> params = assemblySubmitParam(phones,content,enterpriseNo,user.getUser_Name(),user.getHttp_Sign_Key(),subCode);
            //发送
            result = SMSUtil.sendSms(url,params,"utf-8");
        } catch (Exception e) {
            SuperLogger.error(e.getMessage(),e);
        }
        return result;
    }

    private void checkParam(String param, String msg) {
        if(StringUtils.isEmpty(param) || "null".equals(param)){
            throw new ServiceException(msg+"不能为空！");
        }
    }
}
