package com.feicui365.live.live.adapter;

import android.view.View;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import com.feicui365.live.R;
import com.feicui365.live.live.utils.ArmsUtils;
import com.feicui365.live.live.utils.GlideUtils;
import com.feicui365.live.model.entity.ContributeRank;
import com.feicui365.live.util.LevelUtils;

import org.jetbrains.annotations.NotNull;

/**
 *
 */
public class LiveRankAdapter extends BaseQuickAdapter<ContributeRank, BaseViewHolder> {


    public LiveRankAdapter() {
        super(R.layout.live_user_rank_item);

    }


    @Override
    protected void convert(@NotNull BaseViewHolder helper, ContributeRank bean) {
        switch (helper.getLayoutPosition()) {
            case 0:
                helper.setBackgroundRes(R.id.tv_rank, R.drawable.ic_live_rank_1);
                helper.setText(R.id.tv_rank, "");
                break;
            case 1:
                helper.setBackgroundRes(R.id.tv_rank, R.drawable.ic_live_rank_2);
                helper.setText(R.id.tv_rank, "");
                break;
            case 2:
                helper.setBackgroundRes(R.id.tv_rank, R.drawable.ic_live_rank_3);
                helper.setText(R.id.tv_rank, "");
                break;
            default:
                helper.setText(R.id.tv_rank, ArmsUtils.getRankNum(helper.getLayoutPosition()));
                helper.setBackgroundRes(R.id.tv_rank, 0);

                break;
        }


        GlideUtils.setImage(mLayoutInflater.getContext(), bean.getUser().getAvatar(), R.mipmap.moren, helper.getView(R.id.civ_avatar));
        if (ArmsUtils.getVipLevelIcon(bean.getUser().getVip_level(), bean.getUser().getVip_date()) == 0) {
            helper.getView(R.id.iv_vip).setVisibility(View.GONE);
        } else {
            helper.getView(R.id.iv_vip).setVisibility(View.VISIBLE);
            helper.setImageResource(R.id.iv_vip, ArmsUtils.getVipLevelIcon(bean.getUser().getVip_level(), bean.getUser().getVip_date()));
        }

        helper.setImageResource(R.id.iv_user_level, LevelUtils.getUserLevel(bean.getUser().getUser_level()));

        helper.setText(R.id.tv_name, bean.getUser().getNick_name());
        helper.setText(R.id.tv_num, ArmsUtils.formatNum(bean.getIntimacy()));

    }
}
