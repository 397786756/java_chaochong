package com.teeqee.mybatis.dao;

import com.teeqee.mybatis.pojo.PlayerInfo;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface PlayerInfoMapper {
    int deleteByPrimaryKey(Integer uid);

    int insert(PlayerInfo record);

    int insertSelective(PlayerInfo record);

    PlayerInfo selectByPrimaryKey(String openid);

    int updateByPrimaryKeySelective(PlayerInfo record);

    int updateByPrimaryKey(PlayerInfo record);
}