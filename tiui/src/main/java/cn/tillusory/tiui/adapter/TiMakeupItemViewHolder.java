package cn.tillusory.tiui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import cn.tillusory.tiui.R;

/**
 * Created by Anko on 2019-09-05.
 * Copyright (c) 2018-2020 拓幻科技 - tillusory.cn. All rights reserved.
 */
public class TiMakeupItemViewHolder extends RecyclerView.ViewHolder {

    public ImageView thumbIV;
    public ImageView loadingIV;
    public ImageView downloadIV;
    public TextView nameTV;

    public TiMakeupItemViewHolder(View itemView) {
        super(itemView);
        thumbIV = itemView.findViewById(R.id.thumbIV);
        downloadIV = itemView.findViewById(R.id.downloadIV);
        loadingIV = itemView.findViewById(R.id.loadingIV);
        nameTV = itemView.findViewById(R.id.nameTV);
    }

    public void startLoadingAnimation() {
        Animation animation = AnimationUtils.loadAnimation(itemView.getContext(), R.anim.loading_animation);
        loadingIV.startAnimation(animation);
    }

    public void stopLoadingAnimation() {
        loadingIV.clearAnimation();
    }

}