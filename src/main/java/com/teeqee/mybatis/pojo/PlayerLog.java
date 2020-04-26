package com.teeqee.mybatis.pojo;

import lombok.Data;

import java.util.Date;

/**
 * @description: 用户的日志信息
 * @author :zhengsongjie
 * @Package: com.teeqee.mybatis.pojo
 * @Software: IntelliJ IDEA
 */
@Data
public class PlayerLog {
    /**登录的时间*/
    private Date loginTime;
    /**玩家操作次数*/
    private Long actionNum;
    /***/
}
