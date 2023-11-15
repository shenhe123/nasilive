package com.feicui365.live.live.dialog;

import android.annotation.SuppressLint;
import android.arch.lifecycle.Lifecycle;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.AutoDisposeConverter;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import com.feicui365.live.R;
import com.feicui365.live.base.BaseDialogFragment;
import com.feicui365.live.contract.LivePushContrat;
import com.feicui365.live.im.TxImSendUtils;
import com.feicui365.live.live.utils.ArmsUtils;
import com.feicui365.live.net.APIService;
import com.feicui365.live.presenter.LivePushPresenter;
import com.feicui365.live.shop.entity.Good;
import com.feicui365.live.ui.act.WebShopActivity;
import com.feicui365.live.ui.adapter.ShopAnchorItemAdapter;
import com.feicui365.live.util.MyUserInstance;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


@SuppressLint("ValidFragment")
public class LiveShopDialog extends BaseDialogFragment implements LivePushContrat.View {
    @BindView(R.id.tv_shop_num)
    TextView tvShopNum;
    @BindView(R.id.rv_shop_list)
    RecyclerView rvShopList;


    List<Good> shopItems;
    LivePushPresenter mPresenter;
    ShopAnchorItemAdapter shopItemAdapter;
    int mType = 0;//0主播,1用户
    String mAnchorId;

    public LiveShopDialog(int mType, String mAnchorId) {
        this.mType = mType;
        this.mAnchorId = mAnchorId;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.custom_shop_popup;
    }

    @Override
    protected int getDialogStyle() {
        return R.style.transparentBackgroundDiaolg;
    }

    @Override
    protected boolean canCancel() {
        return true;
    }

    @Override
    public void onActivityCreated(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPresenter = new LivePushPresenter();
        mPresenter.attachView(this);
        shopItems = new ArrayList<>();
        mPresenter.getGoodsList(mAnchorId);
        initList();


    }

    @Override
    public void getGoodsList(ArrayList<Good> bean) {

        if (ArmsUtils.isArrEmpty(bean)) {
            shopItems.addAll(bean);
            initList();
        } else {
            tvShopNum.setText("共 " + 0 + " 件商品");
        }


    }

    private void initList() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvShopList.setLayoutManager(linearLayoutManager);
        shopItemAdapter = new ShopAnchorItemAdapter(shopItems, getContext(), mType);
        rvShopList.setAdapter(shopItemAdapter);
        shopItemAdapter.setUpdate(new ShopAnchorItemAdapter.UpDate() {
            @Override
            public void upDate(Good shopItem, int postion) {
                switch (mType) {
                    case 0:
                        for (int i = 0; i < shopItems.size(); i++) {
                            if (shopItems.get(i).getId().equals(shopItem.getId())) {
                                shopItems.get(i).setLive_explaining(shopItem.getLive_explaining());
                                if (shopItems.get(i).getLive_explaining().equals("1")) {
                                    TxImSendUtils.sendGoodMessage(shopItems.get(i));
                                }

                            } else {
                                shopItems.get(i).setLive_explaining("0");
                            }
                        }
                        shopItemAdapter.notifyDataSetChanged();
                        break;
                    case 1:
                        Intent i = new Intent(getContext(), WebShopActivity.class);
                        i.putExtra("jump_url", APIService.Goods + shopItem.getId() + "?token=" + MyUserInstance.getInstance().getUserinfo().getToken() + "&uid=" + MyUserInstance.getInstance().getUserinfo().getId());
                        startActivity(i);
                        break;
                }


            }
        });

        tvShopNum.setText("共 " + shopItems.size() + " 件商品");
    }

    @Override
    protected void setWindowAttributes(Window window) {
        window.setWindowAnimations(R.style.bottomToTopAnim);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;

        params.height = ArmsUtils.dip2px(getContext(), 500);
        params.gravity = Gravity.BOTTOM;
        window.setAttributes(params);
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

    @Override
    public <T> AutoDisposeConverter<T> bindAutoDispose() {
        return AutoDispose.autoDisposable(AndroidLifecycleScopeProvider
                .from(this, Lifecycle.Event.ON_DESTROY));
    }
}
