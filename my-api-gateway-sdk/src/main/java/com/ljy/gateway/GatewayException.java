package com.ljy.gateway;

/**
 * @description:网关异常
 * @author: 龙嘉翼
 * @Date: 2023/5/18
 */
public class GatewayException extends RuntimeException {

    public GatewayException(String msg) {
        super(msg);
    }

    public GatewayException(String msg, Throwable cause) {
        super(msg, cause);
    }

}