package com.teeqee.spring.dispatcher.servlet.entity;

import lombok.Data;
import lombok.ToString;

/**
 * @Description: 玩家获取活跃度相关
 * @Author: zhengsongjie
 * @Software: IntelliJ IDEA
 */
@Data
@ToString
public class Active {
    /**本条数据唯一标识*/
    private int dataid;
    /**活跃度类型 1代表日活跃 2代表周活跃*/
    private int kind;
    /**活跃度*/
    private int liveness;
    /**奖励id （先后顺序）*/
    private int rewardid;
    /**奖励是否已经领取 0代表未领取 1代表已经领取*/
    private int rewarded;
}

