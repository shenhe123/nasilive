package com.feicui365.live.util;

import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.feicui365.live.base.MyApp;
import com.feicui365.live.ui.act.LoginActivity;
import com.feicui365.nasinet.utils.AppManager;
import com.orhanobut.hawk.Hawk;

public class MyStringCall extends StringCallback {
    @Override
    public void onSuccess(Response<String> response) {
        JSONObject jsonObject = JSON.parseObject(response.body());

        if (jsonObject.getString("status").equals("2")) {
            Log.e("TAG","登录超时");
            Hawk.remove("USER_REGIST");
            AppManager.getAppManager().startActivity(LoginActivity.class);
            AppManager.getAppManager().finishOthersActivity(LoginActivity.class);

            Toast.makeText(MyApp.getInstance(), "您的账号已在其他地方登录", Toast.LENGTH_LONG).show();
        }
    }
}
