package com.ljy.gateway.core.executor;

import com.ljy.gateway.core.executor.result.GatewayResult;
import com.ljy.gateway.core.mapping.HttpStatement;

import java.util.Map;

/**
 * @description:
 * @author: 龙嘉翼
 * @Date: 2023/5/9
 */
public interface Executor {
    GatewayResult exec(HttpStatement httpStatement, Map<String, Object> params) throws Exception;

}
