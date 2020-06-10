package com.teeqee.mybatis.pojo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.teeqee.spring.dispatcher.cmd.PlayerCmd;
import com.teeqee.spring.dispatcher.cmd.StaticData;
import com.teeqee.spring.dispatcher.servlet.entity.*;
import com.teeqee.spring.result.SpecialResult;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.noark.core.util.DateUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


@Data
public class PlayerData {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    /**玩家的uid*/
    private Long uid;
    /**玩家的openid*/
    private String openid;
    /**孵化器最大库存*/
    private Integer stockmax;
    /**孵化器剩余库存*/
    private Integer stock;
    /**金币数量*/
    private Integer gold;
    /**钻石数量*/
    private Integer diamond;
    /**新手引导步数 替代step*/
    private Double newgamer;
    /**是否开启声音    1-->开启    0-->不开启*/
    private Integer sound;
    /**最高级动物的等级*/
    private Integer biggestanimalid;
    /**vip的等级*/
    private Integer viplv;
    /**今天有没有领取vip奖励*/
    private Integer vipreward;
    /**vip点数*/
    private Integer vip;
    /**玩家当前VIP邀请人数*/
    private Integer invitevip;
    /**今日是否签到*/
    private Boolean todaysign;
    /**玩家总共签到次数*/
    private Integer weeksign;
    /**关卡数*/
    private Integer rounds;
    /**世界打榜剩余挑战次数*/
    private Integer rankchallengenum;
    /**世界打榜换一批的次数*/
    private Integer refreshworldnum;
    /**玩家急速孵化总次数*/
    private Integer speedincubate;
    /**上周世界打榜的排名*/
    private Integer lastweekrank;
    /**领取打榜奖励的次数*/
    private Integer lastweekreward;
    /**玩家孵化的总次数*/
    private Integer totalincubate;
    /**玩家离线的时间戳(毫秒)*/
    private Date lasttime;
    /**公告参与次数*/
    private Integer rankpermission;
    /**是否显示过领取话费页面,0是未显示过领取话费的，1是显示过*/
    private Integer phonefare;
    /**玩家话费数量*/
    private Integer phonefarenumber;
    /**获取宠物的位置信息*/
    private String sitedata;
    /**玩家的动物信息*/
    private String animaldata;
    /**玩家的任务信息*/
    private String taskdata;
    /**当前玩家现有的飞镖数量*/
    private Integer dartnum=5;
    /**建筑data*/
    private String buildingdata;
    /**miss次数*/
    private Integer missnum;
    /**头像*/
    private String avatar="";
    /**昵称*/
    private String nickname="未授权的玩家";
    /**活跃度(周活跃和日活跃)*/
    private String activedata;
    /**是否为机器人*/
    private Boolean isrobot;
    /**纪录转盘邀请次数*/
    private Integer turntableinvitenum;

    private PlayerData() {

    }
    /**是否为机器人*/
    public boolean isrobot(){
        if (isrobot==null){
            return false;
        }
        return isrobot;
    }

    /**开启游客模式*/
    public void isTourist(String openid){
        this.openid=openid;
    }
    /**
     * @param uid 构造函数
     */
    public PlayerData(Long uid) {
        this.uid=uid;
        this.stockmax=10;
        this.stock=10;
        this.gold=0;
        this.diamond=0;
        this.newgamer=0D;
        this.sound=1;
        this.biggestanimalid=1;
        this.viplv=0;
        this.todaysign=false;
        this.weeksign=0;
        this.rounds=1;
        this.vipreward=0;
        this.rankchallengenum=10;
        this.refreshworldnum=3;
        this.speedincubate=0;
        this.lastweekrank=1;
        this.lastweekreward=0;
        this.totalincubate=0;
        this.rankpermission=0;
        this.phonefare=0;
        this.phonefarenumber=0;
        this.missnum=0;
        this.lasttime=new Date();
        turntableinvitenum=0;
    }

