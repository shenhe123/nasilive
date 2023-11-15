package com.feicui365.live.shop.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.feicui365.live.R;
import com.feicui365.live.shop.entity.Star;

import java.util.List;

public class StarAdapter extends BaseQuickAdapter<Star, BaseViewHolder> {
    public StarAdapter(@Nullable List<Star> data) {
        super(R.layout.star_item, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, Star item) {
        if (item.isCheck()) {
            helper.setImageResource(R.id.iv_star, R.mipmap.star_pick);
        } else {
            helper.setImageResource(R.id.iv_star, R.mipmap.star_unpick);
        }

        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < getData().size(); i++) {
                    if (i <= helper.getLayoutPosition()) {
                        getData().get(i).setCheck(true);
                    } else {
                        getData().get(i).setCheck(false);
                    }
                    notifyDataSetChanged();
                }
            }
        });
    }

    public int getStar() {
        int result = 0;
        for (int i = 0; i < getData().size(); i++) {
            if (getData().get(i).isCheck()) {
                result++;
            }
        }
        return result;
    }
}
