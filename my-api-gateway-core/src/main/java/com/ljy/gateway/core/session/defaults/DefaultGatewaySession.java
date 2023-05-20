package com.ljy.gateway.core.session.defaults;

import com.ljy.gateway.core.bind.IGenericReference;
import com.ljy.gateway.core.mapping.HttpStatement;
import com.ljy.gateway.core.executor.Executor;
import com.ljy.gateway.core.session.Configuration;
import com.ljy.gateway.core.session.GatewaySession;

import java.util.Map;

/**
 * @description:默认网关会话实现类
 * @author: 龙嘉翼
 * @Date: 2023/5/6
 */
public class DefaultGatewaySession implements GatewaySession {
    private Configuration configuration;
    private String uri;
    private Executor executor;

    public DefaultGatewaySession(Configuration configuration, String uri,Executor executor) {
        this.configuration = configuration;
        this.uri = uri;
        this.executor = executor;
    }

    @Override
    public Object get(String methodName, Map<String, Object> params) {
        HttpStatement httpStatement = configuration.getHttpStatement(uri);
        try{
            return executor.exec(httpStatement,params);
        }catch (Exception e){
            throw new RuntimeException("Error exec get. Cause: " + e);
        }
    }

    @Override
    public Object post(String methodName, Map<String, Object> params) {
        return get(methodName, params);
    }

    @Override
    public IGenericReference getMapper() {
        return configuration.getMapper(uri,this);
    }

    @Override
    public Configuration getConfiguration() {
        return configuration;
    }
}
