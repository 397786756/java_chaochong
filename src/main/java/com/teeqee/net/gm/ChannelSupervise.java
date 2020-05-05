package com.teeqee.net.gm;

import com.alibaba.fastjson.JSON;
import com.teeqee.net.handler.Session;
import io.netty.channel.Channel;
import io.netty.channel.ChannelId;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;


/**
 * 功能描述: netty服务器管理者
 *
 * @author zhengsongjie
 * @date 2020-03-12 下午 05:02
 */
public class ChannelSupervise {

    /**
     * group
     */
    private static ChannelGroup GlobalGroup = new DefaultChannelGroup("channelGroups", GlobalEventExecutor.INSTANCE);

    /**
     * @param channel 添加用户管道
     */
    public static void  addChannel(Channel channel) {
        GlobalGroup.add(channel);
    }

    /**
     * 移除用户的会话
     */
    public static void removeSession(Channel channel) {
        GlobalGroup.remove(channel);
    }

    /**
     * 全局发送消息
     */
    public static void sendToUser(ChannelId channelId, Object msg) {
        //发送此人
        Channel channel = GlobalGroup.find(channelId);
        if (channel != null && channel.isOpen()) {
            TextWebSocketFrame textWebSocketFrame = new TextWebSocketFrame(JSON.toJSONString(msg));
            channel.writeAndFlush(textWebSocketFrame);
        }
    }

    public static ChannelGroup getGlobalGroup() {
        return GlobalGroup;
    }
}