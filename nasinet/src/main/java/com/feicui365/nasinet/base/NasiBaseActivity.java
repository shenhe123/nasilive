package com.feicui365.nasinet.base;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.feicui365.nasinet.R;
import com.feicui365.nasinet.userconfig.UserInstance;
import com.feicui365.nasinet.utils.DipPxUtils;
import com.feicui365.nasinet.utils.ScreenUtil;


import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;


public abstract class NasiBaseActivity extends AppCompatActivity {

    public TextView tv_title, tv_other;
    public RelativeLayout rl_back;
    public RelativeLayout rl_title;
    protected View rootView;

    private Unbinder unbinder;
    public LinearLayout root_view;
    public Context context;
    private boolean is_front = false;
    public ImageView iv_other;
    public int live_chat_h = 0;


    @Override
    protected void onPause() {
        super.onPause();
        is_front = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        is_front = true;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        context = this;
        setContentView(getLayoutId());
        rootView = View.inflate(this, R.layout.base_activity, null);
        addContent();
        setContentView(rootView);
        unbinder = ButterKnife.bind(this);

        initView();
        initData();
        if (!getRunningActivityName().contains("Splash")) {
            if (!NasiSDK.isIs_check()) {
                NasiSDK.getInstance().finish();
            }
        }

    }

    /**
     * 获取布局ID
     *
     * @return
     */
    protected abstract int getLayoutId();

    protected abstract void initData();

    protected abstract void initView();

