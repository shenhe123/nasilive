package com.feicui365.live.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.method.ScrollingMovementMethod;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;



import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.feicui365.live.R;

import com.feicui365.live.ui.act.WebViewActivity;
import com.feicui365.live.model.entity.HomeAd;

public class HomeDialog extends Dialog {


    private ImageView iv_tumb,iv_no_word,iv_close;
    private TextView tv_content,iv_go;
    RelativeLayout rl_close;
    HomeAd homeAd;
    RoundedCorners roundedCorners= new RoundedCorners(10);
    RequestOptions options= RequestOptions.bitmapTransform(roundedCorners) ;
    @SuppressLint("PrivateResource")
    public HomeDialog(@NonNull Context context, HomeAd homeAd) {
        super(context, R.style.Base_Theme_AppCompat_Dialog);
        this.homeAd=homeAd;
        initView();


    }

    private void initView() {
        View rootView = View.inflate(getContext(), R.layout.home_dialog, null);
        setContentView(rootView);
        iv_tumb=rootView.findViewById(R.id.iv_tumb);
        Glide.with(getContext()).applyDefaultRequestOptions(options.placeholder(0)).load(homeAd.getImage_url()).into(iv_tumb);
        iv_close=rootView.findViewById(R.id.iv_close);
        tv_content=rootView.findViewById(R.id.tv_content);
        tv_content.setText(homeAd.getTitle());
        tv_content.setMovementMethod(ScrollingMovementMethod.getInstance());
        iv_go=rootView.findViewById(R.id.iv_go);
        iv_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (homeAd.getJump_type()) {
                    case "1":
                        Intent i = new Intent(getContext(), WebViewActivity.class);
                        i.putExtra("jump_url", (homeAd.getJump_url()));
                        i.putExtra("title", "");
                        getContext().startActivity(i);
                        break;
                    case "2":
                        Intent intent = new Intent();
                        intent.setAction("android.intent.action.VIEW");
                        Uri content_url = Uri.parse(homeAd.getJump_url());
                        intent.setData(content_url);
                        getContext().startActivity(intent);
                        break;
                }
            }
        });




        iv_no_word=rootView.findViewById(R.id.iv_no_word);
        rl_close=rootView.findViewById(R.id.rl_close);
        if(homeAd.getImage_url()==null){
            iv_no_word.setVisibility(View.VISIBLE);

        }else
        if(homeAd.getImage_url().equals("")){
            iv_no_word.setVisibility(View.VISIBLE);

        }else {
            iv_no_word.setVisibility(View.GONE);
            iv_close.setVisibility(View.VISIBLE);
            rl_close.setVisibility(View.GONE);
            iv_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
        }

        rl_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


        Window window = getWindow();
        if (window != null) {
            window.setBackgroundDrawable(new ColorDrawable(Color.argb(0, 0, 0, 0)));
            WindowManager.LayoutParams params = window.getAttributes();
            params.gravity = Gravity.CENTER_HORIZONTAL;
            window.getDecorView().setPadding(0, 0, 0, 0);
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            window.setAttributes(params);
        }
    }





}