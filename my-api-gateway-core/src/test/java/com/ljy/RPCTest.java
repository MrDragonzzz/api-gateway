package com.ljy;

import com.ljy.gateway.core.mapping.HttpCommandType;
import com.ljy.gateway.core.mapping.HttpStatement;
import com.ljy.gateway.core.session.Configuration;
import com.ljy.gateway.core.session.defaults.DefaultGatewaySessionFactory;
import com.ljy.gateway.core.socket.GatewaySocketServer;
import io.netty.channel.Channel;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;
import org.apache.dubbo.config.utils.ReferenceConfigCache;
import org.apache.dubbo.rpc.service.GenericService;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 泛化调用测试
 * 官网案例：https://dubbo.apache.org/zh/docs/advanced/generic-reference/
 */
public class RPCTest {

    @Test
    public void test_rpc() {

        ApplicationConfig application = new ApplicationConfig();
        application.setName("api-gateway-test");
        application.setQosEnable(false);

        RegistryConfig registry = new RegistryConfig();
        registry.setAddress("zookeeper://127.0.0.1:2181");
        registry.setRegister(false);

        ReferenceConfig<GenericService> reference = new ReferenceConfig<>();
        reference.setInterface("cn.bugstack.gateway.rpc.IActivityBooth");
        reference.setVersion("1.0.0");
        reference.setGeneric("true");

        DubboBootstrap bootstrap = DubboBootstrap.getInstance();
        bootstrap.application(application)
                .registry(registry)
                .reference(reference)
                .start();

        ReferenceConfigCache cache = ReferenceConfigCache.getCache();
        GenericService genericService = cache.get(reference);

//        Object result = genericService.$invoke("sayHi", new String[]{"java.lang.String"}, new Object[]{"world"});
//        Map<String, Object> allRequestParams = new HashMap();
//        allRequestParams.put("name", "小傅哥");
//        allRequestParams.put("uid", "10001");
//        Object result = genericService.$invoke("insert", new String[]{"java.lang.Object"}, new Object[]{allRequestParams});

        String[] parameterTypes = new String[]{"java.lang.String", "cn.bugstack.gateway.rpc.dto.XReq"};

        Map<String, Object> params01 = new HashMap<>();
//        params.put("class", "cn.bugstack.gateway.rpc.dto.XReq");
        params01.put("str", "10001");

        Map<String, Object> params02 = new HashMap<>();
//        params.put("str", "10001");
        params02.put("uid", "10001");
        params02.put("name", "小傅哥");

//        Object user = genericService.$invoke("sayHi", new String[]{"java.lang.String"}, params.values().toArray());
//        Object user = genericService.$invoke("insert", new String[]{"cn.bugstack.gateway.rpc.dto.XReq"}, new Object[]{params});
        Object user = genericService.$invoke("test", new String[]{"java.lang.String", "cn.bugstack.gateway.rpc.dto.XReq"}, new Object[]{params01.values().toArray()[0], params02});


        System.out.println(user);
    }

    public static class ApiTest {

        private final Logger logger = LoggerFactory.getLogger(ApiTest.class);

        /**
         * 测试：
         * http://localhost:7397/wg/activity/sayHi
         * 参数：
         * {
         * "str": "10001"
         * }
         * <p>
         * http://localhost:7397/wg/activity/index
         * 参数：
         * {
         * "name":"小傅哥",
         * "uid":"10001"
         * }
         */
        @Test
        public void test_gateway() throws InterruptedException, ExecutionException {
            // 1. 创建配置信息加载注册
            Configuration configuration = new Configuration();
            configuration.registryConfig("api-gateway-test", "zookeeper://127.0.0.1:2181", "cn.bugstack.gateway.rpc.IActivityBooth", "1.0.0");

            HttpStatement httpStatement01 = new HttpStatement(
                    "api-gateway-test",
                    "cn.bugstack.gateway.rpc.IActivityBooth",
                    "sayHi",
                    "java.lang.String",
                    "/wg/activity/sayHi",
                    HttpCommandType.GET,
                    false);

            HttpStatement httpStatement02 = new HttpStatement(
                    "api-gateway-test",
                    "cn.bugstack.gateway.rpc.IActivityBooth",
                    "insert",
                    "cn.bugstack.gateway.rpc.dto.XReq",
                    "/wg/activity/insert",
                    HttpCommandType.POST,
                    true);

            configuration.addMapper(httpStatement01);
            configuration.addMapper(httpStatement02);

            // 2. 基于配置构建会话工厂
            DefaultGatewaySessionFactory gatewaySessionFactory = new DefaultGatewaySessionFactory(configuration);

            // 3. 创建启动网关网络服务
            GatewaySocketServer server = new GatewaySocketServer(configuration, gatewaySessionFactory);

            Future<Channel> future = Executors.newFixedThreadPool(2).submit(server);
            Channel channel = future.get();

            if (null == channel) throw new RuntimeException("netty server start error channel is null");

            while (!channel.isActive()) {
                logger.info("netty server gateway start Ing ...");
                Thread.sleep(500);
            }
            logger.info("netty server gateway start Done! {}", channel.localAddress());

            Thread.sleep(Long.MAX_VALUE);
        }

    }
}
