package com.feicui365.live.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import com.feicui365.live.R;
import com.feicui365.live.model.entity.Trend;

import de.hdodenhof.circleimageview.CircleImageView;

public class UnlockDialog extends Dialog {


    private ImageView close_iv;
    private TextView content_tv,price_tv;
    CircleImageView avatar_iv;
    Trend my_trend;
    UnLockListener unLockListener;
    @SuppressLint("PrivateResource")
    public UnlockDialog(@NonNull Context context, Trend my_trend) {
        super(context, R.style.Base_Theme_AppCompat_Dialog);
        this.my_trend=my_trend;
        initView();
    }


    public interface UnLockListener{
        void unLockTrend(String id);
    }

    public void setUnLockListener(UnLockListener unLockListener) {
        this.unLockListener = unLockListener;
    }

    private void initView() {
        View rootView = View.inflate(getContext(), R.layout.unlock_dialog, null);
        setContentView(rootView);

        close_iv=findViewById(R.id.close_iv);
        content_tv=findViewById(R.id.content_tv);
        price_tv=findViewById(R.id.price_tv);
        avatar_iv=findViewById(R.id.avatar_iv);
        Glide.with(getContext()).load(my_trend.getUser().getAvatar()).into(avatar_iv);
        content_tv.setText("解锁" + my_trend.getUser().getNick_name() +"的私密动态");
        price_tv.setText("解锁此动态需要"+ my_trend.getUnlock_price() + "金币");
        close_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        price_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(unLockListener!=null){
                    unLockListener.unLockTrend(my_trend.getId());
                    dismiss();
                }
            }
        });



        Window window = getWindow();
        if (window != null) {
            window.setBackgroundDrawable(new ColorDrawable(Color.argb(0, 0, 0, 0)));
            WindowManager.LayoutParams params = window.getAttributes();
            params.gravity = Gravity.CENTER_HORIZONTAL;
            //设置dialog宽度充满屏幕
            window.getDecorView().setPadding(0, 0, 0, 0);
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            window.setAttributes(params);
        }
    }



}