package com.hero.wireless.netway.service;

import com.drondea.sms.type.DefaultEventGroupFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public enum ScheduleServiceFactory {

    /**
     * 单例
     */
    INS;

    private static final Logger logger = LoggerFactory.getLogger(ScheduleServiceFactory.class);

    /**
     * 执行任务的线程池
     */
    private final static ScheduledExecutorService busiWork = DefaultEventGroupFactory.getInstance().getScheduleExecutor();


    /**
     * 实现一个无限循环任务，
     *
     * @param task          需要执行的任务
     * @param delay         任务的执行间隔
     */
    public <T> void submitUnlimitCircleTask(Callable<Boolean> task, long delay) {
        addtask(busiWork, task, delay);
    }

    private <T> void addtask(final ScheduledExecutorService executor, final Callable<Boolean> task, final long delay) {

        if (executor.isShutdown()) {
            return;
        }

        executor.schedule(() -> {
            try {
                boolean call = task.call();
                //返回false暂停处理
                if (!call) {
                    return;
                }
            } catch (Throwable e) {
                logger.error("business schedule error:", e);
            }
            addtask(executor, task, delay);
        }, delay, TimeUnit.MILLISECONDS);
    }

}
