package com.teeqee.mybatis.dao;

import com.teeqee.mybatis.pojo.PlayerData;
import com.teeqee.mybatis.pojo.PlayerInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PlayerDataMapper {
    int insert(PlayerData record);

    int insertSelective(PlayerData record);

    PlayerData selectByPrimaryKey(String openid);
}