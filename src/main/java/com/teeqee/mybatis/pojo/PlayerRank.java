package com.teeqee.mybatis.pojo;

import lombok.Data;

/**
 * 功能描述: 玩家排行榜
 * @author  zhengsongjie
 * @Date  2020-05-12 上午 10:20
 */
@Data
public class PlayerRank {
    /**uid*/
    private Long uid;

    private Long opponent1;

    private Long opponent2;

    private Long opponent3;

    private Long opponent4;

    private Long opponent5;

    private Long opponent6;

    private Boolean isopponent;

    private Long rank;

    private Integer ranktotal;

    public PlayerRank(Long uid) {
    }

    public PlayerRank() {
    }

    /**初始化一些数据*/
    public void initData(int ranktotal,boolean isopponent){
        this.ranktotal=ranktotal;
        this.isopponent=isopponent;
    }

    /**本周是否挑战过了*/
    public boolean isRankWeek(){
        if (isopponent==null){
            return false;
        }
        return isopponent;
    }

    /**检查是否为机器人*/
    public void checkRobot(){

    }
}