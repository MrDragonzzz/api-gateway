package com.ljy.gateway.core.session;

/**
 * @description:网关会话工厂接口
 * @author: 龙嘉翼
 * @Date: 2023/5/5
 */
public interface GatewaySessionFactory {
    GatewaySession openSession(String uri);
}
