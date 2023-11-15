package com.feicui365.live.model.entity;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.Serializable;

public class Guardian implements Serializable , MultiItemEntity {
    String anchorid;
    String uid;
    String intimacy;
    String intimacy_week;
    UserRegist user;

    public Guardian() {
    }

    public String getAnchorid() {
        return anchorid;
    }

    public void setAnchorid(String anchorid) {
        this.anchorid = anchorid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getIntimacy() {
        return intimacy;
    }

    public void setIntimacy(String intimacy) {
        this.intimacy = intimacy;
    }

    public String getIntimacy_week() {
        return intimacy_week;
    }

    public void setIntimacy_week(String intimacy_week) {
        this.intimacy_week = intimacy_week;
    }

    public UserRegist getUser() {
        return user;
    }

    public void setUser(UserRegist user) {
        this.user = user;
    }

    @Override
    public int getItemType() {

        return 2;
    }
}
