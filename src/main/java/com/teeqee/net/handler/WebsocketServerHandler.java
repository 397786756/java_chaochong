package com.teeqee.net.handler;

import com.teeqee.net.gm.ChannelSupervise;
import com.teeqee.net.gm.NettyPlayerInfoAttributeKey;
import com.teeqee.spring.event.SessionEventService;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Description:
 * @Author: zhengsongjie
 * @File: WebsocketServerHandler
 * @Version: 1.0.0
 * @Time: 2020-04-21 上午 11:04
 * @Project: java_chaochong
 * @Package: com.teeqee.net.handler
 * @Software: IntelliJ IDEA
 */
@Component
@ChannelHandler.Sharable
public class WebsocketServerHandler extends SimpleChannelInboundHandler<Object> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Value("${server.socket-uri}")
    private String socketUri;
    @Resource
    private SessionEventService sessionEventService;
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        ctx.fireChannelActive();
        ChannelSupervise.addChannel(ctx.channel());
        NettyPlayerInfoAttributeKey.getSession(ctx.channel());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        sessionEventService.close(ctx.channel().attr(NettyPlayerInfoAttributeKey.PLAYER_INFO_ATTRIBUTEKEY).get(),true);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        super.userEventTriggered(ctx, evt);
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent idleStateEvt = (IdleStateEvent) evt;
            switch (idleStateEvt.state()) {
                case READER_IDLE:
                    break;
                case WRITER_IDLE:
                    break;
                case ALL_IDLE:
                    channelInactive(ctx);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
       // sessionEventService.close(session);
    }


    @Override
    public void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof TextWebSocketFrame) {
            sessionEventService.send(((TextWebSocketFrame) msg).text(),NettyPlayerInfoAttributeKey.getSession(ctx.channel()));
        }else if (msg instanceof FullHttpRequest ){
            FullHttpRequest request = (FullHttpRequest) msg;
            if (request.uri().contains(socketUri)) {
                ctx.fireChannelRead(request.setUri(socketUri).retain());
            } else {
                ctx.close();
            }
            logger.info("websocket connect ={}",ctx.channel().id().asLongText());
        }
    }
}
