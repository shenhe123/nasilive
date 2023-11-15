package com.feicui365.live.model.entity;

import java.io.Serializable;

public class Comment implements Serializable {

    String id;
    String rootid;
    String videoid;
    String uid;
    String touid;
    String content;
    String create_time;
    String like_count;
    String reply_count;
    UserRegist user;
    UserRegist touser;
    String liked;
    private String tocommentid;

    public String getTocommentid() {
        return tocommentid;
    }

    public void setTocommentid(String tocommentid) {
        this.tocommentid = tocommentid;
    }

    public String getLiked() {
        return liked;
    }

    public void setLiked(String liked) {
        this.liked = liked;
    }

    public UserRegist getTouser() {
        return touser;
    }

    public void setTouser(UserRegist touser) {
        this.touser = touser;
    }

    public Comment() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRootid() {
        return rootid;
    }

    public void setRootid(String rootid) {
        this.rootid = rootid;
    }

    public String getVideoid() {
        return videoid;
    }

    public void setVideoid(String videoid) {
        this.videoid = videoid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTouid() {
        return touid;
    }

    public void setTouid(String touid) {
        this.touid = touid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getLike_count() {
        return like_count;
    }

    public void setLike_count(String like_count) {
        this.like_count = like_count;
    }

    public String getReply_count() {
        return reply_count;
    }

    public void setReply_count(String reply_count) {
        this.reply_count = reply_count;
    }

    public UserRegist getUser() {
        return user;
    }

    public void setUser(UserRegist user) {
        this.user = user;
    }
}
