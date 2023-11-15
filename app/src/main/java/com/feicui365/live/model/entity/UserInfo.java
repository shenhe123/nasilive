package com.feicui365.live.model.entity;

import java.io.Serializable;

public class UserInfo implements Serializable {
    private int star_count;
    private int attent_count;
    private int fans_count;
    private int video_count;
    private int moment_count;
    private int like_video_count;
    private int visitor_count;

    public int getVisitor_count() {
        return visitor_count;
    }

    public void setVisitor_count(int visitor_count) {
        this.visitor_count = visitor_count;
    }

    public int getStar_count() {
        return star_count;
    }

    public void setStar_count(int star_count) {
        this.star_count = star_count;
    }

    public int getAttent_count() {
        return attent_count;
    }

    public void setAttent_count(int attent_count) {
        this.attent_count = attent_count;
    }

    public int getFans_count() {
        return fans_count;
    }

    public void setFans_count(int fans_count) {
        this.fans_count = fans_count;
    }

    public int getVideo_count() {
        return video_count;
    }

    public void setVideo_count(int video_count) {
        this.video_count = video_count;
    }

    public int getMoment_count() {
        return moment_count;
    }

    public void setMoment_count(int moment_count) {
        this.moment_count = moment_count;
    }

    public int getLike_video_count() {
        return like_video_count;
    }

    public void setLike_video_count(int like_video_count) {
        this.like_video_count = like_video_count;
    }
}
