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
 * 观众观看用工具
 */
public class UserPlayLayoutUtils2 {

    View mPkRootview;

    //PK进度条
    PkProgressBar mPkPb;
    //总布局
    RelativeLayout mRootPkLayout;
    RelativeLayout mHomeLayout;
    RelativeLayout mAwayLayout;


    LinearLayout mHomeUserInfoLayout;//
    LinearLayout mAwayUserInfoLayout;//

    SVGAImageView svgaHome;
    SVGAImageView svgaAway;
    SVGAImageView svgaCenter;

    CircleImageView mHomeAnchor;//
    TextView mHomeNickName;//
    ImageView mHomeFollow;

    CircleImageView mAwayAnchor;//
    TextView mAwayNickName;//
    ImageView mAwayFollow;


    TextView mPKtime;//


    private OnLiveUtilsListener onLiveUtilsListener;

    public void setOnLiveUtilsListener(OnLiveUtilsListener onLiveUtilsListener) {
        this.onLiveUtilsListener = onLiveUtilsListener;
    }

    private int localPkSecond = 6*60000;//PK总时间,这个是固定的,没有波次一次性时间开始到结束
    private int localPunishSecond = 120000;//惩罚时间,也是固定的
    int mPkLayoutWidthPx;
    Context context;
    CountDownTimer countDownTimer;

    //需要控制的点有2个,1是否是第一次运行,如果是,需要进行初始化,基本参数的添加
    private boolean isFirst = true;

    //3,是否需要隐藏部分页面,时间到了就隐藏,再来的时候在开启
    UserRegist awayUser;

    SVGAParser parser;
    HotLive mLiveInfo;
    Pkinfo mPkInfo;
    private boolean isHome;

    public UserPlayLayoutUtils2(Context context) {

        this.context = context;
        mPkLayoutWidthPx = PkScreenUtils.widthPx / 2;
        initView();
    }
    public void addView(ViewGroup view) {
        view.addView(mPkRootview);
    }


    public void pkStart(Pkinfo mPkInfo, HotLive liveInfo) {
        this.mPkInfo = mPkInfo;
        mLiveInfo.setPk_status(1);
        mLiveInfo.setPklive(liveInfo);
        mLiveInfo.setPkid(liveInfo.getPkid());
        mLiveInfo.setPkinfo(mPkInfo);
        startLive(mLiveInfo);
    }

    public void setAwayUser(UserRegist awayUser) {
        this.awayUser = awayUser;
        setAwayUserInfo(awayUser);
    }

    //初始化页面组件
    public View initView() {

        parser = new SVGAParser(context);
        mPkRootview = mPkRootview.inflate(context, R.layout.layout_user_player, null);


        mPkPb = mPkRootview.findViewById(R.id.pb_pk);
        mHomeLayout = mPkRootview.findViewById(R.id.rl_home);
        mAwayLayout = mPkRootview.findViewById(R.id.rl_away);
        mRootPkLayout = mPkRootview.findViewById(R.id.rl_pk);
        svgaCenter = mPkRootview.findViewById(R.id.svga_center);
        //主队相关

        mHomeUserInfoLayout = mPkRootview.findViewById(R.id.ll_home_user_info);
        mHomeAnchor = mPkRootview.findViewById(R.id.iv_home_streamer);
        mHomeNickName = mPkRootview.findViewById(R.id.tv_home_name);
        mHomeFollow = mPkRootview.findViewById(R.id.iv_home_follow);

        svgaHome = mPkRootview.findViewById(R.id.svga_home);

        //客队相关

        mAwayUserInfoLayout = mPkRootview.findViewById(R.id.ll_away_user_info);
        mAwayAnchor = mPkRootview.findViewById(R.id.iv_away_streamer);
        mAwayNickName = mPkRootview.findViewById(R.id.tv_away_name);
        mAwayFollow = mPkRootview.findViewById(R.id.iv_away_follow);
        svgaAway = mPkRootview.findViewById(R.id.svga_away);
        //时间,比分
        mPKtime = mPkRootview.findViewById(R.id.tv_pk_time);
        mAwayUserInfoLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        return mPkRootview;
    }





