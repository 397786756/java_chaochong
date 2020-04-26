package com.teeqee.spring.dispatcher.model;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

/**
 * 标记着一个方法的
 */
@Data
public class Model {
    private String cmd;
    private String openId;
    private JSONObject data;

    public Model(String cmd, String openId, JSONObject data) {
        this.cmd = cmd;
        this.openId = openId;
        this.data = data;
    }
}
