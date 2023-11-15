package com.feicui365.live.live.utils;

import android.util.SparseIntArray;

import com.feicui365.live.R;


public class CommonIconUtil {

    private static SparseIntArray sLiveGiftCountMap;//送礼物数字
    private static SparseIntArray sOnLineMap2;//在线类型图标

    static {
        sLiveGiftCountMap = new SparseIntArray();
        sLiveGiftCountMap.put(0, R.mipmap.icon_live_gift_count_0);
        sLiveGiftCountMap.put(1, R.mipmap.icon_live_gift_count_1);
        sLiveGiftCountMap.put(2, R.mipmap.icon_live_gift_count_2);
        sLiveGiftCountMap.put(3, R.mipmap.icon_live_gift_count_3);
        sLiveGiftCountMap.put(4, R.mipmap.icon_live_gift_count_4);
        sLiveGiftCountMap.put(5, R.mipmap.icon_live_gift_count_5);
        sLiveGiftCountMap.put(6, R.mipmap.icon_live_gift_count_6);
        sLiveGiftCountMap.put(7, R.mipmap.icon_live_gift_count_7);
        sLiveGiftCountMap.put(8, R.mipmap.icon_live_gift_count_8);
        sLiveGiftCountMap.put(9, R.mipmap.icon_live_gift_count_9);


        sOnLineMap2 = new SparseIntArray();
/*
        sOnLineMap2.put(Constants.LINE_TYPE_OFF, R.mipmap.o_user_line_off);
        sOnLineMap2.put(Constants.LINE_TYPE_DISTURB, R.mipmap.o_user_line_disturb);
        sOnLineMap2.put(Constants.LINE_TYPE_CHAT, R.mipmap.o_user_line_chat);
        sOnLineMap2.put(Constants.LINE_TYPE_ON, R.mipmap.o_user_line_on);
*/

    }

    public static int getGiftCountIcon(int key) {
        return sLiveGiftCountMap.get(key);
    }




    public static int getOnLineIcon2(int key) {
        return sOnLineMap2.get(key);
    }

}
