package com.teeqee.mybatis.dao;

import com.teeqee.mybatis.pojo.PlayerRankLog;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PlayerRankLogMapper {

    int insertSelective(PlayerRankLog record);

    List<PlayerRankLog> selectByPrimaryKey(Long uid);

}