package com.hero.wireless.netway.handler.netty;

import com.drondea.sms.message.ILongSMSMessage;
import com.drondea.wireless.config.ResultStatus;
import com.drondea.wireless.util.ServiceException;
import com.drondea.wireless.util.SuperLogger;
import com.google.common.cache.RemovalCause;
import com.google.common.cache.RemovalListener;
import com.hero.wireless.enums.DataStatus;
import com.hero.wireless.enums.MessageType;
import com.hero.wireless.enums.ProtocolType;
import com.hero.wireless.netway.config.InitSystemEnv;
import com.hero.wireless.netway.handler.LongMsgCache;
import com.hero.wireless.netway.service.impl.AbstractTcpService;
import com.hero.wireless.web.entity.business.EnterpriseUser;
import com.hero.wireless.web.entity.send.Input;
import com.hero.wireless.web.util.LocalCache;
import com.hero.wireless.web.util.SMSUtil;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import org.apache.commons.lang3.StringUtils;

import java.net.InetSocketAddress;
import java.util.Date;

/**
 * @version V3.0.0
 * @description: 网关业务接受到包的处理
 * @author: 刘彦宁
 * @date: 2020年09月25日09:20
 **/
public abstract class AbstractServerBusinessHandler extends ChannelDuplexHandler {

    private static int ONE_HOUR = 1 * 60 * 60;

    private final RemovalListener<String, LongMsgCache> removalListener = notification -> {
        RemovalCause cause = notification.getCause();
        //超时的才算
        if (!RemovalCause.EXPIRED.equals(cause)) {
            return;
        }
        //以下为长短信组装超时发送回执的逻辑
        String key = notification.getKey();
        LongMsgCache msgCache = notification.getValue();
        //缓存满或者缓存组装超时回执情况
        String[] msgIds = msgCache.getMsgIds();
        String[] mobiles = msgCache.getMobiles();
        String subCode = msgCache.getSubCode();
        //key由协议、批次号、用户id组成
        String[] keys = key.split("_");
        String protocolType = keys[0];
        String batchNumber = keys[1];
        String userId = keys[2];
        Long timestamp = msgCache.getTimestamp();
        SuperLogger.error(StringUtils.join(mobiles, ",") + "长短信组装失败,msgIds:" + StringUtils.join(msgIds, ",") + "原因：" + cause);
        doLongMsgTimeout(Integer.valueOf(userId), mobiles, subCode, batchNumber, msgIds, protocolType, new Date(timestamp), cause.name());
    };

    /**
     * 保存长短信的每个片段的msgId
     */
    private LocalCache<LongMsgCache> msgIdCache = new LocalCache<>(ONE_HOUR, removalListener);

    /**
     * 缓存下游的msgId
     * @param request
     * @param msgId
     * @param userId
     * @return 是否已经缓存完成
     */
    protected boolean saveMsgId2Cache(ILongSMSMessage request, String msgId, Integer userId, String[] mobiles,
                                      String subCode, long timestamp) {
        String localCacheKey = getService().genLocalCacheKey(request.getBatchNumber(), String.valueOf(userId));

        short pkNumber = request.getPkNumber();
        LongMsgCache msgCache = msgIdCache.get(localCacheKey);
        if (msgCache == null) {
            short pkTotal = request.getPkTotal();
            //第一次缓存注意线程安全
            msgCache = new LongMsgCache(pkTotal);
            LongMsgCache msgCachePre = msgIdCache.putIfAbsent(localCacheKey, msgCache);
            if (msgCachePre != null) {
                msgCache = msgCachePre;
            }
            msgCache.setMobiles(mobiles);
            msgCache.setSubCode(subCode);
            msgCache.setTimestamp(timestamp);
        }

        if (request.isMsgComplete()) {
            msgCache.setContent(request.getMsgContent());
        }
        return msgCache.saveCachedMsgId(pkNumber - 1, msgId);
    }

    protected LongMsgCache getLongMsgCache(ILongSMSMessage request, Integer userId) {
        String localCacheKey = getService().genLocalCacheKey(request.getBatchNumber(), String.valueOf(userId));
        LongMsgCache msgCache = msgIdCache.get(localCacheKey);
        if (msgCache != null) {
            msgIdCache.remove(localCacheKey);
            return msgCache;
        }
        return null;
    }

