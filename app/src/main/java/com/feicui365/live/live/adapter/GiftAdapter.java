package com.feicui365.live.live.adapter;

import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import com.feicui365.live.R;
import com.feicui365.live.interfaces.OnGiftClickListener;
import com.feicui365.live.live.utils.GlideUtils;
import com.feicui365.live.model.entity.GiftInfo;

import org.jetbrains.annotations.NotNull;

/**
 *
 */
public class GiftAdapter extends BaseQuickAdapter<GiftInfo, BaseViewHolder> {
    ScaleAnimation mAnimation;
    OnGiftClickListener onGiftClickListener;
    int mChose = -1;

    public void setOnGiftClickListener(OnGiftClickListener onGiftClickListener) {
        this.onGiftClickListener = onGiftClickListener;
    }

    public GiftAdapter() {
        super(R.layout.item_gift_layout);
        initAnimation();
    }

    private void initAnimation() {
        mAnimation = new ScaleAnimation(0.9f, 1.1f, 0.9f, 1.1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        mAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        mAnimation.setDuration(400);
        mAnimation.setRepeatMode(Animation.REVERSE);
        mAnimation.setRepeatCount(-1);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder helper, GiftInfo item) {
        GlideUtils.setImage(mLayoutInflater.getContext(), item.getIcon(), helper.getView(R.id.iv_icon));
        helper.setText(R.id.tv_name, item.getTitle());
        helper.setText(R.id.tv_price, String.valueOf(item.getPrice()));
        helper.getView(R.id.iv_guardian).setVisibility(View.GONE);
        helper.getView(R.id.iv_all).setVisibility(View.GONE);
        switch (item.getUse_type()) {
            //1全2守
            case 1:
                helper.getView(R.id.iv_guardian).setVisibility(View.GONE);
                helper.getView(R.id.iv_all).setVisibility(View.VISIBLE);
                break;
            case 2:
                helper.getView(R.id.iv_guardian).setVisibility(View.VISIBLE);
                helper.getView(R.id.iv_all).setVisibility(View.GONE);
                break;
            default:
                helper.getView(R.id.iv_guardian).setVisibility(View.GONE);
                helper.getView(R.id.iv_all).setVisibility(View.GONE);
                break;
        }

        if (item.isCheck()) {
            helper.getView(R.id.iv_icon).startAnimation(mAnimation);
            helper.getView(R.id.fl_root).setBackgroundResource(R.drawable.shape_theme);
        } else {

            helper.getView(R.id.iv_icon).clearAnimation();
            helper.getView(R.id.fl_root).setBackgroundResource(0);
        }


        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mChose == helper.getLayoutPosition()) {
                    return;
                }


                setCheck(helper.getLayoutPosition());

                if (onGiftClickListener != null) {
                    onGiftClickListener.onGiftClick(item);
                }
                mChose = helper.getLayoutPosition();
            }
        });
    }


    private void setCheck(int postion) {


        for (int i = 0; i < getData().size(); i++) {
            if (i == postion) {
                getData().get(i).setCheck(true);
            } else {
                getData().get(i).setCheck(false);
            }
        }
        notifyDataSetChanged();

    }
}
