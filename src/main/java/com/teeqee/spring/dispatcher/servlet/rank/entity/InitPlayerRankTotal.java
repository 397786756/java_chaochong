package com.teeqee.spring.dispatcher.servlet.rank.entity;

import lombok.Data;
import lombok.ToString;

/**
 * @Description: 初始化的排行榜,按照排名和时间
 * @Author: zhengsongjie
 * @Software: IntelliJ IDEA
 */
@Data
@ToString
public class InitPlayerRankTotal {
private Long uid;
private Long rank;
}
