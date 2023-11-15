package com.feicui365.nasinet.base;

import android.app.Application;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

public class NasiSDK {

    private static boolean is_check = true;
    private static NasiSDK instance;
    private String url = "https://open.nasinet.com/api/identify/identify";

    public static NasiSDK getInstance() {
        synchronized (NasiSDK.class) {
            if (null == instance) {
                instance = new NasiSDK();
                return instance;
            } else {

                return instance;

            }
        }
    }

    public static boolean isIs_check() {
        return is_check;
    }

    public void init(String bundleid, String access_key, String secret_key, final Application application) {
        OkGo.<String>post(url)
                .tag(this)
                .params("bundleid", bundleid)
                .params("access_key", access_key)
                .params("secret_key", secret_key)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        JSONObject data = JSON.parseObject(response.body());
                        if (data.get("status").toString().equals("0")) {
                            is_check = true;
                        } else {
                            finish();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        finish();
                    }
                });
    }

    public void finish() {
        String a = null;
        a.split("/");
    }

}
