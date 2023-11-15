package com.feicui365.live.ui.act;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.feicui365.live.R;
import com.feicui365.live.util.MyUserInstance;
import com.feicui365.live.widget.PkProgressBar;

import java.io.IOException;
import java.util.Random;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public class TestActivity extends Activity {
    PkProgressBar pkProgressBar;
    Button bt_go, bt_back;
    int i_1 = 0;
    int i_2 = 0;
    EditText et_1, et_2;
    GifImageView gif_ok_status;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    int random = new Random().nextInt(10000);
                    int random_2 = new Random().nextInt(10000);
                    pkProgressBar.cpmputerValue(random, random_2);
                    android.os.Message message1 = new android.os.Message();
                    message1.what = 1;
                    sendMessageDelayed(message1, 100);

                    break;

                case 2:

                    pkProgressBar.cpmputerValue(Integer.parseInt(et_1.getText().toString()), Integer.parseInt(et_2.getText().toString()));


                    break;
            }
        }
    };
    CountDownTimer pk_time;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_activity);
        pkProgressBar = findViewById(R.id.pkProgressBar);


        bt_go = findViewById(R.id.bt_go);
        bt_back = findViewById(R.id.bt_back);

        bt_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {

                        android.os.Message message1 = new android.os.Message();
                        message1.what = 1;
                        handler.sendMessageDelayed(message1, 500);
                    }
                });
            }
        });
        gif_ok_status=findViewById(R.id.gif_ok_status);
        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            /*    handler.removeMessages(1);
                android.os.Message message2 = new android.os.Message();
                message2.what = 2;
                handler.sendMessage(message2);*/
                if (pk_time != null) {
                    pk_time.cancel();
                }
                pk_time = new CountDownTimer(MyUserInstance.getInstance().computerTime("2020-12-14 10:08:01"), 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        String value = String.valueOf((int) (millisUntilFinished / 1000));
                        Log.e("onTick", value);
                        try {
                            et_2.setText(MyUserInstance.getInstance().getTime((int) (millisUntilFinished / 1000)));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFinish() {
                        et_2.setText("倒计时结束");
                    }
                };
                pk_time.start();
            }
        });
        et_1 = findViewById(R.id.et_1);
        et_2 = findViewById(R.id.et_2);

        GifDrawable gifDrawable = null;
        try {
            gifDrawable = new GifDrawable(getAssets(),"pk_finding.gif");
            gif_ok_status.setImageDrawable(gifDrawable);
        } catch (IOException e) {
            e.printStackTrace();
        }



    }


}
