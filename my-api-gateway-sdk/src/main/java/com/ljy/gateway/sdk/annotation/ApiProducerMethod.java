package com.ljy.gateway.sdk.annotation;

import java.lang.annotation.*;

/**
 * @description:网关API生产者自定义注解
 * @author: 龙嘉翼
 * @Date: 2023/5/17
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface ApiProducerMethod {

    /** 方法名称 */
    String methodName() default "";

    /** 访问路径；/wg/activity/sayHi */
    String uri() default "";

    /** 接口类型；GET、POST、PUT、DELETE */
    String httpCommandType() default "GET";

    /** 是否认证；true = 1是、false = 0否 */
    int auth() default 0;

}