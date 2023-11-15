package com.feicui365.live.shop.adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.feicui365.live.R;
import com.feicui365.live.base.Constants;
import com.feicui365.live.shop.activity.ShopActivity;
import com.feicui365.live.shop.entity.UserOrderInfo;
import com.feicui365.live.shop.entity.OrderGoods;

import java.util.List;
//订单详情内的adapter
public class UserOrderInfoAdapter extends BaseQuickAdapter<UserOrderInfo, BaseViewHolder> {


    OnOrderWordListener onOrderWordListener;

    public interface OnOrderWordListener {
        void refund(OrderGoods item);
        void comment(OrderGoods item);
    }


    public OnOrderWordListener getOnOrderWordListener() {
        return onOrderWordListener;
    }

    public void setOnOrderWordListener(OnOrderWordListener onOrderWordListener) {
        this.onOrderWordListener = onOrderWordListener;
    }


    public UserOrderInfoAdapter(@Nullable List<UserOrderInfo> data) {
        super(R.layout.order_info_item, data);

    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, UserOrderInfo item) {
        Glide.with(helper.itemView)
                .applyDefaultRequestOptions(new RequestOptions().centerCrop().placeholder(R.mipmap.zhanwei))
                .load(item.getShop().getAvatar())
                .into((ImageView) helper.getView(R.id.civ_avatar));
        helper.setText(R.id.tv_nickname, item.getShop().getNick_name());

        UserOrderInfoChildAdapter orderChildAdapter = new UserOrderInfoChildAdapter(item.getGoods(),item.getStatus());
        ((RecyclerView) helper.getView(R.id.rv_shop_goods)).setAdapter(orderChildAdapter);
        ((RecyclerView) helper.getView(R.id.rv_shop_goods)).setLayoutManager(new LinearLayoutManager(helper.itemView.getContext()));
        ((RecyclerView) helper.getView(R.id.rv_shop_goods)).setLayoutFrozen(true);


        orderChildAdapter.setOnOrderWordListener(new UserOrderInfoChildAdapter.OnOrderWordListener() {
            @Override
            public void refund(OrderGoods item) {
                if(onOrderWordListener!=null){
                    onOrderWordListener.refund(item);
                }
            }

            @Override
            public void comment(OrderGoods item) {
                if(onOrderWordListener!=null){
                    onOrderWordListener.comment(item);
                }
            }
        });

        helper.getView(R.id.civ_avatar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(helper.itemView.getContext(), ShopActivity.class);
                i.putExtra(Constants.SHOP_ID,String.valueOf(item.getShopid()));
                helper.itemView.getContext().startActivity(i);
            }
        });


    }



}
