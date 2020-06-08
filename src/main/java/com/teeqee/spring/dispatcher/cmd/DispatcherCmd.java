package com.teeqee.spring.dispatcher.cmd;

/**
 * @Description:
 * @author zhengsongjie
 * @Software: IntelliJ IDEA
 */
public class DispatcherCmd {
    /**登录*/
    public static final String  LOGIN = "login";
    /**获取宠物的位置信息*/
    public static final String  GET_SITE = "getsite";
    /**拉取玩家动物信息*/
    public static final String  GET_ANIMAL="getanimal";
    /**拉取任务*/
    public static final String  GET_TASK="gettask";
    /**更新用户信息*/
    public static final String USER_INFOR= "userinfor";
    /**从后端中拉取缓存*/
    public static final String GET_CACHE= "getcache";
    /**从后端中拉取缓存*/
    public static final String NEW_HEART= "newheart";
    /**拉取建筑*/
    public static final String GET_BUILDING= "getbuilding";
    /**拉取幸运转盘*/
    public static final String GET_DARTBOARD= "getdartboard";
    /**更新新手引导步数*/
    public static final String ENDOFGUIDE="endofguide";
    /**开启声音*/
    public static final String OPEN_SOUND="opensound";
    /**关闭声音*/
    public static final String CLOSE_SOUND="closesound";
    /**玩家获取活跃度相关*/
    public static final String GET_ACTIVE="getactive";
    /**玩家修改活跃度*/
    public static final String UPDATE_ACTIVE="updateactive";
    /**玩家获取自己vip相关信息*/
    public static final String GET_VIP="getvip";
    /**签到*/
    public static final String SIGN="sign";
    /**通关统计*/
    public static final String ROUNDS="rounds";
    /**更新玩家话费数量*/
    public static final String UPDATE_PHONEFARENUMBER="updatephonefarenumber";
    /**更新玩家显示领取话费*/
    public static final String UPDATE_PHONEFARE="updatephonefare";
    /**玩家分享得打榜次数*/
    public static final String SHARE_FOR_CHALLENGE = "shareforchallenge";
    /**玩家未射中排行榜*/
    public static final String TOP_LISTMISSNUM = "toplistmissnum";
    /**玩家领取vip*/
    public static final String REWARD_VIP = "rewardvip";
    /**玩家观看视频增加vip等级*/
    public static final String VIDEO_FOR_VIP = "videoforvip";
    /**世界排行榜*/
    public static final String TOP_LIST = "toplist";
    /**玩家视频增加飞镖数 (玩家看视频, 不管剩余几个飞镖, 飞镖个数直接变为5)*/
    public static final String VIDEO_FOR_DARTNUM = "videofordartnum";
    /**飞镖没射中, 发给后端纪录次数*/
    public static final String ADD_MISS_NUM = "addmissnum";
    /**使用飞镖*/
    public static final String USE_DART = "useDart";
    /**获取打榜对战列表*/
    public static final String GET_WORLD_RANK = "getworldrank";
    /**刷新排行榜*/
    public static final String REFRESH_WORLD_RANK = "refreshworldrank";
    /**打榜结算gameover*/
    public static final String WORLD_RANK_END = "worldrankend";
    /**获取打榜战报*/
    public static final String GET_RANK_REPORT = "getrankreport";
    /**打榜开始 减次数减钻石*/
    public static final String WORLD_RANK_START = "worldrankstart";
    /**纪录转盘邀请次数*/
    public static final String ADD_ZHUANPAN = "addZhuanPan";
    /**玩家的每日极速孵化次数*/
    public static final String SPEEDIN_CUBATE = "speedincubate";
}
