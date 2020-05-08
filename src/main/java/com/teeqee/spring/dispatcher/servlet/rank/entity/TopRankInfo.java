package com.teeqee.spring.dispatcher.servlet.rank.entity;

import lombok.Data;

/**
 * @Description: 打榜排行榜
 * @Author:  zhengsongjie
 * @Software: IntelliJ IDEA
 */
@Data
public class TopRankInfo {
    /**用户的uid*/
    private Integer uid;

    /**用户的uid*/
    private String openid;

    private String nickname;

    private String avatar;
    /**分数*/
    private Integer rounds;
    /**排名*/
    private Long runknum;

    public TopRankInfo( Integer rounds, Long runknum) {
        this.rounds = rounds;
        this.runknum = runknum;
    }

    public TopRankInfo() {
    }
    public void init(){
        this.uid=1;
        this.nickname="";
        this.avatar="";
        this.rounds=0;
        this.runknum=0L;
    }
}
