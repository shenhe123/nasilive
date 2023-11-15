package com.feicui365.live.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.feicui365.live.R;
import com.feicui365.live.bean.MessageData;
import com.feicui365.live.model.entity.StartLive;
import com.feicui365.live.model.entity.UserRegist;
import com.feicui365.live.util.HttpUtils;
import com.lxj.xpopup.core.BottomPopupView;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.util.ArrayList;

public class AnchorRoomMangerDialog extends BottomPopupView implements View.OnClickListener {

    RelativeLayout rl_no_talk, rl_manager, rl_close;
    TextView tv_manager, tv_name;
    StartLive startLive;
    MessageData messageData;


    public void setStartLive(StartLive startLive) {
        this.startLive = startLive;
    }

    public void setMessageData(MessageData messageData) {
        this.messageData = messageData;
    }

    public AnchorRoomMangerDialog(@NonNull Context context, StartLive startLive, MessageData messageData) {
        super(context);
        this.startLive = startLive;
        this.messageData = messageData;

    }


    public interface UpDate {
        void onUpDate(ArrayList<UserRegist> manager_list);
    }


    @Override
    protected int getImplLayoutId() {
        return R.layout.anchor_room_manage;
    }

    @Override
    protected void onCreate() {

        super.onCreate();
        rl_no_talk = findViewById(R.id.rl_no_talk);
        rl_manager = findViewById(R.id.rl_manager);
        rl_close = findViewById(R.id.rl_close);
        tv_manager = findViewById(R.id.tv_manager);
        tv_name = findViewById(R.id.tv_name);
        tv_name.setText(messageData.getChat().getNick_name());
        rl_no_talk.setOnClickListener(this);
        rl_manager.setOnClickListener(this);
        rl_close.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_no_talk:

                HttpUtils.getInstance().banUser(startLive.getAnchorid(), messageData.getChat().getSender().getId(), "1", new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        JSONObject jsonObject = JSON.parseObject(response.body());
                        if (jsonObject.getString("status").equals("0")) {
                            Toast.makeText(getContext(), "禁言成功", Toast.LENGTH_SHORT).show();
                            dismiss();
                        }
                    }
                });
                break;
            case R.id.rl_manager:
                if (messageData.getChat().getNick_name().contains("游客")) {
                    Toast.makeText(getContext(), "当前用户是游客", Toast.LENGTH_SHORT).show();
                    dismiss();
                    return;
                }
                break;

            case R.id.rl_close:
                dismiss();
                break;
        }
    }


}
