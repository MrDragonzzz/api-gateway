package com.ljy.gateway.assist;

/**
 * @description:网关异常
 * @author: 龙嘉翼
 * @Date: 2023/5/14
 */
public class GatewayException extends RuntimeException {

    public GatewayException(String msg) {
        super(msg);
    }

    public GatewayException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
