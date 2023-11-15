package com.feicui365.live.shop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.feicui365.live.R;
import com.feicui365.live.base.BaseMvpActivity;
import com.feicui365.live.base.Constants;
import com.feicui365.live.contract.ShopContract;
import com.feicui365.live.model.entity.BaseResponse;
import com.feicui365.live.presenter.ShopPresenter;
import com.feicui365.live.shop.adapter.ShopCarAdapter;
import com.feicui365.live.shop.entity.CartGoodInfo;
import com.feicui365.live.shop.entity.CartGoods;
import com.feicui365.live.util.ToastUtils;
import com.feicui365.nasinet.userconfig.AppStatusManager;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class ShopCarActivity extends BaseMvpActivity<ShopPresenter> implements ShopContract.View {

    @BindView(R.id.cb_all)
    CheckBox cb_all;
    @BindView(R.id.rl_check)
    RelativeLayout rl_check;

    @BindView(R.id.rv_goods_list)
    RecyclerView rv_goods_list;

    @BindView(R.id.tv_all_price)
    TextView tv_all_price;
    @BindView(R.id.tv_submit)
    TextView tv_submit;

    @BindView(R.id.refreshLayout)
    RefreshLayout refreshLayout;

    @BindView(R.id.rl_null)
    RelativeLayout rl_null;


    ShopCarAdapter shopCarAdapter;
    ArrayList<CartGoodInfo> list_data = new ArrayList<>();
    int sumbit_type = 0;
    int page = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_shop_car;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        mPresenter = new ShopPresenter();
        mPresenter.attachView(this);
        AppStatusManager.getInstance().setAppStatus(1);
        setTitle("购物车");
        setOther("管理");
        hideOther(false);
        setOtherTextColor(R.color.black);
        initList();
    }

    private void initList() {
        shopCarAdapter = new ShopCarAdapter(list_data);
        rv_goods_list.setLayoutManager(new LinearLayoutManager(this));
        rv_goods_list.setAdapter(shopCarAdapter);
        shopCarAdapter.setCheckChangeListener(new ShopCarAdapter.CheckChangeListener() {
            @Override
            public void checkChange(ArrayList<CartGoods> check_list) {
                double result = 0;
                for (int i = 0; i < check_list.size(); i++) {
                    result = result + new Double(check_list.get(i).getInventory().getPrice()) * new Double(check_list.get(i).getCount());
                }

                tv_all_price.setText("￥ " + result);
            }

            @Override
            public void checkAll(boolean all_check) {
                if (all_check) {
                    cb_all.setChecked(true);
                } else {
                    cb_all.setChecked(false);
                }
            }
        });

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                initData();
                refreshLayout.setEnableLoadMore(true);
            }
        });

        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                initData();
            }
        });
    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void getCartGoodsList(ArrayList<CartGoodInfo> baseResponse) {
        hideLoading();
        refreshLayout.finishRefresh();
        refreshLayout.finishLoadMore();
        if (page == 1) {
            list_data.clear();
            tv_all_price.setText("￥ 0.0");
            cb_all.setChecked(false);
            if (baseResponse == null) {
                rl_null.setVisibility(View.VISIBLE);
                return;
            }
            if (baseResponse.size() == 0) {
                rl_null.setVisibility(View.VISIBLE);
                return;
            }
        } else {
            if (baseResponse == null) {
                page--;
                refreshLayout.setEnableLoadMore(false);
                return;
            }
            if (baseResponse.size() == 0) {
                page--;
                refreshLayout.setEnableLoadMore(false);
                return;
            }
        }

        rl_null.setVisibility(View.GONE);
        ArrayList<CartGoodInfo> result = new ArrayList<>();

        for (CartGoodInfo cartGoodInfo : baseResponse) {
            if (cartGoodInfo.getCartgoods().size() > 0) {
                for (CartGoods cartGoods : cartGoodInfo.getCartgoods()) {
                    cartGoods.getGoods().setThumb_urls_list(cartGoods.getGoods().getThumb_urls().split(","));
                }
                result.add(cartGoodInfo);
            }
        }


        list_data.addAll(result);

        shopCarAdapter.notifyDataSetChanged();
    }

    @OnClick({R.id.rl_check, R.id.tv_other, R.id.tv_submit})
    public void onClick(View view) {
        if (isFastClick()) {
            return;
        }

        switch (view.getId()) {
            case R.id.rl_check:
                if (cb_all.isChecked()) {
                    cb_all.setChecked(false);
                    shopCarAdapter.changeCheck(false);

                } else {
                    cb_all.setChecked(true);
                    shopCarAdapter.changeCheck(true);

                }
                break;
            case R.id.tv_other:
                if (tv_submit.getText().toString().equals("结算")) {
                    tv_submit.setText("删除");
                    sumbit_type = 1;
                } else {
                    tv_submit.setText("结算");
                    sumbit_type = 0;
                }


                break;
            case R.id.tv_submit:

                switch (sumbit_type) {
                    case 1:
                        if (shopCarAdapter.getResulet().size() == 0) {
                            ToastUtils.showT("请选择需要删除的商品");
                            return;
                        }
                        String cartgoodsids = "";
                        for (int i = 0; i < shopCarAdapter.getResulet().size(); i++) {
                            if (shopCarAdapter.getResulet().size() == 1) {
                                cartgoodsids = cartgoodsids + shopCarAdapter.getResulet().get(i).getId();
                            } else {
                                if (i < shopCarAdapter.getResulet().size() - 1) {
                                    cartgoodsids = cartgoodsids + shopCarAdapter.getResulet().get(i).getId() + ",";
                                } else if (i == shopCarAdapter.getResulet().size() - 1) {
                                    cartgoodsids = cartgoodsids + shopCarAdapter.getResulet().get(i).getId();
                                }

                            }

                        }
                        showLoading();
                        mPresenter.delCartGoods(cartgoodsids);
                        break;
                    case 0:
                        if (shopCarAdapter.getResulet().size() == 0) {
                            ToastUtils.showT("请选择需要结算的商品");
                            return;
                        }
                        Intent intent = new Intent(this, ConfirmOrderActivity.class);
                        Bundle bundleObject = new Bundle();
                        bundleObject.putSerializable(Constants.SHOP_CART_GOODS, shopCarAdapter.getSubmitResulet());
                        intent.putExtras(bundleObject);
                        startActivity(intent);
                        break;
                }


                break;
        }


    }


    @Override
    public void delCartGoods(BaseResponse baseResponse) {
        mPresenter.getCartGoodsList(String.valueOf(page));
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.getCartGoodsList(String.valueOf(page));
    }
}
