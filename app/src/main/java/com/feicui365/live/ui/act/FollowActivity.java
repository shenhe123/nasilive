package com.feicui365.live.ui.act;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.feicui365.live.R;
import com.feicui365.live.base.OthrBase2Activity;
import com.feicui365.live.ui.adapter.PalyTabFragmentPagerAdapter;
import com.feicui365.live.ui.fragment.AttentUserFragment;

import java.util.ArrayList;

//关注,粉丝

public class FollowActivity extends OthrBase2Activity implements View.OnClickListener {


    private PalyTabFragmentPagerAdapter adapter;
    private ArrayList list=new ArrayList();
    private TextView tv_follow;
    private View line1;
    private View line2;
    private TextView tv_fans;
    ViewPager myViewPager;
    RelativeLayout rl_back2,rl_follow,rl_fans;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_follow;
    }

    @Override
    protected void initData() {

    }



    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myViewPager = findViewById(R.id.viewPager);
        tv_follow = findViewById(R.id.tv_follow);
        line1 = findViewById(R.id.line1);
        tv_fans = findViewById(R.id.tv_fans);
        line2 = findViewById(R.id.line2);
        rl_back2=findViewById(R.id.rl_back2);
        rl_back2.setOnClickListener(this);
        rl_fans=findViewById(R.id.rl_fans);
        rl_fans.setOnClickListener(this);
        rl_follow=findViewById(R.id.rl_follow);
        rl_follow.setOnClickListener(this);

        hideTitle(true);

        AttentUserFragment followFragment = new AttentUserFragment("1");
        AttentUserFragment fansFragment = new AttentUserFragment("2");
        list.add(followFragment);
        list.add(fansFragment);

        adapter = new PalyTabFragmentPagerAdapter(getSupportFragmentManager(), list);
        myViewPager.setOffscreenPageLimit(list.size());
        myViewPager.setAdapter(adapter);
        myViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                changeTitle(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });



        changeTitle(getIntent().getIntExtra("index",0));

    }

    private void changeTitle(int temp) {
        if (temp==0){
            tv_follow.setTextSize(TypedValue.COMPLEX_UNIT_SP,20);
            line1.setVisibility(View.VISIBLE);
            tv_fans.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);
            line2.setVisibility(View.GONE);
            myViewPager.setCurrentItem(0);

        } else {
            tv_follow.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);
            line1.setVisibility(View.GONE);
            tv_fans.setTextSize(TypedValue.COMPLEX_UNIT_SP,20);
            line2.setVisibility(View.VISIBLE);
            myViewPager.setCurrentItem(1);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_back2:
                finish();
                break;
            case R.id.rl_follow:
                changeTitle(0);
                break;
            case R.id.rl_fans:
                changeTitle(1);
                break;

        }
    }
}
