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
        JSONObject data = model.getData();
        return model.getSession().getPlayerData().newheart(data);
    }


    /**
     * 获取幸运大转盘
     */
    @Dispather(value = "getdartboard")
    public JSONObject getdartboard(MethodModel model){
        return model.getSession().getPlayerData().getdartboard();
    }

    /**更新新手引导*/
    @Dispather(value = "endofguide")
    public Boolean endofguide(MethodModel model){
        JSONObject data = model.getData();
        Integer step = data.getInteger("step");
        return model.getSession().getPlayerData().endofguide(step);
    }

    /**关闭声音*/
    @Dispather(value = "closesound")
    public Boolean closesound(MethodModel model){
        return model.getSession().getPlayerData().closesound();
    }

    /**开启声音*/
    @Dispather(value = "opensound")
    public Boolean opensound(MethodModel model){
        return model.getSession().getPlayerData().opensound();
    }

    /**玩家获取活跃度相关*/
    @Dispather(value = "getactive")
    public JSONObject getactive(MethodModel model){
        return model.getSession().getPlayerData().getactive();
    }
    /**玩家获取自己vip相关信息 不能使用get方法作为普通方法*/
    @Dispather(value = "getvip")
    public JSONObject getvip(MethodModel model) {
        return model.getSession().getPlayerData().getvipInfo();
    }
    /**玩家领取离线奖励*/
    @Dispather(value = "sign")
    public Boolean sign(MethodModel model) {
        Integer type = model.getData().getInteger("type");
        return model.getSession().getPlayerData().sign(type);
    }
    /**通关统计*/
    @Dispather(value = "rounds")
    public JSONObject rounds(MethodModel model) {
        return model.getSession().getPlayerData().rounds(model.getData());
    }
    /**话费增加*/
    @Dispather(value = "updatephonefarenumber")
    public Boolean updatephonefarenumber(MethodModel model) {
        return model.getSession().getPlayerData().updatephonefarenumber(model.getData().getDouble("addphonefarenumber"));
    }
    /**更新玩家显示领取话费*/
    @Dispather(value = "updatephonefare")
    public Integer updatephonefare(MethodModel model) {
        return model.getSession().getPlayerData().updatephonefare();
    }
    /**玩家分享得打榜次数*/
    @Dispather(value = "shareforchallenge")
    public JSONObject shareforchallenge(MethodModel model) {
        return model.getSession().getPlayerData().shareforchallenge();
    }
}
