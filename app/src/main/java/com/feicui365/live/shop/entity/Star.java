package com.feicui365.live.shop.entity;

import java.io.Serializable;

public class Star implements Serializable {
    int postion;
    boolean check;

    public Star() {
    }

    public int getPostion() {
        return postion;
    }

    public void setPostion(int postion) {
        this.postion = postion;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }
}
