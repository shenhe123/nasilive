package com.feicui365.live.model.entity;

import java.io.Serializable;

public class LivePoster implements Serializable {
     private String title ;
     private String thumb ;
     private String nick_name;
     private String avatar;
     private String desc;
     private String ewm_url;
     private String ewm_desc;

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

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getEwm_url() {
        return ewm_url;
    }

    public void setEwm_url(String ewm_url) {
        this.ewm_url = ewm_url;
    }

    public String getEwm_desc() {
        return ewm_desc;
    }

    public void setEwm_desc(String ewm_desc) {
        this.ewm_desc = ewm_desc;
    }
}
