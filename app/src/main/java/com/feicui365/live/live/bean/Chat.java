package com.feicui365.live.live.bean;



import com.feicui365.live.model.entity.UserRegist;

import java.io.Serializable;


/**
 * 注释
 * 聊天内容BEAN
 * */
public class Chat implements Serializable {



    private String nick_name;
    private int level;
    private boolean isVip;
    private String message;
    private UserRegist sender;
    private int is_guardian;
    private int is_manager;

    public Chat() {
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean isVip() {
        return isVip;
    }

    public void setVip(boolean vip) {
        isVip = vip;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UserRegist getSender() {
        return sender;
    }

    public void setSender(UserRegist sender) {
        this.sender = sender;
    }

    public int getIs_guardian() {
        return is_guardian;
    }

    public void setIs_guardian(int is_guardian) {
        this.is_guardian = is_guardian;
    }

    public int getIs_manager() {
        return is_manager;
    }

    public void setIs_manager(int is_manager) {
        this.is_manager = is_manager;
    }
}
