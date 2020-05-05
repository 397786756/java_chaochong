package com.teeqee.spring.dispatcher.servlet.rank.service;

import com.teeqee.spring.dispatcher.servlet.rank.entity.MissRankInfo;

import java.util.List;

/**
 * @Description: 排行榜
 * @Author: zhengsongjie
 * @Software: IntelliJ IDEA
 */
public interface RedisService {

    /**
     * @param pingTai 平台id
     * @return 返回排行榜
     */
     List<MissRankInfo> getRankList(Integer pingTai, String redisKey);
}