    public void addContent() {
        root_view = rootView.findViewById(R.id.root_view);
        tv_title = rootView.findViewById(R.id.tv_title);
        rl_back = rootView.findViewById(R.id.rl_back);
        rl_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        rl_title = rootView.findViewById(R.id.rl_title);
        tv_other = rootView.findViewById(R.id.tv_other);
        iv_other = rootView.findViewById(R.id.iv_other);
        FrameLayout flContent = (FrameLayout) rootView.findViewById(R.id.fl_content);
        View content = View.inflate(this, getLayoutId(), null);
        if (content != null) {
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.MATCH_PARENT);
            flContent.addView(content, params);

        }

    }


    protected void getMoveViewGroup(JSONObject jsonObject) {

        if (this.isFinishing()) {
            return;
        }
        if (!is_front) {
            return;
        }

        //获取TYPE,1,表示购买VIP信息,2表示全频道广播信息
        JSONObject data = jsonObject.getJSONObject("Data").getJSONObject("streamer");
        int type = data.getIntValue("type");


        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> runningTasks = manager.getRunningTasks(1);
        ActivityManager.RunningTaskInfo cinfo = runningTasks.get(0);
        ComponentName component = cinfo.topActivity;
        String[] temp = component.getClassName().split("\\.");

        if (this.toString().contains(temp[temp.length - 1])) {


            switch (type) {

                case 1:
                    RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ScreenUtil.dp2px(this, 240), ScreenUtil.dp2px(this, 80));
                    lp.setMargins(ScreenUtil.dp2px(this, 10), ScreenUtil.dp2px(this, 120), 0, 0);
                    if (this.toString().contains("SuperPlayer") || this.toString().contains("LivePushActivity")) {
                        return;
                    }
                    final View rl_buy_vip = LayoutInflater.from(this).inflate(R.layout.item_broadcast_buy_member, null);
                    Glide.with(this).load(data.getJSONObject("user").getString("avatar")).into((CircleImageView) rl_buy_vip.findViewById(R.id.civ_avatar));
                    ((TextView) rl_buy_vip.findViewById(R.id.tv_name)).setText(data.getJSONObject("user").getString("nick_name"));
                    Glide.with(this).applyDefaultRequestOptions(new RequestOptions().centerInside()).load(UserInstance.getInstance().getVipLevel(data.getJSONObject("vip").getString("level"))).into((ImageView) rl_buy_vip.findViewById(R.id.iv_vip_type));

                    ((TextView) rl_buy_vip.findViewById(R.id.tv_vip_name)).setText(UserInstance.getInstance().getVipLevelName(data.getJSONObject("vip").getString("level")));

                    addContentView(rl_buy_vip, lp);

                    Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.left_anim_enter);
                    myAnim.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    rl_buy_vip.setVisibility(View.GONE);
                                }
                            }, 1500);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                    rl_buy_vip.startAnimation(myAnim);
                    break;
                case 2:
                    RelativeLayout.LayoutParams lp_all = new RelativeLayout.LayoutParams(ScreenUtil.dp2px(this, 290), ScreenUtil.dp2px(this, 50));

                    lp_all.setMargins(ScreenUtil.dp2px(this, 10), ScreenUtil.dp2px(this, 120), 0, 0);


                    final View rl_all_gift = LayoutInflater.from(this).inflate(R.layout.item_broadcast_all_gift, null);
                    Glide.with(this).load(data.getJSONObject("user").getString("avatar")).into((CircleImageView) rl_all_gift.findViewById(R.id.civ_avatar));
                    ((TextView) rl_all_gift.findViewById(R.id.tv_name)).setText(data.getJSONObject("user").getString("nick_name"));
                    ((TextView) rl_all_gift.findViewById(R.id.tv_anchor_name)).setText(data.getJSONObject("anchor").getString("nick_name"));
                    ((TextView) rl_all_gift.findViewById(R.id.tv_gift_name)).setText(data.getJSONObject("gift").getString("title"));
                    addContentView(rl_all_gift, lp_all);

                    Animation myAnim_all_gift = AnimationUtils.loadAnimation(this, R.anim.vip_anim);
                    myAnim_all_gift.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            if (!NasiBaseActivity.this.isFinishing()) {
                                rl_all_gift.setVisibility(View.GONE);

                            }


                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                    rl_all_gift.startAnimation(myAnim_all_gift);
                    break;

            }

        }
    }

    public void setTitleBackroundColor(int color) {
        rl_title.setBackgroundColor(color);
    }

    public void setTitle(String title) {
        tv_title.setText(title);
    }

    public void hideTitle(boolean hide) {
        if (hide) {
            rl_title.setVisibility(View.GONE);
        }
    }


    public void hideOther(boolean hide) {
        if (hide) {
            tv_other.setVisibility(View.GONE);
        } else {
            tv_other.setVisibility(View.VISIBLE);
        }
    }


    public void setOther(String title) {
        tv_other.setText(title);
    }


    public TextView getOther() {
        return tv_other;
    }

    public void setOtherLayout(int width, int height) {


        tv_other.getLayoutParams().width = DipPxUtils.dip2px(this, width);
        tv_other.getLayoutParams().height = DipPxUtils.dip2px(this, height);
    }

    public void setOtherTextColor(int color) {

        tv_other.setTextColor(getResources().getColor(color));

    }

    public void setOtherTextSize(int size) {

        tv_other.setTextSize(size);

    }


    public void setOtherBackRound(int layoutId) {

        tv_other.setBackground(getResources().getDrawable(layoutId));

    }


    @Override
    protected void onDestroy() {
        unbinder.unbind();

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);

        super.onDestroy();
    }


    public String getRunningActivityName() {
        String contextString = this.toString();
        return contextString.substring(contextString.lastIndexOf(".") + 1, contextString.indexOf("@"));
    }

    public void showImageOther(int w, int h, int res) {
        if (iv_other == null) {
            return;
        }

        iv_other.setImageResource(res);
        iv_other.getLayoutParams().height = DipPxUtils.dip2px(this, h);
        iv_other.getLayoutParams().width = DipPxUtils.dip2px(this, w);
        iv_other.setVisibility(View.VISIBLE);


    }


    @Override
    public Resources getResources() {
        Resources resources = super.getResources();
        Configuration configuration = new Configuration();
        configuration.setToDefaults();
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        return resources;
    }
}
