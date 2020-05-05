package com.teeqee.spring.dispatcher.servlet.update;

import com.alibaba.fastjson.JSONObject;
import com.teeqee.spring.dispatcher.model.MethodModel;

import com.teeqee.spring.mode.annotation.DataSourceType;
import com.teeqee.spring.mode.annotation.Dispather;
import org.springframework.stereotype.Service;

/**
 * @Description: 用户修改数据
 * @Author: zhengsongjie
 * @Project: java_chaochong
 * @Software: IntelliJ IDEA
 */
@DataSourceType("update")
@Service
public class PlayerUpdate {



    /**
     * 据说这是一个心跳,因为上个后端的原因所有数据都是客户端储存的
     * 根据文档有很多需要存的
     */
    @Dispather(value = "newheart")
    public JSONObject newheart(MethodModel model){
       return model.getSession().getPlayerData().newheart(model.getData());
    }


    /**
     * 获取幸运大转盘
     */
    @Dispather(value = "getdartboard")
    public JSONObject getdartboard(MethodModel model){
        return model.getSession().getPlayerData().getdartboard();
    }

}
