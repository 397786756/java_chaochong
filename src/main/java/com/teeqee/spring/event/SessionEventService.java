package com.teeqee.spring.event;


import com.teeqee.net.handler.Session;

/**
 * @Description: 会话管理层
 * @Author: zhengsongjie
 * @Software: IntelliJ IDEA
 */
public interface SessionEventService {

    /**
     * @param session 会话
     * @param isClose 是否关闭
     */
    void close(Session session,boolean isClose);

    void send(String msg, Session session) throws Exception;

    void exceptionCaught(Session session);
}
