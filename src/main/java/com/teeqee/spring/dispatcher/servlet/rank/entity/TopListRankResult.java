package com.teeqee.spring.dispatcher.servlet.rank.entity;

import lombok.Data;

import java.util.List;

/**
 * @Description: 世界打榜结果集
 * @Author: zhengsongjie
 * @Software: IntelliJ IDEA
 */
@Data
public class TopListRankResult {
    /**我的排名*/
    private Long myRank;
    /**拉取到的玩家的集合*/
    private List<Long> playerList;
    /**玩家的排名*/
    private List<Long> xList;

    public TopListRankResult(Long myRank, List<Long> playerList, List<Long> xList) {
        this.myRank = myRank;
        this.playerList = playerList;
        this.xList = xList;
    }
}
