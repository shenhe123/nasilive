package com.feicui365.live.model.entity;

import java.io.Serializable;

public class ExponentData implements Serializable {
    private String company_id;
    private MatchOddData start;
    private MatchOddData end;

    public String getCompany_id() {
        return company_id;
    }

    public void setCompany_id(String company_id) {
        this.company_id = company_id;
    }

    public MatchOddData getStart() {
        return start;
    }

    public void setStart(MatchOddData start) {
        this.start = start;
    }

    public MatchOddData getEnd() {
        return end;
    }

    public void setEnd(MatchOddData end) {
        this.end = end;
    }
}
