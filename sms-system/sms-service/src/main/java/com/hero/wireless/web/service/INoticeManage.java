package com.hero.wireless.web.service;

import com.drondea.wireless.util.ServiceException;

/**
 * 通知接口 （发短信、邮箱）
 *INoticeManage.java
 *
 * @author wjl
 * @date 2020年2月27日下午5:00:16
 */
public interface INoticeManage {

    /**
     * 发短信
     * @param phones 发送手机号 必填 多个用“,”分割
     * @param content 必填
     */
    boolean sendSms(String phones,String content) throws ServiceException;

    /**
     * 发邮件
     * @param emailAddress 收件人邮箱地址 必填 多个用“,”分割
     * @param title 邮件标题 推荐填写
     * @param content 发送内容 必填
     */
    boolean sendEmail(String emailAddress,String title,String content) throws ServiceException;

    /**
     * 重发短信接口，发送账号信息必传
     * @param phones
     * @param content
     * @param enterpriseNo
     * @param enterpriseUserId
     * @param subCode
     * @return
     * @throws ServiceException
     */
    boolean reSendSms(String phones, String content,String enterpriseNo,Integer enterpriseUserId,String subCode) throws ServiceException;
}
