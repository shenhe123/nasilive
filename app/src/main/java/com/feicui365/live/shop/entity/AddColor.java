package com.feicui365.live.shop.entity;

import java.io.Serializable;

public class AddColor implements Serializable {
    String color;
    String img_url;
    String tag;

    public AddColor() {
    }

    public AddColor(String color, String img_url) {
        this.color = color;
        this.img_url = img_url;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getImg_url() {

            return img_url;


    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }
}
