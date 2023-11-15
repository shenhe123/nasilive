package com.feicui365.live.model.entity;

import java.io.Serializable;

public class LiveCategory implements Serializable {
    private String id;
    private String title;
    private String icon;
    private String sort;

    public LiveCategory(String title) {
        this.title = title;
    }

    public LiveCategory() {
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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }
}
