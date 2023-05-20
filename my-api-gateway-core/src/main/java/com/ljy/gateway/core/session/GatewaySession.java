package com.ljy.gateway.core.session;

import com.ljy.gateway.core.bind.IGenericReference;

import java.util.Map;

/**
 * @description:用户处理网关 HTTP 请求
 * @author: 龙嘉翼
 * @Date: 2023/5/5
 */
public interface GatewaySession {
    Object get(String uri, Map<String, Object> params);

    Object post(String methodName, Map<String, Object> params);

    IGenericReference getMapper();

    Configuration getConfiguration();
}
