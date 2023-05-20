package com.ljy.gateway.core.authorization;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @description:验证 Token
 * @author: 龙嘉翼
 * @Date: 2023/5/10
 */
public class GatewayAuthorizingToken implements AuthenticationToken {

    private static final long serialVersionUID = 1L;

    // 通信管道ID
    private String channelId;

    // JSON WEB TOKEN
    private String jwt;

    public GatewayAuthorizingToken() {
    }

    public GatewayAuthorizingToken(String channelId, String jwt) {
        this.channelId = channelId;
        this.jwt = jwt;
    }
    @Override
    public Object getPrincipal() {
        return channelId;
    }

    @Override
    public Object getCredentials() {
        return jwt;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
}
