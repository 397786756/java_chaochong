package com.teeqee.mybatis.pojo;


import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.util.Date;

@Data
public class PlayerInfo {
    private Long uid;

    private String openid;

    private String avatar;

    private String nickname;

    private Integer invitevip;

    private Integer gender;

    private String city;

    private String country;

    private String province;

    private String language;

    private Integer channelid;

    private Date createtime;

    public PlayerInfo(String openid,Long uid) {
        this.openid = openid;
        this.uid=uid;
    }

    public PlayerInfo() {
    }

    /**
     * @param data 数据源
     * @return 返回
     */
// "data":{
//     "nickname"://玩家昵称
//     "avatar"://头像地址
//     "gender"://性别
//     "city"://城市
//     "province"://省份
//     "country"://国家
//     "language"://语言
// }"
    public boolean updateUserInfo(JSONObject data) {
        if (data!=null&&data.size()>0){
            String avatar = data.getString("avatar");
            String nickname = data.getString("nickname");
            Integer gender = data.getInteger("gender");
            String city = data.getString("city");
            String province = data.getString("province");
            String country = data.getString("country");
            String language = data.getString("language");
            if (avatar!=null){
                this.avatar=avatar;
            }
            if (nickname!=null){
                this.nickname=nickname;
            }
            if (gender!=null){
                this.gender=gender;
            }
            if (city!=null){
                this.city=city;
            }
            if (province!=null){
                this.province=province;
            }
            if (country!=null){
                this.country=country;
            }
            if (language!=null){
                this.language=language;
            }
            return true;
        }
        return false;
    }

    /**获取昵称*/
    public String getMyNickName(){
        return nickname==null?"未授权玩家":nickname;
    }
    /**获取头像url*/
    public String getMyAvatar(){
        return avatar==null?"":avatar;
    }
}