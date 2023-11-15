package com.feicui365.live.model.entity;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.Serializable;
import java.util.ArrayList;

public class Trend implements Serializable, MultiItemEntity {
    private String id;
    private String uid;
    private String title;
    private String image_url;
    private String video_url;
    private String blur_image_url;
    private String type;
    private String recommend;
    private String collect_count;
    private String watch_count;
    private String like_count;
    private String comment_count;
    private String single_display_type;
    private String create_time;
    private String status;
    private String banned_reason;
    private String unlock_price;
    private String is_secret;
    private String unlocked;
    private String liked;
    private String collected;
    private UserRegist user;
    public String content;
    public ArrayList<String> photos;
    public String media_type;
    public String topic;

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getMedia_type() {
        return media_type;
    }

    public void setMedia_type(String media_type) {
        this.media_type = media_type;
    }



    public Trend() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getVideo_url() {
        return video_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }

    public String getBlur_image_url() {
        return blur_image_url;
    }

    public void setBlur_image_url(String blur_image_url) {
        this.blur_image_url = blur_image_url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRecommend() {
        return recommend;
    }

    public void setRecommend(String recommend) {
        this.recommend = recommend;
    }

    public String getCollect_count() {
        return collect_count;
    }

    public void setCollect_count(String collect_count) {
        this.collect_count = collect_count;
    }

    public String getWatch_count() {
        return watch_count;
    }

    public void setWatch_count(String watch_count) {
        this.watch_count = watch_count;
    }

    public String getLike_count() {
        return like_count;
    }

    public void setLike_count(String like_count) {
        this.like_count = like_count;
    }

    public String getComment_count() {
        return comment_count;
    }

    public void setComment_count(String comment_count) {
        this.comment_count = comment_count;
    }

    public String getSingle_display_type() {
        return single_display_type;
    }

    public void setSingle_display_type(String single_display_type) {
        this.single_display_type = single_display_type;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBanned_reason() {
        return banned_reason;
    }

    public void setBanned_reason(String banned_reason) {
        this.banned_reason = banned_reason;
    }

    public String getUnlock_price() {
        return unlock_price;
    }

    public void setUnlock_price(String unlock_price) {
        this.unlock_price = unlock_price;
    }

    public String getIs_secret() {
        return is_secret;
    }

    public void setIs_secret(String is_secret) {
        this.is_secret = is_secret;
    }

    public String getUnlocked() {
        return unlocked;
    }

    public void setUnlocked(String unlocked) {
        this.unlocked = unlocked;
    }

    public String getLiked() {
        return liked;
    }

    public void setLiked(String liked) {
        this.liked = liked;
    }

    public String getCollected() {
        return collected;
    }

    public void setCollected(String collected) {
        this.collected = collected;
    }

    public UserRegist getUser() {
        return user;
    }

    public void setUser(UserRegist user) {
        this.user = user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ArrayList<String> getPhotos() {
        return photos;
    }

    public void setPhotos(ArrayList<String> photos) {
        this.photos = photos;
    }

    public Trend(String content, ArrayList<String> photos) {
        this.content = content;
        this.photos = photos;
    }

    @Override
    public int getItemType() {
        return Integer.parseInt(type);
    }
}
