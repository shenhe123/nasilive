package com.feicui365.live.shop.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.feicui365.live.R;
import com.feicui365.live.shop.entity.OrderGoods;

import java.util.List;

public class UserOrderInfoChildAdapter extends BaseQuickAdapter<OrderGoods, BaseViewHolder> {
    int status;

    public UserOrderInfoChildAdapter(@Nullable List<OrderGoods> data, int status) {
        super(R.layout.order_info_child_item, data);
        this.status = status;
    }

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

    @Override
    protected void convert(@NonNull BaseViewHolder helper, OrderGoods item) {
        String[] images = item.getGoods().getThumb_urls().split(",");
        Glide.with(helper.itemView)
                .applyDefaultRequestOptions(new RequestOptions().centerCrop().placeholder(R.mipmap.zhanwei))
                .load(images[0])
                .into((ImageView) helper.getView(R.id.iv_pic));

        helper.setText(R.id.tv_title, item.getGoods().getTitle());
        if(item.getSize()!=null){

            helper.setText(R.id.tv_color, item.getColor()+item.getSize());
        }else{
            helper.setText(R.id.tv_color, item.getColor());
        }

        helper.setText(R.id.tv_price, "￥ " + item.getPrice());
        helper.setText(R.id.tv_count, "x" + item.getCount());

        //按钮的状态
        switch (status) {
            //已支付
            case 1:
                if (item.getReturn_status() == 1) {
                    helper.setGone(R.id.tv_button, false);
                } else {
                    helper.setGone(R.id.tv_button, true);
                    helper.setText(R.id.tv_button, "申请退款");
                }

                break;
            //3已收货
            case 3:
                if (item.getEvaluate_status() == 1) {
                    helper.setGone(R.id.tv_button, false);
                } else {
                    helper.setGone(R.id.tv_button, true);
                    helper.setText(R.id.tv_button, "评价");

                }

                break;
            default:
                helper.setGone(R.id.tv_button, false);
                break;

        }

        helper.getView(R.id.tv_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click(((TextView) helper.getView(R.id.tv_button)).getText().toString(), item);
            }
        });
    }

    private void click(String text, OrderGoods item) {
        switch (text) {
            case "申请退款":
                if (onOrderWordListener != null) {
                    onOrderWordListener.refund(item);
                }
                break;

            case "评价":
                if (onOrderWordListener != null) {
                    onOrderWordListener.comment(item);
                }
                break;

        }
    }
}
