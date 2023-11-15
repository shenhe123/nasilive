package com.feicui365.live.shop.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.feicui365.live.R;
import com.feicui365.live.shop.entity.UserOrderInfo;

import java.util.List;

//订单详情内的adapter
public class SellerOrderInfoAdapter extends BaseQuickAdapter<UserOrderInfo, BaseViewHolder> {



    public SellerOrderInfoAdapter(@Nullable List<UserOrderInfo> data) {
        super(R.layout.seller_order_info_item, data);

    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, UserOrderInfo item) {
        helper.setText(R.id.tv_order_code, "订单编号:" + item.getOrder_no());


        SellerOrderInfoChildAdapter orderChildAdapter = new SellerOrderInfoChildAdapter(item.getGoods(),item.getStatus());
        ((RecyclerView) helper.getView(R.id.rv_shop_goods)).setAdapter(orderChildAdapter);
        ((RecyclerView) helper.getView(R.id.rv_shop_goods)).setLayoutManager(new LinearLayoutManager(helper.itemView.getContext()));
        ((RecyclerView) helper.getView(R.id.rv_shop_goods)).setLayoutFrozen(true);


        initStatus(helper,  item);


    }

    private void initStatus(BaseViewHolder helper, UserOrderInfo item) {

        switch ( item.getStatus()){
            case 0:
                helper.setText(R.id.tv_status, "待付款");
                break;

            case 1:

                switch (item.getDelivery_status()) {

                    case 0:
                        helper.setText(R.id.tv_status, "待发货");

                        break;

                    case 1:
                        helper.setText(R.id.tv_status, "已发货");

                        break;
                    case 2:
                        helper.setText(R.id.tv_status, "已签收");

                        break;
                }
                break;

            case 2:
                helper.setText(R.id.tv_status, "已发货");
                break;

            case 3:
                helper.setText(R.id.tv_status, "已完成");
                break;

        }

    }


}
