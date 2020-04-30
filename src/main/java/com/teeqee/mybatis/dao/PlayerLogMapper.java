package com.teeqee.mybatis.dao;

import com.teeqee.mybatis.pojo.PlayerLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;


@Mapper
public interface PlayerLogMapper {
    int deleteByPrimaryKey(Integer uid);

    int insert(PlayerLog record);

    int insertSelective(PlayerLog record);

    PlayerLog selectByPrimaryKey(Integer uid);

    int updateByPrimaryKeySelective(PlayerLog record);

    int updateByPrimaryKey(PlayerLog record);

    /**
     * @param openid player openId
     * @param loginTime 今日登陆时间
     * @return 返回一个日志
     */
    PlayerLog selectByTimeLog(@Param("openid") String  openid,@Param("loginTime") Date loginTime);
}