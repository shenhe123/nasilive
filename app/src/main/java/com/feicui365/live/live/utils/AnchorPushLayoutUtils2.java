package com.feicui365.live.live.utils;

import android.content.Context;
import android.os.CountDownTimer;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.opensource.svgaplayer.SVGADrawable;
import com.opensource.svgaplayer.SVGAImageView;
import com.opensource.svgaplayer.SVGAParser;
import com.opensource.svgaplayer.SVGAVideoEntity;

import com.feicui365.live.R;
import com.feicui365.live.base.MyApp;
import com.feicui365.live.bean.ImMessage;
import com.feicui365.live.interfaces.OnLiveUtilsListener;

import com.feicui365.live.model.entity.HotLive;
import com.feicui365.live.model.entity.Pkinfo;
import com.feicui365.live.model.entity.UserRegist;
import com.feicui365.live.util.ComputerUtils;
import com.feicui365.live.widget.PkProgressBar;

import org.jetbrains.annotations.NotNull;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 主播用布局工具
 */
public class AnchorPushLayoutUtils2 {

    private View view;

    //PK进度条
    private PkProgressBar mPkPb;
    //总布局
    private RelativeLayout mRootPkLayout;
    private RelativeLayout mPkLayout;
    private RelativeLayout mHomeLayout;
    private RelativeLayout mAwayLayout;


    private LinearLayout mHomeUserInfoLayout;//
    private LinearLayout mAwayUserInfoLayout;//

    private SVGAImageView svgaHome;
    private SVGAImageView svgaAway;
    private SVGAImageView svgaCenter;

    private CircleImageView mHomeAnchor;//
    private TextView mHomeNickName;//
    private ImageView mHomeFollow;

    private CircleImageView mAwayAnchor;//
    private TextView mAwayNickName;//
    private ImageView mAwayFollow;


    private TextView mPKtime;//




    private int localPkSecond = 6*60000;//PK总时间,这个是固定的,没有波次一次性时间开始到结束
    private int localPunishSecond = 120000;//惩罚时间,也是固定的

    private Context context;
    private CountDownTimer countDownTimer;

    //需要控制的点有2个,1是否是第一次运行,如果是,需要进行初始化,基本参数的添加
    private boolean isFirst = true;

    //3,是否需要隐藏部分页面,时间到了就隐藏,再来的时候在开启
    private UserRegist awayUser;

    private SVGAParser parser;
    private HotLive mLiveInfo;

    int mPkLayoutWidthPx;
    private boolean isHome;



    private OnLiveUtilsListener onLiveUtilsListener;

    public void setOnLiveUtilsListener(OnLiveUtilsListener onLiveUtilsListener) {
        this.onLiveUtilsListener = onLiveUtilsListener;
    }

    public AnchorPushLayoutUtils2(Context context) {

        this.context = context;
        mPkLayoutWidthPx = PkScreenUtils.widthPx / 2;
        initView();
    }


    public void addView(ViewGroup view) {
        view.addView(mRootPkLayout);
    }


    public void setAwayUser(UserRegist awayUser) {
        this.awayUser = awayUser;
        setAwayUserInfo(awayUser);
    }

    //初始化页面组件
    public View initView() {

        parser = new SVGAParser(context);
        view = View.inflate(context, R.layout.layout_anchor_push_2, null);
        mRootPkLayout = view.findViewById(R.id.rl_pk_root);
        mPkPb = view.findViewById(R.id.pb_pk);
        mHomeLayout = view.findViewById(R.id.rl_home);
        mAwayLayout = view.findViewById(R.id.rl_away);
        mPkLayout = view.findViewById(R.id.rl_pk);
        svgaCenter = view.findViewById(R.id.svga_center);
        //主队相关

        mHomeUserInfoLayout = view.findViewById(R.id.ll_home_user_info);
        mHomeAnchor = view.findViewById(R.id.iv_home_streamer);
        mHomeNickName = view.findViewById(R.id.tv_home_name);
        mHomeFollow = view.findViewById(R.id.iv_home_follow);
        svgaHome = view.findViewById(R.id.svga_home);

        //客队相关

        mAwayUserInfoLayout = view.findViewById(R.id.ll_away_user_info);
        mAwayAnchor = view.findViewById(R.id.iv_away_streamer);
        mAwayNickName = view.findViewById(R.id.tv_away_name);
        mAwayFollow = view.findViewById(R.id.iv_away_follow);
        svgaAway = view.findViewById(R.id.svga_away);

        //时间,比分
        mPKtime = view.findViewById(R.id.tv_pk_time);
        return view;
    }


