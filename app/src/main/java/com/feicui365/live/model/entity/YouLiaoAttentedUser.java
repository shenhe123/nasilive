package com.feicui365.live.model.entity;

import java.io.Serializable;

public class YouLiaoAttentedUser implements Serializable {
    private String id;
    private String avatar;
    private String nick_name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }
}
