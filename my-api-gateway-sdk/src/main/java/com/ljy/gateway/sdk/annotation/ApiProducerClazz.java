package com.ljy.gateway.sdk.annotation;

import java.lang.annotation.*;

/**
 * @description:网关API生产者自定义注解
 * @author: 龙嘉翼
 * @Date: 2023/5/17
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface ApiProducerClazz {

    /** 接口名称 */
    String interfaceName() default "";

    /** 接口版本 */
    String interfaceVersion() default "";
}
