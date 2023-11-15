package com.feicui365.live.ui.act;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gyf.immersionbar.ImmersionBar;
import com.feicui365.live.R;
import com.feicui365.live.ui.adapter.ApplyAnchorAdapter;
import com.feicui365.live.ui.fragment.ApplyAnchor1Fragment;
import com.feicui365.live.ui.fragment.ApplyAnchor2Fragment;
import com.feicui365.live.ui.fragment.ApplyAnchor3Fragment;
import com.feicui365.live.widget.NoSrcollViewPager;

import java.util.ArrayList;


public class ApplyAnchorActivity extends FragmentActivity {

    public String id_font="", id_back="", id_all="", user_id="", ali_account="", ali_num="",real_name="";
    ApplyAnchor1Fragment fg_step_1;
    ApplyAnchor2Fragment fg_step_2;
    ApplyAnchor3Fragment fg_step_3;
    NoSrcollViewPager viewPager;
    ArrayList<Fragment> fragments;
    private FragmentSkipInterface mFragmentSkipInterface;
    TextView tv_title;
    ImageView iv_apply_step,iv_back;
    public  int type=0;

    public void setFragmentSkipInterface(FragmentSkipInterface fragmentSkipInterface) {
        mFragmentSkipInterface = fragmentSkipInterface;
    }

    /**
     * Fragment跳转
     */
    public void skipToFragment() {
        if (mFragmentSkipInterface != null) {
            mFragmentSkipInterface.gotoFragment(viewPager);
        }
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.apply_anchor_activity);
        ImmersionBar.with(this).init();
        init();

    }


    private void init() {
     //   fg_step_1 = new ApplyAnchor1Fragment();
        fg_step_2 = new ApplyAnchor2Fragment();
     //   fg_step_3 = new ApplyAnchor3Fragment();
        fragments = new ArrayList<>();
       // fragments.add(fg_step_1);
        fragments.add(fg_step_2);
        //fragments.add(fg_step_3);
        viewPager = findViewById(R.id.vp_apply_anchor);
        tv_title = findViewById(R.id.tv_title);
        iv_apply_step = findViewById(R.id.iv_apply_step);
        if (getIntent().getIntExtra("TYPE",0)==1) {
            type=1;
            tv_title.setText("申请成为主播");
        }
        if (getIntent().getIntExtra("TYPE",0)==2) {
            type=2;
            tv_title.setText("实名认证");

        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        viewPager.setAdapter(new ApplyAnchorAdapter(fragmentManager, fragments));
//        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//
//                switch (position) {
//                    case 0:
//                        iv_apply_step.setImageDrawable(getResources().getDrawable(R.mipmap.apply_1));
//                        break;
//                    case 1:
//                        iv_apply_step.setImageDrawable(getResources().getDrawable(R.mipmap.apply_2));
//                        break;
//                    case 2:
//                        iv_apply_step.setImageDrawable(getResources().getDrawable(R.mipmap.apply_3));
//                        break;
//                }
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });

        iv_back=findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public interface FragmentSkipInterface {
        void gotoFragment(NoSrcollViewPager viewPager);
    }
}
