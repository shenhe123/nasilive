package com.feicui365.live.model.entity;

import android.view.View;

import com.alibaba.fastjson.annotation.JSONField;



public class ChatGiftBean {

    public static final int TYPE_NORMAL = 0;
    public static final int TYPE_DELUXE = 1;
    public static final int MARK_NORMAL = 0;
    public static final int MARK_HOT = 1;
    public static final int MARK_GUARD = 2;

    private int id;
    private int type;//0 普通礼物 1是豪华礼物
    private int mark;// 0 普通  1热门  2守护
    private String title;
    private String price;
    private String icon;
    private boolean checked;
    private int page;
    private View mView;
    private String animat_type;
    private String animation;
    private String use_type;

    public String getUse_type() {
        return use_type;
    }

    public void setUse_type(String use_type) {
        this.use_type = use_type;
    }

    public String getAnimation() {
        return animation;
    }

    public void setAnimation(String animation) {
        this.animation = animation;
    }

    public String getAnimat_type() {
        return animat_type;
    }

    public void setAnimat_type(String animat_type) {
        this.animat_type = animat_type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @JSONField(name = "price")
    public String getPrice() {
        return price;
    }

    @JSONField(name = "price")
    public void setPrice(String price) {
        this.price = price;
    }

    @JSONField(name = "icon")
    public String getIcon() {
        return icon;
    }

    @JSONField(name = "icon")
    public void setIcon(String icon) {
        this.icon = icon;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public View getView() {
        return mView;
    }

    public void setView(View view) {
        mView = view;
    }
}
