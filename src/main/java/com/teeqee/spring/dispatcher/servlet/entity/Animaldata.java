package com.teeqee.spring.dispatcher.servlet.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @Description: 拉取玩家动物信息
 * @Author: zhengsongjie
 * @Software: IntelliJ IDEA
 */
@Data
public class Animaldata {
    @JSONField(name="animalid")
    private Integer a;
    @JSONField(name="lv")
    private Integer l;

    public Animaldata(Integer a, Integer l) {
        this.a = a;
        this.l = l;
    }
}
