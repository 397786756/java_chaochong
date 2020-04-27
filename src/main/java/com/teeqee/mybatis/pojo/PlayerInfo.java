package com.teeqee.mybatis.pojo;


import lombok.Data;

@Data
public class PlayerInfo {
    private Integer uid;

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


    public PlayerInfo() {
    }
}