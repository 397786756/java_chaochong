package com.teeqee.spring.dispatcher.servlet.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.ToString;

/**
 * @Description: 获取宠物的位置信息
 * @Software: IntelliJ IDEA
 */

@ToString
public class Site {
    @JSONField(name="siteid")
    private int s;
    @JSONField(name="animalid")
    private int a;

    public int getS() {
        return s;
    }

    public int getA() {
        return a;
    }

    public void setS(int s) {
        this.s = s;
    }

    public void setA(int a) {
        this.a = a;
    }

    public Site(int s, int a) {
        this.s = s;
        this.a = a;
    }
}
