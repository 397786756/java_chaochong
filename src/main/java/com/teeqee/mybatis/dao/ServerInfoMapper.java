package com.teeqee.mybatis.dao;

import com.teeqee.mybatis.pojo.ServerInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 功能描述: gameserver dao
 * @author  zhengsongjie
 * @Date  2020-05-05 下午 07:26
 */
@Mapper
public interface ServerInfoMapper {
    List<ServerInfo> serverInfoList();
}