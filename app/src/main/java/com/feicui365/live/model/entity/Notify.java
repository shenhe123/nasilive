package com.feicui365.live.model.entity;

public class Notify {
    private String type;
    private UserRegist user;
    private String link_acc_url;
    private String touid;
    private Pkinfo pkinfo;
    private HotLive pklive;

    public HotLive getPklive() {
        return pklive;
    }

    public void setPklive(HotLive pklive) {
        this.pklive = pklive;
    }

    public Pkinfo getPkinfo() {
        return pkinfo;
    }

    public void setPkinfo(Pkinfo pkinfo) {
        this.pkinfo = pkinfo;
    }

    public String getLink_acc_url() {
        return link_acc_url;
    }

    public void setLink_acc_url(String link_acc_url) {
        this.link_acc_url = link_acc_url;
    }

    public String getTouid() {
        return touid;
    }

    public void setTouid(String touid) {
        this.touid = touid;
    }

    public Notify() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public UserRegist getUser() {
        return user;
    }

    public void setUser(UserRegist user) {
        this.user = user;
    }
}
