package com.teeqee.spring.dispatcher.servlet.entity;

import lombok.Data;

/**
 * @Description: 排行榜简单实体类
 * @Author: zhengsongjie
 * @Software: IntelliJ IDEA
 */
@Data
public class RankInfo {
    private String nickname;
    private String avatar;
    private Integer rounds;

}
