package com.teeqee.net.handler;


import com.teeqee.mybatis.pojo.PlayerInfo;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 抽象类网关
 * @author : zhengosngjie
 * @Software: IntelliJ IDEA
 */

public abstract class AbstractSession<T> extends SimpleChannelInboundHandler {


    /**因为超宠是登录后再传openId的*/
    private String openId;

    protected String pingTai;

    protected Channel channel;


    /** 是否已经传openId*/
    private boolean sendOpenId=false;

    /**存对象*/
    private Map<String, Object> keyToAttrs = new HashMap<>();

    private Date loginTime=new Date();


    public String getOpenId() {
        return openId;
    }



    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public Map<String, Object> getMap() {
        return keyToAttrs;
    }

    public void setMap(Map<String, Object> map) {
        this.keyToAttrs = map;
    }

    public Object getAttr(String key) {
        return keyToAttrs.get(key);
    }

    public void add(String key, Object v) {
        keyToAttrs.put(key, v);
    }

    /**
     * @param cmd 获取对象
     * @return 返回对象
     */
    public PlayerInfo getPlayerInfo(String cmd){
        return (PlayerInfo) keyToAttrs.get(cmd);
    }


    public void getOnLineInfo(String openId){

    }

}
