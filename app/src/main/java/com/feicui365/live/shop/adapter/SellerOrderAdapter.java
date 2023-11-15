package com.feicui365.live.shop.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.feicui365.live.R;
import com.feicui365.live.shop.entity.SellerOrderInfo;

import java.util.List;

public class SellerOrderAdapter extends BaseQuickAdapter<SellerOrderInfo, BaseViewHolder> {


    ControlListener controlListener;
    String type = "0";

    public ControlListener getControlListener() {
        return controlListener;
    }

    public void setControlListener(ControlListener controlListener) {
        this.controlListener = controlListener;
    }

    public interface ControlListener {
        void onChatUser(SellerOrderInfo item);


        void onSendGood(SellerOrderInfo item);

    }


    public SellerOrderAdapter(@Nullable List<SellerOrderInfo> data) {
        super(R.layout.seller_order_item, data);

    }

    public SellerOrderAdapter(@Nullable List<SellerOrderInfo> data, String type) {
        super(R.layout.seller_order_item, data);
        this.type = type;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, SellerOrderInfo item) {

        helper.setText(R.id.tv_order_code, "订单编号:" + item.getOrder_no());
        helper.setText(R.id.tv_creat_time, item.getCreate_time());

        SellerOrderChildAdapter orderChildAdapter = new SellerOrderChildAdapter(item.getGoods());
        ((RecyclerView) helper.getView(R.id.rv_shop_goods)).setAdapter(orderChildAdapter);
        ((RecyclerView) helper.getView(R.id.rv_shop_goods)).setLayoutManager(new LinearLayoutManager(helper.itemView.getContext()));
        ((RecyclerView) helper.getView(R.id.rv_shop_goods)).setLayoutFrozen(true);

        if (type.equals("1")) {
            helper.setGone(R.id.ll_button, true);
            helper.setText(R.id.tv_button_1, "联系买家");
            helper.setText(R.id.tv_button_2, "发货");


            helper.getView(R.id.tv_button_2).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (controlListener != null) {
                        controlListener.onSendGood(item);
                    }
                }
            });

            helper.getView(R.id.tv_button_1).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (controlListener != null) {
                        controlListener.onChatUser(item);
                    }
                }
            });
        } else {
            helper.setGone(R.id.ll_button, false);
        }


    }


}
