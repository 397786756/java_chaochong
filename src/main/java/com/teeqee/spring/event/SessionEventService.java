package com.teeqee.spring.event;


import com.teeqee.net.handler.Session;
import io.netty.handler.codec.http.FullHttpRequest;

/**
 * @Description: 会话管理层
 * @Author: zhengsongjie
 * @Software: IntelliJ IDEA
 */
public interface SessionEventService {


    void open(Session session);

    void close(Session session);

    void send(String msg, Session session) throws Exception;

    void exceptionCaught(Session session);
}
