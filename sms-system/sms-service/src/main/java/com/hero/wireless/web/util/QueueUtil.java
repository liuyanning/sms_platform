package com.hero.wireless.web.util;

import com.drondea.wireless.util.SuperLogger;
import com.hero.wireless.enums.DataStatus;
import com.hero.wireless.enums.ProtocolType;
import com.hero.wireless.sms.sender.service.ISenderSmsService;
import com.hero.wireless.sort.ISortSMSService;
import com.hero.wireless.web.config.DatabaseCache;
import com.hero.wireless.web.dao.send.IReportNotifyAwaitDAO;
import com.hero.wireless.web.dao.send.ISubmitAwaitDAO;
import com.hero.wireless.web.dao.send.ext.ISubmitExtDAO;
import com.hero.wireless.web.entity.business.Alarm;
import com.hero.wireless.web.entity.business.EnterpriseUser;
import com.hero.wireless.web.entity.send.*;
import com.hero.wireless.web.service.IReportNotifyService;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * MQUtil
 *
 * @author Lenovo
 */
@Service
public class QueueUtil {

    private static QueueUtil queueUtil;

    @Resource(name = "defaultSenderServiceImpl")
    private ISenderSmsService senderSmsService;
    @Resource(name = "reportNotifyService")
    private IReportNotifyService reportNotifyService;
    @Resource(name = "sortSmsService")
    protected ISortSMSService sortSmsService;
    @Resource(name = "submitExtDAO")
    private ISubmitExtDAO submitExtDAO;
    @Resource(name = "ISubmitAwaitDAO")
    protected ISubmitAwaitDAO<SubmitAwait> submitAwaitDAO;
    @Resource(name = "IReportNotifyAwaitDAO")
    protected IReportNotifyAwaitDAO<ReportNotifyAwait> reportNotifyAwaitDAO;

    @PostConstruct
    public void init() {
        queueUtil = this;
    }


    private static final ConcurrentHashMap<String, Queue<Submit>> SUBMIT_QUEUE_MAP = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<String, Queue<Report>> USER_NOTIFY_QUEUE_MAP = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<String, Queue<Inbox>> USER_INBOX_QUEUE_MAP = new ConcurrentHashMap<>();

    private static final Queue<InputLog> INSERT_INPUT_LOG_QUEUE = new ConcurrentLinkedQueue<>();

    private static final Queue<Submit> INSERT_SUBMITED_QUEUE = new ConcurrentLinkedQueue<>();

    private static final Queue<Report> INSERT_REPORT_QUEUE = new ConcurrentLinkedQueue<>();

    private static final Queue<ReportNotify> INSERT_REPORT_NOTIFY_QUEUE = new ConcurrentLinkedQueue<>();


    /**
     * Title: HttpReport Description:
     *
     * @author yjb
     * @date 2020-03-12
     */
    public static class HttpReport {
        private String protocolTypeCode;
        private int enterpriseUserId;
        private boolean isPushMQ;//是否放进MQ里
        private List<Report> reports = new CopyOnWriteArrayList<>();

        public boolean isPushMQ() {
            return isPushMQ;
        }

        public void setPushMQ(boolean pushMQ) {
            isPushMQ = pushMQ;
        }

        public String getProtocolTypeCode() {
            return protocolTypeCode;
        }

        public int getEnterpriseUserId() {
            return enterpriseUserId;
        }

        public List<Report> getReports() {
            return reports;
        }

        public void setProtocolTypeCode(String protocolTypeCode) {
            this.protocolTypeCode = protocolTypeCode;
        }

        public void setEnterpriseUserId(int enterpriseUserId) {
            this.enterpriseUserId = enterpriseUserId;
        }

        public void setReports(List<Report> reports) {
            this.reports = reports;
        }

        /**
         * @param protocolTypeCode
         * @param enterpriseUserId
         */
        public HttpReport(String protocolTypeCode, int enterpriseUserId) {
            super();
            this.protocolTypeCode = protocolTypeCode;
            this.enterpriseUserId = enterpriseUserId;
        }

        /**
         * @param protocolTypeCode
         * @param enterpriseUserId
         */
        public HttpReport(String protocolTypeCode, int enterpriseUserId ,boolean isPushMQ) {
            super();
            this.protocolTypeCode = protocolTypeCode;
            this.enterpriseUserId = enterpriseUserId;
            this.isPushMQ = isPushMQ;
        }

        /**
         *
         */
        public HttpReport() {
            super();
        }
    }

    @Scheduled(fixedDelay = 1000)
    public void insertInputLog() {
        List<InputLog> insertInputLogList = new ArrayList<InputLog>();
        while (true) {
            InputLog inputLog = INSERT_INPUT_LOG_QUEUE.poll();
            if (inputLog == null) {
                break;
            }
            insertInputLogList.add(inputLog);

            if (insertInputLogList.size() > 50000) {
                SuperLogger.warn("每秒处理速度超过50000，臣妾处理不了==================insertInputLog");
                break;
            }
        }
        if (!insertInputLogList.isEmpty()) {
            ListUtils.partition(insertInputLogList, 200).forEach(item -> {
                senderSmsService.saveInputLog(item);
            });
            SuperLogger.trace("insertInputLog=======>" + insertInputLogList.size());
        }
    }

