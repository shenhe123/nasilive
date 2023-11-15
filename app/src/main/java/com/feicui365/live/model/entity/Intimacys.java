package com.feicui365.live.model.entity;

import java.io.Serializable;

public class Intimacys implements Serializable {
    private String id;
    private String uid;
    private String anchorid;
    private String intimacy;
    private UserRegist user;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getAnchorid() {
        return anchorid;
    }

    public void setAnchorid(String anchorid) {
        this.anchorid = anchorid;
    }

    public String getIntimacy() {
        return intimacy;
    }

    public void setIntimacy(String intimacy) {
        this.intimacy = intimacy;
    }

    public UserRegist getUser() {
        return user;
    }

    public void setUser(UserRegist user) {
        this.user = user;
    }
}
