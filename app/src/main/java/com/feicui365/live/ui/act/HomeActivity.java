package com.feicui365.live.ui.act;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.alibaba.fastjson.JSON;
import com.dueeeke.videoplayer.player.VideoViewManager;
import com.feicui365.live.live.activity.LiveEditActivity;
import com.feicui365.live.util.WordUtil;
import com.lxj.xpopup.XPopup;
import com.feicui365.live.R;
import com.feicui365.live.base.BaseMvpActivity;
import com.feicui365.live.contract.HomeContract;
import com.feicui365.live.eventbus.MessageBus;
import com.feicui365.live.eventbus.UnReadBus;
import com.feicui365.live.im.TxImUtils;
import com.feicui365.live.interfaces.OnGetUnRead;

import com.feicui365.live.live.dialog.TipsDialog;
import com.feicui365.live.live.utils.ArmsUtils;
import com.feicui365.live.ui.fragment.HomeFragment;
import com.feicui365.live.util.ToastUtils;
import com.feicui365.live.widget.CommentHomeBottomList;
import com.feicui365.live.widget.Dialogs;
import com.feicui365.live.model.entity.UserRegist;
import com.feicui365.live.presenter.HomePresenter;
import com.feicui365.live.ui.fragment.HomeShortVideoFragment;
import com.feicui365.live.ui.fragment.HomeTrendsFragment;
import com.feicui365.live.ui.fragment.MyFragment;
import com.feicui365.live.util.MyUserInstance;
import com.feicui365.nasinet.utils.AppManager;
import com.orhanobut.hawk.Hawk;
import com.tbruyelle.rxpermissions2.RxPermissions;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;

public class HomeActivity extends BaseMvpActivity<HomePresenter> implements HomeContract.View, OnGetUnRead {
    @BindView(R.id.home_tab_iv)
    ImageView home_tab_iv;
    @BindView(R.id.home_tab_tv)
    TextView home_tab_tv;
    @BindView(R.id.short_tab_iv)
    ImageView short_tab_iv;
    @BindView(R.id.short_tab_tv)
    TextView short_tab_tv;


    @BindView(R.id.trend_tab_iv)
    ImageView trend_tab_iv;
    @BindView(R.id.trend_tab_tv)
    TextView trend_tab_tv;
    @BindView(R.id.my_tab_iv)
    ImageView my_tab_iv;
    @BindView(R.id.my_tab_tv)
    TextView my_tab_tv;
    @BindView(R.id.tv_red_hot)
    TextView tv_red_hot;
    @BindView(R.id.iv_star)
    Button iv_star;
    @BindView(R.id.iv_anchor_auth)
    Button iv_anchor_auth;




    private HashSet<DialogFragment> mDialogFragmentSet;
//    HomeFragment homeFragment;
  //  HomeShortVideoFragment shortVideoFragment;
 //   HomeTrendsFragment homeTrendsFragment;
    MyFragment myFragment;

    private int now_page = 0;
    // OnGetUnRead onGetUnRead;
    private List<Fragment> mFragments = new ArrayList<>();
    private static boolean mBackKeyPressed = false;

    @Override
    protected int getLayoutId() {

        return R.layout.activity_home_2;
    }



    @Override
    protected void initData() {
        if (MyUserInstance.getInstance().hasToken()) {
            updateUserInfo();

        }
    }

