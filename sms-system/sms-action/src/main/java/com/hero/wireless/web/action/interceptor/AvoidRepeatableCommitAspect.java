package com.hero.wireless.web.action.interceptor;

import com.drondea.wireless.util.DateTime;
import com.drondea.wireless.util.SuperLogger;
import com.hero.wireless.json.LayuiResultUtil;
import com.hero.wireless.web.util.GlobalRepeat;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * 
* Title: AvoidRepeatableCommitAspect  
* Description:
* @author yjb
* @date 2019-12-09
 */
@Aspect
@Component
public class AvoidRepeatableCommitAspect {
	
	private String cacheKey = "avoidRepeatableCommit:";
 
    /**
     * @param point
     */
    @Around("@annotation(com.hero.wireless.web.action.interceptor.AvoidRepeatableCommitAnnotation)")
    public Object around(ProceedingJoinPoint point) throws Throwable {
 
        HttpServletRequest request  = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        //获取注解
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        AvoidRepeatableCommitAnnotation avoidRepeatableCommit =  method.getAnnotation(AvoidRepeatableCommitAnnotation.class);
        //目标类、方法
        String systemString = avoidRepeatableCommit.systemModuleName();
        String className = method.getDeclaringClass().getName();
        String name = method.getName();
        String ipKey = String.format("%s#%s#%s",systemString, className, name);
        int hashCode = Math.abs(ipKey.hashCode());
        String sessionId = request.getSession().getId();
        String key = cacheKey + String.format("%s_%d",sessionId,hashCode);;
        if(StringUtils.isNotEmpty(avoidRepeatableCommit.key())){
        	key = cacheKey + avoidRepeatableCommit.key();
        }
        long timeout = avoidRepeatableCommit.timeout();
        if (timeout < 0){
            timeout = 10 * DateTime.SECOND;
        }
        try {
            Boolean result = GlobalRepeat.putIfAbsent(key);
			if (result != null){
				SuperLogger.debug( "请勿重复提交");
			    return LayuiResultUtil.fail("请勿重复提交");
			}
            GlobalRepeat.expire(key, timeout, TimeUnit.MILLISECONDS);
		} catch (Exception e) {
			SuperLogger.error( "请注意：reids连接失败或者redis值处理异常");
		}
        //执行方法
        Object object = point.proceed();
        //方法执行完  删除key
        GlobalRepeat.remove(key);
        return object;
    }
 
}