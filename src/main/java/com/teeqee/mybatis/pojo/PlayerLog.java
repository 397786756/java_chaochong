package com.teeqee.mybatis.pojo;

import lombok.Data;

import java.util.Date;

/**
 * 用户日志
 * @author  zhengsongjie
 * @date  2020-04-30 上午 11:47
 */
@Data
public class PlayerLog {
    /**日志的uuid*/
    private Integer uid;
    /**用户的openId*/
    private String openid;
    /**用户登录时间记录日*/
    private Date loginTime;
    /**登录的次数*/
    private Integer loginTotal=0;
    /**在线时长(分钟)*/
    private Integer onlineTime=0;
    /**今日视频次数*/
    private Integer videoNum=0;
    /**玩家的指令次数,去除心跳*/
    private Integer messageNum=0;
    /**只是记录下当前时间*/
    private Date time = new Date();


    public PlayerLog() {
    }

    public PlayerLog(String openid, Date loginTime) {
        this.openid = openid;
        this.loginTime = loginTime;
    }

    /**操作次数+1*/
    public void messageNumAdd(){
        messageNum+=1;
    }
    /**登录次数+1*/
    public void loginTotalAdd(){
        loginTotal+=1;
    }
    /**视频次数+1*/
    public void videoNumAdd(){
        videoNum+=1;
    }

    /**日志的下线操作*/
    public void offline(){
        onlineTime+=(int)((System.currentTimeMillis()-time.getTime())/ 60);
    }

}