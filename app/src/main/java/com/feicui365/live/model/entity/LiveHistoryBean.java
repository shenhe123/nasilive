package com.feicui365.live.model.entity;

import java.io.Serializable;

public class LiveHistoryBean implements Serializable {
     private String title ;
     private String thumb ;
     private int is_hide_name ;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public int getIs_hide_name() {
        return is_hide_name;
    }

    public void setIs_hide_name(int is_hide_name) {
        this.is_hide_name = is_hide_name;
    }
}
