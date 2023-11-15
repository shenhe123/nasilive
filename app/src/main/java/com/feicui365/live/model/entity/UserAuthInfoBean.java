package com.feicui365.live.model.entity;

import java.io.Serializable;

public class UserAuthInfoBean implements Serializable {
    private String real_name;
    private String id_num;
    private String id_card_url;
    private String reject_reason;
    private int  status;

    public String getReal_name() {
        return real_name;
    }

    public void setReal_name(String real_name) {
        this.real_name = real_name;
    }

    public String getId_num() {
        return id_num;
    }

    public void setId_num(String id_num) {
        this.id_num = id_num;
    }

    public String getId_card_url() {
        return id_card_url;
    }

    public void setId_card_url(String id_card_url) {
        this.id_card_url = id_card_url;
    }

    public String getReject_reason() {
        return reject_reason;
    }

    public void setReject_reason(String reject_reason) {
        this.reject_reason = reject_reason;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
