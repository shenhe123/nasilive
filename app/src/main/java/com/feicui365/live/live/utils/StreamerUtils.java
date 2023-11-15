package com.feicui365.live.live.utils;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import android.support.annotation.NonNull;


import com.feicui365.nasinet.utils.AppManager;

import com.feicui365.live.R;
import com.feicui365.live.base.BaseMvpActivity;
import com.feicui365.live.base.MyApp;
import com.feicui365.live.bean.ImMessage;
import com.feicui365.live.live.bean.Streamer;
import com.feicui365.live.model.entity.UserRegist;

import java.util.concurrent.ConcurrentLinkedQueue;


/**
 * 横幅工具
 */
public class StreamerUtils {

    private ConcurrentLinkedQueue<ImMessage> mQueue;
    private ConcurrentLinkedQueue<ImMessage> mVipQueue;
    private boolean isAnimate = false;
    private boolean isVipAnimate = false;

    private final int ANIMATE_FINISH_NORMAL = 991;


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case ANIMATE_FINISH_NORMAL:
                    //如果当前activity已经销毁不做处理,执行下一个动画,如果没有销毁,删除对应的飘屏横幅
                    isAnimate = false;

                    ImMessage messageBean = mQueue.poll();
                    if (messageBean != null) {
                        sendStreamer(messageBean);
                    }

