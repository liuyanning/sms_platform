package com.hero.wireless.netway.config;

import com.drondea.sms.session.AbstractServerSessionManager;
import com.drondea.sms.session.cmpp.CmppServerSessionManager;
import com.drondea.sms.session.sgip.SgipServerSessionManager;
import com.drondea.sms.session.smgp.SmgpServerSessionManager;
import com.drondea.sms.session.smpp.SmppServerSessionManager;
import com.drondea.sms.type.DefaultEventGroupFactory;
import com.drondea.wireless.config.Constant;
import com.drondea.wireless.util.CommonThreadPoolFactory;
import com.drondea.wireless.util.IpUtil;
import com.drondea.wireless.util.SuperLogger;
import com.hero.wireless.enums.ProtocolType;
import com.hero.wireless.netway.service.ReportMsgPuller;
import com.hero.wireless.web.config.DatabaseCache;
import com.hero.wireless.web.entity.business.Enterprise;
import com.hero.wireless.web.entity.business.EnterpriseUser;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * 初始化系统环境
 *
 * @author Administrator
 */
@Component("initNetwayEnv")
public class InitSystemEnv implements ApplicationListener<ContextRefreshedEvent> {

    /**
     * 用于保存用户相关资源和数据
     */
    public final static Map<Integer, EnterpriseUser> USER_INFOS = new ConcurrentHashMap<>();

    public final static Map<Integer, Enterprise> ENTERPRISE_INFOS = new ConcurrentHashMap<>();

    public final static Map<Integer, ReportMsgPuller> USER_REPORT_PULLER = new ConcurrentHashMap<>();

    public final static List<AbstractServerSessionManager> SERVER_SESSION_MANAGER_LIST = new ArrayList<>();



    @Resource
    private CmppServerEnv cmppServerEnv;
    @Resource
    private SmppServerEnv smppServerEnv;
    @Resource
    private SmgpServerEnv smgpServerEnv;
    @Resource
    private NetwayEnv netwayEnv;
    @Resource(name = "databaseCache")
    private DatabaseCache databaseCache;

    /**
     * 本地ip
     */
    public static String LOCAL_IP;

    private CmppServerSessionManager cmppServerSessionManager;
    private SmppServerSessionManager smppServerSessionManager;
    private SmgpServerSessionManager smgpServerSessionManager;
    private SgipServerSessionManager sgipServerSessionManager;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        // 解决执行两次的问题
        if (event.getApplicationContext().getParent() != null) {
            return;
        }
        SuperLogger.debug("初始化.....");

        CommonThreadPoolFactory.getInstance().getBizPoolExecutor().submit(() -> {
            databaseCache.init();
            //加载敏感字
            DatabaseCache.refreshSensitiveWordLocalCache();
            //刷新导流策略
            DatabaseCache.refreshDiversionLocalCache();
            //加载号码路由
            DatabaseCache.refreshSmsRouteLocalCache();
            //加载白名单
            DatabaseCache.refreshWhiteListLocalCache();
            //加载黑名单
            DatabaseCache.refreshBlackListLocalCache();
        });

        LOCAL_IP = IpUtil.getLocalIp();

        cmppServerSessionManager = cmppServerEnv.start();
        smppServerSessionManager = smppServerEnv.start();
        smgpServerSessionManager = smgpServerEnv.start();

        SERVER_SESSION_MANAGER_LIST.add(cmppServerSessionManager);
        SERVER_SESSION_MANAGER_LIST.add(smppServerSessionManager);
        SERVER_SESSION_MANAGER_LIST.add(smgpServerSessionManager);
        SERVER_SESSION_MANAGER_LIST.add(sgipServerSessionManager);

        //连接通道
        netwayEnv.start();

    }

    /**
     * 用户没有连接后关闭相关的资源
     * 60秒钟检测一次，如果没有连接就关闭consumer
     */
    @Scheduled(fixedDelay = 60 * 1000)
    private void closeUserResource() {
        List<EnterpriseUser> allLoginUser = getAllLoginUser();
        allLoginUser.forEach(user -> {
            updateUserTcpInfo(cmppServerSessionManager, user, ProtocolType.CMPP);
            updateUserTcpInfo(smppServerSessionManager, user, ProtocolType.SMPP);
            updateUserTcpInfo(smgpServerSessionManager, user, ProtocolType.SMGP);
        });
    }



    public static void addReportPuller(int userId, ProtocolType protocolType) {
        String key = "user:" + userId + protocolType;
        // 必须要双重检查
        if (USER_REPORT_PULLER.get(userId) != null) {
            return;
        }

        synchronized (key.intern()) {
            if (USER_REPORT_PULLER.get(key) == null) {
                ReportMsgPuller puller = new ReportMsgPuller(userId, protocolType);
                puller.start(500);
                USER_REPORT_PULLER.put(userId, puller);
            }
        }
    }

    public static void removeReportPuller(int userId, ProtocolType protocolType) {
        String key = "user:" + userId + protocolType;
        ReportMsgPuller puller = USER_REPORT_PULLER.remove(key);
        if (puller != null) {
            puller.stop();
        }
    }

    private void updateUserTcpInfo(AbstractServerSessionManager abstractServerSessionManager,
                                   EnterpriseUser user, ProtocolType protocolType) {
        if (abstractServerSessionManager == null) {
            return;
        }
        String userName = user.getTcp_User_Name();
        Integer userId = user.getId();
        int userSessionSize = abstractServerSessionManager.getUserSessionSize(userName);
        if (userSessionSize == 0 || !user.getStatus().equals(Constant.ENTERPRISE_INFO_STATUS_NORMAL)) {
            removeReportPuller(userId, protocolType);
            abstractServerSessionManager.closeUserSession(userName);
        } else {
            addReportPuller(userId, protocolType);
            //修改用户的提交速度
            modifyUserSpeed(abstractServerSessionManager, userId);
        }

    }

    private void modifyUserSpeed(AbstractServerSessionManager abstractServerSessionManager, int userId) {
        EnterpriseUser enterpriseUser = DatabaseCache.getEnterpriseUserCachedById(userId);
        Integer submitSpeed = ObjectUtils.defaultIfNull(enterpriseUser.getTcp_Submit_Speed(), 200);
        //修改用户提交速度
        abstractServerSessionManager.resetUserSubmitSpeed(enterpriseUser.getTcp_User_Name(), submitSpeed);
    }


    private List<EnterpriseUser> getAllLoginUser() {
        return new ArrayList<>(USER_INFOS.values());
    }


    public static void addUserInfo(Integer id, EnterpriseUser user) {
        USER_INFOS.putIfAbsent(id, user);
    }

    public static void addEnterprise(Integer id, Enterprise enterprise) {
        ENTERPRISE_INFOS.put(id, enterprise);
    }


    @PreDestroy
    public void release() throws InterruptedException {
        SuperLogger.debug("关闭netway，销毁资源=====");
        SuperLogger.debug("关闭拉取程序。");
        USER_REPORT_PULLER.forEach((key, value) -> value.stop());
        Thread.sleep(3000);
        SuperLogger.debug("关闭tcp连接。");
        if (cmppServerSessionManager != null) {
            cmppServerSessionManager.doClose();
        }
        if (smppServerSessionManager != null) {
            smppServerSessionManager.doClose();
        }
        if (smgpServerSessionManager != null) {
            smgpServerSessionManager.doClose();
        }
        if (sgipServerSessionManager != null) {
            sgipServerSessionManager.doClose();
        }

        DefaultEventGroupFactory.getInstance().close();

        Runtime.getRuntime().exit(0);
    }

}
