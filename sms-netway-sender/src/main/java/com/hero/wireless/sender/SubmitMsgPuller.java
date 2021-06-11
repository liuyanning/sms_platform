package com.hero.wireless.sender;

import com.drondea.sms.type.DefaultEventGroupFactory;
import com.drondea.wireless.util.SuperLogger;
import com.hero.wireless.sms.sender.service.ISenderSmsService;
import com.hero.wireless.web.entity.send.Submit;
import com.hero.wireless.web.util.ApplicationContextUtil;
import com.hero.wireless.web.util.QueueUtil;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @version V3.0.0
 * @description: 拉取通道的消息
 * @author: 刘彦宁
 * @date: 2020年07月23日15:36
 **/
public class SubmitMsgPuller {

    private String channelNo;
    private volatile boolean start;
    private Long beginId = 0L;
    private ISenderSmsService senderSmsService;

    public SubmitMsgPuller(String channelNo) {
        this.channelNo = channelNo;
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
                SuperLogger.debug("通道关闭，停止拉取消息========>" + channelNo);
                return;
            }

            //有消息,不延时
            int nextDelay = 0;

            //从数据库获取消息
            boolean result = getSubmitList();
            //没有消息要延时1秒再获取
            if (!result) {
                nextDelay = 1000;
            }
            pullMessage(nextDelay);
        }, delay, TimeUnit.MILLISECONDS);
    }

    private boolean getSubmitList() {
        int submitCount = QueueUtil.getSubmitCount(channelNo);
        //缓存太多先不拉取
        if (submitCount > 500) {
            return false;
        }
        List<Submit> submitAwait = senderSmsService.getSubmitAwaitAndDel(channelNo, beginId);
        if (ObjectUtils.isEmpty(submitAwait)) {
            return false;
        }
        QueueUtil.notifySubmitAwait(channelNo, submitAwait);
        //记录最后一个id
        beginId = submitAwait.get(submitAwait.size() - 1).getId();
        return true;
    }

    public void stop() {
        this.start = false;
    }

}
