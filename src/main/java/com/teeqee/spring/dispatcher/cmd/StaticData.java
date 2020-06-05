package com.teeqee.spring.dispatcher.cmd;


import com.alibaba.fastjson.JSONArray;
import com.teeqee.spring.dispatcher.servlet.entity.Taskdata;
import org.checkerframework.checker.units.qual.C;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author :zhengsongjie
 * @Description: 静态配置类
 * @Software: IntelliJ IDEA
 */
@Component
public class StaticData {
    /**
     * 获取宠物的位置信息
     */
    public static final String SITEDATA = "[{\"s\":0,\"a\":1},{\"s\":1,\"a\":1},{\"s\":2,\"a\":0},{\"s\":3,\"a\":0},{\"s\":4,\"a\":0},{\"s\":5,\"a\":0},{\"s\":6,\"a\":0},{\"s\":7,\"a\":0},{\"s\":8,\"a\":0},{\"s\":9,\"a\":0},{\"s\":10,\"a\":-1},{\"s\":11,\"a\":-1},{\"s\":12,\"a\":-1},{\"s\":13,\"a\":-1},{\"s\":14,\"a\":-1},{\"s\":15,\"a\":-1},{\"s\":16,\"a\":-1},{\"s\":17,\"a\":-1},{\"s\":18,\"a\":-1},{\"s\":19,\"a\":-1}]";


    /**
     * 拉取玩家动物信息 初始化
     */
    public static  String ANIMAL_DATA = "[{\"a\":1,\"l\":1}]";


    /**
     * 拉取任务
     */
    public static  String TASK_DATA = "";
    /**
     * 拉取建筑
     */
    public static final String BUILDING_DATA = "[{\"lv\":0,\"id\":0},{\"lv\":0,\"id\":1},{\"lv\":0,\"id\":2},{\"lv\":0,\"id\":3},{\"lv\":0,\"id\":4},{\"lv\":0,\"id\":5},{\"ss\":0,\"lv\":0,\"id\":6}]";



    @PostConstruct
    private static void initTaskData() {
        List<Integer> list = new ArrayList<>(Arrays.asList(1, 50, 50, 1, 3, 20, 2, 5, 1, 5));
        List<Taskdata> taskdataList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            Integer neednumber = list.get(i);
            Taskdata taskdata = new Taskdata(i,0,i+1,neednumber);
            taskdataList.add(taskdata);
        }
        TASK_DATA= JSONArray.parseArray(JSONArray.toJSONString(taskdataList)).toJSONString();
    }
}
