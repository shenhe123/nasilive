package com.feicui365.live.ui.uiinterfae;

import com.feicui365.live.model.entity.GiftInfo;


import java.util.ArrayList;

public interface ShowGift{
    void show(GiftInfo data);
    void setGift(ArrayList<GiftInfo> giftList);
}