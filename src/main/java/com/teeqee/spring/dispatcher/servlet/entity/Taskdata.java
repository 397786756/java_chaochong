package com.teeqee.spring.dispatcher.servlet.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.ToString;

/**
 * @Description: 拉取任务
 * @Author: zhengsongjie
 * @Software: IntelliJ IDEA
 */

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

    public Integer getT() {
        return t;
    }

    public void setT(Integer t) {
        this.t = t;
    }

    public Integer getN() {
        return n;
    }

    public void setN(Integer n) {
        this.n = n;
    }

    public Integer getD() {
        return d;
    }

    public void setD(Integer d) {
        this.d = d;
    }
}
