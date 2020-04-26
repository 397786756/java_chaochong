package com.teeqee.spring.dispatcher.method;

import com.alibaba.fastjson.JSONObject;
import com.teeqee.spring.dispatcher.cmd.DispatcherCmd;
import com.teeqee.spring.dispatcher.impl.Login;
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


    private ConcurrentHashMap<String, Function<JSONObject, Result>> map=new ConcurrentHashMap<>(1024);



    public Result run(JSONObject jsonObject) {
        String cmd = jsonObject.getString("cmd");
        JSONObject data = jsonObject.getJSONObject("data");
        Function<JSONObject, Result> function = map.get(cmd);
        if (function!=null){
            return function.apply(jsonObject);
        }
        return new Result("error",cmd+"-> cmd undefined");
    }

    @Override
    public void run(String... args) {
        map.put(DispatcherCmd.LOGIN,map->login.login(map));
    }
}
