package com.hero.wireless.netway.service;

import com.drondea.sms.type.DefaultEventGroupFactory;
import com.drondea.wireless.config.Constant;
import com.drondea.wireless.util.SuperLogger;
import com.hero.wireless.enums.ProtocolType;
import com.hero.wireless.sms.sender.service.ISenderSmsService;
import com.hero.wireless.web.entity.send.Inbox;
import com.hero.wireless.web.entity.send.Report;
import com.hero.wireless.web.service.ISendManage;
import com.hero.wireless.web.util.ApplicationContextUtil;
import com.hero.wireless.web.util.GlobalRepeat;
import com.hero.wireless.web.util.QueueUtil;
import org.apache.commons.lang3.ObjectUtils;

import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @version V3.0.0
 * @description: 拉取用户回执和MO的消息
 * @author: 刘彦宁
 * @date: 2020年07月23日15:36
 **/
public class ReportMsgPuller {

    private Integer userId;
    private ProtocolType protocolType;
    private volatile boolean start;
    private ISendManage sendManage;
    private Long beginId = 0L;
    private ISenderSmsService senderSmsService;

    public ReportMsgPuller(Integer userId, ProtocolType protocolType) {
        this.userId = userId;
        this.protocolType = protocolType;
        this.sendManage = ApplicationContextUtil.getBean("sendManage");
        this.senderSmsService = ApplicationContextUtil.getBean("defaultSenderServiceImpl");
    }

    public void start(long delay) {
        this.start = true;
        pullMessage(delay);
    }

    public void pullMessage(long delay) {
        ScheduledExecutorService executor = DefaultEventGroupFactory.getInstance().getScheduleExecutor();

        executor.schedule(() -> {

            if (start == false) {
                SuperLogger.debug("用户关闭，停止拉取消息========>" + userId);
                return;
            }

            //有消息,不延时
            int nextDelay = 0;

            //从数据库获取Report
            boolean result = getReportList();
            //从数据库获取MO
            queryInboxList(userId, 50);
            //没有消息要延时1秒再获取
            if (!result) {
                nextDelay = 1000;
            }
            pullMessage(nextDelay);
        }, delay, TimeUnit.MILLISECONDS);
    }

    private boolean getReportList() {
        int reportCount = QueueUtil.getReportCount(protocolType, userId);
        //缓存太多先不拉取
        if (reportCount > 500) {
            return false;
        }
        String key = "user:" + userId;
        synchronized (key.intern()) {
            List<Report> reportAwait = senderSmsService.getReportAwaitAndDel(userId, beginId, false);
            if (ObjectUtils.isEmpty(reportAwait)) {
                return false;
            }
            //记录最后一个id
            beginId = reportAwait.get(reportAwait.size() - 1).getId();
            QueueUtil.notifyReportAwait(protocolType, userId, reportAwait);
        }

        return true;
    }

    private void queryInboxList(Integer userId, Integer pageSize) {
        String key = Constant.SOURCE_TYPE_CODE_QUERY_REPORT + ":inbox_" + userId;
        if (!GlobalRepeat.getAndSet(key)) {
            return;
        }
        int moCount = QueueUtil.getMoCount(protocolType, userId);
        if (moCount > 50) {
            return;
        }
        List<Inbox> inboxList = this.sendManage.queryNotifyInboxList(userId, pageSize);

        // 当拉去的集合为空时 限制拉取频次
        if (ObjectUtils.isEmpty(inboxList)) {
            // 超时时间10秒
            GlobalRepeat.expire(key,10, TimeUnit.SECONDS);
            return;
        }
        GlobalRepeat.remove(key);
        QueueUtil.notifyMO(protocolType, userId, inboxList);
    }

    public void stop() {
        this.start = false;
    }

}
