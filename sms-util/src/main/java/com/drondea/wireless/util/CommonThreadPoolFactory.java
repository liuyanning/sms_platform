package com.drondea.wireless.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

/**
 * @version V3.0.0
 * @description: 公用线程池
 * @author: 刘彦宁
 * @date: 2020年08月19日18:12
 **/
public class CommonThreadPoolFactory {

    private static final Logger logger = LoggerFactory.getLogger(CommonThreadPoolFactory.class);

    /**
     * cpu核心数
     */
    private static int processorNum = Runtime.getRuntime().availableProcessors();

    private static final CustomRejectedExecutionHandler REJECTED_EXECUTION_HANDLER = new CustomRejectedExecutionHandler();

    private static class CommonThreadPoolFactoryHolder {
        private final static CommonThreadPoolFactory INSTANCE = new CommonThreadPoolFactory();
    }

    private CommonThreadPoolFactory() {
    }

    public static CommonThreadPoolFactory getInstance() {
        return CommonThreadPoolFactoryHolder.INSTANCE;
    }

    private final ThreadPoolExecutor BIZ_EXECUTOR = new ThreadPoolExecutor(
            16 + processorNum * 2,
            16 + processorNum * 3,
            1000 * 60,
            TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>(),
            new ThreadFactoryImpl("BizCommonThread_"),
            REJECTED_EXECUTION_HANDLER);

    private final ThreadPoolExecutor RESPONSE_EXECUTOR = new ThreadPoolExecutor(
            processorNum * 2,
            processorNum * 2,
            1000 * 60,
            TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>(),
            new ThreadFactoryImpl("ResponseThread_"),
            REJECTED_EXECUTION_HANDLER);

    private final ThreadPoolExecutor SORT_EXECUTOR = new ThreadPoolExecutor(
            16 + processorNum * 2,
            16 + processorNum * 3,
            1000 * 60,
            TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>(),
            new ThreadFactoryImpl("SortCommonThread_"),
            REJECTED_EXECUTION_HANDLER);

    private final ThreadPoolExecutor SORT_FAIL_EXECUTOR = new ThreadPoolExecutor(
            8 + processorNum * 2,
            8 + processorNum * 3,
            1000 * 60,
            TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>(),
            new ThreadFactoryImpl("SortFailCommonThread_"),
            REJECTED_EXECUTION_HANDLER);

    private final ThreadPoolExecutor INPUTLOG_EXECUTOR = new ThreadPoolExecutor(
            8 + processorNum * 2,
            8 + processorNum * 3,
            1000 * 60,
            TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>(),
            new ThreadFactoryImpl("InputLogCommonThread_"),
            REJECTED_EXECUTION_HANDLER);


    private final ScheduledExecutorService SCHEDULE_EXECUTOR = new ScheduledThreadPoolExecutor(2,
            new ThreadFactoryImpl("BizCommonScheduledThread_"), REJECTED_EXECUTION_HANDLER);


    public ThreadPoolExecutor getBizPoolExecutor() {
        return BIZ_EXECUTOR;
    }

    public ThreadPoolExecutor getSortPoolExecutor(){
        return SORT_EXECUTOR;
    }

    public ThreadPoolExecutor getInputLogPoolExecutor(){
        return INPUTLOG_EXECUTOR;
    }

    public ThreadPoolExecutor getSortFailPoolExecutor(){
        return SORT_FAIL_EXECUTOR;
    }

    public ThreadPoolExecutor getResponseExecutor() {
        return RESPONSE_EXECUTOR;
    }

    public ScheduledExecutorService getScheduleExecutor() {
        return SCHEDULE_EXECUTOR;
    }


    class ThreadUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {

        @Override
        public void uncaughtException(Thread thread, Throwable throwable) {
            logger.error("ThreadPool {} got exception {}", thread, throwable);
        }
    }

    public static class CustomRejectedExecutionHandler implements RejectedExecutionHandler {

        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {

            String msg = String.format("Thread pool is EXHAUSTED!" +
                            " Pool Size: %d (active: %d, core: %d, max: %d, largest: %d), Task: %d (completed: %d)," +
                            " Executor status:(isShutdown:%s, isTerminated:%s, isTerminating:%s)",
                    executor.getPoolSize(), executor.getActiveCount(), executor.getCorePoolSize(), executor.getMaximumPoolSize(), executor.getLargestPoolSize(),
                    executor.getTaskCount(), executor.getCompletedTaskCount(), executor.isShutdown(), executor.isTerminated(), executor.isTerminating());
            // 线程池拒绝，异常处理
            logger.error(msg + ":" + executor.getQueue().size());
        }
    }

    public void shutdown() {
        if (BIZ_EXECUTOR != null) {
            BIZ_EXECUTOR.shutdown();
        }
        if (SCHEDULE_EXECUTOR != null) {
            SCHEDULE_EXECUTOR.shutdown();
        }
    }
}
