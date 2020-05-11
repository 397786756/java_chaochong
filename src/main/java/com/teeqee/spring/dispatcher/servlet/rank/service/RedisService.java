package com.teeqee.spring.dispatcher.servlet.rank.service;


import com.teeqee.spring.dispatcher.servlet.entity.TopRankInfo;

import java.util.List;

/**
 * @Description: 排行榜
 * @Author: zhengsongjie
 * @Software: IntelliJ IDEA
 */
public interface RedisService {

    /**
     * @param channelId 平台id
     * @param type      排行榜的类型
     * @return 返回排行榜
     */
    List<TopRankInfo> getRankList(Integer channelId, Integer type);


    /**
     * @param channelId 平台id
     * @param type      排行榜的类型
     * @param openId    玩家的openId
     * @return 返回我的个人排名
     */
    TopRankInfo getMyTopRankInfo(Integer channelId, Integer type, String openId);

    /**
     * @param channelId 平台id
     * @param type      排行榜的类型
     * @param openId    玩家的openId
     * @param score     分数
     * @param siCover   是否覆盖
     * @return 返回是否加入成功
     */
    void addRank(Integer channelId, Integer type, String openId, Double score, boolean siCover);
}
