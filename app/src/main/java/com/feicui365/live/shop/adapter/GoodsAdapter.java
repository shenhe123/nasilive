package com.feicui365.live.shop.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.feicui365.live.R;
import com.feicui365.live.shop.entity.Good;

import java.util.List;

public class GoodsAdapter extends BaseQuickAdapter<Good, BaseViewHolder> {
    public GoodsAdapter( @Nullable List<Good> data) {
        super(R.layout.good_item, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, Good item) {
        String[] pic = item.getThumb_urls().split(",");
        Glide.with(helper.itemView)
                .applyDefaultRequestOptions(new RequestOptions().centerCrop().placeholder(R.mipmap.zhanwei))
                .load(pic[0])
                .into((ImageView) helper.getView(R.id.iv_cover));

        helper.setText(R.id.tv_title,item.getTitle());
        helper.setText(R.id.tv_price,"￥"+item.getPrice());
        helper.setText(R.id.tv_left,"已售 "+item.getSale_count()+" 件");
    }
}
