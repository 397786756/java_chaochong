package com.teeqee.mybatis.dao;


import com.teeqee.mybatis.pojo.Player;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PlayerMapper {

    int insertSelective(Player record);

    Player selectByPrimaryKey(String uuid);

    int updateByPrimaryKeySelective(Player record);

}