    protected void doBizSubmit(ILongSMSMessage requestMessage,EnterpriseUser enterpriseUser,
                               String[] mobiles, String subCode, final ChannelHandlerContext ctx,
                               String charset, String msgId, String protoType, Date inputDate){
        if (StringUtils.isNotEmpty(subCode) && subCode.startsWith(enterpriseUser.getTcp_User_Name())) {
            subCode = StringUtils.replaceOnce(subCode, enterpriseUser.getTcp_User_Name(), "");
        }

        String content;
        //获取响应给下游的msgId，用于report
        if (requestMessage.isLongMsg()) {
            LongMsgCache msgCache = getLongMsgCache(requestMessage, enterpriseUser.getId());
            if (msgCache == null) {
                SuperLogger.error(String.format("收集msgid失败====>%1$s", requestMessage));
                return;
            }
            msgId = msgCache.getMsgIdStr();
            content = msgCache.getContent();
        } else {
            content = requestMessage.getMsgContent();
        }

        InetSocketAddress socket = (InetSocketAddress) ctx.channel().remoteAddress();
        String clientIp = socket.getAddress().getHostAddress();

        // 长短信合并后的批次号
        String batchNumber = requestMessage.getBatchNumber();
        int result = doSubmits(enterpriseUser, mobiles, subCode, content,
                batchNumber, clientIp, charset, msgId, protoType ,inputDate);
        if (result != 0) {
            SuperLogger.warn(String.format("response已响应：0，业务检查失败====>%1$s", requestMessage.toString()));
        }
    }

    private int doSubmits(EnterpriseUser enterpriseUser, String[] mobiles, String spNo, String content, String batchNumber,
                          String clientIp, String charset, String msgId, String protocolType, Date inputDate) {
        try {
            Input input = buildInput(enterpriseUser, mobiles, spNo, content, batchNumber, clientIp, charset, msgId, protocolType, inputDate);
            return getService().saveInput(input);
        } catch (Exception e) {
            SuperLogger.error(e.getMessage(), e);
            return -1;
        }
    }

    /**
     * 长短信组装超时使用
     * @param userId
     * @param mobiles
     * @param spNo
     * @param batchNumber
     * @param msgIds
     * @param protocolType
     * @param inputDate
     * @param cause
     * @return
     */
    private void doLongMsgTimeout(Integer userId, String[] mobiles, String spNo, String batchNumber,
                              String[] msgIds, String protocolType, Date inputDate, String cause) {
        EnterpriseUser enterpriseUser = InitSystemEnv.USER_INFOS.get(userId);
        Input input = buildInput(enterpriseUser, mobiles, spNo, "", batchNumber, "127.0.0.1", "UCS2", "", protocolType, inputDate);
        SMSUtil.notifyNetWayReportByMsgId(input, mobiles, msgIds, "系统错误，内容不完整" + cause);
    }

    private Input buildInput(EnterpriseUser enterpriseUser, String[] mobiles, String spNo, String content, String batchNumber,
                             String clientIp, String charset, String msgId, String protocolType, Date inputDate) {
        String mobileCompose = StringUtils.join(mobiles, ",");
        // 保存input 并通知分拣
        Input input = new Input();
        input.setProtocol_Type_Code(protocolType);
        input.setMessage_Type_Code(MessageType.SMS.toString());
        input.setSource_IP(clientIp);
        input.setEnterprise_No(enterpriseUser.getEnterprise_No());
        input.setEnterprise_User_Id(enterpriseUser.getId());
        input.setInput_Date(inputDate);
        if (ProtocolType.SMPP.toString().equalsIgnoreCase(protocolType)) {
            String mobile = mobiles[0];
            String countryCode = SMSUtil.getCountryCOdeByPhoneNo(mobile);
            if (countryCode == null) {
                throw new ServiceException(ResultStatus.COUNTRY_CODE_IS_NULL);
            }
            mobile = mobile.replaceFirst(countryCode, "");
            input.setPhone_Nos(mobile);
            input.setCountry_Code(countryCode);
        } else {
            input.setPhone_Nos(mobileCompose);
            input.setCountry_Code("86");
        }

        input.setContent(content);
        input.setSub_Code(spNo);
        input.setCharset(charset);
        input.setMsg_Batch_No(batchNumber);
        input.setEnterprise_Msg_Id(msgId);
        input.setData_Status_Code(DataStatus.NORMAL.toString());
        input.setGate_Ip(InitSystemEnv.LOCAL_IP);
        return input;
    }

    /**
     * 获取service实现
     * @return
     */
    protected abstract AbstractTcpService getService();

}