                    break;


            }


        }
    };

    //初始化横幅跟VIP横幅的队列
    public StreamerUtils() {
        mQueue = new ConcurrentLinkedQueue<>();
        mVipQueue = new ConcurrentLinkedQueue<>();
    }


    /*
     * 注释
     * 添加到队列菜单,如果当前是动画中只添加,没有动画在播放直接播放
     * */

    public void addTolist(ImMessage messageBean) {
        mQueue.offer(messageBean);
        if (!isAnimate) {
            ImMessage mbNext = mQueue.poll();
            if (mbNext != null) {
                sendStreamer(mbNext);
            }
        }
    }

    public void addToViplist(ImMessage messageBean, Activity activity) {
        mVipQueue.offer(messageBean);
        if (!isVipAnimate) {
            ImMessage mbNext = mVipQueue.poll();
            if (mbNext != null) {
                sendVipStreamer(mbNext, activity);
            }
        }
    }


    /*
     * 注释
     * 发送横幅
     * 获取当前顶层Activity,如果是空,返回
     * 如果已经结束,走下一个
     * */
    public void sendStreamer(ImMessage messageBean) {
        if(messageBean.getData()==null){
            return;
        }
        if(messageBean.getData().getStreamer()==null){
            return;
        }
        //这是当前顶层activity
        BaseMvpActivity topActivity = (BaseMvpActivity) AppManager.getAppManager().getTopActivity();
        if (topActivity == null) {
            return;
        }

        FrameLayout view = (FrameLayout) topActivity.getWindow().getDecorView();

        if (topActivity.isFinishing()) {
            Message message = new Message();
            message.what = ANIMATE_FINISH_NORMAL;
            handler.sendMessage(message);
            return;
        }
        if (topActivity.isFinishing()) {
            return;
        }
        topActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (!isAnimate) {
                    isAnimate = true;
                    Animation mStreamerAnimation = AnimationUtils.loadAnimation(topActivity, R.anim.vip_anim);
                    //横幅类型 1-VIP 2-全频道礼物
                    Streamer streamer=messageBean.getData().getStreamer();

                    switch (streamer.getType()) {

                        case 1:
                            UserRegist sender = messageBean.getData().getChat().getSender();
                            if (sender == null) {
                                return;
                            }
                            //vip
                            int vip = ArmsUtils.getVipLevel2(sender.getVip_level(), sender.getVip_date());


                            if (vip == 0) {
                                //啥都不是
                                return;
                            } else {


                                RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ArmsUtils.dip2px(topActivity, 250), ArmsUtils.dip2px(topActivity, 40));
                                lp.setMargins(ArmsUtils.dip2px(topActivity, 10), 300, 0, 0);
                                View vipAnimateView = LayoutInflater.from(topActivity).inflate(R.layout.item_streamer_vip_in, null);
                                isVipAnimate = true;
                                ((TextView) vipAnimateView.findViewById(R.id.tv_user_name)).setText(sender.getNick_name());
                                //是VIP不是守护

                                vipAnimateView.findViewById(R.id.rl_root).setBackgroundResource(ArmsUtils.getVipStreamerBG(sender.getVip_level()));

                                ((TextView) vipAnimateView.findViewById(R.id.tv_vip_name)).setTextColor(MyApp.getInstance().getResources().getColor(ArmsUtils.getVipNameColor(sender.getVip_level())));
                                ((TextView) vipAnimateView.findViewById(R.id.tv_vip_name)).setText(ArmsUtils.getVipNameText(sender.getVip_level()));
                                ((ImageView) vipAnimateView.findViewById(R.id.iv_vip_level))
                                        .setImageResource(ArmsUtils.getVipLevelIcon(sender.getVip_level(), sender.getVip_date()));


                                view.addView(vipAnimateView, lp);
                                Animation vipInAnimation = AnimationUtils.loadAnimation(topActivity, R.anim.vip_anim);
                                vipAnimateView.startAnimation(vipInAnimation);

                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        isVipAnimate = false;
                                        if (!topActivity.isFinishing()) {
                                            vipAnimateView.setVisibility(View.GONE);
                                            ((ViewGroup) vipAnimateView.getParent()).removeView(vipAnimateView);

                                        }
                                        ImMessage mbNext = mVipQueue.poll();
                                        if (mbNext != null) {
                                            sendVipStreamer(mbNext, topActivity);
                                        }
                                    }
                                }, 4000);
                            }
                            break;
                        case 2:

                            UserRegist mUserGift = streamer.getUser();
                            if (mUserGift == null) {
                                return;
                            }
                            RelativeLayout.LayoutParams layoutParamsAll
                                    = new RelativeLayout.LayoutParams(ArmsUtils.dip2px(topActivity, 320), ArmsUtils.dip2px(topActivity, 50));

                            layoutParamsAll.setMargins(ArmsUtils.dip2px(topActivity, 10), ArmsUtils.dip2px(topActivity, 120), 0, 0);


                            final View viewAllGift = LayoutInflater.from(topActivity).inflate(R.layout.item_broadcast_all_gift, null);
                            if (ArmsUtils.isStringEmpty(mUserGift.getAvatar())) {
                                GlideUtils.setImage(topActivity,
                                        mUserGift.getAvatar()
                                        , R.mipmap.moren
                                        , viewAllGift.findViewById(R.id.civ_avatar));
                            } else {
                                GlideUtils.setImage(topActivity,
                                        R.mipmap.moren
                                        , viewAllGift.findViewById(R.id.civ_avatar));
                            }


                            ((TextView) viewAllGift.findViewById(R.id.tv_name)).setText(mUserGift.getNick_name());
                            ((TextView) viewAllGift.findViewById(R.id.tv_anchor_name)).setText(streamer.getAnchor().getNick_name());
                            ((TextView) viewAllGift.findViewById(R.id.tv_gift_name)).setText(streamer.getGift().getTitle());

                            view.addView(viewAllGift, layoutParamsAll);


                            viewAllGift.startAnimation(mStreamerAnimation);
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    isAnimate = false;
                                    if (!topActivity.isFinishing()) {
                                        viewAllGift.setVisibility(View.GONE);
                                        view.removeView(viewAllGift);
                                    }
                                    ImMessage mbNext = mQueue.poll();
                                    if (mbNext != null) {
                                        sendStreamer(mbNext);
                                    }
                                }
                            }, 4000);


                            break;

                    }

                }
            }
        });


    }

    /*
     * 注释
     * 发送VIP进场横幅
     * 获取当前顶层Activity,如果是空,返回
     * 如果已经结束,走下一个
     * VIP需要区别是否是守护,VIP等级,以及是否过期
     * */
    public void sendVipStreamer(ImMessage messageBean, Activity activity) {


        if (activity.isFinishing()) {
            return;
        }
        FrameLayout view = (FrameLayout) activity.getWindow().getDecorView();
        UserRegist sender = messageBean.getData().getChat().getSender();
        if (sender == null) {
            return;
        }
        //vip
        int vip = ArmsUtils.getVipLevel2(sender.getVip_level(), sender.getVip_date());


        if (vip == 0) {
            //啥都不是
            return;
        } else {


            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ArmsUtils.dip2px(activity, 250), ArmsUtils.dip2px(activity, 40));
            lp.setMargins(ArmsUtils.dip2px(activity, 10), 300, 0, 0);
            View vipAnimateView = LayoutInflater.from(activity).inflate(R.layout.item_streamer_vip_in, null);
            isVipAnimate = true;
            ((TextView) vipAnimateView.findViewById(R.id.tv_user_name)).setText(sender.getNick_name());
            //是VIP不是守护

            vipAnimateView.findViewById(R.id.rl_root).setBackgroundResource(ArmsUtils.getVipStreamerBG(sender.getVip_level()));

            ((TextView) vipAnimateView.findViewById(R.id.tv_vip_name)).setTextColor(MyApp.getInstance().getResources().getColor(ArmsUtils.getVipNameColor(sender.getVip_level())));
            ((TextView) vipAnimateView.findViewById(R.id.tv_vip_name)).setText(ArmsUtils.getVipNameText(sender.getVip_level()));
            ((ImageView) vipAnimateView.findViewById(R.id.iv_vip_level))
                    .setImageResource(ArmsUtils.getVipLevelIcon(sender.getVip_level(), sender.getVip_date()));


            view.addView(vipAnimateView, lp);
            Animation vipInAnimation = AnimationUtils.loadAnimation(activity, R.anim.vip_anim);
            vipAnimateView.startAnimation(vipInAnimation);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    isVipAnimate = false;
                    if (!activity.isFinishing()) {
                        vipAnimateView.setVisibility(View.GONE);
                        ((ViewGroup) vipAnimateView.getParent()).removeView(vipAnimateView);

                    }
                    ImMessage mbNext = mVipQueue.poll();
                    if (mbNext != null) {
                        sendVipStreamer(mbNext, activity);
                    }
                }
            }, 4000);
        }
    }
}