    public View getView() {
        return view;
    }


    /**
     * 1,初始化时间,计算剩余时间,计算公式为默认PK时间减去PK消耗的时间(PK创建的时间-减去本地系统时间)
     * 2,如果剩余时间小于0,开始计算成啊时间,计算公式为PK默认时间+惩罚时间减去PK消耗的时间(PK创建的时间-减去本地系统时间)
     * 3,如果剩余时间大于0,表示还在PK中
     * 4,如果超过了惩罚时间+默认时间也就算了,如果在惩罚时间中,要显示胜负动画
     */
    public void startLive(HotLive liveInfo) {
        mLiveInfo = liveInfo;

        if(ArmsUtils.getIsPkingByBean(liveInfo)==0){
            initPage(0);
        }else{
            initPage(1);
        }



    }





    //初始化基本页面,主要确定是否是PK场景,是否需要打开或者关闭没有页面
    private void initPage(int type) {
        switch (type) {
            case 0:
                //正常直播
                RelativeLayout.LayoutParams mRootParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                        RelativeLayout.LayoutParams.MATCH_PARENT);

                mPkLayout.setLayoutParams(mRootParams);

                mHomeLayout.setLayoutParams(mRootParams);
                //隐藏其他所有内容
                mAwayLayout.setVisibility(View.GONE);
                mPkPb.setVisibility(View.GONE);
                mPKtime.setVisibility(View.GONE);
                break;
            case 1:
                //PK,这里有若干内容
                /**
                 * 1,区分左右,主场左,客场右,由pkinfo来确认主客场
                 *
                 * */

                if (String.valueOf(mLiveInfo.getPkinfo().getHome_anchorid()).equals(mLiveInfo.getAnchorid())) {
                    isHome = true;
                } else {
                    isHome = false;
                }

                RelativeLayout.LayoutParams flRootParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                        ArmsUtils.dip2px(context, 285));
                flRootParams.setMargins(0, ArmsUtils.dip2px(context, 160), 0, 0);
                mPkLayout.setLayoutParams(flRootParams);


                if (isHome) {
                    RelativeLayout.LayoutParams mParamsHome = new RelativeLayout.LayoutParams(mPkLayoutWidthPx
                            , RelativeLayout.LayoutParams.MATCH_PARENT);
                    mHomeLayout.setLayoutParams(mParamsHome);
                    RelativeLayout.LayoutParams mParamsAway = new RelativeLayout.LayoutParams(mPkLayoutWidthPx
                            , RelativeLayout.LayoutParams.MATCH_PARENT);
                    mParamsAway.setMargins(mPkLayoutWidthPx, 0, 0, 0);
                    mAwayLayout.setLayoutParams(mParamsAway);
                } else {
                    RelativeLayout.LayoutParams mParamsHome = new RelativeLayout.LayoutParams(mPkLayoutWidthPx
                            , RelativeLayout.LayoutParams.MATCH_PARENT);
                    mParamsHome.setMargins(mPkLayoutWidthPx, 0, 0, 0);
                    mHomeLayout.setLayoutParams(mParamsHome);
                    RelativeLayout.LayoutParams mParamsAway = new RelativeLayout.LayoutParams(mPkLayoutWidthPx
                            , RelativeLayout.LayoutParams.MATCH_PARENT);

                    mAwayLayout.setLayoutParams(mParamsAway);
                }


