package com.teeqee.spring.dispatcher.servlet.rank.entity;

import lombok.Data;

/**
 * @Description: 未命中排行榜
 * @Author: zhengsongjie
 * @Software: IntelliJ IDEA
 */
@Data
public class MissRankInfo {
    /**用户的uid*/
    private Integer uid;
    private String nickname;
    private String avatar;
    /**未射中次数*/
    private Integer missnum;
    /**排名*/
    private Long runknum;

    public MissRankInfo(Integer uid, String nickname, String avatar, Integer missnum, Long runknum) {
        this.uid = uid;
        this.nickname = nickname;
        this.avatar = avatar;
        this.missnum = missnum;
        this.runknum = runknum;
    }
}
