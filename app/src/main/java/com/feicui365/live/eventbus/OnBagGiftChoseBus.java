package com.feicui365.live.eventbus;

import com.feicui365.live.model.entity.BagGiftInfo;

public class OnBagGiftChoseBus {
    public BagGiftInfo giftInfo;

    public OnBagGiftChoseBus(BagGiftInfo giftInfo) {
        this.giftInfo = giftInfo;
    }
}
