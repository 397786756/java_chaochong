package com.teeqee.net.handler;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.teeqee.net.gm.ChannelSupervise;
import com.teeqee.spring.event.SessionEventService;
import io.netty.channel.*;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.timeout.IdleStateEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * websocket 握手handler
 */
@ChannelHandler.Sharable
@Component
public class WebSocketFrameHandler extends SimpleChannelInboundHandler<Object> {

    @Value("${server.socket-uri}")
    private String socketUri;

    @Resource
    private SessionEventService sessionEventService;


    @Override
    public void channelActive(ChannelHandlerContext ctx) {
         ctx.fireChannelActive();
         ChannelSupervise.addChannel(ctx.channel());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) {
        //如果是文本类型
        if (msg instanceof  TextWebSocketFrame){

        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
           cause.printStackTrace();
           ctx.close();
    }


    @Override
    public void channelInactive(ChannelHandlerContext ctx) {

    }

    /**
     * 触发事件
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent idleStateEvt = (IdleStateEvent) evt;
            switch (idleStateEvt.state()) {
                case READER_IDLE:
                    break;
                case WRITER_IDLE:
                    break;
                case ALL_IDLE:
                    ctx.channel().close();
                    break;
                default:
                    break;
            }
        }
    }
}