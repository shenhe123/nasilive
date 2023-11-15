package com.feicui365.live.model.entity;

import java.io.Serializable;

public class RegistBody implements Serializable {
    public String account;
    public String pwd;
    public String smscode;

    public RegistBody(String account, String pwd, String smscode) {
        this.account = account;
        this.pwd = pwd;
        this.smscode = smscode;
    }
}
