package com.ljy.gateway.core.authorization;

/**
 * @description:认证服务接口
 * @author: 龙嘉翼
 * @Date: 2023/5/10
 */
public interface IAuth {
    boolean validate(String id,String token);
}
