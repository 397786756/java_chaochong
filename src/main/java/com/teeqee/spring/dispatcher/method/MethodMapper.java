package com.teeqee.spring.dispatcher.method;

import com.teeqee.spring.dispatcher.cmd.DispatcherCmd;
import com.teeqee.spring.dispatcher.model.MethodModel;
import com.teeqee.spring.dispatcher.servlet.login.PlayerLogin;
import com.teeqee.spring.dispatcher.servlet.rank.PlayerRank;
import com.teeqee.spring.dispatcher.servlet.update.PlayerUpdate;
import com.teeqee.spring.result.Result;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;


@Component
public class MethodMapper implements CommandLineRunner{
    /**login push*/
    @Resource
    private PlayerLogin playerLogin;
    /**update*/
    @Resource
    private PlayerUpdate playerUpdate;
    /**rank*/
    @Resource
    private PlayerRank playerRank;
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
        map.put(DispatcherCmd.LOGIN,map-> playerLogin.login(map));
        /**获取宠物的位置信息*/
        map.put(DispatcherCmd.GET_SITE,map-> playerLogin.getsite(map));
        /**拉取玩家动物信息*/
        map.put(DispatcherCmd.GET_ANIMAL,map-> playerLogin.getanimal(map));
        /**拉取任务信息*/
        map.put(DispatcherCmd.GET_TASK,map-> playerLogin.gettask(map));
        /**修改头像昵称语言 返回空信息*/
        map.put(DispatcherCmd.USER_INFOR,map-> playerLogin.userinfor(map));
        /**从后端中拉取缓存*/
        map.put(DispatcherCmd.GET_CACHE,map-> playerLogin.getcache(map));
        /**很长很大的心跳包*/
        map.put(DispatcherCmd.NEW_HEART,map->playerUpdate.newheart(map));
        /**拉取建筑*/
        map.put(DispatcherCmd.GET_BUILDING,map-> playerLogin.getbuilding(map));
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
        /**玩家分享得打榜次数*/
        map.put(DispatcherCmd.SHARE_FOR_CHALLENGE,map->playerUpdate.shareforchallenge(map));
        /**玩家领取vip*/
        map.put(DispatcherCmd.REWARD_VIP,map->playerUpdate.rewardvip(map));
        /**未命中排行榜*/
        map.put(DispatcherCmd.TOP_LISTMISSNUM,map->playerRank.toplistmissnum(map));
        /**世界排行榜*/
        map.put(DispatcherCmd.TOP_LIST,map->playerRank.toplist(map));
        /**玩家观看视频增加vip等级*/
        map.put(DispatcherCmd.VIDEO_FOR_VIP,map->playerUpdate.videoforvip(map));
        /**玩家视频增加飞镖数*/
        map.put(DispatcherCmd.VIDEO_FOR_DARTNUM,map->playerUpdate.videofordartnum(map));
        /**飞镖没射中, 发给后端纪录次数*/
        map.put(DispatcherCmd.ADD_MISS_NUM,map->playerUpdate.addmissnum(map));
    }
}
