package com.teeqee.mybatis.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class PlayerRankLog {
    private Integer id;
    /**玩家的uid*/
    private Long uid;
    /**被挑战者玩家的uid*/
    private Long uid2;
    /**是否赢了*/
    private Integer iswin;
    /**挑战之前的排名*/
    private Long beforerank;
    /**挑战之后的排名*/
    private Long afterrank;
    /**挑战者的时间戳*/
    private Date datatime;
    /**挑战者的昵称*/
    private String opponentnickname;
    /**挑战者的头像*/
    private String opponentavatar;
}