package com.teeqee.spring.dispatcher.servlet.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @Description: 世界打榜的返回的结果集
 * @Author: zhengsongjie
 * @Software: IntelliJ IDEA
 */
@Data
public class WorldRankEnd {
    /**1为赢了, 0为输了*/
    @JSONField(name = "iswin")
    private Integer iswin;
    /**对手的uid*/
    @JSONField(name = "opponentuid")
    private Long opponentuid;
    /**我的阵容*/
    @JSONField(name = "animal")
    private String animal;
    /**对方的阵容*/
    @JSONField(name = "opponentanimal")
    private String opponentanimal;
    /**我的打榜排名(打之前)*/
    @JSONField(name = "rank")
    private Long rank;
    /**敌人的打榜排名*/
    @JSONField(name = "opponentrank")
    private Long opponentrank;
    /**听说是金币*/
    @JSONField(name = "gold")
    private Integer gold;

    public WorldRankEnd() {
    }

    public WorldRankEnd(Integer iswin, Long opponentuid, String animal, String opponentanimal, Long rank, Long opponentrank, Integer gold) {
        this.iswin = iswin;
        this.opponentuid = opponentuid;
        this.animal = animal;
        this.opponentanimal = opponentanimal;
        this.rank = rank;
        this.opponentrank = opponentrank;
        this.gold = gold;
    }
}
