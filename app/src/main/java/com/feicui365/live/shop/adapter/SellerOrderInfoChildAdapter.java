package com.feicui365.live.shop.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.feicui365.live.R;
import com.feicui365.live.shop.entity.OrderGoods;

import java.util.List;

public class SellerOrderInfoChildAdapter extends BaseQuickAdapter<OrderGoods, BaseViewHolder> {
    int status;

    public SellerOrderInfoChildAdapter(@Nullable List<OrderGoods> data, int status) {
        super(R.layout.seller_order_info_child_item, data);
        this.status = status;
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



    }


}
