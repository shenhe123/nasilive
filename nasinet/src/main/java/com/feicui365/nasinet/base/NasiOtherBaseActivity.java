package com.feicui365.nasinet.base;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.feicui365.nasinet.R;
import com.feicui365.nasinet.userconfig.UserInstance;
import com.feicui365.nasinet.utils.ScreenUtil;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public abstract class NasiOtherBaseActivity extends FragmentActivity {


    private boolean is_front=false;


    @Override
    protected void onPause() {
        super.onPause();
        is_front=false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        is_front=true;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




        if( !getRunningActivityName().contains("Splash")){
            if(!NasiSDK.isIs_check()){
                NasiSDK.getInstance().finish();
            }
        }
    }



    protected void getMoveViewGroup(JSONObject jsonObject) {
        Log.e("getMoveViewGroup",this.toString()+"       "+is_front);
        if(this.isFinishing()){
            return;
        }
        if(!is_front){
            return;
        }
        JSONObject data = jsonObject.getJSONObject("Data").getJSONObject("streamer");
        int type=data.getIntValue("type");


        Log.e("getMoveViewGroup2",this.toString()+"       "+is_front);
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> runningTasks;
        runningTasks = manager.getRunningTasks(1);
        ActivityManager.RunningTaskInfo cinfo = runningTasks.get(0);
        ComponentName component = cinfo.topActivity;
        String[] temp = component.getClassName().split("\\.");

        if (this.toString().contains(temp[temp.length - 1])) {
            switch (type){

                case 1:
                    RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ScreenUtil.dp2px(this, 240), ScreenUtil.dp2px(this, 80));
                    lp.setMargins(0, 400, 0, 0);
                    if(this.toString().contains("SuperPlayer")||this.toString().contains("LivePushActivity")){
                        return;
                    }
                    final View rl_buy_vip = LayoutInflater.from(this).inflate(R.layout.item_broadcast_buy_member, null);
                    Glide.with(this).load(data.getJSONObject("user").getString("avatar")).into((CircleImageView) rl_buy_vip.findViewById(R.id.civ_avatar));
                    ((TextView) rl_buy_vip.findViewById(R.id.tv_name)).setText(data.getJSONObject("user").getString("nick_name"));
                    Glide.with(this).load(UserInstance.getInstance().getVipLevel(data.getJSONObject("vip").getString("level"))).into((ImageView) rl_buy_vip.findViewById(R.id.iv_vip_type));

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
                            },1500);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                    rl_buy_vip.startAnimation(myAnim);
                    break;
                case 2:
                    RelativeLayout.LayoutParams lp_all = new RelativeLayout.LayoutParams(ScreenUtil.dp2px(this, 290), ScreenUtil.dp2px(this, 50));
                    if(this.toString().contains("SuperPlayer")||this.toString().contains("LivePushActivity")){
                        lp_all.setMargins(0, 200, 0, 0);
                    }else{
                        lp_all.setMargins(0, 400, 0, 0);
                    }


                    final View rl_all_gift = LayoutInflater.from(this).inflate(R.layout.item_broadcast_all_gift, null);
                    Glide.with(this).load(data.getJSONObject("user").getString("avatar")).into((CircleImageView) rl_all_gift.findViewById(R.id.civ_avatar));
                    ((TextView)rl_all_gift.findViewById(R.id.tv_name)).setText(data.getJSONObject("user").getString("nick_name"));
                    ((TextView)rl_all_gift.findViewById(R.id.tv_anchor_name)).setText(data.getJSONObject("anchor").getString("nick_name"));
                    ((TextView)rl_all_gift.findViewById(R.id.tv_gift_name)).setText(data.getJSONObject("gift").getString("title"));
                    addContentView(rl_all_gift, lp_all);

                    Animation myAnim_all_gift = AnimationUtils.loadAnimation(this, R.anim.vip_anim);
                    myAnim_all_gift.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            if (!NasiOtherBaseActivity.this.isFinishing()) {
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

    public String getRunningActivityName() {
        String contextString = this.toString();
        return contextString.substring(contextString.lastIndexOf(".") + 1, contextString.indexOf("@"));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
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
