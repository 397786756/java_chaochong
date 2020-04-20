package com.teeqee.net.handler;


import com.teeqee.net.gm.ChannelSupervise;
import com.teeqee.utils.UriUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;


/**
 * 功能描述: http握手服务器,添加wss url路径用来处理玩家的服务器组名兼容httpLogin
 *
 * @author zhengsongjie
 * @Date 2020-01-09 上午 11:34
 */
@Component
@ChannelHandler.Sharable
public class HttpRequestHandler extends SimpleChannelInboundHandler<FullHttpRequest> {
    @Value("${server.socket-uri}")
    private String socketUri;


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest msg) {
        if (msg.uri().contains(socketUri)) {
            String skey = UriUtil.getParam(msg.uri(), "sKey");
            if (skey != null && skey.length() > 1) {
                ChannelSupervise.addChannel(ctx.channel());
                ctx.fireChannelRead(msg.setUri(socketUri).retain());
            } else {
                ctx.close();
            }
        } else {
            ctx.close();
        }
    }



}