package com.ljy.gateway.core.socket;

import com.ljy.gateway.core.session.Configuration;
import com.ljy.gateway.core.socket.handlers.GatewayServerHandler;
import com.ljy.gateway.core.session.defaults.DefaultGatewaySessionFactory;
import com.ljy.gateway.core.socket.handlers.AuthorizationHandler;
import com.ljy.gateway.core.socket.handlers.ProtocolDataHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

/**
 * @description:会话管道初始化类
 * @author: 龙嘉翼
 * @Date: 2023/5/6
 */
public class GatewayChannelInitializer  extends ChannelInitializer<SocketChannel> {
    private final Configuration configuration;
    private final DefaultGatewaySessionFactory gatewaySessionFactory;

    public GatewayChannelInitializer(Configuration configuration, DefaultGatewaySessionFactory gatewaySessionFactory) {
        this.configuration = configuration;
        this.gatewaySessionFactory = gatewaySessionFactory;
    }
    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        ChannelPipeline line = channel.pipeline();
        line.addLast(new HttpRequestDecoder());
        line.addLast(new HttpResponseEncoder());
        line.addLast(new HttpObjectAggregator(1024 * 1024));
        line.addLast(new GatewayServerHandler(configuration));
        line.addLast(new AuthorizationHandler(configuration));
        line.addLast(new ProtocolDataHandler(gatewaySessionFactory));
    }
}
