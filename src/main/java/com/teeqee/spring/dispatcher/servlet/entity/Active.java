package com.teeqee.spring.dispatcher.servlet.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

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
    @JSONField(name = "rewardlist")
    private List<Reward> list;
    /**活跃度*/
    @JSONField(name = "liveness")
    private int live;

    public void init(int kind){
        k=kind;
        live=0;
        if (list==null){
            list=new ArrayList<>(16);
        }
        //听说有五个
        int rewardlistSize =5;
        int size = list.size();
        for (int i = 0; i < rewardlistSize; i++) {
            if (rewardlistSize!=size){
                Reward  reward=new Reward();
                int rewardid = reward.getId();
                if (rewardid==0){
                    reward.setId(i);
                }
                list.add(reward);
            }
        }
    }
}

