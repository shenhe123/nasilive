package com.feicui365.live.util;

import android.view.Gravity;
import android.widget.Toast;

import com.feicui365.live.base.MyApp;

public class ToastUtils {


    public static void showT(String text) {
        Toast mToast = Toast.makeText(MyApp.getInstance(), null, Toast.LENGTH_SHORT);
        mToast.setGravity(Gravity.CENTER, 0, 0);

        mToast.setText(text);
        mToast.show();
    }


    public static void showT(int text) {
        Toast mToast = Toast.makeText(MyApp.getInstance(), null, Toast.LENGTH_SHORT);
        mToast.setGravity(Gravity.CENTER, 0, 0);

        mToast.setText(MyApp.getInstance().getResources().getString(text));
        mToast.show();
    }
}
