package com.teeqee.spring.dispatcher.servlet.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @Description: 听说是日活动类型
 * @Author: zhengsongjie
 * @File: Reward
 * @Version: 1.0.0
 * @Time: 2020-06-05 上午 10:14
 * @Project: java_chaochong
 * @Package: com.teeqee.spring.dispatcher.servlet.entity
 * @Software: IntelliJ IDEA
 */
@Data
public class Reward {
    /** 奖励id （先后顺序）*/
    @JSONField(name = "rewardid")
    private int id;
    /**奖励是否已经领取 0代表未领取 1代表已经领取*/
    @JSONField(name = "rewarded")
    private int ed;
}
