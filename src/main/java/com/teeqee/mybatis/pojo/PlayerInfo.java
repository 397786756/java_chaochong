package com.teeqee.mybatis.pojo;

public class PlayerInfo {
    private Integer uid;

    private String openid;

    private Integer avatar;

    private Integer nickname;

    private Integer invitevip;

    private Integer gender;

    private String city;

    private String country;

    private String province;

    private String language;

    private Integer channelid;

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid == null ? null : openid.trim();
    }

    public Integer getAvatar() {
        return avatar;
    }

    public void setAvatar(Integer avatar) {
        this.avatar = avatar;
    }

    public Integer getNickname() {
        return nickname;
    }

    public void setNickname(Integer nickname) {
        this.nickname = nickname;
    }

    public Integer getInvitevip() {
        return invitevip;
    }

    public void setInvitevip(Integer invitevip) {
        this.invitevip = invitevip;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country == null ? null : country.trim();
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language == null ? null : language.trim();
    }

    public Integer getChannelid() {
        return channelid;
    }

    public void setChannelid(Integer channelid) {
        this.channelid = channelid;
    }
}