    @Scheduled(fixedDelay = 1000)
    public void insertSubmit() {
        List<Submit> insertSubmitList = new ArrayList<>();
        while (true) {
            Submit submit = INSERT_SUBMITED_QUEUE.poll();
            if (submit == null) {
                break;
            }
            insertSubmitList.add(submit);
            if (insertSubmitList.size() > 50000) {
                SuperLogger.trace("每秒处理速度超过50000，臣妾处理不了==================notifyInsertSubmit");
                break;
            }
        }

        if (!insertSubmitList.isEmpty()) {
            ListUtils.partition(insertSubmitList, 200).forEach(item -> {
                senderSmsService.saveSubmit(item);
            });
            SuperLogger.trace("notifyInsertSubmit保存条数=======>" + insertSubmitList.size());
        }
    }

    @Scheduled(fixedDelay = 1000)
    public void notifyInsertReport() {
        List<Report> insertReportList = new ArrayList<>();
        while (true) {
            Report report = INSERT_REPORT_QUEUE.poll();
            if (report == null) {
                break;
            }
            insertReportList.add(report);
            if (insertReportList.size() > 50000) {
                break;
            }
        }

        if (!insertReportList.isEmpty()) {
            ListUtils.partition(insertReportList, 200).forEach(item -> {
                senderSmsService.saveReport(item);
            });
            SuperLogger.debug("notifyInsertReport保存条数=======>" + insertReportList.size());
        }

    }

    @Scheduled(fixedDelay = 1000)
    public void notifyInsertReportNotify() {
        List<ReportNotify> insertReportNotifyList = new ArrayList<ReportNotify>();
        while (true) {
            ReportNotify reportNotify = INSERT_REPORT_NOTIFY_QUEUE.poll();
            if (reportNotify == null) {
                break;
            }
            insertReportNotifyList.add(reportNotify);
            if (insertReportNotifyList.size() > 50000) {
                SuperLogger.warn("每秒处理速度超过50000，程序受不了==================notifyInsertReportNotify");
                break;
            }
        }
        if (!insertReportNotifyList.isEmpty()) {
            ListUtils.partition(insertReportNotifyList, 200).forEach(item -> {
                reportNotifyService.batchInsertReportNotify(item);
            });
            SuperLogger.debug("notifyInsertReportNotify保存条数=======>" + insertReportNotifyList.size());
        }
    }


    /**
     * 通知提交
     *
     * @param submit
     * @return
     */
    public static void notifySubmit(Submit submit, String level) {
        SubmitAwait submitAwait = new SubmitAwait();
        CopyUtil.SUBMIT_SUBMITAWAIT_COPIER.copy(submit, submitAwait, null);
        queueUtil.submitAwaitDAO.insert(submitAwait);
    }

    /**
     * 拉取的数据放到缓存
     * @param channelNo
     * @param submitList
     */
    public static void notifySubmitAwait(String channelNo, List<Submit> submitList) {
        Queue<Submit> queue = CacheUtil.getMapCachedQueue(getSubmitCachedKey(channelNo), SUBMIT_QUEUE_MAP);
        queue.addAll(submitList);
    }

    public static String getSubmitCachedKey(String channelNo) {
        return channelNo;
    }

    public static Submit getSubmit(String channelNo) {
        Queue<Submit> submits = SUBMIT_QUEUE_MAP.get(getSubmitCachedKey(channelNo));
        if (submits == null) {
            return null;
        }
        return submits.poll();
    }

    public static int getSubmitCount(String channelNo) {
        Queue<Submit> submits = SUBMIT_QUEUE_MAP.get(getSubmitCachedKey(channelNo));
        if (submits == null) {
            return 0;
        }
        return submits.size();
    }

    public static String getNotifyKey(ProtocolType protocolType, int userId) {
        return protocolType.toString() + ":" + userId;
    }

    public static void notifyReportAwait(ProtocolType protocolType, Integer userId, List<Report> reportList) {
        Queue<Report> queue = CacheUtil.getMapCachedQueue(getNotifyKey(protocolType, userId), USER_NOTIFY_QUEUE_MAP);
        queue.addAll(reportList);
    }

    public static void notifyMO(ProtocolType protocolType, Inbox inbox) {
        Queue<Inbox> queue = CacheUtil.getMapCachedQueue(getNotifyKey(protocolType, inbox.getEnterprise_User_Id()), USER_INBOX_QUEUE_MAP);
        queue.offer(inbox);
    }

