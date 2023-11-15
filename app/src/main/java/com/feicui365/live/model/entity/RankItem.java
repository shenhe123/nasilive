package com.feicui365.live.model.entity;

import java.io.Serializable;

public class RankItem implements Serializable {
    String id;
    String uid;
    String consume;
    UserRegist user;

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

    public String getConsume() {
        return consume;
    }

    public void setConsume(String consume) {
        this.consume = consume;
    }

    public UserRegist getUser() {
        return user;
    }

    public void setUser(UserRegist user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "RankItem{" +
                "id='" + id + '\'' +
                ", uid='" + uid + '\'' +
                ", consume='" + consume + '\'' +
                ", user=" + user +
                '}';
    }
}
