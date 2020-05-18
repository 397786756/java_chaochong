package com.teeqee.spring.dispatcher.servlet.entity;

import lombok.Data;

/**
 * @Description: 超宠打榜对手
 * @Author: zhengsongjie
 * @Package: com.teeqee.spring.dispatcher.servlet.entity
 * @Software: IntelliJ IDEA
 */
@Data
public class Opponent {
    private Long uid;
    private Long rank;
    private String nickname="";
    private String avatar="";
    private String animal=null;

    public Opponent() {
    }

    public Opponent(Long uid, Long rank, String nickname, String avatar, String animal) {
        this.uid = uid;
        this.rank = rank;
        this.nickname = nickname;
        this.avatar = avatar;
        this.animal = animal;
    }

}
