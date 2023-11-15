package com.feicui365.live.live.bean;


import com.feicui365.live.model.entity.GiftInfo;
import com.feicui365.live.model.entity.HotLive;
import com.feicui365.live.model.entity.Pkinfo;
import com.feicui365.live.model.entity.UserRegist;
import com.feicui365.live.shop.entity.Good;

import java.io.Serializable;


/**
 *通知BEAN
 */
public class Notify implements Serializable {
    String displayMessage;
    UserRegist fromUser;
    GiftInfo gift;
    Good goods;
    int isGuardian;
    String link_play_url;
    int memberCount;
    Pkinfo pkInfo;
    HotLive pkLive;
    String roomId;
    UserRegist toUser;
    String type;

    public Notify() {
    }

    public String getDisplayMessage() {
        return displayMessage;
    }

    public void setDisplayMessage(String displayMessage) {
        this.displayMessage = displayMessage;
    }

    public UserRegist getFromUser() {
        return fromUser;
    }

    public void setFromUser(UserRegist fromUser) {
        this.fromUser = fromUser;
    }

    public GiftInfo getGift() {
        return gift;
    }

    public void setGift(GiftInfo gift) {
        this.gift = gift;
    }

    public Good getGoods() {
        return goods;
    }

    public void setGoods(Good goods) {
        this.goods = goods;
    }

    public int getIsGuardian() {
        return isGuardian;
    }

    public void setIsGuardian(int isGuardian) {
        this.isGuardian = isGuardian;
    }

    public String getLink_play_url() {
        return link_play_url;
    }

    public void setLink_play_url(String link_play_url) {
        this.link_play_url = link_play_url;
    }

    public int getMemberCount() {
        return memberCount;
    }

    public void setMemberCount(int memberCount) {
        this.memberCount = memberCount;
    }

    public Pkinfo getPkInfo() {
        return pkInfo;
    }

    public void setPkInfo(Pkinfo pkInfo) {
        this.pkInfo = pkInfo;
    }

    public HotLive getPkLive() {
        return pkLive;
    }

    public void setPkLive(HotLive pkLive) {
        this.pkLive = pkLive;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public UserRegist getToUser() {
        return toUser;
    }

    public void setToUser(UserRegist toUser) {
        this.toUser = toUser;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
