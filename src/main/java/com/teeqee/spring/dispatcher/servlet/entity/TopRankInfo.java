package com.teeqee.spring.dispatcher.servlet.entity;

import lombok.Data;

/**
 * @Description: 打榜排行榜
 * @Author:   zhengsongjie
 * @Software: IntelliJ IDEA
 */
@Data
public class TopRankInfo {
    /**用户的uid*/
    private Long uid;

    private String nickname="未授权的玩家";

    private String avatar="";
    /**分数*/
    private Integer rounds;
    /**排名*/
    private Long ranknum;

    public TopRankInfo( Integer rounds, Long ranknum) {
        this.rounds = rounds;
        this.ranknum = ranknum;
    }

    public TopRankInfo() {
    }
    public void init(){
        this.uid=1L;
        this.nickname="";
        this.avatar="";
        this.rounds=0;
        this.ranknum=0L;
    }
}
