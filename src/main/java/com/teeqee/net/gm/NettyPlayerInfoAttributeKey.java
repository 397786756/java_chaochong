package com.teeqee.net.gm;


import io.netty.util.AttributeKey;

/**
 * 玩家数据缓存区
 */
public class NettyPlayerInfoAttributeKey {
    /**
     * 玩家内容缓冲区
     */
    public static AttributeKey<Object> PLAYER_INFO_ATTRIBUTEKEY= AttributeKey.valueOf("gm");

}
