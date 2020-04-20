package com.teeqee.net.gm;


import io.netty.util.AttributeKey;

/**
 * @Description: 用来存放一些玩家的缓冲数据
 * @Author: zhengsongjie
 * @File: NettyPlayerInfoAttributeKey
 * @Version: 1.0.0
 * @Time: 2020-01-16 下午 04:20
 * @Project: threeKingdomsGame
 * @Package: com.teeqee.threeKingdomsGame.wx.miniapp.netty.gm.gm
 * @Software: IntelliJ IDEA
 */
public class NettyPlayerInfoAttributeKey {
    /**
     * 玩家内容缓冲区
     */
    public static AttributeKey<Object> PLAYER_INFO_ATTRIBUTEKEY= AttributeKey.valueOf("gm");

}
