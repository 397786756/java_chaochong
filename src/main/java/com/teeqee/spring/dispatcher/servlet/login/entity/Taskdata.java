package com.teeqee.spring.dispatcher.servlet.login.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @Description:
 * @Author: zhengsongjie
 * @Software: IntelliJ IDEA
 */
@Data
public class Taskdata {
    @JSONField(name="taskid")
    private Integer t;
    @JSONField(name="number")
    private Integer n;
    @JSONField(name="done")
    private Integer d;
}
