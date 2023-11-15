package com.feicui365.live.widget;


import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.feicui365.live.R;

public class PkProgressBar extends RelativeLayout {
    RelativeLayout rl_root;
    ImageView iv_left, iv_right;
    ProgressBar pb_left, pb_right;
    TextView tv_right, tv_left;
    //进度条最大值
    int max_value = 85;
    Animation animation;
    int rest_left=0;
    int rest_right=0;

    public PkProgressBar(Context context) {
        super(context);

    }

    public PkProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        // 加载布局
        LayoutInflater.from(context).inflate(R.layout.view_pk_progress, this);
        // 获取控件
        rl_root = findViewById(R.id.rl_root);
        iv_left = findViewById(R.id.iv_left);
        iv_right = findViewById(R.id.iv_right);
        pb_left = findViewById(R.id.pb_left);
        pb_right = findViewById(R.id.pb_right);
        tv_right = findViewById(R.id.tv_right);
        tv_left = findViewById(R.id.tv_left);
        animation= AnimationUtils.loadAnimation(getContext(), R.anim.anim_small_to_big);

    }


    public void rest(){
        rest_left=0;
        rest_right=0;
        pb_left.setProgress(0);
        pb_right.setProgress(0);
        tv_right.setText("0");
        tv_left.setText("0");
    }


    public void cpmputerValue(int left, int right) {
        tv_left.setText(String.valueOf(left));
        tv_right.setText(String.valueOf(right));
        if(rest_left!=left){
            tv_left.startAnimation(animation);
        }

        if(rest_right!=right){
            tv_right.startAnimation(animation);
        }


        rest_left=left;
        rest_right=right;

        Double temp_left=(double)left;
        Double temp_right=(double)right;
        Double  result_left=(temp_left/(temp_left+temp_right))*100;
        Double result_right=(temp_right/(temp_left+temp_right))*100;
        setValue(new Double(result_left).intValue(),new Double(result_right).intValue());


    }

    private void setValue(int left, int right) {

        //如果大于
        if (left > right) {
            //并且大于的值大于最大值
            if (left >= max_value) {
                pb_left.setProgress(max_value);
            }else{
                pb_left.setProgress(left);
            }


            pb_left.setVisibility(VISIBLE);
            pb_right.setVisibility(GONE);
            iv_left.setVisibility(GONE);
            iv_right.setVisibility(VISIBLE);
        } else if (left < right) {
            //小于
            if (right >= max_value) {
                pb_right.setProgress(max_value);
            }else{
                pb_right.setProgress(right);
            }


            pb_right.setVisibility(VISIBLE);
            pb_left.setVisibility(GONE);
            iv_left.setVisibility(VISIBLE);
            iv_right.setVisibility(GONE);
        } else if (left == right) {
            //等于
            pb_right.setVisibility(GONE);
            pb_left.setVisibility(GONE);
            iv_left.setVisibility(VISIBLE);
            iv_right.setVisibility(VISIBLE);
        }


    }


}
