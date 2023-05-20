package com.ljy.gateway.center.application;

import java.util.Map;

/**
 * @description:消息服务
 * @author: 龙嘉翼
 * @Date: 2023/5/19
 */
public interface IMessageService {
    Map<String, String> queryRedisConfig();

    void pushMessage(String gatewayId, Object message);
}
