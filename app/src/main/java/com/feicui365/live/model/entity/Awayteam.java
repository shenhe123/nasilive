package com.feicui365.live.model.entity;

import java.io.Serializable;

public class Awayteam implements Serializable {
    private String id;
    private String matchevent_id;
    private String name_zh;
    private String name_zht;
    private String name_en;
    private String short_name_zh;
    private String short_name_zht;
    private String short_name_en;
    private String logo;
    private String found;
    private String website;
    private String national;
    private String country_logo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMatchevent_id() {
        return matchevent_id;
    }

    public void setMatchevent_id(String matchevent_id) {
        this.matchevent_id = matchevent_id;
    }

    public String getName_zh() {
        return name_zh;
    }

    public void setName_zh(String name_zh) {
        this.name_zh = name_zh;
    }

    public String getName_zht() {
        return name_zht;
    }

    public void setName_zht(String name_zht) {
        this.name_zht = name_zht;
    }

    public String getName_en() {
        return name_en;
    }

    public void setName_en(String name_en) {
        this.name_en = name_en;
    }

    public String getShort_name_zh() {
        return short_name_zh;
    }

    public void setShort_name_zh(String short_name_zh) {
        this.short_name_zh = short_name_zh;
    }

    public String getShort_name_zht() {
        return short_name_zht;
    }

    public void setShort_name_zht(String short_name_zht) {
        this.short_name_zht = short_name_zht;
    }

    public String getShort_name_en() {
        return short_name_en;
    }

    public void setShort_name_en(String short_name_en) {
        this.short_name_en = short_name_en;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getFound() {
        return found;
    }

    public void setFound(String found) {
        this.found = found;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getNational() {
        return national;
    }

    public void setNational(String national) {
        this.national = national;
    }

    public String getCountry_logo() {
        return country_logo;
    }

    public void setCountry_logo(String country_logo) {
        this.country_logo = country_logo;
    }
}
