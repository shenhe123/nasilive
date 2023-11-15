package com.feicui365.live.model.entity;

import java.io.Serializable;

public class Gift implements Serializable {
    String id;
    String title;
    String icon;

    public Gift() {
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
}
