package com.teeqee.spring.dispatcher.servlet.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @Description: 动物
 * @Author: zhengsongjie
 * @Package: com.teeqee.spring.dispatcher.servlet.entity
 * @Software: IntelliJ IDEA
 */
@Data
public class Animal {
    /**宠物id*/
    @JSONField(name = "animalid")
    private Integer id;
    /**宠物等级*/
    @JSONField(name = "lv")
    private Integer lv;
    /**宠物血量*/
    @JSONField(name = "blood")
    private Long blood;
    /**宠物攻击力*/
    @JSONField(name = "attack")
    private Integer attack;
    /**宠物防御力*/
    @JSONField(name = "defense")
    private Integer defense;
}
