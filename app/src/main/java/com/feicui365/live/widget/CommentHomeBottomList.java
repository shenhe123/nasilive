package com.feicui365.live.widget;

import android.Manifest;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.lxj.xpopup.core.BottomPopupView;
import com.feicui365.live.R;
import com.feicui365.live.live.activity.LiveEditActivity;

import com.feicui365.live.sv.TCVideoPickerActivity;
import com.feicui365.live.sv.TCVideoRecordActivity;
import com.feicui365.live.ui.act.PublishTrendActivity;
import com.feicui365.live.util.MyUserInstance;
import com.feicui365.live.util.ToastUtils;

import com.feicui365.nasinet.utils.AppManager;
import com.tbruyelle.rxpermissions2.RxPermissions;

import static com.blankj.utilcode.util.ActivityUtils.startActivity;


public class CommentHomeBottomList extends BottomPopupView {

    LinearLayout ll_list;
    ImageView iv_live, iv_short, iv_moment, iv_shoot;
    RelativeLayout rl_close;
    Context mContext;

    //表情结束
    public CommentHomeBottomList(@NonNull Context context) {
        super(context);
        this.mContext = context;
    }


    @Override
    protected int getImplLayoutId() {
        return R.layout.custom_home_bottom_popup;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        iv_live = findViewById(R.id.iv_live);
        iv_short = findViewById(R.id.iv_short);
        iv_moment = findViewById(R.id.iv_moment);
        rl_close = findViewById(R.id.rl_close);
        iv_shoot = findViewById(R.id.iv_shoot);
        ll_list = findViewById(R.id.ll_list);

        iv_live.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //主播开播
                if (MyUserInstance.getInstance().visitorIsLogin()) {
                    if (MyUserInstance.getInstance().isAnchor()) {
                        if (!MyUserInstance.getInstance().isFastClick()) {


                            RxPermissions rxPermissions = new RxPermissions((FragmentActivity) mContext);
                            rxPermissions.request(Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO)
                                    .subscribe(granted -> {
                                        if (granted) {
                                            dismiss();
                                            AppManager.getAppManager().startActivity(LiveEditActivity.class);
                                            //    AppManager.getAppManager().startActivity(LivePushActivity3.class);
                                        } else {
                                            ToastUtils.showT("用户没有授权相机权限,请授权在打开直播");
                                        }
                                    });
                        }
                    } else {
                        ToastUtils.showT("您还不是主播,快快去申请吧");

                    }
                }
            }
        });

        iv_short.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //发布短视频
                if (MyUserInstance.getInstance().visitorIsLogin()) {
                    if (!MyUserInstance.getInstance().isFastClick()) {
                        dismiss();
                      AppManager.getAppManager().startActivity(TCVideoPickerActivity.class);
                    }
                }
            }
        });

        iv_moment.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //发布动态
                if (MyUserInstance.getInstance().visitorIsLogin()) {
                    if (!MyUserInstance.getInstance().isFastClick()) {
                        dismiss();
                      /*  if(ArmsUtils.showInputCodeDiaolog((FragmentActivity) mContext)){
                            return;
                        }*/
                        AppManager.getAppManager().startActivity(PublishTrendActivity.class);
                    }
                }
            }
        });
        iv_shoot.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //拍摄短视频
                if (MyUserInstance.getInstance().visitorIsLogin()) {
                    if (!MyUserInstance.getInstance().isFastClick()) {
                        dismiss();
                        AppManager.getAppManager().startActivity(TCVideoRecordActivity.class);
                    }
                }
            }
        });
        rl_close.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        Animation shakeAnim = AnimationUtils.loadAnimation(mContext, R.anim.anim_shake);
        ll_list.startAnimation(shakeAnim);

    }


}