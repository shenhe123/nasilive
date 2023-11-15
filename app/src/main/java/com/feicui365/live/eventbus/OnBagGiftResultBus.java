package com.feicui365.live.eventbus;

import com.feicui365.live.model.entity.BagSendResult;

public class OnBagGiftResultBus {
    public BagSendResult bean;

    public OnBagGiftResultBus(BagSendResult bean) {
        this.bean = bean;
    }
}
