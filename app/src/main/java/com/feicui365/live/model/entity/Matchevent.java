package com.feicui365.live.model.entity;

import java.io.Serializable;

public class Matchevent implements Serializable {
    private String id;
    private String area_id;
    private String country_id;
    private String type;
    private String name_zh;
    private String name_zht;
    private String name_en;
    private String short_name_zh;
    private String short_name_zht;
    private String short_name_en;
    private String logo;
    private String level;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getArea_id() {
        return area_id;
    }

    public void setArea_id(String area_id) {
        this.area_id = area_id;
    }

    public String getCountry_id() {
        return country_id;
    }

    public void setCountry_id(String country_id) {
        this.country_id = country_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
