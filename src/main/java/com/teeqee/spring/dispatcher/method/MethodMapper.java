package com.teeqee.spring.dispatcher.method;

import com.teeqee.spring.dispatcher.cmd.DispatcherCmd;
import com.teeqee.spring.dispatcher.model.MethodModel;
import com.teeqee.spring.dispatcher.servlet.login.Login;
import com.teeqee.spring.dispatcher.servlet.update.PlayerUpdate;
import com.teeqee.spring.result.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;


@Component
public class MethodMapper implements CommandLineRunner{
    /**login push*/
    @Resource
    private Login login;
    /**update*/
    @Resource
    private PlayerUpdate playerUpdate;
    /**method map*/
    private ConcurrentHashMap<String, Function<MethodModel, Object>> map=new ConcurrentHashMap<>(1024);

    public Result run(MethodModel model) {
        String cmd = model.getCmd();
        Function<MethodModel, Object> function = map.get(cmd);
        if (function!=null){
            //结果集可能不返回
            return new Result(cmd, function.apply(model));
        }else {
            return new Result("error","undefined "+cmd);
        }
    }

    @Override
    public void run(String... args) {
        /**用户登录*/
        map.put(DispatcherCmd.LOGIN,map->login.login(map));
        /**获取宠物的位置信息*/
        map.put(DispatcherCmd.GET_SITE,map->login.getsite(map));
        /**拉取玩家动物信息*/
        map.put(DispatcherCmd.GET_ANIMAL,map->login.getanimal(map));
        /**拉取任务信息*/
        map.put(DispatcherCmd.GET_TASK,map->login.gettask(map));
        /**修改头像昵称语言 返回空信息*/
        map.put(DispatcherCmd.USER_INFOR,map->login.userinfor(map));
        /**从后端中拉取缓存*/
        map.put(DispatcherCmd.GET_CACHE,map->login.getcache(map));
        /**很长很大的心跳包*/
        map.put(DispatcherCmd.NEW_HEART,map->playerUpdate.newheart(map));
        /**拉取建筑*/
        map.put(DispatcherCmd.GET_BUILDING,map->login.getbuilding(map));
        /**拉取幸运转盘*/
        map.put(DispatcherCmd.GET_DARTBOARD,map->playerUpdate.getdartboard(map));
        /**更新新手引导步数*/
        map.put(DispatcherCmd.ENDOFGUIDE,map->playerUpdate.endofguide(map));
        /**更新新手引导步数*/
        map.put(DispatcherCmd.CLOSE_SOUND,map->playerUpdate.closesound(map));
        /**更新新手引导步数*/
        map.put(DispatcherCmd.OPEN_SOUND,map->playerUpdate.opensound(map));
        /**玩家获取活跃度相关*/
        map.put(DispatcherCmd.GET_ACTIVE,map->playerUpdate.getactive(map));
        /**玩家获取自己vip相关信息*/
        map.put(DispatcherCmd.GET_VIP,map->playerUpdate.getvip(map));
        /**签到*/
        map.put(DispatcherCmd.SIGN,map->playerUpdate.sign(map));
        /**通关统计*/
        map.put(DispatcherCmd.ROUNDS,map->playerUpdate.rounds(map));
        /**更新玩家话费数量*/
        map.put(DispatcherCmd.UPDATE_PHONEFARENUMBER,map->playerUpdate.updatephonefarenumber(map));
        /**更新玩家显示领取话费*/
        map.put(DispatcherCmd.UPDATE_PHONEFARE,map->playerUpdate.updatephonefare(map));
        /**更新玩家显示领取话费*/
        map.put(DispatcherCmd.SHARE_FOR_CHALLENGE,map->playerUpdate.shareforchallenge(map));
    }
}