    public static void notifyMO(ProtocolType protocolType, Integer userId, List<Inbox> inboxList) {
        Queue<Inbox> queue = CacheUtil.getMapCachedQueue(getNotifyKey(protocolType, userId), USER_INBOX_QUEUE_MAP);
        queue.addAll(inboxList);
    }

    public static int getMoCount(ProtocolType protocolType, Integer userId) {
        Queue<Inbox> queue = CacheUtil.getMapCachedQueue(getNotifyKey(protocolType, userId), USER_INBOX_QUEUE_MAP);
        return queue.size();
    }

    public static Report getUserReport(ProtocolType protocolType, int userId) {
        Queue<Report> queue = CacheUtil.getMapCachedQueue(getNotifyKey(protocolType, userId), USER_NOTIFY_QUEUE_MAP);
        return queue.poll();
    }
    public static int getReportCount(ProtocolType protocolType, int userId) {
        Queue<Report> queue = CacheUtil.getMapCachedQueue(getNotifyKey(protocolType, userId), USER_NOTIFY_QUEUE_MAP);
        return queue.size();
    }

    public static Inbox getUserMO(ProtocolType protocolType, int userId) {
        Queue<Inbox> queue = CacheUtil.getMapCachedQueue(getNotifyKey(protocolType, userId), USER_INBOX_QUEUE_MAP);
        return queue.poll();
    }

    /**
     * 保存mo
     *
     * @param inbox
     * @return
     */
    public static void saveMo(Inbox inbox) {
        queueUtil.senderSmsService.saveMO(inbox);
    }

    /**
     * 保存状态报告
     *
     * @param entity
     * @return
     */
    public static void saveReport(Report entity) {
        saveReport(Arrays.asList(entity));
    }

    /**
     * @param submits
     * @return
     * @author volcano
     * @date 2019年10月19日下午11:51:29
     * @version V1.0
     */
    public static void saveSubmit(List<Submit> submits) {
        submits.forEach(item -> {
            INSERT_SUBMITED_QUEUE.offer(item);
        });
    }

    /**
     * @param submit
     * @return
     * @author volcano
     * @date 2020年2月26日12:09:46
     * @version V1.0
     */
    public static void saveSubmit(Submit submit) {
        INSERT_SUBMITED_QUEUE.offer(submit);
    }



    /**
     * @param inputLogs
     * @return
     * @author volcano
     * @date 2019年10月24日下午6:56:01
     * @version V1.0
     */
    public static void saveInputLogs(List<InputLog> inputLogs) {
        inputLogs.forEach(item -> {
            INSERT_INPUT_LOG_QUEUE.offer(item);
        });
    }

    /**
     * 单条
     *
     * @param inputLog
     */
    public static void notifySaveInputLog(InputLog inputLog) {
        INSERT_INPUT_LOG_QUEUE.offer(inputLog);
    }

    public static void saveReport(List<Report> reports) {
        reports.forEach(item -> {
            notifyReport(item);
            INSERT_REPORT_QUEUE.offer(item);
        });
    }

    /**
     * 通知保存通知客户记录
     *
     * @param reportNotify
     */
    public static void notifySaveReportNotify(ReportNotify reportNotify) {
        INSERT_REPORT_NOTIFY_QUEUE.offer(reportNotify);
    }

    public static void notifySorter(Input input, Integer priorityLevel) {
        input.setData_Status_Code(DataStatus.NORMAL.toString());
        try {
            queueUtil.sortSmsService.sort(input);
        } catch (Exception e) {
            SuperLogger.error("分拣错误");
            e.printStackTrace();
        }
    }

    /**
     * 通知预警
     *
     * @return
     * @author YJB
     */
    public static void notifyAlarm(Alarm alarm, Integer priorityLevel) {
        //todo 预警
    }

    public static void notifyReport(Report reportEntity) {
        if (reportEntity == null) {
            SuperLogger.warn("Report is null");
            return;
        }
        if (StringUtils.isBlank(reportEntity.getEnterprise_No())) {
            SuperLogger.warn("report.getEnterprise_No() is null");
            return;
        }
        EnterpriseUser user = DatabaseCache.getEnterpriseUserCachedById(reportEntity.getEnterprise_User_Id());
        if (user == null) {
            return;
        }
        if (!BooleanUtils.toBooleanDefaultIfNull(user.getIs_Notify_Report(), false)) {
            return;
        }

        ReportNotifyAwait reportNotifyAwait = new ReportNotifyAwait();
        CopyUtil.REPORT_REPORTNOTIFYAWAIT_COPIER.copy(reportEntity, reportNotifyAwait, null);
        queueUtil.senderSmsService.saveReportAwait(Arrays.asList(reportNotifyAwait));
    }


    /**
     * @param protocol
     * @return
     * @author volcano
     * @date 2019年9月21日上午5:23:30
     * @version V1.0
     */
    private static ProtocolType getProtocol(String protocol) {
        if (StringUtils.isEmpty(protocol)) {
            return ProtocolType.WEB;
        }
        return ProtocolType.valueOf(protocol.toUpperCase());
    }
}
