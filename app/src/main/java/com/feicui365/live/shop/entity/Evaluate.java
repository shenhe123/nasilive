package com.feicui365.live.shop.entity;

import java.io.Serializable;
import java.util.ArrayList;

public class Evaluate  implements Serializable {
    String content;
    String create_time;
    int goodsid;
    String id;
    String img_urls;
    int ordergoodsid;
    int score;
    String uid;
    User user;
    ArrayList<String> img_list;
    OrderGoods ordergoods;

    public Evaluate() {
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

    public int getGoodsid() {
        return goodsid;
    }

    public void setGoodsid(int goodsid) {
        this.goodsid = goodsid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImg_urls() {
        return img_urls;
    }

    public void setImg_urls(String img_urls) {
        this.img_urls = img_urls;
    }

    public int getOrdergoodsid() {
        return ordergoodsid;
    }

    public void setOrdergoodsid(int ordergoodsid) {
        this.ordergoodsid = ordergoodsid;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public class User  implements Serializable{

        String avatar;
        String id;
        String nick_name;

        public User() {
        }


        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
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
    }

    public ArrayList<String> getImg_list() {
        return img_list;
    }

    public void setImg_list(ArrayList<String> img_list) {
        this.img_list = img_list;
    }

    public OrderGoods getOrdergoods() {
        return ordergoods;
    }

    public void setOrdergoods(OrderGoods ordergoods) {
        this.ordergoods = ordergoods;
    }
}
