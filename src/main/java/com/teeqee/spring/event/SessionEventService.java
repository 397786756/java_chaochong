package com.teeqee.spring.event;

import com.alibaba.fastjson.JSONObject;

import io.netty.channel.Channel;

/**
 * @Description: 会话管理层
 * @Author: zhengsongjie
 * @Software: IntelliJ IDEA
 */
public interface SessionEventService {


    void getId(Channel channel);

    void open(Channel channel);

    void close(Channel channel);

    void send(Channel channel, String msg);

    void exceptionCaught(Channel channel);
}
