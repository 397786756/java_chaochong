package com.teeqee.net.session;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author: zhengsongjie
 * @File: WebsocketSeesion
 * @Version: 1.0.0
 * @Time: 2020-04-21 上午 10:03
 * @Project: java_chaochong
 * @Package: com.teeqee.net.session
 * @Software: IntelliJ IDEA
 */


public class WebsocketSeesion extends  NettySession {

    public WebsocketSeesion(Channel channel) {
        super(channel);
    }

    @Override
    public String getIp() {
        return super.getIp();
    }

    @Override
    public boolean isConnect() {
        return super.isConnect();
    }

    @Override
    public void close() {
        super.close();
    }

    @Override
    public void sendMessage(Object message) {
        super.sendMessage(message);
    }

}
