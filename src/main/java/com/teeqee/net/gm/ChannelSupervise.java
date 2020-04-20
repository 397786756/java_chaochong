package com.teeqee.net.gm;

import com.alibaba.fastjson.JSON;
import io.netty.channel.Channel;
import io.netty.channel.ChannelId;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;


/**
 * 功能描述: netty服务器管理者
 * @author zhengsongjie
 * @date 2020-03-12 下午 05:02
 */
public class ChannelSupervise {

    static final String ERROR_CMD = "error";
    static final Integer ERROD_CODE = 500;
    static final String DATA = "dataError";

    /**
     * group
     */
    private static ChannelGroup GlobalGroup = new DefaultChannelGroup("channelGroups", GlobalEventExecutor.INSTANCE);


    public static ChannelGroup getGlobalGroup() {
        return GlobalGroup;
    }

    /**
     * serverName  channelId
     */
    private static ConcurrentHashMap<String, HashMap<String, ChannelId>> ServerNameMap = new ConcurrentHashMap<>(1024);




    /**
     * 移除所有
     */
    public static void removeAll() {
        Iterator<Channel> iterator = GlobalGroup.iterator();
        while (iterator.hasNext()) {
            Channel channel = iterator.next();
            channel.close();
            GlobalGroup.remove(channel);
        }
        //清除hashMaP
        ServerNameMap.clear();
    }

    /**

     * @param channel 返回用户管道id
     */
    public static void addChannel( Channel channel) {
        GlobalGroup.add(channel);
    }

    public static void removeChannel(Channel channel) {

    }


    public static void sendToUser(ChannelId channelId, Object msg) {
        //发送此人
        Channel channel = GlobalGroup.find(channelId);
        if (channel != null && channel.isOpen()) {
            TextWebSocketFrame textWebSocketFrame = new TextWebSocketFrame(JSON.toJSONString(msg));
            channel.writeAndFlush(textWebSocketFrame);
        }
    }

    public static void sendToAll(String msg) {
        //发送全局消息(例如停服功能)
        TextWebSocketFrame textWebSocketFrame = new TextWebSocketFrame(msg);
        GlobalGroup.writeAndFlush(textWebSocketFrame);
    }


    /**
     * @param serverName 服务器的名字
     * @param countryId  国家的id
     * @param jsonObject 制定的消息内容
     */
    public static void sendToServerName(String serverName, Integer countryId, Object jsonObject) {

    }
}