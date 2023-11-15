package com.feicui365.live.widget;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.LinearLayout;

import com.lxj.xpopup.core.AttachPopupView;
import com.feicui365.live.R;
import com.feicui365.live.ui.act.PublishTrendActivity;


public class CommentTrendsPopup extends AttachPopupView implements View.OnClickListener {

    Context context;
    LinearLayout ll_1,ll_2,ll_3,ll_4,ll_5;
    //表情结束
    public CommentTrendsPopup(@NonNull Context context) {
        super(context);

        this.context=context;
    }


    @Override
    protected int getImplLayoutId() {
        return R.layout.custom_trends_popup;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        ll_1=findViewById(R.id.ll_1);
        ll_2=findViewById(R.id.ll_2);
        ll_3=findViewById(R.id.ll_3);

        ll_1.setOnClickListener(this);
        ll_2.setOnClickListener(this);
        ll_3.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
       /* if(ArmsUtils.showInputCodeDiaolog((FragmentActivity) context)){
            return;
        }*/
        switch (v.getId()) {
            case R.id.ll_1:

                context. startActivity(new Intent(context, PublishTrendActivity.class).putExtra("FLAG","1"));
                break;
            case  R.id.ll_2:

                context. startActivity(new Intent(context, PublishTrendActivity.class).putExtra("FLAG","2"));


                break;
            case  R.id.ll_3:
                context. startActivity(new Intent(context, PublishTrendActivity.class).putExtra("FLAG","3"));

                break;


        }
    }

    @Override
    public Drawable getPopupBackground() {
        return getResources().getDrawable(R.drawable.shape_corner_white);
    }
}