    @Override
    protected void initView() {

        mPresenter = new HomePresenter();
        mPresenter.attachView(this);
        mDialogFragmentSet = new HashSet<>();
        hideTitle(true);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                setAndroidNativeLightStatusBar(true);

            }
        }, 1000);


     //   homeFragment = new HomeFragment();
     //   shortVideoFragment = new HomeShortVideoFragment();
      //  homeTrendsFragment = new HomeTrendsFragment();
        myFragment = new MyFragment();

      //  mFragments.add(homeFragment);
      //  mFragments.add(shortVideoFragment);
      //  mFragments.add(homeTrendsFragment);
        mFragments.add(myFragment);

        addFragment(myFragment);

        EventBus.getDefault().register(this);

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetMessage(MessageBus message) {
        if (!this.isFinishing()) {

            int num = Integer.valueOf(message.message);
            String result = "0";
            if (num > 99) {
                result = "99+";
            } else {
                result = String.valueOf(num);
            }
            if (!result.equals("0")) {
                tv_red_hot.setVisibility(View.VISIBLE);
                tv_red_hot.setText(result);
            } else {
                tv_red_hot.setVisibility(View.GONE);
            }
        }


    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUnreadCount(UnReadBus message) {
        if(message.unread>0){
            tv_red_hot.setVisibility(View.VISIBLE);
            tv_red_hot.setText(String.valueOf(message.unread));
        }else{
            tv_red_hot.setVisibility(View.GONE);
        }
    }

    @Override
    public void onError(Throwable throwable) {

    }


    public void updateUserInfo() {
        mPresenter.getUserInfo();
    }

    @Override
    public void setUserInfo(UserRegist bean) {
        MyUserInstance.getInstance().setUserInfo(bean);
        String temp = JSON.toJSONString(bean);
        Hawk.put("USER_REGIST", temp);
        TxImUtils.getSocketManager().imLogin();
        if(ArmsUtils.isStringEmpty(bean.getIs_anchor())){
            if(bean.getIs_anchor().equals("1")){
                iv_star.setVisibility(View.VISIBLE);
                iv_anchor_auth.setVisibility(View.GONE);
            }else{
                iv_star.setVisibility(View.GONE);
                iv_anchor_auth.setVisibility(View.VISIBLE);
            }

        }else{
            iv_star.setVisibility(View.GONE);
            iv_anchor_auth.setVisibility(View.VISIBLE);
        }

    }

    @OnClick({R.id.iv_star,R.id.iv_anchor_auth})
    public void onClick(View view) {

        if (isFastClick()) {
            return;
        }
        Intent i = null;
        switch (view.getId()) {

            case R.id.iv_star:
                if (MyUserInstance.getInstance().visitorIsLogin()) {
                    if (MyUserInstance.getInstance().isAnchor()) {
                        if (!MyUserInstance.getInstance().isFastClick()) {


                            RxPermissions rxPermissions = new RxPermissions(this);
                            rxPermissions.request(Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO)
                                    .subscribe(granted -> {
                                        if (granted) {

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
                break;
            case R.id.iv_anchor_auth:
                if (MyUserInstance.getInstance().visitorIsLogin()) {
                    if (MyUserInstance.getInstance().getUserinfo().getIs_anchor().equals("0")) {
                        if (MyUserInstance.getInstance().getUserinfo().getIs_reject() == 0) {
                            i = new Intent(this, ApplyAnchorActivity.class).putExtra("TYPE", 1);
                        }else {
                            i = new Intent(this, RejectActivity.class);
                        }
                    } else if (MyUserInstance.getInstance().getUserinfo().getIs_anchor().equals("1")) {
                        i = new Intent(this, LiveManageActivity.class);
                        iv_anchor_auth.setText(WordUtil.getString(R.string.LiveLog));
                    }
                    startActivity(i);
                }
                 break;
        }
    }
/*

    @SuppressLint("ResourceAsColor")
    private void selectedTab(int tab) {
        home_tab_iv.setImageResource(R.mipmap.zhibo);
        home_tab_tv.setTextColor(getResources().getColor(R.color.color_939393));

        short_tab_iv.setImageResource(R.mipmap.video);
        short_tab_tv.setTextColor(getResources().getColor(R.color.color_939393));

        trend_tab_iv.setImageResource(R.mipmap.dongtai);
        trend_tab_tv.setTextColor(getResources().getColor(R.color.color_939393));


        my_tab_iv.setImageResource(R.mipmap.wode);
        my_tab_tv.setTextColor(getResources().getColor(R.color.color_939393));
        switch (tab) {
            case 1:
                home_tab_iv.setImageResource(R.mipmap.zhibo_pre);
                home_tab_tv.setTextColor(getResources().getColor(R.color.color_theme));
                break;
            case 2:
                short_tab_iv.setImageResource(R.mipmap.video_pre);
                short_tab_tv.setTextColor(getResources().getColor(R.color.color_theme));
                break;
            case 3:
                trend_tab_iv.setImageResource(R.mipmap.dongtai_pre);
                trend_tab_tv.setTextColor(getResources().getColor(R.color.color_theme));
                break;


            case 4:
                my_tab_iv.setImageResource(R.mipmap.wode_pre);
                my_tab_tv.setTextColor(getResources().getColor(R.color.color_theme));
                break;
        }
    }

*/

    @Override
    protected void onResume() {
        super.onResume();
        if (MyUserInstance.getInstance().hasToken()) {
            updateUserInfo();

        }
      /*  if (null != shortVideoFragment) {
            if (now_page == 2) {
                shortVideoFragment.action = true;
            } else {
                shortVideoFragment.action = false;
            }
        }*/
        //  initIMSDK();
    }


    public void addDialogFragment(DialogFragment dialogFragment) {
        if (mDialogFragmentSet != null && dialogFragment != null) {
            mDialogFragmentSet.add(dialogFragment);
        }
    }

    public void removeDialogFragment(DialogFragment dialogFragment) {
        if (mDialogFragmentSet != null && dialogFragment != null) {
            mDialogFragmentSet.remove(dialogFragment);
        }
    }


    private Dialog dialog;

    @Override
    public void showLoading() {
        hideLoading();
        dialog = Dialogs.createLoadingDialog(this);
        dialog.show();
    }

    @Override
    public void hideLoading() {
        if (null != dialog) {
            dialog.dismiss();
        }
    }




    @Override
    public void onBackPressed() {
        if (!mBackKeyPressed) {
            ToastUtils.showT("再按一次退出程序");
            mBackKeyPressed = true;
            new Timer().schedule(new TimerTask() {//延时两秒，如果超出则擦错第一次按键记录
                @Override
                public void run() {
                    mBackKeyPressed = false;
                }
            }, 2000);
        } else {//退出程序

            AppManager.getAppManager().finishAllActivity();

        }
    }

    public void toPageFragement(int position) {
        if (mFragments.size() <= position) {
            return;
        }
        now_page = position;
        addFragment(mFragments.get(position));
    }

    public void addFragment(Fragment tabFragment) {
        if (tabFragment == null) {
            return;
        }
        if (tabFragment.isAdded()) {
            showFragment(tabFragment);
        } else {
            hideElseFragment(tabFragment);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fl_fragment, tabFragment)
                    .commitAllowingStateLoss();
        }
    }

    public void showFragment(Fragment fragment) {
        hideElseFragment(fragment);
        getSupportFragmentManager().beginTransaction()

                .show(fragment)
                .commitAllowingStateLoss();
    }


    public void hideElseFragment(Fragment fragment) {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        for (int i = 0; i < mFragments.size(); i++) {
            Fragment tabFragmen = mFragments.get(i);
            if (fragment == tabFragmen) {
                continue;
            }
            if (fragment != null && tabFragmen.isAdded()) {
                transaction.hide(tabFragmen);
            }
        }
        transaction.commitAllowingStateLoss();
    }


    @Override
    public void getUnRead(String count) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
