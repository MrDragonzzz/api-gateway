package com.ljy.gateway.core.datasource;

/**
 * @description:连接接口
 * @author: 龙嘉翼
 * @Date: 2023/5/7
 */
public interface Connection {

    Object execute(String method, String[] parameterTypes, String[] parameterNames, Object[] args);

}
