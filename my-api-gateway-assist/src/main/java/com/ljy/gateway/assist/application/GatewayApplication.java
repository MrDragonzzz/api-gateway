package com.ljy.gateway.assist.application;

import com.ljy.gateway.assist.config.GatewayServiceProperties;
import com.ljy.gateway.assist.domain.model.aggregates.ApplicationSystemRichInfo;
import com.ljy.gateway.assist.domain.model.vo.ApplicationInterfaceMethodVO;
import com.ljy.gateway.assist.domain.model.vo.ApplicationInterfaceVO;
import com.ljy.gateway.assist.domain.model.vo.ApplicationSystemVO;
import com.ljy.gateway.assist.domain.service.GatewayCenterService;
import com.ljy.gateway.core.mapping.HttpCommandType;
import com.ljy.gateway.core.mapping.HttpStatement;
import com.ljy.gateway.core.session.Configuration;
import io.netty.channel.Channel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;

import java.util.List;

/**
 * @description:网关应用；与 Spring 链接，调用网关注册和接口拉取
 * @author: 龙嘉翼
 * @Date: 2023/5/14
 */
public class GatewayApplication implements ApplicationListener<ContextClosedEvent>, ApplicationContextAware {
    private Logger logger = LoggerFactory.getLogger(GatewayApplication.class);

    private GatewayServiceProperties properties;
    private GatewayCenterService gatewayCenterService;
    private Configuration configuration;
    private Channel gatewaySocketServerChannel;


    public GatewayApplication(GatewayServiceProperties properties, GatewayCenterService gatewayCenterService, Configuration configuration,Channel gatewaySocketServerChannel) {
        this.properties = properties;
        this.gatewayCenterService = gatewayCenterService;
        this.configuration = configuration;
        this.gatewaySocketServerChannel = gatewaySocketServerChannel;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        try {
            // 1. 注册网关服务；每一个用于转换 HTTP 协议泛化调用到 RPC 接口的网关都是一个算力，这些算力需要注册网关配置中心
            gatewayCenterService.doRegister(properties.getAddress(),
                    properties.getGroupId(),
                    properties.getGatewayId(),
                    properties.getGatewayName(),
                    properties.getGatewayAddress());
            addMappers("");
        } catch (Exception e) {
            logger.error("网关服务启动失败，停止服务。{}", e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public void onApplicationEvent(ContextClosedEvent contextClosedEvent) {
        try {
            if (gatewaySocketServerChannel.isActive()) {
                logger.info("应用容器关闭，Api网关服务关闭。localAddress：{}", gatewaySocketServerChannel.localAddress());
                gatewaySocketServerChannel.close();
            }
        } catch (Exception e) {
            logger.error("应用容器关闭，Api网关服务关闭失败", e);
        }
    }

    public void addMappers(String systemId){
        // 2. 拉取网关配置；每个网关算力都会在注册中心分配上需要映射的RPC服务信息，包括；系统、接口、方法
        ApplicationSystemRichInfo applicationSystemRichInfo = gatewayCenterService.pullApplicationSystemRichInfo(properties.getAddress(), properties.getGatewayId(), systemId);
        List<ApplicationSystemVO> applicationSystemVOList = applicationSystemRichInfo.getApplicationSystemVOList();
        if (applicationSystemVOList.isEmpty()) {
            logger.warn("网关{}服务注册映射为空，请排查 gatewayCenterService.pullApplicationSystemRichInfo 是否检索到此网关算力需要拉取的配置数据。", systemId);
            return;
        }
        for (ApplicationSystemVO system : applicationSystemVOList) {
            List<ApplicationInterfaceVO> interfaceList = system.getInterfaceList();
            for (ApplicationInterfaceVO itf : interfaceList) {
                // 2.1 创建配置信息加载注册
                configuration.registryConfig(system.getSystemId(), system.getSystemRegistry(), itf.getInterfaceId(), itf.getInterfaceVersion());
                List<ApplicationInterfaceMethodVO> methodList = itf.getMethodList();
                // 2.2 注册系统服务接口信息
                for (ApplicationInterfaceMethodVO method : methodList) {
                    HttpStatement httpStatement = new HttpStatement(
                            system.getSystemId(),
                            itf.getInterfaceId(),
                            method.getMethodId(),
                            method.getParameterType(),
                            method.getUri(),
                            HttpCommandType.valueOf(method.getHttpCommandType()),
                            method.isAuth());
                    configuration.addMapper(httpStatement);
                    logger.info("网关服务注册映射 系统：{} 接口：{} 方法：{}", system.getSystemId(), itf.getInterfaceId(), method.getMethodId());
                }
            }
        }
    }

    public void receiveMessage(Object message) {
        logger.info("【事件通知】接收注册中心推送消息 message：{}", message);
        addMappers(message.toString().substring(1, message.toString().length() - 1));
    }

}
