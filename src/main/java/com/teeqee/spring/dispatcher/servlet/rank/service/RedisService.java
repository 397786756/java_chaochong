package com.teeqee.spring.dispatcher.servlet.rank.service;


import com.teeqee.spring.dispatcher.servlet.rank.entity.TopRankInfo;

import java.util.List;

/**
 * @Description: 排行榜
 * @Author: zhengsongjie
 * @Software: IntelliJ IDEA
 */
public interface RedisService {

    /**
     * @param channelId 平台id
     * @return 返回排行榜
     */
    List<TopRankInfo> getRankList(Integer channelId, String redisKey);


    /**
     * @param channelId 平台id
     * @return 返回世界排行榜
     */
    List<TopRankInfo> getTopRankList(Integer channelId, String redisKey);

    /**
     * @param channelId 平台id
     * @return 返回我的个人排名
     */
    TopRankInfo getMyTopRankInfo(Integer channelId, String redisKey, String openId);

    /**
     * @param channelId  平台id
     * @param redisKey redis的Key
     * @param openId   玩家的openId
     * @param score    分数
     * @return 返回是否加入成功
     */
    Boolean addRank(Integer channelId, String redisKey, String openId, Double score);
}
