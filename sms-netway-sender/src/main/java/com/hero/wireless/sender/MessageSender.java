package com.hero.wireless.sender;

import com.drondea.sms.type.DefaultEventGroupFactory;
import com.hero.wireless.sender.filter.DeductFilterHandler;
import com.hero.wireless.sender.filter.Handler;
import com.hero.wireless.sender.filter.PreventShieldFilterHandler;
import com.hero.wireless.sender.handler.HttpClentSessionManager;
import com.hero.wireless.sms.sender.service.HttpServiceImpl;
import com.hero.wireless.web.entity.business.Channel;
import com.hero.wireless.web.entity.send.Submit;
import com.hero.wireless.web.util.ApplicationContextUtil;
import com.hero.wireless.web.util.QueueUtil;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @version V3.0.0
 * @description: 拉取通道的消息
 * @author: 刘彦宁
 * @date: 2020年07月23日15:36
 **/
public class MessageSender {

    /**
     * 返回是否存在消息,此处是立即返回，回调是异步执行
     *
     * @param channel           通道
     * @return
     */
    public static boolean pullMessage(Channel channel) {

        //根据优先级获取
        Submit submit = QueueUtil.getSubmit(channel.getNo());
        if (submit == null) {
            return false;
        }
        try {
            submitHttp(channel, submit);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * http提交短信
     * @param channel
     * @param submitted
     * @throws Exception
     */
    private static void submitHttp(Channel channel, Submit submitted) throws Exception {

        /**这里影响性能，优化成平台级开关，默认关闭。后面优化整个链条*/
        Handler deduct = new DeductFilterHandler();
        Handler preventShield = new PreventShieldFilterHandler();
        deduct.setNextHandler(preventShield);
        submitted = deduct.doFilter(channel, submitted);
        if (submitted == null) {
            return;
        }
        HttpServiceImpl httpServcie = ApplicationContextUtil.getBean(HttpServiceImpl.class);
        httpServcie.submit(submitted);
    }

    public static void pullHttpMessage(Channel channel, int delay, HttpClentSessionManager sessionManager) {
        ScheduledExecutorService executor = DefaultEventGroupFactory.getInstance().getScheduleExecutor();

        //已经关闭，不需要再拉取了
        if (sessionManager.status == false) {
            return;
        }

        executor.schedule(() -> {
            //有消息,不延时
            int nextDelay = 5;
            boolean haveMsg = pullMessage(channel);
            //没有消息要延时1秒再发
            if (!haveMsg) {
                nextDelay = 1000;
            }

            pullHttpMessage(channel, nextDelay, sessionManager);
        }, delay, TimeUnit.MILLISECONDS);
    }

}
