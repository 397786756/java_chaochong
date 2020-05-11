package com.teeqee.spring.dispatcher.servlet.entity;

import com.alibaba.fastjson.annotation.JSONField;
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
    /**活跃度类型 1代表日活跃 2代表周活跃*/
    @JSONField(name = "kind")
    private int k;
    /**奖励id*/
    @JSONField(name = "rewardid")
    private int r;
    /**奖励是否已经领取 0代表未领取 1代表已经领取*/
    @JSONField(name = "rewarded")
    private int re;

}

