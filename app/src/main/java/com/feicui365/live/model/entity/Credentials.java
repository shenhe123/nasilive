package com.feicui365.live.model.entity;

import java.io.Serializable;

public class Credentials implements Serializable {
    private String sessionToken;
    private String tmpSecretId;
    private String tmpSecretKey;

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    public String getTmpSecretId() {
        return tmpSecretId;
    }

    public void setTmpSecretId(String tmpSecretId) {
        this.tmpSecretId = tmpSecretId;
    }

    public String getTmpSecretKey() {
        return tmpSecretKey;
    }

    public void setTmpSecretKey(String tmpSecretKey) {
        this.tmpSecretKey = tmpSecretKey;
    }
}
