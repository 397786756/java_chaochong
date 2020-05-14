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
    private Long hp;
    /**宠物攻击力*/
    @JSONField(name = "attack")
    private Integer atk;
    /**宠物防御力*/
    @JSONField(name = "defense")
    private Integer def;

    public Animal() {
    }

    public Animal(Integer id, Integer lv, Long hp, Integer atk, Integer def) {
        this.id = id;
        this.lv = lv;
        this.hp = hp;
        this.atk = atk;
        this.def = def;
    }
}
