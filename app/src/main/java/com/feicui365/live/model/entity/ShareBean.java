package com.feicui365.live.model.entity;

import java.io.Serializable;

public class ShareBean implements Serializable {
    private String title;
    private String id;
    private int res;

    public ShareBean() {
    }

    public ShareBean(String title, String id, int res) {
        this.title = title;
        this.id = id;
        this.res = res;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }



    public int getRes() {
        return res;
    }

    public void setRes(int res) {
        this.res = res;
    }
}
