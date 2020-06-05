package com.teeqee.spring.dispatcher.servlet.entity;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.ToString;

/**
 * @Description: 拉取任务
 * @Author: zhengsongjie
 * @Software: IntelliJ IDEA
 */

@ToString
@Data
public class Taskdata {
    @JSONField(name = "taskid")
    private Integer t;
    @JSONField(name = "number")
    private Integer n;
    @JSONField(name = "done")
    private Integer d;
    @JSONField(name = "neednumber")
    private Integer nr;

    public Taskdata(Integer t, Integer n, Integer d, Integer nr) {
        this.t = t;
        this.n = n;
        this.d = d;
        this.nr = nr;
    }

    /**初始化数据*/
    public void init() {
        d = 0;
        n = 0;
    }

    public JSONObject getJsonObject(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("t",t );
        jsonObject.put("n",n );
        jsonObject.put("d",d );
        jsonObject.put("nr",nr );
        return jsonObject;
    }
}
