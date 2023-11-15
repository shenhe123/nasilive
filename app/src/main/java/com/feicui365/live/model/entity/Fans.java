package com.feicui365.live.model.entity;

import java.io.Serializable;

public class Fans implements Serializable {
    private String id;
    private String anchorid;
    private String fansid;
    private String create_time;
    private Fan fan;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAnchorid() {
        return anchorid;
    }

    public void setAnchorid(String anchorid) {
        this.anchorid = anchorid;
    }

    public String getFansid() {
        return fansid;
    }

    public void setFansid(String fansid) {
        this.fansid = fansid;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public Fan getFan() {
        return fan;
    }

    public void setFan(Fan fan) {
        this.fan = fan;
    }

    public class  Fan{
        private String id;
        private String nick_name;
        private String avatar;
        private String user_level;
        private Profile profile;
        private int isattent;

        public int getIsattent() {
            return isattent;
        }

        public void setIsattent(int isattent) {
            this.isattent = isattent;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getNick_name() {
            return nick_name;
        }

        public void setNick_name(String nick_name) {
            this.nick_name = nick_name;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getUser_level() {
            return user_level;
        }

        public void setUser_level(String user_level) {
            this.user_level = user_level;
        }

        public Profile getProfile() {
            return profile;
        }

        public void setProfile(Profile profile) {
            this.profile = profile;
        }
    }
}
