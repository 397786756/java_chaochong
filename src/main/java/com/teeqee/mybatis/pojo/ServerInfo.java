package com.teeqee.mybatis.pojo;

import com.teeqee.spring.dispatcher.cmd.DispatcherCmd;
import lombok.Data;
import lombok.ToString;

import java.util.Date;


/**
 * 功能描述: 服务器
 * @author  zhengsongjie
 * @Date  2020-05-05 下午 07:25
 */
@Data
@ToString
public class ServerInfo {
    private Integer uuid;

    private Integer channelId;

    private String chineseName;

    private Date openTime;

    private Integer videoTime;


    /**
     * @return 返回未命中的redisKey
     */
    public String getMissRankKey(){
        return DispatcherCmd.TOP_LISTMISSNUM;
    }

}