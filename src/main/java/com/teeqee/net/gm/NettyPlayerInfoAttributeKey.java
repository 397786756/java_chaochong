package com.teeqee.net.gm;


import com.teeqee.net.handler.Session;
import io.netty.channel.Channel;
import io.netty.util.AttributeKey;

import java.util.Date;

/**
 * 玩家数据缓存区
 */
public class NettyPlayerInfoAttributeKey {
    /**
     * 玩家内容缓冲区
     */
    public static AttributeKey<Session> PLAYER_INFO_ATTRIBUTEKEY= AttributeKey.valueOf("session");


    /**
     * @param channel 管道
     * @return 返回会话session
     */
    public static Session getSession(Channel channel){
        Session session = channel.attr(PLAYER_INFO_ATTRIBUTEKEY).get();
        if (session==null){
            session= new Session();
            session.setChannel(channel);
            session.setLoginTime(new Date());
            channel.attr(PLAYER_INFO_ATTRIBUTEKEY).set(session);
        }
        return session;
    }
}
