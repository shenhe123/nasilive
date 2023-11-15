package com.feicui365.live.model.entity;

import java.io.Serializable;

public class MLVBLoginResponse implements Serializable {
    public String userID;
    public String token;


    public MLVBLoginResponse() {
    }

    public MLVBLoginResponse(String userID) {
        this.userID = userID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
