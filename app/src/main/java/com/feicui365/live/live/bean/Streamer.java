package com.feicui365.live.live.bean;



import com.feicui365.live.model.entity.GiftInfo;
import com.feicui365.live.model.entity.UserRegist;

import java.io.Serializable;


/**
 *横幅
 */
public class Streamer implements Serializable {
    int type;
    VipInfo vip;
    UserRegist user;
    UserRegist anchor;
    String roomId;
    GiftInfo gift;

    public Streamer() {
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public VipInfo getVip() {
        return vip;
    }

    public void setVip(VipInfo vip) {
        this.vip = vip;
    }

    public UserRegist getUser() {
        return user;
    }

    public void setUser(UserRegist user) {
        this.user = user;
    }

    public UserRegist getAnchor() {
        return anchor;
    }

    public void setAnchor(UserRegist anchor) {
        this.anchor = anchor;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public GiftInfo getGift() {
        return gift;
    }

    public void setGift(GiftInfo gift) {
        this.gift = gift;
    }
}
