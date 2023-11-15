package com.feicui365.live.util;

import android.app.Activity;
import android.os.CountDownTimer;
import android.widget.TextView;

import com.feicui365.live.ui.act.LoginActivity;
import com.feicui365.nasinet.utils.AppManager;

public class CountDownUtil2 extends CountDownTimer {
    TextView timeButton;
    FinishListener finishListener;
    Activity context;

    public void setFinishListener(FinishListener finishListener) {
        this.finishListener = finishListener;
    }

    public interface FinishListener {
        void finish();
    }

    public CountDownUtil2(long millisInFuture, long countDownInterval, TextView timeButton) {
        super(millisInFuture, countDownInterval);
        this.timeButton = timeButton;
    }

    public CountDownUtil2(long millisInFuture, long countDownInterval, TextView timeButton, Activity context) {
        super(millisInFuture, countDownInterval);
        this.timeButton = timeButton;
        this.context=context;
    }

    //计时过程
    @Override
    public void onTick(long l) {
        //防止计时过程中重复点击
        timeButton.setClickable(false);
        timeButton.setText(l / 1000 + "秒");
    }

    //计时完毕的方法
    @Override
    public void onFinish() {
        //重新给Button设置文字
        timeButton.setText("进入首页");
        //设置可点击
        // timeButton.setClickable(true);
        AppManager.getAppManager().startActivity(LoginActivity.class);
        context.finish();


    }
}