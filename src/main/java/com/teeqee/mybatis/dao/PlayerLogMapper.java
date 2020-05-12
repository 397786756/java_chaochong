package com.teeqee.mybatis.dao;

import com.teeqee.mybatis.pojo.PlayerLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;


@Mapper
public interface PlayerLogMapper {

    int insertSelective(PlayerLog record);

    int updateByPrimaryKeySelective(PlayerLog record);
    /**
     * @param uid player uid
     * @param loginTime 今日登陆时间
     * @return 返回一个日志
     */
    PlayerLog selectByTimeLog(@Param("uid") Long  uid,@Param("loginTime") Date loginTime);
}