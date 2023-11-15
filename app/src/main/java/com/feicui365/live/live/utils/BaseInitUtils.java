package com.feicui365.live.live.utils;


import com.feicui365.live.R;
import com.feicui365.live.base.MyApp;
import com.feicui365.live.live.bean.BaseGuardianBuyInfo;
import com.feicui365.live.live.bean.BaseGuardianTipsInfo;
import com.feicui365.live.live.bean.BottomItem;

import com.feicui365.live.util.MyUserInstance;

import java.util.ArrayList;


/**
 *
 */
public class BaseInitUtils {


    public static ArrayList<BottomItem> getAnchorBottomList() {
        //String name, boolean chose, int res, int respre
        ArrayList<BottomItem> list = new ArrayList<>();
        list.add(new BottomItem(MyApp.getInstance().getString(R.string.st_im), false, R.drawable.ic_bottom_live_im, R.drawable.ic_bottom_live_im));
        list.add(new BottomItem(MyApp.getInstance().getString(R.string.st_shop_item_list), false, R.drawable.ic_bottom_live_shop, R.drawable.ic_bottom_live_shop));
        list.add(new BottomItem(MyApp.getInstance().getString(R.string.st_link_on), false, R.drawable.ic_bottom_live_link_on, R.drawable.ic_bottom_live_link_off));
        list.add(new BottomItem(MyApp.getInstance().getString(R.string.st_start_pk), false, R.drawable.ic_bottom_live_pk, R.drawable.ic_bottom_live_pk));
        list.add(new BottomItem(MyApp.getInstance().getString(R.string.st_room_setting), false, R.drawable.ic_bottom_live_setting, R.drawable.ic_bottom_live_setting));


        return list;
    }

    public static ArrayList<BottomItem> getUserBottomList() {
        //String name, boolean chose, int res, int respre
        ArrayList<BottomItem> list = new ArrayList<>();
        list.add(new BottomItem(MyApp.getInstance().getString(R.string.st_mine_shop), false, R.drawable.ic_live_shop_car, R.drawable.ic_live_shop_car));
        list.add(new BottomItem(MyApp.getInstance().getString(R.string.st_link_on), false, R.drawable.ic_live_link, R.drawable.ic_live_link));
        list.add(new BottomItem(MyApp.getInstance().getString(R.string.st_gift), false, R.drawable.ic_live_vertical_gift, R.drawable.ic_live_vertical_gift));
        list.add(new BottomItem(MyApp.getInstance().getString(R.string.st_im), false, R.drawable.ic_live_vertical_message, R.drawable.ic_live_vertical_message));

            list.add(new BottomItem("转盘", false, R.mipmap.share_live, R.mipmap.share_live));


        list.add(new BottomItem(MyApp.getInstance().getString(R.string.st_close), false, R.drawable.ic_live_push_close, R.drawable.ic_live_push_close));


        return list;
    }




    public static ArrayList<BaseGuardianBuyInfo> getBaseGuardianBuyInfo() {
        //String name, boolean chose, int res, int respre
        ArrayList<BaseGuardianBuyInfo> list = new ArrayList<>();
        list.add(new BaseGuardianBuyInfo(MyApp.getInstance().getString(R.string.st_live_guardian_type_1), "0",
                MyApp.getInstance().getString(R.string.st_live_guardian_type_tips_1),
                Integer.valueOf(MyUserInstance.getInstance().getUserConfig().getGuard_price().getYear_price())
                , true));
        list.add(new BaseGuardianBuyInfo(MyApp.getInstance().getString(R.string.st_live_guardian_type_2), "1",
                MyApp.getInstance().getString(R.string.st_live_guardian_type_tips_2),
                Integer.valueOf(MyUserInstance.getInstance().getUserConfig().getGuard_price().getMonth_price())

                , false));
        list.add(new BaseGuardianBuyInfo(MyApp.getInstance().getString(R.string.st_live_guardian_type_3), "2",
                MyApp.getInstance().getString(R.string.st_live_guardian_type_tips_3),
                Integer.valueOf(MyUserInstance.getInstance().getUserConfig().getGuard_price().getWeek_price())

                , false));

        return list;
    }

    public static ArrayList<BaseGuardianTipsInfo> getBaseGuardianTipsInfo() {
        //String name, boolean chose, int res, int respre
        ArrayList<BaseGuardianTipsInfo> list = new ArrayList<>();
        list.add(new BaseGuardianTipsInfo(MyApp.getInstance().getString(R.string.st_live_guardian_type_info_title_1),
                MyApp.getInstance().getString(R.string.st_live_guardian_type_info_1),
                R.drawable.ic_guardian_status
                , false));
        list.add(new BaseGuardianTipsInfo(MyApp.getInstance().getString(R.string.st_live_guardian_type_info_title_1),
                MyApp.getInstance().getString(R.string.st_live_guardian_type_info_2),
                R.drawable.ic_guardian_effetc
                , false));
        list.add(new BaseGuardianTipsInfo(MyApp.getInstance().getString(R.string.st_live_guardian_type_info_title_2),
                MyApp.getInstance().getString(R.string.st_live_guardian_type_info_3),
                R.drawable.ic_guardian_gift
                , false));
        list.add(new BaseGuardianTipsInfo(MyApp.getInstance().getString(R.string.st_live_guardian_type_info_title_3),
                MyApp.getInstance().getString(R.string.st_live_guardian_type_info_4),
                R.drawable.ic_guardian_power
                , false));
        return list;
    }


}
