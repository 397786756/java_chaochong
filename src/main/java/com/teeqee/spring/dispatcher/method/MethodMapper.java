package com.teeqee.spring.dispatcher.method;

import com.alibaba.fastjson.JSONObject;
import com.teeqee.net.handler.AbstractSession;
import com.teeqee.spring.dispatcher.cmd.DispatcherCmd;
import com.teeqee.spring.dispatcher.model.MethodModel;
import com.teeqee.spring.dispatcher.servlet.login.Login;
import com.teeqee.spring.result.Result;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;


@Component
public class MethodMapper implements CommandLineRunner{



    @Resource
    private Login login;


    private ConcurrentHashMap<String, Function<MethodModel, Object>> map=new ConcurrentHashMap<>(1024);



    public Result run(MethodModel model) {
        String cmd = model.getCmd();
        Function<MethodModel, Object> function = map.get(cmd);
        if (function!=null){
            return new Result(cmd,function.apply(model));
        }
        return new Result("error","undefined "+cmd);
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
    }
}
