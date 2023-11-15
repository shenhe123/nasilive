package com.feicui365.live.shop.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.feicui365.live.R;
import com.feicui365.live.shop.entity.OrderGoods;
import com.feicui365.live.shop.entity.RefundOrderGoods;

import java.util.ArrayList;
import java.util.List;

public class SellerRefundOrderAdapter extends BaseQuickAdapter<RefundOrderGoods, BaseViewHolder> {



    public SellerRefundOrderAdapter(@Nullable List<RefundOrderGoods> data) {
        super(R.layout.seller_order_item, data);

    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, RefundOrderGoods item) {

       helper.setText(R.id.tv_order_code, "订单编号:"+item.getSuborder().getOrder_no());
        helper.setText(R.id.tv_creat_time, item.getCreate_time());
        List<OrderGoods> temp=new ArrayList<>();
        temp.add(item.getGoods());
        SellerRefundOrderChildAdapter orderChildAdapter = new SellerRefundOrderChildAdapter(temp);
        ((RecyclerView) helper.getView(R.id.rv_shop_goods)).setAdapter(orderChildAdapter);
        ((RecyclerView) helper.getView(R.id.rv_shop_goods)).setLayoutManager(new LinearLayoutManager(helper.itemView.getContext()));
        ((RecyclerView) helper.getView(R.id.rv_shop_goods)).setLayoutFrozen(true);


    }

}
