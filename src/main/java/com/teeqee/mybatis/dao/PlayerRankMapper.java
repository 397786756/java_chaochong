package com.teeqee.mybatis.dao;

import com.teeqee.mybatis.pojo.PlayerRank;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 功能描述: 排行榜dao
 * @author  zhengsongjie
 * @Date  2020-05-12 上午 10:18
 */
@Mapper
public interface PlayerRankMapper {
    int insertSelective(PlayerRank record);

    Integer findChannelIdRobotRankNum(Integer channelid);

    int updateByPrimaryKeySelective(PlayerRank record);


    /**
     * @param scopeUid 机器人的uid区间
     * @return 进行删除
     */
    int deleteByScopeUid(@Param("scopeUidStart") Integer scopeUidStart, @Param("scopeUidEnd") Integer scopeUidEnd);
}