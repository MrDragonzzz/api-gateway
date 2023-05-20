package com.ljy.gateway.core.bind;

import com.ljy.gateway.core.mapping.HttpStatement;
import com.ljy.gateway.core.session.Configuration;
import com.ljy.gateway.core.session.GatewaySession;

import java.util.HashMap;
import java.util.Map;

/**
 * @description:泛化调用注册器
 * @author: 龙嘉翼
 * @Date: 2023/5/6
 */
public class MapperRegistry {
    private final Configuration configuration;
    public MapperRegistry(Configuration configuration){
        this.configuration=configuration;
    }

    private final Map<String, MapperProxyFactory > knownMappers=new HashMap<>();

    public IGenericReference getMapper(String uri,GatewaySession gatewaySession){
        MapperProxyFactory mapperProxyFactory = knownMappers.get(uri);
        if(mapperProxyFactory==null){
            throw new RuntimeException("uri"+uri+"is not know to MapperRegistry.");
        }
        try{
            return mapperProxyFactory.newInstance(gatewaySession);
        }catch(Exception e){
            throw new RuntimeException("Error getting mapper instance. Cause: " + e, e);

        }
    }

    public void addMapper(HttpStatement httpStatement){
        String uri = httpStatement.getUri();

        //如果重复注册则报错
        if(hasMapper(uri)){
            throw new RuntimeException("Uri " + uri + " is already known to the MapperRegistry.");
        }
        knownMappers.put(uri,new MapperProxyFactory(uri));
        //保存接口映射信息
        configuration.addHttpStatement(httpStatement);
    }

    public  boolean hasMapper(String uri) {
        return knownMappers.containsKey(uri);
    }

}
