package com.feicui365.live.model.entity;

import java.io.Serializable;

public class LiveRoomDetailBean implements Serializable {
    private int id;
    private int sort;
    private String nick_name;
    private String avatar;
    private String city;
    private String area;
    private String totalTimes;
    private String watchTimes;
    private String liveTimes;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getTotalTimes() {
        return totalTimes;
    }

    public void setTotalTimes(String totalTimes) {
        this.totalTimes = totalTimes;
    }

    public String getWatchTimes() {
        return watchTimes;
    }

    public void setWatchTimes(String watchTimes) {
        this.watchTimes = watchTimes;
    }

    public String getLiveTimes() {
        return liveTimes;
    }

    public void setLiveTimes(String liveTimes) {
        this.liveTimes = liveTimes;
    }
}
