package com.feicui365.live.shop.adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.feicui365.live.R;
import com.feicui365.live.base.Constants;
import com.feicui365.live.shop.activity.ShopActivity;
import com.feicui365.live.shop.entity.UserOrderInfo;
import com.feicui365.live.util.DateUtil;

import java.util.List;

public class UserOrderAdapter extends BaseQuickAdapter<UserOrderInfo, BaseViewHolder> {

    int time_over = 900000;
    int type = 1;
    ControlListener controlListener;


    public ControlListener getControlListener() {
        return controlListener;
    }

    public void setControlListener(ControlListener controlListener) {
        this.controlListener = controlListener;
    }

    public interface ControlListener {
        void onPayListener(UserOrderInfo item);


        void onCancelOrderListener(UserOrderInfo item);

        void onDeliveryinfoListener(UserOrderInfo item);

        void onTakeDeliveryListener(UserOrderInfo item);


        void onComment(UserOrderInfo item);
    }


    public UserOrderAdapter(@Nullable List<UserOrderInfo> data, int type) {
        super(R.layout.order_item, data);
        this.type = type;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, UserOrderInfo item) {
        Glide.with(helper.itemView)
                .applyDefaultRequestOptions(new RequestOptions().centerCrop().placeholder(R.mipmap.zhanwei))
                .load(item.getShop().getAvatar())
                .into((ImageView) helper.getView(R.id.civ_avatar));
        helper.setText(R.id.tv_nickname, item.getShop().getNick_name());

        UserOrderChildAdapter orderChildAdapter = new UserOrderChildAdapter(item.getGoods());
        ((RecyclerView) helper.getView(R.id.rv_shop_goods)).setAdapter(orderChildAdapter);
        ((RecyclerView) helper.getView(R.id.rv_shop_goods)).setLayoutManager(new LinearLayoutManager(helper.itemView.getContext()));
        ((RecyclerView) helper.getView(R.id.rv_shop_goods)).setLayoutFrozen(true);

        initType(helper, item);


        helper.getView(R.id.tv_button_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click(((TextView) helper.getView(R.id.tv_button_1)).getText().toString(), item);
            }
        });

        helper.getView(R.id.tv_button_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click(((TextView) helper.getView(R.id.tv_button_2)).getText().toString(), item);

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

    private void click(String text, UserOrderInfo item) {

        if (controlListener == null) {
            return;
        }


        //外层列表

        switch (text) {
            case "付款":
                controlListener.onPayListener(item);
                break;

            case "确认收货":
                controlListener.onTakeDeliveryListener(item);
                break;

            case "评价":
                controlListener.onComment(item);
                break;
            case "取消订单":
                controlListener.onCancelOrderListener(item);
                break;
            case "查看物流":
                controlListener.onDeliveryinfoListener(item);
                break;

        }


    }

    private void initType(BaseViewHolder helper, UserOrderInfo item) {

        switch (item.getStatus()) {
            case 0:
                //待支付状态

                helper.setGone(R.id.ll_button, true);
                helper.setGone(R.id.tv_button_1, true);
                helper.setGone(R.id.tv_button_2, true);
                helper.setGone(R.id.tv_over_time, true);

                helper.setText(R.id.tv_status, "待支付");
                helper.setText(R.id.tv_button_1, "取消订单");
                helper.setText(R.id.tv_button_2, "付款");
                String resultr = DateUtil.allTimeToString2(DateUtil.date2TimeStamp(item.getCreate_time()) + time_over);
                helper.setText(R.id.tv_over_time, resultr + " 后订单到期");

                break;
            case 1:
                //已付款,发货阶段可以查看物流
                helper.setGone(R.id.tv_over_time, false);
                helper.setGone(R.id.tv_button_1, false);
                helper.setGone(R.id.tv_button_2, false);
                helper.setGone(R.id.ll_button, true);

                switch (item.getDelivery_status()) {
                    case 0:
                        helper.setText(R.id.tv_status, "待发货");
                        break;
                    case 1:
                        helper.setText(R.id.tv_status, "已发货");
                        helper.setGone(R.id.tv_button_1, true);
                        helper.setText(R.id.tv_button_1, "查看物流");
                        helper.setGone(R.id.tv_button_2, true);
                        helper.setText(R.id.tv_button_2, "确认收货");
                        break;
                    case 2:
                        helper.setText(R.id.tv_status, "已签收");
                        helper.setGone(R.id.tv_button_1, true);
                        helper.setText(R.id.tv_button_1, "查看物流");
                        break;
                }

                break;
            case 2:
                //订单结束
                helper.setText(R.id.tv_status, "订单关闭");

                helper.setGone(R.id.ll_button, false);
                break;
            case 3:
                //交易完成阶段,数量大于一个可以评价,否则隐藏评价按钮
                helper.setGone(R.id.ll_button, true);
                helper.setText(R.id.tv_status, "已完成");
                helper.setGone(R.id.tv_over_time, false);
                helper.setGone(R.id.tv_button_1, true);
                helper.setText(R.id.tv_button_1, "查看物流");

                if (item.getGoods().size() > 1) {
                    helper.setGone(R.id.tv_button_2, false);
                } else {
                    helper.setGone(R.id.tv_button_2, true);
                    helper.setText(R.id.tv_button_2, "评价");
                }

                break;


            default:
                helper.setGone(R.id.tv_status, false);
                helper.setGone(R.id.ll_button, false);
                break;
        }

    }
}
