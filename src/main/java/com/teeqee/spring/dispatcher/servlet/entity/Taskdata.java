package com.teeqee.spring.dispatcher.servlet.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.ToString;

/**
 * @Description: 拉取任务
 * @Author: zhengsongjie
 * @Software: IntelliJ IDEA
 */
@Data
@ToString
public class Taskdata {
    @JSONField(name="taskid")
    private Integer t;
    @JSONField(name="number")
    private Integer n;
    @JSONField(name="done")
    private Integer d;

    public Taskdata(Integer t, Integer n, Integer d) {
        this.t = t;
        this.n = n;
        this.d = d;
    }
}
