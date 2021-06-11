package com.hero.wireless.web.action.interceptor;


import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OperateAnnotation {

    // 模块名
    String moduleName() default "";

    // 操作内容
    String option() default "";

}
