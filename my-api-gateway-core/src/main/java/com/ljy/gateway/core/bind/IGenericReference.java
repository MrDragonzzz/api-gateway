package com.ljy.gateway.core.bind;

import com.ljy.gateway.core.executor.result.GatewayResult;

import java.util.Map;

/**
 * @description:统一泛化调用接口
 * @author: 龙嘉翼
 * @Date: 2023/5/5
 */
public interface IGenericReference {
    GatewayResult $invoke(Map<String,Object> params);
}
