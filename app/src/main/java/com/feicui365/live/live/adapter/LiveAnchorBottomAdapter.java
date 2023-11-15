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
public class LiveAnchorBottomAdapter extends BaseQuickAdapter<BottomItem, BaseViewHolder> {



    public LiveAnchorBottomAdapter(@Nullable List<BottomItem> data) {
        super(R.layout.home_bottom_tab, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder helper, BottomItem bean) {


        if (helper.getLayoutPosition() == 2) {
            if (bean.isChose()) {
                helper.setImageResource(R.id.iv_icon, bean.getRes());
            } else {
                helper.setImageResource(R.id.iv_icon, bean.getRespre());
            }
        }else{
            helper.setImageResource(R.id.iv_icon, bean.getRes());
        }
        helper.setTextColor(R.id.tv_name,mLayoutInflater.getContext().getResources().getColor(R.color.color_6B6B6B) );

        helper.setText(R.id.tv_name, bean.getName());


    }
}
