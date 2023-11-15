package cn.tillusory.tiui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import cn.tillusory.tiui.R;

/**
 * Created by Anko on 2020/4/27.
 * Copyright (c) 2020 拓幻科技 - tillusory.cn. All rights reserved.
 */
public class TiInteractionViewHolder extends RecyclerView.ViewHolder {

    public ImageView thumbIV, downloadIV, loadingIV;

    public TiInteractionViewHolder(View itemView) {
        super(itemView);
        thumbIV = itemView.findViewById(R.id.thumbIV);
        downloadIV = itemView.findViewById(R.id.downloadIV);
        loadingIV = itemView.findViewById(R.id.loadingIV);
    }

    public void startLoadingAnimation() {
        Animation animation = AnimationUtils.loadAnimation(itemView.getContext(), R.anim.loading_animation);
        loadingIV.startAnimation(animation);
    }

    public void stopLoadingAnimation() {
        loadingIV.clearAnimation();
    }

}