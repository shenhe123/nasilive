package com.feicui365.live.model.entity;

import java.io.Serializable;
import java.util.ArrayList;

public class MatchOdd implements Serializable {
    private ArrayList<MatchOddData> asia;
    private ArrayList<MatchOddData> bs;
    private ArrayList<MatchOddData> eu;

    public ArrayList<MatchOddData> getAsia() {
        return asia;
    }

    public void setAsia(ArrayList<MatchOddData> asia) {
        this.asia = asia;
    }

    public ArrayList<MatchOddData> getBs() {
        return bs;
    }

    public void setBs(ArrayList<MatchOddData> bs) {
        this.bs = bs;
    }

    public ArrayList<MatchOddData> getEu() {
        return eu;
    }

    public void setEu(ArrayList<MatchOddData> eu) {
        this.eu = eu;
    }
}
