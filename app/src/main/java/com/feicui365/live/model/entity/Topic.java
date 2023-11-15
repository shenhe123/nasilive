package com.feicui365.live.model.entity;

import java.io.Serializable;

public class Topic implements Serializable {
    String back_img_url;
    String create_time;
    String desc;
    String id;
    String status;
    String title;
    String used_times;

    public Topic() {
    }

    public String getBack_img_url() {
        return back_img_url;
    }

    public void setBack_img_url(String back_img_url) {
        this.back_img_url = back_img_url;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUsed_times() {
        return used_times;
    }

    public void setUsed_times(String used_times) {
        this.used_times = used_times;
    }
}
