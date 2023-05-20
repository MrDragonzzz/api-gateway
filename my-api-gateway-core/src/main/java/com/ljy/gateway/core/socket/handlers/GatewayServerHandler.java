package com.ljy.gateway.core.socket.handlers;

import com.ljy.gateway.core.mapping.HttpStatement;
import com.ljy.gateway.core.session.Configuration;
import com.ljy.gateway.core.socket.agreement.AgreementConstants;
import com.ljy.gateway.core.socket.agreement.GatewayResultMessage;
import com.ljy.gateway.core.socket.agreement.RequestParser;
import com.ljy.gateway.core.socket.agreement.ResponseParser;
import com.ljy.gateway.core.socket.BaseHandler;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @description:
 * @author: 龙嘉翼
 * @Date: 2023/5/6
 */
public class GatewayServerHandler extends BaseHandler<FullHttpRequest> {

    private final Logger logger = LoggerFactory.getLogger(GatewayServerHandler.class);

    private final Configuration configuration;
    public GatewayServerHandler(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    protected void session(ChannelHandlerContext ctx, Channel channel, FullHttpRequest request) {
        logger.info("网关接收请求【全局】 uri：{} method：{}", request.uri(), request.method());

       try {
           // 1. 解析请求参数
           RequestParser requestParser = new RequestParser(request);
           String uri = requestParser.getUri();

           // 2. 保存信息；HttpStatement、Header=token
           HttpStatement httpStatement = configuration.getHttpStatement(uri);
           channel.attr(AgreementConstants.HTTP_STATEMENT).set(httpStatement);

           //放行服务
           request.retain();
           ctx.fireChannelRead(request);
       }catch (Exception e) {
           // 4. 封装返回结果
           DefaultFullHttpResponse response = new ResponseParser().parse(GatewayResultMessage.buildError(AgreementConstants.ResponseCode._500.getCode(), "网关协议调用失败！" + e.getMessage()));
           channel.writeAndFlush(response);
       }
    }
}
