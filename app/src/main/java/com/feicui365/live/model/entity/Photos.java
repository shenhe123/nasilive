package com.feicui365.live.model.entity;

import java.io.Serializable;

public class Photos implements Serializable {
    private String id;
    private String uid;
    private String img_url;
    private String is_cover;
    private String create_time;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getIs_cover() {
        return is_cover;
    }

    public void setIs_cover(String is_cover) {
        this.is_cover = is_cover;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }
}
