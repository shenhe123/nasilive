package com.feicui365.live.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.LinearLayout;

import com.lxj.xpopup.core.BottomPopupView;
import com.feicui365.live.R;


public class CommentZhifu extends BottomPopupView implements View.OnClickListener {

    LinearLayout ll_1,ll_2,ll_3;


    Context context;
   public ToPayListener toPayListener;
    public interface ToPayListener{
        void ali();
        void creditcard();
    }

    public void setToPayListener(ToPayListener toPayListener) {
        this.toPayListener = toPayListener;
    }

    //表情结束
    public CommentZhifu(@NonNull Context context) {
        super(context);

        this.context=context;
    }


    @Override
    protected int getImplLayoutId() {
        return R.layout.custom_pay_popup;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        ll_1=findViewById(R.id.ll_1);
        ll_2=findViewById(R.id.ll_2);
        ll_3=findViewById(R.id.ll_3);

        ll_3.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        ll_1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(toPayListener!=null){
                    toPayListener.ali();
                }
            }
        });

        ll_2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(toPayListener!=null){
                    toPayListener.creditcard();
                }
            }
        });
    }


    @Override
    public void onClick(View v) {

    }


}