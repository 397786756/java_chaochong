package com.teeqee.net.handler;


import com.alibaba.fastjson.JSON;
import com.teeqee.mybatis.pojo.PlayerData;
import com.teeqee.mybatis.pojo.PlayerInfo;
import com.teeqee.mybatis.pojo.PlayerLog;
import com.teeqee.mybatis.pojo.PlayerRank;
import com.teeqee.spring.dispatcher.cmd.PlayerCmd;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 抽象类网关
 * @author : zhengosngjie
 * @Software: IntelliJ IDEA
 */

public class Session<T>  {
    /**因为超宠是登录后再传openId的*/
    private Long uid;
    /**平台默认本地服务器*/
    private Integer channelid=1;
    /**channel*/
    private Channel channel;
    /** 是否已经传openId并且登录*/
    private boolean loginStatus;
    /**登录的时间*/
    private Date loginTime=new Date();


    public boolean close(){
        if (this.channel!=null){
            if (channel.isOpen()&&channel.isActive()){
                channel.close();
                return true;
            }
        }
        return false;
    }

    public void send(Object o){
        if (o!=null){
            String s = JSON.toJSONString(o);
            TextWebSocketFrame textWebSocketFrame = new TextWebSocketFrame(s);
            channel.writeAndFlush(textWebSocketFrame);
        }
    }

    public Integer getChannelid() {
        return channelid;
    }

    public void setChannelid(Integer channelid) {
        this.channelid = channelid;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }



    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    /**存对象*/
    private Map<String, Object> keyToAttrs = new HashMap<>();

  public Long getId(){
      return uid;
  }

    /**
     * @param uid
     */
    public void isLogin(Long uid){
        if (this.uid==null){
            this.uid=uid;
            loginStatus=true;
        }
    }


    public boolean isLoginStatus() {
        return loginStatus;
    }

    public void setLoginStatus(boolean loginStatus) {
        this.loginStatus = loginStatus;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
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

    /**
     * @param key playerCmd
     * @param v 用户的数据源
     */
    public void add(String key, Object v) {
        keyToAttrs.put(key, v);
    }

    /**
     * @return 返回用户的playerData
     */
    public PlayerData getPlayerData(){
        return (PlayerData) keyToAttrs.get(PlayerCmd.PLAYER_DATA);
    }

    /**
     * @return 返回用户的playerInfo
     */
    public PlayerInfo getPlayerInfo(){
        return (PlayerInfo) keyToAttrs.get(PlayerCmd.PLAYER_INFO);
    }
    /**
     * @return 返回用户的playerLog
     */
    public PlayerLog getPlayerLog(){
        return (PlayerLog) keyToAttrs.get(PlayerCmd.PLAYER_LOG);
    }
    /**
     * @return 返回用户的playerLog
     */
    public PlayerRank getPlayerRank(){
        return (PlayerRank) keyToAttrs.get(PlayerCmd.PLAYER_RANK);
    }
}
