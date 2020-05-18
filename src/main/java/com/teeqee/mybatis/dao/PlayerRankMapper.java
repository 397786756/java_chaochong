package com.teeqee.mybatis.dao;

import com.teeqee.mybatis.pojo.PlayerRank;
import com.teeqee.spring.dispatcher.servlet.entity.Opponent;
import com.teeqee.spring.dispatcher.servlet.entity.TopRankInfo;
import com.teeqee.spring.dispatcher.servlet.rank.entity.InitPlayerRankTotal;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 功能描述: 排行榜dao
 *
 * @author zhengsongjie
 * @Date 2020-05-12 上午 10:18
 */
@Mapper
public interface PlayerRankMapper {
    int insertSelective(PlayerRank record);

    Integer findChannelIdRobotRankNum(Integer channelid);

    PlayerRank selectByPrimaryKey(@Param("uid") Long uid);

    int updateByPrimaryKeySelective(PlayerRank record);

    /**
     * @param scopeUidStart 开始的区间
     * @param scopeUidEnd   结束的区间
     * @return
     */
    int deleteByScopeUid(@Param("scopeUidStart") Integer scopeUidStart, @Param("scopeUidEnd") Integer scopeUidEnd);

    /**
     * 去数据库拉取玩家
     */
    Opponent selectChannelidPlayerRank(@Param("channelid") Integer channelid, @Param("uid") Long uid);


    /**
     * @param channelid 渠道的id
     * @return 初始化的时候去服务器拉取排行榜
     */
    List<InitPlayerRankTotal> initPlayerRankTotal(@Param("channelid") Integer channelid);
}