package com.feicui365.live.model.entity;

import java.io.Serializable;
import java.util.ArrayList;

public class HomeRecommendData implements Serializable {
    private ArrayList<HotLive> hot;
    private ArrayList<HotLive> rec_anchors;
    private ArrayList<HotLive> lives;

    public ArrayList<HotLive> getHot() {
        return hot;
    }

    public void setHot(ArrayList<HotLive> hot) {
        this.hot = hot;
    }

    public ArrayList<HotLive> getRec_anchors() {
        return rec_anchors;
    }

    public void setRec_anchors(ArrayList<HotLive> rec_anchors) {
        this.rec_anchors = rec_anchors;
    }

    public ArrayList<HotLive> getLives() {
        return lives;
    }

    public void setLives(ArrayList<HotLive> lives) {
        this.lives = lives;
    }
}
