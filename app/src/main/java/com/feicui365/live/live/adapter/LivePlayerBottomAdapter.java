package com.feicui365.live.live.adapter;



import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import com.feicui365.live.R;
import com.feicui365.live.live.bean.BottomItem;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 *
 */
public class LivePlayerBottomAdapter extends BaseQuickAdapter<BottomItem, BaseViewHolder> {


    public LivePlayerBottomAdapter(@Nullable List<BottomItem> data) {
        super(R.layout.live_play_bottom_item, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder helper, BottomItem bean) {


        helper.setImageResource(R.id.iv_icon, bean.getRes());


    }
}
