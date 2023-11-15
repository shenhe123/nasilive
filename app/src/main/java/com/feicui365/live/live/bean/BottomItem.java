package com.feicui365.live.live.bean;

/**
 *
 */
public class BottomItem {
    String name;
    boolean chose;
    int res;
    int respre;
    long unread;

    public BottomItem() {
    }


    public BottomItem(String name, boolean chose, int res, int respre) {
        this.name = name;
        this.chose = chose;
        this.res = res;
        this.respre = respre;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isChose() {
        return chose;
    }

    public void setChose(boolean chose) {
        this.chose = chose;
    }

    public int getRes() {
        return res;
    }

    public void setRes(int res) {
        this.res = res;
    }

    public int getRespre() {
        return respre;
    }

    public void setRespre(int respre) {
        this.respre = respre;
    }

    public long getUnread() {
        return unread;
    }

    public void setUnread(long unread) {
        this.unread = unread;
    }
}
