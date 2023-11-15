package com.feicui365.live.model.entity;

import java.io.Serializable;

public class PhoneCodeBody implements Serializable {
    public String mobile;

    public PhoneCodeBody(String phone) {
        mobile = phone;
    }
}
