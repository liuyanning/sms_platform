package com.hero.wireless.web.action.interceptor;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 防重复提交
* Title: AvoidRepeatableCommit  
* Description:
* @author yjb
* @date 2019-12-09
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AvoidRepeatableCommitAnnotation {
	
	 /**
	  *  系统模块名:用来区分平台  因为平台的包名路径名类型相同的很多
	  */
    String systemModuleName() default "";
 
    /**
     * 指定时间内不可重复提交,单位毫秒
     */
    long timeout()  default 10000 ;
    
    String key() default "";
 
}
