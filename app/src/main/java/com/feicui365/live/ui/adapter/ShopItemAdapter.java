package com.feicui365.live.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.feicui365.live.R;

import com.feicui365.live.net.APIService;
import com.feicui365.live.shop.entity.Good;
import com.feicui365.live.ui.act.WebShopActivity;
import com.feicui365.live.util.MyUserInstance;


import java.util.List;

public class ShopItemAdapter extends RecyclerView.Adapter<ShopItemAdapter.ViewHolder> {
    List<Good> shopItems;
    Context context;
    RoundedCorners roundedCorners = new RoundedCorners(10);
    //通过RequestOptions扩展功能
    RequestOptions options = RequestOptions.bitmapTransform(roundedCorners);

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


    public ShopItemAdapter(List<Good> shopItems, Context context) {
        this.shopItems = shopItems;
        this.context = context;
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
        holder.iv_see.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, WebShopActivity.class);
                i.putExtra("jump_url", APIService.Goods + shopItem.getId() + "?token=" + MyUserInstance.getInstance().getUserinfo().getToken() + "&uid=" + MyUserInstance.getInstance().getUserinfo().getId());

                context.startActivity(i);
           /*     Intent i = new Intent(context, GoodsInfoActivity.class);
                i.putExtra(Constants.GOODS_INFO, shopItem);

                context.startActivity(i);*/


            }
        });
    }

    @Override
    public int getItemCount() {
        return shopItems.size();
    }
}