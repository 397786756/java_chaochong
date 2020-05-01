package com.teeqee.net.handler;


import com.teeqee.mybatis.pojo.PlayerData;
import com.teeqee.mybatis.pojo.PlayerInfo;
import com.teeqee.mybatis.pojo.PlayerLog;
import com.teeqee.spring.dispatcher.cmd.PlayerCmd;
import io.netty.channel.Channel;


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
    private String openid;
    /**平台*/
    private Integer pingTai;
    /**channel*/
    private Channel channel;

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    /** 是否已经传openId并且登录*/
    private boolean loginStatus;
    /**登录的时间*/
    private Date loginTime=new Date();

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    /**存对象*/
    private Map<String, Object> keyToAttrs = new HashMap<>();

  public String getId(){
      return openid;
  }

    /**
     * @param openid 玩家的openid 标记着已经登录了
     */
    public void isLogin(String openid){
        if (this.openid==null){
            this.openid=openid;
            loginStatus=true;
        }
    }


    public boolean isLoginStatus() {
        return loginStatus;
    }

    public void setLoginStatus(boolean loginStatus) {
        this.loginStatus = loginStatus;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getOpenId() {
        return openid;
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
}
