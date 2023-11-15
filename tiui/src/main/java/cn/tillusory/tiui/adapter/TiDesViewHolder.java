package cn.tillusory.tiui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import cn.tillusory.tiui.R;

/**
 * Created by Anko on 2018/11/22.
 * Copyright (c) 2018-2020 拓幻科技 - tillusory.cn. All rights reserved.
 */
public class TiDesViewHolder extends RecyclerView.ViewHolder {

    public TextView tiTextTV;
    public ImageView tiImageIV;

    public TiDesViewHolder(View itemView) {
        super(itemView);
        tiTextTV = itemView.findViewById(R.id.tiTextTV);
        tiImageIV = itemView.findViewById(R.id.tiImageIV);
    }

}