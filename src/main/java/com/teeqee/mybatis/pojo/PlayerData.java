package com.teeqee.mybatis.pojo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sun.org.apache.bcel.internal.generic.NEW;
import com.teeqee.spring.dispatcher.cmd.PlayerCmd;
import com.teeqee.spring.dispatcher.cmd.StaticData;

import com.teeqee.spring.dispatcher.servlet.entity.*;
import lombok.Data;

import java.util.Date;
import java.util.List;


@Data
public class PlayerData {
    /**玩家的uid*/
    private Integer uid;
    /**渠道的用户id*/
    private String openid;
    /**孵化器最大库存*/
    private Integer stockmax;
    /**孵化器剩余库存*/
    private Integer stock;
    /**金币数量*/
    private Integer gold;
    /**钻石数量*/
    private Integer diamond;
    /**新手引导步数*/
    private Integer newgamer;
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
    /**当前玩家玩转盘的次数*/
    private Integer dartnum;
    /**建筑data*/
    private String buildingdata;
    /**引导步数*/
    private Integer step=0;

    /**头像*/
    private String avatar="";
    /**昵称*/
    private String nickname="未授权的玩家";

    public PlayerData() {
    }

    /**
     * @param openid 构造函数
     */
    public PlayerData(String openid) {
        this.openid=openid;
        this.stockmax=10;
        this.stock=10;
        this.gold=0;
        this.diamond=0;
        this.newgamer=0;
        this.sound=1;
        this.biggestanimalid=1;
        this.viplv=0;
        this.todaysign=false;
        this.weeksign=0;
        this.rounds=0;
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
        this.lasttime=new Date();
    }


    /**
     * @return 返回用户登录需要的数据
     */
    public JSONObject loginPush(){
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
        jsonObject.put(PlayerCmd.PLAYER_DATA, data);
        return jsonObject;
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
        if (taskdata==null){
            this.taskdata=StaticData.TASK_DATA;
        }
        JSONArray jsonArray = JSONArray.parseArray(taskdata);
        JSONArray returnArray = new JSONArray();
        for (Object o : jsonArray) {
            JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(o));
            Integer d = jsonObject.getInteger("d");
            Integer t = jsonObject.getInteger("t");
            Integer n = jsonObject.getInteger("n");
            Taskdata taskdata = new Taskdata(t, n, d);
            returnArray.add(JSON.toJSON(taskdata));
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(PlayerCmd.TASK_DATA, returnArray);
        return jsonObject;
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
            data.clear();
            data.put("info", true);
            return data;
        }
        return new JSONObject();
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
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("d",d );
                jsonObject.put("n",n );
                jsonObject.put("t",t );
                jsonArray.add(jsonObject);
            }
            //转成json对象
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
        Dartboard dartboard = new Dartboard();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(jsonKey, dartboard);
        return jsonObject;
    }

    /**更新新手引导*/
    public Boolean endofguide(Integer step){
        if (step>0){
            if (step>this.step){
                this.step=step;
            }
        }
        return null;
    }



    /**开启声音*/
    public Boolean opensound(){
        this.sound=1;
        return null;
    }

    /**关闭声音*/
    public Boolean closesound(){
        this.sound=0;
        return null;
    }

    /**玩家获取活跃度相关*/
    public JSONObject getactive(){
            return null;
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
    public JSONObject goldAndDiamondInfo(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("gold", gold);
        jsonObject.put("diamond", diamond);
        JSONObject json = new JSONObject();
        json.put("golddata", jsonObject);
        return json;
    }


    /**通关统计(爬塔)*/
    public JSONObject rounds(JSONObject jsonObject){
        if (jsonObject!=null&&jsonObject.size()>0){
            //是否顺利通关 0代表未通关 1代表通关
            Integer success = jsonObject.getInteger("success");
            //获取的金币数量
            Integer gold = jsonObject.getInteger("gold");
            if (success==1){
                this.sound+=1;
            }
            if (gold!=null&&gold>0){
                this.gold+=gold;
            }
            //返回金币和钻石
          return goldAndDiamondInfo();
        }
        return null;
    }

    /**签到*/
    public Boolean sign(Integer type){
        //如果今天没有签到
        if (type!=null&&!todaysign){
            this.todaysign=true;
            this.weeksign+=1;
        }
        return null;
    }

    /**增加话费 话费是double*/
    public Boolean updatephonefarenumber(Double addphonefarenumber){
        if (addphonefarenumber!=null&&addphonefarenumber>0){
            this.phonefarenumber+=addphonefarenumber.intValue();
        }
        return null;
    }

    /**更新玩家显示领取话费*/
    public Integer updatephonefare(){
        return null;
    }

    /**更新玩家显示领取话费*/
    public JSONObject shareforchallenge(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("rankchallengenum", rankchallengenum);
        return jsonObject;
    }
}
