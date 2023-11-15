package com.feicui365.live.shop.entity;

import java.io.Serializable;
import java.util.ArrayList;

public class ClassIfy implements Serializable {

    String id;
    int parentid;
    int status;
    ArrayList<Subcates> subcates;
    String title;
    boolean chose;


    public ClassIfy() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getParentid() {
        return parentid;
    }

    public void setParentid(int parentid) {
        this.parentid = parentid;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public ArrayList<Subcates> getSubcates() {
        return subcates;
    }

    public void setSubcates(ArrayList<Subcates> subcates) {
        this.subcates = subcates;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isChose() {
        return chose;
    }

    public void setChose(boolean chose) {
        this.chose = chose;
    }
}