    /**
     * @return 返回用户登录需要的数据
     */
    public JSONObject loginPush(){
        //登录的时候进行检查
        loginInit();
        JSONObject jsonObject = new JSONObject();
        JSONObject data = new JSONObject();
        data.put("rankpermission", rankpermission);
        data.put("weeksign", weeksign);
        data.put("todaysign", todaysign);
        data.put("sound", sound);
        data.put("stockmax", stockmax);
        data.put("gold", gold);
        data.put("lastweekreward", lastweekreward);
        data.put("speedincubate", speedincubate);
        data.put("viplv", viplv);
        data.put("nickname", nickname);
        data.put("totalincubate", totalincubate);
        data.put("stock", stock);
        data.put("newgamer", newgamer);
        data.put("vipreward", vipreward);
        data.put("openid", openid);
        data.put("biggestanimalid", biggestanimalid);
        data.put("avatar", avatar);
        data.put("phonefare", phonefare);
        data.put("diamond", diamond);
        data.put("lasttime", lasttime);
        data.put("rankchallengenum", rankchallengenum);
        data.put("phonefarenumber", phonefarenumber);
        data.put("lastweekrank", lastweekrank);
        data.put("refreshworldnum", refreshworldnum);
        data.put("rounds", rounds);
        data.put("turntableinvitenum", turntableinvitenum);
        jsonObject.put(PlayerCmd.PLAYER_DATA, data);
        return jsonObject;
    }

    /**看我像极速孵化吗*/
    public boolean speedincubate(){
        if (speedincubate==null){
            speedincubate=1;
        }else {
            speedincubate+=1;
        }
        return true;
    }
    /**日更新数据*/
    public JSONObject init(){
        JSONObject jsonObject = new JSONObject();
        lasttime=new Date();
        logger.info("uid init everyDay:{}",uid);
        //玩家急速孵化总次数
        speedincubate=0;
        //今日是否签到
        todaysign=false;
        if (weeksign==null||weeksign==7){
            weeksign=0;
        }
        //纪录转盘邀请次数
        turntableinvitenum=0;
        //初始化玩家的任务信息
        JSONArray value = initTaskData();

        jsonObject.put("todaysign", false);
        jsonObject.put("turntableinvitenum", 0);
        jsonObject.put("weeksign", weeksign);

        jsonObject.put("taskdata", value);

        return jsonObject;

    }

    private void loginInit(){
        if (lasttime==null||(isYesterday(lasttime,new Date()))){
            init();
        }
    }

    /**初始化taskData*/
    private  JSONArray initTaskData(){
       if (taskdata!=null){
           JSONArray jsonArray = new JSONArray();
           JSONArray parseArray = JSONArray.parseArray(taskdata);
           //转成服务器需要的
           JSONArray parse = new JSONArray();
           int size = parseArray.size();
           for (int i = 0; i < size; i++) {
               JSONObject object = parseArray.getJSONObject(i);
               int d=0;
               int n=0;
               Integer t = object.getInteger("t");
               Integer nr = object.getInteger("nr");
               if (i==0){
                   n=1;
                   d=1;
               }
               Taskdata taskdata = new Taskdata(t, n, d, nr);
               JSONObject json = taskdata.initJson();
               parse.add(json);

               jsonArray.add(taskdata);
           }
           taskdata=parse.toJSONString();
           return jsonArray;
       }
       return null;
    }

    /**世界打榜次数会减一*/
    public JSONObject worldrankstart() {
         if (rankchallengenum<0){
             rankchallengenum=0;
         }else if (rankchallengenum>0){
             rankchallengenum--;
         }
         return goldAndDiamondInfo();
    }
    /**
     * @param oldTime 上次登录的时间
     * @param newTime 现在的时间
     * @return 判断上次登录时间是否为昨天
     */
    private boolean isYesterday(Date oldTime,Date newTime)  {
        Calendar oldCal = Calendar.getInstance();
        Calendar newCal = Calendar.getInstance();
        oldCal.setTime(oldTime);
        newCal.setTime(newTime);
        return Math.abs(newCal.get(Calendar.DAY_OF_YEAR) - oldCal.get(Calendar.DAY_OF_YEAR)) == 1;
    }


    /**
     * @return 拉取建筑
     */
    public JSONObject getbuilding(){
        if (buildingdata==null){
            this.buildingdata=StaticData.BUILDING_DATA;
        }
        JSONArray jsonArray = JSONArray.parseArray(buildingdata);
        JSONArray returnArray = new JSONArray();
        for (Object o : jsonArray) {
            JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(o));
            Integer lv = jsonObject.getInteger("lv");
            Integer id = jsonObject.getInteger("id");
            BuildingData buildingData = new BuildingData(id, lv);
            Integer ss = jsonObject.getInteger("ss");
            if (ss!=null){
                buildingData.setSs(ss);
            }
            returnArray.add(JSON.toJSON(buildingData));
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(PlayerCmd.BUILDING_DATA, returnArray);
        return jsonObject;
    }

