package com.teeqee.spring.event;


import io.netty.handler.codec.http.FullHttpRequest;

/**
 * @Description: 会话管理层
 * @Author: zhengsongjie
 * @Software: IntelliJ IDEA
 */
public interface SessionEventService<T> {


    void open(T session);

    void close(T session);

    void send(String msg, T session) throws Exception;

    void exceptionCaught(T session);
}