    /**
     * 1,初始化时间,计算剩余时间,计算公式为默认PK时间减去PK消耗的时间(PK创建的时间-减去本地系统时间)
     * 2,如果剩余时间小于0,开始计算成啊时间,计算公式为PK默认时间+惩罚时间减去PK消耗的时间(PK创建的时间-减去本地系统时间)
     * 3,如果剩余时间大于0,表示还在PK中
     * 4,如果超过了惩罚时间+默认时间也就算了,如果在惩罚时间中,要显示胜负动画
     */
    public void startLive(HotLive liveInfo) {


        mLiveInfo = liveInfo;

        if (mLiveInfo.getPk_status() == 1) {
            if (mLiveInfo.getPklive() == null) {
                endPK();
            } else {
                if (mLiveInfo.getPkinfo() == null) {
                    endPK();
                } else {
                    mPkInfo = mLiveInfo.getPkinfo();
                }
            }
        }


        //重置该论用户排行
        initPlayer();

    }

    private void initPlayer() {


        int type = mLiveInfo.getPk_status();


        //先确定有没有PK信息

        switch (type) {
            case 0:

                initPage(type);
                break;
            case 1:

                initPage(type);
                initPKTime();
                if (onLiveUtilsListener != null) {
                    onLiveUtilsListener.onPkStart(mLiveInfo.getPklive().getAnchorid());
                }
                break;
            case 2:
                break;

        }


    }

    //初始化基本页面,主要确定是否是PK场景,是否需要打开或者关闭没有页面
    private void initPage(int type) {
        switch (type) {
            case 0:
                //正常直播
                RelativeLayout.LayoutParams flRootParams2 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                        RelativeLayout.LayoutParams.MATCH_PARENT);

                mRootPkLayout.setLayoutParams(flRootParams2);
                mHomeLayout.setVisibility(View.GONE);
                //隐藏其他所有内容
                mAwayLayout.setVisibility(View.GONE);
                mPkPb.setVisibility(View.GONE);
                mPKtime.setVisibility(View.GONE);
                break;
            case 1:
                //PK


                //这里就涉及一个问题,用户看到的主播到底在哪边
                if (String.valueOf(mPkInfo.getHome_anchorid()).equals(mLiveInfo.getAnchorid())) {
                    isHome = true;
                } else {
                    isHome = false;
                }


                RelativeLayout.LayoutParams flRootParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                        ArmsUtils.dip2px(context, 285));
                flRootParams.setMargins(0, ArmsUtils.dip2px(context, 160), 0, 0);
                mRootPkLayout.setLayoutParams(flRootParams);
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
                mHomeLayout.setVisibility(View.VISIBLE);
                mAwayLayout.setVisibility(View.VISIBLE);
                mPkPb.setVisibility(View.VISIBLE);
                mPKtime.setVisibility(View.VISIBLE);
                mPkPb.cpmputerValue(mPkInfo.getHome_score(), mPkInfo.getAway_score());
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
        Pkinfo pkInfo = mLiveInfo.getPkinfo();
        long pkLeftTime = localPunishSecond + localPkSecond - ComputerUtils.computerTime(pkInfo.getCreate_time(), pkInfo.getUpdate_time());
        getResult(mPkInfo.getHome_score(), mPkInfo.getAway_score());
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

        if (mPkInfo != null) {
            mPkInfo.setHome_score(mScoure.getData().getNotify().getPkinfo().getHome_score());
            mPkInfo.setAway_score(mScoure.getData().getNotify().getPkinfo().getAway_score());
            mPkPb.cpmputerValue(mPkInfo.getHome_score(), mPkInfo.getAway_score());
        }

    }

    //结束PK,这里只是PK结束
    public void endPK() {
        mLiveInfo.setPkinfo(null);
        mLiveInfo.setPk_status(0);
        mLiveInfo.setPkid(null);
        mLiveInfo.setPklive(null);
        startLive(mLiveInfo);

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


    public void cleanPkAnimate() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        if (svgaAway != null) {
            svgaAway.clear();

        }
        if (svgaHome != null) {
            svgaHome.clear();
        }
    }


    public void relase() {
        mPkRootview = null;
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


        parser = null;
    }
}
