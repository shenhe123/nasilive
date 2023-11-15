package com.feicui365.live.shop.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.feicui365.live.R;
import com.feicui365.live.shop.entity.OrderGoods;

import java.util.List;

public class FootMarkAdapter extends BaseQuickAdapter<OrderGoods, BaseViewHolder> {
    private boolean is_edit = false;

    public boolean isIs_edit() {
        return is_edit;
    }

    public void setIs_edit(boolean is_edit) {
        this.is_edit = is_edit;
    }

    private OnChoseListener onChoseListener;

    public interface OnChoseListener {
        void onChose();
    }

    public OnChoseListener getOnChoseListener() {
        return onChoseListener;
    }

    public void setOnChoseListener(OnChoseListener onChoseListener) {
        this.onChoseListener = onChoseListener;
    }

    public FootMarkAdapter(@Nullable List<OrderGoods> data) {
        super(R.layout.foot_mark_item, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, OrderGoods item) {
        String[] images=item.getGoods().getThumb_urls().split(",");
        Glide.with(helper.itemView)
                .applyDefaultRequestOptions(new RequestOptions().centerCrop().placeholder(R.mipmap.zhanwei))
                .load(images[0])
                .into((ImageView) helper.getView(R.id.iv_pic));
        helper.setText(R.id.tv_title, item.getGoods().getTitle());
        helper.setText(R.id.tv_time, item.getVisits_time());
        helper.setText(R.id.tv_price, "￥ " + item.getGoods().getPrice());


        helper.getView(R.id.rl_chose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    //开启编辑模式后,显示勾选状态
                    if (item.isIs_chose()) {
                        item.setIs_chose(false);
                        helper.setImageResource(R.id.iv_chose, R.drawable.radio_unchoose);

                    } else {
                        item.setIs_chose(true);
                        helper.setImageResource(R.id.iv_chose, R.drawable.radio_choose);
                    }

                    if (onChoseListener != null) {
                        onChoseListener.onChose();
                    }
            }
        });


        if (is_edit) {
            helper.setGone(R.id.rl_chose, true);
            if (item.isIs_chose()) {
                helper.setImageResource(R.id.iv_chose,  R.drawable.radio_choose);
            } else {
                helper.setImageResource(R.id.iv_chose, R.drawable.radio_unchoose);
            }
        } else {
            helper.setGone(R.id.rl_chose, false);
        }


    }
}
