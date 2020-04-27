package com.teeqee.spring.result;



import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.io.Serializable;
import java.util.function.Function;


/**
 * 功能描述:
 * 这是一个返回集
 * @author : zsj
 * @date : 2019-09-01 下午 03:28
 */
@Data
public class Result implements Serializable {
    private String cmd;
    private Object data;

    public Result() {
        super();
    }


    public Result( String cmd,Object data) {
        super();
        this.cmd=cmd;
        this.data = data;
    }



}

