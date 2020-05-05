package com.teeqee.spring.dispatcher.servlet.entity;

import lombok.Data;

/**
 * @Description: 排行榜简单实体类
 * @Author: zhengsongjie
 * @Software: IntelliJ IDEA
 */
@Data
public class RankInfo {
    /**用户的uid*/
    private Integer uid;
    private String nickname;
    private String avatar;
    /**未射中次数*/
    private Integer rounds;
    /**排名*/
    private Long runknum;

    public RankInfo(Integer uid, String nickname, String avatar, Integer rounds, Long runknum) {
        this.uid = uid;
        this.nickname = nickname;
        this.avatar = avatar;
        this.rounds = rounds;
        this.runknum = runknum;
    }
}
