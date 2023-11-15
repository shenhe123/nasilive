package com.feicui365.nasinet.userconfig;

import android.content.Context;
import android.content.DialogInterface;
import android.icu.text.DecimalFormat;
import android.support.v7.app.AlertDialog;


import com.feicui365.nasinet.R;
import com.feicui365.nasinet.base.NasiSDK;

import com.feicui365.nasinet.utils.AppManager;



import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UserInstance {

 public static UserInstance instance;
    public AlertDialog.Builder builder;


    public static UserInstance getInstance() {
        synchronized (UserInstance.class) {
            if (null == instance) {
                instance = new UserInstance();
                return instance;
            } else {
                return instance;
            }
        }
    }
    public static String ToDBC(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i< c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }if (c[i]> 65280&& c[i]< 65375)
                c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }

    public void initDialog(Context context, final Class c) {
        builder = new AlertDialog.Builder(context).setTitle("当前账户尚未登录")
                .setMessage("请登录使用完整功能.").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //ToDo: 你想做的事情
                        AppManager.getAppManager().startActivity(c);
                        AppManager.getAppManager().finishOthersActivity(c);
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.create().show();
    }

    public int getVipLevel(String lv) {
        if(!NasiSDK.isIs_check()){
            NasiSDK.getInstance().finish();
        }
        int temp = 0;
        switch (lv) {
            case "0":
                temp = 0;
                break;
            case "1":
                temp = R.mipmap.youxia;

                break;
            case "2":
                temp = R.mipmap.qishi;

                break;
            case "3":
                temp = R.mipmap.gongjue;

                break;
            case "4":
                temp = R.mipmap.king;

                break;
        }


        return temp;
    }


    public String getVipLevelName(String lv) {
        if(!NasiSDK.isIs_check()){
            NasiSDK.getInstance().finish();
        }
        String temp = "";
        switch (lv) {
            case "0":
                temp = "";
                break;
            case "1":
                temp = "游侠";

                break;
            case "2":
                temp = "骑士";

                break;
            case "3":
                temp = "公爵";

                break;
            case "4":
                temp = "国王";

                break;
        }


        return temp;
    }

    public int getLevel(String lv) {
        if(!NasiSDK.isIs_check()){
            NasiSDK.getInstance().finish();
        }
        int temp = 0;
        switch (lv) {
            case "1":
                temp = R.mipmap.lv1;

                break;
            case "2":
                temp = R.mipmap.lv2;

                break;
            case "3":
                temp = R.mipmap.lv3;

                break;
            case "4":
                temp = R.mipmap.lv4;

                break;
            case "5":
                temp = R.mipmap.lv5;

                break;
            case "6":
                temp = R.mipmap.lv6;

                break;
            case "7":
                temp = R.mipmap.lv7;

                break;
            case "8":
                temp = R.mipmap.lv8;

                break;
            case "9":
                temp = R.mipmap.lv9;

                break;
            case "10":
                temp = R.mipmap.lv10;

                break;
            case "11":
                temp = R.mipmap.lv11;

                break;
            case "12":
                temp = R.mipmap.lv12;

                break;
            case "13":
                temp = R.mipmap.lv13;

                break;
            case "14":
                temp = R.mipmap.lv14;

                break;
            case "15":
                temp = R.mipmap.lv15;

                break;
            case "16":
                temp = R.mipmap.lv16;

                break;
            case "17":
                temp = R.mipmap.lv17;

                break;
            case "18":
                temp = R.mipmap.lv18;

                break;
            case "19":
                temp = R.mipmap.lv19;

                break;
            case "20":
                temp = R.mipmap.lv20;

                break;

        }

        return temp;

    }

    public int getAnchorLevel(String lv) {
        if(!NasiSDK.isIs_check()){
            NasiSDK.getInstance().finish();
        }
        int temp = 0;
        switch (lv) {
            case "1":
                temp = R.mipmap.anchor_1;

                break;
            case "2":
                temp = R.mipmap.anchor_2;

                break;
            case "3":
                temp = R.mipmap.anchor_3;

                break;
            case "20":
                temp = R.mipmap.anchor_20;

                break;
            case "4":
                temp = R.mipmap.anchor_4;

                break;
            case "5":
                temp = R.mipmap.anchor_5;

                break;
            case "6":
                temp = R.mipmap.anchor_6;

                break;
            case "7":
                temp = R.mipmap.anchor_7;

                break;
            case "8":
                temp = R.mipmap.anchor_8;

                break;
            case "9":
                temp = R.mipmap.anchor_9;

                break;
            case "10":
                temp = R.mipmap.anchor_10;

                break;
            case "11":
                temp = R.mipmap.anchor_11;

                break;
            case "12":
                temp = R.mipmap.anchor_12;

                break;
            case "13":
                temp = R.mipmap.anchor_13;

                break;
            case "14":
                temp = R.mipmap.anchor_14;

                break;
            case "15":
                temp = R.mipmap.anchor_15;

                break;
            case "16":
                temp = R.mipmap.anchor_16;

                break;
            case "17":
                temp = R.mipmap.anchor_17;

                break;
            case "18":
                temp = R.mipmap.anchor_18;

                break;
            case "19":
                temp = R.mipmap.anchor_19;

                break;

        }

        return temp;

    }






    public boolean OverTime(String time) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date mDate = sdf.parse(time);
            long into_time = mDate.getTime();
            long now_time = System.currentTimeMillis();
            if (into_time>now_time) {
                return true;
            }else {
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return false;
    }


    public String getNum(Float f) {
        String data = "";
        DecimalFormat format = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            format = new DecimalFormat(".0");
        }
        data=format.format(f);
        return data;
    }
}
