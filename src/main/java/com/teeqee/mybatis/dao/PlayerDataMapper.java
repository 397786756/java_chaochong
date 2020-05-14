package com.teeqee.mybatis.dao;

import com.teeqee.mybatis.pojo.PlayerData;
import com.teeqee.spring.dispatcher.servlet.entity.TopRankInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 功能描述: player Dao
 * @author  zhengsongjie
 * @Date  2020-05-05 上午 10:43
 */

@Mapper
public interface PlayerDataMapper {

    int insertSelective(PlayerData record);

    PlayerData selectByPrimaryKey(Long uid);


    int updateByPrimaryKeySelective(PlayerData record);

    /**
     * @param channelid 渠道
     * @param orderByName 需要排行的name
     * @return 返回初始化的数据用于排行榜
     */
    List<TopRankInfo> initTopRank(@Param("channelid") Integer channelid, @Param("orderByName") String orderByName,@Param("lasttime") Date pastDate);
}