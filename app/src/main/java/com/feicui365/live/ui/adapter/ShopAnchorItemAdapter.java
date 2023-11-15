package com.feicui365.live.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.feicui365.live.R;
import com.feicui365.live.shop.entity.Good;
import com.feicui365.live.util.ToastUtils;

import com.feicui365.live.util.HttpUtils;

import java.util.List;

public class ShopAnchorItemAdapter extends RecyclerView.Adapter<ShopAnchorItemAdapter.ViewHolder> {
    List<Good> shopItems;
    Context context;
    RoundedCorners roundedCorners = new RoundedCorners(10);
    //通过RequestOptions扩展功能
    RequestOptions options = RequestOptions.bitmapTransform(roundedCorners).centerCrop();
    public UpDate update;
    private int mType;
    public interface UpDate {
        void upDate(Good shopItem, int postion);
    }

    public UpDate getUpdate() {
        return update;
    }

    public void setUpdate(UpDate update) {
        this.update = update;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_see;
        ImageView iv_item_acvatar;
        TextView tv_no, tv_shop_title, tv_shop_price;

        public ViewHolder(View view) {
            super(view);
            iv_item_acvatar = view.findViewById(R.id.iv_item_acvatar);
            iv_see = view.findViewById(R.id.iv_see);
            tv_no = view.findViewById(R.id.tv_no);
            tv_shop_title = view.findViewById(R.id.tv_shop_title);
            tv_shop_price = view.findViewById(R.id.tv_shop_price);
        }

    }


    public ShopAnchorItemAdapter(List<Good> shopItems, Context context,int type) {
        this.shopItems = shopItems;
        this.context = context;
        this.mType=type;
    }

    @Override

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shop_list_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Good shopItem = shopItems.get(position);
        holder.tv_no.setText((position + 1) + "");
        holder.tv_shop_title.setText(shopItem.getTitle());
        holder.tv_shop_price.setText("￥ " + shopItem.getPrice());
        String[] images = shopItem.getThumb_urls().split(",");
        Glide.with(context).applyDefaultRequestOptions(options).asBitmap().load(images[0]).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                holder.iv_item_acvatar.setImageBitmap(resource);
            }
        });

        if(mType==0){
            if (shopItem.getLive_explaining().equals("1")) {
                Glide.with(context).load(R.mipmap.btn_finisih).into(holder.iv_see);
            } else {
                Glide.with(context).load(R.mipmap.btn_start).into(holder.iv_see);
            }
        }else{
            Glide.with(context).load(R.mipmap.see).into(holder.iv_see);

        }


        holder.iv_see.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (mType){
                    case 0:
                        switch (shopItem.getLive_explaining()) {
                            case "0":

                                HttpUtils.getInstance().explainingGoods(shopItem.getId(), "1", new StringCallback() {
                                    @Override
                                    public void onSuccess(Response<String> response) {
                                        JSONObject jsonObject = JSON.parseObject(response.body());
                                        if (HttpUtils.getInstance().swtichStatus(jsonObject)) {
                                            if (update != null) {
                                                shopItem.setLive_explaining("1");
                                                update.upDate(shopItem, position);
                                                Glide.with(context).load(R.mipmap.btn_finisih).into(holder.iv_see);
                                                ToastUtils.showT("开启讲解成功");
                                            }
                                        } else {
                                            ToastUtils.showT("开启讲解失败");
                                        }
                                    }
                                });
                                break;
                            case "1":

                                HttpUtils.getInstance().explainingGoods(shopItem.getId(), "0", new StringCallback() {
                                    @Override
                                    public void onSuccess(Response<String> response) {
                                        JSONObject jsonObject = JSON.parseObject(response.body());
                                        if (HttpUtils.getInstance().swtichStatus(jsonObject)) {
                                            if (update != null) {
                                                Glide.with(context).load(R.mipmap.btn_start).into(holder.iv_see);
                                                shopItem.setLive_explaining("0");
                                                update.upDate(shopItem, position);
                                                ToastUtils.showT("关闭讲解成功");
                                            }
                                        } else {
                                            ToastUtils.showT("关闭讲解失败");
                                        }
                                    }
                                });
                                break;
                        }

                        break;
                    case 1:
                        update.upDate(shopItem, position);
                        break;
                }


            }
        });
    }

    @Override
    public int getItemCount() {
        return shopItems.size();
    }
}