package com.teeqee.spring.dispatcher.cmd;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.teeqee.spring.dispatcher.servlet.login.entity.Animaldata;
import com.teeqee.spring.dispatcher.servlet.login.entity.Taskdata;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 静态配置类
 * @author :zhengsongjie
 * @Software: IntelliJ IDEA
 */
public class StaticData {
    /**
     *获取宠物的位置信息
     */
    public static final String SITEDATA="[{\"s\":0,\"a\":1},{\"s\":1,\"a\":1},{\"s\":2,\"a\":0},{\"s\":3,\"a\":0},{\"s\":4,\"a\":0},{\"s\":5,\"a\":0},{\"s\":6,\"a\":0},{\"s\":7,\"a\":0},{\"s\":8,\"a\":0},{\"s\":9,\"a\":0},{\"s\":10,\"a\":-1},{\"s\":11,\"a\":-1},{\"s\":12,\"a\":-1},{\"s\":13,\"a\":-1},{\"s\":14,\"a\":-1},{\"s\":15,\"a\":-1},{\"s\":16,\"a\":-1},{\"s\":17,\"a\":-1},{\"s\":18,\"a\":-1},{\"s\":19,\"a\":-1}]";


    /**
     *拉取玩家动物信息 初始化
     */
    public static final String ANIMAL_DATA= "[{\"animalid\":1,\"lv\":1}]";



    /**
     *拉取任务
     */
    public static final String TASK_DATA="[{\"d\":0,\"t\":1,\"n\":0},{\"d\":0,\"t\":2,\"n\":0},{\"d\":0,\"t\":3,\"n\":0},{\"d\":0,\"t\":4,\"n\":0},{\"d\":0,\"t\":5,\"n\":0},{\"d\":0,\"t\":6,\"n\":0},{\"d\":0,\"t\":7,\"n\":0},{\"d\":0,\"t\":8,\"n\":0}]";

    public static void main(String[] args) {
        List<Object> list = new ArrayList<>();
        for (int i = 1; i <= 8; i++) {
            Taskdata taskdata = new Taskdata(i, 0, 0);
            list.add(taskdata);
        }
        System.out.println(list.toString());
    }
}
