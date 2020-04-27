package com.teeqee.spring.dispatcher.servlet.login.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * @Description: 获取宠物的位置信息
 * @Software: IntelliJ IDEA
 */
@Data
public class Site {
    @JSONField(name="siteid")
    private int s;
    @JSONField(name="animalid")
    private int a;
}
