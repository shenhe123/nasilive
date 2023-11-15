package com.feicui365.live.shop.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.feicui365.live.R;

import java.util.List;

public class GoodsInfoAdapter extends BaseQuickAdapter<String, BaseViewHolder> {


    public GoodsInfoAdapter(@Nullable List<String> data) {
        super(R.layout.goods_pic, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, String item) {
        Glide.with(helper.itemView)
                .applyDefaultRequestOptions(new RequestOptions().fitCenter().placeholder(R.mipmap.zhanwei))
                .load(item)
                .into((ImageView) helper.getView(R.id.iv_pics));
    }
}
