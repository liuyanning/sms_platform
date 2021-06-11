package com.hero.wireless.web.action.interceptor;

import com.drondea.wireless.util.SuperLogger;
import com.hero.wireless.json.JsonUtil;
import com.hero.wireless.web.action.AdminControllerBase;
import com.hero.wireless.web.dao.business.ISystemLogDAO;
import com.hero.wireless.web.entity.business.SystemLog;
import com.hero.wireless.web.entity.business.ext.AdminUserExt;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Optional;

@Aspect
@Component
public class OperateLogAspect extends AdminControllerBase {

    @Resource(name = "ISystemLogDAO")
    private ISystemLogDAO<SystemLog> operateLogDAO;

    @Pointcut("@annotation(com.hero.wireless.web.action.interceptor.OperateAnnotation)")
    public void actionAspecta() {
    }

    @AfterReturning("actionAspecta()")
    public void doAfter(JoinPoint joinPoint) {
        try {
            MethodSignature ms = (MethodSignature) joinPoint.getSignature();
            Method method = ms.getMethod();
            OperateAnnotation annotation = method.getAnnotation(OperateAnnotation.class);
            SystemLog log = new SystemLog();
            Optional<AdminUserExt> adminUser = Optional.ofNullable(currentAdmin());
            log.setUser_Id(adminUser.map(user -> user.getId()).orElse(000));
            log.setReal_Name(adminUser.map(user -> user.getReal_Name()).orElse("Unknown"));
            log.setModule_Name(annotation.moduleName());
            log.setOperate_Desc(annotation.option());
            String specific_Desc = "参数：";
            Object[] args = joinPoint.getArgs();
            if("用户登录系统".equals(annotation.option())){
                args = null;
            }
            if("用户登录系统".equals(annotation.option()) && "Unknown".equals(log.getReal_Name())){
                return;
            }
            if (args != null) {
                for (int i = 0; i < args.length; i++) {
                    if (args[i] != null) {
                        specific_Desc += JsonUtil.writeJsonOrStringValueNoNull(args[i]) + ",";
                    }
                }
            }
            log.setSpecific_Desc(specific_Desc != null && specific_Desc.length() > 5000 ?
                    specific_Desc.substring(0, 5000) : specific_Desc);
            log.setCreate_Date(new Date());
            log.setUser_Name(adminUser.map(user -> user.getUser_Name()).orElse("Unknown"));
            log.setIp_Address(adminUser.map(user -> user.getCurrent_Login_IP()).orElse("Unknown"));
            operateLogDAO.insert(log);
        } catch (Exception e) {
            SuperLogger.error("保存操作日志异常", e);
        }
    }

}
