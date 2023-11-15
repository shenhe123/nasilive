package com.feicui365.live.model.entity;

import java.io.Serializable;
import java.util.ArrayList;

public class YouliaoFollowData implements Serializable {
    private ArrayList<OutsYouLiaoByMatch> list;
    private ArrayList<Banners> ads;

    public ArrayList<OutsYouLiaoByMatch> getList() {
        return list;
    }

    public void setList(ArrayList<OutsYouLiaoByMatch> list) {
        this.list = list;
    }

    public ArrayList<Banners> getAds() {
        return ads;
    }

    public void setAds(ArrayList<Banners> ads) {
        this.ads = ads;
    }
}
