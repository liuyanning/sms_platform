package com.hero.wireless.netway.service.impl;

import com.drondea.wireless.util.ServiceException;
import com.drondea.wireless.util.SuperLogger;
import com.hero.wireless.enums.MoSpType;
import com.hero.wireless.netway.service.ITcpService;
import com.hero.wireless.web.entity.send.Inbox;
import com.hero.wireless.web.entity.send.Input;
import com.hero.wireless.web.entity.send.ReportNotify;
import com.hero.wireless.web.service.ISendManage;
import com.hero.wireless.web.util.QueueUtil;
import com.hero.wireless.web.util.SMSUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import javax.annotation.Resource;

/**
 * 默认实现 需要和 Sender工程协调 MQ
 *
 * @author zly
 */
public abstract class AbstractTcpService implements ITcpService {

    @Resource(name = "sendManage")
    private ISendManage sendManage;

    @Override
    public int saveInput(Input input) throws Exception {
        try {
            sendManage.batchInputSms(input);
        } catch (ServiceException e) {
            SuperLogger.error(e.getMessage(), e);
            //保存回执
            SMSUtil.notifyNetWayReport(input, e, e.getMessage());
            return NumberUtils.toInt(e.getErrorCode(), -1);
        }
        return 0;
    }

    public void saveReportNotify(ReportNotify reportNotify) {
        QueueUtil.notifySaveReportNotify(reportNotify);
    }

    /**
     * 生成存储下游msgId本地缓存的key
     * @param bachNum
     * @param userId
     * @return
     */
    public abstract String genLocalCacheKey(String bachNum, String userId);

    /**
     * 根据配置的推送类型计算MO的码号
     * @param userName
     * @param moSpTypeCode
     * @param inbox
     * @return
     */
    protected String getDestId(String userName, String moSpTypeCode, Inbox inbox) {
        // 上行码号类型
        String moSpType = StringUtils.isEmpty(moSpTypeCode) ? MoSpType.VIRTUAL_CUSTOM.toString() : moSpTypeCode;
        if (MoSpType.VIRTUAL_CUSTOM.equals(moSpType)) {
            return userName
                    + StringUtils.defaultString(inbox.getInput_Sub_Code(), "");
        } else {
            return userName + StringUtils.defaultString(inbox.getSub_Code(), "");
        }
    }


}
