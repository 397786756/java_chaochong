package com.teeqee.spring.dispatcher.servlet.update;

import com.alibaba.fastjson.JSONObject;
import com.teeqee.net.handler.Session;
import com.teeqee.spring.dispatcher.model.MethodModel;

import com.teeqee.spring.dispatcher.servlet.rank.service.RedisService;
import com.teeqee.spring.dispatcher.servlet.rank.service.RedisServiceImpl;
import com.teeqee.spring.mode.annotation.DataSourceType;
import com.teeqee.spring.mode.annotation.Dispather;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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
     * 排行榜dao
     */
    @Resource
    private RedisService redisService;
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

    /**更新新手引导*/
    @Dispather(value = "endofguide")
    public Boolean endofguide(MethodModel model){
        return model.getSession().getPlayerData().endofguide( model.getData().getInteger("step"));
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
        return model.getSession().getPlayerData().sign( model.getData().getInteger("type"));
    }
    /**通关统计*/
    @Dispather(value = "rounds")
    public JSONObject rounds(MethodModel model) {
        JSONObject data = model.getData();
        Integer success = data.getInteger("success");
        if (success!=null){
            Integer channelid = model.getSession().getChannelid();
            Session session = model.getSession();
            redisService.addRank(channelid, RedisServiceImpl.ROUNDS_TYPE, session.getOpenId(),success.doubleValue(),true);
        }
        return model.getSession().getPlayerData().rounds(data);
    }
    /**话费增加*/
    @Dispather(value = "updatephonefarenumber")
    public Boolean updatephonefarenumber(MethodModel model) {
        Double addphonefarenumber = model.getData().getDouble("addphonefarenumber");
        return model.getSession().getPlayerData().updatephonefarenumber(addphonefarenumber);
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
    /**玩家领取vip*/
    @Dispather(value = "rewardvip")
    public Boolean rewardvip(MethodModel model) {
        return model.getSession().getPlayerData().rewardvip();
    }

    /**玩家观看视频增加vip等级*/
    @Dispather(value = "videoforvip")
    public JSONObject videoforvip(MethodModel model) {
        return model.getSession().getPlayerData().videoforvip();
    }

    /**玩家视频增加飞镖数 (玩家看视频, 不管剩余几个飞镖, 飞镖个数直接变为5)*/
    @Dispather(value = "videofordartnum")
    public Integer videofordartnum(MethodModel model) {
        return model.getSession().getPlayerData().videofordartnum();
    }

    /**飞镖没射中, 发给后端纪录次数*/
    @Dispather(value = "addmissnum")
    public Integer addmissnum(MethodModel model) {
        Session session = model.getSession();
        Integer channelid = session.getChannelid();
        redisService.addRank(channelid, RedisServiceImpl.MISSNUM_TYPE, session.getOpenId(),1D,false);
        return model.getSession().getPlayerData().addmissnum();
    }
    /**使用飞镖*/
    @Dispather(value = "useDart")
    public Boolean useDart(MethodModel model) {return model.getSession().getPlayerData().useDart();}
}
