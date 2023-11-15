package com.feicui365.live.shop.entity;

import java.io.Serializable;

public class AddSize implements Serializable {
    String size;
    String tag;


    public AddSize() {
    }

    public AddSize(String size) {
        this.size = size;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
