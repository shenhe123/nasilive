package com.feicui365.live.eventbus;


import com.feicui365.live.model.entity.GiftInfo;

public class OnGiftChoseBus {
    public GiftInfo giftInfo;

    public OnGiftChoseBus(GiftInfo giftInfo) {
        this.giftInfo = giftInfo;
    }
}
