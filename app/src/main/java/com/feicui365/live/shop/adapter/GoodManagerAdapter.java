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
import com.feicui365.live.shop.entity.GoodManager;

import java.util.List;

public class GoodManagerAdapter extends BaseQuickAdapter<GoodManager, BaseViewHolder> {
    String status;

    public GoodManagerAdapter(@Nullable List<GoodManager> data, String status) {
        super(R.layout.good_manager_item, data);
        this.status = status;
    }

    GoodClickListener goodClickListener;

    public interface GoodClickListener {
        void onEdit(GoodManager item);

        void onUp(GoodManager item);
        void onDown(GoodManager item);
    }

    public GoodClickListener getGoodClickListener() {
        return goodClickListener;
    }

    public void setGoodClickListener(GoodClickListener goodClickListener) {
        this.goodClickListener = goodClickListener;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, GoodManager item) {
        String[] images = item.getThumb_urls().split(",");
        Glide.with(helper.itemView)
                .applyDefaultRequestOptions(new RequestOptions().centerCrop().placeholder(R.mipmap.zhanwei))
                .load(images[0])
                .into((ImageView) helper.getView(R.id.iv_pic));

        helper.setText(R.id.tv_title, item.getTitle());
        helper.setText(R.id.tv_type, item.getCategory().getTitle());
        helper.setText(R.id.tv_price, "￥ " + item.getPrice());
        helper.setText(R.id.tv_count, "已售出" + item.getSale_count() + "件");
        switch (status) {
            //已上架
            case "1":
                helper.setText(R.id.tv_button_1,"编辑商品");
                helper.setText(R.id.tv_button_2,"商品下架");

                helper.setGone(R.id.rl_bottom,true);
                break;
                //已下架
            case "2":
                helper.setText(R.id.tv_button_1,"编辑商品");
                helper.setText(R.id.tv_button_2,"商品上架");

                helper.setGone(R.id.rl_bottom,true);
                break;
            default:
                helper.setGone(R.id.rl_bottom,false);
                break;



        }

        helper.getView(R.id.tv_button_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if(goodClickListener!=null){
                goodClickListener.onEdit(item);
            }
            }
        });

        helper.getView(R.id.tv_button_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click(((TextView)helper.getView(R.id.tv_button_2)).getText().toString(),item);
            }
        });
    }

    private void click(String text, GoodManager item) {
        switch (text) {
            case "商品下架":
                if(goodClickListener!=null){
                    goodClickListener.onDown(item);
                }
                break;

            case "商品上架":
                if(goodClickListener!=null){
                    goodClickListener.onUp(item);
                }
                break;

        }
    }
}
