package com.feicui365.live.model.entity;

import java.io.Serializable;
import java.util.ArrayList;

public class BaseLiveInfo implements Serializable{

    BaseLive live;
    int  fans_count;
    String isattent;
    ArrayList<ContributeRank> contribute;

    public int getFans_count() {
        return fans_count;
    }

    public void setFans_count(int fans_count) {
        this.fans_count = fans_count;
    }

    public BaseLiveInfo() {
    }

    public BaseLive getLive() {
        return live;
    }

    public void setLive(BaseLive live)  {
        this.live = live;
    }



    public String getIsattent() {
        return isattent;
    }

    public void setIsattent(String isattent) {
        this.isattent = isattent;
    }

    public ArrayList<ContributeRank> getContribute() {
        return contribute;
    }

    public void setContribute(ArrayList<ContributeRank> contribute) {
        this.contribute = contribute;
    }

    public class BaseLive implements Serializable {
        int anchorid;
        int profit;
        int hot;

        public int getAnchorid() {
            return anchorid;
        }

        public void setAnchorid(int anchorid) {
            this.anchorid = anchorid;
        }

        public int getProfit() {
            return profit;
        }

        public void setProfit(int profit) {
            this.profit = profit;
        }

        public int getHot() {
            return hot;
        }

        public void setHot(int hot) {
            this.hot = hot;
        }
    }
}
