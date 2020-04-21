package com.teeqee.spring.event;

import com.alibaba.fastjson.JSONObject;

import io.netty.channel.Channel;

/**
 * @Description: 会话管理层
 * @Author: zhengsongjie
 * @Software: IntelliJ IDEA
 */
public interface SessionEventService<T> {

    void getId(T channel);

    void open(T channel);

    void close(T channel);

    void send( String msg,T channel);

    void exceptionCaught(T channel);
}