    /**
     * @return 从后端中获取缓存
     */
    public JSONObject getCache(){
        return new JSONObject();
    }
    /**
     * 获取宠物的位置信息(完成)
     */
    public JSONObject getsite(){
        //需要进行转义
            if (sitedata==null){
               this.sitedata=StaticData.SITEDATA;
            }
        JSONArray jsonArray = JSONArray.parseArray(sitedata);
        JSONArray returnArray = new JSONArray();
        for (Object o : jsonArray) {
            JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(o));
            Integer s = jsonObject.getInteger("s");
            Integer a = jsonObject.getInteger("a");
            Site site = new Site(s,a);
            returnArray.add(JSON.toJSON(site));
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(PlayerCmd.SITE_DATA, returnArray);
        return jsonObject;
    }


    /**
     * 拉取玩家动物信息
     */
    public JSONObject getanimal(){
        //需要进行转义
        if (animaldata==null||"".equals(animaldata)){
            this.animaldata=StaticData.ANIMAL_DATA;
        }
        JSONArray animaldataList = JSONArray.parseArray(animaldata);
        JSONArray jsonArray = new JSONArray(animaldataList.size());
        animaldataList.forEach(animal->{
            JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(animal));
            Integer a = jsonObject.getInteger("a");
            Integer l = jsonObject.getInteger("l");
            Animaldata animaldata = new Animaldata(a, l);
            JSONObject object = JSONObject.parseObject(JSON.toJSONString(animaldata));
            jsonArray.add(object);
        });
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(PlayerCmd.ANIMAL_DATA, jsonArray);
        return jsonObject;
    }

    /**
     * 拉取玩家动物信息
     */
    public JSONObject gettask(){
        //需要进行转义
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        if (taskdata==null) {
            List<Taskdata> list = JSONArray.parseArray(StaticData.TASK_DATA, Taskdata.class);
            jsonArray.addAll(list);
            //客户端的数据
            JSONArray taskDataArray = new JSONArray();
            for (Taskdata task : list) {
                JSONObject json = task.initJson();
                taskDataArray.add(json);
            }
            taskdata=taskDataArray.toJSONString();
        }else {
            JSONArray parseArray = JSONArray.parseArray(taskdata);
            int size = parseArray.size();
            for (int i = 0; i < size; i++) {
                JSONObject object = parseArray.getJSONObject(i);
                Integer d = object.getInteger("d");
                Integer n = object.getInteger("n");
                Integer t = object.getInteger("t");
                Integer nr = object.getInteger("nr");
                Taskdata taskdata = new Taskdata(t, n, d, nr);
                jsonArray.add(taskdata);
            }
        }
        jsonObject.put(PlayerCmd.TASK_DATA, jsonArray);
        return jsonObject;
    }

    public static void main(String[] args) {

        StaticData staticData = new StaticData();
        staticData.initTaskData();
        PlayerData playerData = new PlayerData();
        System.out.println(playerData.gettask());
        playerData.gettask();
        System.out.println(playerData.gettask());
        playerData.gettask();
        System.out.println(playerData.gettask());
        playerData.init();
        System.out.println(playerData.gettask());
    }


    /**
     * 我觉得我是个心跳包
     */
    public JSONObject newheart(JSONObject data){
        if (data!=null&&data.size()>0){
            //宠物的位置
            JSONArray sitedata = data.getJSONArray("sitedata");
            if (sitedata!=null){
                updateSitedata(sitedata);
            }
            //任务
            JSONArray taskdata = data.getJSONArray("taskdata");
            if (taskdata!=null){
                updateTaskdata(taskdata);
            }
            //看样子像建筑
            JSONArray buildingdata = data.getJSONArray("buildingdata");
            if (buildingdata!=null){
                updateBuildingdata(buildingdata);
            }
             //动物的位置
            JSONArray animaldata = data.getJSONArray("animaldata");
            if (animaldata!=null){
                updateAnimaldata(animaldata);
            }
            Integer stock = data.getInteger("stock");
            if (stock!=null){
                this.stock=stock;
            }
            Integer stockmax = data.getInteger("stockmax");
            if (stockmax!=null){
                this.stockmax=stockmax;
            }
            Integer dartnum = data.getInteger("dartnum");
            if (dartnum!=null){
                this.dartnum=dartnum;
            }
            Integer gold = data.getInteger("gold");
            if (gold!=null){
                this.gold=gold;
            }
            Integer diamond = data.getInteger("diamond");
            if (diamond!=null){
                this.diamond=diamond;
            }
            Integer totalincubate = data.getInteger("totalincubate");
            if (totalincubate!=null){
                this.totalincubate=totalincubate;
            }
        }
        return new JSONObject();
    }

    /**修改建筑data*/
    private void updateBuildingdata(JSONArray buildingdata) {
        if (buildingdata != null&&buildingdata.size()>0){
            int size = buildingdata.size();
            JSONArray jsonArray = new JSONArray(size);
            for (int i = 0; i <size; i++) {
                JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(buildingdata.get(i)));
                JSONObject data = new JSONObject();
                Integer lv = jsonObject.getInteger("buildinglv");
                data.put("lv", lv);
                Integer id = jsonObject.getInteger("buildingid");
                data.put("id", id);
                if (i==size-1){
                    Integer ss = jsonObject.getInteger("successodds");
                    data.put("ss", ss);
                }
               jsonArray.add(data);
            }
            this.buildingdata=jsonArray.toJSONString();
        }
    }


    /**
     * @param animaldata 修改玩家的animaldata
     */
    private void updateAnimaldata(JSONArray animaldata){
        List<Animaldata> animaldataList = animaldata.toJavaList(Animaldata.class);
        JSONArray jsonArray = new JSONArray(animaldata.size());
        for (Animaldata animal : animaldataList) {
            JSONObject jsonObject = new JSONObject();
            Integer a = animal.getA();
            Integer l = animal.getL();
            jsonObject.put("a", a);
            jsonObject.put("l", l);
            jsonArray.add(jsonObject);
        }
        this.animaldata=jsonArray.toJSONString();
    }


    /**
     * @param taskdata 任务数据
     */
    private  void updateTaskdata(JSONArray taskdata){
        List<Taskdata> taskDataList = taskdata.toJavaList(Taskdata.class);
        if (taskDataList!=null&&taskDataList.size()>0){
            JSONArray jsonArray = new JSONArray(taskDataList.size());
            for (Taskdata task : taskDataList) {
                Integer d = task.getD();
                Integer n = task.getN();
                Integer t = task.getT();
                Integer nr = task.getNr();
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("d",d );
                jsonObject.put("n",n );
                jsonObject.put("t",t );
                jsonObject.put("nr",nr );
                jsonArray.add(jsonObject);
            }
            this.taskdata=jsonArray.toJSONString();
        }
    }
    /**
     * @param sitedata 宠物的位置
     */
    private  void updateSitedata(JSONArray sitedata){
        List<Site> sites = sitedata.toJavaList(Site.class);
        if (sites!=null&&sites.size()>0){
            JSONArray jsonArray = new JSONArray(sites.size());
            for (Site site : sites) {
                JSONObject jsonMap = new JSONObject();
                int a = site.getA();
                int s = site.getS();
                jsonMap.put("a", a);
                jsonMap.put("s", s);
                jsonArray.add(jsonMap);
            }
            //转成json对象
           this.sitedata=jsonArray.toJSONString();
        }
    }


    /**获取幸运转盘*/
    public JSONObject getdartboard(){
        String jsonKey="dartboard";
        JSONObject jsonObject = new JSONObject();
        JSONObject dartnumJson = new JSONObject();
        dartnumJson.put("dartnum",dartnum);
        jsonObject.put(jsonKey, dartnumJson);
        return jsonObject;
    }

    /**更新新手引导*/
    public Boolean  endofguide(Double step){
        if (step>0){
            if (step>this.newgamer){
                this.newgamer=step;
            }
            return true;
        }else {
            return false;
        }
    }
    /**玩家视频增加飞镖数 (玩家看视频, 不管剩余几个飞镖, 飞镖个数直接变为5)*/
    public Integer videofordartnum(){
           dartnum=5;
           return dartnum;
    }
    /**飞镖没射中, 发给后端纪录次数*/
    public Integer addmissnum(){
        if (missnum==null){
            missnum=1;
        }else {
            missnum+=1;
        }
        return SpecialResult.NULL_INTEGER;
    }
    /**使用飞镖*/
    public Boolean useDart(){
        if (dartnum==null){
            dartnum=0;
        }else if (dartnum>0){
            dartnum--;
            return true;
        }
        return false;
    }
    /**开启声音*/
    public Boolean opensound(){
        this.sound=1;
        return true;
    }

    /**关闭声音*/
    public Boolean closesound(){
        this.sound=0;
        return SpecialResult.NULL_BOOLEAN;
    }

    /**玩家获取活跃度相关*/
    public JSONObject getactive(){
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray;
        if (activedata==null){
            //初始化一下小老弟
            jsonArray= initActive();
            activedata=jsonArray.toJSONString();
        }
        jsonArray= retrunActive();
        jsonObject.put("activedata",jsonArray);
        return jsonObject;
    }

    /**初始化活动*/
    private JSONArray retrunActive(){
        JSONArray jsonArray = null;
        try {
            jsonArray = JSONArray.parseArray(activedata);
        } catch (Exception e) {
            jsonArray= initActive();
            e.printStackTrace();
        }
        return jsonArray;
    }

    /**初始化活动*/
    private JSONArray initActive(){
        JSONArray jsonArray = new JSONArray();
        //有两种类型
        int kind=2;
        for (int i = 0; i < kind; i++) {
            Active active = new Active();
            active.init(i+1);
            jsonArray.add(active);
        }
        return jsonArray;
    }

    /**玩家获取自己vip相关信息*/
    public  JSONObject getvipInfo(){
        JSONObject jsonObject = new JSONObject(4);
        //玩家当前VIP等级
        jsonObject.put("viplv", viplv);
        //玩家当前VIP点数
        jsonObject.put("vip", vip);
        //玩家当前VIP邀请人数
        jsonObject.put("invitevip", invitevip);
        //玩家今日是否领取vip奖励
        jsonObject.put("vipreward", vipreward);
        return jsonObject;
    }


    /**返回用户的金币和钻石*/
    private JSONObject goldAndDiamondInfo(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("gold", gold);
        jsonObject.put("diamond", diamond);
        JSONObject json = new JSONObject();
        json.put("golddata", jsonObject);
        return json;
    }


    /**通关统计(爬塔)*/
    public JSONObject rounds(JSONObject data){
        if (data!=null&&data.size()>0){
            //是否顺利通关 0代表未通关 1代表通关
            Integer success = data.getInteger("success");
            //获取的金币数量
            Integer gold = data.getInteger("gold");
            if (success!=null&&success>0){
                this.rounds+=1;
            }
            if (gold!=null&&gold>0){
                this.gold+=gold;
            }
            //返回金币和钻石
          return goldAndDiamondInfo();
        }
        return SpecialResult.NULL_JSON_OBJECT;
    }

    /**签到*/
    public Boolean sign(Integer type){
        //如果今天没有签到
        if (type!=null&&!todaysign){
            this.todaysign=true;
            this.weeksign+=1;
        }
        return SpecialResult.NULL_BOOLEAN;
    }

    /**增加话费 话费是double*/
    public Boolean updatephonefarenumber(Double addphonefarenumber){
        if (addphonefarenumber!=null&&addphonefarenumber>0){
            this.phonefarenumber+=addphonefarenumber.intValue();
        }
        return SpecialResult.NULL_BOOLEAN;
    }

    /**更新玩家显示领取话费*/
    public Integer updatephonefare(){
        return SpecialResult.NULL_INTEGER;
    }

    /**更新玩家显示领取话费*/
    public JSONObject shareforchallenge(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("rankchallengenum", rankchallengenum);
        return jsonObject;
    }

    /**玩家领取vip*/
    public Boolean rewardvip() {
        if (vipreward==0){
            vipreward=1;
        }
        return SpecialResult.NULL_BOOLEAN;
    }

    /**玩家观看视频增加vip等级*/
    public JSONObject videoforvip() {
        JSONObject jsonObject = new JSONObject(4);
        //玩家当前VIP等级
        jsonObject.put("viplv", viplv);
        //玩家当前VIP点数
        jsonObject.put("vip", vip);
        //玩家当前VIP邀请人数
        jsonObject.put("invitevip", invitevip);
        //玩家今日是否领取vip奖励
        jsonObject.put("vipreward", vipreward);
        return jsonObject;
    }

    /**修改用户活跃度*/
    public Boolean updateactive(JSONObject data) {
        logger.info("updateactive client:{}",data.toJSONString());
        JSONArray datalist = data.getJSONArray("datalist");
        if (datalist!=null&&datalist.size()>0){
            this.activedata=datalist.toJSONString();
            return true;
        }else {
           return false;
        }
    }

    /**纪录转盘邀请次数*/
    public Boolean addZhuanPan() {
        if (turntableinvitenum==null){
            turntableinvitenum=1;
        }
        if (turntableinvitenum>=10){
            turntableinvitenum=10;
        }else {
            turntableinvitenum+=1;
        }
        return true;
    }
}
