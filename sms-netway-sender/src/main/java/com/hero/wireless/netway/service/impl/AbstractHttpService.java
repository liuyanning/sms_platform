package com.hero.wireless.netway.service.impl;

import com.drondea.wireless.config.Constant;
import com.drondea.wireless.util.SuperLogger;
import com.hero.wireless.netway.service.IHttpService;
import com.hero.wireless.sms.sender.service.ISenderSmsService;
import com.hero.wireless.web.dao.send.ext.IInboxExtDAO;
import com.hero.wireless.web.entity.business.EnterpriseUser;
import com.hero.wireless.web.entity.send.Inbox;
import com.hero.wireless.web.entity.send.Report;
import com.hero.wireless.web.service.ISendManage;
import com.hero.wireless.web.util.GlobalRepeat;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author volcano
 * @version V1.0
 * @date 2019年9月21日上午3:56:34
 */
public abstract class AbstractHttpService extends BaseService implements IHttpService {
    @Autowired
    protected IInboxExtDAO inboxExtDAO;
    @Resource(name = "sendManage")
    protected ISendManage sendManage;

    @Resource(name = "defaultSenderServiceImpl")
    private ISenderSmsService senderSmsService;

    /**
     * @param user
     * @return
     * @author volcano
     * @date 2019年9月21日上午4:50:50
     * @version V1.0
     */
    protected List<Inbox> queryInboxList(EnterpriseUser user, Integer pageSize) {
        if (!BooleanUtils.toBooleanDefaultIfNull(user.getIs_Query_Report(), false)) {
            return new ArrayList<>();
        }
        String key = Constant.SOURCE_TYPE_CODE_QUERY_REPORT + ":inbox_" + user.getEnterprise_No() + "_"
                + user.getId();
        if (!GlobalRepeat.getAndSet(key)) {
            return new ArrayList<>();
        }
        List<Inbox> inboxList = this.sendManage.queryNotifyInboxList(user.getId(), pageSize);

        // 当拉去的集合为空时 限制拉取频次
        if (ObjectUtils.isEmpty(inboxList)) {
            GlobalRepeat.expire(key,30, TimeUnit.SECONDS);// 超时时间30秒
        } else {
            GlobalRepeat.remove(key);
        }
        return inboxList;
    }

    /**
     * @param user
     * @return
     * @author volcano
     * @date 2019年9月21日上午4:50:44
     * @version V1.0
     */
    protected List<Report> queryReportList(EnterpriseUser user, Integer maxCount) {
        if (!BooleanUtils.toBooleanDefaultIfNull(user.getIs_Query_Report(), false)) {
            return new ArrayList<>();
        }
        StringBuffer noRepeatKey = new StringBuffer();
        noRepeatKey.append("redis_report_query_key").append("_").append(user.getId());
        if (checkRepeatKey(noRepeatKey.toString())) {
            return new ArrayList<>();
        }
        List<Report> reportAwaitAndDel = senderSmsService.getReportAwaitAndDel(user.getId(), 0L, true);

        // 删除防并发重复拉取的Key
        GlobalRepeat.remove(noRepeatKey.toString());
        return reportAwaitAndDel;
    }

    private boolean checkRepeatKey(String noRepeatKey) {
        try {
            Boolean b = GlobalRepeat.putIfAbsent(noRepeatKey);
            if (b != null) {
                return true;
            }
            GlobalRepeat.expire(noRepeatKey, 10, TimeUnit.MINUTES);
        } catch (Exception e) {
            SuperLogger.error("请注意：获取防重复key时reids连接失败或者redis值处理异常");
        }
        return false;
    }
}
