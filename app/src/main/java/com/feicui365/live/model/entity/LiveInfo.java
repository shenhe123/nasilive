package com.feicui365.live.model.entity;

import java.io.Serializable;
import java.util.ArrayList;

public class LiveInfo implements Serializable {
    private HotLive live;
    private ArrayList<ContributeRank> contribute;


    public HotLive getLive() {
        return live;
    }

    public void setLive(HotLive live) {
        this.live = live;
    }

    public ArrayList<ContributeRank> getContribute() {
        return contribute;
    }

    public void setContribute(ArrayList<ContributeRank> contribute) {
        this.contribute = contribute;
    }


}
