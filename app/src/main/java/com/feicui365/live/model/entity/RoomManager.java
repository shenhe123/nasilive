package com.feicui365.live.model.entity;

public class RoomManager {
    String anchorid;
    String create_time;
    String id;
    String mgrid;
    UserRegist user;

    public RoomManager() {
    }

    public String getAnchorid() {
        return anchorid;
    }

    public void setAnchorid(String anchorid) {
        this.anchorid = anchorid;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMgrid() {
        return mgrid;
    }

    public void setMgrid(String mgrid) {
        this.mgrid = mgrid;
    }

    public UserRegist getUser() {
        return user;
    }

    public void setUser(UserRegist user) {
        this.user = user;
    }
}
