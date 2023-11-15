package com.feicui365.live.shop.custom;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lxj.xpopup.core.BottomPopupView;
import com.feicui365.live.R;

public class BottomServiceDialog extends BottomPopupView {

    private TextView tv_submit;
    private ImageView iv_close2;

    public BottomServiceDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.bottom_service_dialog;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        tv_submit=findViewById(R.id.tv_submit);
        iv_close2=findViewById(R.id.iv_close2);

        tv_submit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        iv_close2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}
