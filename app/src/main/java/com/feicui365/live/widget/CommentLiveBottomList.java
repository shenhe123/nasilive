/*
package com.feicui365.live.widget;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lxj.xpopup.core.BottomPopupView;
import com.feicui365.live.R;


import com.feicui365.live.ui.adapter.ShopItemAdapter;
import com.feicui365.live.util.ToastUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.List;


public class CommentLiveBottomList extends BottomPopupView {

    TextView tv_join;
    ImageView iv_message, iv_shop, iv_join, iv_manager,iv_pk;

    BottomClickListener bottomClickListener;
    Context mContext;
    RelativeLayout rl_pk;
    boolean type=true;

    public void setBottomClickListener(BottomClickListener bottomClickListener) {
        this.bottomClickListener = bottomClickListener;
    }

    public interface BottomClickListener{
        void messageClick();
        void shopClick();
        void joinClick();
        void managerClick();
        void startPk();
    }


    //表情结束
    public CommentLiveBottomList(@NonNull Context context,boolean type) {
        super(context);
        mContext=context;
        this.type=type;
    }
    public CommentLiveBottomList(@NonNull Context context) {
        super(context);
        mContext=context;

    }


    @Override
    protected int getImplLayoutId() {
        return R.layout.custom_live_bottom_popup;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        iv_message = findViewById(R.id.iv_message);
        iv_shop = findViewById(R.id.iv_shop);
        iv_join = findViewById(R.id.iv_join);
        iv_manager = findViewById(R.id.iv_manager);
        tv_join=findViewById(R.id.tv_join);
        iv_pk=findViewById(R.id.iv_pk);
        rl_pk=findViewById(R.id.rl_pk);

        iv_message.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bottomClickListener!=null){
                    bottomClickListener.messageClick();
                }
                dismiss();
            }
        });

        iv_shop.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bottomClickListener!=null){
                    bottomClickListener.shopClick();
                }
                dismiss();
            }
        });

        iv_join.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                RxPermissions rxPermissions = new RxPermissions((FragmentActivity) mContext);
                rxPermissions.request(Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO)
                        .subscribe(granted -> {
                            if (granted) { // Always true pre-M
                                if(bottomClickListener!=null){
                                    bottomClickListener.joinClick();
                                }
                                dismiss();
                            } else {
                                // Oups permission denied

                                ToastUtils.showT("用户没有授权相关权限,无法开始连麦");
                            }
                        });


            }
        });
        iv_manager.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bottomClickListener!=null){
                    bottomClickListener.managerClick();
                }
                dismiss();
            }
        });

        iv_pk.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bottomClickListener!=null){
                    bottomClickListener.startPk();
                }
                dismiss();
            }
        });

        if(type){
            rl_pk.setVisibility(GONE);
        }

    }



    public void is_link(boolean is_link){
        if(is_link){
            Glide.with(getContext()).load(R.mipmap.join_anchor_open).into(iv_join);
            tv_join.setText("关闭连麦");
        }else {
            Glide.with(getContext()).load(R.mipmap.join_anchor_off).into(iv_join);
            tv_join.setText("开启连麦");
        }
    }




}*/
