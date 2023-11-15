package com.feicui365.live.ui.act;

import com.feicui365.live.model.entity.Profile;

import java.io.Serializable;

public class Chat implements Serializable {
    String message;
    String textColor;
    String nameColor;
    int isManager;
    int isGuardian;
    int chatType;
    Sender sender;

    public Chat() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTextColor() {
        return textColor;
    }

    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }

    public String getNameColor() {
        return nameColor;
    }

    public void setNameColor(String nameColor) {
        this.nameColor = nameColor;
    }

    public int getIsManager() {
        return isManager;
    }

    public void setIsManager(int isManager) {
        this.isManager = isManager;
    }

    public int getIsGuardian() {
        return isGuardian;
    }

    public void setIsGuardian(int isGuardian) {
        this.isGuardian = isGuardian;
    }

    public int getChatType() {
        return chatType;
    }

    public void setChatType(int chatType) {
        this.chatType = chatType;
    }

    public Sender getSender() {
        return sender;
    }

    public void setSender(Sender sender) {
        this.sender = sender;
    }

    public static class Sender implements Serializable {
        String agentid;
        String attentCount;
        String diamond;
        String fansCount;
        String giftSpend;
        String gold;
        String guildid;
        String id;
        String avatar;
        String point;
        String visitorCount;
        Profile profile;

        public Sender() {
        }

        public String getAgentid() {
            return agentid;
        }

        public void setAgentid(String agentid) {
            this.agentid = agentid;
        }

        public String getAttentCount() {
            return attentCount;
        }

        public void setAttentCount(String attentCount) {
            this.attentCount = attentCount;
        }

        public String getDiamond() {
            return diamond;
        }

        public void setDiamond(String diamond) {
            this.diamond = diamond;
        }

        public String getFansCount() {
            return fansCount;
        }

        public void setFansCount(String fansCount) {
            this.fansCount = fansCount;
        }

        public String getGiftSpend() {
            return giftSpend;
        }

        public void setGiftSpend(String giftSpend) {
            this.giftSpend = giftSpend;
        }

        public String getGold() {
            return gold;
        }

        public void setGold(String gold) {
            this.gold = gold;
        }

        public String getGuildid() {
            return guildid;
        }

        public void setGuildid(String guildid) {
            this.guildid = guildid;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }



        public String getPoint() {
            return point;
        }

        public void setPoint(String point) {
            this.point = point;
        }

        public String getVisitorCount() {
            return visitorCount;
        }

        public void setVisitorCount(String visitorCount) {
            this.visitorCount = visitorCount;
        }

        public Profile getProfile() {
            return profile;
        }

        public void setProfile(Profile profile) {
            this.profile = profile;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }
    }
}
