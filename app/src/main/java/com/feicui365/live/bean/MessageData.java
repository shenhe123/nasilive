package com.feicui365.live.bean;



import com.feicui365.live.live.bean.Chat;
import com.feicui365.live.live.bean.Streamer;
import com.feicui365.live.model.entity.GiftInfo;
import com.feicui365.live.model.entity.Notify;

import com.feicui365.live.shop.entity.Good;


import java.io.Serializable;

public class MessageData implements Serializable {



    private Good goods;
    private Notify notify;
    private Chat chat;
    private Streamer streamer;
    private GiftInfo gift;

    public MessageData() {
    }

    public Good getGoods() {
        return goods;
    }

    public void setGoods(Good goods) {
        this.goods = goods;
    }

    public Notify getNotify() {
        return notify;
    }

    public void setNotify(Notify notify) {
        this.notify = notify;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    public Streamer getStreamer() {
        return streamer;
    }

    public void setStreamer(Streamer streamer) {
        this.streamer = streamer;
    }

    public GiftInfo getGift() {
        return gift;
    }

    public void setGift(GiftInfo gift) {
        this.gift = gift;
    }
}