                mAwayLayout.setVisibility(View.VISIBLE);
                mPkPb.setVisibility(View.VISIBLE);
                mPKtime.setVisibility(View.VISIBLE);
                mPkPb.cpmputerValue(mLiveInfo.getPkinfo().getHome_score(), mLiveInfo.getPkinfo().getAway_score());
                initPKTime();
                if(onLiveUtilsListener!=null){
                    onLiveUtilsListener.onPkStart(mLiveInfo.getPklive().getAnchorid());
                }
                break;
            case 2:
                //连麦
                break;

        }


        svgaHome.setVisibility(View.GONE);
        svgaHome.clear();
        svgaAway.setVisibility(View.GONE);
        svgaAway.clear();
        svgaCenter.setVisibility(View.GONE);
        svgaCenter.clear();


    }




    public void initPKTime() {
        Pkinfo pkInfo = mLiveInfo.getPkinfo();

        long pkLeftTime = localPkSecond - ComputerUtils.computerTime(pkInfo.getCreate_time(), pkInfo.getUpdate_time());
        mPKtime.setText("");
        mPKtime.setVisibility(View.VISIBLE);
        if (pkLeftTime < 0) {
            //表示已经超时了,计算惩罚时间
            initPunishTime();
        } else {
            //还在PK中,计算PK时间
            if (countDownTimer != null) {
                countDownTimer.cancel();
            }
            countDownTimer = new CountDownTimer(pkLeftTime, 1000) {
                @Override
                public void onTick(long l) {
                    mPKtime.setText(MyApp.getInstance().getString(R.string.st_pk_time, ArmsUtils.timeToString(l)));
                }

                @Override
                public void onFinish() {
                    initPunishTime();
                }

            };
            countDownTimer.start();
        }
    }

    public void initPunishTime() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        if(mLiveInfo.getPkinfo()==null){
            return;
        }
        if(mLiveInfo.getPkinfo().getCreate_time()==null){
            return;
        }
        if(mLiveInfo.getPkinfo().getUpdate_time()==null){
            return;
        }
        Pkinfo pkInfo = mLiveInfo.getPkinfo();
        long pkLeftTime = localPunishSecond - ComputerUtils.computerTime(pkInfo.getCreate_time(), pkInfo.getUpdate_time());
        getResult(mLiveInfo.getPkinfo().getHome_score(), mLiveInfo.getPkinfo().getAway_score());
        countDownTimer = new CountDownTimer(pkLeftTime, 1000) {
            @Override
            public void onTick(long l) {
                mPKtime.setText(MyApp.getInstance().getString(R.string.st_punish_time, ArmsUtils.timeToString(l)));

            }

            @Override
            public void onFinish() {
                endPK();
            }
        };
        countDownTimer.start();
    }

    public void changeScoure(ImMessage mScoure) {
        if (mScoure.getData().getNotify() == null) {
            return;
        }
        if (mScoure.getData().getNotify().getPkinfo() == null) {
            return;
        }

        if (mLiveInfo != null) {
            if(mLiveInfo.getPkinfo()!=null){
                mLiveInfo.getPkinfo().setHome_score(mScoure.getData().getNotify().getPkinfo().getHome_score());
                mLiveInfo.getPkinfo().setAway_score(mScoure.getData().getNotify().getPkinfo().getAway_score());
                mPkPb.cpmputerValue(mLiveInfo.getPkinfo().getHome_score(), mLiveInfo.getPkinfo().getAway_score());
            }

        }

    }

    //结束PK,这里只是PK结束
    public void endPK() {
        mLiveInfo.setPkinfo(null);
        mLiveInfo.setPk_status(0);
        mLiveInfo.setPkid(null);
        mLiveInfo.setPklive(null);


        startLive(mLiveInfo);
        if (onLiveUtilsListener != null) {
            onLiveUtilsListener.onPkEnd();
        }
    }

    private void getResult(int home, int away) {
        if (svgaCenter != null) {
            svgaCenter.clear();
        }
        if (svgaHome != null) {
            svgaHome.clear();
        }
        if (svgaAway != null) {
            svgaAway.clear();
        }
        if (home > away) {
            svgaCenter.setVisibility(View.GONE);
            svgaHome.setVisibility(View.VISIBLE);
            svgaAway.setVisibility(View.VISIBLE);
            parser.decodeFromAssets("pk_winner.svga", new SVGAParser.ParseCompletion() {
                @Override
                public void onComplete(@NotNull SVGAVideoEntity svgaVideoEntity) {
                    SVGADrawable drawable = new SVGADrawable(svgaVideoEntity);
                    if (isHome) {
                        svgaHome.setImageDrawable(drawable);
                        svgaHome.startAnimation();
                    } else {
                        svgaAway.setImageDrawable(drawable);
                        svgaAway.startAnimation();
                    }

                }

                @Override
                public void onError() {

                }
            }, null);
            if (isHome) {
                svgaAway.setImageDrawable(MyApp.getInstance().getDrawable(R.drawable.pk_lose));
            } else {
                svgaHome.setImageDrawable(MyApp.getInstance().getDrawable(R.drawable.pk_lose));
            }


        } else if (home < away) {
            svgaCenter.setVisibility(View.GONE);
            svgaHome.setVisibility(View.VISIBLE);
            svgaAway.setVisibility(View.VISIBLE);

            parser.decodeFromAssets("pk_winner.svga", new SVGAParser.ParseCompletion() {
                @Override
                public void onComplete(@NotNull SVGAVideoEntity svgaVideoEntity) {
                    SVGADrawable drawable = new SVGADrawable(svgaVideoEntity);
                    if (isHome) {
                        svgaAway.setImageDrawable(drawable);
                        svgaAway.startAnimation();
                    } else {
                        svgaHome.setImageDrawable(drawable);
                        svgaHome.startAnimation();
                    }

                }

                @Override
                public void onError() {

                }
            }, null);
            if (isHome) {
                svgaHome.setImageDrawable(MyApp.getInstance().getDrawable(R.drawable.pk_lose));
            } else {
                svgaAway.setImageDrawable(MyApp.getInstance().getDrawable(R.drawable.pk_lose));
            }

        } else if (home == away) {
            svgaCenter.setVisibility(View.VISIBLE);
            svgaHome.setVisibility(View.GONE);
            svgaAway.setVisibility(View.GONE);
            svgaCenter.setImageDrawable(MyApp.getInstance().getDrawable(R.drawable.pk_draw));
        }

    }


    //设置用户基本信息
    public void setAwayUserInfo(UserRegist away) {

        //0用户,1主播
        //如果我是主队,表示我在左边,相反则我在右边
        /**
         * 这里有个问题,当该主播是主场的时候,画面在左边
         * 这里有个问题,当该主播不是主场的时候,画面在右边
         * 并且这里是固定的所以这里啥都不用做..始终显示PK对方的信息就行了
         * */

        mAwayUserInfoLayout.setVisibility(View.VISIBLE);
        mHomeUserInfoLayout.setVisibility(View.GONE);
        GlideUtils.setImage(context, away.getAvatar(), R.mipmap.moren, mAwayAnchor);
        mAwayNickName.setText(away.getNick_name());


        mAwayFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onLiveUtilsListener != null) {
                    onLiveUtilsListener.onPkAnchoorAttend(away.getId());
                }
                mAwayFollow.setVisibility(View.GONE);
            }
        });

    }

    public void showFollowButton() {
        mAwayFollow.setVisibility(View.VISIBLE);
    }





    public void relase() {
        view = null;
        mPkPb = null;


        mHomeUserInfoLayout = null;
        mHomeAnchor = null;
        mHomeNickName = null;
        mHomeNickName = null;

        mAwayUserInfoLayout = null;
        mAwayAnchor = null;
        mAwayNickName = null;
        mAwayNickName = null;


        mPKtime = null;

        if (countDownTimer != null) {
            countDownTimer.cancel();
        }

        if (svgaHome != null) {
            svgaHome.clear();
            svgaHome = null;
        }
        if (svgaAway != null) {
            svgaAway.clear();
            svgaAway = null;
        }
        if (svgaCenter != null) {
            svgaCenter.clear();
            svgaCenter = null;
        }



        parser = null;
    }


}
