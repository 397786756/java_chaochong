package com.teeqee.mybatis.dao;

import com.teeqee.mybatis.pojo.PlayerData;
import com.teeqee.mybatis.pojo.PlayerInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * 功能描述: player Dao
 * @author  zhengsongjie
 * @Date  2020-05-05 上午 10:43
 */

@Mapper
public interface PlayerDataMapper {

    int insertSelective(PlayerData record);

    PlayerData selectByPrimaryKey(String openid);

    int updateByPrimaryKeySelective(PlayerData record);

}