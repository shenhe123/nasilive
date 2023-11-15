package com.feicui365.live.model.entity;

import android.text.TextUtils;

import java.io.Serializable;


public class GiftInfo implements Serializable {


    private int animat_type;
    private String animation;
    private int count;
    private int duration;
    private String icon;
    private String id;
    private int price;
    private  UserRegist sender;
    private  String sort;
    private  String status;
    private  String title;
    private int type;
    private  int use_type;
    private String mKey;
    private int lianCount = 1;
    private boolean isCheck;

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public GiftInfo() {
    }

    public int getAnimat_type() {
        return animat_type;
    }

    public void setAnimat_type(int animat_type) {
        this.animat_type = animat_type;
    }

    public String getAnimation() {
        return animation;
    }

    public void setAnimation(String animation) {
        this.animation = animation;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public UserRegist getSender() {
        return sender;
    }

    public void setSender(UserRegist sender) {
        this.sender = sender;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getUse_type() {
        return use_type;
    }

    public void setUse_type(int use_type) {
        this.use_type = use_type;
    }

    public String getKey() {

        if (TextUtils.isEmpty(mKey)) {
            mKey = this.sender.getId() + this.id + this.count;
        }
        return mKey;
    }

    public int getLianCount() {
        return lianCount;
    }

    public void setLianCount(int lianCount) {
        this.lianCount = lianCount;
    }
}
