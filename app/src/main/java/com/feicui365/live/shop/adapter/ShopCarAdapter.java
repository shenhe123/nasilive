package com.feicui365.live.shop.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.feicui365.live.R;
import com.feicui365.live.shop.entity.CartGoodInfo;
import com.feicui365.live.shop.entity.CartGoods;

import java.util.ArrayList;
import java.util.List;

public class ShopCarAdapter extends BaseQuickAdapter<CartGoodInfo, BaseViewHolder> {
    public ShopCarAdapter(@Nullable List<CartGoodInfo> data) {
        super(R.layout.shop_car_item, data);
    }

    CheckChangeListener checkChangeListener;

    public interface CheckChangeListener {
        void checkChange(ArrayList<CartGoods> check_list);

        void checkAll(boolean all_check);
    }

    public CheckChangeListener getCheckChangeListener() {
        return checkChangeListener;
    }

    public void setCheckChangeListener(CheckChangeListener checkChangeListener) {
        this.checkChangeListener = checkChangeListener;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, CartGoodInfo item) {

        Glide.with(helper.itemView)
                .applyDefaultRequestOptions(new RequestOptions().centerCrop().placeholder(R.mipmap.zhanwei))
                .load(item.getShop().getUser().getAvatar())
                .into((ImageView) helper.getView(R.id.civ_avatar));

        if (item.isCheck()) {

            ((CheckBox) helper.getView(R.id.cb_all)).setChecked(true);
        } else {
            ((CheckBox) helper.getView(R.id.cb_all)).setChecked(false);
        }


        helper.setText(R.id.tv_nickname, item.getShop().getUser().getNick_name());
        ShopCarChildAdapter shopCarChildAdapter = new ShopCarChildAdapter(item.getCartgoods());
        ((RecyclerView) helper.getView(R.id.rv_shop_goods)).setAdapter(shopCarChildAdapter);
        ((RecyclerView) helper.getView(R.id.rv_shop_goods)).setLayoutManager(new LinearLayoutManager(helper.itemView.getContext()));
        ((RelativeLayout) helper.getView(R.id.rl_check)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //复选框点击事件
                //1,总复选框处于勾选/未勾选状态
                if (((CheckBox) helper.getView(R.id.cb_all)).isChecked()) {
                    shopCarChildAdapter.myNotifyData(false);
                    ((CheckBox) helper.getView(R.id.cb_all)).setChecked(false);
                    item.setCheck(false);
                } else {
                    shopCarChildAdapter.myNotifyData(true);
                    ((CheckBox) helper.getView(R.id.cb_all)).setChecked(true);
                    item.setCheck(true);
                }

                if (checkChangeListener != null) {
                    checkChangeListener.checkChange(getResulet());
                    checkChangeListener.checkAll(checkChangeResult());
                }
            }
        });

        shopCarChildAdapter.setCheckChangeListener(new ShopCarChildAdapter.ChildCheckChangeListener() {
            @Override
            public void checkChange(boolean all_check) {
                if (all_check) {
                    ((CheckBox) helper.getView(R.id.cb_all)).setChecked(true);
                    item.setCheck(true);
                } else {
                    ((CheckBox) helper.getView(R.id.cb_all)).setChecked(false);
                    item.setCheck(false);
                }

                if (checkChangeListener != null) {
                    checkChangeListener.checkChange(getResulet());
                    checkChangeListener.checkAll(checkChangeResult());
                }
            }
        });
    }


    public ArrayList<CartGoods> getResulet() {
        ArrayList<CartGoods> goods = new ArrayList<>();
        for (int i = 0; i < getData().size(); i++) {
            for (int y = 0; y < getData().get(i).getCartgoods().size(); y++) {
                if (getData().get(i).getCartgoods().get(y).isCheck()) {
                    goods.add(getData().get(i).getCartgoods().get(y));
                }

            }
        }

        return goods;
    }

    public ArrayList<CartGoodInfo> getSubmitResulet() {
        ArrayList<CartGoodInfo> cartGoodInfos_list = new ArrayList<>();
        for (int i = 0; i < getData().size(); i++) {
            CartGoodInfo cartGoodInfo = (CartGoodInfo) getData().get(i).clone();

            ArrayList<CartGoods> goods = new ArrayList<>();

            for (int y = 0; y < cartGoodInfo.getCartgoods().size(); y++) {

                if (cartGoodInfo.getCartgoods().get(y).isCheck()) {

                    goods.add(cartGoodInfo.getCartgoods().get(y));

                }

            }

            cartGoodInfo.setCartgoods(goods);

            if (goods.size() > 0) {
                cartGoodInfos_list.add(cartGoodInfo);
            }

        }

        return cartGoodInfos_list;
    }

    public boolean checkChangeResult() {
        //只关心是否全部选中
        boolean check = true;
        for (int i = 0; i < getData().size(); i++) {
            if (getData().get(i).isCheck() == false) {
                check = false;
                break;
            }
        }

        return check;
    }


    public void changeCheck(boolean check) {

        for (int i = 0; i < getData().size(); i++) {

            getData().get(i).setCheck(check);

            for (int y = 0; y < getData().get(i).getCartgoods().size(); y++) {
                getData().get(i).getCartgoods().get(y).setCheck(check);
            }
        }
        if (checkChangeListener != null) {
            checkChangeListener.checkChange(getResulet());

        }
notifyDataSetChanged();

    }
}
