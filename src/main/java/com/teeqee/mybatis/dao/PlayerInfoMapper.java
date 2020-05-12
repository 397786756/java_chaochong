package com.teeqee.mybatis.dao;

import com.teeqee.mybatis.pojo.PlayerInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PlayerInfoMapper {

    int insertSelective(PlayerInfo record);

    PlayerInfo selectByPrimaryKey(Long uuid);

    PlayerInfo selectByOpenid(String openid);

    int updateByPrimaryKeySelective(PlayerInfo record);

    int deleteByScopeUid(@Param("scopeUidStart") Integer scopeUidStart, @Param("scopeUidEnd") Integer scopeUidEnd);
}