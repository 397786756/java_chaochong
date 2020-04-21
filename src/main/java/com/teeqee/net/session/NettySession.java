package com.teeqee.net.session;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;

/**
 * 对应netty连接的会话
 *
 * @author fagarine
 */

public class NettySession extends AbstractSession<Channel> {

    public NettySession(Channel channel) {
        super(channel);
    }

    @Override
    public String getIp() {
        InetSocketAddress address = (InetSocketAddress) getChannel().remoteAddress();
        return address.getAddress().getHostAddress();
    }

    @Override
    public boolean isConnect() {
        return channel != null && channel.isActive();
    }

    @Override
    public void close() {
        if (isConnect()) {
            channel.close();
            channel = null;
        }
        clear();
    }

    @Override
    public void sendMessage(Object message) {
        if (channel != null && channel.isActive()) {
            channel.writeAndFlush(message);
        }
    }
}
