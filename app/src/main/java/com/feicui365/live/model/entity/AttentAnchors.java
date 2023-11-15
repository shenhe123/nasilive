package com.feicui365.live.model.entity;

public class AttentAnchors {
    private String id;
    private String anchorid;
    private String fansid;
    private String create_time;
    private UserRegist anchor;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAnchorid() {
        return anchorid;
    }

    public void setAnchorid(String anchorid) {
        this.anchorid = anchorid;
    }

    public String getFansid() {
        return fansid;
    }

    public void setFansid(String fansid) {
        this.fansid = fansid;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public UserRegist getAnchor() {
        return anchor;
    }

    public void setAnchor(UserRegist anchor) {
        this.anchor = anchor;
    }
}
