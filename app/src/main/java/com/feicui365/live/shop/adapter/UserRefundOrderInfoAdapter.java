package com.feicui365.live.shop.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.feicui365.live.R;
import com.feicui365.live.shop.entity.OrderGoods;

import java.util.ArrayList;
import java.util.List;

public class UserRefundOrderInfoAdapter extends BaseQuickAdapter<OrderGoods, BaseViewHolder> {



    public UserRefundOrderInfoAdapter(@Nullable List<OrderGoods> data) {
        super(R.layout.order_item, data);

    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, OrderGoods item) {
        Glide.with(helper.itemView)
                .applyDefaultRequestOptions(new RequestOptions().centerCrop().placeholder(R.mipmap.zhanwei))
                .load(item.getShop().getAvatar())
                .into((ImageView) helper.getView(R.id.civ_avatar));
        helper.setText(R.id.tv_nickname, item.getShop().getNick_name());
        List<OrderGoods> temp=new ArrayList<>();
        temp.add(item);
        UserRefundOrderInfoChildAdapter orderChildAdapter = new UserRefundOrderInfoChildAdapter(temp);
        ((RecyclerView) helper.getView(R.id.rv_shop_goods)).setAdapter(orderChildAdapter);
        ((RecyclerView) helper.getView(R.id.rv_shop_goods)).setLayoutManager(new LinearLayoutManager(helper.itemView.getContext()));
        ((RecyclerView) helper.getView(R.id.rv_shop_goods)).setLayoutFrozen(true);





    }


}
