package com.ljy.gateway.center.domain.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.redis.core.RedisTemplate;
/**
 * @description:消息推送
 * @author: 龙嘉翼
 * @Date: 2023/5/19
 */

@Service
public class Publisher {
    private final RedisTemplate<String, Object> redisMessageTemplate;


    @Autowired
    public Publisher(RedisTemplate<String,Object> redisMessageTemplate){
        this.redisMessageTemplate=redisMessageTemplate;
    }

    public void pushMessage(String topic, Object message) {
        redisMessageTemplate.convertAndSend(topic, message);
    }

}
