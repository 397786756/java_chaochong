package com.teeqee.spring.dispatcher.cmd;

/**
 * @Description: 用户的自定义的组件(就是返回数据源的时候的JSONArray的key)
 * @author : zhengsongjie
 * @Software: IntelliJ IDEA
 */
public class PlayerCmd {
    /**获取用户数据*/
    public static final String PLAYER_DATA= "userdata";
    /**获取用户隐私信息*/
    public static final String PLAYER_INFO="playInfo";
    /**获取用户日志的记录*/
    public static final String PLAYER_LOG="playLog";
    /**获取自己的排行记录*/
    public static final String PLAYER_RANK = "playerRank";

    /**获取宠物的位置信息*/
    public static final String SITE_DATA= "sitedata";
    /**拉取玩家动物信息*/
    public static final String ANIMAL_DATA= "animaldata";
    /**拉取任务*/
    public static final String TASK_DATA= "taskdata";
   /**拉取建筑*/
   public static final String BUILDING_DATA="buildingdata";

}
