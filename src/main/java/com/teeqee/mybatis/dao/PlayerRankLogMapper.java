package com.teeqee.mybatis.dao;

import com.teeqee.mybatis.pojo.PlayerRankLog;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PlayerRankLogMapper {

    int insertSelective(PlayerRankLog record);

    PlayerRankLog selectByPrimaryKey(Long uid);

}