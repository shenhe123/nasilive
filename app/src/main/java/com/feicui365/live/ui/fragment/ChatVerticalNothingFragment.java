/*
package com.feicui365.live.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.feicui365.live.R;
import com.feicui365.live.base.BaseMvpFragment;
import com.feicui365.live.bean.Message;
import com.feicui365.live.contract.SuperPlayerContrat;
import com.feicui365.live.dialog.ChatVerticalDialog;
import com.feicui365.live.dialog.ShareDialog;
import com.feicui365.live.dialog.UserInfoDialog;
import com.feicui365.live.model.entity.BaseResponse;
import com.feicui365.live.model.entity.ContributeRank;
import com.feicui365.live.model.entity.HotLive;
import com.feicui365.live.model.entity.LiveInfo;

import com.feicui365.live.model.entity.UserRegist;
import com.feicui365.live.net.APIService;
import com.feicui365.live.presenter.SuperPlayerPresenter;
import com.feicui365.live.ui.act.SuperPlayerVerticalActivity;
import com.feicui365.live.ui.act.WebShopActivity;
import com.feicui365.live.ui.adapter.ChatListVerticalViewerAdapter;
import com.feicui365.live.ui.adapter.LiveContributeAdapter;
import com.feicui365.live.ui.uiinterfae.ShowGift;
import com.feicui365.live.util.HttpUtils;
import com.feicui365.live.util.MyUserInstance;
import com.feicui365.live.util.ToastUtils;
import com.feicui365.live.widget.ChatPopup;
import com.feicui365.live.widget.CommentContributionList;
import com.feicui365.live.widget.CommentShopList;
import com.feicui365.live.widget.RoomMangePopup;
import com.tencent.liteav.demo.play.bean.GiftData;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

@SuppressLint("ValidFragment")
public class ChatVerticalNothingFragment extends BaseMvpFragment<SuperPlayerPresenter> implements SuperPlayerContrat.View {




    @BindView(R.id.close_iv)
    ImageView close_iv;


    public ChatVerticalNothingFragment() {
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected void initView(View view) {
        mPresenter = new SuperPlayerPresenter();
        mPresenter.attachView(this);

    }








    @Override
    protected int getLayoutId() {
        return R.layout.fragment_chat_verical_nothing;
    }





    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onError(Throwable throwable) {

    }




    @OnClick({ R.id.close_iv})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.close_iv:
                getActivity().finish();
                break;

        }
    }




}
*/
